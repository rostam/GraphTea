// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.ui.components.gmenu;

/**
 * @author Ruzbeh Ebrahimi
 */
public class KeyBoardShortCut {
    private int keyEvent;
    private int keyMnemonic;
    private int keyModifiers;
    private int keyWordIndex;
    private boolean isAccelerator;

    public KeyBoardShortCut(int keyEvent, int keyModifiers, int keyWordIndex, boolean isMnemonics, int keyMnemonic) {
        this.keyMnemonic = keyMnemonic;
        this.keyEvent = keyEvent;
        this.keyModifiers = keyModifiers;
        this.keyWordIndex = keyWordIndex;
        this.isAccelerator = isMnemonics;
    }

    public KeyBoardShortCut(int keyMnemonic, int keyWordIndex, boolean isAccelerator) {
        this.keyMnemonic = keyMnemonic;
        this.keyWordIndex = keyWordIndex;
        this.isAccelerator = isAccelerator;
    }

    public int getKeyEvent() {
        return keyEvent;
    }

    public int getKeyModifiers() {
        return keyModifiers;
    }

    public int getKeyWordIndex() {
        return keyWordIndex;
    }

    public boolean isAccelerator() {
        return isAccelerator;
    }

    public int getKeyMnemonic() {
        return keyMnemonic;
    }
}
