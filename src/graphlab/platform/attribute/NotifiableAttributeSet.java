// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.platform.attribute;

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
     * @param attrNames         the name of attributes to addListener for change
     */
    public void addAttributeListener(AttributeListener attributeListener);

    /**
     * @param attrNames
     * @return List of listeners
     */
    public Collection<AttributeListener> getAttributeListeners();

    /**
     * Remove a listener from list of listeners.
     *
     * @param attributeListener
     */
    public void removeAttributeListener(AttributeListener attributeListener);
}
