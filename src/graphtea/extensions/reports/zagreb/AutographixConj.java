// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.zagreb;

import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;

/**
 * @author Ali Rostami

 */

@CommandAttitude(name = "conj", abbreviation = "_conj")
public class AutographixConj implements GraphReportExtension {
    public String getName() {
        return "AutographiX  Conjecture";
    }

    public Double alpha = 1.0;

    public String getDescription() {
        return "AutographiX  conjecture";
    }

    public Object calculate(GraphData gd) {
        ArrayList<String> out = new ArrayList<String>();
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(gd);

        double first_zagreb = zif.getFirstZagreb(alpha);
        double second_zagreb = zif.getSecondZagreb(alpha);

        double conj = first_zagreb/gd.getGraph().numOfVertices() -
                second_zagreb/gd.getGraph().getEdgesCount();
        if(conj !=0)
            conj = -conj;
        out.add("The result of formula (M_2^1/m-M_1^2/n = "+ conj
                + ") should be great than zero.");
        return out;
    }

    @Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "Topological Indices";
	}
}
