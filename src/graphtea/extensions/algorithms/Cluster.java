package graphtea.extensions.algorithms;

import graphtea.graph.graph.GPoint;

import java.util.ArrayList;

public class Cluster // Represents a cluster of GraphPoints
{
	private final ArrayList<GPoint> member;
	private final GPoint center;

	public Cluster(GPoint c) {
		center = c;
		member = new ArrayList<>();
	}

	public GPoint getCenter() {
		return center;
	}
	
	public ArrayList<GPoint> getMembers(){
		return member;
	}
	
	public void addMember(GPoint p){
		member.add(p);
	}
}