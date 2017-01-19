// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.clique;


import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.SubGraph;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * @author Ali Rostami
 */

@CommandAttitude(name = "mst_prim", abbreviation = "_max_c")
public class MaxCliqueExtension implements GraphReportExtension {
    public String getName() {
        return "Maximal Cliques";
    }

    public String getDescription() {
        return "Maximal Cliques";
    }

    public Object calculate(GraphModel g) {
        Vector<SubGraph> ret = new Vector<>();
        MaxCliqueAlg mca = new MaxCliqueAlg(g);
        Cliques mcs = mca.allMaxCliques();
        for(Vector<Vertex> ss : mcs) {
            SubGraph sg = new SubGraph(g);
            sg.vertices.addAll(ss);
            ret.add(sg);
        }
        return ret;
    }

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "General";
	}

}

/**
 *
 * Bron-Kerbosch clique detection algorithm
 *
 * Samudrala R.,Moult J.:A Graph-theoretic Algorithm for
 * comparative Modeling of Protein Structure; J.Mol. Biol. (1998); vol 279; pp.
 * 287-302
 */
class MaxCliqueAlg
{
    public MaxCliqueAlg(GraphModel g) {
        this.g = g;
    }

    public Cliques allMaxCliques()
    {
        maxCliques = new Cliques();
        List<Vertex> likelyC = new ArrayList<>();
        List<Vertex> searchC = new ArrayList<>();
        List<Vertex> found = new ArrayList<>();
        for(Vertex v : g) {searchC.add(v);}
        cliques(likelyC, searchC, found);
        return maxCliques;
    }

    public Cliques GreatestOne()
    {
        allMaxCliques();

        int maximum = 0;
        Cliques greatest = new Cliques();
        for (Vector<Vertex> clique : maxCliques) {
            if (maximum < clique.size()) {
                maximum = clique.size();
            }
        }
        for (Vector<Vertex> clique : maxCliques) {
            if (maximum == clique.size()) {
                greatest.add(clique);
            }
        }
        return greatest;
    }

    private void cliques(
            List<Vertex> likelyC,
            List<Vertex> C,
            List<Vertex> F)
    {
        List<Vertex> candidates_array = new ArrayList<>(C);
        if (!allEdgesSeen(C, F)) {
            for (Vertex candidate : candidates_array) {
                List<Vertex> new_candidates = new ArrayList<>();
                List<Vertex> new_already_found = new ArrayList<>();
                likelyC.add(candidate);
                C.remove(candidate);
                new_candidates.addAll(C.stream().filter(new_candidate -> g.isEdge(candidate, new_candidate)).collect(Collectors.toList()));
                new_already_found.addAll(F.stream().filter(new_found -> g.isEdge(candidate, new_found)).collect(Collectors.toList()));
                if (new_candidates.isEmpty() && new_already_found.isEmpty()) {
                    maxCliques.add(new Vector<>(likelyC));
                }
                else {
                    cliques(
                            likelyC,
                            new_candidates,
                            new_already_found);
                }

                F.add(candidate);
                likelyC.remove(candidate);
            }
        }
    }

    private boolean allEdgesSeen(List<Vertex> candidates, List<Vertex> already_found)
    {
        boolean end = false;
        int numOfEdges;
        for (Vertex found : already_found) {
            numOfEdges = 0;
            for (Vertex candidate : candidates) {
                if (g.isEdge(found, candidate)) {
                    numOfEdges++;
                }
            }
            if (numOfEdges == candidates.size()) {
                end = true;
            }
        }
        return end;
    }

    private final GraphModel g;
    private Cliques maxCliques;

}

class Cliques extends Vector<Vector<Vertex>> {}
