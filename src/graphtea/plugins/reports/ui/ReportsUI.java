// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.reports.ui;

import de.neuland.jade4j.Jade4J;
import graphtea.graph.ui.GHTMLPageComponent;
import graphtea.platform.StaticUtils;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.extension.Extension;
import graphtea.platform.extension.ExtensionLoader;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.reports.extension.GraphReportExtension;
import graphtea.plugins.reports.extension.GraphReportExtensionHandler;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;


/**
 * @author azin azadi
 */
public class ReportsUI {
    GraphData graphData;

    public static ReportsUI self = null;
    public GHTMLPageComponent html;
    private final BlackBoard blackboard;
    public JPanel sidebar;

    public ReportsUI(BlackBoard b) {
        super();
        self = this;
        this.blackboard = b;
        graphData = new GraphData(b);
        initComponents();
    }

    private void initComponents() {
        sidebar = new JPanel(new BorderLayout(0, 0));
        html = new GHTMLPageComponent(blackboard);
        html.setPreferredSize(new Dimension(400, 100));
        sidebar.setPreferredSize(new Dimension(400, 100));
        sidebar.add(html, BorderLayout.CENTER);
    }

    public void initTable() {
        String h = "";
        HashMap<String, Object> model = new HashMap<>();
        Vector<Extension> reports = ExtensionLoader.extensionsList.get(GraphReportExtensionHandler.class);
        HashSet<String> categories = new HashSet<>();
        HashMap<String, Vector<GraphReportExtension>> categoryLists = new HashMap<>();
        for (Extension r : reports) {
            GraphReportExtension report = (GraphReportExtension) r;
            String category = report.getCategory();
            categories.add(category);
            if (!categoryLists.containsKey(category)){
                categoryLists.put(category, new Vector<>());
            }
            categoryLists.get(category).add(report);
        }

        model.put("reports", reports);
        model.put("categories", categories);
        model.put("categoryLists", categoryLists);
        try {
            h = Jade4J.render(getClass().getResource("sidebar.jade"), model);
        } catch (IOException e) {
            StaticUtils.addExceptionLog(e);
        }
        System.out.println(h);
        html.setHTML(h);
    }
}