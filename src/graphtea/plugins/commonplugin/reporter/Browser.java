// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.commonplugin.reporter;

import graphtea.ui.components.utils.GFrameLocationProvider;
import graphtea.platform.core.exception.ExceptionHandler;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

public class Browser extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = -9157914659496711380L;

    protected JTextField urlField;
    protected JEditorPane browserPane;


    public Browser() {
        super("Browser");
        setPreferredSize(GFrameLocationProvider.getPopUpSize());
        setLocation(GFrameLocationProvider.getPopUpLocation());
        // browser pane
        browserPane = new JEditorPane();
        browserPane.setContentType("text/html");
        browserPane.setEditable(false);
        browserPane.addHyperlinkListener(new HyperActive());

        // create content pane
        JPanel contentPane = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(browserPane);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.setPreferredSize(new Dimension(500, 300));
        setContentPane(contentPane);
    }

    public static boolean browse(URL url) {
        try {
            Desktop.getDesktop().browse(url.toURI());
            return true;
        } catch (Exception e) {
            ExceptionHandler.catchException(e);
            return false;
        }
    }

    public boolean browseInternal(URL url) {
        try {
            browserPane.setPage(url);
            return true;
        } catch (IOException e) {
            System.err.println("Attempted to read a bad URL: " + url);
            return false;
        }
    }

    public static boolean browse(String htmlText) {
        try {
            File temp = File.createTempFile("graphtea", ".html");
            temp.deleteOnExit();
            BufferedWriter out = new BufferedWriter(new FileWriter(temp));
            out.write(htmlText);
            out.close();
            browse(temp.toURL());
            return true;
        } catch (IOException e) {
            ExceptionHandler.catchException(e);
            return false;
        }
    }

    public boolean browseInternal(String htmlText) {
        browserPane.setText(htmlText);
        System.out.println(htmlText);
        return true;
    }
}