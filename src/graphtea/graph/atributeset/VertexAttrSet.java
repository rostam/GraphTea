// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.graph.atributeset;

import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.Vertex;
import graphtea.graph.old.GShape;
import graphtea.graph.old.GStroke;
import graphtea.platform.attribute.AttributeSet;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Azin Azadi
 * @see graphtea.graph.atributeset.GraphAttrSet
 */
public class VertexAttrSet implements AttributeSet {
    private Vertex v;
    public static final String LABEL = "Label";
    public static final String COLOR = "Color";
    public static final String SHAPE = "Shape";
    public static final String BORDER = "Border Stroke";
    public static final String LOCATION = "Location";
    public static final String SIZE = "Size";
    public static final String MARK = "Mark";
    public static final String SELECTED = "Selected";
    public static final String LABEL_LOCATION = "Label Location";

    public VertexAttrSet(Vertex v) {
        this.v = v;
    }

    public Map<String, Object> getAttrs() {
        Map<String, Object> ret = new HashMap<String, Object>(15);
        ret.put(LABEL, v.getLabel());
        ret.put(COLOR, v.getColor());
        ret.put(SHAPE, v.getShape());
        ret.put(BORDER, v.getShapeStroke());
        ret.put(LOCATION, v.getLocation());
        ret.put(SIZE, v.getSize());
        ret.put(MARK, v.getMark());
        ret.put(SELECTED, v.isSelected());
        ret.put(LABEL_LOCATION, v.getLabelLocation());
        if (v.getUserDefinedAttributes() != null)
            ret.putAll(v.getUserDefinedAttributes());
        return ret;
    }

    public void put(String atrName, Object val) {
        if (atrName.equals(LABEL)) {
            v.setLabel((String) val);
        } else if (atrName.equals(SHAPE)) {
            v.setShape((GShape) val);
        } else if (atrName.equals(BORDER)) {
            v.setShapeStroke((GStroke) val);
        } else if (atrName.equals(LOCATION)) {
            v.setLocation((GPoint) val);
        } else if (atrName.equals(SIZE)) {
            v.setSize((GPoint) val);
        } else if (atrName.equals(MARK)) {
            v.setMark((Boolean) val);
        } else if (atrName.equals(SELECTED)) {
            v.setSelected((Boolean) val);
        } else if (atrName.equals(COLOR)) {
            v.setColor((Integer) val);
        } else if (atrName.equals(LABEL_LOCATION)) {
            v.setLabelLocation((GPoint) val);
        } else {
            v.setUserDefinedAttribute(atrName, val);
        }
    }

    public Object get(String atrName) {
        Object ret = null;
        if (atrName.equals(LABEL)) {
            ret = v.getLabel();
        } else if (atrName.equals(SHAPE)) {
            ret = v.getShape();
        } else if (atrName.equals(BORDER)) {
            ret = v.getShapeStroke();
        } else if (atrName.equals(LOCATION)) {
            ret = v.getLocation();
        } else if (atrName.equals(SIZE)) {
            ret = v.getSize();
        } else if (atrName.equals(MARK)) {
            ret = v.getMark();
        } else if (atrName.equals(SELECTED)) {
            ret = v.isSelected;
        } else if (atrName.equals(COLOR)) {
            ret = v.getColor();
        } else if (atrName.equals(LABEL_LOCATION)) {
            ret = v.getLabelLocation();
        } else {
            ret = v.getUserDefinedAttribute(atrName);
        }
        return ret;
    }

}