// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphlab.extensions.io;

import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.GraphPoint;
import graphlab.graph.graph.VertexModel;
import graphlab.plugins.main.saveload.SaveLoadPluginMethods;
import graphlab.plugins.main.saveload.core.GraphIOException;
import graphlab.plugins.main.saveload.core.extension.GraphReaderExtension;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * *Vertices 79                                                              <br>
 * 1 "Hamadani-Zadeh, Javad"                  0.8219    0.9105    0.5000     <br>
 * 2 "Jafarian, A. A."                        0.0770    0.5156    0.5000     <br>
 * 3 "Hamedani, G. G."                        0.9356    0.8392    0.5000     <br>
 * 4 "Nouri-Moghadam, M."                     0.1843    0.4077    0.5000     <br>
 * 5 "Massoumnia, Mohammad-Ali"               0.9356    0.4077    0.5000     <br>
 * 6 "Marvasti, Farokh"                       0.1087    0.2118    0.5000     <br>
 * *Edges                                                                    <br>
 * 1  2 1                                                                   <br>
 * 2  4 2                                                                  <br>
 * 4  1 1                                                                  <br>
 * 2  3 43                                                                  <br>
 * 6  5 1                                                                  <br>
 *
 * each vertex line is: id "vertex label" X Y data
 * each edge line is: sourceId targetId weight
 * @author Azin Azadi
 */
public class LoadNetGraph implements GraphReaderExtension {

    public boolean accepts(File file) {
        return SaveLoadPluginMethods.getExtension(file).equals(getExtension());
    }

    public String getName() {
        return "Net Graph";
    }

    public String getExtension() {
        return "net";
    }

    public GraphModel read(File file) throws GraphIOException {
        try {
            Scanner sc = new Scanner(file);
            String _, s = "";
            String l = sc.next();
            if (!l.contains("ertices"))
                throw new GraphIOException("Incorrect Format(in the first line)");
            int n = sc.nextInt();
            sc.nextLine();
            GraphModel g = new GraphModel(false);
            //Read Vertices
            ArrayList<VertexModel> V = new ArrayList<VertexModel>(n);
            for (int i = 0; i < n; i++) {
                VertexModel curv = new VertexModel();
                l = sc.nextLine();
                int start = l.indexOf('"');
                int end = l.indexOf('"', start + 1);
                curv.setLabel(l.substring(start+1,end));
                l=l.substring(end+1);
                Scanner ss = new Scanner(l);
                if (ss.hasNextDouble())
                    curv.setLocation(new GraphPoint(ss.nextDouble(), ss.nextDouble()));
                V.add(curv);
            }
            g.insertVertices(V);

            //Read Edges
            l = sc.next();
            if (!l.contains("dges"))
                throw new GraphIOException("Incorrect Format(in the first line)");
            ArrayList<EdgeModel> E=new ArrayList<EdgeModel>();
            while (sc.hasNext()) {
                VertexModel src = V.get(sc.nextInt()-1);
                VertexModel trg = V.get(sc.nextInt()-1);
                EdgeModel cure = new EdgeModel(src, trg);
                cure.setWeight(sc.nextInt());
                E.add(cure);
            }
            g.insertEdges(E);
            return g;
        } catch (IOException e) {
            throw new GraphIOException(e.getMessage());
        }
    }

    public String getDescription() {
        return "Net Graph File Format (For Collaboration Graph App)";
    }
}
