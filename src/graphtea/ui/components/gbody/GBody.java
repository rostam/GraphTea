// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui.components.gbody;

import graphtea.platform.lang.Pair;
import graphtea.ui.components.gsidebar.GSideBarPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * this class is the Body of the GFrame, it is important that the word "Body" here means the body of program plus the
 * side bar of the program, so it contains a Body pane, and a Split pane . which a vertical split is between them.
 *
 * @author azin azadi
 */
public class GBody extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 2389438929191166213L;
    private JSplitPane splitPane = new JSplitPane();
    private Component bodyPane;
    //is side bar hidden
    private boolean hidden = false;

    private ArrayList<Pair<Component, String>> showingSideBars = new ArrayList<Pair<Component, String>>();

    public void setBodyPane(Component bodyPane) {
        this.bodyPane = bodyPane;
        splitPane.setRightComponent(bodyPane);
        if (hidden)
            hideSideBar();

    }

    public void showSideBarPane(Component leftPanel, String label) {
//        int _ = splitPane.getDividerLocation();
        Pair<Component, String> p = new Pair<Component, String>(leftPanel, label);
        if (showingSideBars.contains(p)) {
            return;
        }
        showingSideBars.add(p);

        updateEveryThingInsidebar();

//        splitPane.setDividerLocation(_);
//        splitPane.setDividerLocation(leftPanel.getPreferredSize().width);
//        spitPane.setDividerSize(2);
    }

    private void updateEveryThingInsidebar() {
        JPanel sp = createTotalSideBarPanel();
        hidden = false;
        splitPane.setRightComponent(bodyPane);
        splitPane.setLeftComponent(sp);
        add(splitPane);
        validate();
    }

    private JPanel createTotalSideBarPanel() {
        JPanel sp = new JPanel();
        makeJPanelFlat(sp);
        JPanel cur = sp;
        Iterator<Pair<Component, String>> it = showingSideBars.iterator();
        while (it.hasNext()) {
            Pair<Component, String> _ = it.next();
            GSideBarPanel sbp = new GSideBarPanel(this, _.first, _.second);
            if (it.hasNext()) {
                cur.add(new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, sbp, cur = new JPanel()));
                makeJPanelFlat(cur);
            } else {
                cur.add(sbp);
            }
        }
        return sp;
    }

    private void makeJPanelFlat(JPanel p) {
        p.setBorder(null);
        p.setLayout(new BorderLayout(0, 0));
    }

    public void hideSideBar(Component c, String label) {
        showingSideBars.remove(new Pair<Component, String>(c, label));
        if (showingSideBars.isEmpty()) {
            hideSideBar();
        } else {
            updateEveryThingInsidebar();
        }

    }

    public void hideSideBar() {
        splitPane.remove(bodyPane);
        remove(splitPane);
        add(bodyPane);
//        splitPane.setDividerLocation(0);
        hidden = true;
        validate();
//        splitPane.setDividerSize(0);
    }
//    private Component rightPanel = new JPanel();
//    private Component leftPanel = new JPanel();

    public GBody() {
        initComponents();
    }

    private void initComponents() {
        setBodyPane(new Container());
        hideSideBar();
//        setBorder(new LineBorder(Color.red,1,true));//new EmptyBorder(0,0,0,0));
        setLayout(new BorderLayout());
//        add(splitPane);
        setBorder(null);
        splitPane.setBorder(null);


    }
}