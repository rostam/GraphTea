// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.saveload.image;

import graphlab.graph.graph.AbstractGraphRenderer;
import graphlab.graph.graph.GraphModel;
import graphlab.platform.Application;
import graphlab.platform.parameter.Parameter;
import graphlab.platform.parameter.Parametrizable;
import graphlab.platform.preferences.lastsettings.UserModifiableProperty;
import graphlab.plugins.main.saveload.core.GraphIOException;
import graphlab.plugins.main.saveload.core.extension.GraphWriterExtension;

import javax.imageio.ImageIO;
import java.io.File;

/**
 * @author Azin Azadi

 */
public class SaveImage implements GraphWriterExtension, Parametrizable {
    @UserModifiableProperty(displayName = "Default Image Extension")
    @Parameter(name = "File Extension", description = "")
    public static String extension = "png";

    /**
     * saves g in file as a (.extension) image
     *
     * @param extension eg. jpeg, png, bmp, ... It can be any extension supported by ImageIO.write
     */
    public static void saveImage(GraphModel g, final File ff, String extension) {
        Image.save(Image.Graph2Image((AbstractGraphRenderer.getCurrentGraphRenderer(Application.blackboard)), g), ff, extension);
    }

    public String getName() {
        return "Image";
    }

    public String getExtension() {
        return extension;
    }

    public void write(File file, GraphModel graph) throws GraphIOException {
        //todo
//        throw new RuntimeException("not implemented");
        saveImage(graph, file, extension);
    }

    public String getDescription() {
        return extension + " File Format";
    }

    public String checkParameters() {
        if (!ImageIO.getImageWritersByFormatName(extension).hasNext())
            return "This file format is not supported!";
        else
            return null;
    }
}