package graphtea.extensions.algorithms;

import graphtea.graph.graph.*;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.algorithmanimator.core.GraphAlgorithm;
import graphtea.plugins.algorithmanimator.extension.AlgorithmExtension;

import java.awt.*;


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

        Vertex.addGlobalUserDefinedAttribute("net_force", new GraphPoint(0,0));

        Edge.addGlobalUserDefinedAttribute("k",1.0);
        FastRenderer.defaultVertexRadius=7;
        FastRenderer.defaultShapeDimension=new Dimension(15,15);
    }

    AbstractGraphRenderer gr = AbstractGraphRenderer.getCurrentGraphRenderer(graphData.getBlackboard());


    void updateAcce(Vertex v, GraphPoint force) {
        GraphPoint acce = v.getUserDefinedAttribute("acce");
        acce = GraphPoint.add(GraphPoint.div(force, (Double) v.getUserDefinedAttribute("mass")),acce);
        v.setUserDefinedAttribute("acce",acce);
    }


    public void doAlgorithm() {
        GraphModel g = graphData.getGraph();
        Font f = new Font(gr.getName(),gr.getFont().getStyle(),2);
        g.setFont(f);



        Font ff = new Font(gr.getName(),gr.getFont().getStyle(),2);
        gr.setFont(ff);

        double stiffness = 400;
        double repulsion = 400;
        double damping   = 0.01;

        GraphPoint center = new GraphPoint(0,0);
        for(Vertex v : g) {
            center = GraphPoint.add(center, v.getLocation());
        }

        center = GraphPoint.div(center,g.getVerticesCount());


        step("start the visualization.");

        double energy = 0;


       do {

        gr.repaint();

        // CoulombusLaw
        for(Vertex v1 : g)
            for(Vertex v2 : g) {
                if(v1.getId() != v2.getId()) {
                    GraphPoint direction = GraphPoint.sub(v1.getLocation(),v2.getLocation());
                    double distance = direction.norm() + 0.1;
                    direction = GraphPoint.normalise(direction);

                    GraphPoint v1Loc = GraphPoint.div(direction, distance*distance*0.5);
                    v1Loc = GraphPoint.mul(v1Loc, repulsion);
                    updateAcce(v1,v1Loc);


                    GraphPoint v2Loc = GraphPoint.div(direction, distance*distance*-0.5);
                    v2Loc = GraphPoint.mul(v2Loc, repulsion);
                    updateAcce(v2,v2Loc);
                }
            }

        // Hookes Law
        for(Edge e :  g.edges()) {
            Vertex v1 = e.source;
            Vertex v2 = e.target;
            double newDist =  v1.getLocation().distance(v2.getLocation());
            double displacement = 10 - newDist;//1 = the wanted length of edge

            GraphPoint newDirection = GraphPoint.sub(v2.getLocation(),v1.getLocation());
            newDirection = GraphPoint.normalise(newDirection);

            GraphPoint v1NewLoc = GraphPoint.mul(newDirection,stiffness*displacement*-0.5);
            updateAcce(v1,v1NewLoc);

            GraphPoint v2NewLoc = GraphPoint.mul(newDirection,stiffness*displacement*0.5);
            updateAcce(v2,v2NewLoc);
        }

        //attractToCentre
        for(Vertex v : g) {
            //GraphPoint direction = GraphPoint.mul(v.getLocation(),-1.0);
            GraphPoint direction= GraphPoint.sub(center,v.getLocation());
            direction = GraphPoint.mul(direction,repulsion/50);
            updateAcce(v,direction);
        }

        step("update velocity.");

        double timestep = 0.03;
        //update Veocity
        for (Vertex v : g) {
            GraphPoint velo = v.getUserDefinedAttribute("velo");
            GraphPoint acce = v.getUserDefinedAttribute("acce");
            acce = GraphPoint.mul(acce,timestep);
            velo = GraphPoint.add(velo, acce);
            velo = GraphPoint.mul(velo, damping);
            v.setUserDefinedAttribute("velo", velo);
            v.setUserDefinedAttribute("acce", new GraphPoint(0,0));
            //update position
            GraphPoint pos = GraphPoint.mul(velo, timestep);
            v.setLocation(GraphPoint.add(v.getLocation(),pos));
        }

       energy = 1;
       for(Vertex v : g) {
           GraphPoint velo = v.getUserDefinedAttribute("velo");
           double speed = velo.norm();

           double mass = (Double)v.getUserDefinedAttribute("mass");
           //energy += 0.5*mass*speed*speed;
       }
       step("energy" + energy);

       } while(energy > 0.01);
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
