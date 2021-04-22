package graphtea.extensions.reports.boundcheck.forall;


import graphtea.extensions.AlgorithmUtils;
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
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

public class IterGraphs {
    public boolean activeConjCheck;
    public boolean iterative;
    public String type;
    public int size;
    public String bound;
    public String gens;
    public String postproc;
    public int columnID;
    public GraphFilter gf;

    public IterGraphs(boolean activeConjCheck, boolean iterative,
                      String type, int size, String bound, String gens,
                      String postproc,GraphFilter gf,int columnID) {
        this.activeConjCheck = activeConjCheck;
        this.iterative = iterative;
        this.type = type;
        this.size = size;
        this.bound = bound;
        this.gens = gens;
        this.postproc = postproc;
        this.gf=gf;
        this.columnID = columnID;
    }

    public Vector<GraphModel> wrapper_generate() {
        GraphModelIterator it;
        if (!gens.equals(GeneratorFilters.NoGenerator)) {
            it = new GraphGeneratorIterator(gens);
        } else {
            it = new AllGraphIterator(type,size);
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

    public RenderTable wrapper(final GraphReportExtension<RenderTable> mr) {
        GraphModelIterator it;
        if (!gens.equals(GeneratorFilters.NoGenerator)) {
            it = new GraphGeneratorIterator(gens);
        } else {
            it = new AllGraphIterator(type,size);
        }

        ToCall<RenderTable> f = mr::calculate;
        int[] res = null;
        RenderTable pq = new RenderTable();
        IterGraphs.maxValue = -100000;
        IterGraphs.minValue = 1000000;
        while (it.hasNext()) {
            GraphModel g = it.next();
            if(gf!=null) if(!gf.filter(g)) continue;
            if (iterative) {
                    getResIterLimited(f, g, it.getCount(), pq, it.getG6());
            } else {
                RenderTable ret = f.f(g);
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
        AllGraphIterator agi = new AllGraphIterator(type, size);
        GraphModel g = agi.next();
        Point[] pp = PositionGenerators.circle(200, 400, 250, g.numOfVertices());

        int tmpcnt = 0;
        for (Vertex v : g) {
            v.setLocation(pp[tmpcnt]);
            tmpcnt++;
        }

        new GraphData(blackboard).core.showGraph(g);
    }

    static double maxValue = -100000;
    static double minValue = 1000000;
    /**
     * For each iteration, computes the report on the given graph and
     * add the results (if not null) to a render table to gather all data
     *
     * @param f Report calculation to be called
     * @param g Input graph
     * @param count A count value which will be the count column
     * @param mpq Resulting render table
     * @param g6 Input G6 string
     */
    private void getResIterLimited(ToCall<RenderTable> f, GraphModel g, int count, RenderTable mpq, String g6) {
        RenderTable ret = (RenderTable) f.f(g);
        if (ret == null) return;
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
        data.addAll(ret.poll());
        if (ConjectureChecking.PostP.getValue().equals("Equality Filter")) {
            Double toBeTruncated = (Double) data.get(columnID);
            double o = new BigDecimal(toBeTruncated).setScale(8, BigDecimal.ROUND_HALF_UP).doubleValue();

            if (Double.parseDouble(ConjectureChecking.ppvalue) == o) {
                mpq.add(data);
            }
        } else if (ConjectureChecking.PostP.getValue().equals("Max Filter")) {
            Object oo = data.get(columnID);
            if(oo instanceof Double || oo instanceof Integer || oo instanceof String) {
                if (oo instanceof Double) {
                    double o = (Double) data.get(columnID);
                    if (o > maxValue) {
                        maxValue = o;
                        mpq.clear();
                        mpq.add(data);
                    } else if (o == maxValue) {
                        mpq.add(data);
                    }
                } else if (oo instanceof Integer) {
                    int o = (Integer) data.get(columnID);
                    if (o > maxValue) {
                        maxValue = o;
                        mpq.clear();
                        mpq.add(data);
                    } else if (o == maxValue) {
                        mpq.add(data);
                    }
                } else {
                    String o = (String) data.get(columnID);
                    if (o.length() > maxValue) {
                        maxValue = o.length();
                        mpq.clear();
                        mpq.add(data);
                    } else if (o.length() == maxValue) {
                        mpq.add(data);
                    }
                }
            }
        } else if (ConjectureChecking.PostP.getValue().equals("Min Filter")) {
            Object oo = data.get(columnID);
            if(oo instanceof Double || oo instanceof Integer || oo instanceof String) {
                if (oo instanceof Double) {
                    double o = (Double) data.get(columnID);
                    if (o < minValue) {
                        minValue = o;
                        mpq.clear();
                        mpq.add(data);
                    } else if (o == minValue) {
                        mpq.add(data);
                    }
                } else if (oo instanceof Integer) {
                    int o = (Integer) data.get(columnID);
                    if (o < minValue) {
                        minValue = o;
                        mpq.clear();
                        mpq.add(data);
                    } else if (o == minValue) {
                        mpq.add(data);
                    }
                } else {
                    String o = (String) data.get(columnID);
                    if (o.length() < minValue) {
                        minValue = o.length();
                        mpq.clear();
                        mpq.add(data);
                    } else if (o.length() == minValue) {
                        mpq.add(data);
                    }
                }
            }
        } else {
            mpq.add(data);
        }

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