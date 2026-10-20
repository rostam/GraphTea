// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.ui.extension;

import graphtea.graph.graph.GraphModel;
import graphtea.platform.core.AEvent;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.extension.Extension;
import graphtea.platform.lang.NaturalOrder;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.graphgenerator.core.extension.GraphGeneratorExtension;
import graphtea.plugins.main.extension.GraphActionExtension;
import graphtea.plugins.reports.extension.GraphReportExtension;
import graphtea.ui.ParameterShower;
import graphtea.ui.UIUtils;
import graphtea.ui.components.ExtensionConfigFrame;
import graphtea.ui.components.GFrame;
import graphtea.ui.components.gmenu.GMenuItem;
import graphtea.ui.xml.UIHandlerImpl;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * the base class for creating extension handlers
 * the implementing class will have a menu assigned to it automatically the name of the menu will be
 * from the constructors parameter(sp) and the will also listen to UI.getUIEvent(sp.getName())
 *
 * @author azin azadi
 */
public abstract class AbstractExtensionAction<t extends Extension> extends AbstractAction implements ActionListener {
    //todo: Write how to create new extension types document.
    protected JMenu parentMenu;
    public GMenuItem menuItem;
    ExtensionConfigFrame ecf;
    /**
     * a button which will be added to the right of menu item
     */
    protected JButton extraButton;
    public final String actionId;

    public t getTarget() {
        return target;
    }

    public t target;


    /** Category submenus, keyed by their path from the top-level menu. */
    private static final Map<String, JMenu> categoryMenus = new HashMap<>();

    /** Marks menu entries placed by {@link #insertSorted}, as opposed to ones from the UI XML. */
    private static final String SORTED_ENTRY = "graphtea.menu.sortedEntry";

    /** Submenus before commands, each group in natural order of its label. */
    private static final Comparator<JMenuItem> MENU_ORDER = Comparator
            .comparing((JMenuItem m) -> !(m instanceof JMenu))
            .thenComparing(JMenuItem::getText, NaturalOrder.INSTANCE);

    public AbstractExtensionAction(BlackBoard bb, t sp) {
        super(bb);
        target = sp;
        String name = getMenuNamePrefix() + sp.getName();
        actionId = name + sp.getDescription() + target.getClass().getName();
        listen4Event(UIUtils.getUIEventKey(actionId));
        if (!name.isEmpty()) {
            menuItem = createMenuItem(name, actionId, bb);
            parentMenu = getParentMenu();
            if (parentMenu != null) {
                JMenu menu = parentMenu;
                String path = parentMenu.getText();
                for (String level : categoryPath(sp)) {
                    path += "/" + level;
                    menu = categoryMenu(menu, path, level);
                }
                insertSorted(menu, menuItem);
            }
        }
    }

    /**
     * The submenus an extension's entry is filed under. A report category of the form
     * "Verification-Degree" is two levels deep.
     */
    private static List<String> categoryPath(Extension sp) {
        String category = null;
        boolean nested = false;
        if (sp instanceof GraphReportExtension<?> report) {
            category = report.getCategory();
            nested = true;
        } else if (sp instanceof GraphGeneratorExtension generator) {
            category = generator.getCategory();
        } else if (sp instanceof GraphActionExtension action) {
            category = action.getCategory();
        }
        if (category == null || normalizeLabel(category).isEmpty()) {
            return List.of();
        }
        int dash = category.indexOf('-');
        if (!nested || dash < 0) {
            return List.of(normalizeLabel(category));
        }
        return List.of(normalizeLabel(category.substring(0, dash)), normalizeLabel(category.substring(dash + 1)));
    }

    /**
     * The submenu at {@code path}, created under {@code parent} the first time it is asked for.
     *
     * <p>Submenus were previously cached by their own label alone, so two categories with the
     * same second level under different first levels would have shared, and fought over, a
     * single menu.
     */
    private static JMenu categoryMenu(JMenu parent, String path, String label) {
        JMenu menu = categoryMenus.computeIfAbsent(path, k -> new JMenu(label));
        insertSorted(parent, menu);
        return menu;
    }

    /**
     * Inserts an entry among the other extension entries of {@code parent} in natural order of
     * its label, after any entries the UI XML placed there.
     *
     * <p>The order used to come from {@link #getMenuPlace()}, which looked at only the first two
     * characters of a name: "Chain Graph 1" to "Chain Graph 7" all tied and kept whatever order
     * the extensions happened to load in, and each category submenu took its position from
     * whichever entry created it.
     */
    static void insertSorted(JMenu parent, JMenuItem child) {
        Component[] entries = parent.getMenuComponents();
        for (Component c : entries) {
            if (c == child) {
                return;
            }
        }
        child.putClientProperty(SORTED_ENTRY, Boolean.TRUE);
        int at = -1;
        int lastSorted = -1;
        for (int i = 0; i < entries.length; i++) {
            if (entries[i] instanceof JMenuItem sibling && Boolean.TRUE.equals(sibling.getClientProperty(SORTED_ENTRY))) {
                lastSorted = i;
                if (at < 0 && MENU_ORDER.compare(child, sibling) < 0) {
                    at = i;
                }
            }
        }
        if (at < 0) {
            at = lastSorted >= 0 ? lastSorted + 1 : entries.length;
        }
        parent.insert(child, at);
    }

