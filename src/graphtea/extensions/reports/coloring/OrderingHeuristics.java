package graphtea.extensions.reports.coloring;

import graphtea.graph.graph.GraphModel;
import graphtea.library.util.Pair;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

public class OrderingHeuristics {

  public static Vector<Integer> LFO(GraphModel g, Vector<Integer> V) {
    List<Pair<Integer, Integer>> VertexDegree = new Vector<>();
    Vector<Integer> Ordering = new Vector<>();

    //Compute N_2-degree for all vertices in V
    for (int v : V) {
      VertexDegree.add(new Pair<>(v, Neighbors.N_2(g, v).size()));
    }

    //Sort after degree
    //ge_degree because of compability with matlab alg, otherwise gt_degree
    Collections.sort(VertexDegree, new Comparator<Pair<Integer, Integer>>() {
      @Override
      public int compare(Pair<Integer, Integer> t1, Pair<Integer, Integer> t2) {
        return t1.second - t2.second;
      }
    });

    for (Pair<Integer, Integer> p : VertexDegree) {
      Ordering.add(p.first);
    }
    return Ordering;
  }

  public static Vector<Integer> LFOrestricted(GraphModel g, Vector<Integer> V) {
    List<Pair<Integer, Integer>> VertexDegree = new Vector<>();
    Vector<Integer> Ordering = new Vector<>();

    //Compute N_2-degree for all vertices in V
    for (int v : V) {
      VertexDegree.add(new Pair<>(v, Neighbors.N_2_restricted(g, v).size()));
    }

    //Sort after degree
    //ge_degree because of compability with matlab alg, otherwise gt_degree
    Collections.sort(VertexDegree, new Comparator<Pair<Integer, Integer>>() {
      @Override
      public int compare(Pair<Integer, Integer> t1, Pair<Integer, Integer> t2) {
        return t1.second - t2.second;
      }
    });

    for (Pair<Integer, Integer> p : VertexDegree) {
      Ordering.add(p.first);
    }
    return Ordering;
  }

  public static Vector<Integer> SLO(GraphModel g, Vector<Integer> V) {
    List<Pair<Integer, Integer>> VertexDegree = new Vector<>();
    Vector<Integer> Ordering = new Vector<>();

    //Compute N_2-degree for all vertices in V
    for (int v : V) {
      VertexDegree.add(new Pair<>(v, Neighbors.N_2(g, v).size()));
    }

    for (int i = 0; i < V.size(); i++) {
      int min = g.getEdgesCount() + 1;
      int minInd = g.getEdgesCount() + 1;
      for (int j = 0; j < VertexDegree.size(); j++) {
        if (min > VertexDegree.get(j).second) {
          minInd = j;
          min = VertexDegree.get(j).second;
        }
      }

      Pair<Integer, Integer> p = VertexDegree.get(minInd);
      p.second = g.getEdgesCount();
      VertexDegree.set(i, p);
      Ordering.add(p.first);

      //decrement degree of D_2-neighbors
      Vector<Integer> ns = Neighbors.N_2(g, p.first);
      for (int n2 : ns) {
        if (n2 >= V.size()) {
          if (VertexDegree.get(n2 - V.size()).second != -1) {
            Pair<Integer, Integer> tmp = VertexDegree.get(n2 - V.size());
            tmp.second--;
            VertexDegree.set(n2 - V.size(), tmp);
          }
        } else {
          if (VertexDegree.get(n2).second != -1) {
            Pair<Integer, Integer> tmp = VertexDegree.get(n2);
            tmp.second++;
            VertexDegree.set(n2, tmp);
          }
        }
      }

      if (i % 100 == 0) {
        System.out.println("i=" + i);
      }
    }

    Collections.reverse(VertexDegree);
    return Ordering;
  }

  public static Vector<Integer> SLOrestricted(GraphModel g, Vector<Integer> V) {
    List<Pair<Integer, Integer>> VertexDegree = new Vector<>();
    Vector<Integer> Ordering = new Vector<>();

    //Compute N_2-degree for all vertices in V
    for (int v : V) {
      VertexDegree.add(new Pair<>(v, Neighbors.N_2_restricted(g, v).size()));
    }

    for (int i = 0; i < V.size(); i++) {
      int min = g.getEdgesCount() + 1;
      int minInd = g.getEdgesCount() + 1;
      for (int j = 0; j < VertexDegree.size(); j++) {
        if (min > VertexDegree.get(j).second) {
          minInd = j;
          min = VertexDegree.get(j).second;
        }
      }

      Pair<Integer, Integer> p = VertexDegree.get(minInd);
      p.second = g.getEdgesCount();
      VertexDegree.set(i, p);
      Ordering.add(p.first);

      //decrement degree of D_2-neighbors
      Vector<Integer> ns = Neighbors.N_2_restricted(g, p.first);
      for (int n2 : ns) {
        if (n2 >= V.size()) {
          if (VertexDegree.get(n2 - V.size()).second != -1) {
            Pair<Integer, Integer> tmp = VertexDegree.get(n2 - V.size());
            tmp.second--;
            VertexDegree.set(n2 - V.size(), tmp);
          }
        } else {
          if (VertexDegree.get(n2).second != -1) {
            Pair<Integer, Integer> tmp = VertexDegree.get(n2);
            tmp.second++;
            VertexDegree.set(n2, tmp);
          }
        }
      }

      if (i % 100 == 0) {
        System.out.println("i=" + i);
      }
    }

    Collections.reverse(VertexDegree);
    return Ordering;
  }

