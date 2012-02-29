// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.ui;

import graphlab.platform.attribute.AttributeListener;
import graphlab.platform.attribute.AttributeSet;
import graphlab.platform.attribute.NotifiableAttributeSet;
import graphlab.platform.attribute.NotifiableAttributeSetImpl;

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

//    /**
//     * puts an attribute in the model
//     *
//     * @param name
//     * @param atr
//     */
//    public void put(String name, Object atr) {
//        model.getAttributes().put(name, atr);
//    }

    public Object get(String name) {
        return model.get(name);
    }

//    public boolean contains(String name) {
//        return model.contains(name);
//    }

//    /**
//     * gets an attribute from model
//     *
//     * @param name
//     */
//    public <t>t get(String name) {
//        return (t) model.getAttributes().get(name);
//    }
}
