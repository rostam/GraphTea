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

    /**
     * Turns the key part of an accelerator string into a {@link KeyEvent} code.
     *
     * <p>This only ever read a single character, so an accelerator could not name a key that
     * has no printable character. Delete, Escape and the function keys were simply unavailable,
     * which is why the one shortcut users reach for most &mdash; Delete to remove a selection
     * &mdash; had never been wired up.
     *
     * @param accelerator an accelerator such as {@code "control+S"}, {@code "DELETE"} or {@code "F2"}
     * @return the key code, or 0 when the key cannot be resolved
     */
    static int extractKeyEvent(String accelerator) {
        if (accelerator.equals("+")) {
            return KeyEvent.VK_PLUS;
        }
        String key = accelerator.substring(accelerator.lastIndexOf('+') + 1).trim();
        if (key.isEmpty()) {
            // Trailing '+' means the plus key itself, as in "control++".
            return KeyEvent.VK_PLUS;
        }
        if (key.length() == 1) {
            return Character.toUpperCase(key.charAt(0));
        }
        try {
            return KeyEvent.class.getField("VK_" + key.toUpperCase()).getInt(null);
        } catch (ReflectiveOperationException | RuntimeException e) {
            System.err.println("Unknown accelerator key: " + accelerator);
            return 0;
        }
    }
}

