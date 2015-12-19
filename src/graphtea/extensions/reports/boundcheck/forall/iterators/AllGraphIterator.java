package graphtea.extensions.reports.boundcheck.forall.iterators;

import graphtea.extensions.reports.boundcheck.forall.IterProgressBar;
import graphtea.extensions.reports.boundcheck.forall.ShowG;
import graphtea.extensions.reports.boundcheck.forall.Sizes;
import graphtea.graph.graph.GraphModel;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by rostam on 31.10.15.
 * This is a iterator on all graphs with the number of vertices size
 */
public class AllGraphIterator extends GraphModelIterator {
    BufferedReader bri;
    int cnt;
    IterProgressBar pb;
    int size,from,to;

    public AllGraphIterator(String fileSize, int size, boolean part) {
        from = 1;
        to = Sizes.sizes.get(fileSize);
        if(part) {
            String out = JOptionPane.showInputDialog(
                    "Please enter a bound for the graphs that you want to check.\n" +
                            "If the value is in the format of a:b, a bound would be considered.\n" +
                            "The value of b must be limited up to " + Sizes.sizes.get(fileSize) + ".\n" +
                            "Entering a single integer results in a bound like a:a.");
            if(out.contains(":")) {
                Scanner sc = new Scanner(out);
                sc.useDelimiter(":");
                from = sc.nextInt();
                to = sc.nextInt();
            } else {
                from = Integer.parseInt(out);
                to = from;
            }
        }
        bri = ShowG.showG(fileSize,from,to);
        pb = new IterProgressBar(to-from+1);
        if(to-from+1 > 100) {
            pb.setVisible(true);
        }
        this.size=size;
        cnt = from;
    }

    public int size() {
        return to - from + 1;
    }

    @Override
    public int getCount() {
        return cnt;
    }

    @Override
    public boolean hasNext() {
        return cnt < to;
    }

    @Override
    public GraphModel next() {
        cnt++;
        String g = "";
        try {
            bri.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i <= size; i++) {
            try {
                g += bri.readLine() + "\n";
            } catch (IOException e) {
                e.printStackTrace();
                return new GraphModel();
            }
        }

        pb.setValue(cnt - from +1);
        if(cnt > to-2) {
            pb.setVisible(false);
        }

        return ShowG.parseGraph(new Scanner(g));
    }

    @Override
    public void remove() {

    }
}
