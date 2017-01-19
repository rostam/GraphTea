// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.core.actions;

import graphtea.graph.atributeset.*;
import graphtea.graph.event.EdgeEvent;
import graphtea.graph.event.GraphSelectData;
import graphtea.graph.event.VertexEvent;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.graph.old.GStroke;
import graphtea.platform.attribute.*;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.main.select.Select;
import graphtea.plugins.main.select.SelectPluginMethods;
import graphtea.plugins.main.ui.GStrokeEditor;
import graphtea.ui.AttributeSetView;
import graphtea.ui.PortableNotifiableAttributeSetImpl;
import graphtea.ui.UIUtils;
import graphtea.ui.components.gpropertyeditor.GPropertyEditor;

import java.util.HashSet;
import java.util.Set;

import static graphtea.graph.old.GStroke.*;

/**
 * the left side property editor of graphtea gui
 *
 * @author Azin Azadi
 */
public class GraphPropertyEditor extends AbstractAction implements AttributeListener {
    public GPropertyEditor getPropertyEditor() {
        return prosheet;
    }

    private GPropertyEditor prosheet;

    public NotifiableAttributeSet NotifiableAttributeSet;

    public AttributeSetView viewer;

    Vertex lastVertex;

    Edge lastEdge;

    private TimeLimitedNotifiableAttrSet target;

    AttributeSetView edgeView = new AttributeSetView();

    AttributeSetView vertexView = new AttributeSetView();

    AttributeSetView graphView = new AttributeSetView();

    AttributeSetView selectView = new AttributeSetView();

    HashSet<String> hiddenVertexAttributes = new HashSet<>();

    HashSet<String> hiddenEdgeAttributes = new HashSet<>();

    PortableNotifiableAttributeSetImpl xx = new PortableNotifiableAttributeSetImpl();

    TimeLimitedNotifiableAttrSet selectionAttributes = new TimeLimitedNotifiableAttrSet(new AttributeSetImpl());

    SelectPluginMethods spm;

    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public GraphPropertyEditor(BlackBoard bb) {
        super(bb);
        spm = new SelectPluginMethods(blackboard);
        prosheet = new GPropertyEditor();
        prosheet.setSize(150, 100);
        UIUtils.setComponent(blackboard, "property editor", prosheet);
        listen4Event(EdgeEvent.EVENT_KEY);
        listen4Event(VertexEvent.EVENT_KEY);
        listen4Event(Select.EVENT_KEY);
        listen4Event(GraphSelectData.EVENT_KEY);

//        graphView.setVisible(GraphAttrSet.CENTERX, false);
//        graphView.setVisible(GraphAttrSet.CENTERY, false);
//        graphView.setVisible(GraphAttrSet.DIRECTED, false);
//        graphView.setVisible(GraphAttrSet.ZOOM, false);
        graphView.setIndex(GraphAttrSet.LABEL, 0);
        graphView.setIndex(GraphAttrSet.DIRECTED, 1);
        graphView.setIndex(GraphAttrSet.DRAW_VERTEX_LABELS, 5);
        graphView.setIndex(GraphAttrSet.DRAW_EDGE_LABELS, 6);
        graphView.setEditable(GraphAttrSet.DIRECTED, false);
        graphView.setEditable(GraphAttrSet.LABEL, true);

//        graphView.setIndex(EdgeAttrSet.SHOW_EDGE_LABELS, 3);

//Storing Id in vertex is deprecated
//        vertexView.setVisible(Vertex.ID, false);
//        vertexView.setVisible(Vertex.XPOS, false);
//        vertexView.setVisible(Vertex.YPOS, false);
        vertexView.setIndex(VertexAttrSet.LABEL, 0);
        vertexView.setIndex(VertexAttrSet.COLOR, 2);
        vertexView.setIndex(VertexAttrSet.SHAPE, 4);
        vertexView.setIndex(VertexAttrSet.BORDER, 6);
        vertexView.setVisible(VertexAttrSet.SELECTED, false);
        vertexView.setEditor(VertexAttrSet.BORDER, new GStrokeEditor() {
            public Object[] getValues() {
                return new GStroke[]{
                        GStroke.empty,
                        simple,
                        solid,
                        strong,
                        dashed,
                        dotted,
                        dashed_dotted,
                        dashed_dotted_dotted,
                        dashed_dashed_dotted
                };
            }
        });
        vertexView.setEditor(VertexAttrSet.COLOR, new GMergedColorEditor(true));
        vertexView.setrenderer(VertexAttrSet.COLOR, new GMergedColorRenderer(true));

//        edgeView.setVisible(EdgeAttrSet.DIRECTED, false);
//        edgeView.setVisible(EdgeAttrSet.LABEL, false);
        edgeView.setEditable(EdgeAttrSet.SOURCE, false);
        edgeView.setEditable(EdgeAttrSet.TARGET, false);
        edgeView.setIndex(EdgeAttrSet.LABEL, 0);
        edgeView.setIndex(EdgeAttrSet.WEIGHT, 1);
        edgeView.setIndex(EdgeAttrSet.ARROW, 4);
        edgeView.setEditor(EdgeAttrSet.COLOR, new GMergedColorEditor(false));
        edgeView.setrenderer(EdgeAttrSet.COLOR, new GMergedColorRenderer(false));
//        updateSelectView();

    }


