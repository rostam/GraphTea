// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.graph.graph;

import graphtea.graph.atributeset.VertexAttrSet;
import graphtea.graph.event.VertexListener;
import graphtea.graph.old.GShape;
import graphtea.graph.old.GStroke;
import graphtea.library.BaseVertex;
import graphtea.library.BaseVertexProperties;
import graphtea.platform.attribute.AttributeSet;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;


/**
 * Authors: Azin Azadi,Roozbeh
 */


public class Vertex extends BaseVertex {

    public VertexListener view;// = emptyListener;

    //todo(bug): Vertex is dependent on Fast Renderer!
    GShape shape = FastRenderer.defaultVertexShape;

    GStroke shapeStroke = FastRenderer.defaultBorderStroke;

    public boolean isSelected = false;
    String label = null;
    private Point center = new Point();
    //    private static GPoint dp=new GPoint(100,100);
    //represents the location of the vertex on the graph, this location will not changed by zooming and similar operations, the location only change by moving the vertex
    private GPoint location = new GPoint(100, 100);

    //    @UserModifiableProperty(name="defaultShapeDimension")
    private GPoint shapeSize = new GPoint(FastRenderer.defaultShapeDimension.getHeight(), FastRenderer.defaultShapeDimension.getWidth());

    /**
     * the location of the lable relative to the center of the edge
     */

    private GPoint labelLocation = new GPoint(0, 4);

    //________________________   Userdefined Attributes    _________________________________
    /**
     * This is a place to put custom attributes in the vertex, It will be shown in property editor and editable
     */
    private HashMap<String, Object> userDefinedAttributes = null;

    /**
     * these attributed will be added to each vertice's userDefinedAttributes on constructing time.
     */
    private static HashMap<String, Object> globalUserDefinedAttributes = null;


    /**
     * sets and stores a user defined attribute for the vertex. here you can put any attribute you like that are not available
     * in the standard attributes. your attributes will be editable in property editor part of GUI.
     * Use this method carefully. user defined attributes are stored in HashMap and bad use of them will cause memory leak in large graphs
     *
     * @see Vertex#addGlobalUserDefinedAttribute(String,Object)
     */
    public void setUserDefinedAttribute(String name, Object value) {
        if (userDefinedAttributes == null) {
            userDefinedAttributes = new HashMap<>();
        }
        userDefinedAttributes.put(name, value);
    }

    /**
     * returns the specified user defined attribute, or null if it does not exists.
     *
     * @param name The name of attribute
     * @return The user defined attribute with the given name
     */
    public <t> t getUserDefinedAttribute(String name) {
        if (userDefinedAttributes == null)
            return null;
        return (t) userDefinedAttributes.get(name);
    }

    /**
     * removes the given attribute from the list of user defined attributes
     *
     * @param name The name of user defined attribute
     */
    public void removeUserDefinedAttribute(String name) {
        userDefinedAttributes.remove(name);
        if (userDefinedAttributes.size() == 0)
            userDefinedAttributes = null;
    }

    /**
     * @return a HashMap containing all user defined attributes.
     */
    public HashMap<String, Object> getUserDefinedAttributes() {
        return userDefinedAttributes;
    }


    /**
     * sets and stores a global user defined attribute for the vertex. this attributes will be added to each vertex on
     * constructing time using setUserDefinedAttribute method.
     * <p/>
     * note that this method only affects the afterward created vertices, and current vertices will not affected by this method.
     */
    public static void addGlobalUserDefinedAttribute(String name, Object defaultvalue) {
        if (globalUserDefinedAttributes == null) {
            globalUserDefinedAttributes = new HashMap<>();
        }
        globalUserDefinedAttributes.put(name, defaultvalue);
    }

    /**
     * @see Vertex#addGlobalUserDefinedAttribute
     */
    public static void removeGlobalUserDefinedAttribute(String name) {
        globalUserDefinedAttributes.remove(name);
        if (globalUserDefinedAttributes.size() == 0)
            globalUserDefinedAttributes = null;
    }

    {
        //default constructor
        if (globalUserDefinedAttributes != null) {
            userDefinedAttributes = new HashMap<>();
            userDefinedAttributes.putAll(globalUserDefinedAttributes);
        }
    }

    /**
     * copy constructor
     * creates a copy (clone) of v
     *
     * @param v The given vertex
     */
    public Vertex(Vertex v) {
        super();
        this.label = v.label;
        this.location = v.location;
        this.shape = v.shape;
        this.shapeSize = v.shapeSize;
        this.shapeStroke = v.shapeStroke;
        this.labelLocation = v.labelLocation;
        //copies all attributes from second edge to first edge
        AttributeSet a = new VertexAttrSet(v);
        AttributeSet b = new VertexAttrSet(this);
        Map<String, Object> map = a.getAttrs();
        for (String name : map.keySet()) {
            b.put(name, map.get(name));
        }
    }

    public Vertex() {
        super();
    }

    public Vertex getCopy() {
        return new Vertex(this);
    }

    public String toString() {
        Integer i = getId();
        return label + " (" + i + ")";
    }

    public void setMark(boolean mark) {
        super.setMark(mark);
        fireModelListenerChanged();
    }

    public void setVertexListener(VertexListener listener) {
        view = listener;
//        if (listener == null)
//            view = emptyListener;
    }

    public void setProp(BaseVertexProperties prop) {
        super.setProp(prop);
        fireModelListenerChanged();
    }

    private void fireModelListenerChanged() {
        if (view != null)
            view.repaint(this);
    }

    /**
     * @return the center point of the vertex
     */
    public Point getCenter() {
        center.x = (int) (shapeSize.x / 2);
        center.y = (int) (shapeSize.y / 2);
        return center;
    }

    public void setLocation(Point p) {
        setLocation(new GPoint(p.x, p.y));
    }

    public void setLocation(GPoint p) {
        this.location = p;
        if (view != null)
            view.updateLocation(this, location);
    }

    /**
     * @return the location of the vertex. it is the center of vertex
     */
    public GPoint getLocation() {
        return location;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
        fireModelListenerChanged();
    }

    public void setShape(GShape shape) {
        this.shape = shape;
        fireModelListenerChanged();
    }

    public GShape getShape() {
        return shape;
    }

    public void setShapeStroke(GStroke stroke) {
        this.shapeStroke = stroke;
        fireModelListenerChanged();
    }

    public GStroke getShapeStroke() {
        return shapeStroke;
    }

    public void setSize(GPoint size) {
        this.shapeSize = size;
        if (view != null)
            view.updateSize(this, size);
        fireModelListenerChanged();
    }

    public GPoint getSize() {
        return shapeSize;
    }


    public Rectangle2D.Double getBounds() {
        return new Rectangle2D.Double(location.x - center.x, location.y - center.y, shapeSize.x, shapeSize.y);
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        fireModelListenerChanged();
    }

    public void repaint() {
        view.repaint(this);
    }

    public void setLabelLocation(GPoint graphPoint) {
        this.labelLocation = graphPoint;
        if (view != null)
            repaint();
    }

    public GPoint getLabelLocation() {
        return labelLocation;
    }

    public void setColor(int color) {
        super.setColor(color);
        if (view != null)
            repaint();
    }
}
