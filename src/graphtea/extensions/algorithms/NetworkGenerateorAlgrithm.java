package graphtea.extensions.algorithms;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.GraphPoint;
import graphtea.graph.graph.Vertex;
import graphtea.library.Path;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.algorithmanimator.core.GraphAlgorithm;
import graphtea.plugins.algorithmanimator.extension.AlgorithmExtension;
import graphtea.plugins.graphgenerator.core.PositionGenerators;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.core.AlgorithmUtils;
import graphtea.plugins.main.saveload.core.extension.GraphIOExtensionHandler;

import javax.swing.*;
import java.awt.*;
import java.io.*;
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
        GraphPoint center = new GraphPoint(500,300);
        Vertex cent = new Vertex();
        cent.setLocation(center);
        cent.setColor(0);
        cent.setSize(new GraphPoint(0, 0));
        cent.setLabelLocation(new GraphPoint(0,-1));
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
                v.setColor(scNum);
                v.setLocation(new GraphPoint(ps[randInd].x, ps[randInd].y));
                v.setSize(new GraphPoint(0, 0));
                switch (scNum) {
                    case 1:
                        v.setLabel("+");
                        break;
                    case 2:
                        v.setLabel(String.valueOf((char)(0xD7)));
                        break;
                    case 3:
                        v.setLabel(String.valueOf((char)0x25A1));
                        break;

                }
                if(scNum == 1)
                v.setLabel("+");
                g.addVertex(v);
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
