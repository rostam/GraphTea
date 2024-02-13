package graphtea.extensions.reports.boundcheck.forall.iterators;

import graphtea.graph.graph.GraphModel;

import java.util.Iterator;

/**
 * Created by rostam on 01.11.15.
 *
 */
public abstract  class GraphModelIterator implements Iterator<GraphModel> {
    public abstract int size();
    public abstract int getCount();
    public abstract String getG6();
}
