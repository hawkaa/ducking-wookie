package astar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RushHourState {

	private ArrayList<Piece> pieces = new ArrayList<Piece>();
	
	public void addPiece(Piece p) {
		this.pieces.add(p);
	}
	
	public int getPieceAt(int x, int y) {
		if(x < 0 || x>5 || y < 0 || y > 5) {
			return -2;
		}
		for(int i = 0; i< pieces.size(); ++i) {
			if(this.isAt(i, x, y)) {
				return i;
			}
		}
		return -1;
	}
	
	public boolean isAt(int piece, int x, int y) {
		if(piece >= this.pieces.size()) {
			return false;
		}
		Piece p = this.pieces.get(piece);
		int dX = p.getOrientation()==0?1:0;
		int dY = p.getOrientation()==0?0:1;
		for(int i = 0; i< p.getSize(); ++i) {
			if(p.getX() + dX*i == x && p.getY() + dY*i == y) {
				return true;
			}
		}
		return false;
		
	}
	
	public int getHeuristicValue() {
		int score = 0;
		int piece;
		for(int i = 5; i>=0; --i) {
			piece = this.getPieceAt(i, 2);
			if(piece == 0) {
				break;
			}
			++score;
			if(piece != -1) {
				++score;
			}
		}
		return score;
	}
	
	public boolean isGoal() {
		return isAt(0, 5, 2);
	}
	
	public String toString() {
		String ret = "";
		int piece;
		for(int j = 0; j < 6; ++j) {
			if(j != 0) {
				ret += "\n";
			}
			for(int i=0;i<6;++i) {
				piece = this.getPieceAt(i, j);
				if(piece == -1) {
					ret += ".";
				} else {
					ret += piece;
				}
			}
		}
		return ret;
	}
	public boolean equals(Object rhs) {
		if(rhs instanceof RushHourState) {
			for(int i = 0; i < 6; ++i) {
				for(int j=0; j<6;++j) {
					if(this.getPieceAt(i,j) != ((RushHourState)rhs).getPieceAt(i, j)) {
						return false;
					}
				}
			}
			return true;
		} else {
			return super.equals(rhs);
		}
		
	}
	
	public ArrayList<RushHourState> getMutations() {
		ArrayList<RushHourState> r = new ArrayList<RushHourState>();
		for(int i = 0; i<this.pieces.size(); ++i) {
			for(PieceMovement pm: this.getPieceMovements(i)) {
				r.add(this.applyMove(i, pm));
			}
		}
		return r;
	}
	
	public ArrayList<PieceMovement> getPieceMovements(int piece) {
		ArrayList<PieceMovement> pm = new ArrayList<PieceMovement>();
		Piece p = this.pieces.get(piece);
		if(p.orientation == 0) {
			if(this.getPieceAt(p.getX()-1, p.getY()) == -1) {
				pm.add(new PieceMovement(-1,0));
			}
			if(this.getPieceAt(p.getX()+ p.getSize(), p.getY()) == -1) {
				pm.add(new PieceMovement(1,0));
			}
		} else {
			if(this.getPieceAt(p.getX(), p.getY()-1) == -1) {
				pm.add(new PieceMovement(0,-1));
			}
			if(this.getPieceAt(p.getX(), p.getY()+p.getSize()) == -1) {
				pm.add(new PieceMovement(0,1));
			}
		}
		return pm;
	}
	
	public RushHourState copy() {
		RushHourState rhs = new RushHourState();
		for(Piece p: this.pieces) {
			rhs.addPiece(p);
		}
		return rhs;
	}
	
	public RushHourState applyMove(int piece, PieceMovement pm) {
		RushHourState rhs = this.copy();
		ArrayList<PieceMovement> pms = this.getPieceMovements(piece);
		if(pms.contains(pm)) {
			rhs.pieces.set(piece, this.pieces.get(piece).applyMove(pm));
		}
		return rhs;
	}

}
