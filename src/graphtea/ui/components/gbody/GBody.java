// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui.components.gbody;

import java.util.List;
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
    private final JSplitPane splitPane = new JSplitPane();
    private Component bodyPane;
    //is side bar hidden
    private boolean hidden = false;

    private final ArrayList<Pair<Component, String>> showingSideBars = new ArrayList<>();

    public void setBodyPane(Component bodyPane) {
        this.bodyPane = bodyPane;
        splitPane.setRightComponent(bodyPane);
        if (hidden)
            hideSideBar();

    }

    /**
     * Shows one panel in the sidebar, replacing whatever was there.
     *
     * <p>Panels used to accumulate: opening Properties while Reports was open stacked the two
     * in nested split panes, so each got half of an already narrow column and the reports list
     * — the longest thing in the application — was squeezed into a strip about four lines
     * high. The tabs down the left edge look mutually exclusive, so they now behave that way.
     *
     * @param leftPanel the panel to show
     * @param label     its title, shown in the panel header
     */
    public void showSideBarPane(Component leftPanel, String label) {
        Pair<Component, String> p = new Pair<>(leftPanel, label);
        if (showingSideBars.size() == 1 && showingSideBars.get(0).equals(p)) {
            return;
        }
        showingSideBars.clear();
        showingSideBars.add(p);
        updateEveryThingInsidebar();
    }

    /**
     * @param leftPanel a panel
     * @param label     its title
     * @return whether that panel is the one currently showing
     */
    public boolean isShowingSideBarPane(Component leftPanel, String label) {
        return !hidden && showingSideBars.size() == 1
                && showingSideBars.get(0).equals(new Pair<>(leftPanel, label));
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
            Pair<Component, String> stringPair = it.next();
            GSideBarPanel sbp = new GSideBarPanel(this, stringPair.first(), stringPair.second());
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
        showingSideBars.remove(new Pair<>(c, label));
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