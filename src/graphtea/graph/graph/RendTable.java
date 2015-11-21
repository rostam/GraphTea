package graphtea.graph.graph;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by rostam on 25.01.15.
 */
public class RendTable extends Vector<Vector<Object>> implements Iterator<String>{
    int it=0;
    public String toString() {
        String out = "";
        for(int i=0;i<this.size();i++) {
            for(int j=0;j<this.get(0).size();j++) {
                Object o = this.get(i).get(j);
                if(o instanceof Double) {
                    Double toBeTruncated = (Double) o;
                    Double truncatedDouble =
                            new BigDecimal(toBeTruncated).
                                    setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
                    o = truncatedDouble;
                }
                if(j!=this.get(0).size()-1)
                  out += o + ", ";
                else
                    out += o;
            }
            out += "\n";
        }
        return out;
    }

    @Override
    public boolean hasNext() {
        return it<this.size();
    }

    @Override
    public String next() {
        String out = "";
        for(int j=0;j<this.get(0).size();j++) {
            Object o = this.get(it).get(j);
            if(o instanceof Double) {
                Double toBeTruncated = (Double) o;
                Double truncatedDouble =
                        new BigDecimal(toBeTruncated).
                                setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
                o = truncatedDouble;
            }
            if(j!=this.get(0).size()-1)
                out += o + ", ";
            else
                out += o;
        }
        it++;
        return out;
    }

    @Override
    public void remove() {

    }
}
