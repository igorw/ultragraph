package misc;

// representation of coordinates

public class Point {
	private int x = 0;
	private int y = 0;
	
	public Point() {
	}
	
	public Point(int x, int y) {
		this();
		
		setX(x);
		setY(y);
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setPoint(Point p) {
		setPoint(p.getX(), p.getY());
	}
	
	public void setPoint(int x, int y) {
		setX(x);
		setY(y);
	}
}
