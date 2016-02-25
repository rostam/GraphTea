package graphtea.extensions.reports.coloring;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

import java.util.Collections;
import java.util.Vector;


public class Neighbors {
//Compute the distance-2 neighbors of Vertex
  public static Vector<Integer> N_2(GraphModel g, int startV) {
    Vector<Integer> neighbors = new Vector<>();

    for(Vertex v : g.directNeighbors(g.getVertex(startV))) {
      for(Vertex nv : g.directNeighbors(v)) {
        if(nv.getId()!=startV) {
          if(!neighbors.contains(nv.getId())) neighbors.add(nv.getId());
        }
      }
    }

    //Collections.sort(neighbors);
    return neighbors;
  }

  public static Vector<Integer> N_2_restricted(GraphModel g, int startV) {
    Vector<Integer> neighbors = new Vector<>();
    Vertex s = g.getVertex(startV);

    for(Vertex v : g.getNeighbors(s)) {
      for (Vertex nv : g.getNeighbors(v)) {
        if (nv.getId() != startV) {
          if (g.getEdge(s, v).getWeight() == 1 || g.getEdge(v, nv).getWeight() == 1) {
            if (!neighbors.contains(nv.getId())) {
              neighbors.add(nv.getId());
            }
          }
        }
      }
    }

    Collections.sort(neighbors);
    return neighbors;
  }
}