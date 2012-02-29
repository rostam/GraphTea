// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.ui.components;

import graphlab.platform.core.BlackBoard;

import java.awt.*;

/**
 * this interface provides a way to get components from xml files,
 * all components you want to pass to UI from xml should implemented this interface,
 * the returned component will be added to UI.
 * it is VERY IMPORTANT that the implementing class has a constructor with no parameters or just with one parameter
 * that's its type is blackboard.
 *
 * @author azin azadi
 */
public interface GComponentInterface {
    public Component getComponent(BlackBoard b);
}
