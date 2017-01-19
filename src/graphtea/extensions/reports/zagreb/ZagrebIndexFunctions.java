package graphtea.extensions.reports.zagreb;
import graphtea.extensions.Utils;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.library.algorithms.util.LibraryUtils;

import java.util.ArrayList;

/**
 * Created by rostam on 27.01.15.
 * Class containg the functions for computation of Zagreb indices
 */
public class ZagrebIndexFunctions {
    GraphModel g;

    public ZagrebIndexFunctions(GraphModel g) {
        this.g = g;
    }

    public double getInverseSumIndegIndex() {
        double ret = 0;
        for(Edge e : g.getEdges()) {
            ret += (g.getDegree(e.source)*g.getDegree(e.target)*1.0)
                    /(g.getDegree(e.source) + g.getDegree(e.target));
        }
        return ret;
    }

    public double getEdgeDegree(double alpha) {
        double edge_degree = 0;
        for (Edge e : g.getEdges()) {
            edge_degree +=
                    Math.pow(
                            g.getDegree(e.source) +
                                    g.getDegree(e.target) - 2, alpha);
        }

        return edge_degree;
    }


    public double getHyperZagrebIndex() {
        double hz = 0;
        for(Edge e : g.getEdges()) {
            hz += Math.pow(
                    g.getDegree(e.source) + g.getDegree(e.target)
                    ,2);
        }
        return hz;
    }

    public double getFirstZagreb(double alpha) {
        double first_zagreb = 0;
        for (Vertex v : g.vertices()) {
            first_zagreb += Math.pow(g.getDegree(v), alpha + 1);
        }
        return first_zagreb;
    }

    public double getThirdZagreb() {
        double ret = 0;
        for (Edge e : g.getEdges()) {
            ret += Math.abs(g.getDegree(e.source) -
                    g.getDegree(e.target));
        }

        return ret;
    }


    public double getSecondZagreb(double alpha) {
        double second_zagreb = 0;
        for (Edge e : g.getEdges()) {
            second_zagreb +=
                    Math.pow(
                            g.getDegree(e.source) *
                                    g.getDegree(e.target), alpha);
        }

        return second_zagreb;
    }

    public double getFirstReZagreb(double alpha) {
        double first_re_zagreb = 0;
        for (Edge e : g.getEdges()) {

            int d = g.getDegree(e.source) +
                    g.getDegree(e.target) - 2;

            first_re_zagreb += Math.pow(d, alpha + 1);
        }
        return first_re_zagreb;
    }

    public double getFirstReZagrebCoindex(double alpha) {
        double ret = 0;
        if(g.getEdgesCount()==1) return ret;
        GraphModel lg = Utils.createLineGraph(g);
        GraphModel clg = (GraphModel) LibraryUtils.complement(lg);

        for (Edge e : clg.getEdges()) {
            int v1 = lg.getDegree(lg.getVertex(e.source.getId()));
            int v2 = lg.getDegree(lg.getVertex(e.target.getId()));
            ret += Math.pow(v1, alpha) + Math.pow(v2, alpha);
        }

        return ret;
    }


    public double getSecondReZagrebCoindex(double alpha) {
        double ret = 0;
        if(g.getEdgesCount()==1) return ret;
        GraphModel lg = Utils.createLineGraph(g);
        GraphModel clg = (GraphModel) LibraryUtils.complement(lg);

        for (Edge e : clg.getEdges()) {
            int v1 = lg.getDegree(lg.getVertex(e.source.getId()));
            int v2 = lg.getDegree(lg.getVertex(e.target.getId()));
            ret += Math.pow(v1*v2, alpha);
        }

        return ret;
    }

    double getSecondReZagreb(double alpha) {
        double second_re_zagreb = 0;
        ArrayList<Edge> eds = new ArrayList<Edge>();
        for (Edge ee : g.getEdges()) {
            eds.add(ee);
        }
        for (Edge e1 : eds) {
            for (Edge e2 : eds) {
                if (edge_adj(e1, e2)) {
                    int d1 = g.getDegree(e1.source) +
                            g.getDegree(e1.target) - 2;

                    int d2 = g.getDegree(e2.source) +
                            g.getDegree(e2.target) - 2;

                    second_re_zagreb += Math.pow(d1 * d2, alpha);
                }
            }
        }

        second_re_zagreb /= 2;
        return second_re_zagreb;
    }

