package utils;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.AttributedCharacterIterator;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameBoard extends JPanel implements ActionListener{
	 
	 public static boolean message=false;
	 public String boardinfo="";
	
	BufferedImage bb, hb, qb, eb, etd, etl, etr, etu, p1td, p1tl, p1tr, p1tu, p2td, p2tl, p2tr, p2tu, sb, pp;
	char[][] board = new char[64][64];
	int[][] searchBoard = new int[64][64];
	int sizeX, sizeY;
	int hp1=100, hp2=100;
	int p1d = 1, p2d = -1; // 2 = up, 1 = right, -1 = left, -2 = right
	int p1x = 0, p1y = 0, p2x, p2y;
	Bullet bullet1, bullet2; Boolean bullet1exists = false, bullet2exists = false;
	JFrame frame;
	boolean shootside1 = false, shootside2 = false, sp1 = false, sp2 = false;
	ArrayList<Cord> path;
	public void keyTyped(KeyEvent e) {}
	JLabel label1 = new JLabel();
	JLabel label2 = new JLabel();
	
	
	public boolean weak(char c) {
		if(c=='S') return false;
		if(c>='g'&&c<='z') return false;
		return true;
	}
	
	public void updateInfo() {
		boardinfo = "";
		for(int i=0;i<sizeX;i++) {
			for(int j=0;j<sizeY;j++) {
				boardinfo+=board[i][j];
			}
		}
	}
	
	public GameBoard(File myFile) {
		
//		System.out.println("Welcome to Tank Game! Here are the rules.");
//		System.out.println("Use WASD to move, arrows to shoot.");
//		System.out.println("The space key enables side shooting mode, where your bullet flies to the side a little bit.");
//		System.out.println("The Q key generates \"bullet nasa\" (crazy bullet), whose track you would never know.");
//		System.out.println("The E key builds walls to protect you.");
		
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
		
		
		
		Scanner myScanner;
		try {
			myScanner = new Scanner(myFile);
			String size = myScanner.next();
			String line;
			sizeX = Integer.parseInt(size.substring(0, size.length()-1));
			size = myScanner.next();
			sizeY = Integer.parseInt(size.substring(0 ,size.length()-1));
			
			this.setLayout(null);
			label1.setBounds(50*(sizeY-3), 5, 150, 20);
			label1.setText("Player");
			label2.setBounds(50*(sizeY-3), 30, 150, 20);
			label2.setText("Player");
			this.add(label1);
			this.add(label2);
			
			p2x = 50*(sizeY-1); p2y = 50*(sizeX-1);
			double d;
			for(int i=0;i<sizeX;i++) {
				line = myScanner.next();
				for(int j=0;j<sizeY;j++) {
					// randomly generate map
					if(j>sizeY/4&&j<sizeY*3/4) {
						d = Math.random();
						if(d<0.4)board[i][j]='O';
						else if(d<0.8)board[i][j]='D';
						else board[i][j]='S';
					}
					else board[i][j] = line.charAt(j);
					boardinfo+=line.charAt(j);
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		addKeyListener(new Tank1Listener());	
	
		this.setFocusable(true);
		
		//Graphics g;
		
		
	}
	
public GameBoard(char[][] data) {
		
//		System.out.println("Welcome to Tank Game! Here are the rules.");
//		System.out.println("Use WASD to move, arrows to shoot.");
//		System.out.println("The space key enables side shooting mode, where your bullet flies to the side a little bit.");
//		System.out.println("The Q key generates \"bullet nasa\" (crazy bullet), whose track you would never know.");
//		System.out.println("The E key builds walls to protect you.");
		
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
		
		
		
		Scanner myScanner;
			sizeX = data.length;
			sizeY = data[0].length;
			
			this.setLayout(null);
			label1.setBounds(50*(sizeY-3), 5, 150, 20);
			label1.setText("Player");
			label2.setBounds(50*(sizeY-3), 30, 150, 20);
			label2.setText("Player");
			this.add(label1);
			this.add(label2);
			
			p2x = 50*(sizeY-1); p2y = 50*(sizeX-1);
			double d;
			for(int i=0;i<sizeX;i++) {
				for(int j=0;j<sizeY;j++) {
					board[i][j] = data[i][j];
				}
			}
		

		addKeyListener(new Tank1Listener());	
	
		this.setFocusable(true);
		
		//Graphics g;
		
		
	}
	
	
	
	protected void paintComponent(Graphics g) {
		for(int i=0;i<sizeX;i++) {
			for(int j=0;j<sizeY;j++) {
				char c = board[i][j];
				if(c=='D')g.drawImage(bb, j*50, i*50, null);
				if(c=='S' || (c>='a'&&c<='z'))g.drawImage(sb, j*50, i*50, null);
				if(c=='H')g.drawImage(hb, j*50, i*50, null);
				if(c=='Q')g.drawImage(qb, j*50, i*50, null);
				
				if(c=='1' || c=='2')board[i][j]='O';
				
			}
		}
		if(hp1<0) {
			p1x=p1y=-50;
		}
		if(hp2<0) {
			p2x=p2y=-50;
		}
		
		if(hp1>0) {
			if(p1d == 2)g.drawImage(p1tu, p1x, p1y, null);
			if(p1d == 1)g.drawImage(p1tr, p1x, p1y, null);
			if(p1d == -1)g.drawImage(p1tl, p1x, p1y, null);
			if(p1d == -2)g.drawImage(p1td, p1x, p1y, null);
		}
		if(hp2>0) {
			if(p2d == 2)g.drawImage(p2tu, p2x, p2y, null);
			if(p2d == 1)g.drawImage(p2tr, p2x, p2y, null);
			if(p2d == -1)g.drawImage(p2tl, p2x, p2y, null);
			if(p2d == -2)g.drawImage(p2td, p2x, p2y, null);
		}
		
		if(bullet1exists)g.drawImage(eb, bullet1.getX(), bullet1.getY(), null);
		if(bullet2exists)g.drawImage(eb, bullet2.getX(), bullet2.getY(), null);
		label1.setText("Player 1: "+hp1+"/100    ");
		label2.setText("Player 2: "+hp2+"/100    ");

		path = shortestPath(board, p1x, p1y, p2x, p2y);
		if(sp1&&path!=null) {
			for(Cord c: path) {
				g.drawImage(pp, c.getY()*50, c.getX()*50, null);
			}
		}
		if(sp2&&path!=null) {
			for(Cord c: path) {
				g.drawImage(pp, c.getY()*50, c.getX()*50, null);
			}
		}
	}
	
	// WASD = move; arrows = shoot
	
	public GameBoard() {
		
	}
	
	public static void main(String[] args) {
			
			File theFile = new File("something.txt");
			GameBoard gameboard = new GameBoard(theFile);
			JFrame frame;
			frame = new JFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			frame.setSize(gameboard.sizeY*50, gameboard.sizeX*50+25);
			frame.getContentPane().add(gameboard);
			
			
			Server server = new Server(5002);
			System.out.println("Logged in as server");
			

			Runnable helloRunnable = new Runnable() {
			    public void run() {
			          
			        if(gameboard.bullet1exists) {
			        	
			        	gameboard.bullet1.move();
			        	
			        	if(gameboard.bullet1.getX()<0 || gameboard.bullet1.getY()<0 || gameboard.bullet1.getX()>50*gameboard.sizeY || gameboard.bullet1.getY()>50*gameboard.sizeX) {
			        		gameboard.bullet1exists=false; 
			        	}
			        	
			        	if(gameboard.bullet1exists){
			        	
			        	if(Math.abs(gameboard.bullet1.getX()-gameboard.p2x-25)<=25&&Math.abs(gameboard.bullet1.getY()-gameboard.p2y-25)<=25){
			        		gameboard.hp2-=gameboard.bullet1.getD()/50;
			        		if(gameboard.bullet1.getD()<50)gameboard.hp2--;
			        		if(gameboard.hp2<0)gameboard.hp2=0;
			        		gameboard.bullet1exists=false;
			        	}
			        	
			        	int tx = gameboard.bullet1.getX()/50, ty = gameboard.bullet1.getY()/50;
			        	if(gameboard.board[ty][tx]=='Q') {
			        		gameboard.board[ty][tx]='O'; gameboard.bullet1exists = false;
			        	}
			        	if(gameboard.board[ty][tx]=='H') {
			        		gameboard.board[ty][tx]='Q'; gameboard.bullet1exists = false;
			        	}
			        	if(gameboard.board[ty][tx]=='D') {
			        		gameboard.board[ty][tx]='H'; gameboard.bullet1exists = false;
			        	}
			        	if(gameboard.board[ty][tx]=='a') {
			        		gameboard.board[ty][tx]='D'; gameboard.bullet1exists = false;
			        	}
			        	if(gameboard.board[ty][tx]>='b' && gameboard.board[ty][tx]<='z') {
			        		gameboard.board[ty][tx]--; gameboard.bullet1exists = false;
			        	}
			        	if(gameboard.board[ty][tx]=='S') {
			        		gameboard.board[ty][tx]='z';gameboard.bullet1exists = false;
			        	}
			        	
			        	}
			        }
			        
			        
			        if(gameboard.bullet2exists) {
			        	gameboard.bullet2.move();
			        	
			        	if(gameboard.bullet2.getX()<0 || gameboard.bullet2.getY()<0 || gameboard.bullet2.getX()>50*gameboard.sizeY || gameboard.bullet2.getY()>50*gameboard.sizeX) {
			        		gameboard.bullet2exists=false; 
			        	}
			        	
			        	if(gameboard.bullet2exists){
			        	
			        	if(Math.abs(gameboard.bullet2.getX()-gameboard.p1x-25)<=25&&Math.abs(gameboard.bullet2.getY()-gameboard.p1y-25)<=25){
			        		gameboard.hp1-=gameboard.bullet2.getD()/50;
			        		if(gameboard.bullet2.getD()<50)gameboard.hp1--;
			        		if(gameboard.hp1<0)gameboard.hp1=0;
			        		gameboard.bullet2exists=false;
			        	}
			        	
			        	int tx = gameboard.bullet2.getX()/50, ty = gameboard.bullet2.getY()/50;
			        	if(gameboard.board[ty][tx]=='Q') {
			        		gameboard.board[ty][tx]='O'; gameboard.bullet2exists = false;
			        	}
			        	if(gameboard.board[ty][tx]=='H') {
			        		gameboard.board[ty][tx]='Q'; gameboard.bullet2exists = false;
			        	}
			        	if(gameboard.board[ty][tx]=='D') {
			        		gameboard.board[ty][tx]='H'; gameboard.bullet2exists = false;
			        	}
			        	if(gameboard.board[ty][tx]=='a') {
			        		gameboard.board[ty][tx]='D'; gameboard.bullet2exists = false;
			        	}
			        	if(gameboard.board[ty][tx]>='b' && gameboard.board[ty][tx]<='z') {
			        		gameboard.board[ty][tx]--; gameboard.bullet2exists = false;
			        	}
			        	if(gameboard.board[ty][tx]=='S') {
			        		gameboard.board[ty][tx]='z';gameboard.bullet2exists = false;
			        	}
			        	
			        	}
			        }
			        
			        frame.repaint();
			        gameboard.repaint();
			    }
			    
			};

			ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
			executor.scheduleAtFixedRate(helloRunnable, 0, 50, TimeUnit.MILLISECONDS);
			
			while(true) {
				try {
			    	 server.line = server.in.readUTF();
				} catch (IOException e) {
					e.printStackTrace();
				}
					gameboard.readline(server.line);
					gameboard.repaint();
					
					gameboard.updateInfo();
					
			}
			
	}
	
	
	
	private class Tank1Listener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
			// I should prob switch the x and y coordinates
			if(e.getKeyCode() == KeyEvent.VK_W) {
				p1d = 2;
				if(p1y>=10) {
					if(board[(p1y-9)/50][(p1x+1)/50]=='O' && board[(p1y-9)/50][(p1x+49)/50]=='O')p1y-=10;
				}
				repaint();
			}
			
			if(e.getKeyCode() == KeyEvent.VK_S) {
				p1d = -2;
				if(p1y<=50*(sizeX-1)-10) {
					if(board[(p1y+59)/50][(p1x+1)/50]=='O' && board[(p1y+59)/50][(p1x+49)/50]=='O')p1y+=10;
				}
				repaint();
			}
			
			if(e.getKeyCode() == KeyEvent.VK_A) {
				p1d = -1;
				if(p1x>=10) {
					if(board[(p1y+1)/50][(p1x-9)/50]=='O' && board[(p1y+49)/50][(p1x-9)/50]=='O')p1x-=10;
				}
				repaint();
			}
			
			if(e.getKeyCode() == KeyEvent.VK_D) {
				p1d = 1;
				if(p1x<=50*(sizeY-1)-10) {
					if(board[(p1y+1)/50][(p1x+59)/50]=='O' && board[(p1y+49)/50][(p1x+59)/50]=='O')p1x+=10;
				}
				repaint();
			}
			
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				p1d = 2;
				bullet1 = new Bullet(p1x+25, p1y+25, 2, shootside1);
				bullet1exists = true;
				repaint();
			}
			
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				p1d = 1;
				bullet1 = new Bullet(p1x+25, p1y+25, 1, shootside1);
				bullet1exists = true;
				repaint();
			}
			
			if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				p1d = -1;
				bullet1 = new Bullet(p1x+25, p1y+25, -1, shootside1);
				bullet1exists = true;
				repaint();
			}
			
			if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				p1d = -2;
				bullet1 = new Bullet(p1x+25, p1y+25, -2, shootside1);
				bullet1exists = true;
				repaint();
			}
			if(e.getKeyCode() == KeyEvent.VK_Q) {
				bullet1 = new Bullet(p1x+25, p1y+25, 0, shootside1);
				bullet1exists = true;
				repaint();
			}
			if(e.getKeyCode() == KeyEvent.VK_E) {
				boolean flag1 = false, flag2 = true;
				int dxx,dyy;
				if(p1x%50!=0||p1y%50!=0)flag2 = false;
				int xx = p1x/50, yy = p1y/50;
				for(int i=-1;i<=1;i++) {
					for(int j=-1;j<=1;j++) {
						if(xx+i>=0 && xx+i<sizeY && yy+j>=0 && yy+j<sizeX) {
							if(i*i+j*j>0&&weak(board[yy+j][xx+i])) {
								flag1=true;
							}
						}
					}
				} 
				while(flag1&&flag2) {
					dxx = (int)(Math.random()*3)-1; dyy = (int)(Math.random()*3)-1;
					if(xx+dxx>=0 && xx+dxx<sizeY && yy+dyy>=0 && yy+dyy<sizeX){
						if((dxx*dxx+dyy*dyy>0)&&weak(board[yy+dyy][xx+dxx])) {
							board[yy+dyy][xx+dxx]='g';
							flag1 = false;
						}
					}
					
				}
				repaint();
			}
			
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				shootside1=!shootside1;
				repaint();
			}
			if(e.getKeyCode() == KeyEvent.VK_X) {
				sp1 = !sp1;
				
			}
			if(e.getKeyCode() == KeyEvent.VK_Z) {
				path = shortestPath(board, p1x, p1y, p2x, p2y);
				if(path.size()>0) {
					bullet1 = new Bullet(p1x+25,p1y+25,path);
					bullet1exists = true;
				}
				repaint();
			}
			}
		

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	
	// Second Tank
	
	public void readline(String theline){
		if(theline==null)theline = "RECEIVED";
			// I should prob switch the x and y coordinates
			if(theline.equals("UP")) {
				p2d = 2;
				if(p2y>=10) {
					if(board[(p2y-9)/50][(p2x+1)/50]=='O' && board[(p2y-9)/50][(p2x+49)/50]=='O')p2y-=10;
				}
				repaint();
			}
			
			if(theline.equals("DOWN")) {
				p2d = -2;
				if(p2y<=50*(sizeX-1)-10) {
					if(board[(p2y+59)/50][(p2x+1)/50]=='O' && board[(p2y+59)/50][(p2x+49)/50]=='O')p2y+=10;
				}
				repaint();
			}
			
			if(theline.equals("LEFT")) {
				p2d = -1;
				if(p2x>=10) {
					if(board[(p2y+1)/50][(p2x-9)/50]=='O' && board[(p2y+49)/50][(p2x-9)/50]=='O')p2x-=10;
				}
				repaint();
			}
			
			if(theline.equals("RIGHT")) {
				p2d = 1;
				if(p2x<=50*(sizeY-1)-10) {
					if(board[(p2y+1)/50][(p2x+59)/50]=='O' && board[(p2y+49)/50][(p2x+59)/50]=='O')p2x+=10;
				}
				repaint();
			}
			
			if(theline.equals("UPSHOOT")) {
				p2d = 2;
				bullet2 = new Bullet(p2x+25, p2y+25, 2, shootside2);
				bullet2exists = true;
				repaint();
			}
			
			if(theline.equals("RIGHTSHOOT")) {
				p2d = 1;
				bullet2 = new Bullet(p2x+25, p2y+25, 1, shootside2);
				bullet2exists = true;
				repaint();
			}
			
			if(theline.equals("LEFTSHOOT")) {
				p2d = -1;
				bullet2 = new Bullet(p2x+25, p2y+25, -1, shootside2);
				bullet2exists = true;
				repaint();
			}
			
			if(theline.equals("DOWNSHOOT")) {
				p2d = -2;
				bullet2 = new Bullet(p2x+25, p2y+25, -2, shootside2);
				bullet2exists = true;
				repaint();
			}
			if(theline.equals("NASA")) {
				bullet2 = new Bullet(p2x+25, p2y+25, 0, shootside2);
				bullet2exists = true;
				repaint();
			}
			if(theline.equals("BUILD")) {
				boolean flag1 = false, flag2 = true;
				int dxx,dyy;
				if(p2x%50!=0||p2y%50!=0)flag2 = false;
				int xx = p2x/50, yy = p2y/50;
				for(int i=-1;i<=1;i++) {
					for(int j=-1;j<=1;j++) {
						if(xx+i>=0 && xx+i<sizeY && yy+j>=0 && yy+j<sizeX) {
							if(i*i+j*j>0&&weak(board[yy+j][xx+i])) {
								flag1=true;
							}
						}
					}
				} 
				while(flag1&&flag2) {
					dxx = (int)(Math.random()*3)-1; dyy = (int)(Math.random()*3)-1;
					if(xx+dxx>=0 && xx+dxx<sizeY && yy+dyy>=0 && yy+dyy<sizeX){
						if((dxx*dxx+dyy*dyy>0)&&weak(board[yy+dyy][xx+dxx])) {
							board[yy+dyy][xx+dxx]='g';
							flag1 = false;
						}
					}
					
				}
				repaint();
			}
			
			if(theline.equals("CHANGE")) {
				shootside2=!shootside2;
				repaint();
			}
			
			if(theline.equals("SHOWPATH")) {
				sp2 = !sp2;
				repaint();
			}
			
			if(theline.equals("CHEAT")) {
				path = shortestPath(board, p2x, p2y, p1x, p1y);
				if(path.size()>0) {
					bullet2 = new Bullet(p2x+25,p2y+25,path);
					bullet2exists = true;
				}
				repaint();
			}
			
			if(theline.equals("RESERVED")) {
				
			}
			theline = "RECEIVED";
			return;
		}

	@Override
	public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
		
	}
