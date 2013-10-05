package astar;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RushHourStateTest {

	RushHourState rhs1;
	RushHourState rhs2;
	RushHourState rhs3;
	RushHourState rhs4;
	RushHourState rhs5;
	RushHourState rhsFinish;
	
	RHSearchNode s1, s1_1, s1_2, s2, s3, sFinish;
	
	RushHourState[] rhs1_mutations = new RushHourState[3];
	

	@Before
	public void setUp() throws Exception {
		this.rhs1 = new RushHourState();
		rhs1.addPiece(new Piece(0,2,2,2));
		rhs1.addPiece(new Piece(0,0,4,3));
		rhs1.addPiece(new Piece(0,3,4,2));
		rhs1.addPiece(new Piece(0,4,1,2));
		rhs1.addPiece(new Piece(1,2,0,2));
		rhs1.addPiece(new Piece(1,4,2,2));
		
		this.rhs2 = new RushHourState();
		rhs2.addPiece(new Piece(0,0,2,2)); // all the way to the right
		rhs2.addPiece(new Piece(0,0,4,3));
		rhs2.addPiece(new Piece(0,3,4,2));
		rhs2.addPiece(new Piece(0,4,1,2));
		rhs2.addPiece(new Piece(1,2,0,2));
		rhs2.addPiece(new Piece(1,4,2,2));
		
		this.rhs3 = new RushHourState();
		rhs3.addPiece(new Piece(0,2,2,2));
		rhs3.addPiece(new Piece(0,0,4,3));
		rhs3.addPiece(new Piece(0,3,4,2));
		rhs3.addPiece(new Piece(0,4,1,2));
		rhs3.addPiece(new Piece(1,2,0,2));
		// 5 gone
		
		this.rhs4 = new RushHourState();
		rhs4.addPiece(new Piece(0,0,2,3));
		rhs4.addPiece(new Piece(0,0,4,3));
		rhs4.addPiece(new Piece(0,3,4,2));
		rhs4.addPiece(new Piece(0,4,1,2));
		rhs4.addPiece(new Piece(1,2,0,2));
		rhs4.addPiece(new Piece(1,4,2,2));
		
		this.rhs5 = new RushHourState();
		rhs5.addPiece(new Piece(0,2,2,2));
		rhs5.addPiece(new Piece(0,0,4,3));
		rhs5.addPiece(new Piece(0,3,4,2));
		rhs5.addPiece(new Piece(0,4,1,2));
		rhs5.addPiece(new Piece(1,2,0,2));
		rhs5.addPiece(new Piece(1,4,2,2));
		
		this.rhsFinish = new RushHourState();
		rhsFinish.addPiece(new Piece(0,4,2,2));
		rhsFinish.addPiece(new Piece(0,0,4,3));
		rhsFinish.addPiece(new Piece(0,3,4,2));
		rhsFinish.addPiece(new Piece(0,4,1,2));
		rhsFinish.addPiece(new Piece(1,2,0,2));
		
		rhs1_mutations[0] = new RushHourState();
		rhs1_mutations[0].addPiece(new Piece(0,1,2,2));
		rhs1_mutations[0].addPiece(new Piece(0,0,4,3));
		rhs1_mutations[0].addPiece(new Piece(0,3,4,2));
		rhs1_mutations[0].addPiece(new Piece(0,4,1,2));
		rhs1_mutations[0].addPiece(new Piece(1,2,0,2));
		rhs1_mutations[0].addPiece(new Piece(1,4,2,2));
		
		rhs1_mutations[1] = new RushHourState();
		rhs1_mutations[1].addPiece(new Piece(0,2,2,2));
		rhs1_mutations[1].addPiece(new Piece(0,0,4,3));
		rhs1_mutations[1].addPiece(new Piece(0,4,4,2));
		rhs1_mutations[1].addPiece(new Piece(0,4,1,2));
		rhs1_mutations[1].addPiece(new Piece(1,2,0,2));
		rhs1_mutations[1].addPiece(new Piece(1,4,2,2));
		
		rhs1_mutations[2] = new RushHourState();
		rhs1_mutations[2].addPiece(new Piece(0,2,2,2));
		rhs1_mutations[2].addPiece(new Piece(0,0,4,3));
		rhs1_mutations[2].addPiece(new Piece(0,3,4,2));
		rhs1_mutations[2].addPiece(new Piece(0,3,1,2));
		rhs1_mutations[2].addPiece(new Piece(1,2,0,2));
		rhs1_mutations[2].addPiece(new Piece(1,4,2,2));

		s1 = new RHSearchNode(rhs1, 0);
		s1_1 = new RHSearchNode(rhs1, 5);
		s1_2 = new RHSearchNode(rhs1, 1);
		s2 = new RHSearchNode(rhs2, 1);
		s3 = new RHSearchNode(rhs3, 1);
		sFinish = new RHSearchNode(rhsFinish, 1);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetPieceAt() {
		assertEquals(-1, rhs1.getPieceAt(0,0));
		assertEquals(2, rhs1.getPieceAt(4,4));
		assertEquals(4, rhs1.getPieceAt(2,0));
		assertEquals(-2, rhs1.getPieceAt(-1,0));
		assertEquals(-2, rhs1.getPieceAt(-1,-1));
		assertEquals(-2, rhs1.getPieceAt(6,0));
		assertEquals(-2, rhs1.getPieceAt(10,0));
	}
	
	@Test
	public void testIsAt() {
		assertEquals(true, rhs1.isAt(0,2,2));
		assertEquals(false, rhs1.isAt(0,0,0));
		assertEquals(true, rhs1.isAt(1,2,4));
	}
	@Test
	public void testIsGoal() {
		assertEquals(false, rhs1.isGoal());
		assertEquals(false, rhs2.isGoal());
		assertEquals(false, rhs3.isGoal());
		assertEquals(true, rhsFinish.isGoal());
	}
	
	@Test
	public void testGetHeuristicValue() {
		assertEquals(3, rhs1.getHeuristicValue());
		assertEquals(5, rhs2.getHeuristicValue());
		assertEquals(2, rhs3.getHeuristicValue());
		assertEquals(4, rhs4.getHeuristicValue());
	}
	
	@Test
	public void testEquals() {
		assertEquals(true, rhs1.equals(rhs1));
		assertEquals(false, rhs1.equals(rhs2));
		assertEquals(false, rhs2.equals(rhs1));
		assertEquals(true, rhs1.equals(rhs5));
		assertEquals(true, rhs5.equals(rhs1));
	}
	
	@Test
	public void testToString() {
		String s = rhs1.toString();
		System.out.println(s);
		assertEquals("..4...\n..4.33\n..005.\n....5.\n11122.\n......", s);
	}
	
	@Test
	public void  testMutations() {
		ArrayList<RushHourState> l = rhs1.getMutations();
		assertEquals(true, l.contains(rhs1_mutations[0]));
		assertEquals(true, l.contains(rhs1_mutations[1]));
		assertEquals(true, l.contains(rhs1_mutations[2]));
		assertEquals(3, l.size());
	}
	
	@Test
	public void testGetPieceMovements() {
		
		ArrayList<PieceMovement> movements;
		
		movements = rhs1.getPieceMovements(0);
		assertEquals(true, movements.contains(new PieceMovement(-1, 0)));
		
		assertEquals(0, rhs1.getPieceMovements(1).size());
		
		movements = rhs1.getPieceMovements(2);
		assertEquals(true, movements.contains(new PieceMovement(1, 0)));
		
		movements = rhs1.getPieceMovements(3);
		assertEquals(true, movements.contains(new PieceMovement(-1, 0)));
		
		assertEquals(0, rhs1.getPieceMovements(4).size());
		
		assertEquals(0, rhs1.getPieceMovements(5).size());
	}
	
	@Test 
	public void testCopy() {
		assertEquals(rhs5, rhs1.copy());
	}
	
	@Test
	public void testApplyMoves() {
		assertEquals(rhs1, rhs1.applyMove(4, new PieceMovement(0,-1)));
		assertEquals(rhs1_mutations[0], rhs1.applyMove(0, new PieceMovement(-1,0)));
		assertEquals(rhs1_mutations[1], rhs1.applyMove(2, new PieceMovement(1,0)));
		assertEquals(rhs1_mutations[2], rhs1.applyMove(3, new PieceMovement(-1,0)));
	}
	
	@Test
	public void testSerchNodeG() {
		assertEquals(0, s1.getG());
		assertEquals(1, s1_2.getG());
	}
	
	@Test
	public void testSearchNodeH() {
		assertEquals(3, s1.getH());
		assertEquals(3, s1_2.getH());
	}
	
	@Test
	public void testSearchNodeF() {
		assertEquals(3, s1.getF());
		assertEquals(4, s1_2.getF());
	}
	
	@Test
	public void testSearchNodeExpand() throws Exception {
		ArrayList<RHSearchNode> l;
		l = s1.getChildren();
		assertEquals(0, l.size());
		s1.expand();
		assertEquals(3, l.size());
	}
	
	@Test(expected = Exception.class)
	public void testSearchNodeDoubleExpansion() throws Exception {
		s1.expand();
		s1.expand();
	}
	
	@Test
	public void testSearchNodeComparable() {
		ArrayList<RHSearchNode> l = new ArrayList<RHSearchNode>();
		l.add(s1_1);
		l.add(s1_2);
		l.add(s2);
		l.add(s3);
		Collections.sort(l);
		assertEquals(s3, l.get(0));
		assertEquals(s1_2, l.get(1));
		assertEquals(s2, l.get(2));
		assertEquals(s1_1, l.get(3));
		
		
	}
	
	@Test
	public void testSetParent() {
		assertNull(s1.getParent());
		s1.setParent(s2);
		assertEquals(s2, s1.getParent());
	}
	
	@Test
	public void testPriorityQueue() throws Exception {
		PriorityQueue<RHSearchNode> pq = new PriorityQueue<RHSearchNode>();
		pq.add(s1);
		RHSearchNode first = pq.poll();
		assertEquals(s1, first);
		first.expand();
		for(RHSearchNode s: first.getChildren()) {
			pq.add(s);
		}
		RHSearchNode second = pq.poll();
		assertEquals(true, second.getState().equals(rhs1_mutations[1]) || second.getState().equals(rhs1_mutations[2]));
		RHSearchNode third = pq.poll();
		assertEquals(true, third.getState().equals(rhs1_mutations[1]) || third.getState().equals(rhs1_mutations[2]));
		RHSearchNode fourth = pq.poll();
		assertEquals(rhs1_mutations[0], fourth.getState());
	}
	
	@Test
	public void testSearchNodeIsGoal() {
		assertEquals(false, s1.isGoal());
		assertEquals(true, sFinish.isGoal());
	}
	
	

}
