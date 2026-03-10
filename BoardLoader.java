package edu.txst.midterm;

/**
 * Interface defining the contract for classes capable of loading a Board
 * from a specified file.
 */
public interface BoardLoader {
	/**
	 * Loads a board from a given file path.
	 *
	 * @param filename the path to the file containing level data
	 * @return a newly populated Board object
	 */
	Board load(String filename);
}
