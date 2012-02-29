// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.core;

import graphlab.graph.graph.GraphColoring;
import graphlab.graph.graph.SubGraph;
import graphlab.graph.old.ArrowHandler;
import graphlab.graph.old.GShape;
import graphlab.graph.old.GStroke;
import graphlab.graph.old.PolygonArrow;
import graphlab.platform.Application;
import graphlab.platform.StaticUtils;
import graphlab.platform.core.BlackBoard;
import graphlab.platform.core.Listener;
import graphlab.platform.core.exception.ExceptionHandler;
import graphlab.platform.preferences.lastsettings.StorableOnExit;
import graphlab.plugins.main.ui.*;
import graphlab.ui.UI;
import graphlab.ui.UIUtils;
import graphlab.ui.components.GFrame;
import graphlab.ui.components.gpropertyeditor.editors.inplace.GSimpleComboEditor;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

/**
 * @author Reza Mohammadi, azin
 */
public class Init implements graphlab.platform.plugin.PluginInterface, StorableOnExit {

    public static Class uiClass = null;

    public void init(final BlackBoard blackboard) {
        try {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                ExceptionHandler.catchException(e);
            }
            UI ui = new UI(blackboard, false);
            blackboard.addListener(Application.POST_INIT_EVENT, new Listener() {
                public void keyChanged(String key, Object value) {
                    GFrame frame = UIUtils.getGFrame(blackboard);
                    URL resource = getClass().getResource("/images/Icon.ICO");
                    if (resource != null) {
                        ImageIcon icon = new ImageIcon(resource);
                        frame.setIconImage(icon.getImage());
                    }
                    frame.validate();
                    UIUtils.getGFrame(blackboard).setVisible(true);
                }
            });
            ui.loadXML("/graphlab/plugins/main/core/SampleUI.xml", getClass());
            UIUtils.registerRenderer(PolygonArrow.class, new ArrowRenderer());
            UIUtils.registerEditor(PolygonArrow.class, new ArrowEditor());
            UIUtils.registerRenderer(GShape.class, new GShapeRenderer());
            UIUtils.registerRenderer(GStroke.class, new GStrokeRenderer());
            UIUtils.registerEditor(GStroke.class, new GStrokeEditor());
            UIUtils.registerEditor(GShape.class, new GSimpleComboEditor(new GShapeRenderer()));
            UIUtils.registerRenderer(SubGraph.class, new SubGraphRenderer());
            UIUtils.registerRenderer(GraphColoring.class, new GraphColoringRenderer());

            StaticUtils.setFromStringProvider(PolygonArrow.class.getName(), new ArrowHandler());
            graphlab.ui.components.GFrame gFrame = UIUtils.getGFrame(blackboard);
            gFrame.setTitle("GraphLab V1.0");
            gFrame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Do you want to exit?",
                            "Application Exiting...", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        try {
                            SETTINGS.saveSettings();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        System.exit(0);
                    }
                }
            });


        } catch (Exception e) {
            ExceptionHandler.catchException(e);
        }
    }
}