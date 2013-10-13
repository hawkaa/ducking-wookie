package simulated_annealing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

import org.junit.internal.runners.statements.Fail;

import astar.Piece;

public class ECPState extends SimulatedAnnealingState {
	
	private int rows, columns, eggs;
	private boolean board[][];
	private Random rnd;
	
	public static ECPState generateRandom(int rows, int columns, int eggs) {
		ECPState ecps = new ECPState(rows, columns, eggs);
		ecps.randomize();
		return ecps;
	}
	
	
	public ECPState(int rows, int columns, int eggs) {
		this.rows = rows;
		this.columns = columns;
		this.eggs = eggs;
		this.board = new boolean[rows][columns];
		this.rnd = new Random();
		
	}
	
	public String printState() {
		String s = "";
		for(int m =0; m< this.rows; ++m) {
			if(m != 0) {
				s += "\n";
			}
			for(int n = 0; n<this.columns; ++n){
				s+= this.board[m][n]?"*":".";
			}
		}
		return s;
		
	}

	
	@Override
	public ArrayList<SimulatedAnnealingState> getNeighbours() {
		return getNeighbours2();
	}

	
	
	ArrayList<SimulatedAnnealingState> getNeighbours1() {
		ArrayList<SimulatedAnnealingState> l = new ArrayList<SimulatedAnnealingState>();

		ArrayList<ECPPosition> emptyPositions = this.getEmptyPositions();
		ArrayList<ECPPosition> eggPositions = this.getEggPositions();

		Collections.shuffle(emptyPositions);
		Collections.shuffle(eggPositions);
		
		ECPState ecps;
		
		for(int i = 0; i< emptyPositions.size() && i<3; ++i) {
			ecps = this.clone();
			ecps.put(emptyPositions.get(i).row, emptyPositions.get(i).column, true);
			l.add(ecps);
		}
		
		for(int i = 0; i< eggPositions.size() && i<3; ++i) {
			ecps = this.clone();
			ecps.put(eggPositions.get(i).row, eggPositions.get(i).column, false);
			l.add(ecps);
		}
		
		
		return l;
	}
	
	ArrayList<SimulatedAnnealingState> getNeighbours2() {
		ArrayList<SimulatedAnnealingState> l = new ArrayList<SimulatedAnnealingState>();
		for(int i = 0; i < 10; ++i) {

			ECPState s = this.clone();
			for(int j = 0; j < 3; ++j) {
				s.swap(this.rnd.nextInt(this.rows), this.rnd.nextInt(this.columns));
			}
			l.add(s);
			
		}
		
		return l;
	}
	

	@Override
	public double objectiveFunction() {
		return objectiveFunction2();
	}
	
	double objectiveFunction1() {
		return (double)this.getPoints1()/this.getMaxScore1();
	}

	@Override
	protected void randomize() {
		for(int m =0; m< this.rows; ++m) {
			for(int n = 0; n<this.columns; ++n){
				this.board[m][n] = rnd.nextBoolean();
			}
		}
	}
	
	@Override
	public int hashCode() {
		return 0;
	}
	
	public boolean equals(Object o) {
		if(o instanceof ECPState) {
			ECPState s = (ECPState)o;
			if(s.rows != this.rows) { return false; }
			if(s.columns != this.columns) { return false; }
			if(s.eggs != this.eggs) { return false; }
			for(int m =0; m< this.rows; ++m) {
				for(int n = 0; n<this.columns; ++n){
					if(this.board[m][n] != s.board[m][n]) {
						return false;
					}
				}
			}
			return true;
		} else {
			return super.equals(o);
		}
	}
	
	void put(int row, int column, boolean egg) {
		this.board[row][column] = egg;
	}
	
	int getPoints1() {
		int p = 0;
		for(int i = 0; i<this.rows; ++i) {
			for(int j = 0; j<this.columns; ++j) {
				if (this.board[i][j]) {
					p += this.getConflicting(i, j) <=this.eggs?1:0;
				}
			}
		}
		return p;
	}
	
