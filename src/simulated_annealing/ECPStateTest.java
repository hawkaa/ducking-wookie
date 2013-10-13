package simulated_annealing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;


public class ECPStateTest {

	ECPState random1, random2, random3, solved1, solved2, otherDimension1, other1, other2, other3, empty1, empty2, empty3, small1;
	ECPPosition p1, p2, p3;
	
	
	@Before
	public void setUp() {
		random1 = ECPState.generateRandom(5, 5, 2);
		random2 = ECPState.generateRandom(5, 5, 2);
		random3 = ECPState.generateRandom(5, 5, 1);
		
		solved1 = new ECPState(5, 5, 2);
		solved1.put(0, 0, true);
		solved1.put(0, 2, true);
		solved1.put(1, 1, true);
		solved1.put(1, 4, true);
		solved1.put(2, 1, true);
		solved1.put(2, 3, true);
		solved1.put(3, 0, true);
		solved1.put(3, 4, true);
		solved1.put(4, 2, true);
		solved1.put(4, 3, true);
		
		solved2 = new ECPState(5, 5, 2);
		solved2.put(0, 0, true);
		solved2.put(0, 2, true);
		solved2.put(1, 1, true);
		solved2.put(1, 4, true);
		solved2.put(2, 1, true);
		solved2.put(2, 3, true);
		solved2.put(3, 0, true);
		solved2.put(3, 4, true);
		solved2.put(4, 2, true);
		solved2.put(4, 3, true);
		
		other1 = new ECPState(5, 5, 2);
		other1.put(0, 0, true);
		other1.put(0, 2, true);
		other1.put(1, 1, true);
		other1.put(1, 4, true);
		// Missing 2, 1
		other1.put(2, 3, true);
		other1.put(3, 0, true);
		other1.put(3, 4, true);
		other1.put(4, 2, true);
		other1.put(4, 3, true);
		
		other2 = new ECPState(5, 5, 2);
		other2.put(1, 0, true);
		other2.put(3, 0, true);
		other2.put(0, 3, true);
		
		other3 = new ECPState(5, 5, 2);
		other3.put(1, 0, true);
		other3.put(2, 0, true);
		other3.put(3, 0, true);
		other3.put(0, 3, true);
		
		otherDimension1 = new ECPState(5,3,2);

		empty1 = new ECPState(5,5, 2);
		empty2 = new ECPState(5,5, 1);
		empty3 = new ECPState(4,6, 2);
		
		small1 = new ECPState(2,2,1);
		small1.put(0, 0, true);
		small1.put(1, 1, true);
		

		p1 = new ECPPosition(1, 1);
		p2 = new ECPPosition(1, 2);
		p3 = new ECPPosition(1, 1);
		
	}
	
	@Test 
	public void testPositionEquals() {
		assertEquals(true, p1.equals(p1));
		assertEquals(true, p1.equals(p3));
		assertEquals(true, p3.equals(p1));
		assertEquals(false, p2.equals(p1));
		assertEquals(false, p1.equals(p2));
	}
	
	@Test
	public void testObjectiveFunction() {
		assertEquals(true, random1.objectiveFunction() >= 0.0 && random1.objectiveFunction() <= 1.0);
		assertEquals(true, random2.objectiveFunction() >= 0.0 && random2.objectiveFunction() <= 1.0);
		assertEquals(true, solved1.objectiveFunction() >= 0.0 && solved1.objectiveFunction() <= 1.0);
	}
	
	@Test
	public void testPrint() {
		assertEquals("*.*..\n.*..*\n.*.*.\n*...*\n..**.", solved1.printState());
	}
	
	@Test
	public void testCompleteSolution() {
		assertEquals(1.0, solved1.objectiveFunction1(), 0.001);
	}
	
	@Test
	public void testAlmostCompleteSolution() {
		assertEquals(0.9, other1.objectiveFunction1(), 0.001);
	}
	
	@Test
	public void testScore() {
		assertEquals(10, solved1.getPoints1());
		assertEquals(9, other1.getPoints1());
	}
	
