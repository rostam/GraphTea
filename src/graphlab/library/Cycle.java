// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphlab.library;

import graphlab.library.exceptions.InvalidVertexException;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * I think this is enough for a path;
 *
 * @author Omid
 * @param <VertexType>
 */
public class Cycle<VertexType extends BaseVertex> implements Iterable {
    private ArrayList<VertexType> vertices;

    public void insert(VertexType vertex) {
        if (vertices.contains(vertex))
            throw new InvalidVertexException("Duplicate vertex in cycle");
        vertices.add(vertex);
    }

    public void insert(VertexType vertex, int position) {
        if (vertices.contains(vertex))
            throw new InvalidVertexException("Duplicate vertex in cycle");
        vertices.add(position, vertex);
    }

    public Iterator iterator() {
        return vertices.iterator();
    }

    public VertexType get(int i) {
        return vertices.get(i);
    }

    public int size() {
        return vertices.size();
    }

}