	int getMaxScore1() {
		return rows < columns? rows*eggs: columns*eggs;
	}
	
	protected ECPState clone() {
		ECPState s = new ECPState(this.rows, this.columns, this.eggs);
		for(int m =0; m< this.rows; ++m) {
			for(int n = 0; n<this.columns; ++n){
				s.board[m][n] = this.board[m][n]; 
			}
		}
		return s;
	}
	
	
	/**
	 * 
	 * @return the number of conflicting operations for a given row and column 
	 */
	int getConflicting(int row, int column) {
		ArrayList<Integer> conflicts = new ArrayList<Integer>();

		conflicts.add(getConflicting(row, column, 0, -1) + (this.board[row][column]?1:0) + getConflicting(row, column, 0, 1));
		conflicts.add(getConflicting(row, column, -1, 0) + (this.board[row][column]?1:0) + getConflicting(row, column, 1, 0));
		
		conflicts.add(getConflicting(row, column, -1, -1) + (this.board[row][column]?1:0) + getConflicting(row, column, 1, 1));
		conflicts.add(getConflicting(row, column, -1, 1) + (this.board[row][column]?1:0) + getConflicting(row, column, 1, -1));
		return Collections.max(conflicts);
	}

	boolean isConflicting(int row, int column) {
		if(this.board[row][column] == false) {
			return false;
		}
		return this.getConflicting(row, column) > this.eggs;
	}
	
	int getConflicting(int row, int column, int deltaRow, int deltaColumn) {
		int c = 0;
		int currentRow = row + deltaRow;
		int currentColumn = column+deltaColumn;
		while(currentColumn >= 0 && currentColumn < this.columns && currentRow >= 0 && currentRow < this.rows) {
			c += this.board[currentRow][currentColumn]?1:0;
			currentRow += deltaRow;
			currentColumn += deltaColumn;
		}
			
		return c;
	}
	
	int getNumPieces() {
		int num = 0;
		for(int i = 0; i<this.rows; ++i) {
			for(int j = 0; j<this.columns; ++j) {
				num += this.board[i][j]?1:0;
			}
		}
		return num;
	}
	

	
	ArrayList<ECPPosition> getEmptyPositions() {
		ArrayList<ECPPosition> pos = new ArrayList<ECPPosition>();
		for(int i = 0; i<this.rows; ++i) {
			for(int j = 0; j<this.columns; ++j) {
				if(!this.board[i][j]) {
					pos.add(new ECPPosition(i, j));
				}
			}
		}
		return pos;
	}
	ArrayList<ECPPosition> getEggPositions() {
		ArrayList<ECPPosition> pos = new ArrayList<ECPPosition>();
		for(int i = 0; i<this.rows; ++i) {
			for(int j = 0; j<this.columns; ++j) {
				if(this.board[i][j]) {
					pos.add(new ECPPosition(i, j));
				}
			}
		}
		return pos;
	}
	
	static boolean onlyUniqueElements(ArrayList<ECPState> l) {
		HashSet<ECPState> e = new HashSet<ECPState>();
		for(ECPState ecps: l) {
			e.add(ecps);
		}
		return e.size() == l.size();
	}
	
	void swap(int row, int column) {
		this.board[row][column] = !this.board[row][column];
	}


	int getMaxScore2() {
		int iMaxPieces = rows < columns? rows*eggs: columns*eggs;
		System.out.println(iMaxPieces);
		int size = rows*columns;
		int empty = size-iMaxPieces;
		return iMaxPieces*3 + empty*1;
	}


	int getPoints2() {
		int points = 0;
		for(int i = 0; i<this.rows; ++i) {
			for(int j = 0; j<this.columns; ++j) {
				if(!isConflicting(i, j)){
					points += this.board[i][j]?3:1;
				}
			}
		}
		return points;
	}


	double objectiveFunction2() {
		return (double)this.getPoints2()/this.getMaxScore2();
	}

}
