import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * 
 * @author Julius Ware
 * @version 2.0
 */

public class Connect4 extends JFrame {
	private int Player;
	private int rows;
	private int columns;
	private JPanel squarePanel;
	private JPanel buttonPanel;
	private JLabel status;
	private Square[][] squares;
	private JButton[] columnButtons;
	public static final Color PLAYER1 = Color.black;
	public static final Color PLAYER2 = Color.red;

	/**
	 * Defines a Connect4 board with specified number of rows and columns throws
	 * Exception if rows or columns is less than 4
	 * 
	 * @param rows
	 *            number of rows
	 * @param columns
	 *            number of columns
	 */
	public Connect4(int rows, int columns) {
		if (rows < 4 || columns < 4)
			throw new IllegalArgumentException("Bad Data: input < 4");
		this.rows = rows;
		this.columns = columns;
		this.Player = 1;

		this.frameSetup();

	}

	/**
	 * Creates the JFrame for the Connect4 board sets the size, title, and
	 * Implements methods to create a board with a label, buttons, and default
	 * Close operation
	 */
	private void frameSetup() {
		this.setResizable(false);
		this.setTitle("Let's Play Connect 4!");
		this.squarePanelSetup();
		this.buttonPanelSetup();
		this.statusSetup();
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(0, 0, 500, 500);
		this.setLocationRelativeTo(null);

	}

	/**
	 * Defines a JPanel and fills it with a 2D array of Square objects producing
	 * A the squares for the board
	 */
	private void squarePanelSetup() {
		squarePanel = new JPanel();
		squarePanel.setLayout(new GridLayout(this.rows, this.columns));
		squares = new Square[this.rows][this.columns];

		for (int row = 0; row < squares.length; row++) {
			for (int cell = 0; cell < squares[0].length; cell++) {
				squares[row][cell] = new Square(PLAYER1, PLAYER2);
				squarePanel.add(squares[row][cell]);

			}

		}

		this.add(squarePanel, BorderLayout.CENTER);
	}

	/**
	 * Defines a JPanel and fills it with an array of JButton objects depending
	 * On the number of columns in the board
	 */
	private void buttonPanelSetup() {
		ButtonListener listens = new ButtonListener();
		buttonPanel = new JPanel(new GridLayout());
		columnButtons = new JButton[columns];
		for (int i = 0; i < columns; i++) {
			columnButtons[i] = new JButton("" + i);
			buttonPanel.add(columnButtons[i]);
			columnButtons[i].addActionListener(listens);
		}

		this.add(buttonPanel, BorderLayout.NORTH);
	}

	/**
	 * Defines a JLabel indicating who's turn it is
	 */
	private void statusSetup() {

		status = new JLabel("Player " + this.Player + "'s turn!", JLabel.CENTER);

		this.add(status, BorderLayout.SOUTH);

	}

	/**
	 * places a black or red piece in the specified square
	 * 
	 * @param row
	 *            row of the square
	 * @param col
	 *            column of the square
	 * @param player
	 *            current player (Player1 or Player two)
	 */
	private void updateSquare(int row, int cell, int player) {
		squares[row][cell].setOccupant(this.Player);
		squares[row][cell].repaint();
		updatePlayer();
	}

	/**
	 * Updates the current player and sets the status accordingly
	 */
	private void updatePlayer() {
		switch (this.Player) {

		case (1):
			this.Player = 2;
			break;
		case (2):
			this.Player = 1;
			break;
		}
		status.setText("Player " + this.Player + "'s turn!");
	}

	/**
	 * Scans the current column, if column is full disables the respective
	 * column button
	 * 
	 * @param col
	 *            current column and/or last column a piece was placed
	 */
	private void scanCol(int col) {
		int count = 0;
		for (int i = 0; i < this.rows; i++) {
			if (squares[i][col].getOccupant() != 0) {
				count++;
			}
			if (count == this.rows) {
				columnButtons[col].setEnabled(false);
			}
		}

	}

