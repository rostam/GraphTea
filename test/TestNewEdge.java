// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

import graphtea.library.BaseEdge;
import graphtea.library.BaseVertex;

/**
 * @author Omid
 */
public class TestNewEdge extends BaseEdge<BaseVertex> {

    public TestNewEdge(BaseVertex source, BaseVertex target) {
        super(source, target);

    }

    public TestNewEdge copy() {
        System.out.println("here3");
        return new TestNewEdge(source, target);
    }

    public static void main(String[] args) {
        //TestNewEdge t = new TestNewEdge(null,null);

        //t.copy();
        //String i = "hello";
    }
}
