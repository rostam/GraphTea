
import graphtea.extensions.generators.*;
import graphtea.graph.graph.GraphModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GeneratorsTest {

    @Test
    public void testWheelGenerator() {
        WheelGenerator.n = 10;
        WheelGenerator varWheelGenerator = new WheelGenerator();
        GraphModel g = varWheelGenerator.generateGraph();
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        Assertions.assertEquals(numOfVertices, 10);
        Assertions.assertEquals(numOfEdges, 10);
    }

    @Test
    public void testWebGraph() {
        WebGraph.n = 10;
        WebGraph varWebGraph = new WebGraph();
        GraphModel g = varWebGraph.generateGraph();
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        Assertions.assertEquals(numOfVertices, 10);
        Assertions.assertEquals(numOfEdges, 10);
    }

    @Test
    public void testCompleteGraphGenerator() {
        CompleteGraphGenerator.n = 10;
        CompleteGraphGenerator varCompleteGraphGenerator = new CompleteGraphGenerator();
        GraphModel g = varCompleteGraphGenerator.generateGraph();
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        Assertions.assertEquals(numOfVertices, 10);
        Assertions.assertEquals(numOfEdges, 10);
    }

    @Test
    public void testCircleGenerator() {
        CircleGenerator.n = 10;
        CircleGenerator varCircleGenerator = new CircleGenerator();
        GraphModel g = varCircleGenerator.generateGraph();
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        Assertions.assertEquals(numOfVertices, 10);
        Assertions.assertEquals(numOfEdges, 10);
    }

    @Test
    public void testPathGenerator() {
        PathGenerator.n = 10;
        PathGenerator varPathGenerator = new PathGenerator();
        GraphModel g = varPathGenerator.generateGraph();
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        Assertions.assertEquals(numOfVertices, 10);
        Assertions.assertEquals(numOfEdges, 10);
    }

    @Test
    public void testHelmGraph() {
        HelmGraph.n = 10;
        HelmGraph varHelmGraph = new HelmGraph();
        GraphModel g = varHelmGraph.generateGraph();
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        Assertions.assertEquals(numOfVertices, 10);
        Assertions.assertEquals(numOfEdges, 10);
    }

    @Test
    public void testCmnGenerator() {
        CmnGenerator.n = 10;
        CmnGenerator varCmnGenerator = new CmnGenerator();
        GraphModel g = varCmnGenerator.generateGraph();
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        Assertions.assertEquals(numOfVertices, 10);
        Assertions.assertEquals(numOfEdges, 10);
    }

    @Test
    public void testRandomGenerator() {
        RandomGenerator.n = 10;
        RandomGenerator varRandomGenerator = new RandomGenerator();
        GraphModel g = varRandomGenerator.generateGraph();
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        Assertions.assertEquals(numOfVertices, 10);
        Assertions.assertEquals(numOfEdges, 10);
    }

    @Test
    public void testRandomTreeGenerator() {
        RandomTreeGenerator.n = 10;
        RandomTreeGenerator varRandomTreeGenerator = new RandomTreeGenerator();
        GraphModel g = varRandomTreeGenerator.generateGraph();
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        Assertions.assertEquals(numOfVertices, 10);
        Assertions.assertEquals(numOfEdges, 10);
    }

    @Test
    public void testCrownGraph() {
        CrownGraph.n = 10;
        CrownGraph varCrownGraph = new CrownGraph();
        GraphModel g = varCrownGraph.generateGraph();
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        Assertions.assertEquals(numOfVertices, 10);
        Assertions.assertEquals(numOfEdges, 10);
    }

    @Test
    public void testPrismGraph() {
        PrismGraph.n = 10;
        PrismGraph varPrismGraph = new PrismGraph();
        GraphModel g = varPrismGraph.generateGraph();
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        Assertions.assertEquals(numOfVertices, 10);
        Assertions.assertEquals(numOfEdges, 10);
    }

    @Test
    public void testBananaTreeGenerator() {
        BananaTreeGenerator.n = 10;
        BananaTreeGenerator varBananaTreeGenerator = new BananaTreeGenerator();
        GraphModel g = varBananaTreeGenerator.generateGraph();
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        Assertions.assertEquals(numOfVertices, 10);
        Assertions.assertEquals(numOfEdges, 10);
    }

    @Test
    public void testGeneralizedPetersonGenerator() {
        GeneralizedPetersonGenerator.n = 10;
        GeneralizedPetersonGenerator varGeneralizedPetersonGenerator = new GeneralizedPetersonGenerator();
        GraphModel g = varGeneralizedPetersonGenerator.generateGraph();
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        Assertions.assertEquals(numOfVertices, 10);
        Assertions.assertEquals(numOfEdges, 10);
    }

    @Test
    public void testTadpoleGenerator() {
        TadpoleGenerator.n = 10;
        TadpoleGenerator varTadpoleGenerator = new TadpoleGenerator();
        GraphModel g = varTadpoleGenerator.generateGraph();
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        Assertions.assertEquals(numOfVertices, 10);
        Assertions.assertEquals(numOfEdges, 10);
    }

    @Test
    public void testAntiprismGraph() {
        AntiprismGraph.n = 10;
        AntiprismGraph varAntiprismGraph = new AntiprismGraph();
        GraphModel g = varAntiprismGraph.generateGraph();
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        Assertions.assertEquals(numOfVertices, 10);
        Assertions.assertEquals(numOfEdges, 10);
    }

    @Test
    public void testGearGenerator() {
        GearGenerator.n = 10;
        GearGenerator varGearGenerator = new GearGenerator();
        GraphModel g = varGearGenerator.generateGraph();
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        Assertions.assertEquals(numOfVertices, 10);
        Assertions.assertEquals(numOfEdges, 10);
    }

    @Test
    public void testTreeGenerator() {
        TreeGenerator.n = 10;
        TreeGenerator varTreeGenerator = new TreeGenerator();
        GraphModel g = varTreeGenerator.generateGraph();
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        Assertions.assertEquals(numOfVertices, 10);
        Assertions.assertEquals(numOfEdges, 10);
    }

    @Test
    public void testRegularGraphGenerator() {
        RegularGraphGenerator.n = 10;
        RegularGraphGenerator varRegularGraphGenerator = new RegularGraphGenerator();
        GraphModel g = varRegularGraphGenerator.generateGraph();
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        Assertions.assertEquals(numOfVertices, 10);
        Assertions.assertEquals(numOfEdges, 10);
    }

    @Test
    public void testStarGenerator() {
        StarGenerator.n = 10;
        StarGenerator varStarGenerator = new StarGenerator();
        GraphModel g = varStarGenerator.generateGraph();
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        Assertions.assertEquals(numOfVertices, 10);
        Assertions.assertEquals(numOfEdges, 10);
    }

    @Test
    public void testCocktailPartyGraph() {
        CocktailPartyGraph.n = 10;
        CocktailPartyGraph varCocktailPartyGraph = new CocktailPartyGraph();
        GraphModel g = varCocktailPartyGraph.generateGraph();
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        Assertions.assertEquals(numOfVertices, 10);
        Assertions.assertEquals(numOfEdges, 10);
    }

    @Test
    public void testKmnGenerator() {
        KmnGenerator.n = 10;
        KmnGenerator varKmnGenerator = new KmnGenerator();
        GraphModel g = varKmnGenerator.generateGraph();
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        Assertions.assertEquals(numOfVertices, 10);
        Assertions.assertEquals(numOfEdges, 10);
    }

    @Test
    public void testCrossedPrismGraph() {
        CrossedPrismGraph.n = 10;
        CrossedPrismGraph varCrossedPrismGraph = new CrossedPrismGraph();
        GraphModel g = varCrossedPrismGraph.generateGraph();
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        Assertions.assertEquals(numOfVertices, 10);
        Assertions.assertEquals(numOfEdges, 10);
    }

    @Test
    public void testKmnoGenerator() {
        KmnoGenerator.n = 10;
        KmnoGenerator varKmnoGenerator = new KmnoGenerator();
        GraphModel g = varKmnoGenerator.generateGraph();
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        Assertions.assertEquals(numOfVertices, 10);
        Assertions.assertEquals(numOfEdges, 10);
    }

    @Test
    public void testKenserGraphGenerator() {
        KenserGraphGenerator.n = 10;
        KenserGraphGenerator varKenserGraphGenerator = new KenserGraphGenerator();
        GraphModel g = varKenserGraphGenerator.generateGraph();
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        Assertions.assertEquals(numOfVertices, 10);
        Assertions.assertEquals(numOfEdges, 10);
    }

    @Test
    public void testPmnGenerator() {
        PmnGenerator.n = 10;
        PmnGenerator varPmnGenerator = new PmnGenerator();
        GraphModel g = varPmnGenerator.generateGraph();
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        Assertions.assertEquals(numOfVertices, 10);
        Assertions.assertEquals(numOfEdges, 10);
    }

    @Test
    public void testFlowerGraph() {
        FlowerGraph.n = 10;
        FlowerGraph varFlowerGraph = new FlowerGraph();
        GraphModel g = varFlowerGraph.generateGraph();
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        Assertions.assertEquals(numOfVertices, 10);
        Assertions.assertEquals(numOfEdges, 10);
    }

}