// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.topological;


/**
 * @author Ali Rostami
 */
public class RandicIndex extends AbstractStringReport {

    @Override
    public String getName() {
        return "Randic Index";
    }

    @Override
    protected String[] computeLines(ZagrebIndexFunctions zif) {
        return new String[]{"Randic Index : " + zif.getSecondZagreb(-0.5)};
    }

    @Override
    public String getCategory() {
        return "Topological Indices-Degree Based";
    }
}
