// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.commandline.commands;

import graphlab.graph.atributeset.GraphAttrSet;
import graphlab.graph.graph.Edge;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.Vertex;
import graphlab.platform.core.BlackBoard;
import graphlab.platform.lang.CommandAttitude;
import graphlab.platform.parameter.Parameter;
import graphlab.plugins.main.GraphData;
import graphlab.plugins.main.core.actions.edge.AddEdge;

/**
 * @author amir khosrowshahi , Mohammad Ali Rostami
 * @email ma.rostami@yahoo.com
 */
public class EdgeCommands {

    BlackBoard bb;
    private GraphData datas;

    public EdgeCommands(BlackBoard bb) {
        this.bb = bb;
        datas = new GraphData(bb);
    }

    @CommandAttitude(name = "add_edge", abbreviation = "_ae")
    public void addEdge(@Parameter(name = "first vertex label")String label1
            , @Parameter(name = "second vertex label")String label2) throws ShellCommandException {
        try {
            AddEdge.doJob(((GraphModel) bb.getData(GraphAttrSet.name)), getVertexByLabel(label1), getVertexByLabel(label2));
        } catch (NullPointerException e) {
            throw new ShellCommandException("your entered vertex label doesnt exist");
//            Init.run.ext_console.error("your entered vertex label doesnt exist!");
        }
    }

    @CommandAttitude(name = "remove_edge", abbreviation = "_re"
            , description = "Removes an Edge")
    public void removeEdge(@Parameter(name = "vertex 1 label :")String label1
            , @Parameter(name = "vertex 2 label:")String label2) {
        datas.getGraph().removeEdge(getEdge(label1, label2));
    }

    @CommandAttitude(name = "get_edge", abbreviation = "_ge"
            , description = "return the specific edge")
    private Edge getEdge(String label1, String label2) {
        return datas.getGraph().getEdge(getVertexByLabel(label1), getVertexByLabel(label2));
    }


    @CommandAttitude(name = "set_edge_label", abbreviation = "_sel")
    public void setEdgeLabel(@Parameter(name = "first vertex label :")String label1
            , @Parameter(name = "second vertex label :")String label2,
              @Parameter(name = "new edge label :")String newLabel) throws ShellCommandException {
        try {
            getEdge(label1, label2).setLabel(newLabel);
        } catch (NullPointerException e) {
            throw new ShellCommandException("your entered a edge label that doesn't exist!");
//            Init.run.ext_console.error("your entered a edge label that doesn't exist!");
        }
    }

    @CommandAttitude(name = "set_edge_weight", abbreviation = "_sew")
    public void setEdgeWeight(@Parameter(name = "first vertex label :")String label1
            , @Parameter(name = "second vertex label :")String label2,
              @Parameter(name = "new edge weight :")int newWeight) throws ShellCommandException {
        try {
            getEdge(label1, label2).setWeight(newWeight);
        } catch (NullPointerException e) {
            throw new ShellCommandException("your entered edge doesn't exist!");
//            Init.run.ext_console.error("your entered edge doesn't exist!");
        }
    }

    @CommandAttitude(name = "get_edge_label", abbreviation = "_gel")
    public String getEdgeLabel(@Parameter(name = "first vertex label :")String label1
            , @Parameter(name = "second vertex label :")String label2) throws ShellCommandException {
        try {
            //Init.run.ext_console.printlnResult(getEdge(label1, label2).model.getLabel());
            return getEdge(label1, label2).getLabel();

        } catch (NullPointerException e) {
            throw new ShellCommandException("your entered edge doesn't exist!");
        }
    }

    @CommandAttitude(name = "get_edge_weight", abbreviation = "_gew")
    public int getEdgeWeight(@Parameter(name = "first vertex label :")String label1
            , @Parameter(name = "second vertex label :")String label2) throws ShellCommandException {
        try {
            //Init.run.ext_console.printlnResult(getEdge(label1, label2).model.getWeight());
            return getEdge(label1, label2).getWeight();
        } catch (NullPointerException e) {
            throw new ShellCommandException("your entered edge doesn't exist!");
        }
    }

    Vertex getVertexByID(String id) {
        int ID = Integer.parseInt(id);
        for (Vertex v : datas.getGraph()) {
            //Init.run.ext_console.printlnResult(v.getId());
            if (v.getId() == ID)
                return v;
        }
        return null;
    }

    Vertex getVertexByLabel(String label) {
        for (Vertex v : datas.getGraph()) {
            if (v.getLabel().equals(label))
                return v;
        }
        return null;
    }
}
