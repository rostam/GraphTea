// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.samples.ui.texteditor.myplugin;

import graphlab.platform.core.BlackBoard;
import graphlab.samples.ui.texteditor.myplugin.actions.Utils;
import graphlab.ui.UI;
import graphlab.ui.UIUtils;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import java.io.IOException;

/**
 * The main class of GraphLab notepad sample.
 * <p/>
 * see http://graphlab.sharif.ir/trac/wiki/XMLBasedUI
 * @author Azin Azadi aazadi@gmail.com
 */
public class UISample {
    public static void main(String[] args) throws IOException, SAXException, IllegalAccessException, UnsupportedLookAndFeelException, InstantiationException, ClassNotFoundException {
        //set the look and feel
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        //load xml file
        BlackBoard blackBoard = new BlackBoard();
        UI u = new UI(blackBoard, true);
        u.loadXML("myui.xml", UISample.class);

        //gets the component which is created when loading status bar.
        //according to myui.xml it's id is "statusbar"
        final JLabel lbl = (JLabel) UIUtils.getComponent(blackBoard, "statusbar");
        final JTextArea editor = Utils.getMainEditor(blackBoard);

        //sets the lbl to show current row and colomn of caret in text editor
        editor.addCaretListener(new CaretListener() {
            public void caretUpdate(CaretEvent e) {
                try {
                    int caretPosition = editor.getCaretPosition();
                    int line = editor.getLineOfOffset(caretPosition);
                    int col = caretPosition - editor.getLineStartOffset(line);
                    lbl.setText(line + ":" + col);
                } catch (BadLocationException e1) {
                    e1.printStackTrace();
                }
            }
        });
        u.getGFrame().setVisible(true);
    }
}
