package graphtea.extensions.algorithms;

import graphtea.graph.graph.*;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.algorithmanimator.core.GraphAlgorithm;
import graphtea.plugins.algorithmanimator.extension.AlgorithmExtension;


/**
 * Created with IntelliJ IDEA.
 * User: rostam
 * Date: 6/1/13
 * Time: 1:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class ForceDirectedVisualizer extends GraphAlgorithm implements AlgorithmExtension {
    public ForceDirectedVisualizer(BlackBoard blackBoard) {
        super(blackBoard);

        Vertex.addGlobalUserDefinedAttribute("mass",1.0);
        Vertex.addGlobalUserDefinedAttribute("velo",new GraphPoint(0,0));
        Vertex.addGlobalUserDefinedAttribute("acce",new GraphPoint(0,0));

        Edge.addGlobalUserDefinedAttribute("k",1.0);

    }

    AbstractGraphRenderer gr = AbstractGraphRenderer.getCurrentGraphRenderer(graphData.getBlackboard());


    @Override
    public void doAlgorithm() {
        GraphModel g = graphData.getGraph();

        double stiffness = 400;
        double repulsion = 200;
        double damping   = 0.5;

        GraphPoint center = new GraphPoint(0,0);
        for(Vertex v : g) {
            center = GraphPoint.add(center, v.getLocation());
        }

        center = GraphPoint.div(center,g.getVerticesCount());


        step("start the visualization.");

        double energy = 0;

       do {

        gr.repaint();
        //CoulombusLaw and HoolesLaw

        for(Vertex v1 : g)
            for(Vertex v2 : g)
                if(!v1.equals(v2)) {
                    double distance = v1.getLocation().distance(v2.getLocation()) + 1;
                    GraphPoint direction = GraphPoint.sub(v1.getLocation(),v2.getLocation());
                    direction = GraphPoint.normalise(direction);
                    GraphPoint v1Loc = GraphPoint.div(direction, distance * distance * 0.5);
                    //step("v1loc" + v1Loc);
                    v1Loc = GraphPoint.mul(v1Loc, repulsion);
                    //v1Loc = GraphPoint.div(v1Loc, (Double) v1.getUserDefinedAttribute("mass"));
                    v1Loc = GraphPoint.add(v1.getLocation(),v1Loc);

                    GraphPoint v2Loc = GraphPoint.div(direction, distance*distance*-0.5);
                    v2Loc = GraphPoint.mul(v2Loc, repulsion);
                    v2Loc = GraphPoint.div(v2Loc, (Double) v2.getUserDefinedAttribute("mass"));
                    v2Loc = GraphPoint.add(v2.getLocation(), v2Loc);

                    v1.setLocation(v1Loc);
                    v2.setLocation(v2Loc);

                    if(g.getEdge(v1,v2) != null) {
                        double newDist =  v1.getLocation().distance(v2.getLocation());
                        GraphPoint newDirection = GraphPoint.sub(v1.getLocation(),v2.getLocation());
                        newDirection = GraphPoint.normalise(newDirection);
                        double displacement = 100 - distance;//1 = the wanted length of edge
                        GraphPoint v1NewLoc = GraphPoint.mul(newDirection,displacement*-0.01);
                        v1NewLoc = GraphPoint.add(v1.getLocation(), v1NewLoc);

                        GraphPoint v2NewLoc = GraphPoint.mul(newDirection,displacement*0.01);
                        v2NewLoc = GraphPoint.add(v2.getLocation(), v2NewLoc);
                        //v1.setLocation(v1NewLoc);
                        //v2.setLocation(v2NewLoc);
                    }

//                    if(g.getEdge(v1,v2) != null) {
//
//                        //Hooke
//                        double newDist =  GraphPoint.sub(v1Loc,v2Loc).norm();
//                        GraphPoint newDirection = GraphPoint.sub(v1Loc,v2Loc);
//                        double displacement = newDist - 1;//distance;
//
//                        GraphPoint v1NewLoc = GraphPoint.mul(newDirection,
//                            (Double) g.getEdge(v1, v2).getUserDefinedAttribute("k")*displacement*-0.5);
//                        v1NewLoc = GraphPoint.div(v1NewLoc, (Double) v1.getUserDefinedAttribute("mass"));
//                        v1NewLoc = GraphPoint.add(v1.getLocation(), v1NewLoc);
//
//                        GraphPoint v2NewLoc = GraphPoint.mul(newDirection,
//                            (Double) g.getEdge(v1,v2).getUserDefinedAttribute("k")*displacement*0.5);
//                        v2NewLoc = GraphPoint.div(v2NewLoc, (Double) v1.getUserDefinedAttribute("mass"));
//                        v2NewLoc = GraphPoint.add(v1.getLocation(),v2NewLoc);
//
//                        v1.setLocation(v1NewLoc);
//                        v2.setLocation(v2NewLoc);
//                    }
                }

           step("attract to Center.");

        //attractToCentre
        for(Vertex v : g) {
            GraphPoint direction = GraphPoint.sub(center,v.getLocation());
            direction = GraphPoint.normalise(direction);
            direction = GraphPoint.mul(direction,repulsion/50);
            GraphPoint newLoc = GraphPoint.add(v.getLocation(), direction);
            v.setLocation(newLoc);
        }
//
//           step("update velocity.");
//
//
//        double timestep = 0.03;
//        //update Veocity
//        for (Vertex v : g) {
//            GraphPoint velo = v.getUserDefinedAttribute("velo");
//            GraphPoint acce = v.getUserDefinedAttribute("acce");
//            acce = GraphPoint.mul(acce,timestep);
//            velo = GraphPoint.add(velo, acce);
//            velo = GraphPoint.mul(velo, damping);
//            v.setUserDefinedAttribute("velo", velo);
//            v.setUserDefinedAttribute("acce", new GraphPoint(0,0));
//            //update position
//            GraphPoint pos = GraphPoint.mul(velo, timestep);
//            v.setLocation(GraphPoint.add(v.getLocation(),pos));
//        }

       for(Vertex v : g) {
           GraphPoint velo = v.getUserDefinedAttribute("velo");
           double speed = velo.norm();

           double mass = (Double)v.getUserDefinedAttribute("mass");
           energy += 0.5*mass*speed*speed;
       }
       step("energy" + energy);

       } while(energy < 0.01);
    }

    @Override
    public String getName() {
        return "Force Directed Visualizer";
    }

    @Override
    public String getDescription() {
        return "Force Directed Visualizer";
    }
}
