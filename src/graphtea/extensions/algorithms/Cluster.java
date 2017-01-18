package graphtea.extensions.algorithms;

import graphtea.graph.graph.GraphPoint;

import java.util.ArrayList;

public class Cluster // Represents a cluster of GraphPoints
{
	private ArrayList<GraphPoint> member;
	private GraphPoint center;

	public Cluster(GraphPoint c) {
		center = c;
		member = new ArrayList<>();
	}

	public GraphPoint getCenter() {
		return center;
	}
	
	public ArrayList<GraphPoint> getMembers(){
		return member;
	}
	
	public void addMember(GraphPoint p){
		member.add(p);
	}
}