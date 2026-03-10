package edu.txst.midterm;

/**
 * Represents the game board, consisting of a grid of cells and a step counter.
 */
public class Board implements Cloneable {
	private final int COLUMNS = 10;
	private final int ROWS = 6;
	private Integer[][] grid;
	public StepCounter stepCounter;

	/**
	 * Constructs an empty board with the default dimensions.
	 */
	public Board() {
		this.grid = new Integer[ROWS][COLUMNS];
	}

	/**
	 * Initializes the step counter for this board.
	 *
	 * @param maxSteps the maximum number of steps allowed for the level
	 */
	public void setStepCounter(int maxSteps) {
		stepCounter = new StepCounter(maxSteps);
	}

	/**
	 * Sets the value of a specific cell in the grid.
	 *
	 * @param row   the row index
	 * @param col   the column index
	 * @param value the new integer value for the cell
	 */
	public void setCell(int row, int col, int value) {
		if (row >= 0 && row < ROWS && col >= 0 && col < COLUMNS) {
			grid[row][col] = value;
		}
	}

	/**
	 * Retrieves the integer value of a specific cell in the grid.
	 *
	 * @param row the row index
	 * @param col the column index
	 * @return the cell value, or -1 if the coordinates are out of bounds
	 */
	public int getCell(int row, int col) {
		if (row >= 0 && row < ROWS && col >= 0 && col < COLUMNS) {
			return grid[row][col];
		}
		return -1; // Return -1 or throw exception for out of bounds
	}

	/**
	 * Creates and returns a deep copy of this Board, including its step counter
	 * and all cell values.
	 *
	 * @return a cloned Board instance
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
