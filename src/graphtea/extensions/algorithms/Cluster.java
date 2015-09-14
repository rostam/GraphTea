package graphtea.extensions.algorithms;

import java.util.ArrayList;

import graphtea.graph.graph.GraphPoint;

public class Cluster // Represents a cluster of GraphPoints
{
	private ArrayList<GraphPoint> member;
	private GraphPoint center;

	public Cluster(GraphPoint c) {
		center = c;
		member = new ArrayList<GraphPoint>();
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