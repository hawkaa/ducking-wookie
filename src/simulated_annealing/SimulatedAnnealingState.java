package simulated_annealing;

import java.util.ArrayList;

public abstract class SimulatedAnnealingState {

	/**
	 * Returns neighbours of the current state
	 * @return ArrayList of neighbours
	 */
	public abstract ArrayList<SimulatedAnnealingState> getNeighbours();
	
	/**
	 * Evaluates the current state and returns a value between 0.0 and 1.0
	 * @return state evaluation between 0.0 and 1.0
	 */
	public abstract double objectiveFunction();
	
	/**
	 * Randomizes the current state
	 */
	protected abstract void randomize();
	
}
