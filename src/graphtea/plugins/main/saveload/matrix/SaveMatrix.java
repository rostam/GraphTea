// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.saveload.matrix;

import graphtea.graph.graph.GraphModel;
import graphtea.plugins.main.saveload.core.GraphIOException;
import graphtea.plugins.main.saveload.core.extension.GraphWriterExtension;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Azin Azadi
 */
public class SaveMatrix implements GraphWriterExtension {

    /**
     * saves g as matrix in file
     */
    public static void saveMatrix(GraphModel g, File file) throws IOException {
        FileWriter output = new FileWriter(file);
        output.write(Matrix.Matrix2String(Matrix.graph2Matrix(g)));
        output.close();
    }

    public String getName() {
        return "Matrix";
    }

    public String getExtension() {
        return "mat";
    }

    public void write(File file, GraphModel graph) throws GraphIOException {
        try {
            saveMatrix(graph, file);
        } catch (IOException e) {
            throw new GraphIOException(e.getMessage());
        }

    }

    public String getDescription() {
        return "Matrix File Format";
    }

    public static void saveMatrixes(File file, GraphModel g, boolean isDirected) throws IOException {
        FileWriter output = new FileWriter(file,true);
        output.append(System.getProperty("line.separator"));
        output.append(Matrix.Matrix2String(Matrix.graph2Matrix(g)));
        output.close();
    }
}


