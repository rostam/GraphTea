// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui;

import graphtea.platform.attribute.NotifiableAttributeSet;

/**
 * @author azin azadi
 */
public interface NotifiableAttributeSetView extends NotifiableAttributeSet {
    AttributeSetView getView();
}
