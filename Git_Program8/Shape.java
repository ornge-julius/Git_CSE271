import java.awt.Color;
import java.util.ArrayList;

/**
 * Defines a Shape that has a bounding box to define its location. This is
 * intended to be extended by shapes with specific sets of vertices. On its own,
 * the Shape class doesn't have any concrete meaning. It's not until it's
 * extended that it starts to become really useful. Might as well make this an
 * abstract class, right?
 * 
 * @author Norm Krumpe
 * 
 */
public abstract class Shape {
	private Point bound;
	private int width;
	private int height;
	private Color color;

	/**
	 * Constructs a shape based on its bounding box and color, with the
	 * upper-left corner and width and height of the bounding box being
	 * specified, along with a color
	 * 
	 * @param x
	 *            x-coordinate of the bounding box' upper-left corner
	 * @param y
	 *            w-coordinate of the bounding box' upper-left corner
	 * @param width
	 *            width of the bounding box
	 * @param height
	 *            height of the bounding box
	 * @param color
	 *            color of the shape
	 */
	public Shape(int x, int y, int width, int height, Color color) {
		if (x < 0)
			throw new IllegalArgumentException("Bad x value: " + x);
		if (y < 0)
			throw new IllegalArgumentException("Bad y value: " + y);
		this.bound = new Point(x, y);
		this.width = width;
		this.height = height;
		setColor(color);
	}

	/**
	 * Creates a shape that is a copy of this shape, copying its color and all
	 * the details of its bounding box.
	 * 
	 * @param s
	 *            the shape that is to be copied
	 */
	public Shape(Shape s) {
		this((s.bound).getXCoor(), (s.bound).getYCoor(), s.width, s.height,
				s.color);
	}

	/**
	 * Returns the vertices of this shape. Note that the vertices can be
	 * directly computed from the location, size, and type of shape. That is, if
	 * two triangles have the same x, y, width, and height, then they have
	 * identical vertices.
	 * 
	 * @return the vertices of this shape
	 */

	// This method currently does nothing; seems it should
	// be made abstract, or also could be deleted as
	// the rest of the classes have their own
	// getVetices methods

	/*
	 * This method was later made abstract because it was an empty method that
	 * did nothing and it was overridden by all child classes
	 */
	public abstract Point[] getVertices();

	/**
	 * Sets the width of the bounding box
	 * 
	 * @param width
	 *            width of the bounding box
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Sets the height of the bounding box
	 * 
	 * @param height
	 *            height of the bounding box
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Gets the x-coordinate of the upper-left corner of the bounding box
	 * 
	 * @return the x-coordinate of the upper-left corner of the bounding box
	 */
	public int getX() {
		return bound.getXCoor();
	}

	/**
	 * Gets the y-coordinate of the upper-left corner of the bounding box
	 * 
	 * @return the y-coordinate of the upper-left corner of the bounding box
	 */
	public int getY() {
		return bound.getYCoor();
	}

	/**
	 * Sets the x-coordinate of the upper-left corner of the bounding box
	 * 
	 * @param x
	 *            the x-coordinate of the upper-left corner of the bounding box
	 */
	public void setX(int x) {
		this.bound.setXCoor(x);
	}

	/**
	 * Sets the y-coordinate of the upper-left corner of the bounding box
	 * 
	 * @param y
	 *            the y-coordinate of the upper-left corner of the bounding box
	 */
	public void setY(int y) {
		this.bound.setYCoor(y);
	}

	/**
	 * Gets the width of the bounding box
	 * 
	 * @return the width of the bounding box
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Gets the height of the bounding box
	 * 
	 * @return the height of the bounding box
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Gets the color of the shape
	 * 
	 * @return a string representation of the shape's color
	 */
	public Color getColor() {
		return color;
	}

	// This method should be inherited by the child classes
	// so that it won't have to be put into each child class

	/**
	 * returns the perimeter of this shape based on this shape's vertices
	 * 
	 * @return the perimeter of this shape
	 */
	public double getPerimeter() {
		Point vert[] = getVertices();
		double perim = 0;
		for (int i = 0; i < vert.length - 2; i++) {
			perim += Math.sqrt((Math.pow(
					(vert[i + 1].getXCoor() - vert[i].getXCoor()), 2) + Math
					.pow((vert[i + 1].getYCoor() - vert[i].getYCoor()), 2)));
		}
		return perim;
	}

	// This method should be inherited by the child classes
	// so that it won't have to be put into each child class

	/**
	 * returns the perimeter of the specified shape based on this shape's
	 * vertices
	 * 
	 * @return the area of this shape
	 */
	public double getArea() {
		Point vert[] = getVertices();
		double area = 0;
		for (int i = 0; i < vert.length - 2; i++) {
			area += ((vert[i].getXCoor() * vert[i + 1].getYCoor()) - (vert[i]
					.getYCoor() * vert[i + 1].getXCoor()));
		}

		area = Math
				.abs((area + ((vert[vert.length - 1].getXCoor()
						* vert[0].getYCoor() - (vert[vert.length - 1]
						.getYCoor() * vert[0].getXCoor()))) / 2));
		return area;
	}

	/**
	 * Sets the color of the shape to a valid color, or blue if the parameter is
	 * not one of the valid colors.
	 * 
	 * @param color2
	 *            A String representing the color of the shape
	 */
	public void setColor(Color color2) {

		this.color = color2;
	}

	/*
	 * A String representation of the object, with information about its
	 * bounding box and color.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[bound=" + bound + ", width=" + width + ", height=" + height
				+ ", color=" + color + "]";
	}

	/**
	 * Determines if the supplied shape has the same bounding box and color
	 * details as this shape.
	 * 
	 * @param that
	 *            the other shape to be compared
	 * @return true if this shape and that shape have the same bounding box and
	 *         color, and false otherwise
	 */
	public boolean equals(Object that) {
		if (that instanceof Shape) {
			Shape s = (Shape) that;
			return this.bound.equals(s.bound) && this.width == s.width
					&& this.height == s.height && this.color.equals(s.color);
		}
		return false;
	}

	/**
	 * Determines if this shape's vertices are all at least zero and less than
	 * or equal to the specified values
	 * 
	 * @param maxX
	 *            maximum acceptable x-coordinate
	 * @param maxY
	 *            maximum acceptable y-coordinate
	 * @return true if all vertices are in the range [0, maxX] and [0, maxY],
	 *         false otherwise
	 */

	// This method was deleted from all child classes because
	// it can be inherited instead
	public boolean isInsideBox(int maxX, int maxY) {

		Point[] v = getVertices();

		for (int i = 0; i < v.length; i++) {
			if (v[i].getXCoor() > maxX || v[i].getYCoor() > maxY)
				return false;
		}
		return true;
	}

}
