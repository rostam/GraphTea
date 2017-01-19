// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.io;

import Jama.Matrix;
import graphtea.extensions.reports.coloring.MM;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.plugins.graphgenerator.core.PositionGenerators;
import graphtea.plugins.main.saveload.SaveLoadPluginMethods;
import graphtea.plugins.main.saveload.core.GraphIOException;
import graphtea.plugins.main.saveload.core.extension.GraphReaderExtension;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author Ali ROstami
 */
public class LoadMtx implements GraphReaderExtension {

    public boolean accepts(File file) {
        return SaveLoadPluginMethods.getExtension(file).equals(getExtension());
    }

    public String getName() {
        return "Mtx format";
    }

    public String getExtension() {
        return "mtx";
    }

    public GraphModel read(File file) throws GraphIOException {
        try {
            Matrix mm = MM.loadMatrixFromSPARSE(file);
            int rows = mm.getRowDimension();
            int cols = mm.getColumnDimension();
            GraphModel g = new GraphModel(false);
            for(int i=0;i<rows;i++) {
                g.addVertex(new Vertex());
            }
            for(int i=0;i<rows;i++) {
                for(int j=0;j<cols;j++) {
                    if(mm.get(i,j) > 0) {
                        g.addEdge(new Edge(
                                g.getVertex(i),
                                g.getVertex(j)
                        ));
                    }
                }
            }
            Point pp[] = PositionGenerators.circle(420, 300, 250, g.numOfVertices());

            int tmpcnt = 0;
            for (Vertex v : g) {
                v.setLocation(pp[tmpcnt]);
                tmpcnt++;
            }
            return g;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getDescription() {
        return "GraphTea File Format";
    }
}
