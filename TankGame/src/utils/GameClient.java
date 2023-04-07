package utils;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GameClient extends JPanel implements ActionListener {
	JFrame tframe;
	Client client;
	char[][] board = new char[16][20];
	BufferedImage bb, hb, qb, eb, etd, etl, etr, etu, p1td, p1tl, p1tr, p1tu, p2td, p2tl, p2tr, p2tu, sb, pp;
	
	public GameClient() {
		
		try {
			bb = ImageIO.read(new File("break_brick.jpg"));
			hb = ImageIO.read(new File("half_brick.png"));
			qb = ImageIO.read(new File("quarter_brick.png"));
			eb = ImageIO.read(new File("enemy_bullet.png"));
			etd = ImageIO.read(new File("enemy_tank_down.png"));
			etl = ImageIO.read(new File("enemy_tank_left.png"));
			etr = ImageIO.read(new File("enemy_tank_right.png"));
			etu = ImageIO.read(new File("enemy_tank_up.png"));
			p1td = ImageIO.read(new File("player1_tank_down.png"));
			p1tl = ImageIO.read(new File("player1_tank_left.png"));
			p1tr = ImageIO.read(new File("player1_tank_right.png"));
			p1tu = ImageIO.read(new File("player1_tank_up.png"));
			p2td = ImageIO.read(new File("player2_tank_down.png"));
			p2tl = ImageIO.read(new File("player2_tank_left.png"));
			p2tr = ImageIO.read(new File("player2_tank_right.png"));
			p2tu = ImageIO.read(new File("player2_tank_up.png"));
			sb = ImageIO.read(new File("solid_brick.jpg"));
			pp = ImageIO.read(new File("pathpoint.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		tframe = new JFrame();
		client = new Client("172.16.200.173", 5002);
		tframe.setSize(100, 100);
		tframe.setVisible(true);
		tframe.getContentPane().add(this);
		addKeyListener(new LineInput());
		this.setFocusable(true);
		tframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void paintComponent(Graphics g) {
		for(int i=0;i<16;i++) {
			for(int j=0;j<20;j++) {
				char c = board[i][j];
				if(c=='D')g.drawImage(bb, j*50, i*50, null);
				if(c=='S' || (c>='a'&&c<='z'))g.drawImage(sb, j*50, i*50, null);
				if(c=='H')g.drawImage(hb, j*50, i*50, null);
				if(c=='Q')g.drawImage(qb, j*50, i*50, null);
				
				if(c=='1' || c=='2')board[i][j]='O';
				
			}
		}
//		if(hp1<0) {
//			p1x=p1y=-50;
//		}
//		if(hp2<0) {
//			p2x=p2y=-50;
//		}
//		
//		if(hp1>0) {
//			if(p1d == 2)g.drawImage(p1tu, p1x, p1y, null);
//			if(p1d == 1)g.drawImage(p1tr, p1x, p1y, null);
//			if(p1d == -1)g.drawImage(p1tl, p1x, p1y, null);
//			if(p1d == -2)g.drawImage(p1td, p1x, p1y, null);
//		}
//		if(hp2>0) {
//			if(p2d == 2)g.drawImage(p2tu, p2x, p2y, null);
//			if(p2d == 1)g.drawImage(p2tr, p2x, p2y, null);
//			if(p2d == -1)g.drawImage(p2tl, p2x, p2y, null);
//			if(p2d == -2)g.drawImage(p2td, p2x, p2y, null);
//		}
//		
//		if(bullet1exists)g.drawImage(eb, bullet1.getX(), bullet1.getY(), null);
//		if(bullet2exists)g.drawImage(eb, bullet2.getX(), bullet2.getY(), null);
//		label1.setText("Player 1: "+hp1+"/100    ");
//		label2.setText("Player 2: "+hp2+"/100    ");
//
//		path = shortestPath(board, p1x, p1y, p2x, p2y);
//		if(sp1&&path!=null) {
//			for(Cord c: path) {
//				g.drawImage(pp, c.getY()*50, c.getX()*50, null);
//			}
//		}
//		if(sp2&&path!=null) {
//			for(Cord c: path) {
//				g.drawImage(pp, c.getY()*50, c.getX()*50, null);
//			}
//		}
	}
	
	public void actionPerformed(ActionEvent e) {
		
		
	}
	
	public static void main(String[] args) {
		GameClient tp = new GameClient();
		
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
