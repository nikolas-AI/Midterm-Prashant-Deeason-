package edu.txst.midterm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Implements the BoardLoader interface to construct a Board by
 * parsing a Comma-Separated Values (CSV) file.
 */
public class CSVBoardLoader implements BoardLoader {

	/**
	 * Loads the board data from a CSV file. The first row dictates the
	 * maximum step count, while subsequent rows represent the grid layout.
	 *
	 * @param filename the relative or absolute path of the CSV file
	 * @return a constructed Board object populated with the CSV data
	 */
	@Override
	public Board load(String filename) {
		Board board = new Board();
		String line;
		int row = 0;

		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			while ((line = br.readLine()) != null && row < 7) {
				if (row == 0) {
					int maxSteps = Integer.parseInt(line);
					board.setStepCounter(maxSteps);
				} else {
					String[] values = line.split(",");
					for (int col = 0; col < values.length && col < 10; col++) {
						int cellType = Integer.parseInt(values[col].trim());
						board.setCell(row - 1, col, cellType);
					}
				}
				row++;
			}
		} catch (IOException | NumberFormatException e) {
			System.err.println("Error loading level: " + e.getMessage());
		}

		return board;
	}
}
