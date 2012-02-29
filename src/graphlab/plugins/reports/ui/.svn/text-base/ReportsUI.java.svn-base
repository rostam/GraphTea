// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.reports.ui;

import graphlab.platform.core.BlackBoard;
import graphlab.platform.parameter.Parametrizable;
import graphlab.plugins.main.GraphData;
import graphlab.plugins.reports.extension.GraphReportExtension;
import graphlab.ui.AttributeSetView;
import graphlab.ui.ParameterShower;
import graphlab.ui.PortableNotifiableAttributeSetImpl;
import graphlab.ui.components.gpropertyeditor.GPropertyEditor;
import graphlab.ui.components.gpropertyeditor.GPropertyTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author azin azadi

 */
public class ReportsUI implements ActionListener {
    JButton recalculateBtn = new JButton("Recalculate");
    GPropertyEditor propEd = new GPropertyEditor();
    GraphData graphData;
    ArrayList<GraphReportExtension> reports = new ArrayList<GraphReportExtension>();
    HashMap<String, GraphReportExtension> reportByName = new HashMap<String, GraphReportExtension>();

    PortableNotifiableAttributeSetImpl reportResults = new PortableNotifiableAttributeSetImpl();

    //    private blackboard blackboard;
    JFrame frm = new JFrame("Reports");

    public ReportsUI(BlackBoard b, boolean init) {
        super();
        if (init)
            initComponents();
        propEd.connect(reportResults);
        BlackBoard blackboard = b;
        graphData = new GraphData(blackboard);
    }

    private void initComponents() {
        BorderLayout lom = new BorderLayout(0, 0);
        frm.setLayout(lom);
        frm.add(initWrapper());
        frm.pack();
    }

    JPanel initWrapper() {
        JPanel wrapper = new JPanel(new BorderLayout(0, 0));
        wrapper.setPreferredSize(new Dimension(150, 100));
        wrapper.add(propEd, BorderLayout.CENTER);
        wrapper.add(recalculateBtn, BorderLayout.SOUTH);
        recalculateBtn.addActionListener(this);
//        recalculateBtn.setSize(100, 20);
//        propEd.getTable().addColumn(new TableColumn(2,150));
        propEd.getTable().addNotify();
        propEd.getTable().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = propEd.getTable().rowAtPoint(e.getPoint());
                    GPropertyTableModel gtm = (GPropertyTableModel) propEd.getTable().getModel();
                    ParameterShower ps = new ParameterShower();
                    String name = (String) gtm.getValueAt(row, 0);
                    GraphReportExtension o = reportByName.get(name);
                    if (o instanceof Parametrizable)
                        if (ps.xshow(o)) {
                            reCalculateReport(name);
                        }
                }
            }
        });
        return wrapper;
    }
//    public Component getComponent(blackboard b) {
//        return ;
//    }

    public void addReport(GraphReportExtension gre) {
        reports.add(gre);
//        reCalculateReports();
    }

    public void show() {
        frm.setVisible(true);
        reCalculateReports();
    }

    public void hide() {
        frm.setVisible(false);
    }

    //recalc button pressed
    public void actionPerformed(ActionEvent e) {
        new Thread() {
            public void run() {
                reCalculateReports();
            }
        }.start();
    }

    public void reCalculateReports() {
        if (graphData.getGraph() == null)
            return;
        for (GraphReportExtension gre : reports) {
            String name = gre.getName();
            reportResults.put(name, gre.calculate(graphData));
            reportByName.put(name, gre);
            AttributeSetView view = reportResults.getView();
            view.setEditable(name, false);
            view.setDescription(name, gre.getDescription());
        }
        propEd.connect(reportResults);
    }

    public void reCalculateReport(String reportName) {
        GraphReportExtension gre = reportByName.get(reportName);
        reportResults.put(reportName, gre.calculate(graphData));
    }
}