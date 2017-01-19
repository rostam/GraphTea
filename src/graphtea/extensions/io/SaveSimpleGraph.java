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
import graphtea.plugins.main.saveload.core.GraphIOException;
import graphtea.plugins.main.saveload.core.extension.GraphWriterExtension;

import java.awt.*;
import java.io.*;
import java.util.Iterator;

public class SaveSimpleGraph implements GraphWriterExtension {

    public static void save(GraphModel g, String fileName){
        try {
            new SaveSimpleGraph().write(new File(fileName), g);
        } catch (GraphIOException e) {
            StaticUtils.addExceptionLog(e);
        }
    }

    
    public String getName() {
        return "Simple Graph";
    }

    public String getExtension() {
        return "simplegraph";
    }

    public void write(File file, GraphModel graph) throws GraphIOException {
        try {
            PrintWriter o = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            o.println("graph:");
            if (graph.isDirected())
                o.println("directed");
            else
                o.println("undirected");
            o.println("label " + graph.getLabel());
            Font f = graph.getFont();
            o.println("font "  + f.getName() + " "
                    + f.getStyle() + " " + f.getSize());

            //output vertices
            o.println("begin vertices:");
            for (Vertex v : graph) {
                o.println("vertex " + v.getId() + ":");
                o.println("label " + v.getLabel());
                GPoint p = v.getLocation();
                o.println("location " + p.getX() + "," + p.getY());
                o.println("color " + v.getColor());
                o.println("labellocation " + v.getLabelLocation().getX()
                        +  " "  + v.getLabelLocation().getY() );
            }

            //output edges
            o.println("begin edges");
            for (Iterator<Edge> ie = graph.edgeIterator(); ie.hasNext();) {
                Edge e = ie.next();
                o.println(e.source.getId() + " -> " + e.target.getId());
                o.println("label " + e.getLabel());
                o.println("color " + e.getColor());
                o.println("weight " + e.getWeight());
            }
            o.close();
        } catch (IOException e) {
            throw new GraphIOException(e.getMessage());
        }
    }

    public String getDescription() {
        return "Simple Graph File Format";
    }
}
