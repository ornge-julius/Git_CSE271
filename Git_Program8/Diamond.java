import java.awt.Color;

/**
 * Class that represents a diamond with a bounding box and color
 * 
 * @author Julius Ware
 * 
 */
public class Diamond extends Shape {

	public Diamond(int xPos, int yPos, int newWidth, int newHeight,
			Color newColor) {
		super(xPos, yPos, newWidth, newHeight, newColor);
	}

	/**
	 * Creates a diamond that is a copy of this rectangle, copying its color and
	 * all the details of its bounding box.
	 * 
	 * @param t
	 *            the rectangle that is to be copied
	 */
	public Diamond(Diamond t) {
		super(t);
	}

	/*
	 * Returns a String representation of this shape, by putting the shape type
	 * in front of the inherited bounding box and color information
	 * 
	 * @see Shape#toString()
	 */
	public String toString() {
		return "Diamond " + super.toString();
	}

	/*
	 * Returns true if the parameter is the same type of shape, and everything
	 * else matches from the parent class check
	 * 
	 * @see Shape#equals(java.lang.Object)
	 */
	public boolean equals(Object that) {
		if (that instanceof Diamond) {
			return super.equals(that);
		}
		return false;
	}

	/**
	 * Gets an array of points representing the vertices of this shape.
	 * 
	 * @return an array of points representing the vertices of this shape.
	 */

	public Point[] getVertices() {
		Point[] result = new Point[4];

		result[0] = new Point((int) (getX() + .5 * getWidth()), getY());
		result[1] = new Point(getX() + getWidth(),
				(int) (getY() + .5 * getHeight()));
		result[2] = new Point((int) (getX() + .5 * getWidth()), getY()
				+ getHeight());
		result[3] = new Point(getX(), (int) (getY() + .5 * getHeight()));

		return result;
	}
}
