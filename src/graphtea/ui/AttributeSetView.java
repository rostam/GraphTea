// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui;

import graphtea.platform.attribute.AttributeSet;
import graphtea.platform.attribute.AttributeSetImpl;
import graphtea.ui.components.gpropertyeditor.GBasicCellEditor;
import graphtea.ui.components.gpropertyeditor.GBasicCellRenderer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

/**
 * the eXtended attribute which is a kind of view for notifiableAttributeSet, it then connects to the notifiableAttributeSet
 * which is a kind of model for it.
 *
 * @author Azin Azadi
 */
public class AttributeSetView {
    HashMap<String, Boolean> editable = new HashMap<String, Boolean>();
    HashMap<String, GBasicCellEditor> editors = new HashMap<String, GBasicCellEditor>();
    final int dlen = 8;
    HashMap[] descriptions = new HashMap[dlen];
    private AttributeSet a = new AttributeSetImpl();

    static int dname = 2, desc = 3, cat = 4, index = 5, visible = 6, valid = 7, EDITOR = 8;
    private HashMap<String, GBasicCellRenderer> renderers = new HashMap<String, GBasicCellRenderer>();

    public AttributeSetView() {
        for (int i = 0; i < dlen; i++) {
            descriptions[i] = new HashMap();
        }
    }

    public boolean isEditable(String name) {
        if (editable.get(name) != null)
            return editable.get(name);
        else
            return true;
    }

    public void setEditable(String name, boolean isEditable) {
//        if (contains(name))
        editable.put(name, isEditable);
    }

    private void set(int i, String name, String value) {
        descriptions[i].put(name, value);
    }

    private String get(int i, String name) {
        if (!descriptions[i].containsKey(name))
            return "";
        return (String) descriptions[i].get(name);
    }


    /**
     * display names not implemented yet.
     */
    public void setDisplayName(String name, String displayName) {
        //todo: impl.
        set(dname, name, displayName);
    }

    public String getDisplayName(String name) {
        String s = get(dname, name);
        if (s.equals(""))
            return name;
        return s;
    }

    public void setDescription(String name, String description) {
        set(desc, name, description);
    }

    public String getDescription(String name) {
        return get(desc, name);
    }

    //todo: implement category @ gpropertyeditor
    public void setCategory(String name, String category) {
        set(cat, name, category);
    }

    public String getCategory(String name) {
        return get(cat, name);
    }

    public void setEditor(String name, GBasicCellEditor editor) {
        editors.put(name, editor);
    }

    public GBasicCellEditor getEditor(String name) {
        return editors.get(name);
    }

    public void setrenderer(String name, GBasicCellRenderer renderer) {
        renderers.put(name, renderer);
    }

    public GBasicCellRenderer getrenderer(String name) {
        return renderers.get(name);
    }

    public void setVisible(String name, Boolean isVisible) {
        set(visible, name, isVisible.toString());
    }

    /**
     * returns true if the name didn't setted to be invisible.
     * visibility option is most usable in property editor
     */
    public boolean isVisible(String name) {
        return !get(visible, name).equalsIgnoreCase("false");
    }

    public void setValid(String name, Boolean isvalid) {
        set(valid, name, isvalid.toString());
    }

    /**
     * returns true if the name didn't setted to be invalid.
     * valid option is most usable in property editor
     * e.g. it is used when the property editor reffers to more than one item
     * that all of them have the name property but with DIFFERENT values, at
     * this time the name value should be invalid
     */
    public boolean isvalid(String name) {
        return !get(valid, name).equalsIgnoreCase("false");
    }


    HashMap<String, Integer> indices = new HashMap<String, Integer>();

    /**
     * set the index of the attribute, the lower index attributes are put before, in the getNames()
     */
    public void setIndex(String name, int atrIndex) {
        //store indices in a seperate Map from string to int.
        indices.put(name, atrIndex);
//        set(index, name, index + "");
    }

    /**
     * returns the index of the given name, if the index didn't set before, it returns Integer.MAX_VALUE
     */
    public int getIndex(String name) {
        if (!indices.containsKey(name)) {
            return Integer.MAX_VALUE;
        }
        return indices.get(name);
    }

    public String getNameAt(int i) {
        //todo: cache the names array for speed & performance
        return getNames()[i];
    }

    /**
     * gets all visible names sorted by indices and then alphabetically!
     */
    public String[] getNames() {
        Set<String> s = a.getAttrs().keySet();
//        int n = s.size()- descriptions[visible].size();
        String[] _ss = new String[s.size()];
        int i = 0;
        for (String _ : s) {
            if (_ != null && isVisible(_))
                _ss[i++] = _;
        }
        int n = i;
        String[] ss = new String[n];
        for (i = 0; i < n; i++) {
            ss[i] = _ss[i];
        }
        String t;
        Arrays.sort(ss);
        //sort ss by indices and then alphabetically
        for (i = 0; i < n; i++)
            for (int j = 0; j < i; j++)
//            if index of j is larger, or if indices are equals but they are not in dictionary order
                if (getIndex(ss[i]) < getIndex(ss[j])) {
//                if (ss[i].compareTo(ss[j])!=1) {
                    t = ss[i];
                    ss[i] = ss[j];
                    ss[j] = t;
                }
        return ss;
    }

    /**
     * connects this to a
     *
     * @param a
     */
    public void setAttribute(AttributeSet a) {
        this.a = a;
    }

    public AttributeSet getAttribute() {
        return a;
    }
}