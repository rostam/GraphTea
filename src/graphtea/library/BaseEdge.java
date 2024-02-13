// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

/*
 * BaseEdge.java
 *
 * Created on November 13, 2004, 8:21 PM
 */

package graphtea.library;


/**
 * The base class for all edges. By default each vertex has two integer
 * properties, color and weight.
 * @author Hooman Mohajeri Moghaddam made the default weight = 1
 * @author Omid Aladini
 */

public class BaseEdge<VertexType extends BaseVertex>
        implements Comparable<BaseEdge<VertexType>> {


    protected BaseEdgeProperties prop;

    public final VertexType source;
    public final VertexType target;

    /**
     * Number of times edge iteration is called. This will be set as a temporary flag
     * in order to reduce running time of edge iteration back to O(n^2).
     * Don't touch this please, if you like your iterations work well.
     */
    int edgeIterationIndex = 0;

    public BaseEdge(VertexType source, VertexType target) {
        this.source = source;
        this.target = target;
        this.prop = new BaseEdgeProperties(0, 1, false);
    }

    public BaseEdge(VertexType source, VertexType target, BaseEdgeProperties prop) {
        this.source = source;
        this.target = target;
        this.prop = prop;
    }


    public BaseEdge<VertexType> getCopy(VertexType v1, VertexType v2) {
        return new BaseEdge<VertexType>(v1, v2, prop);
    }

    /**
     * Returns the color of the edge.
     *
     * @return The color associated with the edge.
     */
    public int getColor() {
        return getProp().color;
    }

    /**
     * Sets the color of the edge.
     *
     * @param color Sets colorings as the color of the edge.
     */
    public void setColor(int color) {
        getProp().color = color;
    }

    /**
     * Returns the weight of the edge.
     *
     * @return The weight associated with the edge.
     */
    public int getWeight() {
        return getProp().weight;
    }

    /**
     * Sets the weight of the edge.
     *
     * @param weight Sets w as the color of the edge.
     */
    public void setWeight(int weight) {
        getProp().weight = weight;
    }

    /**
     * Returns the mark of the edge.
     *
     * @return The mark associated with the edge.
     */
    public boolean getMark() {
        return getProp().mark;
    }

    /**
     * Sets the mark of the edge.
     *
     * @param m Sets m as the mark of the edge.
     */
    public void setMark(boolean m) {
        getProp().mark = m;
    }

    /**
     * Sets properties object for this edge; Overwrites the existing.
     *
     * @param prop The property object to set.
     */
    public void setProp(BaseEdgeProperties prop) {
        this.prop = prop;
    }

    /**
     * Returns property object for this edge.
     *
     * @return Returns property object for this edge.
     */
    public BaseEdgeProperties getProp() {
        return prop;
    }

    @Override
    public String toString() {
        return "Edge:" + source + "->" + target;
    }

    /**
     * Compares two edges according to their wrights.
     *
     * @param o Edge to compare.
     * @return 0 if two objects are equal, -1 if this object is less than and
     *         1 if this object is greater than the supplied object.
     */
    public int compareTo(BaseEdge<VertexType> o) {
        return Integer.compare(prop.weight, o.prop.weight);

    }
}
