package edu.txst.midterm;

/**
 * Manages the step limit and tracking for a level.
 * It tracks the taken steps against the maximum budget.
 */
public class StepCounter {
	private int currentSteps;
	private int maxSteps;

	/**
	 * Retrieves the maximum steps allowed.
	 *
	 * @return the maximum steps allowed
	 */
	public int getMaxSteps() {
		return maxSteps;
	}

	/**
	 * Computes the remaining steps available to the player.
	 *
	 * @return the difference between maxSteps and used steps
	 */
	public int getRemainingSteps() {
		return maxSteps - currentSteps;
	}

	/**
	 * Registers a taken step by increasing the internal counter.
	 * The counter is capped at the maximum steps limit.
	 */
	public void increaseSteps() {
		currentSteps++;
		if (currentSteps > maxSteps)
			currentSteps = maxSteps;

	}

	/**
	 * Deducts taken steps, effectively giving the player more remaining steps.
	 * Used for bonuses (e.g., First Aid Kits).
	 *
	 * @param amount the number of steps to erase from the spent counter
	 */
	public void decreaseSteps(int amount) {
		currentSteps -= amount;
		if (currentSteps < 0)
			currentSteps = 0;
	}

	/**
	 * Purges the current step count, resetting it back to 0.
	 */
	public void resetSteps() {
		currentSteps = 0;
	}

	/**
	 * Constructs a StepCounter mapped to a specific limit constraint.
	 *
	 * @param maxSteps the absolute limit of steps for the level
	 */
	public StepCounter(int maxSteps) {
		if (maxSteps < 1) {
			maxSteps = 1;
		}
		this.maxSteps = maxSteps;
		currentSteps = 0;
	}
}
