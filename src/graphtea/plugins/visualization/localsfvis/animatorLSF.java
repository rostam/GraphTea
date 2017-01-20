// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.visualization.localsfvis;

import graphtea.graph.graph.*;
import graphtea.library.exceptions.InvalidVertexException;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;
import java.io.File;
import java.util.Arrays;
import java.util.HashSet;

/**
 * @author azin azadi
 */
class animatorLSF extends Thread {
    private boolean b = true;
    private boolean c = true;

    public animatorLSF(BlackBoard blackboard, GraphModel g, AbstractGraphRenderer gv) {
        this.blackboard = blackboard;
        this.g = g;
        this.gv = gv;
        getVertices();
    }

    private BlackBoard blackboard;
    private GraphModel g;
    private AbstractGraphRenderer gv;
    private Vertex[] v;
    private GRect[] vRects;  //represents the rectangle arround each vertex
    private GPoint[] verPos; //fresh generated vertex positions!
    private GPoint[] velocity;
    private GPoint[] prevVerPos; //refferes to previous state of vertex positions, for finding vertices that moved by anything else
    //    private final double neighborRadius = 300;
    private double stres = 10;
    private double maxF = 1000;
    private final int E = 5;
    private double springK = 0.7;
    private double q1q2k = 50000;
    private boolean stop = false;
    private int n = 0;
    private int counter1 = 0,
            counter2 = 0,
            counter3 = 0,
            counter4 = 0;

    private HashSet<Integer>[] neighbors;

    boolean saveScreenShots = false;

    public void run() {
        stop = false;
        {   //determining the directory name for saveing images
//            int _i = 0;
//            String _prefix = "c:\\";
//            while (new File(_prefix + _i).isDirectory() || new File(_prefix + _i).isFile()) _i++;
//            new File(_prefix + _i).mkdir();
//            prefix = _prefix + _i + "\\";
        }
//        SaveLoadPluginMethods slpm = new SaveLoadPluginMethods(blackboard);
//        gv.animation = true;
//        gv.ignoreUpdates = true;
        int stablep = 0;

        final GPoint centerPoint = calculateGraphCenterPoint();     //calculating the center of graph to pin its center
        //final GRect bounds = g.getAbsBounds();     //calculating the bounds of graph to pin its center

        while (!stop) {
            if (counter3++ == 150) {
                counter3 = 0;
                int nstable = 0;
                for (int i = 0; i < n; i++) {
                    if (stableVertex[i])
                        ++nstable;
                    stableVertex[i] = false;
                }
//                if (nstable > 4 * n / 5)
//                    ++stablep;
//                if (stablep == 4) {
                stablep = 0;
                if (isDynamic) {
                    stres();
                    stres = stres * 99 / 100;
                }
//                }

//                System.out.println(nstable + " stable vertices");
            }
            if (counter4++ == 100) {
                counter4 = 2;
                if (isDynamic)
                    updateKs();
            }
            if (counter1++ == 20) {
                counter1 = 0;
                refreshNeighbors();
                getVertices();
            }
            refreshPositioning();
            if (counter2++ == 10) {
                counter2 = 0;
//                gv.ignoreUpdates = true;
                try {
                    gv.ignoreRepaints(new Runnable() {
                        public void run() {
                            GPoint newCenterPoint = calculateGraphCenterPoint();
                            GRect newBounds = calculateGraphAbsBoundsPoint();
                            //todo: to preserve graph bounds
                            double dx = centerPoint.x - newCenterPoint.x;
                            double dy = centerPoint.y - newCenterPoint.y;
                            for (int i = 0; i < n; i++) {
                                double x = verPos[i].x;
                                double y = verPos[i].y;
//                                x=x+dx+ ((x-newCenterPoint.x)/newBounds.width)*bounds.width;
                                x += dx;
                                y += dy;
                                GPoint p = v[i].getLocation();
//                    if (p.distance(verPos[i])<2)
//                        stableVertex[i] = true;

//                        if (!p.equals(prevVerPos[i])) {     //only change the position when it is changed
//                            continue;
//                        }

//                    if (!stableVertex[i])
                                v[i].setLocation(verPos[i]);
                                prevVerPos[i] = p;
//                    else
//                        stableVertex[i]=true;
                            }
                        }
                    });
                }
                catch (InvalidVertexException e) {
                    getVertices();
                    refreshNeighbors();
                }

                if (saveScreenShots && b && c) {
//                    slpm.saveAsImage(nextFile(), "png");                       //saving the screen shot

                }
                b = !b;
                if (b)
                    c = !c;       //har 4 bar ie bar
//                gv.ignoreUpdates = true;

//                getVertices();
//                refreshNeighbors();
            }

            try {
                Thread.sleep((long) (0.7 * Math.log10(n) * Math.sqrt(n)));
            } catch (InterruptedException e) {
                ExceptionHandler.catchException(e);
            }
        }
//        gv.ignoreUpdates = false;
//        gv.animation = false;
//        gv.updateBounds();
        gv.repaint();
    }

