package graphtea.plugins.reports.forall;


import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RendTable;

import java.io.*;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

public class IterGraphs {


    public void writeFilterGraphs(String file, Vector<Integer> gs, String filt) throws IOException {
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

    public void filter(String file, GraphFilter filt, int size) throws IOException {
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
        writeFilterGraphs(file,gs,filt.getName());
        pb.setVisible(false);
    }

    public RendTable iterBounds(String file, int size, ToCall f, String bound) throws IOException {
        RendTable ret = new RendTable();
        int[] res = null;
        RendTable retForm = new RendTable();
        retForm.add(new Vector<>());

        BufferedReader bri = ShowG.showG(file);
        String line;
        String g="";
        IterProgressBar pb = new IterProgressBar(size);
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
        return retForm;
    }


    public RendTable countBounds(String file, int size, ToCall f, String bound) throws IOException {
        RendTable ret = new RendTable();
        int[] res = null;
        BufferedReader bri = ShowG.showG(file);
        String line;
        String g="";
        IterProgressBar pb = new IterProgressBar(size);
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

    public GraphModel getith(String file, int size, int ith) throws FileNotFoundException {
        IterProgressBar pb = new IterProgressBar(size);
        if(ith >= 30) {
            pb.setVisible(true);
            pb.setAlwaysOnTop(true);
        }
        int gcount = 0;

        Scanner sc = new Scanner(new File(file));
        int cnt=0;
        int vecCnt=0;
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


    public void checkTypeOfBounds(RendTable ret, int[] res, int i, String bound) {
        switch (bound) {
            case "upper":
                if ((double) ret.get(1).get(0) >= (double) ret.get(1).get(i)) {
                    res[i]++;
                }
                break;
            case "lower":
                if ((double) ret.get(1).get(0) <= (double) ret.get(1).get(i)) {
                    res[i]++;
                }
                break;
            case "strictLower":
                if ((double) ret.get(1).get(0) > (double) ret.get(1).get(i)) {
                    res[i]++;
                }
                break;
            case "strictUpper":
                if ((double) ret.get(1).get(0) < (double) ret.get(1).get(i)) {
                    res[i]++;
                }
                break;
        }
    }
}