package graphtea.plugins.reports.extension;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RendTable;
import graphtea.plugins.main.saveload.matrix.LoadMatrix;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

/**
 * Created by rostam on 21.07.15.
 */
public class CheckForAll {
    GraphReportExtension mr;
    boolean dataIsThere = false;

    public CheckForAll(GraphReportExtension mr) {
        this.mr=mr;
    }

    public RendTable forall() {
        RendTable ret = new RendTable();
        int[] res = null;
        int start = 0;
        if (GraphReportExtensionAction.upto) start = 2;
        else start = GraphReportExtensionAction.Size;
        Vector<GraphModel> gs=new Vector<>();
        try {
                gs = LoadMatrix.loadMatrixes(new File(GraphReportExtensionAction.currentType
                        + GraphReportExtensionAction.Size + ".txt"), false);
            } catch (IOException e) {
            e.printStackTrace();
        }

        for(GraphModel g : gs) {
            ret = (RendTable) mr.calculate(g);
            if (ret.get(0).size() <= 2) return null;
            if (res == null) {
                res = new int[ret.get(0).size()];
            }
            for (int i = 1; i < ret.get(0).size(); i++) {
                checkTypeOfBounds(ret, res, i);
            }
        }

        RendTable retForm = new RendTable();
        retForm.add(new Vector<Object>());
        retForm.add(new Vector<Object>());
        retForm.get(0).addAll(ret.get(0));
        for (int iii = 0; iii < ret.get(0).size(); iii++) {
            retForm.get(1).add(res[iii]);
        }
        retForm.get(0).add("Num of Filtered Graphs");
        retForm.get(1).add(gs.size());
        return retForm;
    }

    public void checkTypeOfBounds(RendTable ret, int[] res, int i) {
        if (GraphReportExtensionAction.upperBound) {
            if ((double) ret.get(1).get(0) <= (double) ret.get(1).get(i)) {
                res[i]++;
            }
        } else if (GraphReportExtensionAction.lowerBound) {
            if ((double) ret.get(1).get(0) >= (double) ret.get(1).get(i)) {
                res[i]++;
            }
        } else if (GraphReportExtensionAction.strictUpperBound) {
            if ((double) ret.get(1).get(0) < (double) ret.get(1).get(i)) {
                res[i]++;
            }
        } else if (GraphReportExtensionAction.strictUpperBound) {
            if ((double) ret.get(1).get(0) > (double) ret.get(1).get(i)) {
                res[i]++;
            }
        }
    }

    public static double round(double value, int decimalPlace) {
        double power_of_ten = 1;
        while (decimalPlace-- > 0)
            power_of_ten *= 10.0;
        return Math.round(value * power_of_ten)
                / power_of_ten;
    }

    private static double[] round(double[] array, int prec) {
        double[] res = array;
        for (int i = 0; i < array.length; i++)
            res[i] = round(res[i], prec);
        return res;

    }

    public boolean isFileExists(String ff) {
        File f = new File(ff);
        return f.exists();
    }
}
