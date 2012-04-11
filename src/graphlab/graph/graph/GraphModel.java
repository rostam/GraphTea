// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.graph.graph;

import graphlab.graph.event.GraphModelListener;
import graphlab.library.ListGraph;
import graphlab.library.exceptions.InvalidEdgeException;
import graphlab.library.exceptions.InvalidVertexException;
import graphlab.platform.core.exception.ExceptionHandler;
import graphlab.platform.lang.ArrayX;
import graphlab.platform.preferences.lastsettings.StorableOnExit;
import graphlab.platform.preferences.lastsettings.UserModifiableProperty;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;


/**
 * @author Hooman Mohajeri Moghaddam - added image file for background.
 * @author Azin Azadi,roozbeh ebrahimi
 */

public class GraphModel extends ListGraph<VertexModel, EdgeModel> implements StorableOnExit {
	{
		SETTINGS.registerSetting(this, "Graph Drawings");
	}

	private Font font = Font.decode("Arial");
	@UserModifiableProperty(displayName = "Show Edge Labels")
	public static boolean showEdgeWeights = false;
	@UserModifiableProperty(displayName = "Show Vertex Labels")
	public static boolean vertexLabelsEnabled = true;
	private boolean drawVertexLabels = vertexLabelsEnabled;
	private boolean drawEdgeLabels = showEdgeWeights;
	private ArrayX<String> zoom = new ArrayX<String>("100%", "10%", "25%", "50%", "75%", "100%", "150%", "200%", "400%");
	private File backgroundImageFile=new File("");
	private BufferedImage backgroundImage;

	/**
	 * a number which is constructed from zoom, (150% -> 1.5)
	 */
	private double zoomFactor = 1;
    @UserModifiableProperty(displayName = "Allow Loops")
    public static boolean allowLoopsProperty = false;
    private boolean allowLoops = allowLoopsProperty;

    public GraphModel createEmptyGraph() {
		return new GraphModel(isDirected());
	}

	static final int VERTEX_ADDED_GRAPH_CHANGE = 0;
	static final int VERTEX_REMOVED_GRAPH_CHANGE = 1;
	static final int EDGE_ADDED_GRAPH_CHANGE = 2;
	static final int EDGE_REMOVED_GRAPH_CHANGE = 3;
	static final int GRAPH_CLEARED_GRAPH_CHANGE = 4;
	static final int REPAINT_GRAPH_GRAPH_CHANGE = 5;

	boolean showChangesOnView;
	private String label;
	public static final Color[] color = {Color.orange, Color.blue, Color.yellow, Color.green, Color.magenta, Color.red, Color.cyan, Color.pink, Color.lightGray, Color.darkGray};

	/**
	 * It is true if the edges of this graph are curves (not line),
	 * This will be applied to all edges of graph
	 */
	public boolean isEdgesCurved;

	/**
	 * generates an undirected graph
	 */
	public GraphModel() {
		super(true, 20);
		showChangesOnView = false;
		drawVertexLabels = vertexLabelsEnabled;
	}

	public GraphModel(boolean isdirected) {
		super(isdirected, 20);
		showChangesOnView = false;
		drawVertexLabels = vertexLabelsEnabled;
	}


	//________________________   + Userdefined Attributes    _________________________________
	/**
	 * This is a place to put custom attributes in the graph, It will be shown in property editor and editable
	 */
	private HashMap<String, Object> userDefinedAttributes = null;

	/**
	 * these attributed will be added to each graph's userDefinedAttributes on constructing time.
	 */
	private static HashMap<String, Object> globalUserDefinedAttributes = null;


	/**
	 * sets and stores a user defined attribute for the graph. here you can put any attribute you like that are not available
	 * in the standard attributes. your attributes will be editable in property editor part of GUI.
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
	 * sets and stores a global user defined attribute for the graph. this attributes will be added to each graph on
	 * constructing time using setUserDefinedAttribute method.
	 * <p/>
	 * note that this method only affects the afterward created graphs, and current graph will not affected by this method.
	 */
	public static void addGlobalUserDefinedAttribute(String name, Object defaultvalue) {
		if (globalUserDefinedAttributes == null) {
			globalUserDefinedAttributes = new HashMap<String, Object>();
		}
		globalUserDefinedAttributes.put(name, defaultvalue);
	}