    private GPoint calculateGraphCenterPoint() {
        GPoint centerPoint = new GPoint();
        for (GPoint p : verPos) {
            centerPoint.add(p.x, p.y);
        }
        centerPoint.x /= n;
        centerPoint.y /= n;
        return centerPoint;
    }
    private GRect calculateGraphAbsBoundsPoint() {

        GRect ret = new GRect();
        for (GPoint p : verPos) {
            ret.add(p.x, p.y);
        }
        return ret;
    }

    static String prefix;
    int i = 100000;

    File nextFile() { //for saving image
        return new File(prefix + (i++) + ".jpg");
    }

    boolean temporaryStress = false;

    void stres() {
//        if (minDistance<stres){
        for (int i = 0; i < n; i++) {
            verPos[i].x += stres / 2 - Math.random() * stres;
            verPos[i].y += stres / 2 - Math.random() * stres;
        }
//        }
        temporaryStress = false;
        springK *= 3;
//        maxF *= 2;
        q1q2k /= 4;
//        refreshPositioning();
//        refreshPositioning();
//        refreshPositioning();
//        refreshPositioning();
        refreshPositioning();
        refreshPositioning();
        refreshPositioning();
        springK /= 3;
//        maxF /= 2;
        q1q2k *= 4;
//        System.out.println("stres" + s++);
    }

    float minDistance;
    float maxDistance;

    private void updateKs() {
        int i = 0;
        for (Vertex vm : g) {
            vRects[i] = new GRect(vm.getBounds().getBounds().x,vm.getBounds().getBounds().y,
                    vm.getBounds().getBounds().width,vm.getBounds().getBounds().height);
            i++;
        }

        minDistance = 1000000;
        maxDistance = Float.MIN_VALUE;
        final int MAX_DISTANCE = (int) (Math.sqrt(n) * (60));
        final float MIN_DISTANCE = (float) ((8 - Math.log10(n)) * 10);
        double lb = 60 + 100.0 / Math.sqrt(n);
        double hb = 95 + 100.0 / Math.sqrt(n);
        boolean strongerK = false;
        for (i = 0; i < n; ++i) {
            vRects[i].grow((int) lb, (int) lb);
            for (int j : neighbors[i]) {
//                Rectangle r=vRects[i].union(vRects[j]);
//                r.grow();
//                minDistance =min(minDistance,r.width+r.height);
                float distance = Math.abs((float) (verPos[i].x - verPos[j].x)) + Math.abs((float) (verPos[i].y - verPos[j].y));
//                maxDistance = Math.max(maxDistance, distance);
                minDistance = Math.min(minDistance, distance);
                if (vRects[j].inside(verPos[i])) {
                    strongerK = true;
                }
            }
            vRects[i].grow((int) (-lb), (int) (-lb));
        }
//        Rectangle r = new Rectangle();
//        r.

        GRect gBounds = g.getAbsBounds().getBounds();
        maxDistance = (float) ((gBounds.w + gBounds.h) / 2);
//        System.out.println("min getDistance:" + minDistance);
//        System.out.println("max getDistance:" + maxDistance);
        if (strongerK && maxDistance < MAX_DISTANCE) {
//        if (minDistance < lb) {
            weaker();
        }
        if (!strongerK && minDistance > MIN_DISTANCE) {
            stronger();
        }
//        System.out.println("SpringK: " + springK);
    }

    void stronger() {
        springK /= (0.75);
        q1q2k /= (1.3);
    }

    void weaker() {
        springK *= 0.75;
        q1q2k *= 1.3;
    }

    int s = 0;

    public void _stop() {
        stop = true;
    }

    boolean stableVertex[];


