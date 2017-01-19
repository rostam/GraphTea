// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.io;

import graphtea.graph.graph.GraphModel;
import graphtea.plugins.main.saveload.core.GraphIOException;
import graphtea.plugins.main.saveload.core.extension.GraphWriterExtension;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaveGraph implements GraphWriterExtension {

    public String getName() {
        return "GraphTea Format";
    }

    public String getExtension() {
        return "tea";
    }

    @Override
    public void write(File file, GraphModel graph) throws GraphIOException {
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(file));
            out.writeObject(new GraphSaveObject(graph));
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDescription() {
        return "GraphTea File Format";
    }
}
