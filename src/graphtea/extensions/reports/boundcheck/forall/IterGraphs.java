package graphtea.extensions.reports.boundcheck.forall;


import graphtea.extensions.reports.boundcheck.ConjectureChecking;
import graphtea.extensions.reports.boundcheck.forall.filters.Bounds;
import graphtea.extensions.reports.boundcheck.forall.filters.GeneratorFilters;
import graphtea.extensions.reports.boundcheck.forall.iterators.AllGraphIterator;
import graphtea.extensions.reports.boundcheck.forall.iterators.GraphGeneratorIterator;
import graphtea.extensions.reports.boundcheck.forall.iterators.GraphModelIterator;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.graph.graph.Vertex;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.graphgenerator.core.PositionGenerators;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.core.AlgorithmUtils;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

public class IterGraphs {
    private final boolean part;
    public boolean activeConjCheck = false;
    public boolean iterative = false;
    public String type = "";
    public int size = 0;
    public String bound = "";
    public String gens = "";
    public String postproc = "";
    public GraphFilter gf;

    public IterGraphs(boolean activeConjCheck, boolean iterative,
                      String type, int size, String bound, String gens, boolean part,
                      String postproc,GraphFilter gf) {
        this.activeConjCheck = activeConjCheck;
        this.iterative = iterative;
        this.type = type;
        this.size = size;
        this.bound = bound;
        this.gens = gens;
        this.part = part;
        this.postproc = postproc;
        this.gf=gf;
    }

    public Vector<GraphModel> wrapper_generate() {
        GraphModelIterator it;
        if (!gens.equals(GeneratorFilters.NoGenerator)) {
            it = new GraphGeneratorIterator(gens);
        } else {
            it = new AllGraphIterator(type,size,part);
        }

        Vector<GraphModel> results = new Vector<>();
        while (it.hasNext()) {
            GraphModel g = it.next();
            if(gf!=null) if(!gf.filter(g)) continue;
            if(results.size() < 100000)
                results.add(g);
        }

        return results;
    }

    public RenderTable wrapper(final GraphReportExtension mr) {
        GraphModelIterator it;
        if (!gens.equals(GeneratorFilters.NoGenerator)) {
            it = new GraphGeneratorIterator(gens);
        } else {
            it = new AllGraphIterator(type,size,part);
        }

        ToCall f = mr::calculate;
        int[] res = null;
        RenderTable pq = new RenderTable();
        while (it.hasNext()) {
            GraphModel g = it.next();
            if(gf!=null) if(!gf.filter(g)) continue;
            if (iterative) {
                    getResIterLimited(f, g, it.getCount(), pq, it.getG6());
            } else {
                RenderTable ret = (RenderTable) f.f(g);
                Vector<Object> vo = ret.poll();
                if (res == null) {
                    Vector<String> tts = ret.getTitles();
                    tts.add("Num of Filtered Graphs");
                    pq.setTitles(tts);
                    res = new int[vo.size()];
                }
                for (int i = 1; i < vo.size(); i++) {
                    checkTypeOfBounds(vo, res, i, bound);
                }
            }
        }

        if (!iterative) {
            Vector<Object> result = new Vector<>();
            for (int re : res) {
                result.add(re);
            }
            result.add(it.size());
            pq.add(result);
        }

        return pq;
    }

    public void showWrapper(BlackBoard blackboard) {
        AllGraphIterator agi = new AllGraphIterator(type, size, true);
        GraphModel g = agi.next();
        Point pp[] = PositionGenerators.circle(200, 400, 250, g.numOfVertices());

        int tmpcnt = 0;
        for (Vertex v : g) {
            v.setLocation(pp[tmpcnt]);
            tmpcnt++;
        }

        new GraphData(blackboard).core.showGraph(g);
    }

    private void getResIterLimited(ToCall f, GraphModel g, int count, RenderTable mpq, String g6) {
        RenderTable ret = (RenderTable) f.f(g);
        if (mpq.getTitles().size() == 0) {
            Vector<String> tts = new Vector<>();
            tts.add("Index");
            tts.addAll(ret.getTitles());
            tts.add("Degree Sequence");
            tts.add("G6");
            mpq.setTitles(tts);
        }

        Vector<Object> data = new Vector<>();
        data.add(count + "");
        for (Object o : ret.poll()) {
            data.add(o);
        }
        if (ConjectureChecking.PostP.getValue().equals("Equality Filter")) {
            Double toBeTruncated = (Double) data.get(1);
            Double o = new BigDecimal(toBeTruncated).
                    setScale(8, BigDecimal.ROUND_HALF_UP).doubleValue();

            if (Double.parseDouble(ConjectureChecking.ppvalue) == o) {
                mpq.add(data);
            }
        } else mpq.add(data);

        ArrayList<Integer> al = AlgorithmUtils.getDegreesList(g);
        Collections.sort(al);
        data.add(al.toString());
        data.add(g6);
    }

    public void checkTypeOfBounds(Vector<Object> vo, int[] res, int i, String bound) {
        switch (bound) {
            case Bounds.Upper:
                if ((double) vo.get(0) >= (double) vo.get(i)) {
                    res[i]++;
                }
                break;
            case Bounds.Lower:
                if ((double) vo.get(0) <= (double) vo.get(i)) {
                    res[i]++;
                }
                break;
            case Bounds.StrictLower:
                if ((double) vo.get(0) > (double) vo.get(i)) {
                    res[i]++;
                }
                break;
            case Bounds.StrictUpper:
                if ((double) vo.get(0) < (double) vo.get(i)) {
                    res[i]++;
                }
                break;
        }
    }
}