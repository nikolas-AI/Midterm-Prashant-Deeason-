package edu.txst.midterm;

/**
 * BoardLoader is an interface for loading maze game boards from files.
 */
public interface BoardLoader {
	/**
	 * Loads a board from the specified file path.
	 *
	 * @param filename the path to the board file
	 * @return the loaded Board
	 */
	Board load(String filename);
}
