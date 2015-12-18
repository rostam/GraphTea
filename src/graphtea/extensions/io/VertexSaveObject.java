package graphtea.extensions.io;

import graphtea.graph.graph.GraphPoint;
import graphtea.graph.graph.Vertex;
import graphtea.graph.old.GShape;
import graphtea.graph.old.StrokeSaveObject;

import java.io.Serializable;

/**
 * Created by rostam on 18.12.15.
 * Save Object for correcting the save process
 */

public class VertexSaveObject implements Serializable {
    String shape;
    StrokeSaveObject gs;
    String label;
    int color;
    boolean mark,selected;
    GraphPoint loc, shapeSize, labelLoc;

    public VertexSaveObject(Vertex v) {
        this.gs=new StrokeSaveObject(v.getShapeStroke());
        this.label=v.getLabel();
        this.color=v.getColor();
        this.mark=v.getMark();
        this.selected=v.isSelected();
        this.loc=v.getLocation();
        this.shape = v.getShape().name;
        this.shapeSize=v.getSize();
        this.labelLoc=v.getLabelLocation();
    }

    public Vertex getVertex() {
        Vertex v = new Vertex();
        v.setShapeStroke(gs.getGStroke());
        v.setLabel(label);
        v.setColor(color);
        v.setMark(mark);
        v.setSelected(selected);
        v.setLocation(loc);
        v.setShape(getCorrectShape());
        v.setSize(shapeSize);
        v.setLabelLocation(labelLoc);
        return v;
    }

    public GShape getCorrectShape() {
        if (shape.equals("Rectangle")) return GShape.RECTANGLE;
        if (shape.equals("Oval")) return GShape.OVAL;
        if (shape.equals("Round Rectangle")) return GShape.ROUNDRECT;
        if (shape.equals("Star")) return GShape.STAR;
        if (shape.equals("6 Point Star")) return GShape.SIXPOINTSTAR;
        if (shape.equals("7 Point Star")) return GShape.SEVENPOINTSTAR;
        if (shape.equals("8 Point Star")) return GShape.EIGHTPOINTSTAR;
        if (shape.equals("9 Point Star")) return GShape.NINEPOINTSTAR;
        if (shape.equals("10 Point Star")) return GShape.TENPOINTSTAR;
        if (shape.equals("Leftward Triangle")) return GShape.LEFTWARDTTRIANGLE;
        if (shape.equals("Rightward Triangle")) return GShape.RIGHTWARDTRIANGLE;
        if (shape.equals("Upward Triangle")) return GShape.UPWARDTRIANGLE;
        if (shape.equals("Downward Triangle")) return GShape.DOWNWARDTRIANGLE;
        if (shape.equals("Regular Hexagon")) return GShape.REGULARHEXAGON;
        if (shape.equals("Regular Pentagon")) return GShape.REGULARPENTAGON;
        if (shape.equals("Downward Trapezoid")) return GShape.DOWNWARDTRAPEZOID;
        if (shape.equals("Rightward Trapezoid")) return GShape.RIGHTWARDTRAPEZOID;
        if (shape.equals("Upward Trapezoid")) return GShape.UPWARDTRAPEZOID;
        if (shape.equals("Leftward Trapezoid")) return GShape.LEFTWARDTRAPEZOID;
        if (shape.equals("Downward Parallelogram")) return GShape.DOWNWARDPARALLELOGRAM;
        if (shape.equals("Upward Parallelogram")) return GShape.UPWARDPARALLELOGRAM;
        if (shape.equals("Nice Star")) return GShape.NICESTAR;
        if (shape.equals("6 Point Nice Star")) return GShape.NICESIXPOINTSTAR;
        if (shape.equals("7 Point Nice Star")) return GShape.NICESEVENPOINTSTAR;
        if (shape.equals("8 Point Nice Star")) return GShape.NICEEIGHTPOINTSTAR;
        if (shape.equals("9 Point Nice Star")) return GShape.NICENINEPOINTSTAR;
        if (shape.equals("10 Point Nice Star")) return GShape.NICETENPOINTSTAR;
        return GShape.RECTANGLE;
    }
}
