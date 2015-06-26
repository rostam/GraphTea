// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.graph.atributeset;

import graphtea.graph.graph.GraphModel;
import graphtea.platform.attribute.AttributeSet;
import graphtea.platform.attribute.NotifiableAttributeSetImpl;
import graphtea.platform.lang.ArrayX;

import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * this class provides a way to have a Graph object as a NotifiableAttributeSet
 * this is usefull whenever some one wants to work blindly with graph attributes
 * for example on saving graph to a gml xml file it is important to have all attributes
 * saved, the meaning of values of attributes is not important, or when a property editor
 * wants to show and edit the attributes of graph to the user, at that time it can use
 * a XAttribute to have better looks see GraphPropertyEditor class as an example.
 * <p/>
 * An other usage of this class is whenever some one wants to listen to changes of
 * a user defined or a rare attribute which normally has no listening capability,
 * for example when you want to change the program according to Graph ID whenever it
 * changes. ID attribute on graph has not a formal listening way, so this class is usefull
 * at that time.
 *
 * @author Hooman Mohajeri Moghaddam - added image file for background.
 * @author Azin Azadi
 * @see graphtea.ui.AttributeSetView
 * @see graphtea.ui.NotifiableAttributeSetView
 * @see graphtea.plugins.main.core.actions.GraphPropertyEditor
 */
public class GraphAttrSet implements AttributeSet {

    GraphModel g;
    NotifiableAttributeSetImpl atrs = new NotifiableAttributeSetImpl();

    public static final String EDGEDEFAULT = "edgedefault";
    public static final String EDGEDEFAULT_DIRECTED = "directed";
    public static final String EDGEDEFAULT_UNDIRECTED = "undirected";

    //    public static final String ID = "id";
    public static final String DIRECTED = "directed";
    public static final String LABEL = "label";
    public static final String ZOOM = "Zoom";
    public static final String FONT = "Font";
    public static final String DRAW_VERTEX_LABELS = "Vertex Labels";
    public static final String DRAW_EDGE_LABELS = "Edge Labels";
    public static final String IS_EDGES_CURVED = "Curved Edges";
    public static final String BACKGROUND_IMAGE = "Background";
    public static final String Allow_Loops = "Allow Loops";

    //****//
    //    public static final String CENTERX = "centerx";
    //    public static final String CENTERY = "centery";
    public static final String name = "Graph.GraphModel";

    public Map<String, Object> getAttrs() {
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put(DRAW_VERTEX_LABELS, g.isDrawVertexLabels());
        ret.put(DRAW_EDGE_LABELS, g.isDrawEdgeLabels());
        ret.put(IS_EDGES_CURVED, g.isEdgesCurved());
        ret.put(Allow_Loops, g.isAllowLoops());
        ret.put(DIRECTED, g.isDirected() ? EDGEDEFAULT_DIRECTED : EDGEDEFAULT_UNDIRECTED);
        ret.put(LABEL, g.getLabel());
        ret.put(ZOOM, g.getZoom());
        ret.put(FONT, g.getFont());
        ret.put(BACKGROUND_IMAGE, g.getBackgroundImageFile());
        if (g.getUserDefinedAttributes() != null)
            ret.putAll(g.getUserDefinedAttributes());
        return ret;
    }

    public void put(String atrName, Object val) {
        if (atrName.equals(LABEL)) {
            g.setLabel((String) val);
        } else if (atrName.equals(ZOOM)) {
            g.setZoom((ArrayX<String>) val);
        } else if (atrName.equals(FONT)) {
            g.setFont((Font) val);
        } else if (atrName.equals(DRAW_VERTEX_LABELS)) {
            g.setDrawVertexLabels((Boolean) val);
        } else if (atrName.equals(IS_EDGES_CURVED)) {
            g.setIsEdgesCurved((Boolean) val);
        } else if (atrName.equals(BACKGROUND_IMAGE)) {
            g.setBackgroundImageFile((File) val);
        } else if (atrName.equals(Allow_Loops)) {
            g.setAllowLoops((Boolean) val);
        } else if (atrName.equals(DRAW_EDGE_LABELS)) {
            g.setDrawEdgeLabels((Boolean) val);
        } else {
            g.setUserDefinedAttribute(atrName, val);
        }

    }

    public Object get(String atrName) {
        Object ret = null;
        if (atrName.equals(LABEL)) {
            ret = g.getLabel();
        } else if (atrName.equals(DIRECTED)) {
            ret = g.isDirected();
        } else if (atrName.equals(ZOOM)) {
            ret = g.getZoom();
        } else if (atrName.equals(FONT)) {
            ret = g.getFont();
        } else if (atrName.equals(DRAW_VERTEX_LABELS)) {
            ret = g.isDrawVertexLabels();
        } else if (atrName.equals(IS_EDGES_CURVED)) {
            ret = g.isEdgesCurved();
        } else if (atrName.equals(BACKGROUND_IMAGE)) {
            ret = g.getBackgroundImageFile();
        } else if (atrName.equals(DRAW_EDGE_LABELS)) {
            ret = g.isDrawEdgeLabels();
        } else if (atrName.equals(Allow_Loops)) {
            ret = g.isAllowLoops();
        } else {
            ret = g.getUserDefinedAttribute(atrName);
        }
        return ret;
    }

    public GraphAttrSet(GraphModel g) {
        this.g = g;
    }
}