	@Test
	public void testNumPieces() {
		assertEquals(10, solved1.getNumPieces());
		assertEquals(9, other1.getNumPieces());
	}
	
	@Test
	public void testGenerateNeighbours1() {
		ArrayList<SimulatedAnnealingState> neighbours = other1.getNeighbours1();
		assertEquals(6, neighbours.size());
		
		int iLess = 0;
		int iMore = 0;
		int now = other1.getNumPieces();
		
		for(SimulatedAnnealingState s: neighbours) {
			ECPState ecps = (ECPState)s;
			if(ecps.getNumPieces() == now + 1) {
				++iMore;
			} else if (ecps.getNumPieces() == now - 1) {
				++iLess;
			}
		}
		assertEquals(3, iLess);
		assertEquals(3, iMore);
		
	}
	
	@Test
	public void testGenerateNeighbours1WhenEmpty()  {
		ArrayList<SimulatedAnnealingState> neighbours = empty1.getNeighbours1();
		assertEquals(3, neighbours.size());
		
		int iLess = 0;
		int iMore = 0;
		int now = empty1.getNumPieces();
		
		for(SimulatedAnnealingState s: neighbours) {
			ECPState ecps = (ECPState)s;
			if(ecps.getNumPieces() == now + 1) {
				++iMore;
			} else if (ecps.getNumPieces() == now - 1) {
				++iLess;
			}
		}
		assertEquals(0, iLess);
		assertEquals(3, iMore);
	}
	
	@Test
	public void testGenerateNeighbours1WithLimits()  {
		ArrayList<SimulatedAnnealingState> neighbours = small1.getNeighbours1();
		assertEquals(4, neighbours.size());
		
		int iLess = 0;
		int iMore = 0;
		int now = small1.getNumPieces();
		
		for(SimulatedAnnealingState s: neighbours) {
			ECPState ecps = (ECPState)s;
			if(ecps.getNumPieces() == now + 1) {
				++iMore;
			} else if (ecps.getNumPieces() == now - 1) {
				++iLess;
			}
		}
		assertEquals(2, iLess);
		assertEquals(2, iMore);
	}
	
	@Test
	public void testIfNeighbours1IsUnique() {
		ArrayList<SimulatedAnnealingState> neighbours = other1.getNeighbours1();
		
		ArrayList<ECPState> n = new ArrayList<ECPState>();
		for(SimulatedAnnealingState s: neighbours) {
			n.add((ECPState)s);
		}
		assertEquals(true, ECPState.onlyUniqueElements(n));
	}
	
	@Test
	public void testClone(){
		assertEquals(solved1, solved1.clone());
		assertEquals(other1, other1.clone());
	}
	
	@Test
	public void testMaxScore1() {
		assertEquals(10, solved1.getMaxScore1());
		assertEquals(6, otherDimension1.getMaxScore1());
	}
	
	@Test
	public void testMaxScore2() {
		assertEquals(45, solved1.getMaxScore2());
		assertEquals(45, other1.getMaxScore2());
		assertEquals(35, random3.getMaxScore2());
	}
	
	@Test
	public void testGetPoints2() {
		assertEquals(45, solved1.getPoints2());
		assertEquals(43, other1.getPoints2());
		assertEquals(31, other2.getPoints2());
		assertEquals(24, other3.getPoints2());
		assertEquals(24, empty3.getPoints2());
	}
	
	@Test
	public void testObjectiveFunction2() {
		assertEquals(1.0, solved1.objectiveFunction2(), 0.001);
		assertEquals(0.95555, other1.objectiveFunction2(), 0.001);
	}
	
	@Test
	public void testConflicting() {
		assertEquals(0, otherDimension1.getConflicting(0, 0));
		assertEquals(0, otherDimension1.getConflicting(2, 1));
		assertEquals(2, solved1.getConflicting(4, 4));
		assertEquals(2, solved1.getConflicting(3, 2));
		assertEquals(2, solved1.getConflicting(0, 2));
		assertEquals(2, other1.getConflicting(0, 1));
		assertEquals(1, other1.getConflicting(2, 1));
		assertEquals(2, other2.getConflicting(2, 1));
		assertEquals(1, other2.getConflicting(3, 2));
	}
	
