// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.actions;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.ui.GTabbedGraphPane;
import graphtea.platform.Application;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.lang.NaturalOrder;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.extension.GraphActionExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * An operation that combines two open graphs, named by their tab titles, into a new one.
 *
 * <p>Every such operation (union, join, the products, ...) used to look its graphs up itself
 * and pass whatever it found straight on, so asking for a tab that did not exist &mdash; which
 * the default "G1" does whenever only one graph is open &mdash; ended in a
 * {@link NullPointerException}. The parameter dialog now offers the open graphs in drop-down
 * lists, and the names are still checked before the operation runs.
 *
 * @author M. Ali Rostami
 */
public abstract class TwoGraphAction implements GraphActionExtension, Parametrizable {
    @Parameter(name = "First Graph", description = "The first graph, by its tab title")
    public String g0 = "G0";
    @Parameter(name = "Second Graph", description = "The second graph, by its tab title")
    public String g1 = "G1";

    /**
     * @param first     the graph named by {@link #g0}
     * @param second    the graph named by {@link #g1}
     * @param graphData access to the application, for operations that need it
     * @return the combined graph, which is opened in a new tab
     */
    protected abstract GraphModel combine(GraphModel first, GraphModel second, GraphData graphData);

    @Override
    public String[] parameterChoices(String fieldName) {
        Map<String, GraphModel> graphs = openGraphs(Application.blackboard);
        if (graphs == null || graphs.isEmpty()) {
            return null;
        }
        List<String> names = new ArrayList<>(graphs.keySet());
        names.sort(NaturalOrder.INSTANCE);
        if ("g1".equals(fieldName) && names.size() > 1 && (g1.equals(g0) || !names.contains(g1))) {
            // Offer a different graph from the first one when there is one to offer.
            g1 = names.stream().filter(n -> !n.equals(g0)).findFirst().orElse(g1);
        }
        return names.toArray(new String[0]);
    }

    @Override
    public String checkParameters() {
        Map<String, GraphModel> graphs = openGraphs(Application.blackboard);
        if (graphs == null) {
            return null;
        }
        for (String name : new String[]{g0, g1}) {
            if (!graphs.containsKey(name)) {
                return "There is no open graph named \"" + name + "\".\n"
                        + "Open graphs: " + String.join(", ", new TreeSet<>(graphs.keySet()));
            }
        }
        return null;
    }

    @Override
    public void action(GraphData graphData) {
        Map<String, GraphModel> graphs = openGraphs(graphData.getBlackboard());
        GraphModel first = graphs.get(g0);
        GraphModel second = graphs.get(g1);
        if (first == null || second == null) {
            throw new IllegalArgumentException(checkParameters());
        }
        graphData.core.showGraph(combine(first, second, graphData));
    }

    private static Map<String, GraphModel> openGraphs(BlackBoard blackboard) {
        if (blackboard == null) {
            return null;
        }
        GTabbedGraphPane tabs = blackboard.getData(GTabbedGraphPane.NAME);
        return tabs == null ? null : tabs.getGraphs();
    }
}
