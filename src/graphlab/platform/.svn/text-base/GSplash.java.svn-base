// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

/*
 * Splash.java
 *
 * Created on September 19, 2005, 8:09 PM
 */
 package graphlab.platform;

import graphlab.platform.core.exception.ExceptionHandler;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;

/**
 * an splash screen for showing when the program is loading,
 * it simply redirects the System.out, so every thing you write
 * on System.out will be written on the splash!
 *
 * @author azin
 */
public class GSplash extends javax.swing.JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 7023272493672748876L;
    public PrintWriter pp;
    BufferedReader br;
    private boolean show;
    PrintStream defaultOut;
    private PrintStream out;

    /**
     * Creates new form Splash
     */
    public GSplash() {
        initComponents();
//        pack();
        PipedOutputStream src = new PipedOutputStream();
        PipedInputStream p = null;
        try {
            p = new PipedInputStream(src);
        } catch (IOException e) {
            ExceptionHandler.catchException(e);
        }
        pp = new PrintWriter(src);
        BufferedInputStream bbr = new BufferedInputStream(p);
        br = new BufferedReader(new InputStreamReader(bbr));
        out = new PrintStream(src);
        defaultOut = System.err;
    }

    public PrintWriter getOut() {
        return pp;
    }

    public void showMessages() {
        show = true;
        setVisible(true);
        System.setErr(out);
        new Thread() {
            public void run() {
                String s = "";
                boolean firstTime = true;
                long time = System.currentTimeMillis();
                while (show) {
                    try {
//                        Thread.sleep(100);
//                        if (br.ready()) {
                        String l = br.readLine();
                        s = l + "\n" + s;
//                        defaultOut.println(l);
                        text.setText(s);
                        defaultOut.println(l);
                        if (time + 4000 > System.currentTimeMillis())
                            toFront();
                        firstTime = false;
//                        }
                    } catch (Exception e) {
//                        ExceptionHandler.catchException(e);
                        System.err.println("err?");
                        show = false;
                    }
                }
            }
        }.start();
    }

    public void stopShowing() {
        show = false;
        System.setErr(defaultOut);
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     */
    private void initComponents() {
        text = new javax.swing.JTextArea();
        bg = new javax.swing.JLabel();

        getContentPane().setLayout(null);

//        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
//        setAlwaysOnTop(true);
        toFront();
        setResizable(false);
        setUndecorated(true);
        text.setEditable(false);
        text.setFont(new java.awt.Font("dialog", 20, 9));
        text.setOpaque(false);
        add(text);
        text.setBounds(40, 200, 400, 150);

        bg.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Class<? extends GSplash> clazz = getClass();
        URL resource = clazz.getResource("splash.jpg");
        if (resource == null)
            resource = clazz.getResource("splash.gif");
        if (resource == null)
            resource = clazz.getResource("splash.png");
        if (resource == null)
            resource = clazz.getResource("splash.bmp");
        if (resource != null) {
            ImageIcon icon = new ImageIcon(resource);
            bg.setIcon(icon);
            bg.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
        }
        bg.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        add(bg);
        super.setPreferredSize(bg.getSize());
        super.setSize(bg.getSize());
        Dimension sz = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension s = getSize();
        setLocation((sz.width - s.width) / 2, (sz.height - s.height) / 2);
    }

    // Variables declaration - do not modify
    private javax.swing.JLabel bg;
    private javax.swing.JTextArea text;
    // End of variables declaration

}
