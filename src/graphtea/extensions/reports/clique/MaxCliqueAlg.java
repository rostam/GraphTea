package graphtea.extensions.reports.clique;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

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

    public Vector<Vector<Vertex>> allMaxCliques()
    {
        maxCliques = new Vector<Vector<Vertex>>();
        List<Vertex> likelyC = new ArrayList<>();
        List<Vertex> searchC = new ArrayList<>();
        List<Vertex> found = new ArrayList<>();
        for(Vertex v : g) {searchC.add(v);}
        cliques(likelyC, searchC, found);
        return maxCliques;
    }

    public Vector<Vector<Vertex>> GreatestOne()
    {
        allMaxCliques();

        int maximum = 0;
        Vector<Vector<Vertex>> greatest = new Vector<Vector<Vertex>>();
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
                likelyC.add(candidate);
                C.remove(candidate);
                List<Vertex> new_candidates = C.stream().filter(new_candidate -> g.isEdge(candidate, new_candidate)).collect(Collectors.toList());
                List<Vertex> new_already_found = F.stream().filter(new_found -> g.isEdge(candidate, new_found)).collect(Collectors.toList());
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
    private Vector<Vector<Vertex>> maxCliques;

}


