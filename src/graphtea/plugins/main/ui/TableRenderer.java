// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.main.ui;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.RendTable;
import graphtea.graph.graph.SubGraph;
import graphtea.graph.graph.Vertex;
import graphtea.platform.Application;
import graphtea.plugins.main.GraphData;
import graphtea.ui.components.gpropertyeditor.GBasicCellRenderer;
import graphtea.ui.components.gpropertyeditor.GCellRenderer;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Vector;

/**
 * @author Azin Azadi
 */
public class TableRenderer implements GBasicCellRenderer<RendTable> {
    public Component getRendererComponent(final RendTable sd) {
//        String txt = "";
//        txt = "<HTML><BODY><table>";
//        for(int i=1;i<sd.size();i++) {
//          txt += "<tr>";
//          for(int j=0;j<sd.get(i).size();j++) {
//              txt += "<td>" + sd.get(i).get(j) + "</td>";
//          }
//         txt += "</tr>";
//        }
//        txt = txt + "</table></BODY></HTML>";

        Vector col_names = new Vector();
        String[] names = new String[sd.get(0).size()];
        for(int i=0; i < sd.get(0).size();i++) {
            names[i] = (String)sd.get(0).get(i);
        }

        //BigDecimal bd = new BigDecimal();
        Object[][] data = new Object[sd.size()-1][sd.get(0).size()];
        for(int i=0;i<sd.size()-1;i++) {
            for(int j=0;j < data[i].length;j++) {
                Object o = sd.get(i+1).get(j);
                if(o instanceof Double) {
                    Double toBeTruncated = (Double) o;
                    Double truncatedDouble =
                            new BigDecimal(toBeTruncated).
                                    setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
                    o = truncatedDouble;
                }
                data[i][j] = o;
            }
        }


        JTable table = new JTable(data,names);
        JScrollPane scrollpane = new JScrollPane(table);

        return scrollpane;
    }

    private void showOnGraph(SubGraph mysd) {

    }
}