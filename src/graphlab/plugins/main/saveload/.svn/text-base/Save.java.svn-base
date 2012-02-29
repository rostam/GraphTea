// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.saveload;

import graphlab.graph.graph.GraphModel;
import graphlab.graph.io.GraphML;
import graphlab.plugins.main.saveload.core.GraphIOException;
import graphlab.plugins.main.saveload.core.extension.GraphWriterExtension;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Saves a graph file to GraphML file format
 * @author Reza Mohammadi, Azin Azadi
 */
public class Save implements GraphWriterExtension {

    /**
     * saves g in file as a GraphML
     *
     * @param g
     * @param file
     * @throws IOException
     */
    public static void saveGraphML(GraphModel g, File file) throws IOException {
        FileWriter output = null;
        output = new FileWriter(file);
        output.write(""
                + "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<!DOCTYPE graphml SYSTEM \"graphml.dtd\">\n"
                + "<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\"\n"
                + "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
                + "    xsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns\n"
                + "     http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd\">\n");
        output.write(GraphML.graph2GraphML(g));
        output.write("</graphml>");
        output.close();
    }

    public String getName() {
        return "GraphML";
    }

    public String getExtension() {
        return "xml";
    }

    public void write(File file, GraphModel graph) throws GraphIOException {
        try {
            saveGraphML(graph, file);
        } catch (IOException e) {
            throw new GraphIOException(e.getMessage());
        }
    }

    public String getDescription() {
        return "GraphML Standard File Format for Graphs (Save)";
    }
}