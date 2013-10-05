package astar;

import java.util.ArrayList;

public class RHSearchNode implements Comparable<RHSearchNode> {

	private RushHourState state;
	private int edge;
	private int node_cost;
	private ArrayList<RHSearchNode> children;
	private RHSearchNode parent;
	private boolean expanded = false;
	
	
	public RHSearchNode(RushHourState state, int edge) {
		this.state = state;
		this.edge = edge;
		this.node_cost = state.getHeuristicValue();
		this.children = new ArrayList<RHSearchNode>();
		
	}
	
	public RushHourState getState() {
		return this.state;
	}
	
	public int getF(){
		return node_cost + edge;
	}
	
	public int getH() {
		return node_cost;
	}
	
	public int getG() {
		return edge;
	}
	
	
	public RHSearchNode getParent() {
		return parent;
	}

	public void setParent(RHSearchNode parent) {
		this.parent = parent;
	}

	public void expand() throws Exception {
		if(this.expanded) {
			throw new Exception("Search tree node already expanded");
		}
		this.expanded = true;
		ArrayList<RushHourState> l = this.state.getMutations();
		for(RushHourState rhs: l) {
			children.add(new RHSearchNode(rhs, this.edge + 1));
		}
	}
	
	public ArrayList<RHSearchNode> getChildren() {
		return this.children;
	}

	@Override
	public int compareTo(RHSearchNode o) {
		return -(o.getF() - this.getF());
	}
	
	public boolean isGoal() {
		return this.state.isGoal();
	}
	
	

}