//	public boolean full(int[][] numberBoard) {
//		for(int i=0; i < numberBoard.length;i++) {
//			for(int j=0;j < numberBoard[0].length; j++) {
//				if(numberBoard[i][j]==0)return false;
//			}
//		}
//		return true;
//	}
	
	public ArrayList<Cord> shortestPath(char[][] theBoard, int x1, int y1, int x2, int y2){
		x1=(x1+25)/50; x2=(x2+25)/50; y1=(y1+25)/50; y2=(y2+25)/50;
		int l = 16, w = 20;
		ArrayList<Cord> list = new ArrayList<Cord>();
		int[][] numberBoard = new int[l][w];
		// -1 = obstacle; 0 = TBD; 1 = starting point
		//initialize
		numberBoard[y1][x1] = 1;
		for(int i=0;i<l;i++) {
			for(int j=0;j<w;j++) {
				if(theBoard[i][j]!='O')numberBoard[i][j]=-1;
			}
		}
		//loop until endpoint filled
		int loopnumber = 0, a,b,c,d,e;
		while(numberBoard[y2][x2]==0&&loopnumber<320){
			for(int i=0;i<l;i++) {
				for(int j=0;j<w;j++) {
					if(numberBoard[i][j]==0) {
						a=b=c=d=0;
						if(i>0)a = numberBoard[i-1][j]; 
						if(i<l-1)b = numberBoard[i+1][j];
						if(j>0)c = numberBoard[i][j-1];
						if(j<w-1)d = numberBoard[i][j+1];
						if(a<=0)a=69420; if(b<=0)b=69420; if(c<=0)c=69420; if(d<=0)d=69420;
						e = Math.min(Math.min(a, b), Math.min(c, d));
						if(e>0&&e<1000)numberBoard[i][j]=1+e;
					}
				}
			}
			loopnumber++;
		}
		
		if(numberBoard[y2][x2]>0) {
			int y=x2, x=y2, step=numberBoard[y2][x2];
			while(step>1) {
				Cord cord = new Cord(x,y);
				list.add(cord);
				if(x>0) {
					if(numberBoard[x-1][y]==step-1) {
						x--; step--; continue;
					}
				}
				if(x<l-1) {
					if(numberBoard[x+1][y]==step-1) {
						x++; step--; continue;
					}
				}
				if(y>0) {
					if(numberBoard[x][y-1]==step-1) {
						y--; step--; continue;
					}
				}
				if(y<w-1) {
					if(numberBoard[x][y+1]==step-1) {
						y++; step--; continue;
					}
				}
			}
			Cord cord = new Cord(y1,x1);
			list.add(cord);
		}
		
		//
//		for(int i=0;i<l;i++) {
//			for(int j=0;j<w;j++) {
//				if(numberBoard[i][j]>=0&&numberBoard[i][j]<=9)System.out.print(" ");
//				System.out.print(numberBoard[i][j]+" ");
//			}
//			System.out.println();
//		}
//		System.out.println();
//		for(Cord cord: list) {
//			System.out.println(cord.getX()+" "+cord.getY());
//		}
//		System.out.println();
		//
		return list;
	}
	
}

