package graphtea.extensions.reports.coloring;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.library.util.Pair;

import java.util.Iterator;
import java.util.Vector;
import java.util.stream.Collectors;


public class ColorAlgs {
  /**
   * Compute a distance-2 coloring of a bipartite graph G_b
   * <p/>
   * Input:
   * - G_b bipartite graph
   * - V   contained vertices are colored in the given ordering v_1, ..., v_n
   * <p/>
   * Output:
   * - G_b bipartite graph with colors as weights vertex_color
   */
  public static int PartialD2Coloring(GraphModel g, Vector<Integer> V) {
    Vector<Integer> forbidden = new Vector<>();
    for(int i=0;i<g.numOfVertices();i++) forbidden.add(-1);
    for (int v : V) g.getVertex(v).setColor(0);
    for (int v : V) {
      if (g.getDegree(g.getVertex(v)) > 0) {
        forbidden.set(0, v);
        Vector<Integer> N2 = Neighbors.N_2(g, v);
        for (int n2 : N2) {
          if (g.getVertex(n2).getColor() > 0) {
            forbidden.set(g.getVertex(n2).getColor(), v);
          }
        }
        for (int i = 0; i < forbidden.size(); i++) {
          if (forbidden.get(i) != v) {
            g.getVertex(v).setColor(i);
            break;
          }
        }
      } else {
        g.getVertex(v).setColor(0);
      }
    }

    return getNumOfCols(g,V);
  }

  public static int getNumOfCols(GraphModel g, Vector<Integer> V) {
    int res = 0;
    for (int v : V) {
      if (g.getVertex(v).getColor() > res) res = g.getVertex(v).getColor();
    }
    return res;
  }

  public static boolean IncidentToReqEdge(GraphModel g, int Vertex) {
    for(Edge e : g.edges(g.getVertex(Vertex))) if(e.getWeight()==1) return true;
    return false;
  }


  /**
   * Compute a distance-2 coloring of a bipartite graph G_b restricted
   * to required edges
   * <p/>
   * Input:
   * - G_b bipartite graph with required egdes given as weights edge_weight
   * - V   contained vertices are colored in the given ordering v_1, ..., v_n
   * <p/>
   * Output:
   * - G_b bipartite graph with colors as weights vertex_color
   */

  public static int PartialD2ColoringRestricted(GraphModel g, Vector<Integer> V) {
    Vector<Integer> N2 = new Vector<>();
    Vector<Integer> forbidden = new Vector<>(g.numOfVertices());
    for (int i = 0; i < g.numOfVertices(); i++) forbidden.add(-1);
    for (int v : V) g.getVertex(v).setColor(0);

    for (int v : V) {
      forbidden.set(0, v);
      if (IncidentToReqEdge(g, v)) {
        N2 = Neighbors.N_2_restricted(g, v);
        for (int n2 : N2) {
          if (g.getVertex(n2).getColor() > 0) {
            forbidden.set(g.getVertex(n2).getColor(), v);
          }
        }
        for (int i = 0; i < forbidden.size(); i++) {
          if (forbidden.get(i) != v) {
            g.getVertex(v).setColor(i);
            break;
          }
        }
      } else {
        g.getVertex(v).setColor(0);
      }
    }

    return getNumOfCols(g,V);
  }

