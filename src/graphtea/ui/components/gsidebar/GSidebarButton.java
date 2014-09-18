package graphtea.ui.components.gsidebar;

//fek mikonam age az jense togle button bashan behtar bashe

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GSidebarButton extends GVerticalButton implements ActionListener {
    private static final long serialVersionUID = -3299575618889083096L;
    private Component sidePanel;
    private GSidebar sidebar;
    private String label;

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