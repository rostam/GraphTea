// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.platform.extension;

/**
 * The base interface for creating extensions,
 * extensions are very simple classes that extend capabilities of program in a predefined way, for example
 * one extension can add a new data input format to program.
 * <p/>
 * the extensions can normally be used by putting them on Extensions directory in program directory, they will
 * be loaded on the next run of program.
 *
 * @author azin azadi

 */
public interface Extension extends BasicExtension {
    abstract String getName();

    abstract String getDescription();
}
