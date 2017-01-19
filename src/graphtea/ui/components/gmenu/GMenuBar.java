// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui.components.gmenu;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * this class is the menu bar of GFrame. it has a difference with JMenuBar which is, if you add 2 menu bars with the
 * same name, they will be put in one bar.
 *
 * @author root
 */
public class GMenuBar extends javax.swing.JMenuBar {
    /**
     *
     */
    private static final long serialVersionUID = 2020939077780205134L;
    private HashMap<String, JMenu> menues = new HashMap<>();

    public GMenuBar() {
//        setBackground(new Color(245,245,255));
//        setBorder(new EmptyBorder(0,0,0,0));
    }

    /**
     * stores the places given for components
     */
    private static HashMap<Component, Integer> componentPlaces = new HashMap<>();

    /**
     * inserts the child to parent with the given places,
     * it doesn't forget the place in the next times that you want to insert another childs
     * with another places
     */
    public static Component insert(JMenu parent, Component child, int place) {
        place = checkPlaceValue(place, child);
        int n = parent.getMenuComponentCount();
        Component[] items = new Component[n + 1];
        Component[] c = parent.getMenuComponents();
        System.arraycopy(c, 0, items, 0, n);
        items[items.length - 1] = child;
        sortPlacedComponents(items);
        parent.removeAll();
        for (Component item : items) {
            parent.add(item, -1);
        }
        return child;
    }

    private static Component insert(JMenuBar parent, Component child, int place) {
        place = checkPlaceValue(place, child);
        int n = parent.getMenuCount();
        Component[] items = new Component[n + 1];
        MenuElement[] c = parent.getSubElements();
        System.arraycopy(c, 0, items, 0, n);
        items[items.length - 1] = child;
        sortPlacedComponents(items);
        parent.removeAll();
        for (Component item : items) {
            parent.add(item, -1);
        }
        return child;
    }

    private static int checkPlaceValue(int place, Component child) {
        if (place == -1) {
            if (componentPlaces.containsKey(child))
                place = componentPlaces.get(child);       //it has given a place before
            else
                place = 1000;
        }
        componentPlaces.put(child, place);
        return place;
    }

    private static void sortPlacedComponents(Component[] items) {
        Arrays.sort(items, (o1, o2) -> {
            if (componentPlaces.containsKey(o1) && componentPlaces.containsKey(o2)) {
                return componentPlaces.get(o1) - componentPlaces.get(o2);
            }
            return 1;
        });
    }

    /**
     * returns a new JMenu with the given label that added to this menu bar
     * if there was created a menu with the same label before, the old menu
     * will be returned and no new menu will be created
     */
    private JMenu requestMenu(String label) {
        JMenu jm = menues.get(label);
        if (jm == null) {
            JMenu ret = new JMenu(label);
            menues.put(label, ret);
            add(ret);
//            validate();
            return ret;
        } else
            return jm;
    }

    /**
     * generates a menu from the given path, the path delimiter is "." , so this means
     * a path in a tree, for example file.generate.star means a 3 layer menu.
     * it doesn't create duplicate menues if you request it twice the same menu
     * it just return the privious one,...
     * <p/>
     * it is suggested to ONLY use this method for creating sub menues
     * <p/>
     * if you have no idea on place just take it -1 , (in this case it normally sets it to 1000, but if it was given some value to it before it takes the older value)
     * <p/>
     * if some place was given for path before, the older value will discarded.
     */
    public JMenu getUniqueMenu(String path, int place) {
        StringTokenizer s = new StringTokenizer(path, ".");
//            Scanner s = new Scanner(mname);
//            s.useDelimiter(".");
        if (!path.contains(".")) {       //if it is only a first level menu
            JMenu ret = requestMenu(path);
            insert(this, ret, place);
            return ret;

        }                               //other wise we have more things to do ...
        JMenu mnu = new JMenu("---");
        if (s.hasMoreTokens()) {
            mnu = requestMenu(s.nextToken());
            boolean dontSearch_YouCantFindIt = false;
            while (s.hasMoreTokens()) {             //each token is a level in menu tree
                String ss = s.nextToken();
                JMenu _mnu = null;
                if (!dontSearch_YouCantFindIt) {   //try to find the next level of the menu
                    for (int i = 0; i < mnu.getMenuComponentCount(); i++) {
                        Component menuComponent = mnu.getMenuComponent(i);
                        if (menuComponent instanceof JMenu) {
                            JMenu menu = (JMenu) menuComponent;
                            if (menu.getText().equals(ss)) {
                                _mnu = menu;
                                break;  //:D it is found
                            }
                        }
                    }
                }
                if (_mnu == null) {     // if it is not found
                    _mnu = new JMenu(ss);
                    dontSearch_YouCantFindIt = true;   //the next levels are also can't be found, so don;t search for them
                    //so create the next levels
                    if (s.hasMoreTokens()) mnu.add(_mnu);
                    else
                        insert(mnu, _mnu, place);   //the given place applied to last level.
                } else if (!s.hasMoreTokens())
                    insert(mnu, _mnu, place);   //the given place applied to last level.

//                else {
//                }
                mnu = _mnu;
            }
        }
        return mnu;

    }
}
//            for (int i = 1; i < items.length - 1; i++) {
//                if (items[i] == child) {
//                    int rightPlace = componentPlaces.get(items[i + 1]);
//                    int leftPlace = componentPlaces.get(items[i - 1]);
//                    componentPlaces.put(child, (rightPlace + leftPlace) / 2);
//                    found = true;
//                }
//                ;
//            }
//            if (!found){    //it was at item[0] or at the end
//                if (items[0]==child){
//                    componentPlaces.put(child,componentPlaces.get(items[2])/2);
//                }
//                if (items[items.length-1]==child){
//                    componentPlaces.put(child,componentPlaces.get(items[items.length-1])+10);
//                }
//            }
//
//
