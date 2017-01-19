// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.main.ui;

import graphtea.graph.graph.IndSubGraphs;
import graphtea.graph.graph.Vertex;
import graphtea.platform.Application;
import graphtea.plugins.main.GraphData;
import graphtea.ui.components.gpropertyeditor.GBasicCellRenderer;
import graphtea.ui.components.gpropertyeditor.GCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

/**
 * @author Azin Azadi
 */
public class IndSetColoringRenderer implements GBasicCellRenderer<IndSubGraphs> {
//    public IndSetColoringRenderer() {
//        super();
//        backupColoring();
 //   }
    public Component getRendererComponent(final IndSubGraphs res) {
        String txt;
        GraphData gd = new GraphData(Application.getBlackBoard());
        boolean hasAllVSet = true;

        for(int i=0;i<gd.getGraph().getVerticesCount();i++) {
          if(!res.contains(i)) {
             hasAllVSet = false;
             break;
          }
        }

        txt = "<HTML><BODY>";
            txt += "<B>V:</B>{";
            if(hasAllVSet) txt+="<B>";
            for(int j=0;j<res.size();j++)
            {
                int tmp = res.get(j);
                if(tmp == -1)txt += "},{";
                else txt += tmp + ",";
            }
            if(hasAllVSet) txt+="</B>";

            txt += "}<BR>";


        txt = txt + "</BODY></HTML>";
        //System.out.println(txt);
        JLabel l = new JLabel(txt) {
            @Override
            public void setForeground(Color fg) {
                super.setForeground(fg);
                if (fg== GCellRenderer.SELECTED_COLOR)
                    showOnGraph(res);
            }
        };
        l.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                showOnGraph(res);
            }
        }

        );
        return l;
    }

    private void showOnGraph(IndSubGraphs res) {
   //     resetColoring();
        GraphData gd = new GraphData(Application.getBlackBoard());
        //gd.getGraph().
        for(Vertex v : gd.getGraph()) {
            v.setColor(0);
        }
        int color = 2;
        for(int i=0;i<res.size();i++) {
            if(res.get(i) != -1)
              gd.getGraph().getVertex(res.get(i)).setColor(color);
            else color++;
        }
    }

    private HashMap<Vertex, Integer> vertexColors;

    /**
     * resets and stores all colorings of g
     */
    public void backupColoring() {
        GraphData gd = new GraphData(Application.getBlackBoard());
        vertexColors = new HashMap<>();
        for (Vertex v : gd.getGraph()) {
            vertexColors.put(v, v.getColor());
        }
    }

    public void resetColoring() {
        GraphData gd = new GraphData(Application.getBlackBoard());
        for(Vertex v:vertexColors.keySet()) {
            v.setColor(vertexColors.get(v));
        }

    }

}
