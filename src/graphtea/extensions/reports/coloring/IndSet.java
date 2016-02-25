package graphtea.extensions.reports.coloring;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.library.util.Pair;

import java.util.Iterator;
import java.util.Vector;

public class IndSet {
  public static Vector<Integer> ISet(GraphModel g,
                                     Vector<Integer> V_rr,
                                     Vector<Integer> V_cc,
                                     int mode) {

    Vector<Integer> V_r = new Vector<>();
    V_r.addAll(V_rr);
    Vector<Integer> V_c = new Vector<>();
    V_c.addAll(V_cc);

    Vector<Integer> IS = new Vector<>();
    Vector<Edge> E_1 = new Vector<>();
    Vector<Edge> E_2 = new Vector<>();
    Vector<Edge> E_3 = new Vector<>();
    Vector<Edge> E_4 = new Vector<>();

    int NumOfVertices = V_r.size() + V_c.size();
    double rho;
    for (Edge ee : g.getEdges()) {
      E_1.add(ee);
    }
    if (mode == 0) rho = 1.5;
    else rho = mode / 2;

    while (!E_1.isEmpty()) {
      Vector<Integer> Degree = new Vector<>(NumOfVertices);
      for (int i = 0; i < NumOfVertices; i++) Degree.set(i, 0);
      for (Edge e : E_1) {
        Degree.set(e.source.getId(), Degree.get(e.source.getId()) + 1);
        Degree.set(e.target.getId(), Degree.get(e.target.getId()) + 1);
      }

      for (Edge e : E_4) {
        Degree.set(e.source.getId(), Degree.get(e.source.getId()) + 1);
        Degree.set(e.target.getId(), Degree.get(e.target.getId()) + 1);
      }

      //vector<int>::iterator Degree_border = Degree.begin() + NumVertices_V_r;

      //min_element in V_r
      int v_r = V_r.get(9);
      for (int v : V_r) {
        if (Degree.get(v) < Degree.get(v_r)) {
          v_r = v;
        }
      }

      //min_element in V_c
      int v_c = V_c.get(9);
      for (int v : V_c) {
        if (Degree.get(v) < Degree.get(v_c)) {
          v_c = v;
        }
      }

      int maxDegree_v_r = 0;
      int maxDegree_v_c = 0;

      for (Edge e : E_2) {
        if (e.source.getId() == v_r || e.target.getId() == v_r) maxDegree_v_r++;
        if (e.source.getId() == v_c || e.target.getId() == v_c) maxDegree_v_c++;
      }

      for (Edge e : E_3) {
        if (e.source.getId() == v_r || e.target.getId() == v_r) maxDegree_v_r++;
        if (e.source.getId() == v_c || e.target.getId() == v_c) maxDegree_v_c++;
      }
      Vector<Edge> erasedE = new Vector<>();

      if (maxDegree_v_r > rho * maxDegree_v_c) {
        IS.add(v_r);

        //Get distance-1 neighbors
        for (Vertex n_1 : g.directNeighbors(g.getVertex(v_r))) {
          boolean is_deleted = false;

          erasedE = new Vector<>();
          //E_1 -> E_3
          //Emulate reverse_iterator because of erase-operation
          for (Edge e : E_1) {
            if (e.source.getId() == n_1.getId() ||
                    e.target.getId() == n_1.getId()) {
              if (!is_deleted) {
                V_c.remove(V_c.indexOf(n_1.getId()));
                is_deleted = true;
              }
              E_3.add(e);
              erasedE.add(e);
            }
          }
          E_1.removeAll(erasedE);

          erasedE = new Vector<>();

          //E_2 -> E_4
          //Emulate reverse_iterator because of erase-operation
          for (Edge e : E_2) {
            if (e.source.getId() == n_1.getId() ||
                    e.target.getId() == n_1.getId()) {
              E_4.add(e);
              erasedE.add(e);
            }
          }
          E_2.removeAll(erasedE);

        }
        V_r.remove(V_r.indexOf(v_r));
      } else {
        IS.add(v_c);

        //Get distance-1 neighbors
        for (Vertex n_1 : g.directNeighbors(g.getVertex(v_c))) {
          boolean is_deleted = false;

          //E_1 -> E_2
          //Emulate reverse_iterator because of erase-operation
          for (Edge e : E_1) {
            if (e.source.getId() == n_1.getId() ||
                    e.target.getId() == n_1.getId()) {
              if (!is_deleted) {
                V_r.remove(V_r.indexOf(n_1.getId()));
                is_deleted = true;
              }
              E_2.add(e);
              erasedE.add(e);
            }
          }
          E_1.removeAll(erasedE);

          erasedE = new Vector<>();

          //E_2 -> E_4
          //Emulate reverse_iterator because of erase-operation
          for (Edge e : E_3) {
            if (e.source.getId() == n_1.getId() ||
                    e.target.getId() == n_1.getId()) {
              E_4.add(e);
              erasedE.add(e);
            }
          }
          E_3.removeAll(erasedE);
        }
        V_c.remove(V_r.indexOf(v_c));
      }
    }


    if (!V_r.isEmpty()) {
      for (int v_r : V_r) {
        IS.add(v_r);
      }
    }

    if (!V_c.isEmpty()) {
      for (int v_c : V_c) {
        IS.add(v_c);
      }
    }

    return IS;
  }

