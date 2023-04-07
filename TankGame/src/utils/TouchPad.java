package utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.*;

public class TouchPad extends JPanel implements ActionListener {
	JFrame tframe;
	Client client;
	
	public TouchPad() {
		tframe = new JFrame();
		client = new Client("172.16.200.173", 5002);
		tframe.setSize(100, 100);
		tframe.setVisible(true);
		tframe.getContentPane().add(this);
		addKeyListener(new LineInput());
		this.setFocusable(true);
		tframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent e) {
		
		
	}
	
	public static void main(String[] args) {
		TouchPad tp = new TouchPad();
		
	}
	
	private class LineInput implements KeyListener{

		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			{
				try {
					
					if(e.getKeyCode() == KeyEvent.VK_W) client.out.writeUTF("UP");
					if(e.getKeyCode() == KeyEvent.VK_S)client.out.writeUTF("DOWN");
					if(e.getKeyCode() == KeyEvent.VK_A)client.out.writeUTF("LEFT");
					if(e.getKeyCode() == KeyEvent.VK_D)client.out.writeUTF("RIGHT");
					if(e.getKeyCode() == KeyEvent.VK_UP)client.out.writeUTF("UPSHOOT");
					if(e.getKeyCode() == KeyEvent.VK_DOWN)client.out.writeUTF("DOWNSHOOT");
					if(e.getKeyCode() == KeyEvent.VK_LEFT)client.out.writeUTF("LEFTSHOOT");
					if(e.getKeyCode() == KeyEvent.VK_RIGHT)client.out.writeUTF("RIGHTSHOOT");
					if(e.getKeyCode() == KeyEvent.VK_SPACE)client.out.writeUTF("CHANGE");
					if(e.getKeyCode() == KeyEvent.VK_Q)client.out.writeUTF("NASA");
					if(e.getKeyCode() == KeyEvent.VK_E)client.out.writeUTF("BUILD");
					if(e.getKeyCode() == KeyEvent.VK_X)client.out.writeUTF("SHOWPATH");
					if(e.getKeyCode() == KeyEvent.VK_Z)client.out.writeUTF("CHEAT");
					if(e.getKeyCode() != KeyEvent.VK_0)GameBoard.message = true;
				} catch (IOException e1){
					e1.printStackTrace();
				}
			} 
			
		}

		public void keyReleased(KeyEvent e) {	
		}
		
	}
}
