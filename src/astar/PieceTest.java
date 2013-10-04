package astar;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PieceTest {

	Piece p1, p2, p3;
	
	@Before
	public void setUp() throws Exception {
		p1 = new Piece(0,2,2,2);
		p2 = new Piece(0,1,2,2);
		p3 = new Piece(0,2,2,2);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testPieceEquals() {
		assertEquals(false, p1.equals(p2));
		assertEquals(true, p1.equals(p1));
		assertEquals(true, p1.equals(p3));
		assertEquals(true, p3.equals(p1));
	}

	@Test
	public void testApplyMove() {
		assertEquals(p2, p1.applyMove(new PieceMovement(-1,0)));
	}

}
