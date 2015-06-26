package graphtea.extensions.algorithms;

import java.util.ArrayList;

import graphtea.graph.graph.GraphPoint;

public class Cluster // Represents a cluster of GraphPoints
{
	GraphPoint center;
	ArrayList<GraphPoint> member;

	public Cluster(GraphPoint a) {
		center = a;
		member = new ArrayList<GraphPoint>();
	}

	public GraphPoint getCentroid() {
		double x = 0., y = 0.;
		for (GraphPoint a : member) {
			x += a.x;
			y += a.y;
		}

		return new GraphPoint((int) (x / member.size()), (int) (y / member.size()));
	}
	
	public ArrayList<GraphPoint> getMembers(){
		return member;
	}
}