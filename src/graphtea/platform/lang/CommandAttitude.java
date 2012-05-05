// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.platform.lang;

import java.lang.annotation.ElementType;
import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * The information which will provide a new command in CommandLine (beanshell)
 * @author Mohammad Ali Rostami
 */

@Retention(RUNTIME)
@Target({METHOD, ElementType.TYPE})
public @interface CommandAttitude {
    String name() default "";

    String abbreviation() default "";

    String description() default "";
}
