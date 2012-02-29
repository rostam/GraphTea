// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.main.preview;

import graphlab.graph.JGraph;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.FastRenderer;
import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.platform.core.exception.ExceptionHandler;
import graphlab.plugins.main.saveload.Load;
import graphlab.plugins.main.core.*;
import graphlab.ui.UI;
import graphlab.ui.UIUtils;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * @author azin azadi

 */
public class ShowPreview extends AbstractAction {
    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public ShowPreview(BlackBoard bb) {
        super(bb);
        listen4Event(UIUtils.getUIEventKey("show preview"));
    }

    public void performAction(String eventName, Object value) {
        String fileName = JOptionPane.showInputDialog(null, "Enter the file path to preview");
        show(fileName);
    }

    /**
     * displayes a preview of the given file (as a GraphML file), the preview window will be a light! window which only a few plugins are loaded to build it
     */
    public static void show(String fileName) {
        BlackBoard b = new BlackBoard();
        loadPlugins(b);
        GraphModel g = new GraphModel();
        try {
            g = Load.loadGraphFromFile(new File(fileName));
        } catch (IOException e) {
            ExceptionHandler.catchException(e);
        } catch (ParserConfigurationException e) {
            ExceptionHandler.catchException(e);
        } catch (SAXException e) {
            ExceptionHandler.catchException(e);
        }
        JFrame f = new JFrame("preview");

        f.add(JGraph.getNewComponent(b));
        f.pack();
        f.setVisible(true);
    }

    //not working yet
    public static void showPreview (GraphModel g){
        JFrame f = new JFrame("preview");

        BlackBoard blackboard = new BlackBoard();
        new graphlab.plugins.main.core.Init().init(blackboard);
        f.add(new JGraph(g, new FastRenderer(g, blackboard)));
        f.pack();
        f.setVisible(true);
        
    }
    private static void loadPlugins(BlackBoard b) {
        UI ui = new UI(b, true);
        try {

            ui.loadXML("graphlab/plugins/graph/SampleUI.xml", null);
            ui.addXML("graphlab/plugins/rightclick/config.xml", null);
            ui.addXML("graphlab/plugins/actiongrouping/GroupActions.xml", null);
        } catch (IOException e) {
            ExceptionHandler.catchException(e);
        } catch (SAXException e) {
            ExceptionHandler.catchException(e);
        }
    }

//    public static void main(String[] args) {
//        show(something);
//    }

}