    private void getVertices() {
        int _n = g.getVerticesCount();
        if (_n != n) {
            n = _n;
            verPos = new GPoint[n];
            velocity = new GPoint[n];
            prevVerPos = new GPoint[n];
            dists = new pair[Math.max(n - 1, 0)];
            neighbors = new HashSet[n];
            for (int i = 0; i < n; i++) {
                neighbors[i] = new HashSet<Integer>();
                if (i != 0) dists[i - 1] = new pair(0, 0);  //the len of dists should be n-1
            }
            v = new Vertex[n];
            vRects = new GRect[n];
            stableVertex = new boolean[n];
        }
        int i = 0;
        for (Vertex vm : g) {
            v[i] = vm;
            verPos[i] = vm.getLocation();
            velocity[i] = new GPoint();
            vRects[i] = new GRect(vm.getBounds().getBounds().x,vm.getBounds().getBounds().y,
                    vm.getBounds().getBounds().width,vm.getBounds().getBounds().height);
            prevVerPos[i] = new GPoint(verPos[i]);
            i++;
        }
    }

    GPoint f = new GPoint();
    GPoint fs = new GPoint(0, 0);
    double fex = 0;
    double fey = 0;
    double fsx = 0;
    double fsy = 0;
    double x;
    double y;
    double d;
    int fx;
    int fy;
    double d3;

    //ghalbe tapande
    private void refreshPositioning() {
        for (int i = 0; i < n; i++) {
//            if (!stableVertex[i] || Math.random()<0.1) {
            fex = 0;
            fey = 0;
            fsx = 0;
            fsy = 0;
            for (Integer j : neighbors[i]) {
                x = verPos[i].x - verPos[j].x;
                y = verPos[i].y - verPos[j].y;
                if (x == 0 && y == 0) {          //if vertices are in the same location squeeze them!
                    x = Math.random() * 15;
                    y = Math.random() * 15;
//                    verPos[i].x+=x;
//                    verPos[i].y+=y;
                    verPos[j].y -= x;
                    verPos[j].x -= y;
                    x = verPos[i].x - verPos[j].x;
                    y = verPos[i].y - verPos[j].y;
                }
                d = Math.abs(y) + Math.abs(x);
                if (d < 10)           //baraie paresh haie gonde
                    continue;

                //electrical force
                if (d != 0) {
                    d3 = d * d * d;
                    fex += x / d3;
                    fey += y / d3;
                }
                if (edge(i, j)) {
                    //spring force
                    fsx += x;
                    fsy += y;
                }
            }
            fx = (int) (q1q2k * fex - springK * fsx);
            fy = (int) (q1q2k * fey - springK * fsy);
            int fi = Math.abs(fx) + Math.abs(fy);
            if (fi > maxF) {
//                System.out.println("MAXF exceeded: "+fi);
                fx = (int) (maxF * fx / (fi));
                fy = (int) (maxF * fy / (fi));
            }

            verPos[i].x += fx / E;
            verPos[i].y += fy / E;
//            velocity[i].x += fx;
//            velocity[i].y += fy;
//            verPos[i].x += (fx + velocity[i].x)/E;
//            verPos[i].y += (fy + velocity[i].y)/E;

        }
        if (temporaryStress)
            stres();
//        }
    }

    private boolean edge(int i, int jj) {
        try {
            return g.isEdge(v[i], v[jj]) || g.isEdge(v[jj], v[i]);
        }
        catch (Exception e) {
            getVertices();
            refreshNeighbors();
            return false;
        }
    }

    pair[] dists;

    private void refreshNeighbors() {
        for (int i = 0; i < n; i++) {
            if (!stableVertex[i]) {
                neighbors[i].clear();
                int _j = 0;
                for (int j = 0; j < n; j++) {
                    if (j != i) {
                        dists[_j].node = j;
                        dists[_j++].d = v[i].getLocation().distance(v[j].getLocation());
                        if (edge(i, j))
                            neighbors[i].add(j);
                    }
                }
                Arrays.sort(dists);
                for (int t = 0; t < Math.min(5, n - 1); t++) {
//                    System.out.println(dists[t].d);
                    if (dists[t].d < 200)
                        neighbors[i].add(dists[t].node);
                    if (dists[t].d < 2) {
                        verPos[t].x += 10 * Math.random() - 5;
                        verPos[t].y += 10 * Math.random() - 5;
                    }
                }
            }
        }
    }

    boolean isDynamic = true;

    void setDynamic(boolean b) {
        isDynamic = b;
    }
}
