package graphtea.extensions.reports.spectralreports.maxflowmincut;

import graphtea.graph.graph.GraphModel;
import graphtea.plugins.reports.extension.GraphReportExtension;

import javax.swing.*;

public class GomoryHuTree implements GraphReportExtension  {
		

	public String getName() {
		return "Gomory-Hu Tree";
	}

	public String getDescription() {
		return "Constructs Gomory-Hu Tree";
	}

	public Object calculate(GraphModel g) {
		if(g.isDirected())
		{
			JOptionPane.showMessageDialog(null,"Directed Graphs may not have Gomory-Hu tree");
			return null;
		}
		
		try {
			//GTabbedGraphPane gtgp = gd.getBlackboard().getData(GTabbedGraphPane.NAME);
			//GusfieldGomoryHuTree GGHT;
			//GGHT = new GusfieldGomoryHuTree(g);
	    	//gtgp.addGraph(GGHT.GHTree);
	    	//GGHT.perform();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	@Override
	public String getCategory() {
		return "Spectral";
	}


/*
 if not caps:
        caps = {}
        for edge in graph.edges():
            caps[edge] = igraph.edge_weight(edge)

    #temporary flow variable
    f = {}

    #we use a numbering of the nodes for easier handling
    n = {}
    N = 0
    for node in graph.nodes():
        n[N] = node
        N = N + 1

    #predecessor function
    p = {}.fromkeys(range(N),0)
    p[0] = None

    for s in range(1,N):
        t = p[s]
        S = []
        #max flow calculation
        (flow,cut) = maximum_flow(graph,n[s],n[t],caps)
        for i in range(N):
            if cut[n[i]] == 0:
                S.append(i)

        value = cut_value(graph,flow,cut)

        f[s] = value

        for i in range(N):
            if i == s:
                continue
            if i in S and p[i] == t:
                p[i] = s
        if p[t] in S:
            p[s] = p[t]
            p[t] = s
            f[s] = f[t]
            f[t] = value

    #cut tree is a dictionary, where each edge is associated with its weight
    b = {}
    for i in range(1,N):
        b[(n[i],n[p[i]])] = f[i]
    return b


 */
}
