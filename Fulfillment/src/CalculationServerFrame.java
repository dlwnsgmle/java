import java.awt.BorderLayout;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CalculationServerFrame extends JFrame	 {
	private JTextArea log = new JTextArea();
	public CalculationServerFrame() {
		super("��Ƽ������ ��� ����");
		setSize(250, 250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//������ ���� ��ư
		//(X)�� Ŭ���ϸ� ���α׷� ����
		Container c = getContentPane();
		c.add(new JLabel("��� ���� �Դϴ�"));
		c.add(new JScrollPane(log), BorderLayout.CENTER);
		setVisible(true);
		
		new ServerThread().start();//���� ����
	}
	
	class ServerThread extends Thread {
		@Override
		public void run() {
			ServerSocket listener = null;
			Socket socket = null;
			try {
				listener = new ServerSocket(9998);
				while(true) {
					socket = listener.accept();
					log.append("Ŭ���̾�Ʈ �����\n");
					new ServerThread().start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				if(listener != null)
					listener.close();
				if(socket != null)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	class SetviceThread extends Thread {
		private Socket socket = null;
		private BufferedReader in = null;
		private BufferedWriter out = null;
		public SetviceThread(Socket socket) {
			this.socket = socket;
			try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		public void run() {
			while(true) {
				try {
					String first = in.readLine();
					String operator = in.readLine();
					String second = in.readLine();
					int a = Integer.parseInt(first);
					int b = Integer.parseInt(second);
					
					String resText = "" ;
					int res = 0;
					switch(operator) {
					case "+" : res = a + b; resText =
							Integer.toString(res); break;
					case "-" : res = a - b; resText =
							Integer.toString(res);break;
					case "*" : res = a * b; resText =
							Integer.toString(res); break;
					case "/" :
						if(b == 0) resText = "0���� ���� �� ����";
						else {
							res = a / b;
							resText =Integer.toString(res);
						}
						break;
						default:
							resText = "�߸��� ����";
					}
					
					out.write(resText+"\n");
					out.flush();
					log.append(first + operator + second + "=" +
					resText + "\n");
				}catch (IOException e) {
					log.append("���� ����");
					return;
				}
			}
		}
	}
	public static void main(String[] args) {
		new CalculationServerFrame();
	}
}