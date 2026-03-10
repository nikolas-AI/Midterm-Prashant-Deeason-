package edu.txst.midterm;

/**
 * Board represents the maze game board with a 6x10 grid.
 * It manages the cell states and the step counter for tracking player movement.
 * Implements Cloneable to allow board state saving for game resets.
 */
public class Board implements Cloneable {
	private final int COLUMNS = 10;
	private final int ROWS = 6;
	private Integer[][] grid;
	public StepCounter stepCounter;

	/**
	 * Initializes an empty Board with a 6x10 grid.
	 */
	public Board() {
		this.grid = new Integer[ROWS][COLUMNS];
	}

	/**
	 * Sets the step counter for this board with the specified maximum steps.
	 *
	 * @param maxSteps the maximum steps for this level
	 */
	public void setStepCounter(int maxSteps) {
		stepCounter = new StepCounter(maxSteps);
	}

	/**
	 * Sets the value of a cell at the specified row and column.
	 * Invalid coordinates are ignored.
	 *
	 * @param row the row index
	 * @param col the column index
	 * @param value the cell type value
	 */
	public void setCell(int row, int col, int value) {
		if (row >= 0 && row < ROWS && col >= 0 && col < COLUMNS) {
			grid[row][col] = value;
		}
	}

	/**
	 * Retrieves the value of a cell at the specified row and column.
	 * Returns -1 if the coordinates are out of bounds.
	 *
	 * @param row the row index
	 * @param col the column index
	 * @return the cell type value, or -1 if out of bounds
	 */
	public int getCell(int row, int col) {
		if (row >= 0 && row < ROWS && col >= 0 && col < COLUMNS) {
			return grid[row][col];
		}
		return -1; // Return -1 or throw exception for out of bounds
	}

	/**
	 * Creates a deep copy of this board including the grid and step counter.
	 * Used for implementing game reset functionality.
	 *
	 * @return a cloned Board with the same state
	 */
	@Override
	public Board clone() {
		Board copy = new Board();
		copy.setStepCounter(stepCounter.getMaxSteps());
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLUMNS; c++) {
				copy.setCell(r, c, this.grid[r][c]);
			}
		}
		return copy;
	}
}
