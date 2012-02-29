// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.main.core.actions.preferences;

import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.platform.preferences.AbstractPreference;
import graphlab.platform.preferences.Preferences;
import graphlab.platform.preferences.lastsettings.StorableOnExit;
import graphlab.ui.UIUtils;
import graphlab.ui.components.prefeditor.GPrefPane;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Rouzbeh Ebrahimi
 */
public class PreferencesAction extends AbstractAction implements StorableOnExit {
    public static final String EVENT_KEY = UIUtils.getUIEventKey("Prefs");
    Preferences pref;

    public PreferencesAction(BlackBoard bb) {
        super(bb);
        listen4Event(EVENT_KEY);
        pref = bb.getData("Preferences");

    }


    public void performAction(String eventName, Object value) {
        pref.retrieveEveryItem();
//        HashMap<String, HashSet<AbstractPreference>> complicatedTabs = new HashMap<String, HashSet<AbstractPreference>>();
        managePrefUI();
    }


    private void managePrefUI() {
        HashMap<String, AbstractPreference> tabs = new HashMap<String, AbstractPreference>();
        Iterator<AbstractPreference> iterator = pref.set.iterator();

        for (; iterator.hasNext();) {
            AbstractPreference ap = iterator.next();
            tabs.put(ap.preferenceName, ap);
        }

//        GTabbedAttributePane pane = new GTabbedAttributePane(tabs);
//        GTabbedAttributeFrame.showEditDialog(pane,true);
        GPrefPane gpp = new GPrefPane(blackboard, tabs);
        gpp.setVisible(true);
    }


}
