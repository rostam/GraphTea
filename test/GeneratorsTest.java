
import graphtea.extensions.generators.*;
import graphtea.graph.graph.GraphModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import graphtea.extensions.reports.basicreports.Diameter;
import graphtea.extensions.reports.basicreports.GirthSize;
import graphtea.extensions.reports.basicreports.MaxAndMinDegree;
import java.util.ArrayList;

public class GeneratorsTest {


    @Test
    public void testWheelGenerator() {
        WheelGenerator.n = 10;
        WheelGenerator varWheelGenerator = new WheelGenerator();
        GraphModel g = varWheelGenerator.generateGraph();
        int n = WheelGenerator.n;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, 3);
        Assertions.assertEquals(diameter, 2);
        Assertions.assertEquals(numOfVertices, n);
        Assertions.assertEquals(numOfEdges, 2*(n-1));
        Assertions.assertEquals(maxDegree, n - 1);
        Assertions.assertEquals(minDegree, 3);
    }

    @Test
    public void testWebGraph() {
        WebGraph.n = 10;
        WebGraph varWebGraph = new WebGraph();
        GraphModel g = varWebGraph.generateGraph();
        int n = WebGraph.n;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, 4);
        Assertions.assertEquals(diameter, 6);
        Assertions.assertEquals(numOfVertices, 3*n);
        Assertions.assertEquals(numOfEdges, 4*n);
        Assertions.assertEquals(maxDegree, 4);
        Assertions.assertEquals(minDegree, 1);
    }

    @Test
    public void testCompleteGraphGenerator() {
        CompleteGraphGenerator.n = 10;
        CompleteGraphGenerator varCompleteGraphGenerator = new CompleteGraphGenerator();
        GraphModel g = varCompleteGraphGenerator.generateGraph();
        int n = CompleteGraphGenerator.n;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        //assume n is bigger than 3
        Assertions.assertEquals(girth, 3);
        Assertions.assertEquals(diameter, 1);
        Assertions.assertEquals(numOfVertices, n);
        Assertions.assertEquals(numOfEdges, n*(n-1)/2);
        Assertions.assertEquals(maxDegree, n-1);
        Assertions.assertEquals(minDegree, n-1);
    }

    @Test
    public void testCircleGenerator() {
        CircleGenerator.n = 10;
        CircleGenerator varCircleGenerator = new CircleGenerator();
        GraphModel g = varCircleGenerator.generateGraph();
        int n = CircleGenerator.n;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, n);
        Assertions.assertEquals(diameter, n/2);
        Assertions.assertEquals(numOfVertices, n);
        Assertions.assertEquals(numOfEdges, n);
        Assertions.assertEquals(maxDegree, 2);
        Assertions.assertEquals(minDegree, 2);
    }

    @Test
    public void testPathGenerator() {
        PathGenerator.n = 10;
        PathGenerator varPathGenerator = new PathGenerator();
        GraphModel g = varPathGenerator.generateGraph();
        int n = PathGenerator.n;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, 0);
        Assertions.assertEquals(diameter, n-1);
        Assertions.assertEquals(numOfVertices, n);
        Assertions.assertEquals(numOfEdges, n-1);
        Assertions.assertEquals(maxDegree, 2);
        Assertions.assertEquals(minDegree, 1);
    }

    @Test
    public void testHelmGraph() {
        HelmGraph.n = 10;
        HelmGraph varHelmGraph = new HelmGraph();
        GraphModel g = varHelmGraph.generateGraph();
        int n = HelmGraph.n;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, 3);
        Assertions.assertEquals(diameter, 4);
        Assertions.assertEquals(numOfVertices, 2*n+1);
        Assertions.assertEquals(numOfEdges, 3*n);
        Assertions.assertEquals(maxDegree, n);
        Assertions.assertEquals(minDegree, 1);
    }

    @Test
    public void testCmnGenerator() {
        CmnGenerator.n = 10;
        CmnGenerator varCmnGenerator = new CmnGenerator();
        GraphModel g = varCmnGenerator.generateGraph();
        int n = CmnGenerator.n;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, n);
        Assertions.assertEquals(diameter, n);
        Assertions.assertEquals(numOfVertices, n);
        Assertions.assertEquals(numOfEdges, n);
        Assertions.assertEquals(maxDegree, n);
        Assertions.assertEquals(minDegree, n);
    }

    @Test
    public void testRandomGenerator() {
        RandomGenerator.n = 10;
        RandomGenerator varRandomGenerator = new RandomGenerator();
        GraphModel g = varRandomGenerator.generateGraph();
        int n = RandomGenerator.n;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, n);
        Assertions.assertEquals(diameter, n);
        Assertions.assertEquals(numOfVertices, n);
        Assertions.assertEquals(numOfEdges, n);
        Assertions.assertEquals(maxDegree, n);
        Assertions.assertEquals(minDegree, n);
    }

    @Test
    public void testRandomTreeGenerator() {
        RandomTreeGenerator.n = 10;
        RandomTreeGenerator varRandomTreeGenerator = new RandomTreeGenerator();
        GraphModel g = varRandomTreeGenerator.generateGraph();
        int n = RandomTreeGenerator.n;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, n);
        Assertions.assertEquals(diameter, n);
        Assertions.assertEquals(numOfVertices, n);
        Assertions.assertEquals(numOfEdges, n);
        Assertions.assertEquals(maxDegree, n);
        Assertions.assertEquals(minDegree, n);
    }

    @Test
    public void testCrownGraph() {
        CrownGraph.n = 10;
        CrownGraph varCrownGraph = new CrownGraph();
        GraphModel g = varCrownGraph.generateGraph();
        int n = CrownGraph.n;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, n);
        Assertions.assertEquals(diameter, n);
        Assertions.assertEquals(numOfVertices, n);
        Assertions.assertEquals(numOfEdges, n);
        Assertions.assertEquals(maxDegree, n);
        Assertions.assertEquals(minDegree, n);
    }

    @Test
    public void testPrismGraph() {
        PrismGraph.n = 10;
        PrismGraph varPrismGraph = new PrismGraph();
        GraphModel g = varPrismGraph.generateGraph();
        int n = PrismGraph.n;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, n);
        Assertions.assertEquals(diameter, n);
        Assertions.assertEquals(numOfVertices, n);
        Assertions.assertEquals(numOfEdges, n);
        Assertions.assertEquals(maxDegree, n);
        Assertions.assertEquals(minDegree, n);
    }

    @Test
    public void testBananaTreeGenerator() {
        BananaTreeGenerator.n = 10;
        BananaTreeGenerator varBananaTreeGenerator = new BananaTreeGenerator();
        GraphModel g = varBananaTreeGenerator.generateGraph();
        int n = BananaTreeGenerator.n;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, n);
        Assertions.assertEquals(diameter, n);
        Assertions.assertEquals(numOfVertices, n);
        Assertions.assertEquals(numOfEdges, n);
        Assertions.assertEquals(maxDegree, n);
        Assertions.assertEquals(minDegree, n);
    }

    @Test
    public void testGeneralizedPetersonGenerator() {
        //Test the Peterson graph PG(5,2)
        GeneralizedPetersonGenerator.n = 5;
        GeneralizedPetersonGenerator.k = 2;
        GeneralizedPetersonGenerator varGeneralizedPetersonGenerator = new GeneralizedPetersonGenerator();
        GraphModel g = varGeneralizedPetersonGenerator.generateGraph();
        int n = GeneralizedPetersonGenerator.n;
        int k = GeneralizedPetersonGenerator.k;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, 5);
        Assertions.assertEquals(diameter, 2);
        Assertions.assertEquals(numOfVertices, 10);
        Assertions.assertEquals(numOfEdges, 15);
        Assertions.assertEquals(maxDegree, 3);
        Assertions.assertEquals(minDegree, 3);
    }

    @Test
    public void testTadpoleGenerator() {
        TadpoleGenerator.n = 10;
        TadpoleGenerator varTadpoleGenerator = new TadpoleGenerator();
        GraphModel g = varTadpoleGenerator.generateGraph();
        int n = TadpoleGenerator.n;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, n);
        Assertions.assertEquals(diameter, n);
        Assertions.assertEquals(numOfVertices, n);
        Assertions.assertEquals(numOfEdges, n);
        Assertions.assertEquals(maxDegree, n);
        Assertions.assertEquals(minDegree, n);
    }

    @Test
    public void testAntiprismGraph() {
        AntiprismGraph.n = 10;
        AntiprismGraph varAntiprismGraph = new AntiprismGraph();
        GraphModel g = varAntiprismGraph.generateGraph();
        int n = AntiprismGraph.n;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, n);
        Assertions.assertEquals(diameter, n);
        Assertions.assertEquals(numOfVertices, n);
        Assertions.assertEquals(numOfEdges, n);
        Assertions.assertEquals(maxDegree, n);
        Assertions.assertEquals(minDegree, n);
    }

    @Test
    public void testGearGenerator() {
        GearGenerator.n = 10;
        GearGenerator varGearGenerator = new GearGenerator();
        GraphModel g = varGearGenerator.generateGraph();
        int n = GearGenerator.n;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, n);
        Assertions.assertEquals(diameter, n);
        Assertions.assertEquals(numOfVertices, n);
        Assertions.assertEquals(numOfEdges, n);
        Assertions.assertEquals(maxDegree, n);
        Assertions.assertEquals(minDegree, n);
    }

    @Test
    public void testTreeGenerator() {
        TreeGenerator.n = 10;
        TreeGenerator varTreeGenerator = new TreeGenerator();
        GraphModel g = varTreeGenerator.generateGraph();
        int n = TreeGenerator.n;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, n);
        Assertions.assertEquals(diameter, n);
        Assertions.assertEquals(numOfVertices, n);
        Assertions.assertEquals(numOfEdges, n);
        Assertions.assertEquals(maxDegree, n);
        Assertions.assertEquals(minDegree, n);
    }

    @Test
    public void testRegularGraphGenerator() {
        RegularGraphGenerator.n = 10;
        RegularGraphGenerator varRegularGraphGenerator = new RegularGraphGenerator();
        GraphModel g = varRegularGraphGenerator.generateGraph();
        int n = RegularGraphGenerator.n;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, n);
        Assertions.assertEquals(diameter, n);
        Assertions.assertEquals(numOfVertices, n);
        Assertions.assertEquals(numOfEdges, n);
        Assertions.assertEquals(maxDegree, n);
        Assertions.assertEquals(minDegree, n);
    }

    @Test
    public void testStarGenerator() {
        StarGenerator.n = 10;
        StarGenerator varStarGenerator = new StarGenerator();
        GraphModel g = varStarGenerator.generateGraph();
        int n = StarGenerator.n;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, 0);
        Assertions.assertEquals(diameter, 2);
        Assertions.assertEquals(numOfVertices, n);
        Assertions.assertEquals(numOfEdges, n - 1);
        Assertions.assertEquals(maxDegree, n - 1);
        Assertions.assertEquals(minDegree, 1);
    }

    @Test
    public void testCocktailPartyGraph() {
        CocktailPartyGraph.n = 10;
        CocktailPartyGraph varCocktailPartyGraph = new CocktailPartyGraph();
        GraphModel g = varCocktailPartyGraph.generateGraph();
        int n = CocktailPartyGraph.n;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, n);
        Assertions.assertEquals(diameter, n);
        Assertions.assertEquals(numOfVertices, n);
        Assertions.assertEquals(numOfEdges, n);
        Assertions.assertEquals(maxDegree, n);
        Assertions.assertEquals(minDegree, n);
    }

    @Test
    public void testKmnGenerator() {
        KmnGenerator.n = 10;
        KmnGenerator varKmnGenerator = new KmnGenerator();
        GraphModel g = varKmnGenerator.generateGraph();
        int n = KmnGenerator.n;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, n);
        Assertions.assertEquals(diameter, n);
        Assertions.assertEquals(numOfVertices, n);
        Assertions.assertEquals(numOfEdges, n);
        Assertions.assertEquals(maxDegree, n);
        Assertions.assertEquals(minDegree, n);
    }

    @Test
    public void testCrossedPrismGraph() {
        CrossedPrismGraph.n = 10;
        CrossedPrismGraph varCrossedPrismGraph = new CrossedPrismGraph();
        GraphModel g = varCrossedPrismGraph.generateGraph();
        int n = CrossedPrismGraph.n;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, n);
        Assertions.assertEquals(diameter, n);
        Assertions.assertEquals(numOfVertices, n);
        Assertions.assertEquals(numOfEdges, n);
        Assertions.assertEquals(maxDegree, n);
        Assertions.assertEquals(minDegree, n);
    }

    @Test
    public void testKmnoGenerator() {
        KmnoGenerator.n = 10;
        KmnoGenerator varKmnoGenerator = new KmnoGenerator();
        GraphModel g = varKmnoGenerator.generateGraph();
        int n = KmnoGenerator.n;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, n);
        Assertions.assertEquals(diameter, n);
        Assertions.assertEquals(numOfVertices, n);
        Assertions.assertEquals(numOfEdges, n);
        Assertions.assertEquals(maxDegree, n);
        Assertions.assertEquals(minDegree, n);
    }

    @Test
    public void testKenserGraphGenerator() {
        KenserGraphGenerator.n = 10;
        KenserGraphGenerator varKenserGraphGenerator = new KenserGraphGenerator();
        GraphModel g = varKenserGraphGenerator.generateGraph();
        int n = KenserGraphGenerator.n;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, n);
        Assertions.assertEquals(diameter, n);
        Assertions.assertEquals(numOfVertices, n);
        Assertions.assertEquals(numOfEdges, n);
        Assertions.assertEquals(maxDegree, n);
        Assertions.assertEquals(minDegree, n);
    }

    @Test
    public void testPmnGenerator() {
        PmnGenerator.n = 10;
        PmnGenerator varPmnGenerator = new PmnGenerator();
        GraphModel g = varPmnGenerator.generateGraph();
        int n = PmnGenerator.n;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, n);
        Assertions.assertEquals(diameter, n);
        Assertions.assertEquals(numOfVertices, n);
        Assertions.assertEquals(numOfEdges, n);
        Assertions.assertEquals(maxDegree, n);
        Assertions.assertEquals(minDegree, n);
    }

    @Test
    public void testFlowerGraph() {
        FlowerGraph.n = 10;
        FlowerGraph varFlowerGraph = new FlowerGraph();
        GraphModel g = varFlowerGraph.generateGraph();
        int n = FlowerGraph.n;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, n);
        Assertions.assertEquals(diameter, n);
        Assertions.assertEquals(numOfVertices, n);
        Assertions.assertEquals(numOfEdges, n);
        Assertions.assertEquals(maxDegree, n);
        Assertions.assertEquals(minDegree, n);
    }

}