  /**
   * Compute a star bicoloring of a bipartite graph G_b (this version
   * was implemented for Michael Luelfesmann's diploma thesis)
   * <p/>
   * Input:
   * - G_b   bipartite graph
   * - V_r   contained row vertices are colored in the given ordering v_1, ..., v_n
   * - V_c   contained column vertices are colored in the given ordering v_1, ..., v_n
   * - Mode  parameter \rho (\rho=Mode/2)
   * - Mode2 parameter \rho if using independent set algorithm of Coleman (Mode==1)
   * <p/>
   * Output:
   * - G_b bipartite graph with colors given as weights vertex_color
   */
  public static int StarBicoloringScheme(GraphModel g, Vector<Integer> V_r,
                                         Vector<Integer> V_c,
                                         int Mode, int Mode2) {
    Vector<Integer> IS = new Vector<>();

    //Compute independent set
    if (Mode == 1) {
      IS = IndSet.ISet(g, V_r, V_c, Mode2); //ISet = IS_Coleman(G_b,V_r,V_c);
    } else if (Mode == 2) {
      IS = IndSet.ISetVariant(g, V_r, V_c, 1.0f);
    } else if (Mode == 3) {
      IS = IndSet.ISetVariant(g, V_r, V_c, 1.5f);
    } else if (Mode == 4) {
      IS = IndSet.ISetVariant(g, V_r, V_c, 2);
    } else if (Mode == 5) {
      IS = IndSet.ISetVariant(g, V_r, V_c, 2.5f);
    } else if (Mode == 6) {
      IS = IndSet.ISetVariant(g, V_r, V_c, 3.0f);
    } else if (Mode == 7) {
      IS = IndSet.ISetVariant(g, V_r, V_c, 3.5f);
    } else if (Mode == 8) {
      IS = IndSet.ISetVariant(g, V_r, V_c, 4.0f);
    } else if (Mode == 9) {
      IS = IndSet.ISetVariant(g, V_r, V_c, 4.5f);
    } else if (Mode == 10) {
      IS = IndSet.ISetVariant(g, V_r, V_c, 5.0f);
    } else if (Mode == 11) {
      IS = IndSet.ISetVariant(g, V_r, V_c, 5.5f);
    }

    //Color vertices in independent set with color 0
    for (int IS_it : IS) {
      g.getVertex(IS_it).setColor(0);
    }

    //Color all vertices which are not colored
    StarBicoloring(g, V_r, V_c);
    Vector<Integer> V = new Vector<>();
    V.addAll(V_c);
    V.addAll(V_r);
    return getNumOfCols(g,V);
  }

  public static boolean StarBicoloring(GraphModel g, Vector<Integer> V_r, Vector<Integer> V_c) {
    Vector<Integer> forbiddenColors = new Vector<>(V_r.size());
    for(int i=0; i < V_r.size();i++) forbiddenColors.add(-1);
    Vector<Integer> Vertices = new Vector<>(V_r.size() + V_c.size());

    //GraphModel gg = g.getCopy();
    for (int i = 0; i < V_r.size(); i++) {
      Vertices.add(g.getVertex(i).getId());
    }

    for (int i = 0; i < V_c.size(); i++) {
      Vertices.add(g.getVertex(i).getId());
    }

    for (int v : Vertices) {
      if (g.getVertex(v).getColor() != 0) {
        forbiddenColors.set(0, v);
        for (Vertex w : g.getNeighbors(g.getVertex(v))) {
          if (w.getColor() <= 0) {
            for (Vertex x : g.getNeighbors(w)) {
              if (x.getColor() > 0) forbiddenColors.set(x.getColor(), v);
            }
          } else {
            for (Vertex x : g.getNeighbors(w)) {
              if (x.getColor() > 0) {
                for (Vertex y : g.getNeighbors(x)) {
                  if (y.getColor() > 0) {
                    if (w.getColor() == y.getColor() && w.getId() != y.getId()) {
                      forbiddenColors.set(x.getColor(), v);
                    }
                  }
                }
              }
            }
          }
        }
      }

      for (int i = 0; i < forbiddenColors.size(); i++) {
        if (forbiddenColors.get(i) != v) g.getVertex(v).setColor(i);
      }

    }
    return true;
  }

  /**
   * Compute a star bicoloring of a bipartite graph G_b restricted to
   * the required edges (this version was implemented for Michael
   * Luelfesmann's diploma thesis)
   *
   * Input:
   * - G_b   bipartite graph with required egdes given as weights edge_weight
   * - V_r   contained row vertices are colored in the given ordering v_1, ..., v_n
   * - V_c   contained column vertices are colored in the given ordering v_1, ..., v_n
   * - Mode  parameter \rho (\rho=Mode/2)
   * - Mode2 parameter \rho if using independent set algorithm of Coleman (Mode==1)
   *
   * Output:
   * - G_b bipartite graph with colors given as weights vertex_color
   */  public static int StarBicoloringSchemeRestricted(GraphModel g,
                                                        Vector<Integer> V_r,
                                         Vector<Integer> V_c,
                                         int Mode, int Mode2) {
    Vector<Integer> IS = new Vector<>();

    //Compute independent set
    if (Mode == 1) {
      IS = IndSet.ISetRestricted(g, V_r, V_c, Mode2); //ISet = IS_Coleman(G_b,V_r,V_c);
    } else if (Mode == 2) {
      IS = IndSet.ISetVariantRestricted(g, V_r, V_c, 1.0f);
    } else if (Mode == 3) {
      IS = IndSet.ISetVariantRestricted(g, V_r, V_c, 1.5f);
    } else if (Mode == 4) {
      IS = IndSet.ISetVariantRestricted(g, V_r, V_c, 2);
    } else if (Mode == 5) {
      IS = IndSet.ISetVariantRestricted(g, V_r, V_c, 2.5f);
    } else if (Mode == 6) {
      IS = IndSet.ISetVariantRestricted(g, V_r, V_c, 3.0f);
    } else if (Mode == 7) {
      IS = IndSet.ISetVariantRestricted(g, V_r, V_c, 3.5f);
    }

    //Color vertices in independent set with color 0
    for (int IS_it : IS) {
      g.getVertex(IS_it).setColor(0);
    }

    //Color all vertices which are not colored
    StarBicoloringRestricted(g, V_r, V_c);

    Vector<Integer> V = new Vector<>();
    V.addAll(V_c);
    V.addAll(V_r);
    return getNumOfCols(g,V);
  }

