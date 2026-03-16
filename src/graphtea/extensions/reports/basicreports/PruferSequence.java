// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.basicreports;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;


/**
 * Computes the Prüfer sequence of a labeled tree.
 *
 * The Prüfer sequence is a unique sequence of n-2 vertex labels that encodes
 * a labeled tree on n vertices. It is produced by repeatedly removing the leaf
 * with the smallest label and appending its neighbor's label to the sequence.
 *
 * The graph must be an unrooted labeled tree (connected, undirected, n-1 edges).
 */
@CommandAttitude(name = "prufer_sequence", abbreviation = "_ps")
public class PruferSequence implements GraphReportExtension<String> {

    public String calculate(GraphModel g) {
        int n = g.getVerticesCount();
        if (n < 2) {
            return "Graph must have at least 2 vertices";
        }
        if (g.getEdgesCount() != n - 1) {
            return "Graph is not a tree (edge count must equal n-1)";
        }

        // degree[i] mirrors the current degree of vertex with id=i
        int[] degree = new int[n];
        Vertex[] vertices = g.getVertexArray();
        for (Vertex v : vertices) {
            degree[v.getId()] = g.getDegree(v);
        }

        int[] sequence = new int[n - 2];
        boolean[] removed = new boolean[n];

        for (int step = 0; step < n - 2; step++) {
            // find the leaf with the smallest id
            int leaf = -1;
            for (int i = 0; i < n; i++) {
                if (!removed[i] && degree[i] == 1) {
                    leaf = i;
                    break;
                }
            }
            if (leaf == -1) {
                return "Graph is not a tree (cycle detected)";
            }

            // find the unique neighbor of this leaf
            Vertex leafVertex = g.getVertex(leaf);
            int neighbor = -1;
            for (Vertex nb : g.getNeighbors(leafVertex)) {
                if (!removed[nb.getId()]) {
                    neighbor = nb.getId();
                    break;
                }
            }
            if (neighbor == -1) {
                return "Graph is not a tree (disconnected)";
            }

            sequence[step] = neighbor;
            removed[leaf] = true;
            degree[leaf] = 0;
            degree[neighbor]--;
        }

        // format as comma-separated list of 1-based labels for readability
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sequence.length; i++) {
            sb.append(sequence[i]);
            if (i < sequence.length - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public String getName() {
        return "Prufer Sequence";
    }

    public String getDescription() {
        return "Prufer sequence of a labeled tree";
    }

    @Override
    public String getCategory() {
        return "General";
    }
}
