package graphtea.extensions.reports.boundcheck.forall.iterators;

import graphtea.extensions.G6Format;
import graphtea.extensions.reports.boundcheck.forall.IterProgressBar;
import graphtea.extensions.reports.boundcheck.forall.ShowG;
import graphtea.extensions.reports.boundcheck.forall.Sizes;
import graphtea.graph.graph.GraphModel;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by rostam on 31.10.15.
 * This is a iterator on all graphs with the number of vertices size
 */
public class AllGraphIterator extends GraphModelIterator {
    //BufferedReader bri;
    int cnt;
    IterProgressBar pb;
    int size;
    Scanner g_iters;
    String g6;

    public AllGraphIterator(String fileSize, int size, boolean part) {
        try {
            g_iters = new Scanner(new File("graphs/"+fileSize+".g6"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //bri = ShowG.showG(fileSize,from,to);
        pb = new IterProgressBar(size);
        this.size=size;
        cnt = 0;
        if(size > 1000) pb.setVisible(true);
    }

    public int size() {
        return size;
    }

    @Override
    public int getCount() {
        return cnt;
    }

    @Override
    public String getG6() {
        return g6;
    }

    @Override
    public boolean hasNext() {
        return g_iters.hasNext();
    }

    @Override
    public GraphModel next() {
        cnt++;
        g6 = g_iters.nextLine();
        pb.setValue(cnt);
        if(cnt > size - 2) {
            pb.setVisible(false);
        }
        return G6Format.stringToGraphModel(g6);
    }

    @Override
    public void remove() {

    }
}
