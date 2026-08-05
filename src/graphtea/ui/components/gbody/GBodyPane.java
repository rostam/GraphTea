// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui.components.gbody;

import javax.swing.*;

/**
 * this is an interface which is used to give a JPanel,
 * in graphtea the Graph implements this.
 * the program should put a JPanel in the Body of the GFrame,
 * so it finds the JPanel via the getPanel method of the class that specified in the body tag of XML.
 *
 * @author azin azadi
 */
public interface GBodyPane {
    //todo: return a component
    JPanel getPanel();
}
