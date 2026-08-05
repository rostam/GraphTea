// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui;

import graphtea.platform.attribute.AttributeListener;
import graphtea.platform.attribute.AttributeSet;
import graphtea.platform.attribute.NotifiableAttributeSet;
import graphtea.platform.attribute.NotifiableAttributeSetImpl;

import java.util.Collection;
import java.util.Map;

/**
 * this is a portable NotifiableAttributeSetImpl + View
 * this means that the model of view can be changed
 *
 * @author azin azadi
 */
public class PortableNotifiableAttributeSetImpl implements NotifiableAttributeSet, NotifiableAttributeSetView {
    private AttributeSetView view;
    private NotifiableAttributeSet model;

    public PortableNotifiableAttributeSetImpl() {
        view = new AttributeSetView();
        model = new NotifiableAttributeSetImpl();
        view.setAttribute(this.getAttributes());
    }

    public void setView(AttributeSetView view) {
        this.view = view;
        view.setAttribute(model);
    }

    public AttributeSetView getView() {
        return view;
    }

    public void setModel(NotifiableAttributeSet aModel) {
        model = aModel;
        view.setAttribute(model);
    }

    public AttributeSet getAttributes() {
        return model;
    }

    public void addAttributeListener(AttributeListener attributeListener) {
        model.addAttributeListener(attributeListener);
    }

    public Collection<AttributeListener> getAttributeListeners() {
        return model.getAttributeListeners();
    }

    public void removeAttributeListener(AttributeListener attributeListener) {
        model.removeAttributeListener(attributeListener);
    }

    public NotifiableAttributeSet getModel() {
        return model;
    }

    public Map<String, Object> getAttrs() {
        return model.getAttrs();
    }

    public void put(String name, Object value) {
        model.put(name, value);
    }

    public Object get(String name) {
        return model.get(name);
    }
}
