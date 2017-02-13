// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.library;

import graphtea.library.exceptions.InvalidVertexException;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * I think this is enough for a path;
 *
 * @author Omid
 * @param <VertexType>
 */
public class Path<VertexType extends BaseVertex> implements Iterable<VertexType> {
    private ArrayList<BaseVertex> vertices = new ArrayList<>();

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
        return (Iterator<VertexType>) vertices.iterator();
    }

    public VertexType get(int i) {
        return (VertexType) vertices.get(i);
    }

    public int size() {
        return vertices.size();
    }

}
