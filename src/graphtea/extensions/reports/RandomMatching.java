// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports;


import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.SubGraph;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.HashMap;
import java.util.Random;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * @author Ali Rostami
 */

@CommandAttitude(name = "maxium_matching", abbreviation = "_max_match")
public class RandomMatching implements GraphReportExtension {
    public String getName() {
        return "Random Matching";
    }

    public String getDescription() {
        return "Random Matching";
    }

    private Random r = new Random();
    private Random r2 = new Random(10);
    public Object calculate(GraphModel g) {
        SubGraph sg = new SubGraph();
        int limit=r2.nextInt(g.getEdgesCount());

        Vector<Integer> vi = new Vector<>();
        HashMap<Vertex,Vertex> vv= new HashMap<>();
        for(int i=0;i<g.getVerticesCount();i++) {
            vi.add(i);
        }

        Vertex[] varr = g.getVertexArray();
        Vertex[] rvarr = rotate(varr,r.nextInt(g.getVerticesCount()-2));


        for(Vertex v1 : rvarr) {
            if(vv.size() > limit) break;
            for(Vertex v2 : g.directNeighbors(v1)) {
                if(vi.contains(v1.getId()) && vi.contains(v2.getId())) {
                    vv.put(v1,v2);

                    vi.remove(vi.indexOf(v1.getId()));
                    vi.remove(vi.indexOf(v2.getId()));
                    break;
                }
            }
        }

        for(Vertex v : vv.keySet()) {
            sg.vertices.add(v);
            sg.vertices.add(vv.get(v));
        }

        sg.edges.addAll(vv.keySet().stream().map(v -> g.getEdge(v, vv.get(v))).collect(Collectors.toList()));

        Vector<Object> ret = new Vector<>();
        ret.add("Number of Matching:" + sg.edges.size());
        ret.add(sg);

        return ret;
    }

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "General";
	}

    private Vertex[] rotate(final Vertex[] unOrderedArr, final int orderToRotate) {
        final int length = unOrderedArr.length;
        final Vertex[] rotated = new Vertex[length];
        for (int i = 0; i < length; i++) {
            rotated[(i + orderToRotate) % length] = unOrderedArr[i];
        }
        return rotated;
    }
}
