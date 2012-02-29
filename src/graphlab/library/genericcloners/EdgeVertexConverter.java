// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphlab.library.genericcloners;

import graphlab.library.BaseEdge;
import graphlab.library.BaseVertex;

/**
 * @author Omid
 */

public interface EdgeVertexConverter
        <ImportVertexType extends BaseVertex,
                ExportVertexType extends BaseVertex,
                ImportEdgeType extends BaseEdge<ImportVertexType>,
                ExportEdgeType extends BaseEdge<ExportVertexType>>
        extends
        VertexConverter<ImportVertexType, ExportVertexType>,
        EdgeConverter<ImportVertexType, ExportVertexType, ImportEdgeType, ExportEdgeType> {

    public ExportEdgeType convert(ImportEdgeType e, ExportVertexType newSource, ExportVertexType newTarget);

    public ExportVertexType convert(ImportVertexType e);
}
