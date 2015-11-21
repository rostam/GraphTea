package graphtea.extensions.reports.boundcheck.forall.iterators;

import graphtea.graph.graph.RendTable;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Vector;

/**
 * Created by rostam on 21.11.15.
 */
public class MyPriorityQueue extends PriorityQueue<Vector<Object>> {
    int which = 0;
    int maxSize = 0;

    public MyPriorityQueue(int which,int maxSize) {
        super(1000,new RendTableComparator(which));
        this.which=which;
        this.maxSize = maxSize;
    }

    public boolean add(Vector<Object> v) {
        boolean ret = super.add(v);
        if(super.size() > maxSize) super.poll();
        return ret;
    }


    public void getRendTable(RendTable retForm) {
        while(this.size() != 0 ) {
            retForm.add(new Vector<Object>());
            retForm.lastElement().addAll(this.poll());
        }
    }
}

class RendTableComparator implements Comparator<Vector<Object>> {
    int which=0;
    public RendTableComparator(int which) {
        this.which=which;
    }
    @Override
    public int compare(Vector<Object> first, Vector<Object> second) {
        return (Double)first.get(which)
                > (Double)second.get(which) ? 1 : -1;
    }

}
