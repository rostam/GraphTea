// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.automaticupdator.net.interdirected.autoupdate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Michael Quattlebaum
 */
public class GuiStatusScreen extends JPanel {
    static ScrollingJList jl;
    static JButton button;
    private static final long serialVersionUID = -6251102856738091493L;

    GuiStatusScreen(String title, String detailtext, int width, int height, String buttontext, boolean showbutton, String imageurl, int imagew, int imageh) {
        Font screenfont = new Font("sanserif", Font.PLAIN, 12);
        Font buttonfont = new Font("sanserif", Font.BOLD, 12);
        final JFrame frame = new JFrame(title);
        button = new JButton(buttontext);
        button.setEnabled(false);
        button.setFont(buttonfont);
        JLabel label = new JLabel(detailtext);
        label.setFont(screenfont);
        JLabel space = new JLabel();
        label.setAlignmentX(JLabel.LEFT_ALIGNMENT);

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.getContentPane().setLayout(new FlowLayout());
        if (imageurl != null) {
            JLabel image = new JLabel();
            if (imageurl.indexOf("http:") > -1) {
                image.setText("<HTML><IMG SRC='" + imageurl + "'></HTML>");
            } else {
                ImageIcon icon = new ImageIcon(imageurl);
                image.setIcon(icon);
            }
            frame.getContentPane().add(image, BorderLayout.NORTH);
        }
        GuiStatusScreen.jl = new ScrollingJList(width, height, imageh, imagew);
        GuiStatusScreen.jl.list.setModel(new DefaultListModel());
        frame.getContentPane().add(label, BorderLayout.NORTH);
        frame.getContentPane().add(space, BorderLayout.NORTH);
        frame.getContentPane().add(GuiStatusScreen.jl, BorderLayout.CENTER);
        if (showbutton) frame.getContentPane().add(button, BorderLayout.SOUTH);
        frame.setMinimumSize(new Dimension(width, height));
        frame.setPreferredSize(new Dimension(width, height));
        frame.setSize(new Dimension(width, height));
        //May want to set to true with a more complex Flow Layout
//	    frame.setResizable(false);

        button.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        // Button Actions go here.
                        frame.setVisible(false);
                    }
                }
        );

        frame.setVisible(true);

    }

    /**
     *
     */
    public void enableButton() {
        button.setEnabled(true);
    }

    /**
     * @param text Text to append to the end of the status window.
     */
    public void appendText(String text) {
        DefaultListModel dlm = (DefaultListModel) GuiStatusScreen.jl.list.getModel();
        dlm.addElement((Object) text);
        GuiStatusScreen.jl.list.ensureIndexIsVisible
                (GuiStatusScreen.jl.list.getModel().getSize() - 1);
    }

}

/**
 * @author Michael Quattlebaum
 */
class ScrollingJList extends JPanel {
    private static final long serialVersionUID = -6251102856738091493L;
    JList list;
    int width;
    int height;
    int iw;
    int ih;

    /**
     * @param w Width of the application window
     * @param h Height of the application window
     */
    public ScrollingJList(int w, int h, int imagew, int imageh) {
        setLayout(new BorderLayout());
        Font listfont = new Font("monospaced", Font.PLAIN, 11);
        list = new JList();
        list.setFont(listfont);
        add(new JScrollPane(list));
        width = w;
        height = h;
        iw = imagew;
        ih = imageh;
    }

    /* (non-Javadoc)
      * @see javax.swing.JComponent#getPreferredSize()
      */
    public Dimension getPreferredSize() {
        return new Dimension(width - 50, height - ih - 80);
    }

}