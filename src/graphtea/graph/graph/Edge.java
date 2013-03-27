// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.graph.graph;

import graphtea.graph.atributeset.EdgeAttrSet;
import graphtea.graph.event.EdgeListener;
import graphtea.graph.old.Arrow;
import graphtea.graph.old.ArrowHandler;
import graphtea.graph.old.GStroke;
import graphtea.library.BaseEdge;
import graphtea.library.BaseEdgeProperties;
import graphtea.platform.attribute.AttributeSet;

import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hooman Mohajeri Moghaddam added new constructor
 * User: azin azadi,roozbeh ebrahimi
 *       Mohsen Mansouryar > Added Loop Support
 *
 */
public class Edge extends BaseEdge<Vertex> {

    public EdgeListener view;
    private boolean isSelected = false;
    private boolean showWeight = true;
    private GStroke stroke;

    private Arrow arrow = ArrowHandler.defaultArrow;

    /**
     * the location of the lable relative to the center of the edge
     */
    private GraphPoint labelLocation = new GraphPoint(0, 0);

    /**
     * it's a straight line between source and target vertices
     */
    public Line2D.Double line = new Line2D.Double();

    public GraphPoint curveControlPoint;

    /**
     * the text that is showing as the label of the edge on screen
     */
    String text = "";

    /**
     * This is a place to put custom attributes in the vertex, It will be shown in property editor and editable
     */
    private HashMap<String, Object> userDefinedAttributes = null;

    /**
     * these attributed will be added to each vertice's userDefinedAttributes on constructing time.
     */
    private static HashMap<String, Object> globalUserDefinedAttributes = null;

    /**
     * initial diameter of a loop
     */
    private double loopWidth = 45;
    public final static double MIN_LOOP_WIDTH = 35;

    /**
     * sets and stores a user defined attribute for the vertex. here you can put any attribute you like that are not available
     * in the standard attributes. your attributes will be editable in property editor part of GUI.
     * Use this method carefully. user defined attributes are stored in HashMap and bad use of them will cause memory leak in large graphs
     *
     * @param name
     * @param value
     */
    public void setUserDefinedAttribute(String name, Object value) {
        if (userDefinedAttributes == null) {
            userDefinedAttributes = new HashMap<String, Object>();
        }
        userDefinedAttributes.put(name, value);
    }

    /**
     * returns the specified user defined attribute, or null if it does not exists.
     *
     * @param name
     * @return
     */
    public <t> t getUserDefinedAttribute(String name) {
        if (userDefinedAttributes == null)
            return null;
        return (t) userDefinedAttributes.get(name);
    }

    /**
     * removes the given attribute from the list of user defined attributes
     *
     * @param name
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
            globalUserDefinedAttributes = new HashMap<String, Object>();
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
            userDefinedAttributes = new HashMap<String, Object>();
            userDefinedAttributes.putAll(globalUserDefinedAttributes);
        }
    }

    /**
     * This is the copy constructor method for the Edge Model
     *
     * @param vm1
     * @param vm2
     * @return
     */
    public Edge getCopy(Vertex vm1, Vertex vm2) {
        return new Edge(this, vm1, vm2);
    }

    /**
     * copy constructor
     * creates a copy (clone) of edge,
     * the source and target of edge will be source and target parameters
     * other properties of edge will be copied from edge parameter
     *
     * @param edge
     */
    public Edge(Edge edge, Vertex source, Vertex target) {
        super(source, target);
        //copies all attributes from second edge to first edge
        AttributeSet a = new EdgeAttrSet(edge);
        AttributeSet b = new EdgeAttrSet(this);
        Map<String, Object> map = a.getAttrs();
        for (String name : map.keySet()) {
            b.put(name, map.get(name));
        }
        updateText();
        showWeight = GraphModel.showEdgeWeights;
        stroke = FastRenderer.defaultStroke;
    }

    public Edge(Vertex v1, Vertex v2) {
        super(v1, v2);
        showWeight = GraphModel.showEdgeWeights;
        stroke = FastRenderer.defaultStroke;
    }

    public Edge(Vertex v1, Vertex v2, BaseEdgeProperties prop) {
        super(v1, v2, prop);
        showWeight = GraphModel.showEdgeWeights;
        stroke = FastRenderer.defaultStroke;
    }
    
    GStroke last;

    public void setMark(boolean m) {
        super.setMark(m);
        repaintView();
    }

    private void repaintView() {
        if (view != null) {
            view.repaint(this);
        }
    }

    public void setStroke(GStroke stroke) {
        this.stroke = stroke;
        repaintView();
    }