	/**
	 * @see GraphModel#addGlobalUserDefinedAttribute
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
	//________________________   - Userdefined Attributes    _________________________________

	/**
	 * determines whether show changes in model to view, for example when an algorithm changes the color of a vertex
	 * in VertexModel(BaseVertex) should a color be assigned in GUI to it or not?
	 *
	 * @param showChangesOnView
	 */
	public void setShowChangesOnView(boolean showChangesOnView) {
		this.showChangesOnView = showChangesOnView;
		fireGraphChange(REPAINT_GRAPH_GRAPH_CHANGE, null, null);
	}

	public boolean isShowChangesOnView() {
		return showChangesOnView;
	}

	/**
	 * same to insertVertex
	 */
	public void insertVertex(VertexModel newVertex) {
		super.insertVertex(newVertex);
		fireGraphChange(VERTEX_ADDED_GRAPH_CHANGE, newVertex, null);
	}

	public void insertVertices(Collection<VertexModel> vertices) {
		for (VertexModel v : vertices) {
			insertVertex(v);
		}
	}

	public void removeAllEdges(VertexModel source, VertexModel target) throws InvalidVertexException {
		super.removeAllEdges(source, target);
		fireGraphChange(REPAINT_GRAPH_GRAPH_CHANGE, null, null);
	}

	public void removeEdge(EdgeModel edge) throws InvalidEdgeException {
		super.removeEdge(edge);
		fireGraphChange(EDGE_REMOVED_GRAPH_CHANGE, null, edge);
	}

	public void removeVertex(VertexModel v) throws InvalidVertexException {
		Iterator<EdgeModel> it = edgeIterator(v);
		while (it.hasNext()) {
			removeEdge(it.next());

		}
		super.removeVertex(v);
		fireGraphChange(VERTEX_REMOVED_GRAPH_CHANGE, v, null);
	}

	public void clear() {
		super.clear();
		fireGraphChange(GRAPH_CLEARED_GRAPH_CHANGE, null, null);
	}

	public EdgeModel getEdge(VertexModel src, VertexModel trg) {
		Object t[] = null;
		try {
			t = super.getEdges(src, trg).toArray();
		}
		catch (Exception e) {
			ExceptionHandler.catchException(e);
		}
		if (t==null || t.length == 0)
			return null;
		else
			return (EdgeModel) t[0];
	}

	/**
	 * adds new edge only if it doesn't exist in graph
	 *
	 * @param newedge
	 */
	public void insertEdge(EdgeModel newedge) {
        if (!isAllowLoops() && newedge.source == newedge.target)
            return;
		try {
			if (!isEdge(newedge.source, newedge.target)) {
				super.insertEdge(newedge);
                fireGraphChange(EDGE_ADDED_GRAPH_CHANGE, null, newedge);
			}
		}
		catch (Exception e) {
			ExceptionHandler.catchException(e);
		}
	}

	public int getEdgesCount() {
		return super.getEdgesCount();
		//        //graph fact: num of edges = 1/2 * sigma(degrees)
		//        int ret = 0;
		//        for (VertexModel v : this) {
		//            ret += getInDegree(v);
		//        }
		//        return (int) (ret / (isDirected() ? 1 : 2));
	}

	//                         Listener

	HashSet<GraphModelListener> glisteners = new HashSet<GraphModelListener>();

	public void addGraphListener(GraphModelListener listener) {
		glisteners.add(listener);
	}

	public void removeGraphListener(GraphModelListener listener) {
		glisteners.remove(listener);
	}

