package edu.txst.midterm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * CSVBoardLoader loads maze game boards from CSV files.
 * CSV format: First line contains max steps, followed by 6 lines with 10 comma-separated cell values.
 */
public class CSVBoardLoader implements BoardLoader {

	/**
	 * Loads a board from a CSV file.
	 * First line: max steps
	 * Next 6 lines: comma-separated cell type values (10 columns each)
	 *
	 * @param filename the path to the CSV file
	 * @return the loaded Board, or an empty board if loading fails
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
