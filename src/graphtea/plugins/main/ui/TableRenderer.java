// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.main.ui;

import graphtea.graph.graph.RendTable;
import graphtea.graph.graph.SubGraph;
import graphtea.ui.components.gpropertyeditor.GBasicCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
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

        Object[][] data = new Object[sd.size()-1][sd.get(0).size()];
        for(int i=0;i<sd.size()-1;i++) {
            for(int j=0;j < data[i].length;j++) {
                Object o = sd.get(i+1).get(j);

                if(o instanceof Double && !o.toString().equals("NaN")) {
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