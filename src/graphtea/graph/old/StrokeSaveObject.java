package graphtea.graph.old;

import java.io.Serializable;

/**
 * Created by rostam on 18.12.15.
 */
public class StrokeSaveObject implements Serializable {
    String name;
    int w;
    float[] dashInfo;

    public StrokeSaveObject(GStroke gs) {
        name = gs.name;
        w=gs.w;
        dashInfo=gs.stroke.getDashArray();
    }

    public GStroke getGStroke() {
        System.out.println("test"+name+" "+w);
        return new GStroke(name,w,dashInfo);
    }
}
