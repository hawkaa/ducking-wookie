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
		double fpmax, q, plim, x;
		SimulatedAnnealingState pmax = null;
		double temp = 100.0;
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
			
			fpmax = fp;
			pmax = p;
			for(SimulatedAnnealingState neighbour: neighbours) {
				
				if(neighbour.objectiveFunction()>= fpmax) {
					fpmax = neighbour.objectiveFunction();
					pmax = neighbour;
				}
			}
			System.out.println("[" + temp + "] Climbs from " + fp + " to " + fpmax + ".");
			p = pmax;
			fp = fpmax;
			
			temp -= 0.001;
		}
		System.out.println(pmax.objectiveFunction());
		return pmax;
	}
	

}
