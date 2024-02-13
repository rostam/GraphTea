// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.platform.attribute;

import java.util.Collection;

/**
 * An Interface which is an AttributeSet and also it
 * is notifiable<br>
 * as an example see NotifiableAttributeSetImpl
 * <br/>
 * The difference between a NotifiableAttributeSet and a BlackBoard is that, NotifiableAttributeSet is designed
 * for a small set of attributes, so for example getAttributeListeners() will return all listeners of all attributes,
 * but BlackBoard is for a bigger set of attributes, and there you can give listeners for just one key at a time.
 *
 * @see NotifiableAttributeSetImpl
 *
 * @author Azin Azadi
 */
public interface NotifiableAttributeSet extends AttributeSet {

    /**
     * Add a listener to changes of an AttributeSet. <br>
     * It's better to use a <code>List</code> because of
     * <code>getAttributeListeners()</code> method.
     *
     * @param attributeListener the listener!
     */
    void addAttributeListener(AttributeListener attributeListener);

    /**
     * @return List of listeners
     */
    Collection<AttributeListener> getAttributeListeners();

    /**
     * Remove a listener from list of listeners.
     *
     * @param attributeListener The attribute listener
     */
    void removeAttributeListener(AttributeListener attributeListener);
}
