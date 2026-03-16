// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.extensions.actions;

/**
 * @author Mohammad Ali Rostami
 * @author Azin Azadi
 */
public class VertexSemitotalGraph extends SubdividedGraphBase {
    @Override protected boolean addCurvedOriginalEdge() { return true; }

    @Override
    public String getName() { return "Vertex Semitotal Graph"; }

    @Override
    public String getDescription() { return "Vertex Semitotal Graph"; }
}
