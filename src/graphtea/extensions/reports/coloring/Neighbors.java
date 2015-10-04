package graphtea.extensions.reports.coloring;

import graphtea.graph.graph.GraphModel;

import java.util.Collections;
import java.util.Vector;
import graphtea.graph.graph.Vertex;


public class Neighbors {
//Compute the distance-2 neighbors of Vertex
  public static Vector<Integer> N_2(GraphModel g, int Vertex) {
    Vector<Integer> neighbors = new Vector<>();

    for(Vertex v : g.getNeighbors(g.getVertex(Vertex))) {
      for(Vertex nv : g.getNeighbors(v)) {
        if(nv.getId()!=Vertex) {
          if(!neighbors.contains(nv)) neighbors.add(nv.getId());
        }
      }
    }

    Collections.sort(neighbors);
    return neighbors;
  }

  public static Vector<Integer> N_2_restricted(GraphModel g, int Vertex) {
    Vector<Integer> neighbors = new Vector<>();
    Vertex s = g.getVertex(Vertex);


    for(Vertex v : g.getNeighbors(s)) {
      for (Vertex nv : g.getNeighbors(v)) {
        if (nv.getId() != Vertex) {
          if (g.getEdge(s, v).getWeight() == 1 || g.getEdge(v, nv).getWeight() == 1) {
            if (!neighbors.contains(nv)) {
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