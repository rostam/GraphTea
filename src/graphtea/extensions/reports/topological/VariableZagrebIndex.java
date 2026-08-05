// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.topological;

import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;

/**
 * @author Ali Rostami
 */
public class VariableZagrebIndex extends AbstractStringReport implements Parametrizable {

    @Parameter(name = "Alpha", description = "")
    public Double alpha = 1.0;

    @Override
    public String getName() {
        return "All Variable Zagreb Indices";
    }

    @Override
    protected String[] computeLines(ZagrebIndexFunctions zif) {
        return new String[]{
            "First Variable Zagreb Index : " + zif.getFirstVariableZagrebIndex(alpha),
            "Second Variable Zagreb Index : " + zif.getSecondVariableZagrebIndex(alpha)
        };
    }

    @Override
    public String getCategory() {
        return "Topological Indices-Zagreb Indices";
    }
}
