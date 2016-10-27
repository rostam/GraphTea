// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.graph.old;

import graphtea.platform.StaticUtils;
import graphtea.platform.lang.FromStringProvider;

import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.Vector;

import static java.awt.BasicStroke.CAP_BUTT;
import static java.awt.BasicStroke.JOIN_MITER;

/**
 * User: root
 */
public class GStroke implements Serializable,  FromStringProvider<GStroke> {
    static {
        StaticUtils.setFromStringProvider(GStroke.class.getName(), new GStroke());
    }

    private static final
    long serialVersionUID = 1699990527314740484L;
    static Vector<GStroke> strokes = new Vector<GStroke>();
    public static GStroke empty = new GStroke("Empty", 0, new float[]{0, 100000000f});
    public static GStroke simple = new GStroke("simple", 1, new float[]{1, 0});
    public static GStroke solid = new GStroke("solid", new float[]{1, 0});
    public static GStroke strong = new GStroke("strong", 4, new float[]{1, 0});
    public static GStroke dashed = new GStroke("dashed", new float[]{6, 2});
    public static GStroke dotted = new GStroke("dotted", new float[]{2, 4});
    public static GStroke dashed_dotted = new GStroke("dashed-dotted", new float[]{6, 2, 2, 6});
    public static GStroke dashed_dotted_dotted = new GStroke("dashed-dotted-dotted", new float[]{6, 2, 2, 6, 2, 6});
    public static GStroke dashed_dashed_dotted = new GStroke("dashed-dashed-dotted", new float[]{6, 2, 6, 2, 2, 4});

    public String name;
    public BasicStroke stroke;
    int w = 2;
    public static final String STROKE = "stroke";

    public GStroke(String name, float[] dashInfo) {
        init(name, w, dashInfo);
    }

    public GStroke(String name, int w, float[] dashInfo) {
        init(name, w, dashInfo);
    }

    private void init(String name, int w, float[] dashInfo) {
        this.name = name;
        this.stroke = new BasicStroke(w, CAP_BUTT, JOIN_MITER, 1, dashInfo, 2);
        if (name.equals("solid") || name.equals("simple"))
            stroke = new BasicStroke(w);
        strokes.add(this);
    }

    public GStroke() {
    }

    public GStroke fromString(String data) {
        for (GStroke stroke : strokes)
            if (stroke.name.equals(data)) {
                return stroke;
            }
        return null;
    }

    public String toString() {
        return name;
    }

    private void writeObject(java.io.ObjectOutputStream stream)
            throws IOException {
        stream.writeObject(name);
    }

    private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        name = (String) stream.readObject();
        GStroke target = fromString(name);
        stroke = target.stroke;
        w = target.w;
    }
}