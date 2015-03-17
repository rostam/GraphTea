// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.graph.ui;

import javax.swing.*;

/**
 * @author Azin Azadi
 */
public class GSplitedPane extends JSplitPane {
    public GHTMLPageComponent helper;
    public JComponent main;

    public GSplitedPane(GHTMLPageComponent helper, JComponent main) {
        super(JSplitPane.VERTICAL_SPLIT, helper, main);
        this.helper = helper;
        this.main = main;
        setDividerLocation(225);
        setDividerSize(1);
    }
}
