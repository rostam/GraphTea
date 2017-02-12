// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.main.ui;

import graphtea.extensions.G6Format;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.graph.graph.Vertex;
import graphtea.platform.Application;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.graphgenerator.core.PositionGenerators;
import graphtea.plugins.main.GraphData;
import graphtea.ui.components.gpropertyeditor.GBasicCellRenderer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.math.BigDecimal;
import java.util.Vector;

/**
 * @author Azin Azadi
 */
public class TableRenderer implements GBasicCellRenderer<RenderTable> {
    JTable table = new JTable();

    public Component getRendererComponent(final RenderTable sd) {
        Object[] onames = (sd.getTitles()).toArray();
        String[] names = new String[onames.length];
        for(int i=0;i < onames.length;i++) {
            names[i] = onames[i].toString();
        }
        Object[][] data = new Object[sd.size()][sd.getTitles().size()];
        int i=0;
        while(!sd.isEmpty()) {
            Vector<Object> row = sd.poll();
            for(int j=0;j < row.size();j++) {
                Object o = row.get(j);

                if(o instanceof Double && !o.toString().equals("NaN")) {
                    Double toBeTruncated = (Double) o;
                    o = new BigDecimal(toBeTruncated).
                            setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
                }

                data[i][j] = o;
            }
            i++;

        }

        table = new JTable(data,names);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(event -> {
            if (sd.getTitles().get(sd.getTitles().size() - 1).equals("G6"))
                if (event.getValueIsAdjusting()) {
                    GraphModel g = G6Format.stringToGraphModel(table.getValueAt(table.getSelectedRow(),
                            sd.getTitles().size() - 1).toString());
                    Point pp[] = PositionGenerators.circle(200, 400, 250, g.numOfVertices());

                    int tmpcnt = 0;
                    for (Vertex v : g) {
                        v.setLocation(pp[tmpcnt]);
                        tmpcnt++;
                    }
                    new GraphData(Application.getBlackBoard()).core.showGraph(g);
                }
        });
        return new JScrollPane(table);
    }
}