// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.reports.ui;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Colours, fonts and number formats shared by the Conjecture Checker's windows, taken from the
 * look and feel so that they suit both the light and the dark theme.
 *
 * @author M. Ali Rostami
 */
final class CheckerStyle {

    private CheckerStyle() {
    }

    static boolean dark() {
        Color bg = UIManager.getColor("Panel.background");
        if (bg == null) {
            return false;
        }
        double luminance = (0.299 * bg.getRed() + 0.587 * bg.getGreen() + 0.114 * bg.getBlue()) / 255;
        return luminance < 0.5;
    }

    static Color success() {
        return dark() ? new Color(0x81C784) : new Color(0x2E7D32);
    }

    static Color failure() {
        return dark() ? new Color(0xFF8A80) : new Color(0xC62828);
    }

    static Color warning() {
        return dark() ? new Color(0xFFCC80) : new Color(0x9A5B00);
    }

    static Color muted() {
        Color c = UIManager.getColor("Label.disabledForeground");
        return c != null ? c : Color.GRAY;
    }

    static Color text() {
        Color c = UIManager.getColor("Label.foreground");
        return c != null ? c : Color.BLACK;
    }

    static Font monospaced(JComponent like) {
        Font base = like.getFont();
        return new Font(Font.MONOSPACED, Font.PLAIN, base == null ? 13 : base.getSize() + 1);
    }

    /** A bold heading for a step of the form, e.g. "1  Conjecture". */
    static JLabel heading(String text) {
        JLabel l = new JLabel(text);
        l.setFont(l.getFont().deriveFont(Font.BOLD, l.getFont().getSize2D() + 1f));
        l.setBorder(BorderFactory.createEmptyBorder(10, 0, 4, 0));
        return l;
    }

    /** A small grey explanatory line. */
    static JLabel hint(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(muted());
        l.setFont(l.getFont().deriveFont(l.getFont().getSize2D() - 1f));
        return l;
    }

    /**
     * @param v a value
     * @return it for a table: integers without decimals, others to six decimals, NaN as "undefined"
     */
    static String number(double v) {
        if (Double.isNaN(v)) {
            return "undefined";
        }
        if (Double.isInfinite(v)) {
            return v > 0 ? "∞" : "−∞";
        }
        if (v == Math.rint(v) && Math.abs(v) < 1e15) {
            return NumberFormat.getIntegerInstance().format((long) v);
        }
        return new DecimalFormat("0.######").format(v);
    }

    /** @return a count with thousands separators */
    static String count(long n) {
        return NumberFormat.getIntegerInstance().format(n);
    }

    /** @return "1 graph" or "3 graphs" */
    static String graphs(long n) {
        return count(n) + (n == 1 ? " graph" : " graphs");
    }
}
