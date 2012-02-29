// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

/*
 * GraphConverter.java
 *
 * Created on November 21, 2004, 2:07 AM
 */

package graphlab.library.genericcloners;

import graphlab.library.BaseEdge;
import graphlab.library.BaseGraph;
import graphlab.library.BaseVertex;


/**
 * @author Omid Aladini
 */
public interface GraphConverter
        <ImportVertexType extends BaseVertex,
                ExportVertexType extends BaseVertex,
                ImportEdgeType extends BaseEdge<ImportVertexType>,
                ExportEdgeType extends BaseEdge<ExportVertexType>,
                ImportGraphType extends BaseGraph<ImportVertexType, ImportEdgeType>,
                ExportGraphType extends BaseGraph<ExportVertexType, ExportEdgeType>>

        extends EdgeConverter<ImportVertexType, ExportVertexType, ImportEdgeType, ExportEdgeType>,
        VertexConverter<ImportVertexType, ExportVertexType> {

    public ExportGraphType convert(ImportGraphType g);

}
