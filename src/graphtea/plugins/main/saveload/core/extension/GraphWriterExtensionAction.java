// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.saveload.core.extension;

import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.ui.GTabbedGraphPane;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.ui.UserNotifier;
import graphtea.plugins.main.saveload.ExampleFileFilter;
import graphtea.ui.UIUtils;
import graphtea.ui.extension.AbstractExtensionAction;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import java.io.File;

/**
 * Writes the current graph with one {@link GraphWriterExtension}: an entry of
 * <b>File &rsaquo; Save Graph To</b>.
 *
 * <p>A failed write used to be stored in a field nobody read, after which "File Saved!" was
 * shown regardless, so the user believed a graph was saved when it was not. The graph and file
 * name were also kept in fields shared between saves running at the same time. Failures are now
 * reported, and the file is remembered so that <b>File &rsaquo; Save</b> can write it again.
 *
 * @author azin azadi
 */
public class GraphWriterExtensionAction extends AbstractExtensionAction<GraphWriterExtension> {

    private final GraphWriterExtension ge;

    public GraphWriterExtensionAction(BlackBoard bb, GraphWriterExtension ge) {
        super(bb, ge);
        this.ge = ge;
    }

    public String getParentMenuName() {
        return "File.Save Graph To";
    }

    public String getMenuNamePrefix() {
        return "";
    }

    public void performExtension() {
        saveAs(blackboard.getData(GraphAttrSet.name));
    }

    /**
     * @param file a file
     * @return whether this writer produces files of that kind
     */
    public boolean writes(File file) {
        return GraphFiles.hasExtension(file, ge.getExtension());
    }

    /**
     * Asks for a file name, then writes the graph to it.
     *
     * @param g the graph to save
     */
    public void saveAs(GraphModel g) {
        if (g == null) {
            return;
        }
        File file = chooseFile(g);
        if (file != null) {
            write(g, file);
        }
    }

    private File chooseFile(GraphModel g) {
        JFileChooser chooser = new JFileChooser();
        ExampleFileFilter filter = new ExampleFileFilter(ge.getExtension(), ge.getName());
        filter.setDescription(ge.getDescription());
        chooser.setFileFilter(filter);
        File previous = GraphFiles.get(g);
        if (previous != null && writes(previous)) {
            chooser.setSelectedFile(previous);
        } else {
            File dir = previous != null ? previous.getParentFile()
                    : GraphIOExtensionHandler.defaultFile == null ? null : new File(GraphIOExtensionHandler.defaultFile);
            if (dir != null && !dir.isDirectory()) {
                dir = dir.getParentFile();
            }
            chooser.setSelectedFile(new File(dir, g.getLabel() + "." + ge.getExtension()));
        }
        if (chooser.showSaveDialog(UIUtils.getGFrame(blackboard)) != JFileChooser.APPROVE_OPTION) {
            return null;
        }
        File file = chooser.getSelectedFile();
        if (!writes(file)) {
            file = new File(file.getPath() + "." + ge.getExtension());
        }
        GraphIOExtensionHandler.defaultFile = file.getPath();
        if (file.isFile() && !UserNotifier.ask("Replace file",
                file.getName() + " already exists. Do you want to replace it?", "Replace", "Cancel", false)) {
            return null;
        }
        return file;
    }

    /**
     * Writes the graph to the file on a background thread and reports how it went.
     *
     * @param g    the graph
     * @param file the file to write
     */
    public void write(GraphModel g, File file) {
        GTabbedGraphPane.showNotificationMessage("Saving " + file.getName() + "…", blackboard, true);
        Thread t = new Thread(() -> {
            try {
                ge.write(file, g);
                GraphFiles.remember(g, file);
                SwingUtilities.invokeLater(() -> GTabbedGraphPane.showTimeNotificationMessage(
                        "Saved " + g.getLabel() + " to " + file.getName(), blackboard, 4000, true));
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    GTabbedGraphPane.hideNotificationMessage(blackboard);
                    UserNotifier.report("Saving " + file.getName(), e);
                });
            }
        }, "graphtea-save");
        t.start();
    }
}
