// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.ui.components.gmenu;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 * @author Rouzbeh Ebrahimi
 */
public class KeyBoardShortCutProvider {
    public static HashMap<String, KeyBoardShortCut> shortCuts = new HashMap<String, KeyBoardShortCut>();

    public static KeyBoardShortCut registerKeyBoardShortcut(String accelerator, String label, int index) {
        if (label == null) return null;
        int ind = index;
        //int mod = extractModifiers(accelerator);
        int mne = extractMnemonics(label, ind);
        ind = Math.max(ind, 0);
        boolean isAccel = true;
        KeyBoardShortCut k;
        if (accelerator == null) {
            isAccel = false;
            k = new KeyBoardShortCut(mne, ind, isAccel);

        } else {
            int mod = extractModifiers(accelerator);
            int keyEvent = extractKeyEvent(accelerator);
            k = new KeyBoardShortCut(keyEvent, mod, ind, isAccel, mne);
        }

        shortCuts.put(label, k);
        return k;
    }

    static int extractModifiers(String Acc) {
        Acc = Acc.toLowerCase();
        boolean isControl = Acc.indexOf("control") != -1;
        boolean isShift = Acc.indexOf("shift") != -1;
        boolean isAlt = Acc.indexOf("alt") != -1;
        int control = (isControl ? InputEvent.CTRL_MASK : 0);
        int shift = (isShift ? InputEvent.SHIFT_MASK : 0);
        int alt = (isAlt ? InputEvent.ALT_MASK : 0);
        return control + alt + shift;
    }

    static int extractMnemonics(String priLabel, int index) {
        return priLabel.charAt(index);
    }

    //todo: provide the way that defining accelerator for keys like delete, insert, ... be possible, by iterating over integers and KeyEvent.getKeyText, or probably the reflection
    static int extractKeyEvent(String accelerator) {
        if (!accelerator.equals("+")) {
            int i = accelerator.lastIndexOf('+') + 1;
            return accelerator.charAt(i);
        } else
            return KeyEvent.VK_PLUS;
    }

    static int extractindex(String index) {
        if (index != null) {
            return Integer.parseInt(index);
        }
        return 0;
    }
}

