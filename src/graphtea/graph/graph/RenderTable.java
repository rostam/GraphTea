package graphtea.graph.graph;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Vector;

/**
 * Created by rostam on 21.11.15.
 * Render Table for reports
 */
public class RenderTable extends PriorityQueue<Vector<Object>> {
    int which = 1;
    int maxSize = 5000;
    public static boolean noFilter = false;

    private Vector<String> titles = new Vector<>();

    public void setTitles(Vector<String> tts) {
        titles=tts;
    }

    public Vector<String> getTitles() {
        return titles;
    }

    public RenderTable() {
        super(1000,new RenderTableMaxComparator(1));
    }

    public RenderTable(int which) {
        super(1000,new RenderTableMaxComparator(which));
        this.which=which;
    }

    public RenderTable(int which, int maxSize) {
        super(1000,new RenderTableMaxComparator(which));
        this.which=which;
        this.maxSize = maxSize;
    }

    public RenderTable(int which, int maxSize, Comparator<Vector<Object>> comp) {
        super(1000,comp);
        this.which=which;
        this.maxSize = maxSize;
    }

    public boolean add(Vector<Object> v) {
        boolean ret = super.add(v);
        if(noFilter) return ret;
        if(super.size() > maxSize) super.poll();
        return ret;
    }
}

class RenderTableMaxComparator implements Comparator<Vector<Object>> {
    int which = 0;

    public RenderTableMaxComparator(int which) {
        this.which = which;
    }

    @Override
    public int compare(Vector<Object> first, Vector<Object> second) {
        if (first.get(which) instanceof Double)
            return (Double) first.get(which)
                    > (Double) second.get(which) ? 1 : -1;
        else
            return (Integer) first.get(which)
                    > (Integer) second.get(which) ? 1 : -1;
    }
}

class RenderTableMinComparator implements Comparator<Vector<Object>> {
    private int which=0;
    public RenderTableMinComparator(int which) {
        this.which=which;
    }
    @Override
    public int compare(Vector<Object> first, Vector<Object> second) {
        if (first.get(which) instanceof Double)
            return (Double) first.get(which)
                    < (Double) second.get(which) ? 1 : -1;
        else
            return (Integer) first.get(which)
                    < (Integer) second.get(which) ? 1 : -1;
    }
}
