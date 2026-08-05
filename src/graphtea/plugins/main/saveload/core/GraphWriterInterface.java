// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.saveload.core;

import graphtea.graph.graph.GraphModel;

import java.io.File;

/**
 * @author azin azadi

 */
public interface GraphWriterInterface {

    /**
     * Retrieves the name of the file type.
     *
     * @return the Name
     */
    String getName();

    /**
     * Retrieves the file extension for the file type. Example: "xml", "gr".
     *
     * @return the Extension
     */
    String getExtension();

    /**
     * Writes the graph to the file.
     *
     * @param file  the file
     * @param graph the graph
     * @throws GraphIOException In the case of the writer error.
     */
    void write(File file, GraphModel graph) throws GraphIOException;
}
