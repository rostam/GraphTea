// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.ui.components.gpropertyeditor.utils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JFontChooser extends JComponent {
    /**
     *
     */
    private static final long serialVersionUID = 660143495474577287L;
    public static int OK_OPTION = 0;
    public static int CANCEL_OPTION = 1;

    private JList fontList, sizeList;
    private JCheckBox cbBold, cbItalic;
    private JTextArea txtSample;

    private String[] sizes = new String[]
            {"2", "4", "6", "8", "10", "12", "13", "14", "16", "18", "20", "22", "24", "30", "36", "48", "72"};

    public JFontChooser() {
        // create all components

        fontList = new JList(GraphicsEnvironment.getLocalGraphicsEnvironment().
                getAvailableFontFamilyNames()) {
            /**
             *
             */
            private static final long serialVersionUID = 2307765155498619149L;

            public Dimension getPreferredScrollableViewportSize() {
                return new Dimension(150, 144);
            }
        };
        fontList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        sizeList = new JList(sizes) {
            /**
             *
             */
            private static final long serialVersionUID = -2474666139561694389L;

            public Dimension getPreferredScrollableViewportSize() {
                return new Dimension(25, 144);
            }
        };
        sizeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        cbBold = new JCheckBox("Bold");

        cbItalic = new JCheckBox("Italic");


        txtSample = new JTextArea() {
            /**
             *
             */
            private static final long serialVersionUID = 1805024865116989603L;

            public Dimension getPreferredScrollableViewportSize() {
                return new Dimension(385, 80);
            }

            public void paint(Graphics g) {
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                super.paint(g);
            }
        };
        txtSample.setText("The quick brown fox jumped over the fence");

        // set the default font

        setFont(null);

        // add the listeners

        ListSelectionListener listListener = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                txtSample.setFont(getCurrentFont());
            }
        };

        fontList.addListSelectionListener(listListener);
        sizeList.addListSelectionListener(listListener);


        ActionListener cbListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtSample.setFont(getCurrentFont());
            }
        };

        cbBold.addActionListener(cbListener);
        cbItalic.addActionListener(cbListener);

        // build the container

        setLayout(new java.awt.BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new java.awt.BorderLayout());

        leftPanel.add(new JScrollPane(fontList), java.awt.BorderLayout.CENTER);
        leftPanel.add(new JScrollPane(sizeList), java.awt.BorderLayout.EAST);

        add(leftPanel, java.awt.BorderLayout.CENTER);


        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new java.awt.BorderLayout());

        JPanel rightPanelSub1 = new JPanel();
        rightPanelSub1.setLayout(new java.awt.FlowLayout());

        rightPanelSub1.add(cbBold);
        rightPanelSub1.add(cbItalic);

        rightPanel.add(rightPanelSub1, java.awt.BorderLayout.NORTH);

        JPanel rightPanelSub2 = new JPanel();
        rightPanelSub2.setLayout(new java.awt.GridLayout(2, 1));

        rightPanel.add(rightPanelSub2, java.awt.BorderLayout.SOUTH);

        add(rightPanel, java.awt.BorderLayout.EAST);

        add(new JScrollPane(txtSample), java.awt.BorderLayout.SOUTH);

        setSize(200, 200);
    }

    public void setFont(Font font) {
        if (font == null) font = txtSample.getFont();

        //fontList.setSelectedValue(font.getName(), true);
        //fontList.ensureIndexIsVisible(fontList.getSelectedIndex());
        //sizeList.setSelectedValue("" + font.getSize(), true);
        //sizeList.ensureIndexIsVisible(sizeList.getSelectedIndex());

        //cbBold.setSelected(font.isBold());
        //cbItalic.setSelected(font.isItalic());
    }

    public Font getFont() {
        return getCurrentFont();
    }

    private Font getCurrentFont() {
        String fontFamily = (String) fontList.getSelectedValue();
        int fontSize=24;
        if(sizeList.getSelectedValue()!=null)
            fontSize = Integer.parseInt((String) sizeList.getSelectedValue());

        int fontType = Font.PLAIN;

        if (cbBold.isSelected()) fontType += Font.BOLD;
        if (cbItalic.isSelected()) fontType += Font.ITALIC;

        return new Font(fontFamily, fontType, fontSize);
    }
}