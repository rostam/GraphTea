package graphtea.extensions.reports.coloring.fillinaware;

import java.util.Vector;

/**
 * Created by rostam on 15.02.16.
 * A structure for needed information
 */
public class ColInf {
    //Block NNZ,Fillin - Coloring - PotentialRequired - AdditionallyRequired
    public ColInf(int rows,int NNZ, int F,int C,int P,int add) {
        this.NNZ = NNZ;
        this.F = F;
        this.P = P;
        this.C = C;
        this.add = add;
        this.rows=rows;
    }

    public Vector<Object> getVec() {
        Vector<Object> ret = new Vector<>();
        ret.add(rows);
        ret.add(NNZ);
        ret.add(F);
        //ret.add(P);
        ret.add(C);
        ret.add(add);
        return ret;
    }

    public int rows,F,C,P,add,NNZ;
}
