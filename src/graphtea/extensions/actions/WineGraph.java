package graphtea.extensions.actions;

import graphtea.extensions.io.LoadSimpleGraph;
import graphtea.extensions.io.SaveSimpleGraph;
import graphtea.graph.graph.*;
import graphtea.graph.old.GStroke;
import graphtea.library.BaseVertex;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.core.AlgorithmUtils;
import graphtea.plugins.main.extension.GraphActionExtension;
import graphtea.plugins.main.saveload.core.GraphIOException;
import graphtea.plugins.main.saveload.core.extension.GraphIOExtensionHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: rostam
 * Date: 5/10/13
 * Time: 7:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class WineGraph implements GraphActionExtension {
    static final String CURVE_WIDTH = "Curve Width";
    JFileChooser fileChooser = new JFileChooser();

    @Override
    public String getName() {
        return "Wine Art";
    }

    @Override
    public String getDescription() {
        return "Wine Art";
    }

    @Override
    public void action(GraphData graphData) {
        fileChooser.showOpenDialog(null);
        AbstractGraphRenderer gr = AbstractGraphRenderer.getCurrentGraphRenderer(graphData.getBlackboard());
        gr.addPrePaintHandler(new Painter2(graphData,fileChooser));
        gr.repaint();
    }
}


class Painter2 implements PaintHandler {
    private final JFileChooser fc;
    GraphData gd;
    GraphModel G;
    Integer curveWidth = 6;
    public Painter2(GraphData gd, JFileChooser fileChooser) {
        this.gd = gd;
        this.G  = gd.getGraph();
        this.fc = fileChooser;
    }


    public void paint(Graphics gr1d, Object destinationComponent, Boolean b) {
        try {
            File selectedFile = fc.getSelectedFile();
            BufferedImage originalImage = ImageIO.read(selectedFile);
            gr1d.drawImage(originalImage,100, 50,300,600,null);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }
}

