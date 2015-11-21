package graphtea.extensions.reports.boundcheck.forall.iterators;

import graphtea.extensions.reports.boundcheck.forall.IterProgressBar;
import graphtea.extensions.reports.boundcheck.forall.ShowG;
import graphtea.extensions.reports.boundcheck.forall.Sizes;
import graphtea.graph.graph.GraphModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by rostam on 31.10.15.
 * This is a iterator on all graphs with the number of vertices size
 */
public class AllGraphIterator extends GraphModelIterator {
    BufferedReader bri;
    int maxCnt;
    int cnt=0;
    IterProgressBar pb;
    int size;

    public AllGraphIterator(String fileSize, int size, Integer part) {
        this.maxCnt = Sizes.sizes.get(fileSize);
        bri = ShowG.showG(fileSize);
        pb = new IterProgressBar(maxCnt);
        pb.setVisible(true);
        this.size=size;
    }

    public int size() {
        return maxCnt;
    }

    @Override
    public int getCount() {
        return cnt;
    }

    @Override
    public boolean hasNext() {
        return cnt < maxCnt;
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

        pb.setValue(cnt);
        if(cnt == maxCnt-1) {
            pb.setVisible(false);
        }

        return ShowG.parseGraph(new Scanner(g));
    }

    @Override
    public void remove() {

    }
}
