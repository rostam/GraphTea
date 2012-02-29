// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.saveload;


import graphlab.graph.graph.GraphModel;
import graphlab.plugins.main.saveload.core.GraphIOException;
import graphlab.plugins.main.saveload.core.extension.GraphReaderExtension;
import graphlab.plugins.main.saveload.xmlparser.GraphmlHandlerImpl;
import graphlab.plugins.main.saveload.xmlparser.GraphmlParser;
import graphlab.platform.core.exception.ExceptionHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Load implements GraphReaderExtension {

    /**
     * loads a graph from a file, note that this method clears the graph first
     *
     * @param selectedFile
     * @param g
     */
    public static GraphModel loadGraphFromFile(File selectedFile) throws IOException, ParserConfigurationException, SAXException {
        FileInputStream istream = new FileInputStream(selectedFile);
        GraphmlHandlerImpl ghi = new GraphmlHandlerImpl();

        GraphmlParser.parse(new InputSource(istream), ghi);
        istream.close();
        return ghi.getGraph();
    }

    public boolean accepts(File file) {
        return SaveLoadPluginMethods.getExtension(file).equals(getExtension());
    }

    public String getName() {
        return "GraphML";
    }

    public String getExtension() {
        return "xml";
    }

    public GraphModel read(File file) throws GraphIOException {
        try {
            return loadGraphFromFile(file);
        } catch (Exception e) {
            ExceptionHandler.catchException(e);
            throw new GraphIOException(e.getMessage());
        }
    }

    public String getDescription() {
        return "GraphML Standard File Format for Graphs (Load)";
    }
}

