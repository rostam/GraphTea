// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.commandline.commands;

import graphlab.graph.atributeset.GraphAttrSet;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.GraphPoint;
import graphlab.graph.graph.VertexModel;
import graphlab.platform.core.BlackBoard;
import graphlab.platform.core.exception.ExceptionHandler;
import graphlab.platform.lang.CommandAttitude;
import graphlab.platform.parameter.Parameter;
import graphlab.plugins.main.GraphData;
import graphlab.plugins.main.core.actions.vertex.AddVertex;

import java.util.Iterator;
import java.util.Vector;

/**
 * @author amir khosrowshahi , Mohammad Ali Rostami
 * @email ma.rostami@yahoo.com
 */
public class VertexCommands {

    BlackBoard bb;
    private GraphData datas;

    public VertexCommands(BlackBoard bb) {
        this.bb = bb;
        datas = new GraphData(bb);
    }


    @CommandAttitude(name = "set_label", abbreviation = "_sl"
            , description = "Changes the label of a vertex")
    public void setLabel(@Parameter(name = "vertex label:")String label
            , @Parameter(name = "new vertex label:")String newlabel) {
        VertexModel v = getVertexByLabel(label);

        v.setLabel(newlabel);
    }

    @CommandAttitude(name = "vertex_iterator", abbreviation = "_v_i"
            , description = "get a iterator on verteices of graph")
    public Iterator<VertexModel> getVertexIterator() {
        return datas.getGraph().iterator();
    }

    @CommandAttitude(name = "is_selected", abbreviation = "_is"
            , description = "shows the vertex is selected or not")
    public Boolean isSelected(@Parameter(name = "vertex label:")String label) {
        VertexModel v = getVertexByLabel(label);
//        //Init.run.ext_console.println(v.isSelected(), Init.run.ext_console.getResultColor());
        return v.isSelected();
    }


    @CommandAttitude(name = "replace", abbreviation = "_r"
            , description = "Replaces the given vertex by a new position")
    public void replace(@Parameter(name = "vertex label:")String label
            , @Parameter(name = "new X position:")int x,
              @Parameter(name = "new Y position:")int y) {
        VertexModel v = getVertexByLabel(label);
        if (x == 0) {
            x = (int) v.getLocation().x;
        }
        if (y == 0) {
            y = (int) v.getLocation().y;
        }
        v.setLocation(new GraphPoint(x, y));
    }

    @CommandAttitude(name = "add_vertex", abbreviation = "_av", description = "adds a vertex")
    public void addVertex(@Parameter(name = "x positon")int x
            , @Parameter(name = "y positon")int y) {
        try {
            AddVertex.doJob((GraphModel) bb.getData(GraphAttrSet.name), x, y);
        }
        catch (Exception e) {
            ExceptionHandler.catchException(e);
        }
    }

    @CommandAttitude(name = "select_vertex", abbreviation = "_sv")
    public void selectVertex(@Parameter(name = "vertex label :")String label) throws ShellCommandException {
        VertexModel v = getVertexByLabel(label);
        Vector<VertexModel> vertices = new Vector<VertexModel>();
        vertices.add(v);

        if (v != null) datas.select.setSelectedVertices(vertices);
        else throw new ShellCommandException("your entered vertex label doesnt exist!");
    }

    @CommandAttitude(name = "remove_vertex", abbreviation = "_rv"
            , description = "Removes a Vertex")
    public void removeVertex(@Parameter(name = "vertex label :")String label) {
        datas.getGraph().removeVertex(getVertexByLabel(label));
    }

    VertexModel getVertexByID(String id) {
        int ID = Integer.parseInt(id);
        for (VertexModel v : datas.getGraph()) {
            //Init.run.ext_console.printlnResult(v.getId());
            if (v.getId() == ID)
                return v;
        }
        return null;
    }

    VertexModel getVertexByLabel(String label) {
        for (VertexModel v : datas.getGraph()) {
            if (v.getLabel().equals(label))
                return v;
        }
        return null;
    }
}
