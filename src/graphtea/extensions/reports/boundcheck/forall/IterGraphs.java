package graphtea.extensions.reports.boundcheck.forall;


import graphtea.extensions.reports.boundcheck.forall.filters.Bounds;
import graphtea.extensions.reports.boundcheck.forall.filters.GeneratorFilters;
import graphtea.extensions.reports.boundcheck.forall.iterators.AllGraphIterator;
import graphtea.extensions.reports.boundcheck.forall.iterators.GraphGeneratorIterator;
import graphtea.extensions.reports.boundcheck.forall.iterators.GraphModelIterator;
import graphtea.extensions.reports.boundcheck.forall.iterators.MyPriorityQueue;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RendTable;
import graphtea.graph.graph.Vertex;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.reports.extension.GraphReportExtension;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class IterGraphs {
    private final Integer part;
    public boolean activeConjCheck = false;
    public boolean iterative = false;
    public String type = "";
    public int size = 0;
    public String bound = "";
    public String gens = "";
    public String postproc = "";

    public IterGraphs(boolean activeConjCheck, boolean iterative,
                      String type, int size, String bound, String gens, Integer part, String postproc) {
        this.activeConjCheck = activeConjCheck;
        this.iterative = iterative;
        this.type = type;
        this.size = size;
        this.bound = bound;
        this.gens = gens;
        this.part = part;
        this.postproc = postproc;
    }

    public RendTable wrapper(final GraphReportExtension mr) {
        RendTable result = new RendTable();
        GraphModelIterator it;
        if (!gens.equals(GeneratorFilters.NoGenerator)) {
            it = new GraphGeneratorIterator(gens);

        } else {
            if (size == 10 && part != 0) {
                it = new AllGraphIterator(type + size + part, size, part);
            } else {
                it = new AllGraphIterator(type + size, size, part);
            }
        }

        ToCall f = new ToCall() {
            @Override
            public Object f(GraphModel g) {
                return mr.calculate(g);
            }
        };
        if (!iterative) {
            result.add(new Vector<>());
            result.add(new Vector<>());
        }

        int[] res = null;
        int fl = 0;
        MyPriorityQueue pq = new MyPriorityQueue(1, 500);
        while (it.hasNext()) {
            fl++;
            GraphModel g = it.next();
            if (iterative) {
                if (size >= 8 && type.contains("all")) {
                    getResIterLimited(f, result, g, it.getCount(), pq);
                } else {
                    getResIter(f, result, g, it.getCount());
                }
            } else {
                RendTable ret = (RendTable) f.f(g);
                if (res == null) {
                    result.get(0).addAll(ret.get(0));
                    res = new int[ret.get(0).size()];
                }
                for (int i = 1; i < ret.get(0).size(); i++) {
                    checkTypeOfBounds(ret, res, i, bound);
                }
            }
        }

        if (size >= 8 && type.contains("all")) {
            pq.getRendTable(result);
        }

        if (!iterative) {
            for (int re : res) {
                result.get(1).add(re);
            }
            result.get(0).add("Num of Filtered Graphs");
            result.get(1).add(it.size());
        }

        return result;
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

    private void getResIter(ToCall f, RendTable retForm, GraphModel g, int count) {
        RendTable ret = (RendTable) f.f(g);

        if (retForm.size() == 0) {
            retForm.add(new Vector<>());
            retForm.get(0).add("Counter");
            retForm.get(0).addAll(ret.get(0));
        }
        retForm.add(new Vector<Object>());

        retForm.lastElement().add(count + "");
        retForm.lastElement().addAll(ret.get(1));

    }

    private void getResIterLimited(ToCall f, RendTable retForm, GraphModel g, int count
            , MyPriorityQueue mpq) {
        RendTable ret = (RendTable) f.f(g);
        Vector<Object> data = new Vector<>();
        data.add(count + "");
        for (Object o : ret.get(1)) {
            data.add(o);
        }
        mpq.add(data);


        if (retForm.size() == 0) {
            retForm.add(new Vector<>());
            retForm.get(0).add("Index");
            retForm.get(0).addAll(ret.get(0));
        }
    }

    public GraphModel getith(String file, int size, int ith) {
        IterProgressBar pb = new IterProgressBar(ith);
        if (ith >= 30) {
            pb.setVisible(true);
            pb.setAlwaysOnTop(true);
            pb.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
        }

        Scanner sc = null;
        try {
            String cur = new java.io.File(".").getCanonicalPath();

            if (System.getProperty("os.name").contains("Win")) {
                cur = cur + "\\graphs\\";
            } else {
                cur = cur + "/graphs/";
            }
            sc = new Scanner(new File(cur + file + size + ".g6"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int cnt = 0;
        while (sc.hasNext()) {
            cnt++;
            pb.setValue(cnt);
            String line = sc.nextLine();
            if (ith == cnt) {
                pb.setVisible(false);
                return ShowG.showOneG(line);
            }
        }
        pb.setVisible(false);
        return null;
    }


    public void checkTypeOfBounds(RendTable ret, int[] res, int i, String bound) {
        switch (bound) {
            case Bounds.Upper:
                if ((double) ret.get(1).get(0) >= (double) ret.get(1).get(i)) {
                    res[i]++;
                }
                break;
            case Bounds.Lower:
                if ((double) ret.get(1).get(0) <= (double) ret.get(1).get(i)) {
                    res[i]++;
                }
                break;
            case Bounds.StrictLower:
                if ((double) ret.get(1).get(0) > (double) ret.get(1).get(i)) {
                    res[i]++;
                }
                break;
            case Bounds.StrictUpper:
                if ((double) ret.get(1).get(0) < (double) ret.get(1).get(i)) {
                    res[i]++;
                }
                break;
        }
    }

    public void show_ith(int cnt, BlackBoard blackboard) {
        new GraphData(blackboard).core.showGraph(getith(type, size, cnt));
    }

    public void show_itojth(int from, int to, BlackBoard blackboard) {
        if (to - from > 10) return;
        for (int i = from; i <= to; i++) {
            show_ith(i, blackboard);
        }
    }

    public void show_several(Vector<Integer> gs, BlackBoard blackboard) {
        Collections.sort(gs);
        for (int i : gs) {
            show_ith(i, blackboard);
        }
    }
}