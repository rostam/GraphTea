// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.extensions.io;

import graphlab.graph.graph.Edge;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.GraphPoint;
import graphlab.graph.graph.Vertex;
import graphlab.plugins.main.saveload.core.GraphIOException;
import graphlab.plugins.main.saveload.core.extension.GraphWriterExtension;
import graphlab.platform.StaticUtils;

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

            //output vertices
            o.println("begin vertices:");
            for (Vertex v : graph) {
                o.println("vertex " + v.getId() + ":");
                o.println("label " + v.getLabel());
                GraphPoint p = v.getLocation();
                o.println("location " + p.getX() + "," + p.getY());
                o.println("color " + v.getColor());
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
