// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.graph.atributeset;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GPoint;
import graphtea.graph.old.Arrow;
import graphtea.graph.old.GStroke;
import graphtea.platform.attribute.AttributeSet;
import graphtea.platform.attribute.NotifiableAttributeSetImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Azin Azadi
 * @see GraphAttrSet
 */
public class EdgeAttrSet implements AttributeSet {

    Edge e;
    NotifiableAttributeSetImpl atrs = new NotifiableAttributeSetImpl();

    public EdgeAttrSet(Edge e) {
        this.e = e;
    }

    public static final String SOURCE = "source";
    public static final String TARGET = "target";
    //public static final String DIRECTED = "directed";
    //public static final String ID = "id";
    public static final String WEIGHT = "Weight";
    public static final String LABEL = "Label";
    public static final String SHOW_WEIGHT = "Show Weight";
    public static final String COLOR = "Color";
    public static final String MARK = "Mark";
    public static final String STROKE = "Stroke";
    public static final String LABEL_LOCATION = "Label Location";
    public static final String ARROW = "Arrow";
    public static final String CURVE_CONTROL_POINT = "Curve Control Point";

    public void put(String atrName, Object val) {
        if (atrName.equals(LABEL)) {
            e.setLabel((String) val);
        } else if (atrName.equals(WEIGHT)) {
            e.setWeight((Integer) val);
        } else if (atrName.equals(SHOW_WEIGHT)) {
            e.setShowWeight((Boolean) val);
        } else if (atrName.equals(COLOR)) {
            e.setColor((Integer) val);
        } else if (atrName.equals(MARK)) {
            e.setMark((Boolean) val);
        } else if (atrName.equals(COLOR)) {
            e.setColor((Integer) val);
        } else if (atrName.equals(STROKE)) {
            e.setStroke((GStroke) val);
        } else if (atrName.equals(LABEL_LOCATION)) {
            e.setLabelLocation((GPoint) val);
        } else if (atrName.equals(ARROW)) {
            e.setArrow((Arrow) val);
        } else if (atrName.equals(CURVE_CONTROL_POINT)) {
            e.setCurveControlPoint((GPoint) val);
        } else {
            e.setUserDefinedAttribute(atrName, val);
        }
    }


    public Object get(String atrName) {
        Object ret = null;
        if (atrName.equals(LABEL)) {
            ret = e.getLabel();
        } else if (atrName.equals(STROKE)) {
            ret = e.getStroke();
        } else if (atrName.equals(COLOR)) {
            ret = e.getColor();
        } else if (atrName.equals(MARK)) {
            ret = e.getMark();
        } else if (atrName.equals(WEIGHT)) {
            ret = e.getWeight();
        } else if (atrName.equals(SHOW_WEIGHT)) {
            ret = e.isShowWeight();
        } else if (atrName.equals(LABEL_LOCATION)) {
            ret = e.getLabelLocation();
        } else if (atrName.equals(ARROW)) {
            ret = e.getArrow();
//        } else if (atrName.equals(HEAD)) {
//            ret = e.source;
//        } else if (atrName.equals(TAIL)) {
//            ret = e.target;
        } else if (atrName.equals(CURVE_CONTROL_POINT)) {
            ret = e.getCurveControlPoint();
        } else {
            ret = e.getUserDefinedAttribute(atrName);
        }
        return ret;
    }

    public Map<String, Object> getAttrs() {
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put(WEIGHT, e.getWeight());
        ret.put(LABEL, e.getLabel());
        ret.put(SHOW_WEIGHT, e.isShowWeight());
        ret.put(COLOR, e.getColor());
        ret.put(MARK, e.getMark());
        ret.put(STROKE, e.getStroke());
        ret.put(LABEL_LOCATION, e.getLabelLocation());
        ret.put(ARROW, e.getArrow());
        ret.put(CURVE_CONTROL_POINT, e.getCurveControlPoint());
        if (e.getUserDefinedAttributes() != null)
            ret.putAll(e.getUserDefinedAttributes());
        return ret;
    }

}