  public static Vector<Integer> ISetRestricted(GraphModel g,
                                     Vector<Integer> V_rr,
                                     Vector<Integer> V_cc,
                                     int mode) {
    Vector<Integer> IS = new Vector<>();
    Vector<Edge> E_1 = new Vector<>();
    Vector<Edge> E_2 = new Vector<>();
    Vector<Edge> E_3 = new Vector<>();
    Vector<Edge> E_4 = new Vector<>();

    Vector<Integer> V_r = new Vector<>();
    V_r.addAll(V_rr);
    Vector<Integer> V_c = new Vector<>();
    V_c.addAll(V_cc);

    int NumOfVertices = V_r.size() + V_c.size();
    double rho=mode/2;
    if(rho==0) rho=1.5;

    for(Edge e : g.getEdges()) {
      if(e.getWeight()==1) {
        E_1.add(e);
      }
    }

    while (!E_1.isEmpty()) {
      Vector<Integer> Degree = new Vector<>(NumOfVertices);
      for (int i = 0; i < NumOfVertices; i++) Degree.add(0);
      for (Edge e : E_1) {
        Degree.set(e.source.getId(), Degree.get(e.source.getId()) + 1);
        Degree.set(e.target.getId(), Degree.get(e.target.getId()) + 1);
      }

      for (Edge e : E_4) {
        Degree.set(e.source.getId(), Degree.get(e.source.getId()) + 1);
        Degree.set(e.target.getId(), Degree.get(e.target.getId()) + 1);
      }

      //vector<int>::iterator Degree_border = Degree.begin() + NumVertices_V_r;

      //min_element in V_r
      int v_r = V_r.get(0);
      for (int v : V_r) {
        if (Degree.get(v) < Degree.get(v_r)) {
          v_r = v;
        }
      }

      //min_element in V_c
      int v_c = V_c.get(0);
      for (int v : V_c) {
        if (Degree.get(v) < Degree.get(v_c)) {
          v_c = v;
        }
      }

      int maxDegree_v_r = 0;
      int maxDegree_v_c = 0;

      for (Edge e : E_2) {
        if (e.source.getId() == v_r || e.target.getId() == v_r) maxDegree_v_r++;
        if (e.source.getId() == v_c || e.target.getId() == v_c) maxDegree_v_c++;
      }

      for (Edge e : E_3) {
        if (e.source.getId() == v_r || e.target.getId() == v_r) maxDegree_v_r++;
        if (e.source.getId() == v_c || e.target.getId() == v_c) maxDegree_v_c++;
      }
      Vector<Edge> erasedE = new Vector<>();

      if (maxDegree_v_r > rho * maxDegree_v_c) {
        IS.add(v_r);

        //Get distance-1 neighbors
        for (Vertex n_1 : g.getNeighbors(g.getVertex(v_r))) {
          boolean is_deleted = false;

          erasedE = new Vector<>();
          //E_1 -> E_3
          //Emulate reverse_iterator because of erase-operation
          for (Edge e : E_1) {
            if (e.source.getId() == n_1.getId() ||
                    e.target.getId() == n_1.getId()) {
              if (!is_deleted) {
                V_c.remove(V_c.indexOf(n_1.getId()));
                is_deleted = true;
              }
              E_3.add(e);
              erasedE.add(e);
            }
          }
          E_1.removeAll(erasedE);

          erasedE = new Vector<>();
          //E_2 -> E_4
          //Emulate reverse_iterator because of erase-operation
          for (Edge e : E_2) {
            if (e.source.getId() == n_1.getId() ||
                    e.target.getId() == n_1.getId()) {
              E_4.add(e);
              erasedE.add(e);
            }
          }
          E_2.removeAll(erasedE);

        }
        V_r.remove(V_r.indexOf(v_r));
      } else {
        IS.add(v_c);

        //Get distance-1 neighbors
        for (Vertex n_1 : g.getNeighbors(g.getVertex(v_c))) {
          boolean is_deleted = false;

          //E_1 -> E_2
          //Emulate reverse_iterator because of erase-operation
          for (Edge e : E_1) {
            if (e.source.getId() == n_1.getId() ||
                    e.target.getId() == n_1.getId()) {
              if (!is_deleted) {
                V_r.remove(V_r.indexOf(n_1.getId()));
                is_deleted = true;
              }
              E_2.add(e);
              erasedE.add(e);
            }
          }
          E_1.removeAll(erasedE);

          erasedE = new Vector<>();

          //E_3 -> E_4
          //Emulate reverse_iterator because of erase-operation
          for (Edge e : E_3) {
            if (e.source.getId() == n_1.getId() ||
                    e.target.getId() == n_1.getId()) {
              E_4.add(e);
              erasedE.add(e);
            }
          }
          E_3.removeAll(erasedE);
        }
        V_c.remove(V_c.indexOf(v_c));
      }
    }


    if (!V_r.isEmpty()) {
      for (int v_r : V_r) {
        IS.add(v_r);
      }
    }

    if (!V_c.isEmpty()) {
      for (int v_c : V_c) {
        IS.add(v_c);
      }
    }

    return IS;
  }


