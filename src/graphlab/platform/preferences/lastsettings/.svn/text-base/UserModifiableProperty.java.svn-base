// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.platform.preferences.lastsettings;

/**
 * @author Rouzbeh Ebrahimi
 */

import static java.lang.annotation.ElementType.FIELD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({FIELD})
public @interface UserModifiableProperty {
    String displayName() default "";

    boolean obeysAncestorCategory() default true;

    String category() default "";
}
