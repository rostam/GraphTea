// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

/*
 * BaseVertex.java
 *
 * Created on November 13, 2004, 8:31 PM
 */

package graphtea.library;

import java.util.ArrayList;

/**
 * The base class for all vertices. By default each vertex has a integer property
 * named color.
 *
 * @author Omid Aladini
 */

public class BaseVertex {
    protected BaseVertexProperties prop;
    private ArrayList<Integer> subgraphIds = null;
    private Integer id;

    public BaseVertex(BaseVertexProperties prop) {
        this.prop = prop;
    }

    public BaseVertex() {
        prop = new BaseVertexProperties(0, false);
    }

    @Override
    public String toString() {
        return "v" + id;
    }

    public BaseVertex getCopy() {
        return new BaseVertex(this.prop);
    }

    /**
     * Returns the color of the vertex.
     *
     * @return The color associated with the vertex.
     */
    public int getColor() {
        return prop.color;
    }

    /**
     * Sets the color of the vertex.
     *
     * @param color Sets colorings as the color of the vertex.
     */
    public void setColor(int color) {
        prop.color = color;
    }


    /**
     * Returns true if the vertex is already marked.
     *
     * @return Returns true if the vertex is already marked.
     */
    public boolean getMark() {
        return prop.mark;
    }

    /**
     * Flag whether it is marked.
     *
     * @param mark whether the vertex is marked.
     */
    public void setMark(boolean mark) {
        prop.mark = mark;
    }

    /**
     * Returns the index of the vertex in the graph.
     *
     * @return Returns the index of the vertex in the graph.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the index of the vertex in the graph that belongs to a specified subgraph.
     *
     * @return Returns the index of the vertex in the graph that belongs to a specified subgraph..
     */
    int getSubgraphId(int subgraphIndex) {
        return subgraphIds.get(subgraphIndex - 1);
    }

    void informNewSubgraph() {
        if (subgraphIds == null)
            subgraphIds = new ArrayList<>();
        subgraphIds.add(0);
    }

    /**
     * Sets the index of the vertex in the graph.
     *
     * @param id the index of the vertex to be set.
     */
    void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the index of the vertex in the graph that belongs to a specified subgraph.
     *
     * @return Sets the index of the vertex in the graph that belongs to a specified subgraph..
     */
    int setSubgraphId(int subgraphIndex, int id) {
        return subgraphIds.set(subgraphIndex - 1, id);
    }

    /**
     * Sets properties object for this vertex; Overwrites the existing.
     *
     * @param prop The property object to set.
     */
    public void setProp(BaseVertexProperties prop) {
        this.prop = prop;
    }

    /**
     * Returns property object for this vertex.
     *
     * @return Returns property object for this vertex.
     */
    public BaseVertexProperties getProp() {
        return prop;
    }

}
