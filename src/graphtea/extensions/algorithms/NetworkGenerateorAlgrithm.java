package graphtea.extensions.algorithms;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.graph.old.GStroke;
import graphtea.library.BaseVertexProperties;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.algorithmanimator.core.GraphAlgorithm;
import graphtea.plugins.algorithmanimator.extension.AlgorithmExtension;
import graphtea.plugins.graphgenerator.core.PositionGenerators;
import graphtea.plugins.main.saveload.core.extension.GraphIOExtensionHandler;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Scanner;
import java.util.Vector;

/**
 * author: rostam
 */
public class NetworkGenerateorAlgrithm extends GraphAlgorithm implements AlgorithmExtension {
    public NetworkGenerateorAlgrithm(BlackBoard blackBoard) {
        super(blackBoard);
    }

    @Override
    public void doAlgorithm(){
        BaseVertexProperties.isLabelColorImp=true;
        step("Start of the simulation.");
        step("Select the file");
        JFileChooser fileChooser = new JFileChooser();
        if (GraphIOExtensionHandler.defaultFile != null)
            fileChooser.setCurrentDirectory(new File(GraphIOExtensionHandler.defaultFile));
        int l = fileChooser.showOpenDialog(null);

        Scanner fread = null;
        if (l == JFileChooser.APPROVE_OPTION) {
            try{
            File selectedFile = fileChooser.getSelectedFile();
            fread = new Scanner(selectedFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        GraphModel g = graphData.getGraph();
        g.setFont(new Font(g.getFont().getName(),g.getFont().getStyle(),18));

        int scNum = 0;
        int cnt = 0;
        int maxNumNodesInLevel = 30;
        Vector<Integer> forbiddenIndex = null;
        GPoint center = new GPoint(500,300);
        Vertex cent = new Vertex();
        cent.setLocation(center);
        cent.setColor(0);
        cent.setSize(new GPoint(0, 0));
        cent.setLabelLocation(new GPoint(0,-1));
        g.addVertex(cent);
        forbiddenIndex = new Vector<Integer>();
        while(fread.hasNextLine()) {
            String command = fread.nextLine();
            step(command);
            if(command.startsWith("Scenario")) {
                scNum = Integer.parseInt(command.substring(9));
                cnt=0;

            }
            else {
                Vertex v = new Vertex();
                int randInd = 0 + (int)(Math.random()*maxNumNodesInLevel);
                cnt++;
                int distance = 0;
                if(cnt < 10 )
                  distance = Integer.parseInt(command.substring(9, command.lastIndexOf('m')));
                else distance = Integer.parseInt(command.substring(10, command.lastIndexOf('m')));
                Point[] ps = PositionGenerators.circle(distance*3,(int)center.x,(int)center.y,maxNumNodesInLevel);
                while(forbiddenIndex.contains(randInd)) {
                    randInd = 0 + (int)(Math.random()*maxNumNodesInLevel);
                }
                forbiddenIndex.add(randInd);
                if(forbiddenIndex.size() > (maxNumNodesInLevel * 2/3)) forbiddenIndex =  new Vector<Integer>();
                v.setColor(1);
                v.setLocation(new GPoint(ps[randInd].x, ps[randInd].y));
                v.setShapeStroke(GStroke.empty);
                v.setSize(new GPoint(15,15));
                v.setLabelLocation(new GPoint(0,-2));
                //v.setSize(new GPoint(0, 0));
                switch (scNum) {
                    case 1:
                        v.setLabel(String.valueOf((char)(0x25CB)));
                        break;
                    case 2:
                        v.setLabel(String.valueOf((char)(0x25A1)));
                        break;
                    case 3:
                        //v.setLabel(String.valueOf((char)0x25A1));
                        v.setLabel(String.valueOf((char)0x25C7));
                        break;

                }
                g.addVertex(v);

                Edge e = new Edge(cent,v);

                e.setColor(8);
                g.addEdge(e);

            }

        }

        step("End of the simulation.");

    }

    @Override
    public String getName() {
        return "Network Generator ";
    }

    @Override
    public String getDescription() {
        return "The network generator ...";
    }
}
