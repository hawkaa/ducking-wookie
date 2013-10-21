package simulated_annealing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<SimulatedAnnealingState> sas = new ArrayList<SimulatedAnnealingState>();
		sas.add(new ECPState(5, 5, 2));
		sas.add(new ECPState(6, 6, 2));
		sas.add(new ECPState(8, 8, 1));
		sas.add(new ECPState(10, 10, 3));
		
		SimulatedAnnealingState solution;
		
		for(SimulatedAnnealingState sa : sas) {
			solution = simulatedAnnealing(sa);
			System.out.println("FOUND SOULTION WITH " + solution.objectiveFunction() + " SCORE:");
			System.out.println(((ECPState)solution).printState());
		}
		
	}
	
	/**
	 * This is the general simulated annealing algorithm
	 * @param Start state
	 * @return The best solution it could find
	 */
	public static SimulatedAnnealingState simulatedAnnealing(SimulatedAnnealingState currentState) {
		
		// Temperature
		double temp = 30.0;
		
		// Target value
		double target = 1.0;
		
		
		// Variables for saving max score and values
		double sasMaxScore;
		SimulatedAnnealingState sasMax = null;
		
		// Variable for saving neighbours
		SimulatedAnnealingState randomNeighbour = null;
		ArrayList<SimulatedAnnealingState> neighbours;
		
		// Various doubles
		double q, plim, x, randomNeighbourScore,delta, prob, currentScore;
		
		
		// Randomizing the board
		currentState.randomize();
		//Saving currentScore
		currentScore = currentState.objectiveFunction();
		
		
		while(temp > 0) {
			if(currentScore >= target) {
				return currentState; // if current state has score within target, return it.
			}
			
			// Generate neighbours and shuffle them
			neighbours = currentState.getNeighbours();
			Collections.shuffle(neighbours);
			
			// Reset values before evaluation
			sasMaxScore = 0.0;
			sasMax = null;
			
			// Loop through neighbours and find the one with the best score
			for(SimulatedAnnealingState neighbour: neighbours) {
				
				if(neighbour.objectiveFunction()>= sasMaxScore) {
					sasMaxScore = neighbour.objectiveFunction();
					sasMax = neighbour;
				}
			}
			if(sasMaxScore >= currentScore) {
				// If there is a better neighbour, jump to it
				currentState = sasMax;
				currentScore = sasMaxScore;
			} else {
				// If there is no better neighbour, pick a random one (the array is already shuffled)
				randomNeighbour = neighbours.get(0);
				randomNeighbourScore = randomNeighbour.objectiveFunction();
				delta = (currentScore-randomNeighbourScore);
				
				// Calculate the probability that the algorithm will pick this neighbour
				prob = Math.pow(Math.E, -delta/temp);
				if(prob > Math.random()) {
					currentState = randomNeighbour;
					currentScore = randomNeighbourScore;
				}
			}
			// Decreases temperature
			temp -= 0.0002;
		}
		return currentState;
	}
	

}
