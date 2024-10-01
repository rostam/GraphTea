// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.reports.extension;

import graphtea.extensions.reports.boundcheck.forall.IterGraphs;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.main.GraphData;
import graphtea.ui.UIUtils;
import graphtea.ui.components.gpropertyeditor.GCellRenderer;
import graphtea.ui.components.utils.GFrameLocationProvider;
import graphtea.ui.extension.AbstractExtensionAction;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;

/**
 * @author M. Ali Rostami - Conjecture check
 * @author Hooman Mohajeri Moghaddam - added save button, fixed recalculate button
 * @author azin azadi

 */
public class GraphReportExtensionAction extends AbstractExtensionAction {
    protected GraphReportExtension mr;
    public static IterGraphs ig = null;

    public GraphReportExtensionAction(BlackBoard bb, GraphReportExtension gg) {
        super(bb, gg);
        this.mr = gg;
    }

    public String getParentMenuName() {
        return "Reports";
    }

    public Object performExtensionInCommandLine() {
        return mr.calculate(new GraphData(blackboard).getGraph());
    }

    Component rendererComponent;
    Component ContentPane;
    JDialog jd;
    JFileChooser fileChooser;

    public void performExtension() {
        //        if (testAndSetParameters(gr)) {
        new Thread() {
            Object result = new Object();

            public void run() {
                if (ig != null && ig.activeConjCheck && !mr.getName().equals("Bound Check")) {
                    result = ig.wrapper(mr);
                } else {
                    result = mr.calculate(new GraphData(blackboard).getGraph());
                }
                if (result == null)
                    return;
// Your existing code...
                jd = new JDialog(UIUtils.getGFrame(blackboard));
                jd.setVisible(true);
                jd.setTitle(mr.getName());
                jd.setLayout(new BorderLayout());

// Create a JPanel with padding
                JPanel contentPanel = new JPanel(new BorderLayout());
                contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Set padding (top, left, bottom, right)

// Add components to the contentPanel
                contentPanel.add(new JLabel(mr.getDescription() + ':'), BorderLayout.NORTH);
                rendererComponent = GCellRenderer.getRendererFor(result);
                rendererComponent.setEnabled(true);
// Add vertical spacing above the rendererComponent
                contentPanel.add(Box.createRigidArea(new Dimension(100, 10)));  // Same spacing above result

// Add the rendererComponent directly to contentPanel
                contentPanel.add(rendererComponent, BorderLayout.CENTER);
// Add the contentPanel to the dialog
                jd.add(contentPanel, BorderLayout.CENTER);

                JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                JButton recalc = new JButton("Recalculate");
                panel.add(recalc);
                contentPanel.add(panel, BorderLayout.SOUTH);  // Add the button panel to the contentPanel

                recalc.addActionListener(actionEvent -> {
                    Object result = mr.calculate(new GraphData(blackboard).getGraph());
                    contentPanel.remove(rendererComponent);
                    rendererComponent = GCellRenderer.getRendererFor(result);
                    rendererComponent.setEnabled(true);
                    contentPanel.add(rendererComponent, BorderLayout.CENTER);
                    jd.pack();  // Resize the dialog to fit the new contents
                    jd.repaint();
                });
                fileChooser = new JFileChooser();
                JButton save = new JButton("Save");
                panel.add(save);
                save.addActionListener(actionEvent -> {

                    fileChooser.addChoosableFileFilter(new FileFilter() {

                        @Override
                        public String getDescription() {
                            return "*.txt";
                        }

                        @Override
                        public boolean accept(File arg0) {
                            if (arg0.isDirectory())
                                return true;
                            else {
                                String path = arg0.getAbsolutePath().toLowerCase();
                                return path.endsWith("txt") && (path.charAt(path.length() - 4)) == '.';
                            }
                        }
                    });
                    fileChooser.setDialogTitle("Choose a file");
                    fileChooser.showSaveDialog(jd);
                    try {
                        File curFile = fileChooser.getSelectedFile();
                        FileWriter fw = new FileWriter(curFile);
                        JViewport viewp = ((JScrollPane) rendererComponent).getViewport();
                        if (viewp.getView() instanceof JTable) {
                            JTable table = (JTable) viewp.getView();
                            for (int row = 0; row < table.getRowCount(); row++) {
                                for (int col = 0; col < table.getColumnCount(); col++) {
                                    if (col != table.getColumnCount() - 1) {
                                        fw.write(table.getValueAt(row, col) + ",");
                                    } else {
                                        fw.write(table.getValueAt(row, col).toString());
                                    }
                                }
                                fw.write("\n");
                            }
                            fw.close();
                        } else {
                            JList list = (JList) viewp.getView();
                            fw.write(list.toString());
                            fw.close();
                        }
                        JOptionPane.showMessageDialog(jd, "Saved to file successfully.");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });

                jd.add(panel, BorderLayout.SOUTH);
                jd.setLocation(GFrameLocationProvider.getPopUpLocation());
                jd.pack();

            }
        }.start();
    }
}
