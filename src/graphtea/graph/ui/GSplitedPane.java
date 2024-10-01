// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.graph.ui;

import javax.swing.*;
import java.awt.*;

/**
 * @author Azin Azadi
 */
public class GSplitedPane extends JSplitPane {
    public GHTMLPageComponent helper;
    public JComponent main;

    public GSplitedPane(GHTMLPageComponent helper, JComponent main) {
        super(JSplitPane.VERTICAL_SPLIT, main, helper); // Swapped main and helper components
        this.helper = helper;
        this.main = main;

        // Fix size for the helper component (bottom)
        helper.setPreferredSize(new Dimension(-1, 75));
        helper.setMinimumSize(new Dimension(-1, 75)); // Optional: ensure minimum height

        // Set the divider and resizing behavior
        setResizeWeight(1.0); // Makes the main component take up all available space
        setDividerLocation(-1); // Divider will start just above the fixed size of helper
        setDividerSize(1); // Thin divider size
    }
}
