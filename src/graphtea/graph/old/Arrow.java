// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.graph.old;

import java.awt.*;
import java.io.Serializable;

/**
 * @author Azin Azadi
 */
public interface Arrow extends Serializable {
    String ARROW = "ARROW";

    String getName();

    void paintArrow(Graphics g, int w, int h);

    Rectangle getBounds();
}
