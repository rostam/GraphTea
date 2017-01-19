// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.io;

import graphtea.graph.graph.GraphModel;
import graphtea.plugins.main.saveload.SaveLoadPluginMethods;
import graphtea.plugins.main.saveload.core.GraphIOException;
import graphtea.plugins.main.saveload.core.extension.GraphReaderExtension;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * @author Ali ROstami
 */
public class LoadGraph implements GraphReaderExtension {

    public boolean accepts(File file) {
        return SaveLoadPluginMethods.getExtension(file).equals(getExtension());
    }

    public String getName() {
        return "GraphTea Format";
    }

    public String getExtension() {
        return "tea";
    }

    public GraphModel read(File file) throws GraphIOException {
        try {
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream(file));
            GraphSaveObject gso = (GraphSaveObject) in.readObject();
            return gso.getG();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getDescription() {
        return "GraphTea File Format";
    }
}
