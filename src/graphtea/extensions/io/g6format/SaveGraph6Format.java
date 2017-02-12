// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.io.g6format;

import Jama.Matrix;
import graphtea.extensions.G6Format;
import graphtea.graph.graph.GraphModel;
import graphtea.plugins.main.saveload.core.GraphIOException;
import graphtea.plugins.main.saveload.core.extension.GraphWriterExtension;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveGraph6Format implements GraphWriterExtension {

    public String getName() {
        return "Graph6 Format";
    }

    public String getExtension() {
        return "g6";
    }

    @Override
    public void write(File file, GraphModel graph) throws GraphIOException {
        try {
            FileWriter fw = new FileWriter(file,true);
            G6Format g6f = new G6Format();
            String s = g6f.graphToG6(graph);
            fw.write(s);
            fw.write(System.lineSeparator());
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDescription() {
        return "Graph6 File Format";
    }


}