  public static Vector<Integer> IDO(GraphModel g, Vector<Integer> V) {
    List<Pair<Integer, Pair<Integer, Integer>>> VertexDegree = new Vector<>();
    Vector<Integer> Ordering = new Vector<>();

    //Compute N_2-degree for all vertices in V
    for (int v : V) {
      VertexDegree.add(new Pair<>(v, new Pair<>(Neighbors.N_2(g, v).size(), 0)));
    }

    for (int i = 0; i < V.size(); i++) {

      Pair<Integer, Pair<Integer, Integer>> max = Collections.max(VertexDegree,
              new Comparator<Pair<Integer, Pair<Integer, Integer>>>() {
                @Override
                public int compare(Pair<Integer, Pair<Integer, Integer>> t1, Pair<Integer, Pair<Integer, Integer>> t2) {
                  if (t1.second.second < t2.second.second) {
                    return 1;
                  } else if (t1.second.second == t2.second.second && t1.second.first < t2.second.first) {
                    return 1;
                  } else {
                    return 0;
                  }
                }
              });

      int maxIndex = 0;
      for (int j = 0; j < VertexDegree.size(); j++) {
        Pair<Integer, Pair<Integer, Integer>> p = VertexDegree.get(j);
        if (p.first == max.first && p.second.first == max.second.first
                && p.second.second == max.second.second) {
          maxIndex = j;
          break;
        }
      }

      Pair<Integer, Pair<Integer, Integer>> p = VertexDegree.get(maxIndex);
      p.second.second = -1;
      VertexDegree.set(maxIndex, p);
      Ordering.add(p.first);


      //decrement degree of D_2-neighbors
      Vector<Integer> ns = Neighbors.N_2_restricted(g, p.first);
      for (int n2 : ns) {
        if (n2 >= V.size()) {
          if (VertexDegree.get(n2 - V.size()).second.second != -1) {
            Pair<Integer, Pair<Integer, Integer>> tmp = VertexDegree.get(n2 - V.size());
            tmp.second.second++;
            VertexDegree.set(n2 - V.size(), tmp);
          }
        } else {
          if (VertexDegree.get(n2).second.second != -1) {
            Pair<Integer, Pair<Integer, Integer>> tmp = VertexDegree.get(n2);
            tmp.second.second++;
            VertexDegree.set(n2, tmp);
          }
        }
      }
    }

    return Ordering;
  }

  public static Vector<Integer> IDOrestricted(GraphModel g, Vector<Integer> V) {
    List<Pair<Integer, Pair<Integer, Integer>>> VertexDegree = new Vector<>();
    Vector<Integer> Ordering = new Vector<>();

    //Compute N_2-degree for all vertices in V
    for (int v : V) {
      VertexDegree.add(new Pair<>(v, new Pair<>(Neighbors.N_2_restricted(g, v).size(), 0)));
    }

    for (int i = 0; i < V.size(); i++) {

      Pair<Integer, Pair<Integer, Integer>> max = Collections.max(VertexDegree,
              new Comparator<Pair<Integer, Pair<Integer, Integer>>>() {
                @Override
                public int compare(Pair<Integer, Pair<Integer, Integer>> t1, Pair<Integer, Pair<Integer, Integer>> t2) {
                  if (t1.second.second < t2.second.second) {
                    return 1;
                  } else if (t1.second.second == t2.second.second && t1.second.first < t2.second.first) {
                    return 1;
                  } else {
                    return 0;
                  }
                }
              });

      int maxIndex = 0;
      for (int j = 0; j < VertexDegree.size(); j++) {
        Pair<Integer, Pair<Integer, Integer>> p = VertexDegree.get(j);
        if (p.first == max.first && p.second.first == max.second.first
                && p.second.second == max.second.second) {
          maxIndex = j;
          break;
        }
      }

      Pair<Integer, Pair<Integer, Integer>> p = VertexDegree.get(maxIndex);
      p.second.second = -1;
      VertexDegree.set(maxIndex, p);
      Ordering.add(p.first);


      //decrement degree of D_2-neighbors
      Vector<Integer> ns = Neighbors.N_2_restricted(g, p.first);
      for (int n2 : ns) {
        if (n2 >= V.size()) {
          if (VertexDegree.get(n2 - V.size()).second.second != -1) {
            Pair<Integer, Pair<Integer, Integer>> tmp = VertexDegree.get(n2 - V.size());
            tmp.second.second++;
            VertexDegree.set(n2 - V.size(), tmp);
          }
        } else {
          if (VertexDegree.get(n2).second.second != -1) {
            Pair<Integer, Pair<Integer, Integer>> tmp = VertexDegree.get(n2);
            tmp.second.second++;
            VertexDegree.set(n2, tmp);
          }
        }
      }
    }

    return Ordering;
  }
}