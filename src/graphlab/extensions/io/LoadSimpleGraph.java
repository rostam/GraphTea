// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.extensions.io;

import graphlab.graph.graph.Edge;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.GraphPoint;
import graphlab.graph.graph.Vertex;
import graphlab.platform.StaticUtils;
import graphlab.plugins.main.saveload.SaveLoadPluginMethods;
import graphlab.plugins.main.saveload.core.GraphIOException;
import graphlab.plugins.main.saveload.core.extension.GraphReaderExtension;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * sample.simplegraph :<br>
 * graph:<br>
 * directed<br>
 * label g0<br>
 * begin vertices:<br>
 * vertex 0:<br>
 * label v1<br>
 * location 120,23<br>
 * color 3<br>
 * vertex 1:<br>
 * label second vertex<br>
 * color 1<br>
 * vertex 2:<br>
 * vertex 3:<br>
 * label 4<br>
 * begin edges:<br>
 * 0 -> 1<br>
 * label my edge<br>
 * color 12<br>
 * weight -2<br>
 * 3 -> 0<br>
 * label edge 2<br>
 * 2 -> 1<br>
 *
 * @author Azin Azadi
 */
public class LoadSimpleGraph implements GraphReaderExtension {

    public boolean accepts(File file) {
        return SaveLoadPluginMethods.getExtension(file).equals(getExtension());
    }

    public String getName() {
        return "Simple Graph";
    }

    public String getExtension() {
        return "simplegraph";
    }

    public GraphModel read(File file) throws GraphIOException {
        try {
            FileReader in = new FileReader(file);
//            BufferedReader sc = new BufferedReader(in);
            Scanner sc = new Scanner(file);
            String _, s = "";
            String l = sc.nextLine();
            if (!l.equals("graph:"))
                throw new GraphIOException("Incorrect Format(in the first line)");
            l = sc.nextLine();
            GraphModel g;
            if (l.equals("directed"))
                g = new GraphModel(true);
            else
                g = new GraphModel(false);
            sc.next();
            g.setLabel(sc.nextLine());

            //Read Vertices
            sc.nextLine();

            l = sc.next();
            ArrayList<Vertex> V = new ArrayList<Vertex>();
            Vertex curv = new Vertex();
            while (!l.equals("begin")) {    //begin edges
                if (l.equals("vertex")) {
                    String s1 = sc.next();
                    int i = parseInt(s1.substring(0, s1.length() - 1));
                    final GraphPoint zeropoint = new GraphPoint(0, 0);
                    curv.setLocation(new GraphPoint(400 * Math.random(), 400 * Math.random()));
                    V.add(curv = new Vertex());
                } else if (l.equals("label")) {
                    curv.setLabel(sc.nextLine());
                } else if (l.equals("location")) {
                    curv.setLocation((GraphPoint) StaticUtils.fromString(GraphPoint.class.getName(), sc.nextLine()));
                } else if (l.equals("color")) {
                    curv.setColor(parseInt(sc.next()));
                }
                l = sc.next();
            }

            g.insertVertices(V);
            //Read Edges
            sc.nextLine();
            Vertex v1 = V.get(parseInt(sc.next()));
            sc.next();
            Vertex v2 = V.get(parseInt(sc.next()));
            ArrayList<Edge> E = new ArrayList<Edge>();
            Edge cure = new Edge(v1, v2);
            E.add(cure);
            while (sc.hasNext()) {
                l = sc.next();
                if (l.equals("label")) {
                    cure.setLabel(sc.nextLine());
                } else if (l.equals("color")) {
                    cure.setColor(parseInt(sc.next()));
                } else if (l.equals("weight")) {
                    cure.setWeight(parseInt(sc.next()));
                } else {
                    sc.next();
                    cure = new Edge(V.get(parseInt(l)), V.get(parseInt(sc.next())));
                    E.add(cure);
                }
            }

            g.insertEdges(E);
            return g;
        } catch (IOException e) {
            throw new GraphIOException(e.getMessage());
        }
    }

    public String getDescription() {
        return "Simple Graph File Format";
    }
}
