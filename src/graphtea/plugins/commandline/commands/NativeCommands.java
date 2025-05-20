// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.commandline.commands;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.lang.CommandAttitude;
import graphtea.platform.parameter.Parameter;
import graphtea.plugins.main.GraphData;

import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
/**
 * @author Mohammad Ali Rostami
 */
public class NativeCommands {

    public NativeCommands(BlackBoard bb) {
        GraphData datas = new GraphData(bb);
    }

    private native String homomorph(String graph_format1, String graph_format2);

    @CommandAttitude(name = "ghomomorph", abbreviation = "_ghom", description = "check homomorphism")
    public String ghomomorph(@Parameter(name = "first_graph")GraphModel g1,
                             @Parameter(name = "second_graph")GraphModel g2) {
        StringBuilder graph1 = new StringBuilder();
        StringBuilder graph2 = new StringBuilder();
        graph1.append(g1.getVerticesCount()).append("\n");
        graph2.append(g2.getVerticesCount()).append("\n");
        Iterator<Edge> it1 = g1.edgeIterator();
        Iterator<Edge> it2 = g2.edgeIterator();

        HashMap<Integer, Integer> map = new HashMap<>();
        HashMap<Integer, Integer> nmap = new HashMap<>();
        Iterator<Vertex> vm = g1.iterator();
        int counter = 0;
        while (vm.hasNext()) {
            Vertex vv = vm.next();
            map.put(vv.getId(), counter);
            nmap.put(counter++, vv.getId());
        }

        while (it1.hasNext()) {
            Edge e = it1.next();
            graph1.append(map.get(e.source.getId())).append(" ").append(map.get(e.target.getId())).append("\n");
        }

        map = new HashMap<>();
        vm = g2.iterator();
        counter = 0;

        while (vm.hasNext())
            map.put(vm.next().getId(), counter++);

        while (it2.hasNext()) {
            Edge e = it2.next();
            graph2.append(map.get(e.source.getId())).append(" ").append(map.get(e.target.getId())).append("\n");
        }

        String s = homomorph(graph1.toString(), graph2.toString());
        double time = Double.parseDouble(s.substring(0, s.indexOf("\n")));
        s = s.substring(s.indexOf("\n") + 1);
        if (s.equals("false")) return "time : " + time + ".\nNo homomorphism exists.";
        StringBuilder result = new StringBuilder();
        StringTokenizer stk2 = new StringTokenizer(s);
        while (stk2.hasMoreElements()) {
            String temp = (String) stk2.nextElement();
            result.append(nmap.get(Integer.parseInt(temp.substring(0, temp.indexOf("-"))))).append("->").append(nmap.get(Integer.parseInt(temp.substring(temp.indexOf(">") + 1)))).append("\n");
        }

        return "time : " + time + ".\nFounded Homomorphism is:\n" + result;
    }
}
