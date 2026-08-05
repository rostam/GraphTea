// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.platform.extension;

/**
 * The father of all extensions
 * @see graphtea.platform.extension.Extension
 * @see graphtea.ui.extension.UIActionExtension
 *
 * If an extension only implements this interface it will be loaded with its constructor
 * getting the blackboard or with its default constructor.
 *
 * This extension is useful for doing initialization on application loading, or background
 * functionalities without any UI components.
 *
 * @author Azin Azadi
 */
public interface BasicExtension {
}
