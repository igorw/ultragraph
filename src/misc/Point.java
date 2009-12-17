package misc;

/**
 * point
 * representation of coordinates
 */
public class Point {
	/**
	 * x coordinate
	 */
	private int x = 0;
	
	/**
	 * y coordinate
	 */
	private int y = 0;
	
	/**
	 * default constructor
	 */
	public Point() {
	}
	
	/**
	 * constructor with coordinates
	 * 
	 * @param x x
	 * @param y y
	 */
	public Point(int x, int y) {
		this();
		
		setX(x);
		setY(y);
	}
	
	/**
	 * setter for x
	 * @param x x
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * setter for y
	 * @param y y
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * getter for x
	 * @return x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * getter for y
	 * @return y
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * setter with coordinates using point
	 * @param p point
	 */
	public void setPoint(Point p) {
		setPoint(p.getX(), p.getY());
		
	}
	
	/**
	 * setter with coordinates using ints
	 * @param x x
	 * @param y y
	 */
	public void setPoint(int x, int y) {
		setX(x);
		setY(y);
	}
}
