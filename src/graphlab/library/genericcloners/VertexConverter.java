// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphlab.library.genericcloners;

import graphlab.library.BaseVertex;

/**
 * @author Omid Aladini
 */
public interface VertexConverter<ImportVertexType extends BaseVertex,
        ExportVertexType extends BaseVertex> {
    public ExportVertexType convert(ImportVertexType v);

}
