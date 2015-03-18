// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.platform.extension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * Provides some information about the extension
 *
 * @author azin azadi
 * @see Extension
 */

@Retention(RUNTIME)
@Target({ElementType.TYPE})
public @interface ExtensionExternalData {
    /**
     * a URL which locates a help for extension, the help will
     * provide more information about what extension does and
     * how it can be used, it also can contain some theoreticall
     * aspects of extension, how it is implemented and so on.
     */
    String helpURL();

    /**
     * a URL which locates the source code for the extension, it
     * will be then accessible by the user.
     * <p/>
     * this field will be setted on extension load automatically if it hasn't been setted,
     * on this case it will be locate the file of extension.
     * automatically setting the sourceCodeURL will be done only when the extension source code
     * is not a .class file.
     *
     * @see ExtensionLoader
     */
    String sourceCodeURL();
}
