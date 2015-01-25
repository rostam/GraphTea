package graphtea.graph.graph;

import java.util.Vector;

/**
 * Created by rostam on 25.01.15.
 */
public class RendTable extends Vector<Vector<Object>> {
    public String toString() {
        String out = "";
        for(int i=0;i<this.size();i++) {
            for(int j=0;j<this.get(0).size();j++) {
                if(j!=this.get(0).size()-1)
                  out += this.get(i).get(j) + ", ";
                else
                    out += this.get(i).get(j);
            }
            out += "\n";
        }
        return out;
    }
}