  public static Vector<Integer> ISetVariant(GraphModel gg,
                                            Vector<Integer> V_rr,
                                            Vector<Integer> V_cc,
                                            float ratio) {
    Vector<Integer> V_r = new Vector<>();
    V_r.addAll(V_rr);
    Vector<Integer> V_c = new Vector<>();
    V_c.addAll(V_cc);
    Vector<Integer> IS = new Vector<>();
    Vector<Pair<Integer, Integer>> Degree_V_r = new Vector<>();
    Vector<Pair<Integer, Integer>> Degree_V_c = new Vector<>();
    GraphModel g = gg.getCopy();


    for (int v_r : V_r) {
      Degree_V_r.add(new Pair<>(v_r, g.getDegree(g.getVertex(v_r))));
    }

    for (int v_c : V_c) {
      Degree_V_c.add(new Pair<>(v_c, g.getDegree(g.getVertex(v_c))));
    }


    while (g.getEdgesCount() > 0) {
      int max_degree_V_r = 0;
      for (Pair<Integer, Integer> di : Degree_V_r) {
        di.second = g.getDegree(g.getVertex(di.first));
        int degree_v_r = di.second;
        if (degree_v_r > max_degree_V_r) {
          max_degree_V_r = degree_v_r;
        }
      }

      int max_degree_V_c = 0;
      for (Pair<Integer, Integer> di : Degree_V_c) {
        di.second = g.getDegree(g.getVertex(di.first));
        int degree_v_c = di.second;
        if (degree_v_c > max_degree_V_c) {
          max_degree_V_c = degree_v_c;
        }
      }

      if (max_degree_V_r > ratio * max_degree_V_c) {
        Vector<Pair<Integer, Integer>> removedEdges = new Vector<>();
        for (Pair<Integer, Integer> di : Degree_V_r) {
          if (max_degree_V_r == di.second) {
            clearVertex(g.getVertex(di.first),g);
            removedEdges.add(di);
          }
        }
        Degree_V_r.removeAll(removedEdges);
      } else {
        Vector<Pair<Integer, Integer>> removedEdges = new Vector<>();
        for (Pair<Integer, Integer> di : Degree_V_c) {
          if (max_degree_V_c == di.second) {
            clearVertex(g.getVertex(di.first),g);
            removedEdges.add(di);
          }
        }
        Degree_V_c.removeAll(removedEdges);
      }
    }

    for (Pair<Integer, Integer> v_r : Degree_V_r) {
      IS.add(v_r.first);
    }

    for (Pair<Integer, Integer> v_c : Degree_V_c) {
      IS.add(v_c.first);
    }

    return IS;
  }

