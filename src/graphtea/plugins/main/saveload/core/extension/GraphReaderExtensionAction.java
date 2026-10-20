// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.saveload.core.extension;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.ui.GTabbedGraphPane;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.ui.UserNotifier;
import graphtea.plugins.main.saveload.ExampleFileFilter;
import graphtea.ui.UIUtils;
import graphtea.ui.extension.AbstractExtensionAction;

import javax.swing.JFileChooser;
import java.io.File;

/**
 * Reads a graph with one {@link GraphReaderExtension} into a new tab: an entry of
 * <b>File &rsaquo; Load Graph From</b>.
 *
 * <p>A file the reader did not accept used to be ignored without a word, so choosing a file of
 * the wrong kind appeared to do nothing.
 *
 * @author azin azadi
 */
public class GraphReaderExtensionAction extends AbstractExtensionAction<GraphReaderExtension> {
    private final GraphReaderExtension gi;

    public GraphReaderExtensionAction(BlackBoard bb, GraphReaderExtension gi) {
        super(bb, gi);
        this.gi = gi;
    }

    public String getMenuNamePrefix() {
        return "";
    }

    public String getParentMenuName() {
        return "File.Load Graph From";
    }

    public void performExtension() {
        JFileChooser chooser = new JFileChooser();
        ExampleFileFilter filter = new ExampleFileFilter(gi.getExtension(), gi.getName());
        filter.setDescription(gi.getDescription());
        chooser.setFileFilter(filter);
        if (GraphIOExtensionHandler.defaultFile != null) {
            chooser.setCurrentDirectory(new File(GraphIOExtensionHandler.defaultFile));
        }
        if (chooser.showOpenDialog(UIUtils.getGFrame(blackboard)) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File file = chooser.getSelectedFile();
        GraphIOExtensionHandler.defaultFile = file.getPath();
        try {
            if (!gi.accepts(file)) {
                throw new IllegalArgumentException(file.getName() + " is not a " + gi.getName()
                        + " file. Expected a ." + gi.getExtension() + " file.");
            }
            GraphModel g = gi.read(file);
            GraphFiles.remember(g, file);
            GTabbedGraphPane.getCurrentGTabbedGraphPane(blackboard).addGraph(g);
        } catch (Exception e) {
            UserNotifier.report("Opening " + file.getName(), e);
        }
    }
}
