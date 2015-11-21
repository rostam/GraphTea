// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.main.ui;

import graphtea.graph.graph.RenderTable;
import graphtea.ui.components.gpropertyeditor.GBasicCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.Vector;

/**
 * @author Azin Azadi
 */
public class TableRenderer implements GBasicCellRenderer<RenderTable> {
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
                            setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
                }

                data[i][j] = o;
            }
            i++;

        }

        JTable table = new JTable(data,names);
        return new JScrollPane(table);
    }
}