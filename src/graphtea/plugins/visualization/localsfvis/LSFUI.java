// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.visualization.localsfvis;

import graphtea.graph.event.GraphSelectData;
import graphtea.platform.core.Listener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author azin azadi
 */
public class LSFUI extends JPanel implements ActionListener {
    private static final long serialVersionUID = 3844363865479171073L;

    private LocalSF target;

    private Listener n = new Listener() {
        //todo: what does this performJob do (Rouzbeh)?
        public void performJob(String name) {
            animatorLSF a = target.getCurrentAnimator();
            if (a != null)
                dynamic.setSelected(a.isDynamic);
        }

        public void keyChanged(String name, Object value) {
            animatorLSF a = target.getCurrentAnimator();
            if (a != null)
                dynamic.setSelected(a.isDynamic);
        }

        public boolean isEnable() {
            return isVisible();
        }
    };

    void setTaget(LocalSF trg) {
        this.target = trg;
        initComponents();
        trg.getBlackBoard().addListener(GraphSelectData.EVENT_KEY, n);
    }

    private void initComponents() {
        start = new javax.swing.JButton();
        stop = new javax.swing.JButton();
        opener = new javax.swing.JButton();
        closer = new javax.swing.JButton();
        stress = new javax.swing.JButton();
        dynamic = new javax.swing.JCheckBox();

        setLayout(new java.awt.GridLayout(3, 2));

        start.setText("Start");
        start.addActionListener(this);
        add(start);

        stop.setText("Stop");
        stop.addActionListener(this);
        add(stop);

        opener.setText("+");
        opener.addActionListener(this);

        add(opener);

        closer.setText("-");
        closer.addActionListener(this);
        add(closer);

        stress.setText("Stress");
        stress.addActionListener(this);
        add(stress);

        dynamic.setText("Automatic");
        dynamic.setSelected(true);
        dynamic.addActionListener(this);
        dynamic.setSelected(true);
        updateButtons();
        add(dynamic);

    }

    // Variables declaration - do not modify
    private javax.swing.JCheckBox dynamic;
    private javax.swing.JButton closer;
    private javax.swing.JButton opener;
    private javax.swing.JButton start;
    private javax.swing.JButton stop;
    private javax.swing.JButton stress;
    // End of variables declaration

    public void actionPerformed(ActionEvent e) {
        if (target != null) {
            animatorLSF anim = this.target.getCurrentAnimator();
            if (anim != null) {
                if (e.getSource() == closer)
                    anim.stronger();
                if (e.getSource() == opener)
                    anim.weaker();
                if (e.getSource() == stress)
                    anim.temporaryStress = true;
                if (e.getSource() == dynamic){
                    updateButtons();
                    anim.setDynamic(dynamic.isSelected());
                }
            }
            if (e.getSource() == stop) {
                target.stop();
            }
            if (e.getSource() == start) {
                target.start();
                anim = this.target.getCurrentAnimator();
                anim.setDynamic(dynamic.isSelected());
            }
        }
    }

    private void updateButtons() {
        boolean b = !dynamic.isSelected();
        start.setEnabled(b);
        closer.setEnabled(b);
        opener.setEnabled(b);
//        stop.setEnabled(b);
        stress.setEnabled(b);
    }
}