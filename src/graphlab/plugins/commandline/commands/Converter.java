// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.commandline.commands;

import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.VertexModel;
import graphlab.library.genericcloners.EdgeVertexConverter;

/**
 * @author Mohammad Ali Rostami
 */
public class Converter
        implements EdgeVertexConverter<VertexModel, VertexModel, EdgeModel, EdgeModel> {
    public EdgeModel convert(EdgeModel e, VertexModel newSource, VertexModel newTarget) {
        if (e != null)
            return new EdgeModel(e, newSource, newTarget);
        else
            return new EdgeModel(newSource, newTarget);
    }

    public VertexModel convert(VertexModel e) {
        return new VertexModel(e);
    }
}