    private Vertex lastVertex() {
        VertexEvent x = blackboard.getData(VertexEvent.EVENT_KEY);
        return (x == null ? null : x.v);
    }

    private Edge lastEdge() {
        EdgeEvent x = blackboard.getData(EdgeEvent.EVENT_KEY);
        return (x == null ? null : x.e);
    }

    private GraphModel lastGraph() {
        GraphSelectData x = blackboard.getData(GraphSelectData.EVENT_KEY);
        return (x == null ? null : x.g);
    }

    public void performAction(String eventName, Object value) {
        prosheet = (GPropertyEditor) UIUtils.getComponent(blackboard,
                "property editor");
        if (getTarget() != null)
            if (eventName == Select.EVENT_KEY) {
                if (!spm.isSelectionEmpty()) {
                    updatePropertyEditor_selected();
//                    viewer = selectView;
                } else {
                    setTarget(new GraphNotifiableAttrSet(lastGraph()));
                    viewer = graphView;
                }
            }
//        if (eventName ==VertexEvent.name)) {
//            VertexEvent ve = blackboard.get(VertexEvent.name);
//            if (ve.eventType == VertexEvent.PRESSED) {
//                if (spm.isSelectionEmpty()) {
//                    setTarget(new VertexNotifiableAttrSet(lastVertex()));
//                    viewer = vertexView;
//                }
//            }
//        }
//        if (eventName ==EdgeEvent.name)) {
//            EdgeEvent ve = blackboard.get(EdgeEvent.name);
//            if (ve.eventType == EdgeEvent.PRESSED) {
//                if (spm.isSelectionEmpty()) {
//                    setTarget(new EdgeNotifiableAttrSet(lastEdge()));
//                    viewer = edgeView;
//                }
//            }
//        }
        if (eventName == GraphSelectData.EVENT_KEY) {
            setTarget(new GraphNotifiableAttrSet(lastGraph()));
            viewer = graphView;
        }
        updateTarget();
    }

    private void updateTarget() {
        if (prosheet != null && getTarget() != null) {
            xx.setView(viewer);
            xx.setModel(getTarget());
            prosheet.connect(xx);
        }
        if(getTarget() != null) {
            getTarget().addAttributeListener(new AttributeListener() {
                @Override
                public void attributeUpdated(String name, Object oldVal, Object newVal) {
                    blackboard.setData("undo point", lastGraph());
                }
            });
        }
    }

    public void attributeUpdated(String name, Object oldVal, Object newVal) {
        //occurs whenever one attribute in selection attribute changed by user
        //note that the vertex and edge attributes are updates directly, not from here
        if (!iChangedTheAttribute) {
            String key = name.substring(1);     //the original name of attribute
            if (name.startsWith("v")) { //it was a vertex attribute
                for (Vertex v : spm.getSelectedVertices()) {
                    VertexAttrSet _v = new VertexAttrSet(v);
                    _v.put(key, newVal);
                }
            }
            if (name.startsWith("e")) { //it was an edge attribute
                for (Edge e : spm.getSelectedEdges()) {
                    EdgeAttrSet _e = new EdgeAttrSet(e);
                    _e.put(key, newVal);

                }
            }
        }
    }

    boolean iChangedTheAttribute = false;

