// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.saveload;

import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.graph.AbstractGraphRenderer;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.SubGraph;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.plugin.PluginMethods;
import graphtea.plugins.main.saveload.image.Image;
import graphtea.plugins.main.saveload.matrix.CopyAsMatrix;
import graphtea.plugins.main.saveload.matrix.LoadMatrix;
import graphtea.plugins.main.saveload.matrix.Matrix;
import graphtea.plugins.main.saveload.matrix.SaveMatrix;

import java.io.File;
import java.io.IOException;

/**
 * @author azin azadi

 */
public class SaveLoadPluginMethods implements PluginMethods {
    private BlackBoard blackboard;

    public SaveLoadPluginMethods(BlackBoard blackboard) {
        this.blackboard = blackboard;
    }

//************************     G E N E R A L       ***********************************

//************************     S A V E       ***********************************

    /**
     * saves the current graph as matrix
     *
     * @see graphtea.plugins.main.saveload.matrix.SaveMatrix#saveMatrix(graphtea.graph.graph.GraphModel,java.io.File)
     */
    public void saveAsMatrix(File file) throws IOException {
        SaveMatrix.saveMatrix(getGraph(), file);
    }

    /**
     * @see graphtea.plugins.main.saveload.matrix.SaveMatrix#saveMatrix(graphtea.graph.graph.GraphModel,java.io.File)
     */
    public void saveAsMatrix(GraphModel g, File file) throws IOException {
        SaveMatrix.saveMatrix(g, file);
    }

    public String Graph2MatrixString(GraphModel g) {
        return (Matrix.Matrix2String(Matrix.graph2Matrix(g)));
    }

    public String Graph2MatrixHTML(GraphModel g){
        return (Matrix.Matrix2HTML(Matrix.graph2Matrix(g)));
    }

    /**
     * saves the current graph as a (format) image. format e.g. jpeg, png, ...
     */
    public void saveAsImage(File file, String format) {
        format = format.replaceAll(".", "");
        Image.save(Image.Graph2Image(blackboard.getData(AbstractGraphRenderer.EVENT_KEY), getGraph()), file, format);

    }

    /**
     * saves g in file as a (jpeg) image
     */
    public void saveAsImage(GraphModel g, File file, String extension) {
        graphtea.plugins.main.saveload.image.SaveImage.saveImage(g, file, extension);
    }


//************************     L O A D       ***********************************

    /**
     * loads the matrix saved in file to the current graph
     *
     * @param file
     */
    public GraphModel loadMatrix(File file) throws IOException {
        return LoadMatrix.loadMatrix(file);
    }

    /**
     * copies the Vertices and Edges as a graph to clipboard
     *
     * @param sd The given selected subgraph
     */
    //todo: move this to ccp (Rousbeh)
    public void copySelectedAsMatrix(SubGraph sd) {
        CopyAsMatrix.copyAsMatrix(getGraph(), sd);
    }

    private GraphModel getGraph() {
        return blackboard.getData(GraphAttrSet.name);
    }

    /**
     * Return the extension portion of the file's name .
     *
     * @see #getExtension
     * @see javax.swing.filechooser.FileFilter#accept
     */
    public static String getExtension(File f) {
        if (f != null) {
            String filename = f.getName();
            int i = filename.lastIndexOf('.');
            if (i > 0 && i < filename.length() - 1) {
                return filename.substring(i + 1).toLowerCase();
            }
        }
        return null;
    }
}
