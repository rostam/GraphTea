// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.samples.ui.texteditor.myplugin;

import graphtea.platform.core.BlackBoard;
import graphtea.samples.ui.texteditor.myplugin.actions.Utils;
import graphtea.ui.UI;
import graphtea.ui.UIUtils;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.io.IOException;

/**
 * The main class of GraphTea notepad sample.
 * <p/>
 * see http://graphtea.sharif.ir/trac/wiki/XMLBasedUI todo: [tea] link to be updated
 * @author Azin Azadi aazadi@gmail.com
 */
public class UISample {
    public static void main(String[] args) throws IOException, SAXException, IllegalAccessException, UnsupportedLookAndFeelException, InstantiationException, ClassNotFoundException {
        //set the look and feel
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        //load xml file
        BlackBoard blackBoard = new BlackBoard();
        UI u = new UI(blackBoard);
        u.loadXML("myui.xml", UISample.class);

        //gets the component which is created when loading status bar.
        //according to myui.xml it's id is "statusbar"
        final JLabel lbl = (JLabel) UIUtils.getComponent(blackBoard, "statusbar");
        final JTextArea editor = Utils.getMainEditor(blackBoard);

        //sets the lbl to show current row and colomn of caret in text editor
        editor.addCaretListener(e -> {
            try {
                int caretPosition = editor.getCaretPosition();
                int line = editor.getLineOfOffset(caretPosition);
                int col = caretPosition - editor.getLineStartOffset(line);
                lbl.setText(line + ":" + col);
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }
        });
        u.getGFrame().setVisible(true);
    }
}
