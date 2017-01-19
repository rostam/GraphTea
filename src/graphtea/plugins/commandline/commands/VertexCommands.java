// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.commandline.commands;

import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.Vertex;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.platform.lang.CommandAttitude;
import graphtea.platform.parameter.Parameter;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.core.actions.vertex.AddVertex;

import java.util.Iterator;
import java.util.Vector;

/**
 * @author amir khosrowshahi , Mohammad Ali Rostami
 * @email rostamiev@gmail.com
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
        Vertex v = getVertexByLabel(label);

        v.setLabel(newlabel);
    }

    @CommandAttitude(name = "vertex_iterator", abbreviation = "_v_i"
            , description = "get a iterator on verteices of graph")
    public Iterator<Vertex> getVertexIterator() {
        return datas.getGraph().iterator();
    }

    @CommandAttitude(name = "is_selected", abbreviation = "_is"
            , description = "shows the vertex is selected or not")
    public Boolean isSelected(@Parameter(name = "vertex label:")String label) {
        Vertex v = getVertexByLabel(label);
//        //Init.run.ext_console.println(v.isSelected(), Init.run.ext_console.getResultColor());
        return v.isSelected();
    }


    @CommandAttitude(name = "replace", abbreviation = "_r"
            , description = "Replaces the given vertex by a new position")
    public void replace(@Parameter(name = "vertex label:")String label
            , @Parameter(name = "new X position:")int x,
              @Parameter(name = "new Y position:")int y) {
        Vertex v = getVertexByLabel(label);
        if (x == 0) {
            x = (int) v.getLocation().x;
        }
        if (y == 0) {
            y = (int) v.getLocation().y;
        }
        v.setLocation(new GPoint(x, y));
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
        Vertex v = getVertexByLabel(label);
        Vector<Vertex> vertices = new Vector<Vertex>();
        vertices.add(v);

        if (v != null) datas.select.setSelectedVertices(vertices);
        else throw new ShellCommandException("your entered vertex label doesnt exist!");
    }

    @CommandAttitude(name = "remove_vertex", abbreviation = "_rv"
            , description = "Removes a Vertex")
    public void removeVertex(@Parameter(name = "vertex label :")String label) {
        datas.getGraph().removeVertex(getVertexByLabel(label));
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
