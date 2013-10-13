package simulated_annealing;


public class ECPPosition {
	public int row;
	public int column;
	public ECPPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public boolean equals(Object o) {
		if(o instanceof ECPPosition) {
			ECPPosition p = (ECPPosition)o;
			if(p.row != this.row) { return false;}
			if(p.column != this.column) { return false;}
			return true;
		} else {
			return super.equals(o);
		}
	}
}