    /** Trims a label and collapses runs of whitespace; several extension names had stray spaces. */
    static String normalizeLabel(String label) {
        return label.strip().replaceAll("\\s+", " ");
    }

    /**
     * if you want to create custom menues for your extension (i.e. want to add some extra components to it) you should
     * override this method.
     *
     * @param name The name of the menu
     * @param actionId The action id
     * @param bb The blackboard
     * @return  The menu item
     * @see graphtea.plugins.algorithmanimator.extension.AlgorithmExtensionAction
     */
    protected final GMenuItem createMenuItem(String name, String actionId, BlackBoard bb) {
        name = normalizeLabel(name);
        int index = Math.max(name.indexOf(UIHandlerImpl.menueIndexChar), 0);
        String labelString = name.replace(UIHandlerImpl.menueIndexChar + "", "");
        if (isInsertExtraButtonToMenuItem()) {
            GMenuItem menuItem = new GMenuItem(labelString, actionId, bb, null, index) {
                public Dimension getPreferredSize() {
                    Dimension preferredSize = super.getPreferredSize();
                    Dimension bps = extraButton.getPreferredSize();
                    preferredSize.width += bps.getWidth() + 4;
                    preferredSize.height = (int) Math.max(preferredSize.height, bps.getHeight()) + 1;
                    return preferredSize;
                }

            };
            String imageURL = "/graphtea/plugins/main/resources/edit16_.gif";
            extraButton = new JButton(new ImageIcon(getClass().getResource(imageURL)));
            extraButton.setBorder(new EmptyBorder(0, 0, 0, 0));
            extraButton.addActionListener(this);
            extraButton.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    actionPerformed(null);
                }
            });
            menuItem.setLayout(new BorderLayout());
            menuItem.add(extraButton, BorderLayout.EAST);
            return menuItem;
        } else
            return new GMenuItem(name, actionId, bb);
    }

    /**
     * determines whether to insert an extra button at the right side of menu item,
     * normally this is true whenever the target extension implements Parameterizable (have some parameters)
     * on this mode on pressing the button a dialog for setting the parameters will be shown, and then
     * it will be executed, if the user clicks the menu item directly the extension will be
     * performed without asking of parameters.
     * <p/>
     * if you want to override this method for your own ExtensionAction
     * NOTE that on pressing the button the actionPerformed will be called, so you can
     * do what you want to do by overriding that method.
     *
     * @return true to insert the extra button, false to no insert it.
     * @see Parametrizable
     * @see Parameter
     * @see graphtea.plugins.algorithmanimator.extension.AlgorithmExtensionAction
     */
    protected boolean isInsertExtraButtonToMenuItem() {
        return false; //target instanceof Parameterizable; we don't want this button for now. just go with the simple interface
    }

    /**
     * to put a prefix before the name of your extension menu override this method
     */
    protected String getMenuNamePrefix() {
        return "";
    }

    /**
     * gets a menu for adding the sub menu, if returns null, no submenu will added!
     */
    protected JMenu getParentMenu() {
        GFrame f = UIUtils.getGFrame(blackboard);
        String mname = getParentMenuName();
        return f.getMenu().getUniqueMenu(mname, -1);
    }

    /**
     * first checks if o instanceof Parameterizable if so, shows an
     * editor for it's Parameters.
     *
     * @param o The object
     * @return true if o isn't an instance of Parameterizable or the user cancels the editing
     */
    public boolean testAndSetParameters(Object o) {
        if (o instanceof Parametrizable) {
            ParameterShower ps = new ParameterShower();

            return ps.show((Parametrizable) o);
        } else
            return true;
    }

    /**
     * removes all UI Components that are created for the extension (menus, ...)
     */
    public void removeCreatedUIComponents() {
        if (parentMenu != null && menuItem != null) {
            parentMenu.remove(menuItem);
            //if parent menu is empty remove it and so on.
            if (parentMenu.getMenuComponentCount() == 0) {
                parentMenu.getParent().remove(parentMenu);
            }
        }

    }

    /**
     * returns the menu name that the menuitem of this action is its child
     */
    public abstract String getParentMenuName();

    public final void performAction(String eventKey, Object value) {
        if (testAndSetParameters(target))
            performExtension();
    }

    public void track(){
        blackboard.setData("ATrack", new AEvent().category(getParentMenuName()).action(target.getName()));
    }


    /**
     * occurs whenever extraButton pressed
     * <p/>
     * =
     */
    public void actionPerformed(ActionEvent e) {
        parentMenu.setSelected(false);
        if (testAndSetParameters(target))
            performExtension();
    }

    public abstract void performExtension();
    public GraphModel generateGraph(){return new GraphModel();}

}
