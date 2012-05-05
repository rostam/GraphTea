// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.library.genericcloners;

import graphtea.library.BaseVertex;

/**
 * @author Omid Aladini
 */
public interface VertexConverter<ImportVertexType extends BaseVertex,
        ExportVertexType extends BaseVertex> {
    public ExportVertexType convert(ImportVertexType v);

}
