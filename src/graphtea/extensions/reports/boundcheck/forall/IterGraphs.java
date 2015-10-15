package graphtea.extensions.reports.boundcheck.forall;


import graphtea.extensions.reports.boundcheck.forall.filters.Bounds;
import graphtea.extensions.reports.boundcheck.forall.filters.GeneratorFilters;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RendTable;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.io.*;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

public class IterGraphs {
    public static String state = "";
    public static boolean activeConjCheck = false;
    public static boolean iterative = false;
    public static String type = "";
    public static int size = 0;
    public static String bound = "";
    public static String gens = "";

    public static void parseState() {
        if(!state.equals("")) {
            Scanner sc = new Scanner(state);
            sc.useDelimiter(",");
            if (sc.hasNext()) {
                activeConjCheck = Boolean.parseBoolean(sc.next());
                iterative = Boolean.parseBoolean(sc.next());
                type = sc.next();
                size = Integer.parseInt(sc.next());
                bound = sc.next();
                gens = sc.next();
            }
        }
    }

    public static RendTable wrapper(final GraphReportExtension mr) {
        parseState();
        RendTable result = new RendTable();
        if(!gens.equals("")) {
            if (!iterative) {
                result = IterGraphs.countBoundsGenerators(type, size, new ToCall() {
                    @Override
                    public Object f(GraphModel g) {
                        return mr.calculate(g);
                    }
                }, bound);
            } else {
                result = IterGraphs.iterBoundsGenerators(type, size, new ToCall() {
                    @Override
                    public Object f(GraphModel g) {
                        return mr.calculate(g);
                    }
                }, bound);
            }
        } else if (!iterative) {
            try {
                result = IterGraphs.countBounds(type, size, new ToCall() {
                    @Override
                    public Object f(GraphModel g) {
                        return mr.calculate(g);
                    }
                }, bound);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                result = IterGraphs.iterBounds(type, size, new ToCall() {
                    @Override
                    public Object f(GraphModel g) {
                        return mr.calculate(g);
                    }
                }, bound);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private static RendTable iterBoundsGenerators(String type, int size, ToCall toCall, String bound) {
        return GeneratorFilters.generateGraphs(gens,toCall,bound);
    }

    private static RendTable countBoundsGenerators(String type, int size, ToCall toCall, String bound) {
        return null;
    }

    public static void writeFilterGraphs(String file, Vector<Integer> gs, String filt) throws IOException {
        FileWriter fw = new FileWriter(new File(filt));
        Scanner sc = new Scanner(new File(file));
        int cnt=0;
        int vecCnt=0;
        Collections.sort(gs);
        while(sc.hasNext()) {
            cnt++;
            String line = sc.nextLine();
            if(gs.get(vecCnt) == cnt) {
                fw.append(line);
                vecCnt++;
            }
        }
        fw.close();
    }

    public static void filter(String file, GraphFilter filt, int size) throws IOException {
        String line;
        String g="";
        Vector<Integer> gs = new Vector<>();
        IterProgressBar pb = new IterProgressBar(size);

        BufferedReader bri = ShowG.showG(file);
        bri.readLine();
        int cnt =0;
        while ((line = bri.readLine()) != null) {
            if(!line.equals("")) {
                g+=line+"\n";
            } else {
                if(!g.equals("")) {
                    cnt++;
                    pb.setValue(cnt);
                    pb.validate();
                    GraphModel tmp = ShowG.parseGraph(new Scanner(g));
                    if(!filt.filter(tmp)) continue;
                    gs.add(cnt);
                    g = "";
                }
            }
        }
        bri.close();
        writeFilterGraphs(file, gs, filt.getName());
        pb.setVisible(false);
    }

    public static int getSize(String str) {
        Scanner sc = null;
        try {
            sc = new Scanner(new File("graphs/sizes.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(sc.hasNext()) {
            String tmp = sc.next();
            int ret = sc.nextInt();
            if(tmp.contains(str)) return ret;
        }
        return 0;
    }

    public static RendTable iterBounds(String file, int size, ToCall f, String bound) throws IOException {
        RendTable ret = new RendTable();
        int[] res = null;
        RendTable retForm = new RendTable();
        retForm.add(new Vector<>());

        BufferedReader bri = ShowG.showG(file+size);
        String line;
        String g="";
        System.out.println("size " + getSize(file+size));
        IterProgressBar pb = new IterProgressBar(getSize(file+size));
        pb.setVisible(true);
        bri.readLine();
        int cnt =0;
        while ((line = bri.readLine()) != null) {
            if(!line.equals("")) {
                g+=line+"\n";
            } else {
                if(!g.equals("")) {
                    cnt++;
                    pb.setValue(cnt);
                    pb.validate();
                    GraphModel tmp = ShowG.parseGraph(new Scanner(g));
                    ret=(RendTable)f.f(tmp);
                    if(retForm.size()==1) {
                        retForm.get(0).add("Counter");
                        retForm.get(0).addAll(ret.get(0));
                    }
                    retForm.add(new Vector<>());
                    retForm.lastElement().add(cnt);
                    retForm.lastElement().addAll(ret.get(1));

                    if (ret.get(0).size() <= 2) return null;
                    if (res == null) {
                        res = new int[ret.get(0).size()];
                    }
                    for (int i = 1; i < ret.get(0).size(); i++) {
                        checkTypeOfBounds(ret, res, i, bound);
                    }
                    g = "";
                }
            }
        }
        bri.close();
        cnt++;
        pb.setVisible(false);
        GraphModel tmp = ShowG.parseGraph(new Scanner(g));
        ret=(RendTable)f.f(tmp);
        if(retForm.size()==1) {
            retForm.get(0).add("Counter");
            retForm.get(0).addAll(ret.get(0));
        }
        retForm.add(new Vector<>());
        retForm.lastElement().add(cnt);
        retForm.lastElement().addAll(ret.get(1));

        if (ret.get(0).size() <= 2) return null;
        if (res == null) {
            res = new int[ret.get(0).size()];
        }
        for (int i = 1; i < ret.get(0).size(); i++) {
            checkTypeOfBounds(ret, res, i, bound);
        }
        return retForm;
    }

    public static RendTable countBounds(String file, int size, ToCall f, String bound) throws IOException {
        RendTable ret = new RendTable();
        int[] res = null;
        BufferedReader bri = ShowG.showG(file+size);

        String line;
        String g="";
        IterProgressBar pb = new IterProgressBar(getSize(file+size));
        bri.readLine();
        int cnt =0;
        while ((line = bri.readLine()) != null) {
            if(!line.equals("")) {
                g+=line+"\n";
            } else {
                if(!g.equals("")) {
                    cnt++;
                    pb.setValue(cnt);
                    pb.validate();
                    GraphModel tmp = ShowG.parseGraph(new Scanner(g));
                    ret=(RendTable)f.f(tmp);
                    if (ret.get(0).size() <= 2) return null;
                    if (res == null) {
                        res = new int[ret.get(0).size()];
                    }
                    for (int i = 1; i < ret.get(0).size(); i++) {
                        checkTypeOfBounds(ret, res, i, bound);
                    }
                    g = "";
                }
            }
        }
        bri.close();
        cnt++;
        pb.setVisible(false);
        GraphModel tmp = ShowG.parseGraph(new Scanner(g));
        ret=(RendTable)f.f(tmp);
        if (ret.get(0).size() <= 2) return null;
        if (res == null) {
            res = new int[ret.get(0).size()];
        }
        for (int i = 1; i < ret.get(0).size(); i++) {
            checkTypeOfBounds(ret, res, i, bound);
        }
        RendTable retForm = new RendTable();
        retForm.add(new Vector<>());
        retForm.add(new Vector<>());
        retForm.get(0).addAll(ret.get(0));
        for (int iii = 0; iii < ret.get(0).size(); iii++) {
            retForm.get(1).add(res[iii]);
        }
        retForm.get(0).add("Num of Filtered Graphs");
        retForm.get(1).add(cnt);
        return retForm;
    }

    public static GraphModel getith(String file, int size, int ith){
        IterProgressBar pb = new IterProgressBar(ith);
        if(ith >= 30) {
            pb.setVisible(true);
            pb.setAlwaysOnTop(true);
        }

        Scanner sc = null;
        try {
            sc = new Scanner(new File(file+size+".g6"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int cnt=0;
        while(sc.hasNext()) {
            cnt++;
            pb.setValue(cnt);
            String line = sc.nextLine();
            if(ith == cnt) {
                pb.setVisible(false);
                return ShowG.showOneG(line);
            }
        }
        pb.setVisible(false);
        return null;
    }


    public static void checkTypeOfBounds(RendTable ret, int[] res, int i, String bound) {
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

    public static void show_ith(int cnt, BlackBoard blackboard) {
        parseState();
        new GraphData(blackboard).core.showGraph(IterGraphs.getith(type, size, cnt));
    }
}