	@Test
	public void testConflictingDirection() {
		assertEquals(0, solved1.getConflicting(4, 0, -1, 1));
		assertEquals(2, solved1.getConflicting(4, 0, -1, 0));
		assertEquals(2, solved1.getConflicting(4, 0, 0, 1));
		assertEquals(1, solved1.getConflicting(3, 2, 1, 1));
	}
	
	
	@Test
	public void testGetEmptyPositions() {
		ArrayList<ECPPosition> pos = solved1.getEmptyPositions();
		assertEquals(true, pos.contains(new ECPPosition(0, 1)));
		assertEquals(true, pos.contains(new ECPPosition(0, 3)));
		assertEquals(true, pos.contains(new ECPPosition(0, 4)));
		assertEquals(true, pos.contains(new ECPPosition(1, 0)));
		assertEquals(true, pos.contains(new ECPPosition(1, 2)));
		assertEquals(true, pos.contains(new ECPPosition(1, 3)));
		assertEquals(true, pos.contains(new ECPPosition(2, 0)));
		assertEquals(true, pos.contains(new ECPPosition(2, 2)));
		assertEquals(true, pos.contains(new ECPPosition(2, 4)));
		assertEquals(true, pos.contains(new ECPPosition(3, 1)));
		assertEquals(true, pos.contains(new ECPPosition(3, 2)));
		assertEquals(true, pos.contains(new ECPPosition(3, 3)));
		assertEquals(true, pos.contains(new ECPPosition(4, 0)));
		assertEquals(true, pos.contains(new ECPPosition(4, 1)));
		assertEquals(true, pos.contains(new ECPPosition(4, 4)));
		

		pos = small1.getEmptyPositions();
		assertEquals(true, pos.contains(new ECPPosition(0, 1)));
		assertEquals(true, pos.contains(new ECPPosition(1, 0)));
	}
	
	@Test
	public void testGetEggPositions() {
		ArrayList<ECPPosition> pos = solved1.getEggPositions();
		assertEquals(true, pos.contains(new ECPPosition(0, 0)));
		assertEquals(true, pos.contains(new ECPPosition(0, 2)));
		assertEquals(true, pos.contains(new ECPPosition(1, 1)));
		assertEquals(true, pos.contains(new ECPPosition(1, 4)));
		assertEquals(true, pos.contains(new ECPPosition(2, 1)));
		assertEquals(true, pos.contains(new ECPPosition(2, 3)));
		assertEquals(true, pos.contains(new ECPPosition(3, 0)));
		assertEquals(true, pos.contains(new ECPPosition(3, 4)));
		assertEquals(true, pos.contains(new ECPPosition(4, 2)));
		assertEquals(true, pos.contains(new ECPPosition(4, 3)));
	}
	

	@Test
	public void testIsEqual() {
		assertEquals(true, solved1.equals(solved2));
		assertEquals(false, solved1.equals(other1));
		assertEquals(false, solved1.equals(otherDimension1));
		assertEquals(false, otherDimension1.equals(solved1));
		assertEquals(false, empty1.equals(empty2));
		assertEquals(false, empty1.equals(empty3));
		assertEquals(false, empty3.equals(empty1));
	}
	
	@Test
	public void testUniqueElementThingy() {
		ArrayList<ECPState> l = new ArrayList<ECPState>();
		l.add(this.solved1);
		l.add(this.other1);
		l.add(this.other2);
		assertEquals(true, ECPState.onlyUniqueElements(l));
		l.add(this.solved2);
		assertEquals(false, ECPState.onlyUniqueElements(l));
	}
	
	@Test
	public void testIsConflicting() {
		assertEquals(false, other2.isConflicting(1, 0));
		assertEquals(false, other2.isConflicting(3, 0));
		assertEquals(false, other3.isConflicting(0, 0));
		assertEquals(true, other3.isConflicting(1, 0));
		assertEquals(true, other3.isConflicting(2, 0));
		assertEquals(true, other3.isConflicting(3, 0));
	}
}