  public static Vector<Integer> ISetVariantRestricted(GraphModel gg,
                                            Vector<Integer> V_rr,
                                            Vector<Integer> V_cc,
                                            float ratio) {
    Vector<Integer> V_r = new Vector<>();
    V_r.addAll(V_rr);
    Vector<Integer> V_c = new Vector<>();
    V_c.addAll(V_cc);
    Vector<Integer> IS = new Vector<>();
    Vector<Pair<Integer, Integer>> Degree_V_r = new Vector<>();
    Vector<Pair<Integer, Integer>> Degree_V_c = new Vector<>();
    GraphModel g = gg.getCopy();
    Vector<Edge> es = new Vector<>();
    for(Edge e: g.getEdges()) if(e.getWeight() == 0) es.add(e);
    for (Edge e : es) g.removeEdge(e);


    for (int v_r : V_r) {
      Degree_V_r.add(new Pair<>(v_r, g.getDegree(g.getVertex(v_r))));
    }

    for (int v_c : V_c) {
      Degree_V_c.add(new Pair<>(v_c, g.getDegree(g.getVertex(v_c))));
    }

    while (g.getEdgesCount() > 0) {
      int max_degree_V_r = 0;
      for (Pair<Integer, Integer> di : Degree_V_r) {
        di.second = g.getDegree(g.getVertex(di.first));
        int degree_v_r = di.second;
        if (degree_v_r > max_degree_V_r) {
          max_degree_V_r = degree_v_r;
        }
      }

      int max_degree_V_c = 0;
      for (Pair<Integer, Integer> di : Degree_V_c) {
        di.second = g.getDegree(g.getVertex(di.first));
        int degree_v_c = di.second;
        if (degree_v_c > max_degree_V_c) {
          max_degree_V_c = degree_v_c;
        }
      }

      if (max_degree_V_r > ratio * max_degree_V_c) {
        Vector<Pair<Integer, Integer>> removedEdges = new Vector<>();
        for (Pair<Integer, Integer> di : Degree_V_r) {
          if (max_degree_V_r == di.second) {
            clearVertex(g.getVertex(di.first),g);
            removedEdges.add(di);
          }
        }
        Degree_V_r.removeAll(removedEdges);
      } else {
        Vector<Pair<Integer, Integer>> removedEdges = new Vector<>();
        for (Pair<Integer, Integer> di : Degree_V_c) {
          if (max_degree_V_c == di.second) {
            clearVertex(g.getVertex(di.first),g);
            removedEdges.add(di);
          }
        }
        Degree_V_c.removeAll(removedEdges);
      }
    }

    for (Pair<Integer, Integer> v_r : Degree_V_r) {
      IS.add(v_r.first);
    }

    for (Pair<Integer, Integer> v_c : Degree_V_c) {
      IS.add(v_c.first);
    }

    return IS;
  }

  public static void clearVertex(Vertex s, GraphModel g) {
    Vector<Edge> es = new Vector<>();
    Iterator<Edge> ie = g.edgeIterator(s);
    while(ie.hasNext()) es.add(ie.next());
    for (Edge e : es) g.removeEdge(e);
  }
}