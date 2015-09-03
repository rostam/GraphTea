// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.connectivity;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * @author M. Ali Rostami
 */

@CommandAttitude(name = "k_vertex_connectivity", abbreviation = "_kvc")
public class KConnected implements GraphReportExtension {

    public Object calculate(GraphModel g) {
        return kconn(g);
    }

    public static int kconn(GraphModel g) {
        int pre;
        int now = Integer.MIN_VALUE;
        boolean preconn, nowconn;

        for (Vertex v : g.vertices())
            now = Math.max(now, g.getDegree(v));

        if (kdisconn(g, now - 1) == null) nowconn = true;
        else nowconn = false;

        do {
            preconn = nowconn;
            pre = now;
            if (preconn) now = pre + 1;
            else now = pre - 1;
            if (kdisconn(g, now - 1) == null) nowconn = true;
            else nowconn = false;
        } while (!((preconn && !nowconn && pre == now - 1) || (!preconn
                && nowconn && pre == now + 1)));

        if (!preconn)
            return now;

        return pre;
    }

    public static Vertex[] kdisconn(GraphModel g, int k) {
        LinkedList<Integer> remain = new LinkedList<>();
        HashSet<Integer> popped = new HashSet<>();
        boolean[] done = new boolean[g.numOfVertices()];
        specArr karray = new specArr(k, g.numOfVertices());

        if (k >= g.numOfVertices())
            return g.getVertexArray();

        do {
            remain.clear();
            popped.clear();
            Arrays.fill(done, false);

            for (int j = 0; j < k; j++)
                popped.add(karray.get(j));

            for (int j = 0; remain.size() == 0; j++)
                if (!popped.contains(j))
                    remain.add(j);

            while (remain.size() > 0) {
                Vertex n = g.getVertex(remain.poll());
                Integer index;

                done[n.getId()] = true;

                for (Vertex o : g.getNeighbors(n)) {
                    index = o.getId();

                    if (!done[index] && !remain.contains(index)
                            && !popped.contains(index))
                        remain.add(index);
                }
            }

            for (int i = 0; i < done.length; i++)
                if (!done[i] && !popped.contains(i)) {
                    Vertex[] tuple = new Vertex[k];

                    for (int j = 0; j < k; j++)
                        tuple[j] = g.getVertex(karray.get(j));

                    return tuple;
                }
        } while (karray.next());

        return null;
    }

    private static class specArr extends ArrayList<Integer>{
        final int k, n;

        public specArr(int k, int n) {
            this.k = k;
            this.n = n;

            for (int i = 0; i < k; i++)
                this.add(i);
        }

        public boolean next() {
            int i = k - 1;
            while (i >= 0 && this.get(i) >= n - (k - 1 - i))
                i--;
            if (i >= 0) {
                this.set(i,this.get(i)+1);
                for (int j = i + 1; j < k; j++)
                    this.set(j,this.get(j-1)+1);
                return true;
            }
            return false;
        }
    }

    public String getName() {
        return "Vertex Connectivity";
    }

    public String getDescription() {
        return "Vertex Connectivity";
    }


    @Override
    public String getCategory() {
        // TODO Auto-generated method stub
        return "Connectivity";
    }
}
