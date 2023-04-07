package utils;

import java.util.ArrayList;

public class Bullet {
	
	private int x, y, dx, dy, d;
	private boolean side,nasa,way;
	private ArrayList<Cord> myway;
	
	public Bullet() {
		x = y = dx = dy = d = 0;
	}
	
	public Bullet(int x0, int y0, int dx0, int dy0) {
		x=x0; y=y0; dx=dx0; dy=dy0;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getDx() {
		return dx;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public int getDy() {
		return dy;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}

	public Bullet(int x0, int y0, int dir, boolean side) {
		x=x0; y=y0; dx=dy=d=0;
		double de = Math.random();
		if(dir==2) {
			dy=-10;
			if(side) {
				if(de<.5)dx=-1;
				else dx=1;
			}
		}
		if(dir==1) {
			dx=10;
			if(side) {
				if(de<.5)dy=-1;
				else dy=1;
			}
		}
		if(dir==-1) {
			dx=-10;
			if(side) {
				if(de<.5)dy=-1;
				else dy=1;
			}
		}
		if(dir==-2) {
			dy=10;
			if(side) {
				if(de<.5)dx=-1;
				else dx=1;
			}
		}
		if(dir==0) {
			nasa=true;
		}
	}
	
	public Bullet(int x0, int y0, ArrayList<Cord> pathway) {
		x=x0; y=y0;
		way = true; myway = pathway;
		
	}
	
	public boolean isSide() {
		return side;
	}

	public void setSide(boolean side) {
		this.side = side;
	}

	public void move() {
		x+=dx; y+=dy; d+=(int)(Math.sqrt(dx*dx+dy*dy));
		if(nasa) {
			dx=(int)(Math.random()*401-200);
			dy=(int)(Math.random()*401-200);
		}
		if(way&&myway.size()>0) {
			Cord waypoint;
			waypoint = myway.get(myway.size()-1);
			int waypointX = waypoint.getY()*50+25, waypointY = waypoint.getX()*50+25;
			if(waypointX==x&&waypointY==y)myway.remove(myway.size()-1);
			{
				dx = 10*(int)Math.signum(waypointX-x);
				dy = 10*(int)Math.signum(waypointY-y);
			}
		}
		if(way&&myway.size()==0) {
			nasa = true;
		}
	}
	
	public int getD() {
		return d;
	}

	public void setD(int d) {
		this.d = d;
	}

	public static void main(String[] args){
		
	}
	
}