  public static boolean StarBicoloringRestricted
          (GraphModel g, Vector<Integer> V_r, Vector<Integer> V_c) {
    Vector<Integer> forbiddenColors = new Vector<>(V_r.size());
    for(int i=0;i < V_r.size();i++) forbiddenColors.add(-1);
    Vector<Integer> Vertices = new Vector<>(V_r.size() + V_c.size());

    //GraphModel gg = g.getCopy();
    for(int v_r : V_r) Vertices.add(v_r);
    for(int v_c : V_c) Vertices.add(v_c);

    for (int v : Vertices) {
      if (g.getVertex(v).getColor() != 0) {
        forbiddenColors.set(0, v);
        for (Vertex w : g.getNeighbors(g.getVertex(v))) {
          if (w.getColor() <= 0) {
            for (Vertex x : g.getNeighbors(w)) {
              if (x.getColor() > 0) {
                if(g.getEdge(x,w).getWeight()==1 ||
                        g.getEdge(w,g.getVertex(v)).getWeight()==1) {
                  forbiddenColors.set(x.getColor(), v);
                }
              }
            }
          } else {
            for (Vertex x : g.getNeighbors(w)) {
              if(g.getEdge(x,w).getWeight()==1) {
                if (x.getColor() > 0) {
                  for (Vertex y : g.getNeighbors(x)) {
                    if (y.getColor() > 0) {
                      if (w.getColor() == y.getColor() && w.getId() != y.getId()) {
                        forbiddenColors.set(x.getColor(), v);
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }

      for (int i = 0; i < forbiddenColors.size(); i++) {
        if (forbiddenColors.get(i) != v) {
          g.getVertex(v).setColor(i);
        }
      }

    }
    return true;
  }


  public static int StarBicoloringSchemeDynamicOrdering
          (GraphModel g, Vector<Integer> V_r,
                                         Vector<Integer> V_c,
                                         int Mode, int sort, int Mode2) {
    Vector<Integer> IS = new Vector<>();
    Vector<Integer> num_colors = new Vector<>();

    //Compute independent set
    if (Mode == 1) {
      IS = IndSet.ISet(g, V_r, V_c, Mode2); //ISet = IS_Coleman(G_b,V_r,V_c);
    } else if (Mode == 2) {
      IS = IndSet.ISetVariant(g, V_r, V_c, 1.0f);
    } else if (Mode == 3) {
      IS = IndSet.ISetVariant(g, V_r, V_c, 1.5f);
    } else if (Mode == 4) {
      IS = IndSet.ISetVariant(g, V_r, V_c, 2);
    } else if (Mode == 5) {
      IS = IndSet.ISetVariant(g, V_r, V_c, 2.5f);
    } else if (Mode == 6) {
      IS = IndSet.ISetVariant(g, V_r, V_c, 3.0f);
    } else if (Mode == 7) {
      IS = IndSet.ISetVariant(g, V_r, V_c, 3.5f);
    } else if (Mode == 8) {
      IS = IndSet.ISetVariant(g, V_r, V_c, 4.0f);
    } else if (Mode == 9) {
      IS = IndSet.ISetVariant(g, V_r, V_c, 4.5f);
    } else if (Mode == 10) {
      IS = IndSet.ISetVariant(g, V_r, V_c, 5.0f);
    } else if (Mode == 11) {
      IS = IndSet.ISetVariant(g, V_r, V_c, 5.5f);
    }

    //Color vertices in independent set with color 0
    for (int IS_it : IS) {
      g.getVertex(IS_it).setColor(0);
    }

    //Color all vertices which are not colored
    StarBicoloringDynamicOrdering(g, V_r, V_c, sort);
    Vector<Integer> V = new Vector<>();
    V.addAll(V_c);
    V.addAll(V_r);
    return getNumOfCols(g,V);
  }

  public static boolean StarBicoloringDynamicOrdering
          (GraphModel g, Vector<Integer> V_r, Vector<Integer> V_c,
           int Ordering) {
    Vector<Integer> forbiddenColors = new Vector<>(V_r.size());
    for(int i=0; i<V_r.size();i++) forbiddenColors.add(-1);
    Vector<Integer> Vertices = new Vector<>(V_r.size() + V_c.size());

    //GraphModel gg = g.getCopy();
    for (int i = 0; i < V_r.size(); i++) {
      Vertices.add(g.getVertex(i).getId());
    }

    for (int i = 0; i < V_c.size(); i++) {
      Vertices.add(g.getVertex(i).getId());
    }

    if (Ordering !=0){
      if (Ordering == 1){Vertices=OrderingHeuristics.LFO(g,Vertices);}
      if (Ordering == 2){Vertices=OrderingHeuristics.SLO(g,Vertices);}
      if (Ordering == 3){Vertices=OrderingHeuristics.IDO(g,Vertices);}
    }

    for (int v : Vertices) {
      if (g.getVertex(v).getColor() != 0) {
        forbiddenColors.set(0, v);
        for (Vertex w : g.getNeighbors(g.getVertex(v))) {
          if (w.getColor() <= 0) {
            for (Vertex x : g.getNeighbors(w)) {
              if (x.getColor() > 0) forbiddenColors.set(x.getColor(), v);
            }
          } else {
            for (Vertex x : g.getNeighbors(w)) {
              if (x.getColor() > 0) {
                for (Vertex y : g.getNeighbors(x)) {
                  if (y.getColor() > 0) {
                    if (w.getColor() == y.getColor() && w.getId() != y.getId()) {
                      forbiddenColors.set(x.getColor(), v);
                    }
                  }
                }
              }
            }
          }
        }
      }

      for (int i = 0; i < forbiddenColors.size(); i++) {
        if (forbiddenColors.get(i) != v) g.getVertex(v).setColor(i);
      }

    }
    return true;
  }

  public static int StarBicoloringSchemeDynamicOrderingRestricted
          (GraphModel g, Vector<Integer> V_r,
           Vector<Integer> V_c,
           int Mode, int sort, int Mode2) {
    Vector<Integer> IS = new Vector<>();
    Vector<Integer> num_colors = new Vector<>();

    //Compute independent set
    if (Mode == 1) {
      IS = IndSet.ISetRestricted(g, V_r, V_c, Mode2); //ISet = IS_Coleman(G_b,V_r,V_c);
    } else if (Mode == 2) {
      IS = IndSet.ISetVariantRestricted(g, V_r, V_c, 1.0f);
    } else if (Mode == 3) {
      IS = IndSet.ISetVariantRestricted(g, V_r, V_c, 1.5f);
    } else if (Mode == 4) {
      IS = IndSet.ISetVariantRestricted(g, V_r, V_c, 2);
    } else if (Mode == 5) {
      IS = IndSet.ISetVariantRestricted(g, V_r, V_c, 2.5f);
    } else if (Mode == 6) {
      IS = IndSet.ISetVariantRestricted(g, V_r, V_c, 3.0f);
    } else if (Mode == 7) {
      IS = IndSet.ISetVariantRestricted(g, V_r, V_c, 3.5f);
    } else if (Mode == 8) {
      IS = IndSet.ISetVariantRestricted(g, V_r, V_c, 4.0f);
    } else if (Mode == 9) {
      IS = IndSet.ISetVariantRestricted(g, V_r, V_c, 4.5f);
    } else if (Mode == 10) {
      IS = IndSet.ISetVariantRestricted(g, V_r, V_c, 5.0f);
    } else if (Mode == 11) {
      IS = IndSet.ISetVariantRestricted(g, V_r, V_c, 5.5f);
    }

    //Color vertices in independent set with color 0
    for (int IS_it : IS) {
      g.getVertex(IS_it).setColor(0);
    }

    //Color all vertices which are not colored
    StarBicoloringDynamicOrderingRestricted(g, V_r, V_c, sort);
    Vector<Integer> V = new Vector<>();
    V.addAll(V_c);
    V.addAll(V_r);
    return getNumOfCols(g,V);
  }

  public static boolean StarBicoloringDynamicOrderingRestricted
          (GraphModel g, Vector<Integer> V_r, Vector<Integer> V_c,
           int Ordering) {
    Vector<Integer> forbiddenColors = new Vector<>(V_r.size());
    for(int i=0;i<V_r.size();i++) forbiddenColors.add(-1);
    Vector<Integer> Vertices = new Vector<>(V_r.size() + V_c.size());

    //GraphModel gg = g.getCopy();
    for (int i = 0; i < V_r.size(); i++) {
      Vertices.add(g.getVertex(i).getId());
    }

    for (int i = 0; i < V_c.size(); i++) {
      Vertices.add(g.getVertex(i).getId());
    }

    if (Ordering != 0) {
      if (Ordering == 1) {
        Vertices = OrderingHeuristics.LFO(g, Vertices);
      }
      if (Ordering == 2) {
        Vertices = OrderingHeuristics.SLO(g, Vertices);
      }
      if (Ordering == 3) {
        Vertices = OrderingHeuristics.IDO(g, Vertices);
      }
    }

    for (int v : Vertices) {
      if (g.getVertex(v).getColor() != 0) {
        forbiddenColors.set(0, v);
        for (Vertex w : g.getNeighbors(g.getVertex(v))) {
          if (w.getColor() <= 0) {
            for (Vertex x : g.getNeighbors(w)) {
              if (x.getColor() > 0) {
                if (g.getEdge(g.getVertex(v), w).getWeight() == 1 ||
                        g.getEdge(x, w).getWeight() == 1) {
                  forbiddenColors.set(x.getColor(), v);
                }
              }
            }
          } else {
            for (Vertex x : g.getNeighbors(w)) {
              if (g.getEdge(x, w).getWeight() == 1) {
                if (x.getColor() > 0) {
                  for (Vertex y : g.getNeighbors(x)) {
                    if (y.getColor() > 0) {
                      if (w.getColor() == y.getColor() && w.getId() != y.getId()) {
                        forbiddenColors.set(x.getColor(), v);
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }

      for (int i = 0; i < forbiddenColors.size(); i++) {
        if (forbiddenColors.get(i) != v) g.getVertex(v).setColor(i);
      }

    }
    return true;
  }

  /**
   * Compute a star bicoloring of a bipartite graph G_b (this version
   * was implemented for Alexandru Calotoiu's diploma thesis)
   * <p/>
   * Combined means that a vertex is directly colored after its selection
   * <p/>
   * Input:
   * - G_b   bipartite graph
   * - V_r   contained row vertices are colored in the given ordering v_1, ..., v_n
   * - V_c   contained column vertices are colored in the given ordering v_1, ..., v_n
   * - Mode  parameter \rho (\rho=Mode/2)
   * - Mode2 parameter \rho if using independent set algorithm of Coleman (Mode==1)
   * <p/>
   * Output:
   * - G_b bipartite graph with colors given as weights vertex_color
   */
  public static int StarBicoloringSchemeCombinedVertexCoverColoring(GraphModel g,
                                                                    final Vector<Integer> V_rr,
                                                                    final Vector<Integer> V_cc,
                                                                    final int Mode) {
    Vector<Integer> IS = new Vector<>();
    Vector<Integer> V_r = new Vector<>();
    Vector<Integer> V_c = new Vector<>();
    V_r.addAll(V_rr);
    V_c.addAll(V_cc);
    Vector<Integer> num_colors;
    Vector<Integer> V_r_aux = new Vector<>();
    Vector<Integer> V_c_aux = new Vector<>();
    V_r_aux.addAll(V_r);
    V_c_aux.addAll(V_c);
    GraphModel gaux = g.getCopy();
    float ratio = 1;

    if (Mode != 1) ratio = Mode / 2;

    Vector<Pair<Integer, Integer>> Degree_V_r_aux = new Vector<>();
    Vector<Pair<Integer, Integer>> Degree_V_c_aux = new Vector<>();
    Vector<Pair<Integer, Integer>> copy_real_r = new Vector<>();
    Vector<Pair<Integer, Integer>> copy_real_c = new Vector<>();
    Vector<Integer> forbiddenColors = new Vector<>(V_r.size());
    for (int i = 0; i < V_r.size(); i++) {
      forbiddenColors.add(-1);
    }

    Iterator<Integer> v_r_aux_it = V_r_aux.iterator();
    for (int v_r : V_r) {
      if (v_r_aux_it.hasNext()) {
        int v_r_aux = v_r_aux_it.next();
        copy_real_r.add(new Pair<>(v_r, v_r_aux));
      }
    }

    Iterator<Integer> v_c_aux_it = V_c_aux.iterator();
    for (int v_c : V_c) {
      if (v_c_aux_it.hasNext()) {
        int v_c_aux = v_c_aux_it.next();
        copy_real_c.add(new Pair<>(v_c, v_c_aux));
      }
    }

    for (int v_r : V_r_aux) {
      Degree_V_r_aux.add(new Pair<>(v_r, gaux.getDegree(gaux.getVertex(v_r))));
    }

    for (int v_c : V_c_aux) {
      Degree_V_c_aux.add(new Pair<>(v_c, gaux.getDegree(gaux.getVertex(v_c))));
    }

    while (gaux.getEdgesCount() > 0) {
      int max_degree_V_r_aux = 0;
      for (Pair<Integer, Integer> di : Degree_V_r_aux) {
        di.second = gaux.getDegree(gaux.getVertex(di.first));
        int degree_v_r = di.second;
        if (degree_v_r > max_degree_V_r_aux) {
          max_degree_V_r_aux = degree_v_r;
        }
      }

      int max_degree_V_c_aux = 0;
      for (Pair<Integer, Integer> di : Degree_V_c_aux) {
        di.second = gaux.getDegree(gaux.getVertex(di.first));
        int degree_v_c = di.second;
        if (degree_v_c > max_degree_V_c_aux) {
          max_degree_V_c_aux = degree_v_c;
        }
      }

      if (max_degree_V_r_aux > ratio * max_degree_V_c_aux) {
        Vector<Pair<Integer, Integer>> removedEdges = new Vector<>();
        for (Pair<Integer, Integer> di : Degree_V_r_aux) {
          if (max_degree_V_r_aux == di.second) {
            Pair<Integer, Integer> cr = new Pair<>(0, 0);
            for (Pair<Integer, Integer> crtmp : copy_real_r) {
              if (crtmp.second == di.first) {
                cr = crtmp;
                break;
              }
            }
            int v = 0;
            for (int tmp : V_r) {
              if (cr.first == v) {
                v = tmp;
                break;
              }
            }

            IndSet.clearVertex(gaux.getVertex(di.first),gaux);
            removedEdges.add(di);

            forbiddenColors.set(0, v);
            for (Vertex w : g.getNeighbors(g.getVertex(v))) {
              if (w.getColor() <= 0) {
                for (Vertex x : g.getNeighbors(w)) {
                  if (x.getColor() > 0) {
                    forbiddenColors.set(x.getId(), v);
                  }
                }
              } else { //Color[w]>0
                for (Vertex x : g.getNeighbors(w)) {
                  if (x.getColor() > 0) {
                    for (Vertex y : g.getNeighbors(x)) {
                      if (y.getColor() > 0) {
                        if (w.getColor() == y.getColor() && w.getId() != y.getId()) {
                          forbiddenColors.set(x.getId(), v);
                        }
                      }
                    }

                  }
                }
              }
            }
            Degree_V_r_aux.removeAll(removedEdges);

            //Find first color which can be assigned to v_c
            for (int i = 0; i < forbiddenColors.size(); i++) {
              if (forbiddenColors.get(i) != v) {
                g.getVertex(v).setColor(forbiddenColors.get(i));
                break;
              }
            }
          }
        }
      } else {
        Vector<Pair<Integer, Integer>> removedEdges = new Vector<>();
        for (Pair<Integer, Integer> di : Degree_V_c_aux) {
          if (max_degree_V_c_aux == di.second) {
            Pair<Integer, Integer> cr = new Pair<>(0, 0);
            for (Pair<Integer, Integer> crtmp : copy_real_c) {
              if (crtmp.second == di.first) {
                cr = crtmp;
                break;
              }
            }
            int v = 0;
            for (int tmp : V_c) {
              if (cr.first == v) {
                v = tmp;
                break;
              }
            }

            IndSet.clearVertex(gaux.getVertex(di.first),gaux);
            removedEdges.add(di);

            forbiddenColors.set(0, v);
            for (Vertex w : g.getNeighbors(g.getVertex(v))) {
              if (w.getColor() <= 0) {
                for (Vertex x : g.getNeighbors(w)) {
                  if (x.getColor() > 0) {
                    forbiddenColors.set(x.getId(), v);
                  }
                }
              } else { //Color[w]>0
                for (Vertex x : g.getNeighbors(w)) {
                  if (x.getColor() > 0) {
                    for (Vertex y : g.getNeighbors(x)) {
                      if (y.getColor() > 0) {
                        if (w.getColor() == y.getColor() && w.getId() != y.getId()) {
                          forbiddenColors.set(x.getId(), v);
                        }
                      }
                    }
                  }
                }
              }
            }
            //Find first color which can be assigned to v_c
            for (int i = 0; i < forbiddenColors.size(); i++) {
              if (forbiddenColors.get(i) != v) {
                g.getVertex(v).setColor(forbiddenColors.get(i));
                break;
              }
            }
          }
        }
        Degree_V_c_aux.removeAll(removedEdges);
      }
    }
    for (Pair<Integer, Integer> di : Degree_V_r_aux) {
      IS.add(di.first);
    }
    for (Pair<Integer, Integer> di : Degree_V_c_aux) {
      IS.add(di.first);
    }

    for (int i = 0; i < IS.size(); i++) {
      g.getVertex(IS.get(i)).setColor(0);
    }


    Vector<Integer> V = new Vector<>();
    V.addAll(V_c);
    V.addAll(V_r);
    return getNumOfCols(g,V);
  }

  public static int StarBicoloringSchemeCombinedVertexCoverColoringRestricted(GraphModel g,
                                                                              final Vector<Integer> V_rr,
                                                                              final Vector<Integer> V_cc,
                                                                              final int Mode) {
    Vector<Integer> IS = new Vector<>();
    Vector<Integer> V_r = new Vector<>();
    Vector<Integer> V_c = new Vector<>();
    V_r.addAll(V_rr);
    V_c.addAll(V_cc);
    Vector<Integer> V_r_aux = new Vector<>();
    Vector<Integer> V_c_aux = new Vector<>();
    V_r_aux.addAll(V_r);
    V_c_aux.addAll(V_c);
    GraphModel gaux = g.getCopy();
    float ratio = 1;

    if (Mode != 1) ratio = Mode / 2;

    Vector<Pair<Integer, Integer>> Degree_V_r_aux = new Vector<>();
    Vector<Pair<Integer, Integer>> Degree_V_c_aux = new Vector<>();
    Vector<Pair<Integer, Integer>> copy_real_r = new Vector<>();
    Vector<Pair<Integer, Integer>> copy_real_c = new Vector<>();
    Vector<Integer> forbiddenColors = new Vector<>(V_r.size());
    for (int i = 0; i < V_r.size(); i++) {
      forbiddenColors.add(-1);
    }

    Iterator<Integer> v_r_aux_it = V_r_aux.iterator();
    for (int v_r : V_r) {
      if (v_r_aux_it.hasNext()) {
        int v_r_aux = v_r_aux_it.next();
        copy_real_r.add(new Pair<>(v_r, v_r_aux));
      }
    }

    Iterator<Integer> v_c_aux_it = V_c_aux.iterator();
    for (int v_c : V_c) {
      if (v_c_aux_it.hasNext()) {
        int v_c_aux = v_c_aux_it.next();
        copy_real_c.add(new Pair<>(v_c, v_c_aux));
      }
    }

    for (int v_r : V_r_aux) {
      Degree_V_r_aux.add(new Pair<>(v_r, gaux.getDegree(gaux.getVertex(v_r))));
    }

    for (int v_c : V_c_aux) {
      Degree_V_c_aux.add(new Pair<>(v_c, gaux.getDegree(gaux.getVertex(v_c))));
    }

    while (gaux.getEdgesCount() > 0) {
      int max_degree_V_r_aux = 0;
      for (Pair<Integer, Integer> di : Degree_V_r_aux) {
        di.second = gaux.getDegree(gaux.getVertex(di.first));
        int degree_v_r = di.second;
        if (degree_v_r > max_degree_V_r_aux) {
          max_degree_V_r_aux = degree_v_r;
        }
      }

      int max_degree_V_c_aux = 0;
      for (Pair<Integer, Integer> di : Degree_V_c_aux) {
        di.second = gaux.getDegree(gaux.getVertex(di.first));
        int degree_v_c = di.second;
        if (degree_v_c > max_degree_V_c_aux) {
          max_degree_V_c_aux = degree_v_c;
        }
      }

      if (max_degree_V_r_aux > ratio * max_degree_V_c_aux) {
        Vector<Pair<Integer, Integer>> removedEdges = new Vector<>();
        for (Pair<Integer, Integer> di : Degree_V_r_aux) {
          if (max_degree_V_r_aux == di.second) {
            Pair<Integer, Integer> cr = new Pair<>(0, 0);
            for (Pair<Integer, Integer> crtmp : copy_real_r) {
              if (crtmp.second.equals(di.first)) {
                cr = crtmp;
                break;
              }
            }
            int v = 0;
            for (int tmp : V_r) {
              if (cr.first == v) {
                v = tmp;
                break;
              }
            }

            IndSet.clearVertex(gaux.getVertex(di.first),gaux);
            removedEdges.add(di);
            forbiddenColors.set(0, v);
            for (Vertex w : g.getNeighbors(g.getVertex(v))) {
              if (w.getColor() <= 0) {
                for (Vertex x : g.getNeighbors(w)) {
                  if (x.getColor() > 0) {
                    if (g.getEdge(w, g.getVertex(v)).getWeight() == 1 ||
                            g.getEdge(w, x).getWeight() == 1) {
                      forbiddenColors.set(x.getId(), v);
                    }
                  }
                }
              } else { //Color[w]>0
                for (Vertex x : g.getNeighbors(w)) {
                  if (x.getColor() > 0) {
                    if (g.getEdge(w, x).getWeight() == 1) {
                      for (Vertex y : g.getNeighbors(x)) {
                        if (y.getColor() > 0) {
                          if (w.getColor() == y.getColor() && w.getId() != y.getId()) {
                            forbiddenColors.set(x.getId(), v);
                          }
                        }
                      }
                    }
                  }
                }
              }
            }

            //Find first color which can be assigned to v_c
            for (int i = 0; i < forbiddenColors.size(); i++) {
              if (forbiddenColors.get(i) != v) {
                g.getVertex(v).setColor(forbiddenColors.get(i));
                break;
              }
            }
          }
        }
        Degree_V_r_aux.removeAll(removedEdges);
      } else {
        Vector<Pair<Integer, Integer>> removedEdges = new Vector<>();
        for (Pair<Integer, Integer> di : Degree_V_c_aux) {
          if (max_degree_V_c_aux == di.second) {
            Pair<Integer, Integer> cr = new Pair<>(0, 0);
            for (Pair<Integer, Integer> crtmp : copy_real_c) {
              if (crtmp.second == di.first) {
                cr = crtmp;
                break;
              }
            }
            int v = 0;
            for (int tmp : V_c) {
              if (cr.first == v) {
                v = tmp;
                break;
              }
            }

            IndSet.clearVertex(gaux.getVertex(di.first),gaux);
            removedEdges.add(di);

            forbiddenColors.set(0, v);
            for (Vertex w : g.getNeighbors(g.getVertex(v))) {
              if (w.getColor() <= 0) {
                for (Vertex x : g.getNeighbors(w)) {
                  if (x.getColor() > 0) {
                    if (g.getEdge(w, g.getVertex(v)).getWeight() == 1 ||
                            g.getEdge(w, x).getWeight() == 1) {
                      forbiddenColors.set(x.getId(), v);
                    }

                  }
                }
              } else { //Color[w]>0
                for (Vertex x : g.getNeighbors(w)) {
                  if (x.getColor() > 0) {
                    if (g.getEdge(w, x).getWeight() == 1) {
                      for (Vertex y : g.getNeighbors(x)) {
                        if (y.getColor() > 0) {
                          if (w.getColor() == y.getColor() && w.getId() != y.getId()) {
                            forbiddenColors.set(x.getId(), v);
                          }
                        }
                      }
                    }
                  }
                }
              }
            }

            //Find first color which can be assigned to v_c
            for (Integer forbiddenColor : forbiddenColors) {
              if (forbiddenColor != v) {
                g.getVertex(v).setColor(forbiddenColor);
                break;
              }
            }
          }
        }
        Degree_V_c_aux.removeAll(removedEdges);
      }
    }
    IS.addAll(Degree_V_r_aux.stream().map(di -> di.first).collect(Collectors.toList()));
    IS.addAll(Degree_V_c_aux.stream().map(di -> di.first).collect(Collectors.toList()));

    for (Integer I : IS) {
      g.getVertex(I).setColor(0);
    }


    Vector<Integer> V = new Vector<>();
    V.addAll(V_c);
    V.addAll(V_r);
    return getNumOfCols(g,V);
  }
}
