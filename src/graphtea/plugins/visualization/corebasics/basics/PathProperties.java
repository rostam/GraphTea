// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.visualization.corebasics.basics;

/**
 * @author Rouzbeh Ebrahimi
 */
public class PathProperties {
    private String name;
    private int firstColor, secondColor;

    public PathProperties(String name) {
        this.setName(name);
        secondColor = -1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getFirstColor() {
        return firstColor;
    }

    public void setFirstColor(int firstColor) {
        this.firstColor = firstColor;
    }

    public int getSecondColor() {
        return secondColor;
    }

    public void setSecondColor(int secondColor) {
        this.secondColor = secondColor;
    }
}
