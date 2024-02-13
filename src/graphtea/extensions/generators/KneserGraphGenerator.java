// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.generators;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.graphgenerator.GraphGenerator;
import graphtea.plugins.graphgenerator.core.PositionGenerators;
import graphtea.plugins.graphgenerator.core.SimpleGeneratorInterface;
import graphtea.plugins.graphgenerator.core.extension.GraphGeneratorExtension;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Author: M. Ali Rostami
 *
 * https://en.wikipedia.org/wiki/Kneser_graph
 *
 */

@CommandAttitude(name = "generate_kneser", abbreviation = "_g_kneser")
public class KneserGraphGenerator implements GraphGeneratorExtension, Parametrizable, SimpleGeneratorInterface {
    @Parameter(name = "k")
    public static Integer k = 2;
    @Parameter(name = "n")
    public static Integer n = 5;

    private Vector<HashSet<Integer>> computedVertices;

    private static Vector<HashSet<Integer>> ComputeVertices(int n, int k) {
        List<String> list = new ArrayList<>();
        for(int i : IntStream.rangeClosed(0, n-1).toArray()) {
            list.add(i+"");
        }
        java.util.List<String> combinations = list.stream()
                .reduce(Collections.<String>emptyList(),
                        (sets, item) -> Stream.of(
                                sets.stream(),
                                Stream.of(item),
                                sets.stream().map(str->str+"-"+item)
                        ).flatMap(x->x)
                                .collect(Collectors.toList()),
                        (sets, sets2) -> {
                            throw new UnsupportedOperationException(
                                    "Impossible error in sequential streams");
                        }
                ).stream().filter(s -> s.chars().filter(ch -> ch == '-').count() == (k/2)).collect(Collectors.toList());

        Vector<HashSet<Integer>> vs = new Vector<>();
        for(String s : combinations) {
            vs.add(new HashSet<>());
            String[] splitted = s.split("-");
            for(String ss : splitted) {
                vs.lastElement().add(Integer.parseInt(ss));
            }
        }
//        System.out.println(vs);
//        System.out.println(combinations);
        return vs;
    }

    Vertex[] v;

    public Vertex[] getVertices() {
        computedVertices = ComputeVertices(n, k);
        Vertex[] ret = new Vertex[computedVertices.size()];
        for (int i = 0; i < computedVertices.size(); i++)
            ret[i] = new Vertex();
        v = ret;
        return ret;
    }

    public Edge[] getEdges() {
        Vector<Edge> ret = new Vector<>();
        for(int i=0;i < computedVertices.size();i++) {
            for(int j=i+1;j < computedVertices.size();j++) {
                HashSet<Integer> s1 = computedVertices.get(i);
                HashSet<Integer> s2 = computedVertices.get(j);
                Set<Integer> intersection = new HashSet<>(s1);
                intersection.retainAll(s2);
                if(intersection.size() == 0) ret.add(new Edge(v[i], v[j]));
            }
        }
        Edge[] ret1 = new Edge[ret.size()];
        for (int i = 0; i < ret.size(); i++) {
            ret1[i] = ret.get(i);
        }
        return ret1;
    }

    public GPoint[] getVertexPositions() {
        return PositionGenerators.circle(5, 5, 100, 100, computedVertices.size());
    }

    public String getName() {
        return "Kneser Graph K(n,k)";
    }

    public String getDescription() {
        return "Generate Kneser Graph K(n,d)";
    }

    public String checkParameters() {
    	if( k <0 || n<0)return "Both N & D must be positive!";
    	if(k >0 & n>0 & k >n)return " D must be smaller than N!";
    	else
    		return null;
    }

    public GraphModel generateGraph() {
        return GraphGenerator.getGraph(false, this);
    }

    /**
     * generates a Kneser Graph with given parameters
     */
    public static GraphModel generateKneserGraph(int n, int k) {
        KneserGraphGenerator.n = n;
        KneserGraphGenerator.k = k;
        return GraphGenerator.getGraph(false, new KneserGraphGenerator());
    }

    @Override
    public String getCategory() {
        return "General Graphs";
    }

    public static void main(String[] args) {
        KneserGraphGenerator kneserGraphGenerator = new KneserGraphGenerator();
        kneserGraphGenerator.generateGraph();
    }
}