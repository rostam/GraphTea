// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui.components.utils;

import graphtea.platform.lang.BoundedInteger;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author azin azadi
 */
public class LabledSlider extends JComponent implements ChangeListener {
    /**
     *
     */
    private static final long serialVersionUID = -8976402299284319256L;
    JSlider slider;
    private final JLabel label;

    public JSlider getSlider() {
        return slider;
    }

    public LabledSlider(BoundedInteger value) {
        slider = new JSlider(JSlider.HORIZONTAL, value.getMin(), value.getMax(), value.getValue());
        slider.setOpaque(false);
        label = new JLabel();
        updateLabel();
        slider.addChangeListener(this);
        initComponents();
        validate();
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        setLayout(new java.awt.GridBagLayout());

        label.setLabelFor(slider);
        add(label, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(slider, gridBagConstraints);
    }


    public void stateChanged(ChangeEvent e) {
        updateLabel();
    }

    private void updateLabel() {
        label.setText(slider.getValue() + "");
    }
}
