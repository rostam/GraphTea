// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.saveload.core;

import graphtea.graph.graph.GraphModel;

import java.io.File;
import java.io.IOException;

/**
 * @author azin azadi

 */
public interface GraphReaderInterface {
    /**
     * Reads the file and enters the data in the graph. The method gets an
     * empty graph object, and initializes it with the data from the file.
     *
     * @param file the file
     * @return boolean Indicates whether the file is acceptable for reading.
     */
    boolean accepts(File file);

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
     * Reads the file and enters the data in the graph. The method gets an empty graph,
     * and initializes it with the data from the file.
     *
     * @param file The input file
     * @throws GraphIOException In the case of the reader error.
     */
    GraphModel read(File file) throws GraphIOException;
}