    double getFirstZagrebCoindex(double alpha) {
        double first_zagreb = 0;

        GraphModel g2 = (GraphModel) LibraryUtils.complement(g);
        for (Edge e : g2.getEdges()) {
            int v1 = g.getDegree(g.getVertex(e.source.getId()));
            int v2 = g.getDegree(g.getVertex(e.target.getId()));
            first_zagreb += Math.pow(v1, alpha) + Math.pow(v2, alpha);
        }

        return first_zagreb;
    }

    double getSecondZagrebCoindex(double alpha) {
        double second_zagreb = 0;

        GraphModel g2 = (GraphModel) LibraryUtils.complement(g);

        for (Edge e : g2.getEdges()) {
            int v1 = g.getDegree(g.getVertex(e.source.getId()));
            int v2 = g.getDegree(g.getVertex(e.target.getId()));
            second_zagreb += Math.pow(v1*v2, alpha);
        }

        return second_zagreb;
    }

    public double getFirstZagrebSelectedEdges(double alpha) {
        double first_zagreb = 0;
        for (Vertex v : g.vertices()) {
            for (Vertex nv : g.getNeighbors(v))
                if (g.getEdge(v, nv).isSelected()) {
                    first_zagreb += Math.pow(g.getDegree(v), alpha + 1);
                    break;
                }
        }
        return first_zagreb;
    }

    public double getSecondZagrebSelectedEdges(double alpha) {
        double second_zagreb = 0;
        for (Edge e : g.getEdges()) {
            if (e.isSelected()) {
                second_zagreb +=
                        Math.pow(
                                g.getDegree(e.source) *
                                        g.getDegree(e.target), alpha);
            }
        }
        return second_zagreb;
    }

    public double getFirstReZagrebSelectedEdges(double alpha) {
        double first_re_zagreb = 0;
        for (Edge e : g.getEdges()) {
            if (e.isSelected()) {
                int d = g.getDegree(e.source) +
                        g.getDegree(e.target) - 2;

                first_re_zagreb += Math.pow(d, alpha + 1);
            }
        }
        return first_re_zagreb;
    }

    double getSecondReZagrebSelectedEdges(double alpha) {
        double second_re_zagreb = 0;
        ArrayList<Edge> eds = new ArrayList<Edge>();
        for (Edge ee : g.getEdges()) {
            eds.add(ee);
        }
        for (Edge e1 : eds) {
            for (Edge e2 : eds) {
                if (e1.isSelected() && e2.isSelected()) {
                    if (edge_adj(e1, e2)) {
                        int d1 = g.getDegree(e1.source) +
                                g.getDegree(e1.target) - 2;

                        int d2 = g.getDegree(e2.source) +
                                g.getDegree(e2.target) - 2;

                        second_re_zagreb += Math.pow(d1 * d2, alpha);
                    }
                }
            }
        }

        second_re_zagreb /= 2;
        return second_re_zagreb;
    }

    double getFirstZagrebCoindexSelectedEdges(double alpha) {
        double first_zagreb = 0;

        boolean cond1 = false, cond2=false;
        for (Vertex v1 : g.getVertexArray()) {
            for (Vertex v2 : g.getVertexArray()) {

                for(Vertex nv1 : g.getNeighbors(v1)) {
                    if(g.getEdge(v1,nv1).isSelected()) {
                        cond1 = true;
                        break;
                    }
                }

                for(Vertex nv2 : g.getNeighbors(v2)) {
                    if(g.getEdge(v2,nv2).isSelected()) {
                        cond2 = true;
                        break;
                    }
                }

                if(cond1 && cond2) {
                    if (v1.getId() != v2.getId()) {
                        if (!g.isEdge(v1, v2)) {
                            first_zagreb += Math.pow(g.getDegree(v1), alpha) +
                                    Math.pow(g.getDegree(v2), alpha);
                        }
                    }
                }
            }
        }

        first_zagreb /= 2;

        return first_zagreb;
    }

    double getSecondZagrebCoindexSelectedEdges(double alpha) {
        double second_zagreb = 0;

        boolean cond1 = false, cond2 = false;
        for (Vertex v1 : g.getVertexArray()) {
            for (Vertex v2 : g.getVertexArray()) {
                for (Vertex nv1 : g.getNeighbors(v1)) {
                    if (g.getEdge(v1, nv1).isSelected()) {
                        cond1 = true;
                        break;
                    }
                }

                for (Vertex nv2 : g.getNeighbors(v2)) {
                    if (g.getEdge(v2, nv2).isSelected()) {
                        cond2 = true;
                        break;
                    }
                }
                if (cond1 && cond2) {
                    if (v1.getId() != v2.getId()) {
                        if (!g.isEdge(v1, v2)) {
                            second_zagreb += Math.pow(g.getDegree(v1) *
                                    g.getDegree(v2), alpha);

                        }
                    }
                }
            }
        }

        second_zagreb /= 2;
        return second_zagreb;
    }

