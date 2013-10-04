package astar;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PieceMovementTest {

	PieceMovement p1, p2, p3;
	
	@Before
	public void setUp() throws Exception {
		p1 = new PieceMovement(-1, 0);
		p2 = new PieceMovement(0,1);
		p3 = new PieceMovement(-1, 0);
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

}
