// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui.components.gsidebar;


import graphtea.platform.core.BlackBoard;
import graphtea.ui.components.gbody.GBody;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

/**
 * this class is the sidebar part of GFrame
 * Author: Azin Azadi
 */
public class GSidebar extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = -599129598320886343L;
    private final GBody targetBody;

    /**
     * constructor
     */
    public GSidebar(GBody targetBody, BlackBoard blackboard) {
        this.targetBody = targetBody;
        initComponents();
    }

    private void initComponents() {
        BoxLayout mgr = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(mgr);
    }

    /**
     * adds a button to side bar, and match it to component
     */
    public void addButton(String iconFileName, Component component, String label) {
        addButton(new ImageIcon(iconFileName), component, label);
    }

    public void addButton(URL iconURl, Component component, String label) {
        addButton(new ImageIcon(iconURl), component, label);
    }

    public void addButton(Icon icon, Component c, String label) {
        GSidebarButton b = new GSidebarButton(icon, c, this, label);
        if (label.equals("shell"))
            add(b,1);
        else add(b);
        validate();
    }

    /**
     * sets the currently viewable panel
     */
    public void setPanel(Component sidePanel, String label) {
        targetBody.showSideBarPane(sidePanel, label);
    }

    /**
     * hides the panel from the screen
     */
    public void hidePanel() {
        targetBody.hideSideBar();
    }
}

/**
 * java currently (now we have jdk 1.5) does not supporots "Vertical Text"
 * yes, yes, i know that it's a shame for java.
 * i searched the internet for some hours to find that how can i write a String vertically in any way on the screen
 * you know that the side bar buttons are vertically, and the texts in JButtons is aligned vertically
 * I suggested some ways for doing this.
 * 1- the texts in the swing can be HTML, i mean that you can write for ex. b.setText("<html><font color="red">asda</font></html>");
 * we used this method in hour vertices to color out texts.
 * so if we have the ability of writing text vertically in HTML, then we can use this to have the vertical buttons,
 * i had sawn vertical texts, in some pages using Internet Explorer. but when i search for such a future , it seems
 * that this feautre is not a standard HTML feature, and just supported by IE.
 * 2- we can build a system from base, like the current system. current sytem write horizentally, and we can build our
 * system writing vertically, independent of the old system.
 * this way is hard to implement. it needs a great amount of work to support all the features of such a system.
 * when i was searching the internet for finding a solution, i find a project for writing the texts vertically , and
 * it used the way i said above, and also it was free!
 * but i didn't like to use the system in GraphTea project. because i think that it adds too complexitiy to GraphTea
 * and also i think that the one that should do the jub is SUN. so i think that it is better that wait until the sun
 * implement this feature, and i preffered a third solution.
 * 3- our slide bar button will not more than 10. so simply we can just take a picture from input and display it
 * , so the user can put every thing on the picture including vertical texts.
 * 4- and there is another way , that i think it is a hack!
 * we can write the texts horizontally , then take a picture of it, and then simply rotate the text. :D
 * but in graphtea i preffer to use the 3rd way because of its simplicity.
 * --------------
 * 5- roozbeh suggests a solution for the problem. he shows the Sun Java 2D demos, that are in the JDK Demos. there
 * was a demo of fonts in java2d. it seems that it can be very simple to rotate the texts in java2d.
 */
class GVerticalButton extends JToggleButton {

    /**
     *
     */
    private static final long serialVersionUID = 4372060475633555488L;
    //todo: inja hast ta badan por beshe :D. (to be filled later)


}

//fek mikonam age az jense togle button bashan behtar bashe

class GSidebarButton extends GVerticalButton implements ActionListener {
    /**
     *
     */
    private static final long serialVersionUID = -3299575618889083096L;
    private final Component sidePanel;
    private final GSidebar sidebar;
    private final String label;

    public GSidebarButton(Icon icon, Component sidepanel, GSidebar sidebar, String label) {
        //todo: in sidePanel shaiad lazem she jaie JPanel ie chizi too maiehaie sidebarpanel bashe. felan ke lozoomi nemibinam
        this.sidePanel = sidepanel;
        this.sidebar = sidebar;
        this.label = label;
        setIcon(icon);

        if (icon.getIconHeight() == -1) //if the icon was not loaded succesfully
            setText("|");
        setBorder(new LineBorder(Color.gray, 1, true));
        //setPreferredSize(new Dimension(icon.getIconWidth() + 2, 2 + icon.getIconHeight()));
        addActionListener(this);
    }

    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e) {
//        if (super.isSelected())
        sidebar.setPanel(sidePanel, label);
//        else
//            sidebar.hidePanel();
    }

}