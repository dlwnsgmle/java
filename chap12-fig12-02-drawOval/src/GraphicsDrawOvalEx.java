import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GraphicsDrawOvalEx extends JFrame{
	private MyPanel Panel = new MyPanel();
	
	public GraphicsDrawOvalEx() {
		setTitle("drawOval «œ¿’");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(Panel);
		
		setSize(200, 170);
		setVisible(true);
	}
	
	class MyPanel extends JPanel {
		public void paintComponent(Graphics g) {
			g.setColor(Color.RED);
			g.drawOval(20, 20, 80, 80);
		}
	}
	public static void main(String [] args) {
		new GraphicsDrawOvalEx();
	}
}