	/**
	 * Checks if the game is over using coordinates of the last piece played
	 * also calls upon other methods to check if the game has ended in a win or
	 * a draw
	 * 
	 * @param col
	 *            column of the last piece played
	 * @param row
	 *            row of the last piece played
	 */
	private void gameOver(int col, int row) {
		int countMatches = 0;
		int counter = 0;

		/*
		 * Checks for a connect four in the current column, If connect four is
		 * found ends game
		 */

		for (counter = 0; counter < this.rows - 1; counter++) {
			if (squares[counter][col].getOccupant() == squares[counter + 1][col]
					.getOccupant() && squares[counter][col].getOccupant() != 0) {
				countMatches++;

			} else {

				countMatches = 0;

			}

		}

		if (countMatches == 3 || checkDiag() == true) {
			updatePlayer();
			status.setText("Player " + this.Player + " wins!");
			JOptionPane.showMessageDialog(this, "Player " + this.Player
					+ " wins!", "Game Over!", JOptionPane.PLAIN_MESSAGE);
			for (int i = 0; i < columnButtons.length; i++) {
				columnButtons[i].setEnabled(false);

			}
			return;
		}

		// Checks if the game will end in a draw

		if (checkDraw() == true) {
			status.setText("It's a draw!");
			JOptionPane.showMessageDialog(this, "It's a draw!", "Game Over!",
					JOptionPane.PLAIN_MESSAGE);
			return;
		}

		/*
		 * Resets number of matches found and the counter, checks for a connect
		 * four in the current row, If connect four is found ends game
		 */
		countMatches = 0;
		counter = 0;
		for (counter = 0; counter < this.columns - 1 && countMatches < 3; counter++) {
			if (squares[row][counter].getOccupant() == squares[row][counter + 1]
					.getOccupant() && squares[row][counter].getOccupant() != 0) {
				countMatches++;
			} else {
				countMatches = 0;
			}
		}

		if (countMatches == 3) {
			updatePlayer();
			status.setText("Player " + this.Player + " wins!");
			JOptionPane.showMessageDialog(this, "Player " + this.Player
					+ " wins!", "Game Over!", JOptionPane.PLAIN_MESSAGE);
			for (int i = 0; i < columnButtons.length; i++) {
				columnButtons[i].setEnabled(false);

			}
		}

	}

	/**
	 * Returns a boolean value indicating if a connect four is found in a
	 * diagonal
	 * 
	 * @return boolean value indicating if a connect four is found
	 */
	private boolean checkDiag() {

		boolean gameover = false;
		// Check diagonal lower left to upper right
		for (int row = 0; row < this.rows - 3; row++)
			for (int col = 0; col < this.columns - 3; col++)
				if (squares[row][col].getOccupant() > 0
						&& squares[row][col].getOccupant() == squares[row + 1][col + 1]
								.getOccupant()
						&& squares[row][col].getOccupant() == squares[row + 2][col + 2]
								.getOccupant()
						&& squares[row][col].getOccupant() == squares[row + 3][col + 3]
								.getOccupant()) {
					gameover = true;
					return gameover;
				}
		// Check diagonal upper left to lower right
		for (int row = this.rows - 1; row >= 3; row--)
			for (int col = 0; col < this.columns - 3; col++)
				if (squares[row][col].getOccupant() > 0
						&& squares[row][col].getOccupant() == squares[row - 1][col + 1]
								.getOccupant()
						&& squares[row][col].getOccupant() == squares[row - 2][col + 2]
								.getOccupant()
						&& squares[row][col].getOccupant() == squares[row - 3][col + 3]
								.getOccupant()) {
					gameover = true;
					return gameover;
				}

		return gameover;

	}

	/**
	 * returns a boolean value indicating if the game has ended in a draw
	 * 
	 * @return boolean value indicating if the game ends in a draw
	 */
	private boolean checkDraw() {
		boolean draw = false;
		int counter = 0;

		for (int i = 0; i < this.rows - 1; i++) {
			for (int j = 0; j < this.columns - 1; j++) {
				if (squares[i][j].getOccupant() != 0)
					counter++;
			}
		}

		if (counter == (this.rows * this.columns))
			draw = true;

		return draw;

	}

	public class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String command = arg0.getActionCommand();

			for (int j = 0; j < columnButtons.length; j++) {

				if (command.equals("" + j)) {
					for (int i = rows - 1; i >= 0; i--) {
						if (squares[i][j].getOccupant() == 0) {
							updateSquare(i, j, Player);
							scanCol(j);
							gameOver(j, i);

							return;
						}
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		Connect4 board = new Connect4(6, 7);

	}

}