    private void updatePropertyEditor_selected() {
        HashSet<Vertex> selectedVertices = spm.getSelectedVertices();
        HashSet<Edge> selectedEdges = spm.getSelectedEdges();

        int numOfSelectedVertices = selectedVertices.size();
        int numOfSelectedEdges = selectedEdges.size();

        //if there was only 1 selected vertex(edge) show the classic editor for it
        if (numOfSelectedVertices + numOfSelectedEdges == 1) {
            if (numOfSelectedVertices == 1) {
                setTarget(new VertexNotifiableAttrSet(selectedVertices.iterator().next()));
                viewer = vertexView;
            } else {
                setTarget(new EdgeNotifiableAttrSet(selectedEdges.iterator().next()));
                viewer = edgeView;
            }
        } else {
            viewer = selectView;

            iChangedTheAttribute = true;
            TimeLimitedNotifiableAttrSet retAtrs = new TimeLimitedNotifiableAttrSet(new AttributeSetImpl());
            retAtrs.removeAttributeListener(this);

            //adding each Vertex/Edge attribute to selectionAttributes
            int i = 0;
            retAtrs.put("Vertices", numOfSelectedVertices);
            selectView.setEditable("Vertices", false);
            selectView.setIndex("Vertices", i++);
            for (Vertex v : selectedVertices) {
                if (i < 500) {
                    AttributeSet vAtrs = new VertexAttrSet(v);
                    vertexView.setAttribute(vAtrs);
                    for (String atrName : vertexView.getNames()) {
                        String key = vertexAtrName(atrName);
                        insertAttributeToSelectionAtrs(retAtrs, key, vAtrs, atrName);
                        selectView.setDisplayName(key, atrName);
                        selectView.setIndex(key, i++);
                    }
                }
            }

            retAtrs.put("   ", "");
            selectView.setEditable("   ", false);
            retAtrs.put("Edges", numOfSelectedEdges);
            selectView.setIndex("   ", i++);
            selectView.setIndex("Edges", i++);
            selectView.setEditable("Edges", false);
            for (Edge e : selectedEdges) {
                if (i < 1000) {
                    AttributeSet eAtrs = new EdgeAttrSet(e);
                    for (String atrName : ((Set<String>)eAtrs.getAttrs().keySet())) {
                        String key = edgeAtrName(atrName);
                        insertAttributeToSelectionAtrs(retAtrs, key, eAtrs, atrName);
                        selectView.setDisplayName(key, atrName);
                        selectView.setIndex(key, i++);
                    }
                }
            }
            selectView.setVisible("v" + VertexAttrSet.LOCATION, false);
            selectView.setVisible("v" + VertexAttrSet.SELECTED, false);
            selectView.setEditor("v" + VertexAttrSet.BORDER, new GStrokeEditor() {
                public Object[] getValues() {
                    return new GStroke[]{
                            GStroke.empty,
                            simple,
                            solid,
                            strong,
                            dashed,
                            dotted,
                            dashed_dotted,
                            dashed_dotted_dotted,
                            dashed_dashed_dotted
                    };
                }
            });
            selectView.setEditor("v" + VertexAttrSet.COLOR, new GMergedColorEditor());
            selectView.setrenderer("v" + VertexAttrSet.COLOR, new GMergedColorRenderer());

            selectView.setEditor("e" + VertexAttrSet.COLOR, new GMergedColorEditor());
            selectView.setrenderer("e" + VertexAttrSet.COLOR, new GMergedColorRenderer());

            viewer = selectView;
            retAtrs.addAttributeListener(this);
//            updateSelectView();
            iChangedTheAttribute = false;

            setTarget(retAtrs);
        }

    }

    /**
     * adds the given atr from source to dest, and if there was some values
     * on the place before, invalidate the place
     *
     * @param dest
     * @param key     reffers to the atr name in dest
     * @param source
     * @param atrName reffers to the atr name in source that should be inserted to dest
     */
    private void insertAttributeToSelectionAtrs(AttributeSet dest, String key, AttributeSet source, String atrName) {
        if (dest.getAttrs().containsKey(key)) {
            Object oldVal = dest.get(key);
            if (oldVal == null)
                return;
            Object newVal = source.get(atrName);
            if (!oldVal.equals(newVal)) {
                selectView.setValid(key, false);
            }
        } else {
            selectView.setValid(key, true);  //it is valid default (in first time)
        }
        dest.put(key, source.get(atrName));
    }

//    private void updateSelectView() {
//        //selectView is the union of vertexView and edgeView
////        int maxIndex = Integer.MIN_VALUE;
////        selectView.setIndex("Vertices", 0);
//        for (String key : selectionAttributes.getAttrs().keySet()) {
//            String name = key.substring(1);     //the original name of attribute
//            if (key.startsWith("v")) {
//                selectView.setVisible(key, vertexView.isVisible(name));
////                selectView.setDisplayName(key, name);
////                selectView.setCategory(key, vertexAtrName(vertexView.getCategory(name)));
//                selectView.setDescription(key, vertexView.getDescription(name));
//                selectView.setEditable(key, vertexView.isEditable(name));
////                selectView.setIndex(key, vertexView.getIndex(name));
////                maxIndex = Math.max(maxIndex, vertexView.getIndex(name));
//            }
////            selectView.setIndex("Edges", maxIndex + 1);
//            if (key.startsWith("e")) {
//                selectView.setVisible(key, edgeView.isVisible(name));
////                selectView.setDisplayName(key, name);
////                selectView.setCategory(key, vertexAtrName(edgeView.getCategory(name)));
//                selectView.setDescription(key, edgeView.getDescription(name));
//                selectView.setEditable(key, edgeView.isEditable(name));
////                selectView.setIndex(key, maxIndex + edgeView.getIndex(name));
//            }
//        }
//    }

    /**
     * suggests a name for the given vertex attribute to be used in selection attributed
     */
    private String vertexAtrName(String name) {
        return "v" + name;
    }

    /**
     * suggests a name for the given edge attribute to be used in selection attributed
     */
    private String edgeAtrName(String name) {
        return "e" + name;
    }


    private TimeLimitedNotifiableAttrSet getTarget() {
        return target;
    }

    public void track(){}
    
    private void setTarget(TimeLimitedNotifiableAttrSet target) {
        if (this.target != null) {
            this.target.stop();
        }
        this.target = target;
        this.target.stop();
    }
}