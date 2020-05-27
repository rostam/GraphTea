// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui.components.gmenu;


import graphtea.platform.core.BlackBoard;
import graphtea.ui.actions.UIEventData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * this is the same as JMenuItem with the diffrece that it sends its events to the blackboard automatically.
 * see GButton for more details.
 *
 * @author Azin Azadi
 */
//todo: like GButton
public class GMenuItem extends JMenuItem implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 6309834468678258546L;

    public GMenuItem(String label, String action, BlackBoard b) {
        super(label);
        blackboard = b;
        addActionListener(this);
//        add(new JButton(" ..."));
//        validate();
        //-----------
        t = new UIEventData();
        t.action = action;
    }

    public GMenuItem(String label, String action, BlackBoard b, String accelerator, int index) {
        //icons R ! supported yet. now it is important to just work correctly
        super(label);
        blackboard = b;
        addActionListener(this);
        //-----------
        t = new UIEventData();
        t.action = action;
        KeyBoardShortCut shortcut = KeyBoardShortCutProvider.registerKeyBoardShortcut(accelerator, label, index);
        if (shortcut != null) {
            if (!shortcut.isAccelerator()) {
                setMnemonic(shortcut.getKeyMnemonic());
                setDisplayedMnemonicIndex(shortcut.getKeyWordIndex());

            } else {
                setAccelerator(KeyStroke.getKeyStroke(shortcut.getKeyEvent(), shortcut.getKeyModifiers()));
                setDisplayedMnemonicIndex(shortcut.getKeyWordIndex());
                setMnemonic(shortcut.getKeyMnemonic());
            }
        }
//        setLayout(new BorderLayout(10,0));
//
//        JButton pref = new JButton("Prefs");
//        pref.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae){
//            t.eventType=0;
//            blackboard.listen4Event(UIEventData.event(""), t);}});
//        add(pref, BorderLayout.EAST);

//        validate();


    }

    public String toString() {
        return getText();
    }

    public void actionPerformed(ActionEvent e) {
        blackboard.setData(UIEventData.name(""), t);
    }

    private final BlackBoard blackboard;
    private final UIEventData t;
}