    public GStroke getStroke() {
        return stroke;
    }

    public void setWeight(int weight) {
        super.setWeight(weight);
        updateText();
        repaintView();
    }

    public void setProp(BaseEdgeProperties prop) {
        super.setProp(prop);
        repaintView();
    }

    public void setColor(int color) {
        super.setColor(color);
        repaintView();
    }

    public Arrow getArrow() {
        return arrow;
    }

    public void setArrow(Arrow a) {
        this.arrow = a;
        repaintView();
    }

    public void setEdgeListener(EdgeListener listener) {
        this.view = listener;
    }

    /**
     * Returns the getAngle, in radians, of the edge from the positive
     * x-axis, where the source vertex marks the origin of the edge. The
     * getAngle will be in the range of 0 to 2<em>pi</em> and will increase
     * counter-clockwise around the origin.
     * (this method taked from GraphMaker Project)
     *
     * @return getAngle in radians from positive x-axis.
     */
    public double getAngle() {
        // Subtract y coordinates in reverse order from normal
        // as the screen coordinate system is flipped along the
        // x-axis from the Cartesian coordinate system.
        GraphPoint sourceLoc = source.getLocation();
        GraphPoint targetLoc = target.getLocation();
        double angle = Math.atan2(sourceLoc.y  - targetLoc.y ,
                sourceLoc.x  - targetLoc.x );
        if (angle < 0) {
            // atan2 returns getAngle in phase -pi to pi, which means
            // we have to convert the answer into 0 to 2pi range.
            angle += 2 * Math.PI;
        }
        return angle;
    } // getAngle

    public String toString() {
        return label+":"+source + "->" + target;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        repaintView();
    }

    public String getId() {
        //todo: omid: BaseEdge doesn't have id!
        return source.getId() + "" + target.getId();
    }


    //---------------    + Curved Edge            --------------------
    /**
     * @return return the curve control point of this edge, relative to the center of the edge
     *         the abstract position for regular edges can be give by: (src.x + trg.x) / 2 + ctrlPnt.x  , also for y
     *         and for loops is src.x + minLoopWidth * sqrt(2), src.y + minLoopWidth * sqrt(2)
     */
    public GraphPoint getCurveControlPoint() {
        if (curveControlPoint == null) {
            if (!isLoop()) {
                curveControlPoint = new GraphPoint(0, 0);
            } else {
                setCurveControlPoint(new GraphPoint(MIN_LOOP_WIDTH, MIN_LOOP_WIDTH));
            }
        }
        return curveControlPoint;
    }


    /**
     * returns absolute center point of a loop based on its loopWidth
     * @return
     */
    public GraphPoint getLoopCenter(){
        double cx = getCurveControlPoint().x,
               cy = getCurveControlPoint().y,
               sx = source.getLocation().x,
               sy = source.getLocation().y;
        return new GraphPoint(sx + cx/2f, sy + cy/2f);
    }

    public double getLoopWidth() {
        return loopWidth;
    }

    public void setCurveControlPoint(GraphPoint controlPoint) {
        
        boolean allowed = false;
        if (isLoop()) {
            double value = GraphPoint.distance(0, 0, controlPoint.x, controlPoint.y);
            if (value > MIN_LOOP_WIDTH) {
                loopWidth = value;
                allowed = true;
            }
        } else {allowed = true;}

        if (allowed) {
            this.curveControlPoint = controlPoint;
            repaintView();
        }
    }
    //---------------    - Curved Edge            --------------------

    //--------------        +text operation            ---------------
    String label;

    public void setLabel(String label) {
        this.label = label;
        updateText();
    }

    public String getLabel() {
        return label;
    }

    private void updateText() {
        String _label;
        if (!isShowWeight())
            _label = label;
        else if (isShowWeight())
            _label = String.valueOf(super.getWeight());
        else
            _label = label + "," + String.valueOf(super.getWeight());
        this.text = _label;
        repaintView();
    }
    //--------------        -text operation            ---------------


    public boolean isShowWeight() {
        return showWeight;
    }

    public void setShowWeight(boolean showWeight) {
        this.showWeight = showWeight;
        updateText();
        repaintView();
    }

    public void updateBounds() {
        repaintView();
    }

    public void setLabelLocation(GraphPoint graphPoint) {
        this.labelLocation = graphPoint;
        repaintView();
    }

    public GraphPoint getLabelLocation() {
        return labelLocation;
    }

    /**
     * return true if the edges is a loop
     */
    public boolean isLoop(){
        return source.equals(target);
    }

}
