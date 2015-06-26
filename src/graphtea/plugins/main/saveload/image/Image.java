// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.saveload.image;

import graphtea.graph.graph.AbstractGraphRenderer;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.core.exception.ExceptionHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;


/**
 * @author Azin Azadi
 */
public class Image {
//    public static void SaveGraph2Image(GraphModel g){
    //
    //    }
    static boolean debug = false;

    public static BufferedImage Graph2Image(AbstractGraphRenderer gv, GraphModel gm) {
        //Rectangle r = gm.getZoomedBounds().getBounds();
        Rectangle r = gv.getBounds();
        if (debug) System.out.println("start");
        //todo: the following line is depended on graph view to be a swing component
        BufferedImage bi = gv.getGraphicsConfiguration().createCompatibleImage(r.width, r.height);
        if (debug) System.out.println("bi created");
        Graphics2D gr = bi.createGraphics();
        gr.translate(-r.x, -r.y);
//        if (gv.getMiny() < 0)
        gr.translate(gv.getMinx(), gv.getMiny());
        gv.paint((Graphics) gr);
        if (debug) System.out.println("bi painted");
        gr.translate(r.x, r.y);
        return bi;
    }

    public static void save(RenderedImage image, File ff, String extension) {
        try {
            if (debug) System.out.println("writing started");
            ImageIO.write(image, extension, ff);
            if (debug) System.out.println("writing finished");
        } catch (IOException e) {
            ExceptionHandler.catchException(e);  //To change body of catch statement use File | Settings | File Templates.
        }

//        fc.setTitle("SaveImage dijkstra.Image As ...");
        //      fc.sa

    }
}