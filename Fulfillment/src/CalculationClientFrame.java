import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CalculationClientFrame extends JFrame {
	private JTextField startTf = new JTextField(7);
	private JTextField operatorTf = new JTextField(3);
	private JTextField endTf = new JTextField(7);
	private JTextField resTf = new JTextField(7);
	private JButton calcBtn = new JButton("���");
	private Socket socket = null;
	private BufferedReader in = null;
	private BufferedWriter out = null;
	
	public CalculationClientFrame() {
		super("��� Ŭ���̾�Ʈ");
		setSize(410, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//������ ��(X)�� Ŭ���ϸ� ���α׷� ����
		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		c.add(startTf);
		c.add(operatorTf);
		c.add(endTf);
		c.add(new JLabel("="));
		c.add(resTf);
		c.add(calcBtn);
		
		setVisible(true);
		
		setupConnection();
		
		calcBtn.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent arg0) {
			try {
				String startText = startTf.getText().trim();
				String operatorText = operatorTf.getText().trim();
				String endText = endTf.getText().trim();
				if(startText.length() == 0 || operatorText.length() == 0 || endText.length() == 0)
					return; //�Էµ��� �ʾ���
				out.write(startText+"\n");
				out.write(operatorText+"\n");
				out.write(endText+"\n");
				out.flush();
				
				String result = in.readLine();
				resTf.setText(result);
			}catch (IOException e) {
				System.out.println("Ŭ���̾�Ʈ : ��������");
				return;
				//e.printStackTrace();
				}
			}
		});
		
		
	}
	public void setupConnection() {
		try{
			socket = new Socket("localhost",9998);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		new CalculationClientFrame();
	}
}

