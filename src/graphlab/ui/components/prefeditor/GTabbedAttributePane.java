// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.ui.components.prefeditor;

import graphlab.platform.attribute.NotifiableAttributeSetImpl;
import graphlab.platform.preferences.AbstractPreference;
import graphlab.ui.components.gpropertyeditor.GPropertyEditor;
import graphlab.ui.components.utils.GFrameLocationProvider;

import javax.swing.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Rouzbeh Ebrahimi
 */
public class GTabbedAttributePane extends JTabbedPane {
    /**
     *
     */
    private static final long serialVersionUID = -1122049309619308113L;
    public HashMap<String, AbstractPreference> tabs;
    public HashMap<String, HashSet<AbstractPreference>> complicatedTabs;
    public boolean isComplicatedForm;

    /**
     * Creates new form GAttrFrame
     */
    public GTabbedAttributePane(HashMap<String, AbstractPreference> tabs) {
        super();
        this.tabs = tabs;
        initComponents();
    }

    public GTabbedAttributePane(HashMap<String, HashSet<AbstractPreference>> tabs, boolean complicatedForm) {
        super();
        this.complicatedTabs = tabs;
        this.isComplicatedForm = complicatedForm;
        initComponents();
    }

    /**
     * @return the return status of this dialog - true-> the ok presses, false-> cancelled by user
     */
    public boolean getReturnStatus() {
        return status;
    }

    GPropertyEditor table;

    public void initComponents() {
        setPreferredSize(GFrameLocationProvider.getPrefSize());
        setLocation(GFrameLocationProvider.getPrefLocation());
        setName("Preferences");
        Iterator<String> iter;
        if (!isComplicatedForm) {
            iter = tabs.keySet().iterator();
        } else {
            iter = complicatedTabs.keySet().iterator();
        }
        for (; iter.hasNext();) {
            if (!isComplicatedForm) {
                String title = iter.next();
                GPropertyEditor gp = new GPropertyEditor();
                gp.connect(tabs.get(title).attributeSet);
                gp.setVisible(true);
                addTab(title, gp);
            } else {
                String title = iter.next();
                GPropertyEditor gp = new GPropertyEditor();
                Iterator<AbstractPreference> i = complicatedTabs.get(title).iterator();
                NotifiableAttributeSetImpl attributeSet = new NotifiableAttributeSetImpl();
                for (; i.hasNext();) {
                    AbstractPreference ap = i.next();

                    Map<String, Object> attributeMap = ap.attributeSet.getAttrs();
                    Iterator<String> j = attributeMap.keySet().iterator();
                    for (; j.hasNext();) {
                        String name = j.next();
                        Object o = attributeMap.get(name);
                        attributeSet.put(ap.preferenceName + ":   " + name, o);
                    }
                }
//                GraphPreferences prefInstance=new GraphPreferences();
                gp.connect(attributeSet);

                gp.setVisible(true);
                addTab(title, gp);
            }
        }

    }

    private boolean status = false;
    private boolean finished = false;

    private void closeDialog() {
        finished = true;
    }

    private void finished(boolean status) {
        this.status = status;
        closeDialog();
        //dispose();
    }
//    public static GTabbedAttributePane showEditDialog(NotifiableAttributeSet input){
//        return showEditDialog(input, true);
//    }
//    /** Shows a Property editor to edit the attributes in the input.
//     * the modal is like the modal in JDialog */
//    public static GTabbedAttributePane showEditDialog(NotifiableAttributeSet input, boolean modal){
//        GTabbedAttributePane gAttrpane = new GTabbedAttributePane();
//        gAttrpane.setVisible(true);
//        return gAttrPane;
//    }

    /**
     * return the GProertyEditor which is the main editor of notifiableAttributeSet
     */
    public GPropertyEditor getPropertyEditor() {
        return table;
    }
}
