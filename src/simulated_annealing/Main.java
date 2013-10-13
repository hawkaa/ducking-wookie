package simulated_annealing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SimulatedAnnealingState sas = simulatedAnnealing(new ECPState(10, 10, 3));
		System.out.println(((ECPState)sas).printState());
	}
	
	
	public static SimulatedAnnealingState simulatedAnnealing(SimulatedAnnealingState p) {
		double fpmax, q, plim, x, randomNeighbourScore,delta, prob;
		SimulatedAnnealingState pmax = null;
		SimulatedAnnealingState randomNeighbour = null;
		double temp = 30.0;
		double target = 1.0;
		ArrayList<SimulatedAnnealingState> neighbours;
		
		
		p.randomize();
		double fp = p.objectiveFunction();
		
		
		while(temp > 0) {
			if(fp >= target) {
				return p;
			}
			neighbours = p.getNeighbours();
			Collections.shuffle(neighbours);
			
			fpmax = 0.0;
			pmax = null;
			for(SimulatedAnnealingState neighbour: neighbours) {
				
				if(neighbour.objectiveFunction()>= fpmax) {
					fpmax = neighbour.objectiveFunction();
					pmax = neighbour;
				}
			}
			if(fpmax > fp) {
				System.out.println("Found a better match! Will keep on iterating (" + fp +" to " + fpmax + ")");
				p = pmax;
				fp = fpmax;
			} else if (fpmax == fp) {
				p = pmax;
				fp = fpmax;
			} else {
				randomNeighbour = neighbours.get(0);
				randomNeighbourScore = randomNeighbour.objectiveFunction();
				delta = (fp-randomNeighbourScore);
				

				prob = Math.pow(Math.E, -delta/temp);
				if(prob > Math.random()) {
					System.out.println("Using new neighbour (" + fp + " to " + randomNeighbourScore + "), won with probability " + prob + ";");
					p = randomNeighbour;
					fp = randomNeighbourScore;
				}
			}
			
			temp -= 0.0002;
		}
		System.out.println(p.objectiveFunction());
		return p;
	}
	

}
