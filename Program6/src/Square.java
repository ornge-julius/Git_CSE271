import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;

/**
 * 
 * @author Julius Ware
 * @version 2.0
 */
public class Square extends JPanel {
	// Sets the color of the grid itself, traditionally
	// yellow in the board game.
	public static final Color BACK_COLOR = new Color(200, 200, 0);

	// The size of the boundary surrounding the circle. A value of
	// 0 would mean the circle touches the square on all 4 sides
	public static final int BOUNDARY = 5;

	// The color of the circle in an unoccupied square
	public static final Color EMPTY_COLOR = Color.gray;

	// The colors for each player
	private Color player1, player2;

	// The current occupant. 0 means empty, and 1 and 2 represent
	// players 1 and 2 respectively
	private int occupant;

	/**
	 * defines a Square object with two specified colors for each player also
	 * sets occupant value to 0
	 * 
	 * @param player1
	 *            player one color
	 * @param player2
	 *            player two color
	 */
	public Square(Color player1, Color player2) {
		super();

		if (player1.equals(player2) || player1.equals(BACK_COLOR)
				|| player1.equals(EMPTY_COLOR) || player2.equals(BACK_COLOR)
				|| player2.equals(EMPTY_COLOR)) {
			throw new IllegalStateException(
					"Invalid player colors.  player1, player2, BACK_COLOR, "
							+ "and EMPTY_COLOR must all be different.");
		}

		this.setBackground(Color.YELLOW);
		this.player1 = player1;
		this.player2 = player2;
		occupant = 0;
	}

	/**
	 * sets the occupant value to the specified player or makes the square blank
	 * again throws an exception if the values are not 0, 1, or 2
	 * 
	 * @param num
	 *            player number
	 */
	public void setOccupant(int occupant) {
		if (occupant < 0 || occupant > 2)
			throw new IllegalArgumentException("Must be 0, 1, or 2");
		this.occupant = occupant;
		this.setToolTipText("Player " + occupant);
		repaint();
	}

	/**
	 * returns the value of occupant
	 * 
	 * @return value of occupant
	 */
	public int getOccupant() {
		return this.occupant;
	}

	/**
	 * Creates the graphics for the Square component set the background of the
	 * square to yellow and draws a circle with the color depending on the value
	 * of the occupant
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		switch (occupant) {
		case 0:
			g.setColor(EMPTY_COLOR);
			break;
		case 1:
			g.setColor(player1);
			break;
		case 2:
			g.setColor(player2);
		}

		g.fillOval(BOUNDARY, BOUNDARY, this.getWidth() - 2 * BOUNDARY,
				this.getHeight() - 2 * BOUNDARY);
		g.setColor(Color.black);
		g.drawRect(0, 0, this.getWidth(), this.getHeight());

	}

}
