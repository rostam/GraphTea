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
import graphtea.plugins.reports.extension.GraphReportExtension;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Scanner;
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

    public IterGraphs(boolean activeConjCheck, boolean iterative,
                      String type, int size, String bound, String gens, boolean part, String postproc) {
        this.activeConjCheck = activeConjCheck;
        this.iterative = iterative;
        this.type = type;
        this.size = size;
        this.bound = bound;
        this.gens = gens;
        this.part = part;
        this.postproc = postproc;
    }

    public RenderTable wrapper(final GraphReportExtension mr) {
        GraphModelIterator it;
        if (!gens.equals(GeneratorFilters.NoGenerator)) {
            it = new GraphGeneratorIterator(gens);

        } else {
                it = new AllGraphIterator(type + size, size, part);
        }

        ToCall f = new ToCall() {
            @Override
            public Object f(GraphModel g) {
                return mr.calculate(g);
            }
        };

        int[] res = null;
        int fl = 0;
        RenderTable pq = new RenderTable();
        while (it.hasNext()) {
            fl++;
            GraphModel g = it.next();
            if (iterative) {
                    getResIterLimited(f, g, it.getCount(), pq);
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
        AllGraphIterator agi = new AllGraphIterator(type + size, size, true);
        GraphModel g = agi.next();
        Point pp[] = PositionGenerators.circle(200, 400, 250, g.numOfVertices());

        int tmpcnt = 0;
        for (Vertex v : g) {
            v.setLocation(pp[tmpcnt]);
            tmpcnt++;
        }

        new GraphData(blackboard).core.showGraph(g);
    }


    public void writeFilterGraphs(String file, Vector<Integer> gs, String filt) throws IOException {
        FileWriter fw = ShowG.outG(filt);
        Scanner br = ShowG.inG(file);
        int cnt = 0;
        int vecCnt = 0;
        Collections.sort(gs);
        String line = "";
        while (br.hasNext()) {
            line = br.nextLine();
            cnt++;
            if (vecCnt < gs.size() && gs.get(vecCnt) == cnt) {
                fw.append(line + "\n");
                vecCnt++;
            }
        }
        fw.flush();
        fw.close();
    }

    public void filter(String type, int size, GraphFilter filt) throws IOException {
        String line;
        String g = "";
        Vector<Integer> gs = new Vector<>();
        IterProgressBar pb = new IterProgressBar(size);
        BufferedReader bri = ShowG.showG(type + size);
        int cnt = 0;
        while ((line = bri.readLine()) != null) {
            if (!line.equals("")) {
                g += line + "\n";
            } else {
                if (!g.equals("")) {
                    cnt++;
                    pb.setValue(cnt);
                    pb.validate();
                    GraphModel tmp = ShowG.parseGraph(new Scanner(g));
                    g = "";
                    if (!filt.filter(tmp)) continue;
                    gs.add(cnt);

                }
            }
        }
        bri.close();
        writeFilterGraphs(type + size, gs, filt.getName() + size);
        Sizes.sizes.put(filt.getName() + size, gs.size());
        pb.setVisible(false);
    }

    private void getResIterLimited(ToCall f, GraphModel g, int count, RenderTable mpq) {
        RenderTable ret = (RenderTable) f.f(g);
        if (mpq.getTitles().size() == 0) {
            Vector<String> tts = new Vector<>();
            tts.add("Index");
            tts.addAll(ret.getTitles());
            mpq.setTitles(tts);
        }

        Vector<Object> data = new Vector<>();
        data.add(count + "");
        for (Object o : ret.poll()) {
            data.add(o);
        }
        if (ConjectureChecking.PostP.getValue().equals("Equality Filter")) {
            Double toBeTruncated = (Double) ((Double)data.get(1)).doubleValue();
            Double o = new BigDecimal(toBeTruncated).
                    setScale(8, BigDecimal.ROUND_HALF_UP).doubleValue();

            if (Double.parseDouble(ConjectureChecking.ppvalue) == o.doubleValue()) {
                mpq.add(data);
            }
        } else mpq.add(data);

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