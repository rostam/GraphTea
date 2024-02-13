// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui.components.gmenu;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 * @author Rouzbeh Ebrahimi
 */
public class KeyBoardShortCutProvider {
    public static HashMap<String, KeyBoardShortCut> shortCuts = new HashMap<>();

    public static KeyBoardShortCut registerKeyBoardShortcut(String accelerator, String label, int index) {
        if (label == null) return null;
        int ind = index;
        //int mod = extractModifiers(accelerator);
        int mne = extractMnemonics(label, ind);
        ind = Math.max(ind, 0);
        KeyBoardShortCut k;
        if (accelerator == null) {
            k = new KeyBoardShortCut(mne, ind, false);

        } else {
            int mod = extractModifiers(accelerator);
            int keyEvent = extractKeyEvent(accelerator);
            k = new KeyBoardShortCut(keyEvent, mod, ind, true, mne);
        }

        shortCuts.put(label, k);
        return k;
    }

    static int extractModifiers(String Acc) {
        Acc = Acc.toLowerCase();
        boolean isControl = Acc.contains("control");
        boolean isShift = Acc.contains("shift");
        boolean isAlt = Acc.contains("alt");
        int control = (isControl ? InputEvent.CTRL_DOWN_MASK : 0);
        int shift = (isShift ? InputEvent.SHIFT_DOWN_MASK : 0);
        int alt = (isAlt ? InputEvent.ALT_DOWN_MASK : 0);
        return control + alt + shift;
    }

    private static int extractMnemonics(String priLabel, int index) {
        return priLabel.charAt(index);
    }

    //todo: provide the way that defining accelerator
    // for keys like delete, insert, ... be possible, by iterating over integers
    // and KeyEvent.getKeyText, or probably the reflection
    static int extractKeyEvent(String accelerator) {
        if (!accelerator.equals("+")) {
            int i = accelerator.lastIndexOf('+') + 1;
            return accelerator.charAt(i);
        } else
            return KeyEvent.VK_PLUS;
    }
}

