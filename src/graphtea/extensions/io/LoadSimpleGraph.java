// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.io;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.Vertex;
import graphtea.platform.StaticUtils;
import graphtea.plugins.main.saveload.SaveLoadPluginMethods;
import graphtea.plugins.main.saveload.core.GraphIOException;
import graphtea.plugins.main.saveload.core.extension.GraphReaderExtension;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

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
            Scanner sc = new Scanner(file);
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
            g.setLabel(sc.next());

            sc.next();
            String fontName = sc.next();
            int style = Integer.parseInt(sc.next());
            int size = Integer.parseInt(sc.next());
            Font f = new Font(sc.next(), style, size);
            g.setFont(f);

            //Read Vertices
            sc.nextLine();

            l = sc.next();
            ArrayList<Vertex> V = new ArrayList<>();

            while (!l.equals("begin")) {    //begin edges
                String s1 = sc.next();
                int i = parseInt(s1.substring(0, s1.length() - 1));
                Vertex curv = new Vertex();
                final GPoint gp = new GPoint(0, 0);
                sc.next();
                curv.setLabel(sc.next());
                sc.next();
                curv.setLocation((GPoint) StaticUtils.fromString(GPoint.class.getName(), sc.nextLine()));
                sc.next();
                curv.setColor(parseInt(sc.next()));
                sc.next();
                curv.setLabelLocation(new GPoint(
                        Double.parseDouble(sc.next()),
                        Double.parseDouble(sc.next())
                ));
                V.add(curv);
                l = sc.next();
            }

            g.insertVertices(V);
            //Read Edges
            if (sc.hasNextLine())
                sc.nextLine();
            Vertex v1 = null;
            if (sc.hasNext())
                v1 = V.get(parseInt(sc.next()));
            else return g;
            sc.next();
            Vertex v2 = V.get(parseInt(sc.next()));
            ArrayList<Edge> E = new ArrayList<>();
            Edge cure = new Edge(v1, v2);
            E.add(cure);
            while (sc.hasNext()) {
                l = sc.next();
                switch (l) {
                    case "label":
                        cure.setLabel(sc.nextLine());
                        break;
                    case "color":
                        cure.setColor(parseInt(sc.next()));
                        break;
                    case "weight":
                        cure.setWeight(parseInt(sc.next()));
                        break;
                    default:
                        sc.next();
                        cure = new Edge(V.get(parseInt(l)), V.get(parseInt(sc.next())));
                        E.add(cure);
                        break;
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
