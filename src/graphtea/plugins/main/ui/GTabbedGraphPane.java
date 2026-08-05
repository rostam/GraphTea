// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.main.ui;

import graphtea.platform.core.BlackBoard;
import graphtea.ui.components.GComponentInterface;

import java.awt.*;

public class GTabbedGraphPane implements GComponentInterface {
    public Component getComponent(BlackBoard b) {
        return new graphtea.graph.ui.GTabbedGraphPane(b).getComponent(b);
    }
}
