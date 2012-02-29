// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.ui;

import graphlab.graph.ui.GHTMLPageComponent;
import graphlab.graph.ui.HyperlinkHandler;
import graphlab.platform.attribute.NotifiableAttributeSet;
import graphlab.platform.core.BlackBoard;
import graphlab.platform.core.exception.ExceptionHandler;
import graphlab.platform.preferences.lastsettings.StorableOnExit;
import graphlab.ui.actions.UIEventData;
import graphlab.ui.components.GFrame;
import graphlab.ui.components.gpropertyeditor.GBasicCellEditor;
import graphlab.ui.components.gpropertyeditor.GBasicCellRenderer;
import graphlab.ui.components.gpropertyeditor.GCellEditor;
import graphlab.ui.components.gpropertyeditor.GCellRenderer;
import graphlab.ui.components.gpropertyeditor.utils.ObjectViewer;
import graphlab.ui.components.utils.GAttrFrame;

import javax.swing.*;
import java.awt.*;

/**
 * @author Azin Azadi
 */
public class UIUtils {
    public static Component getComponent(BlackBoard b, String id) {
        return b.getData(getComponentVariableKeyNameInBlackBoard(id));
    }

    public static void setComponent(BlackBoard b, String id, Component c) {
        b.setData(getComponentVariableKeyNameInBlackBoard(id), c);
    }

    /**
     * This method gives a standard way to name the awt.components that are in the black board.
     * when in XML we give an id to a component, when we want to fetch it from blackboard we should use this
     * method to have its exact name in black board which is stored in a Variable
     *
     * @param componentId the id of component which is given via XML
     * @return the name of Variable in the blackboard which the component can be accessed
     */

    public static String getComponentVariableKeyNameInBlackBoard(String componentId) {
        return "UI Component" + componentId;
    }

    /**
     * returns the GFrame object that mapped to the blackboard.
     * the returned GFrame contains all menus, sidebars, toolbars and ... of the User Interface.
     */
    public static GFrame getGFrame(BlackBoard b) {
        UI ui = b.getData(UI.name);
        return ui.frame;
    }

    /**
     * @return the UI instance which is currently running in the given blackboard environment
     */
    public static UI getUI(BlackBoard blackboard) {
        return blackboard.getData(UI.name);
    }

    public static String getUIEventKey(String id) {
        return UIEventData.name(id);
    }

    public static void exit() {
        if (JOptionPane.showConfirmDialog(null, "Are you sure to exit?",
                "Exiting From the Application...", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                StorableOnExit.SETTINGS.saveSettings();
            } catch (Exception e) {
                ExceptionHandler.catchException(e);
            }
            System.exit(0);
        }
    }

    //______________________________       gpropertyeditor       ____________________________________
    /**
     * @see graphlab.ui.components.utils.GAttrFrame#showEditDialog(graphlab.platform.attribute.NotifiableAttributeSet,boolean)
     */
    public static GAttrFrame showEditDialog(NotifiableAttributeSet input, boolean modal) {
        return GAttrFrame.showEditDialog(input, modal);
    }

    /**
     * @see graphlab.ui.components.gpropertyeditor.utils.ObjectViewer
     */
    public static ObjectViewer showObject(Object o) {
        return ObjectViewer.showObject(o);
    }

    /**
     * @see graphlab.ui.components.gpropertyeditor.GCellRenderer#registerRenderer(Class,graphlab.ui.components.gpropertyeditor.GBasicCellRenderer)
     */
    public static void registerRenderer(Class clazz, GBasicCellRenderer viewer) {
        GCellRenderer.registerRenderer(clazz, viewer);
    }

    /**
     * @see graphlab.ui.components.gpropertyeditor.GCellEditor#registerEditor(Class,graphlab.ui.components.gpropertyeditor.GBasicCellEditor)
     */
    public static void registerEditor(Class clazz, GBasicCellEditor editor) {
        GCellEditor.registerEditor(clazz, editor);
    }

    /**
     * @see graphlab.ui.components.gpropertyeditor.GCellEditor#getEditorFor(Object)
     */
    public static GBasicCellEditor getEditorFor(Object value) {
        return GCellEditor.getEditorFor(value);
    }

    /**
     * @see graphlab.ui.components.gpropertyeditor.GCellRenderer#getRendererFor(Object)
     */
    public static Component getRendererFor(Object value) {
        return GCellRenderer.getRendererFor(value);
    }



    //_____________    GHTMLPageComponent    _________________
    /**
     * @see graphlab.graph.ui.GHTMLPageComponent#registerHyperLinkHandler(java.lang.String,graphlab.graph.ui.HyperlinkHandler)
     */
    public static void registerHyperLinkHandler(String protocol, HyperlinkHandler h) {
        GHTMLPageComponent.registerHyperLinkHandler(protocol, h);
    }
}