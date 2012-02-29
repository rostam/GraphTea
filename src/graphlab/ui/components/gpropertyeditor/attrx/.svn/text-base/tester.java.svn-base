// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.ui.components.gpropertyeditor.attrx;

import graphlab.platform.attribute.AttributeListener;
import graphlab.platform.lang.ArrayX;
import graphlab.platform.lang.BoundedInteger;
import graphlab.ui.AttributeSetView;
import graphlab.ui.PortableNotifiableAttributeSetImpl;
import graphlab.ui.components.utils.GAttrFrame;

import java.awt.*;
import java.io.File;
import java.util.Vector;

/**
 * documented in GraphLab wiki: HowToUsePropertyEditor
 * @author Azin Azadi
 */
public class tester {
    public static void main(String[] args) {
        PortableNotifiableAttributeSetImpl x = new PortableNotifiableAttributeSetImpl();
        x.addAttributeListener(new AttributeListener() {
            public void attributeUpdated(String name, Object oldVal, Object newVal) {
                System.out.println(name + ":" + newVal);
            }
        });
        x.put("azin", "azadi");
        x.put("file", new File("c:\\a.txt"));
        Vector a = new Vector();
        a.add("azin");
        a.add(Color.red);
        a.add(new BoundedInteger(10, 100, 0));
        x.put("iterable", a);

        AttributeSetView atr = x.getView();
//        atr.get()
        atr.setEditable("azin", false);
        atr.setDisplayName("azin", "azin->disp name");
        atr.setDescription("azin", "azin>desc");


        x.put("xaray", new ArrayX("azin", "azin", "azadi", "yazdi", "graph"));
//        x.put("---", new ArrayX(true, false, "azin", Color.red));
        x.put("bi", new BoundedInteger(20, 30, 10));

        GAttrFrame.showEditDialog(x);

        //Thread.getDefaultUncaughtExceptionHandler()
        //ClassLoader.getSystemClassLoader().
        //java.lang.Compiler.compileClass()
    }
}
