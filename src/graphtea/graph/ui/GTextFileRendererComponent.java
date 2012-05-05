// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.graph.ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Ali Ershadi
 */
public class GTextFileRendererComponent extends JScrollPane {
    public GTextFileRendererComponent(File f) {
        try {
            JTextArea jta = new JTextArea();
            Scanner sc = new Scanner(f);
            String s = "";
            JViewport jvp = new JViewport();

            while (sc.hasNextLine())
                s += sc.nextLine() + "\n";
            jta.setText(s);
            jta.setBackground(new Color(200, 200, 255));
            jta.setEditable(false);
            jta.setFont(new Font("Sans Roman", 0, 14));
            jvp.add(jta);
            this.setViewport(jvp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
