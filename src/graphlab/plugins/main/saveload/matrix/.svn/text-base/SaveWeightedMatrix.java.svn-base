// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.saveload.matrix;

import graphlab.graph.graph.GraphModel;
import graphlab.plugins.main.saveload.core.GraphIOException;
import graphlab.plugins.main.saveload.core.extension.GraphWriterExtension;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Azin Azadi
 */
public class SaveWeightedMatrix implements GraphWriterExtension {

    /**
     * saves g as matrix in file
     */
    public static void saveMatrix(GraphModel g, File file) throws IOException {
        FileWriter output = new FileWriter(file);
        output.write(WeightedMatrix.Matrix2String(WeightedMatrix.graph2Matrix(g)));
        output.close();
    }

    public String getName() {
        return "Weighted Matrix";
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
}


