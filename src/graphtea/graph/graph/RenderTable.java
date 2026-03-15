package graphtea.graph.graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by rostam on 21.11.15.
 * Render Table for reports
 */
public class RenderTable extends PriorityQueue<List<Object>> {
    int which = 1;
    int maxSize = 100000;
    public static boolean noFilter = false;

    private List<String> titles = new ArrayList<>();

    public void setTitles(List<String> tts) {
        titles = tts;
    }

    public List<String> getTitles() {
        return titles;
    }

    public RenderTable() {
        super(100000, new RenderTableMaxComparator(1));
    }

    public RenderTable(int which) {
        super(100000, new RenderTableMaxComparator(which));
        this.which = which;
    }

    public RenderTable(int which, int maxSize) {
        super(100000, new RenderTableMaxComparator(which));
        this.which = which;
        this.maxSize = maxSize;
    }

    public RenderTable(int which, int maxSize, Comparator<List<Object>> comp) {
        super(100000, comp);
        this.which = which;
        this.maxSize = maxSize;
    }

    public boolean add(List<Object> v) {
        boolean ret = super.add(v);
        if (noFilter) return ret;
        if (super.size() > maxSize) super.poll();
        return ret;
    }
}

class RenderTableMaxComparator implements Comparator<List<Object>> {
    int which = 0;

    public RenderTableMaxComparator(int which) {
        this.which = which;
    }

    @Override
    public int compare(List<Object> first, List<Object> second) {
        if (first.get(which) instanceof Double)
            return (Double) first.get(which) < (Double) second.get(which) ? 1 : -1;
        else
            return (Integer) first.get(which) < (Integer) second.get(which) ? 1 : -1;
    }
}

