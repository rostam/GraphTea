package graphtea.extensions.reports.boundcheck.forall;

import javax.swing.*;

/**
 * Created by rostam on 30.09.15.
 * @author M. Ali Rostami
 */
public class IterProgressBar extends JFrame {
    JProgressBar pb;

    public IterProgressBar(int size) {
        pb = new JProgressBar(0, size);
        pb.setValue(0);
        pb.setStringPainted(true);
        this.add(pb);

        this.setSize(600, 100);
        this.setLocation(200, 200);
    }

    public void setValue(int val) {
        pb.setValue(val);
    }


}
