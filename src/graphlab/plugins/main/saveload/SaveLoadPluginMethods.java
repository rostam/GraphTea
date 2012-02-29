// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.saveload;

import graphlab.graph.atributeset.GraphAttrSet;
import graphlab.graph.graph.AbstractGraphRenderer;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.SubGraph;
import graphlab.platform.core.BlackBoard;
import graphlab.platform.plugin.PluginMethods;
import graphlab.plugins.main.saveload.image.Image;
import graphlab.plugins.main.saveload.matrix.CopyAsMatrix;
import graphlab.plugins.main.saveload.matrix.LoadMatrix;
import graphlab.plugins.main.saveload.matrix.Matrix;
import graphlab.plugins.main.saveload.matrix.SaveMatrix;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
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
     * @see graphlab.plugins.main.saveload.matrix.SaveMatrix#saveMatrix(graphlab.graph.graph.GraphModel,java.io.File)
     */
    public void saveAsMatrix(File file) throws IOException {
        SaveMatrix.saveMatrix(getGraph(), file);
    }

    /**
     * @see graphlab.plugins.main.saveload.matrix.SaveMatrix#saveMatrix(graphlab.graph.graph.GraphModel,java.io.File)
     */
    public void saveAsMatrix(GraphModel g, File file) throws IOException {
        SaveMatrix.saveMatrix(g, file);
    }

    public String matrix2String(GraphModel g) {
        return (Matrix.Matrix2String(Matrix.graph2Matrix(g)));
    }


    /**
     * saves the current graph as a (format) image. format e.g. jpeg, png, ...
     */
    public void saveAsImage(File file, String format) {
        format = format.replaceAll(".", "");
        Image.save(Image.Graph2Image((AbstractGraphRenderer) blackboard.getData(AbstractGraphRenderer.EVENT_KEY), getGraph()), file, format);

    }

    /**
     * saves g in file as a (jpeg) image
     */
    public void saveAsImage(GraphModel g, File file, String extension) {
        graphlab.plugins.main.saveload.image.SaveImage.saveImage(g, file, extension);
    }


    /**
     * @see graphlab.plugins.main.saveload.Save#saveGraphML(graphlab.graph.graph.GraphModel,java.io.File)
     */
    public void saveAsGraphML(File file) throws IOException {
        graphlab.plugins.main.saveload.Save.saveGraphML(getGraph(), file);
    }

    /**
     * @see graphlab.plugins.main.saveload.Save#saveGraphML(graphlab.graph.graph.GraphModel,java.io.File)
     */
    public void saveAsGraphML(GraphModel g, File f) throws IOException {
        graphlab.plugins.main.saveload.Save.saveGraphML(g, f);
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
     * clears the current graph and load a graphml file saved in file to the current graph
     */
    public GraphModel loadGraphML(File f) throws IOException, ParserConfigurationException, SAXException {
        return graphlab.plugins.main.saveload.Load.loadGraphFromFile(f);
    }

    /**
     * copies the Vertices and Edges as a graph to clipboard
     *
     * @param sd
     */
    //todo: move this to ccp (Rousbeh)
    public void copySelectedAsMatrix(SubGraph sd) {
        CopyAsMatrix.copyAsMatrix(getGraph(), sd);
    }

    private GraphModel getGraph() {
        GraphModel g = blackboard.getData(GraphAttrSet.name);
        return g;
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
            ;
        }
        return null;
    }
}