	void fireGraphChange(int change, VertexModel v, EdgeModel e) {
		for (GraphModelListener l : glisteners) {
			switch (change) {
			case VERTEX_ADDED_GRAPH_CHANGE:
				l.vertexAdded(v);
				break;
			case VERTEX_REMOVED_GRAPH_CHANGE:
				l.vertexRemoved(v);
				break;
			case EDGE_ADDED_GRAPH_CHANGE:
				l.edgeAdded(e);
				break;
			case EDGE_REMOVED_GRAPH_CHANGE:
				l.edgeRemoved(e);
				break;
			case GRAPH_CLEARED_GRAPH_CHANGE:
				l.graphCleared();
				break;
			case REPAINT_GRAPH_GRAPH_CHANGE:
				l.repaintGraph();
				break;
			}
		}
	}
	// -            GraphModelListener

	/**
	 * @return the smallest rectangle that fits arround graph without considering the ZOOM
	 */
	public Rectangle2D.Double getAbsBounds() {
		Rectangle2D.Double ret = new Rectangle2D.Double();
		boolean first = true;
		for (VertexModel v : this) {
			GraphPoint location = v.getLocation();
			Point center = v.getCenter();
			GraphPoint shapeSize = v.getSize();
			Rectangle2D.Double p = new Rectangle2D.Double(location.x * zoomFactor - center.x, location.y * zoomFactor - center.y, shapeSize.x, shapeSize.y);
			if (first) {
				ret = new Rectangle2D.Double(p.x, p.y, 0, 0);
				first = false;
			}
			ret.add(p);
		}
		return ret;
	}

	/**
	 * @return the smallest rectangle that fits arround graph with considering zoom
	 */
	public Rectangle2D.Double getZoomedBounds() {
		Rectangle2D.Double ret = new Rectangle2D.Double();
		boolean first = true;
		for (VertexModel v : this) {
			GraphPoint location = v.getLocation();
			Point center = v.getCenter();
			GraphPoint shapeSize = v.getSize();
			Rectangle2D.Double p = new Rectangle2D.Double(location.x * zoomFactor - center.x, location.y * zoomFactor - center.y, shapeSize.x, shapeSize.y);

			if (first) {
				ret = p;
				first = false;
			}
			//            p.x = p.x;
			//            p.y = p.y;
			ret.add(p);
			//            System.out.println(v.getId() + "" +ret);
		}
		return ret;
	}


	public double getZoomFactor() {
		return zoomFactor;
	}


	public ArrayX<String> getZoom() {
		return zoom;
	}

	public void setZoom(ArrayX<String> zoom) {
		this.zoom = zoom;
		String vl = zoom.getValue();
		zoomFactor = Integer.parseInt(vl.substring(0, vl.length() - 1)) / 100.0;
		fireGraphChange(REPAINT_GRAPH_GRAPH_CHANGE, null, null);
	}

	public void zoomIn(){
		double zF = zoomFactor * 1.5;
		setZoom(zF);
	}

	public void zoomOut(){
		double zF = zoomFactor / 1.5;
		setZoom(zF);
	}

	public void setZoom(double zoomFactor) {
		if (zoomFactor<0.01 || zoomFactor>20)
			return;
		String nz = ((int)(zoomFactor * 100))+ "%";
		this.zoom.addValidValue(nz);
		zoom.setValue(nz);
		setZoom(zoom);
	}

	public void setLabel(String s) {
		this.label = s;
	}

	public String getLabel() {
		return label;
	}

	/**
	 * the standard way to convert simple integers (1,2,3...) to colors
	 */
	private static Color color(int m) {
		return color[m % color.length];
	}

	public void insertEdges(Iterable<EdgeModel> edgeModels) {
		for (EdgeModel _ : edgeModels)
			insertEdge(_);
	}

