import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

/**
 * A class that draws various shapes. You should NOT modify anything in this
 * file (but feel free to read through the code) . If you think there is a
 * problem with this code, please contact your instructor.
 * 
 * @author DJ Rao, Keith Frikken, Lukasz Opyrchal
 * 
 */
public class ShapeDisplayer extends JFrame {

	private JLabel mouseLocation;
	private JLabel perimArea;
	private double area, perim;
	private ArrayList<Shape> shapeList;

	public ShapeDisplayer() {
		super("Shape Display");

		shapeList = new ArrayList<Shape>();
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(400, 450));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);

		// Create a scroll pane to contain the components.
		JScrollPane jsp = new JScrollPane(new Board());
		add(jsp, BorderLayout.CENTER);

		// Create a JLabel to display mouse coordinates.
		mouseLocation = new JLabel("  Mouse Location: x=  " + ", y=");
		add(mouseLocation, BorderLayout.SOUTH);

		perimArea = new JLabel(" Perimeter: " + perim + " Area: " + area);
		add(perimArea, BorderLayout.NORTH);

		// Pack and show
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Adds a shape to the ShapeDisplayer. There is a better way to do this, but
	 * we need to cover polymorphism and abstract classes first. Shape must be a
	 * triangle, rectangle, or star.
	 * 
	 * @param newShape
	 *            The shape to add.
	 */
	public void addShape(Shape newShape) {

		if (newShape.isInsideBox(400, 400)) {
			shapeList.add(newShape);

			perim += newShape.getPerimeter();
			area += newShape.getArea();
			perimArea.setText(" Perimeter: " + perim + " Area: " + area);

			repaint();
		}
	}

	/**
	 * A board class that extends JComponent, represents the grid area
	 * 
	 * @author DJ Rao, Keith Frikken
	 * 
	 */
	private class Board extends JComponent {

		/**
		 * A default constructor.
		 */
		public Board() {
			addMouseMotionListener(new MouseMotionAdapter() {
				public void mouseMoved(MouseEvent me) {
					mouseLocation.setText("  Mouse Location: x=  " + me.getX()
							+ ", y=" + me.getY());
				}
			});
		}

		/**
		 * Sets the preferred size, but it is not explicitly called.
		 */
		public Dimension getPreferredSize() {
			Dimension size = new Dimension(200, 200);
			return size;
		}

		/**
		 * Paints all triangles in the triangle list.
		 */
		public void paintComponent(Graphics g) {
			paintBackground(g);
			for (int i = 0; (i < shapeList.size()); i++) {
				Point[] points = new Point[0];
				Shape current = shapeList.get(i);

				points = current.getVertices();

				Color shapeColor = current.getColor();
				Polygon vertices = new Polygon();
				for (Point p : points) {
					vertices.addPoint(p.getXCoor(), p.getYCoor());
				}
				g.setColor(new Color(shapeColor.getRed(),
						shapeColor.getGreen(), shapeColor.getBlue(), 200));
				g.fillPolygon(vertices);
				g.setColor(shapeColor);
				g.drawPolygon(vertices);
			}
		}
	}

	/**
	 * Creates the background. Makes the grid and sets the backgroun to black.
	 * 
	 * @param g
	 *            A graphic component.
	 */
	public void paintBackground(Graphics g) {
		final int Width = getWidth();
		final int Height = getHeight();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.WHITE);
		// Draw horizontal lines and print labels with small font.
		g.setFont(g.getFont().deriveFont(g.getFont().getSize() - 2.0f));
		for (int y = 0; (y < Height); y += 50) {
			g.drawLine(0, y, Width, y);
			g.drawString("" + y, 0, y - 2);
			g.drawString("" + y, Width - 25, y - 2);
		}
		// Draw vertical lines and print labels
		for (int x = 0; (x < Width); x += 50) {
			g.drawLine(x, 0, x, Height);
			g.drawString("" + x, x + 1, 10);
			g.drawString("" + x, x + 1, Height - 1);
		}
	}

	public static void main(String[] args) {
		ShapeDisplayer disp = new ShapeDisplayer();
		int shapeNum = 0;

		for (int i = 0; i < 350; i += 50) {
			for (int j = 0; j < 350; j += 50) {
				shapeNum = (int) (Math.random() * 6 + 1);
				switch (shapeNum) {

				case (1):
					disp.addShape(new Hexagon(i, j, 50, 50, new Color(
							generateColor(), generateColor(), generateColor())));
					break;
				case (2):
					disp.addShape(new Pentagon(i, j, 50, 50, new Color(
							generateColor(), generateColor(), generateColor())));
					break;
				case (3):
					disp.addShape(new Triangle(i, j, 50, 50, new Color(
							generateColor(), generateColor(), generateColor())));
					break;
				case (4):
					disp.addShape(new Rectangle(i, j, 50, 50, new Color(
							generateColor(), generateColor(), generateColor())));
					break;
				case (5):
					disp.addShape(new Star(i, j, 50, 50, new Color(
							generateColor(), generateColor(), generateColor())));
					break;

				case (6):
					disp.addShape(new Diamond(i, j, 50, 50, new Color(
							generateColor(), generateColor(), generateColor())));
				}
			}

		}

	}
 
	// this method generate a random number for the RGB values of
// the colors to be used in the main method
	
	public static int generateColor() {
		return (int) (Math.random() * 255 + 1);
	}

}
