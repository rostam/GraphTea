// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.io;
import graphtea.platform.core.exception.ExceptionHandler;

import graphtea.graph.graph.GraphModel;
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
    public void write(File file, GraphModel graph) {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(file))) {
            out.writeObject(new GraphSaveObject(graph));
        } catch (IOException e) {
            ExceptionHandler.catchException(e);
        }
    }

    public String getDescription() {
        return "GraphTea File Format";
    }
}