	/**
	 * adds graph to this graph and place it in the given rectangle
	 *
	 * @param graph
	 * @param _rect
	 */
	public void addSubGraph(GraphModel graph, Rectangle _rect) {
		Rectangle2D.Double bounds1 = graph.getZoomedBounds();
		Rectangle2D.Double rect = new Rectangle2D.Double(_rect.getX(), _rect.getY(), _rect.getWidth(), _rect.getHeight());
		double kx = rect.width / bounds1.getWidth();
		double ky = rect.height / bounds1.getHeight();
		for (VertexModel vm : graph) {
			GraphPoint p = vm.getLocation();
			insertVertex(vm);
			vm.setLocation(new GraphPoint(((p.x - bounds1.x) * kx + rect.x), (int) ((p.y - bounds1.y) * ky + rect.y)));
		}
		Iterator<EdgeModel> eiter = graph.lightEdgeIterator();
		for (; eiter.hasNext();) {
			EdgeModel edge = eiter.next();
			insertEdge(edge);
		}
	}

	/**
	 * in GraphLab all Colors that assign to Vertices/Edges are in values, so they
	 * can not directly shown with distinct colors, this method gived the standard GraphLab
	 * solution to this which assigns unique colors to 1..20 and if i>20, It will regards the i
	 * itself as the color (new Color(i)) regarding the fact that normally in GraphTheory Colors have
	 * small values.
	 *
	 * @return an RGB color which is representing the given integer Color in GraphLab
	 */
	public static Color getColor(Integer i) {
		Color c = null;
		if (i == null)
			i = 0;
		if (i < 20 && i >= 0) {
			int ii = i % 20;
			if (ii < 10)
				c = color(i);
			else
				c = color(i).darker();
		} else {
			c = new Color(i);
		}
		return c;
	}

	public Font getFont() {
		return this.font;
	}

	public void setFont(Font font) {
		this.font = font;
		fireGraphChange(REPAINT_GRAPH_GRAPH_CHANGE, null, null);
	}

	public boolean isDrawEdgeLabels() {
		return drawEdgeLabels;
	}

	public void setDrawEdgeLabels(boolean drawEdgeLabels) {
		this.drawEdgeLabels = drawEdgeLabels;
		fireGraphChange(REPAINT_GRAPH_GRAPH_CHANGE, null, null);
	}

	public boolean isDrawVertexLabels() {
		return drawVertexLabels;
	}

	public void setDrawVertexLabels(boolean drawVertexLabels) {
		this.drawVertexLabels = drawVertexLabels;
		fireGraphChange(REPAINT_GRAPH_GRAPH_CHANGE, null, null);
	}

	/**
	 * @return true if the edges of this graph are curves (not lines),
	 *         This is about all edges of graph
	 */
	public boolean isEdgesCurved() {
		return isEdgesCurved;
	}

	/**
	 * set the edges of this graph to be curves or lines
	 *
	 * @param isCurve
	 */
	public void setIsEdgesCurved(boolean isCurve) {
		this.isEdgesCurved = isCurve;
		fireGraphChange(REPAINT_GRAPH_GRAPH_CHANGE, null, null);
	}

	public void insertVertices(VertexModel[] vertices) {
		for (VertexModel v : vertices) {
			insertVertex(v);
		}
	}

	public void insertEdges(EdgeModel[] edges) {
		for (EdgeModel e:edges)
			insertEdge(e);
	}


	@Override
	public VertexModel[] getVertexArray() {
		VertexModel[] arr = new VertexModel[getVerticesCount()];

		for (VertexModel v : this)
			arr[getId(v)] = v;

		return arr;
	}

	public EdgeModel insertEdge(VertexModel src, VertexModel trg) {
		EdgeModel ret = new EdgeModel(src, trg);
		insertEdge(ret);
		return ret;
	}

	public File getBackgroundImageFile() {
		return backgroundImageFile;
	}

	public void setBackgroundImageFile(File imageFile) {
		backgroundImageFile = imageFile;
		try
		{
			backgroundImage =  ImageIO.read(imageFile);
		}
		catch(Exception e)
		{
			System.out.println("Error loading image file");
		}
		fireGraphChange(REPAINT_GRAPH_GRAPH_CHANGE, null, null);
	}

    public BufferedImage getBackgroundImage()
    {
        return backgroundImage;
    }

    public boolean isAllowLoops(){
        return allowLoops;
    }
    public void setAllowLoops(boolean allowLoops) {
        allowLoops = allowLoops;
    }


}
