// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.visualization;

//disabled because LocalSF does the same job much better
/**
 * User: root
 */
//public class SpringForce extends AbstractAction {
//    String event = UI.getUIEvent("Spring Force");
//
//    /**
//     * constructor
//     *
//     * @param bb the blackboard of the action
//     */
//    public SpringForce(blackboard bb) {
//        super(bb);
//        listen4Event(event);
//        //the variable which the component is in.
//        final String stop = UI.getComponentVariableNameInBlackBoard("stop");
//        stopbtn = (JButton) UI.getComponent(bb, "stop");
//        if (stopbtn != null)
//            stopbtn.setVisible(false);
//        blackboard.addListener(new Event(stop), new Listener() {
//            public void performJob(String name) {
//                stopbtn = blackboard.get(stop);
//                stopbtn.setVisible(false);
//            }
//
//            public void notifyRelevantActions(Event name) {
//                stopbtn = blackboard.get(stop);
//                stopbtn.setVisible(false);
//            }
//
//            public boolean isEnable() {
//                return true;
//            }
//        });
//    }
//
//    JButton stopbtn;
//    animatorSF a;
//
//    /**
//     * like Action
//     *
//     * @param name
//     */
//    public void performJob(String name) {
//        //should be corrected according to localsf
////        stopbtn.setVisible(true);
////        stopbtn.addActionListener(new ActionListener() {
////            public void actionPerformed(ActionEvent e) {
////                a._stop();
////                stopbtn.setVisible(false);
////            }
////        });
////        a = new animatorSF(g);
////        a.start();
//    }
//
////    GraphModel g = blackboard.getLog(GraphModel.name).getLast();
//}
//
//class animatorSF extends Thread {
//    private GraphModel g;
//    Vertex[] v;
//    GraphPoint[] verPos;
//    private int E = 10;
//    private double springK = 1;
//    private double q1q2k = 200000;
//    int n = 0;
//    boolean stop = false;
//    int counter = 0;
//
//    public animatorSF(GraphModel g) {
//        this.g = gv;
//        n = g.getVerticesCount();
//        verPos = new GraphPoint[n];
//        getVertices();
//    }
//
//    public void run() {
//        stop = false;
//        while (!stop) {
//            verPos = refreshPositioning(verPos);
//            if (counter++ == 20) {
//                counter = 0;
//                for (int i = 0; i < n; i++)
//                    v[i].view.setLocation(verPos[i]);
//            }
//            getVertices();
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                ExceptionHandler.catchException(e);
//            }
//        }
//    }
//
//    public void _stop() {
//        stop = true;
//    }
//
//    private Vertex[] getVertices() {
//        Vertex[] ret = new Vertex[n];
//        int i = 0;
//        for (Vertex vm : g) {
//            ret[i] = vm;
//            verPos[i++] = vm.model.getLocation();
//        }
//
//        return v = ret;
//    }
//
//    private Point[] refreshPositioning(GraphPoint[] lPosition) {
//        Point[] f = new Point[n];
//        Point[] ret = new Point[n];
//        for (int i = 0; i < n; i++) {
//            ret[i] = new Point(0, 0);
//        }
//        for (int i = 0; i < n; i++) {
//            f[i] = new Point(0, 0);
//            for (int j = 0; j < n; j++) {
//                Point ef = electricalForce(i, j, lPosition);
//                f[i].x += ef.x;
//                f[i].y += ef.y;
//                if (g.isEdge(v[i].model, v[j].model) || gv.isEdge(v[j].model, v[i].model)) {
//                    Point sf = springForce(i, j, lPosition);
//                    f[i].x -= sf.x;
//                    f[i].y -= sf.y;
//                }
//            }
//            ret[i].x = lPosition[i].x + f[i].x / E;
//            ret[i].y = lPosition[i].y + f[i].y / E;
//        }
//        return ret;
//    }
//
//    private Point springForce(int i, int j, Point[] lPosition) {
//        Point ret = new Point(0, 0);
//        int x = lPosition[i].x - lPosition[j].x;
//        int y = lPosition[i].y - lPosition[j].y;
//        double v = sqrt(y * y + x * x);
//        double fSize = springK * v;
//        ret.x = (int) (fSize * x / v);
//        ret.y = (int) (fSize * y / v);
//        return ret;
//    }
//
//    private Point electricalForce(int i, int j, Point[] lPosition) {
//        Point ret = new Point(0, 0);
//        int x = lPosition[i].x - lPosition[j].x;
//        int y = lPosition[i].y - lPosition[j].y;
//        double v = pow(x, 2) + pow(y, 2);
//        double fSize = q1q2k / v;
//        double vv = sqrt(v);
//        ret.x = (int) (fSize * x / vv);
//        ret.y = (int) (fSize * y / vv);
//        return ret;
//    }
//
//}