// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.library.genericcloners;

import graphtea.library.BaseEdge;
import graphtea.library.BaseVertex;

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

    ExportEdgeType convert(ImportEdgeType e, ExportVertexType newSource, ExportVertexType newTarget);

    ExportVertexType convert(ImportVertexType e);
}
