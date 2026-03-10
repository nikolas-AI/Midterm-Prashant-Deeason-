package edu.txst.midterm;

/**
 * StepCounter tracks the player's movement steps in the maze game.
 * It manages the maximum steps available and the current steps used.
 */
public class StepCounter {
	private int currentSteps;
	private int maxSteps;

	/**
	 * Gets the maximum steps allowed in the current level.
	 *
	 * @return the maximum steps
	 */
	public int getMaxSteps() {
		return maxSteps;
	}

	/**
	 * Gets the remaining steps available to the player.
	 *
	 * @return the remaining steps (maxSteps - currentSteps)
	 */
	public int getRemainingSteps() {
		return maxSteps - currentSteps;
	}

	/**
	 * Increases the number of used steps by one.
	 * Used steps cannot exceed the maximum available steps.
	 */
	public void increaseSteps() {
		currentSteps++;
		if (currentSteps > maxSteps)
			currentSteps = maxSteps;

	}

	/**
	 * Decreases the number of used steps by the specified amount.
	 * Used steps cannot go below zero.
	 *
	 * @param amount the number of steps to decrease
	 */
	public void decreaseSteps(int amount) {
		currentSteps -= amount;
		if (currentSteps < 0)
			currentSteps = 0;
	}

	/**
	 * Resets the used steps count to zero.
	 */
	public void resetSteps() {
		currentSteps = 0;
	}

	/**
	 * Initializes the StepCounter with a maximum number of steps.
	 * If maxSteps is less than 1, it defaults to 1.
	 *
	 * @param maxSteps the maximum steps allowed
	 */
	public StepCounter(int maxSteps) {
		if (maxSteps < 1) {
			maxSteps = 1;
		}
		this.maxSteps = maxSteps;
		currentSteps = 0;
	}
}
