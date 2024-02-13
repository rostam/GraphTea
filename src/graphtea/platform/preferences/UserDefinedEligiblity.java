// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.platform.preferences;

import graphtea.platform.lang.ArrayX;

import java.util.HashMap;

/**
 * @author Rouzbeh Ebrahimi
 */
public interface UserDefinedEligiblity {
    GraphPreferences GraphPrefFactory();

    HashMap<Object, ArrayX> defineEligibleValuesForSettings(HashMap<Object, ArrayX> objectValues);
}
