package astar;

public class Piece {
	final int orientation, x, y, size;
	public int getOrientation() {
		return orientation;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getSize() {
		return size;
	}
	public Piece(int orientation, int x, int y, int size) {
		this.orientation = orientation;
		this.x = x;
		this.y = y;
		this.size = size;
	}
	
	public Piece applyMove(PieceMovement m) {
		return new Piece(this.orientation, this.x + m.getDx(), this.y + m.getDy(), this.size);
	}
	
	public boolean equals(Object o) {
		if(o instanceof Piece) {
			Piece p = (Piece)o;
			if(p.getX() != this.getX()) { return false;}
			if(p.getY() != this.getY()) { return false;}
			if(p.getOrientation() != this.getOrientation()) { return false;}
			if(p.getSize() != this.getSize()) { return false;}
			return true;
		} else {
			return super.equals(o);
		}
	}
}
