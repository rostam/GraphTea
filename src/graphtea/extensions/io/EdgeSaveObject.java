package graphtea.extensions.io;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.GraphPoint;

import java.io.Serializable;

/**
 * Created by rostam on 18.12.15.
 *
 */
public class EdgeSaveObject implements Serializable {
    boolean select,showWeight,mark;
    GraphPoint labeloc,controlPoint;
    int source, target,color;
    int weight;
    String label;

    public EdgeSaveObject(Edge e) {
        select = e.isSelected();
        showWeight=e.isShowWeight();
        mark=e.getMark();
        labeloc=e.getLabelLocation();
        controlPoint=e.getCurveControlPoint();
        source = e.source.getId();
        target = e.target.getId();
        color = e.getColor();
        weight=e.getWeight();
        label=e.getLabel();
    }

    public void addEdge(GraphModel g) {
        Edge e = new Edge(g.getVertex(source),g.getVertex(target));
        e.setSelected(select);
        e.setShowWeight(showWeight);
        e.setMark(mark);
        e.setLabelLocation(labeloc);
        e.setCurveControlPoint(controlPoint);
        e.setColor(color);
        e.setWeight(weight);
        e.setLabel(label);
        g.addEdge(e);
    }


}
