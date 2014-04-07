package graphtea.extensions.algorithms;

import graphtea.extensions.reports.Partitioner;
import graphtea.extensions.reports.SubSetListener;
import graphtea.graph.graph.*;
import graphtea.library.BaseVertex;
import graphtea.library.Path;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.algorithmanimator.core.GraphAlgorithm;
import graphtea.plugins.algorithmanimator.extension.AlgorithmExtension;
import graphtea.plugins.main.core.AlgorithmUtils;
import graphtea.ui.components.gpropertyeditor.GCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Vector;

/**
 * author: rostam
 * author: azin
 */
public class IndSetProductColoring extends GraphAlgorithm implements AlgorithmExtension {
    public IndSetProductColoring(BlackBoard blackBoard) {
        super(blackBoard);
    }

    public static Vector<ArrayDeque<BaseVertex>> getAllIndependentSets(GraphModel graph) {
        Partitioner p = new Partitioner(graph);
        AllIndSetSubSetListener l = new AllIndSetSubSetListener();
        p.findAllSubsets(l);
        Vector<ArrayDeque<BaseVertex>> ret = new Vector<ArrayDeque<BaseVertex>>();
        for (ArrayDeque<BaseVertex> set : l.maxsets) {
            ret.add(set);
        }
        return ret;
    }

    @Override
    public void doAlgorithm() {
        step("The algorithm first generates all independent sets I.");

        GraphModel g = graphData.getGraph();
        Vector<ArrayDeque<BaseVertex>> maxsets = getAllIndependentSets(g);
        Vector<SubGraph> ret = new Vector<SubGraph>();
        for (ArrayDeque<BaseVertex> maxset : maxsets) {
            SubGraph sd = new SubGraph(g);
            sd.vertices = new HashSet<Vertex>();
            for (BaseVertex v : maxset) {
                sd.vertices.add((Vertex) v);
            }
            ret.add(sd);
        }

        Vector<Vector<Integer>> ind_sets=new Vector<Vector<Integer>>();
        for(int i=0;i<ret.size();i++) {
            HashSet<Vertex> ind_set = ret.get(i).vertices;
            Vector<Integer> indset = new Vector<Integer>();
            for(Vertex vid:ind_set)
                indset.add(vid.getId());
            ind_sets.add(indset);
        }

        new IndSetsDialog(ind_sets,"All Independent Sets","");
        step("Now, the nth power of I is computed in each step, until " +
                "all vertices of G are seen.");

        Vector<Vector<Integer>> ind_sets2= new Vector<Vector<Integer>>(ind_sets);
        for(int i=0;i<3;i++) {
            ind_sets2=setproduct(ind_sets2,ind_sets,i+1);
            new IndSetsDialog(ind_sets2,"","");
            step("The " + i + "th power of I is computed");
        }

        step("That's it!");

    }

    public Vector<Vector<Integer>> setproduct(Vector<Vector<Integer>> set1,Vector<Vector<Integer>> set2,int minuscount) {
        Vector<Vector<Integer>> ret = new Vector<Vector<Integer>>();
        for(int i=0;i<set1.size();i++) {
            Vector<Integer> tt  = set1.get(i);

            for(int j=0;j<set2.size();j++) {
                Vector<Integer> tmp = new Vector<Integer>();
                for(int k=0;k<set1.get(i).size();k++) {
                    tmp.add(tt.get(k));
                }
                Vector<Integer> tt2 = set2.get(j);
                boolean sameItem = false;

                for(int l=0;l<tt2.size();l++) {
                    int tmpInt = tt2.get(l);
                    if(tmpInt != -1 && tmp.contains(tmpInt)) {
                        sameItem = true;
                    }
                }
                if(!sameItem && tmp.size() != 0 && tt2.size() != 0) {
                    tmp.add(-1);
                    tmp.addAll(tt2);
                }

                int mcount = 0;
                for(int cnt : tmp) {
                    if(cnt == -1) mcount ++ ;
                }
                if(mcount == minuscount) ret.add(tmp);
            }

        }
        return ret;
    }


    @Override
    public String getName() {
        return "Inclusion-Exclusion Coloring Algorithm";
    }

    @Override
    public String getDescription() {
        return "Inclusion-Exclusion Coloring Algorithm";
    }
}

class AllIndSetSubSetListener implements SubSetListener {
    Vector<ArrayDeque<BaseVertex>> maxsets = new Vector<ArrayDeque<BaseVertex>>();
    int max = -1;

    public boolean subsetFound(int t, ArrayDeque<BaseVertex> complement, ArrayDeque<BaseVertex> set) {
        maxsets.add(new ArrayDeque<BaseVertex>(set));
        return false;
    }
}

class IndSetsDialog extends JDialog {
    public IndSetsDialog(Vector<Vector<Integer>> ind_sets,
                         String name, String description) {
        this.setVisible(true);
        this.setTitle(name);
        this.setSize(new Dimension(200,400));
        //jdd.setLayout(new BorderLayout(3, 3));
        this.add(new JLabel(description), BorderLayout.NORTH);
        Vector<IndSubGraphs> isg = new Vector<IndSubGraphs>();
        for(int i=0;i < ind_sets.size();i++) {
            IndSubGraphs isgs = new IndSubGraphs();
            isgs.addAll(ind_sets.get(i));
            isg.add(isgs);
        }
        // isg.addAll(ind_sets2);
        Component rc = GCellRenderer.getRendererFor(isg);
        rc.setEnabled(true);
        this.add(rc, BorderLayout.CENTER);
        this.setVisible(true);
        this.validate();
    }

}
