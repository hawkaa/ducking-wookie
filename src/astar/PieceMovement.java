package astar;

public class PieceMovement {
	private int dx, dy;
	
	public PieceMovement(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public int getDx() {
		return dx;
	}

	public int getDy() {
		return dy;
	}
	
	public boolean equals(Object o) {
		if(o instanceof PieceMovement) {
			PieceMovement p = (PieceMovement)o;
			if(p.getDx() != this.getDx()) { return false;}
			if(p.getDy() != this.getDy()) { return false;}
			return true;
		} else {
			return super.equals(o);
		}
	}
	
}
