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
public class Path<VertexType extends BaseVertex> implements Iterable<VertexType> {
    private ArrayList<BaseVertex> vertices = new ArrayList<BaseVertex>();

    public void insert(VertexType vertex) {
        if (vertices.contains(vertex))
            throw new InvalidVertexException("Duplicate vertex in path");
        vertices.add(vertex);
    }

    public void insert(VertexType vertex, int position) {
        if (vertices.contains(vertex))
            throw new InvalidVertexException("Duplicate vertex in path");
        vertices.add(position, vertex);
    }

    public Iterator<VertexType> iterator() {
        Iterator<VertexType> iterator = (Iterator<VertexType>) vertices.iterator();
        return iterator;
    }

    public VertexType get(int i) {
        return (VertexType) vertices.get(i);
    }

    public int size() {
        return vertices.size();
    }

}
