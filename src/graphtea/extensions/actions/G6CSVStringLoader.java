// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.extensions.actions;

import graphtea.extensions.G6Format;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.graphgenerator.core.PositionGenerators;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.extension.GraphActionExtension;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Azin Azadi
 */


public class G6CSVStringLoader implements GraphActionExtension, Parametrizable {
    @Parameter(name = "Graph indices from CSV")
    public String indices = "";

    public String getName() {
        return "G6 CSV Loader ";
    }

    public String getDescription() {
        return "Load graphs from G6 strings of a csv file associated with the given indices";
    }

    public void action(GraphData graphData) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose a file");
        fileChooser.showOpenDialog(new JDialog());
        File curFile = fileChooser.getSelectedFile();
        indices.trim();
        Scanner sc = new Scanner(indices);
        sc.useDelimiter(",");
        while (sc.hasNext()) {
            Scanner file_scan = null;
            String g6="";
            try {
                file_scan = new Scanner(curFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            int given_id = sc.nextInt();
            System.out.println(given_id);
            while(file_scan.hasNextLine()) {
                String line = file_scan.nextLine();
                int id = Integer.parseInt(line.substring(0, line.indexOf(",")).trim());
                if (id == given_id)
                    g6 = line.substring(line.lastIndexOf(",") + 1).trim();
            }
            if(!g6.equals("")) {
                GraphModel g = G6Format.stringToGraphModel(g6);
                Point pp[] = PositionGenerators.circle(200, 400, 250, g.numOfVertices());

                int tmpcnt = 0;
                for (Vertex v : g) {
                    v.setLocation(pp[tmpcnt]);
                    tmpcnt++;
                }
                graphData.core.showGraph(g);
            }
        }
    }

    public String checkParameters() {
        return null;
    }

    @Override
    public String getCategory() {
        return "Other Actions";
    }
}


