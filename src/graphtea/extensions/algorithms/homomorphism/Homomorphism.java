// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.extensions.algorithms.homomorphism;

import graphtea.extensions.generators.CompleteGraphGenerator;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

import java.security.InvalidParameterException;
import java.util.*;

/**
 * @author Soroush Sabet, Ali Rostami
 */
public class Homomorphism {

    private final GraphModel domain;
    private final GraphModel range;
    HashMap<Vertex, Vertex> homomorphism = new HashMap<>();

    Homomorphism(GraphModel domain, GraphModel range,
                 HashMap<Vertex, Vertex> map) {
        this.domain = domain;
        this.range = range;
        homomorphism.putAll(map);
    }

    //

    /**
     * Building Homomorphism from coloring
     *
     * @param domain      The domain graph
     * @param colors      The coloring as a list of integers
     * @param numOfColors the number of colors
     */
    Homomorphism(GraphModel domain, Vector<Integer> colors, int numOfColors) {
        if (Collections.max(colors) != numOfColors) {
            throw new InvalidParameterException("The given number of colors is not equal" +
                    "with the real existing number of colors inside the vector colors.");
        }
        this.domain = domain;
        this.range = CompleteGraphGenerator.generateCompleteGraph(numOfColors);
        for (int i = 0; i < colors.size(); i++) {
            homomorphism.put(this.domain.getVertex(i), this.range.getVertex(colors.get(i)));
        }
    }

    /**
     * Building Homomorphism from coloring
     *
     * @param domain      The domain graph with colors already are set
     *                    in the graph
     * @param numOfColors the number of colors
     */
    Homomorphism(GraphModel domain, int numOfColors) {
        this.domain = domain;
        this.range = CompleteGraphGenerator.generateCompleteGraph(numOfColors);
        for (Vertex v : this.domain) {
            homomorphism.put(v, this.range.getVertex(v.getColor()));
        }
    }

    public GraphModel getDomain() {
        return domain;
    }

    public GraphModel getRange() {
        return range;
    }

    public HashMap<Vertex, Vertex> getHomomorphism() {
        return homomorphism;
    }

    /**
     * checks if the given homomorphism function is an onto map
     *
     * @return true if the given homomorphism function is an onto map
     */
    public boolean isHomomorphismOnto() {
        HashSet<Integer> hs = new HashSet<>();
        for (Vertex key : homomorphism.keySet()) {
            hs.add(homomorphism.get(key).getId());
        }
        return hs.size() == this.range.getVerticesCount();
    }

    /**
     * Produce the quotient graph of this homomorphism,
     * the homomorphism map should be onto
     *
     * @return the quotient graph of this homomorphism
     */
    public GraphModel getQuotient() {
        GraphModel quotient = new GraphModel();
        for (Vertex v : this.range) {
            Vertex u = new Vertex();
            u.getProp().obj = new Vector<Integer>();
            quotient.addVertex(u);
        }

        for (Vertex key : homomorphism.keySet()) {
            Vertex value = homomorphism.get(key);
            ((Vector<Integer>) quotient.getVertex(value.getId()).getProp().obj).add(key.getId());
        }

        for (Vertex v : quotient) {
            for (Vertex u : quotient) {
                if (this.domain.isEdge(this.domain.getVertex(v.getId()), this.domain.getVertex(u.getId()))) {
                    quotient.addEdge(new Edge(v, u));
                }
            }
        }
        return quotient;
    }

    public boolean isValid() {
        boolean res = true;
        for (Vertex v : domain) {
            if (!(homomorphism.containsKey(v)) || (homomorphism.get(v) == null)) {
                res = false;
            }
        }
        if (res) {
            Iterator<Edge> i = domain.edgeIterator();
            Edge e;
            while (i.hasNext()) {
                e = i.next();
                res = range.isEdge(homomorphism.get(e.source), homomorphism.get(e.target));
            }
        }

        return res;
    }

    /**
     * Check if this homomorphism is an auotmorphism
     *
     * @return true if this homomorphism is an auotmorphism
     */
    boolean isAutomorphism() {
        return domain == range;
    }

    public static Homomorphism compose(Homomorphism h1, Homomorphism h2) {
        HashMap<Vertex, Vertex> composed = new HashMap<>();
        HashMap<Vertex, Vertex> H1function = h1.getHomomorphism();
        HashMap<Vertex, Vertex> H2function = h2.getHomomorphism();
        for (Vertex key : H1function.keySet()) {
            composed.put(key, H2function.get(H1function.get(key)));
        }
        return new Homomorphism(h1.getDomain(), h2.getRange(), composed);
    }
}
