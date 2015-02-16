package graphtea.extensions.reports.zagreb;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.Vertex;
import graphtea.plugins.main.GraphData;

import java.util.ArrayList;

/**
 * Created by rostam on 27.01.15.
 * Class containg the functions for computation of Zagreb indices
 */
public class ZagrebIndexFunctions {
    GraphData gd;

    public ZagrebIndexFunctions(GraphData gd) {
        this.gd = gd;
    }

    public double getFirstZagreb(double alpha) {
        double first_zagreb = 0;
        for (Vertex v : gd.getGraph().vertices()) {
            first_zagreb += Math.pow(gd.getGraph().getDegree(v), alpha + 1);
        }
        return first_zagreb;
    }

    public double getSecondZagreb(double alpha) {
        double second_zagreb = 0;
        for (Edge e : gd.getGraph().getEdges()) {
            second_zagreb +=
                    Math.pow(
                            gd.getGraph().getDegree(e.source) *
                                    gd.getGraph().getDegree(e.target), alpha);
        }

        return second_zagreb;
    }

    public double getFirstReZagreb(double alpha) {
        double first_re_zagreb = 0;
        for (Edge e : gd.getGraph().getEdges()) {

            int d = gd.getGraph().getDegree(e.source) +
                    gd.getGraph().getDegree(e.target) - 2;

            first_re_zagreb += Math.pow(d, alpha + 1);
        }
        return first_re_zagreb;
    }

    double getSecondReZagreb(double alpha) {
        double second_re_zagreb = 0;
        ArrayList<Edge> eds = new ArrayList<Edge>();
        for (Edge ee : gd.getGraph().getEdges()) {
            eds.add(ee);
        }
        for (Edge e1 : eds) {
            for (Edge e2 : eds) {
                if (edge_adj(e1, e2)) {
                    int d1 = gd.getGraph().getDegree(e1.source) +
                            gd.getGraph().getDegree(e1.target) - 2;

                    int d2 = gd.getGraph().getDegree(e2.source) +
                            gd.getGraph().getDegree(e2.target) - 2;

                    second_re_zagreb += Math.pow(d1 * d2, alpha);
                }
            }
        }

        second_re_zagreb /= 2;
        return second_re_zagreb;
    }

    double getFirstZagrebCoindex(double alpha) {
        double first_zagreb = 0;

        for (Vertex v1 : gd.getGraph().getVertexArray()) {
            for (Vertex v2 : gd.getGraph().getVertexArray()) {
                if (v1.getId() != v2.getId()) {
                    if (!gd.getGraph().isEdge(v1, v2)) {
                        first_zagreb += Math.pow(gd.getGraph().getDegree(v1), alpha - 1) +
                                Math.pow(gd.getGraph().getDegree(v2), alpha - 1);
                    }
                }
            }
        }


        return first_zagreb;
    }

    double getSecondZagrebCoindex(double alpha) {
        double second_zagreb = 0;

        for (Vertex v1 : gd.getGraph().getVertexArray()) {
            for (Vertex v2 : gd.getGraph().getVertexArray()) {
                if (v1.getId() != v2.getId()) {
                    if (!gd.getGraph().isEdge(v1, v2)) {
                        second_zagreb += Math.pow(gd.getGraph().getDegree(v1) *
                                gd.getGraph().getDegree(v2), alpha);

                    }
                }
            }
        }


        return second_zagreb;
    }

    public double getFirstZagrebSelectedEdges(double alpha) {
        double first_zagreb = 0;
        for (Vertex v : gd.getGraph().vertices()) {
            for (Vertex nv : gd.getGraph().getNeighbors(v))
                if (gd.getGraph().getEdge(v, nv).isSelected()) {
                    first_zagreb += Math.pow(gd.getGraph().getDegree(v), alpha + 1);
                    break;
                }
        }
        return first_zagreb;
    }

    public double getSecondZagrebSelectedEdges(double alpha) {
        double second_zagreb = 0;
        for (Edge e : gd.getGraph().getEdges()) {
            if (e.isSelected()) {
                second_zagreb +=
                        Math.pow(
                                gd.getGraph().getDegree(e.source) *
                                        gd.getGraph().getDegree(e.target), alpha);
            }
        }
        return second_zagreb;
    }

    public double getFirstReZagrebSelectedEdges(double alpha) {
        double first_re_zagreb = 0;
        for (Edge e : gd.getGraph().getEdges()) {
            if (e.isSelected()) {
                int d = gd.getGraph().getDegree(e.source) +
                        gd.getGraph().getDegree(e.target) - 2;

                first_re_zagreb += Math.pow(d, alpha + 1);
            }
        }
        return first_re_zagreb;
    }

    double getSecondReZagrebSelectedEdges(double alpha) {
        double second_re_zagreb = 0;
        ArrayList<Edge> eds = new ArrayList<Edge>();
        for (Edge ee : gd.getGraph().getEdges()) {
            eds.add(ee);
        }
        for (Edge e1 : eds) {
            for (Edge e2 : eds) {
                if (e1.isSelected() && e2.isSelected()) {
                    if (edge_adj(e1, e2)) {
                        int d1 = gd.getGraph().getDegree(e1.source) +
                                gd.getGraph().getDegree(e1.target) - 2;

                        int d2 = gd.getGraph().getDegree(e2.source) +
                                gd.getGraph().getDegree(e2.target) - 2;

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
        for (Vertex v1 : gd.getGraph().getVertexArray()) {
            for (Vertex v2 : gd.getGraph().getVertexArray()) {

                for(Vertex nv1 : gd.getGraph().getNeighbors(v1)) {
                    if(gd.getGraph().getEdge(v1,nv1).isSelected()) {
                        cond1 = true;
                        break;
                    }
                }

                for(Vertex nv2 : gd.getGraph().getNeighbors(v2)) {
                    if(gd.getGraph().getEdge(v2,nv2).isSelected()) {
                        cond2 = true;
                        break;
                    }
                }

                if(cond1 && cond2) {
                    if (v1.getId() != v2.getId()) {
                        if (!gd.getGraph().isEdge(v1, v2)) {
                            first_zagreb += Math.pow(gd.getGraph().getDegree(v1), alpha - 1) +
                                    Math.pow(gd.getGraph().getDegree(v2), alpha - 1);
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
        for (Vertex v1 : gd.getGraph().getVertexArray()) {
            for (Vertex v2 : gd.getGraph().getVertexArray()) {
                for (Vertex nv1 : gd.getGraph().getNeighbors(v1)) {
                    if (gd.getGraph().getEdge(v1, nv1).isSelected()) {
                        cond1 = true;
                        break;
                    }
                }

                for (Vertex nv2 : gd.getGraph().getNeighbors(v2)) {
                    if (gd.getGraph().getEdge(v2, nv2).isSelected()) {
                        cond2 = true;
                        break;
                    }
                }
                if (cond1 && cond2) {
                    if (v1.getId() != v2.getId()) {
                        if (!gd.getGraph().isEdge(v1, v2)) {
                            second_zagreb += Math.pow(gd.getGraph().getDegree(v1) *
                                    gd.getGraph().getDegree(v2), alpha);

                        }
                    }
                }
            }
        }

        second_zagreb /= 2;
        return second_zagreb;
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