    public double getFirstVariableZagrebIndex(double alpha) {
        double ret = 0;
        for(Vertex v : g) {
            ret += Math.pow(g.getDegree(v),
                    2*alpha);
        }
        return ret;
    }

    public double getSecondVariableZagrebIndex(double alpha) {
        double ret = 0;
        for(Edge e : g.getEdges()) {
            double degs = g.getDegree(e.source);
            double degt = g.getDegree(e.target);
            ret+= Math.pow(degs*degt,alpha);
        }
        return ret;
    }

    public double getSecondMixZagrebIndex(double alpha, double beta) {
        double ret=0;
        for(Edge e : g.getEdges()) {
            ret+=Math.pow(g.getDegree(e.source),alpha)*Math.pow(g.getDegree(e.target),beta) +
                    Math.pow(g.getDegree(e.source),beta)*Math.pow(g.getDegree(e.target),alpha);
        }
        return ret;
    }


    public double getGeneralSumConnectivityIndex(double alpha) {
        double ret = 0;
        for (Edge e : g.getEdges()) {
            ret+=Math.pow(g.getDegree(e.source)+g.getDegree(e.target),alpha);
        }
        return ret;
    }

    public double getHarmonicIndex() {
        double ret = 0;
        for (Edge e : g.getEdges()) {
            ret+=2./(g.getDegree(e.source)+g.getDegree(e.target));
        }
        return ret;
    }


    public double getFirstPathZagrebIndex(double alpha) {
        double ret = 0;
        GraphModel g2 = Utils.createLineGraph(g);
        for(Edge e : g2.getEdges()) {
            Vertex src = e.source;
            Vertex tgt = e.target;
            Edge e1= (Edge) src.getProp().obj;
            Edge e2= (Edge) tgt.getProp().obj;
            if(e1.source.getId()==e2.source.getId()) {
                ret+=Math.pow(g.getDegree(e1.target),alpha-1);
                ret+=Math.pow(g.getDegree(e2.target),alpha-1);
            }

            if(e1.target.getId()==e2.source.getId()) {
                ret += Math.pow(g.getDegree(e1.source), alpha - 1);
                ret += Math.pow(g.getDegree(e2.target), alpha - 1);
            }

            if(e1.source.getId()==e2.target.getId()) {
                ret+=Math.pow(g.getDegree(e1.target),alpha-1);
                ret+=Math.pow(g.getDegree(e2.source),alpha-1);
            }

            if(e1.target.getId()==e2.target.getId()) {
                ret+=Math.pow(g.getDegree(e1.source),alpha-1);
                ret+=Math.pow(g.getDegree(e2.source),alpha-1);
            }
        }
        return ret;
    }

    public double getSecondPathZagrebIndex(double alpha) {
        double ret = 0;
        GraphModel g2 = Utils.createLineGraph(g);
        for(Edge e : g2.getEdges()) {
            Vertex src = e.source;
            Vertex tgt = e.target;
            Edge e1= (Edge) src.getProp().obj;
            Edge e2= (Edge) tgt.getProp().obj;
            if(e1.source.getId()==e2.source.getId()) {
                ret+=Math.pow(g.getDegree(e1.target)*g.getDegree(e2.target),alpha);
            }

            if(e1.target.getId()==e2.source.getId()) {
                ret += Math.pow(g.getDegree(e1.source)*g.getDegree(e2.target),alpha);
            }

            if(e1.source.getId()==e2.target.getId()) {
                ret+=Math.pow(g.getDegree(e1.target)*g.getDegree(e2.source),alpha);
            }

            if(e1.target.getId()==e2.target.getId()) {
                ret+=Math.pow(g.getDegree(e1.source)*g.getDegree(e2.source),alpha);
            }
        }
        return ret;
    }

    private boolean edge_adj(Edge e1,Edge e2) {
        if(e1.source.getId()==e2.source.getId()  &&
                e1.target.getId()==e2.target.getId()) return false;
        else if(e1.target.getId()==e2.source.getId() &&
                e1.source.getId()==e2.target.getId()) return false;
        else if(e1.source.getId() == e2.source.getId()) return true;
        else if(e1.source.getId() == e2.target.getId()) return true;
        else if(e1.target.getId() == e2.source.getId()) return true;
        else if(e1.target.getId() == e2.target.getId()) return true;
        return false;
    }
}