
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
    public void testModifiedGeneralizedWebGraph() {
        ModifiedGeneralizedWebGraph.n = 4;
        ModifiedGeneralizedWebGraph.t = 2;
        ModifiedGeneralizedWebGraph varWebGraph = new ModifiedGeneralizedWebGraph();
        GraphModel g = varWebGraph.generateGraph();
        int n = ModifiedGeneralizedWebGraph.n;
        int t = ModifiedGeneralizedWebGraph.t;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, 3);
        Assertions.assertEquals(diameter, n/2 + 2);
        Assertions.assertEquals(numOfVertices, (t+1)*n + 1);
        Assertions.assertEquals(numOfEdges, (2*t*n)+n);
        Assertions.assertEquals(maxDegree, 4);
        Assertions.assertEquals(minDegree, 1);
    }

    @Test
    public void testWebGraph() {
        WebGraph.n = 4;
        WebGraph varWebGraph = new WebGraph();
        GraphModel g = varWebGraph.generateGraph();
        int n = ModifiedGeneralizedWebGraph.n;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, n);
        Assertions.assertEquals(diameter, n/2 + 2);
        Assertions.assertEquals(numOfVertices, 3*n );
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
        CmnGenerator.n = 11;
        CmnGenerator.m = 4;
        CmnGenerator varCmnGenerator = new CmnGenerator();
        GraphModel g = varCmnGenerator.generateGraph();
        int n = CmnGenerator.n;
        int m = CmnGenerator.m;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, 4);
        Assertions.assertEquals(diameter, n/2 + m/2);//???
        Assertions.assertEquals(numOfVertices, n * m);
        Assertions.assertEquals(numOfEdges, 2*n * m);//???
        Assertions.assertEquals(maxDegree, 4);
        Assertions.assertEquals(minDegree, 4);
    }

//    @Test
//    public void testRandomGenerator() {
//        RandomGenerator.n = 10;
//        RandomGenerator varRandomGenerator = new RandomGenerator();
//        GraphModel g = varRandomGenerator.generateGraph();
//        int n = RandomGenerator.n;
//        int numOfVertices = g.numOfVertices();
//        int numOfEdges = g.getEdgesCount();
//        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
//        int maxDegree = maxAndMinDegree.get(0);
//        int minDegree = maxAndMinDegree.get(1);
//        int girth = new GirthSize().calculate(g);
//        int diameter = new Diameter().calculate(g);
//        Assertions.assertEquals(girth, n);
//        Assertions.assertEquals(diameter, n);
//        Assertions.assertEquals(numOfVertices, n);
//        Assertions.assertEquals(numOfEdges, n);
//        Assertions.assertEquals(maxDegree, n);
//        Assertions.assertEquals(minDegree, n);
//    }
//
//    @Test
//    public void testRandomTreeGenerator() {
//        RandomTreeGenerator.n = 10;
//        RandomTreeGenerator varRandomTreeGenerator = new RandomTreeGenerator();
//        GraphModel g = varRandomTreeGenerator.generateGraph();
//        int n = RandomTreeGenerator.n;
//        int numOfVertices = g.numOfVertices();
//        int numOfEdges = g.getEdgesCount();
//        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
//        int maxDegree = maxAndMinDegree.get(0);
//        int minDegree = maxAndMinDegree.get(1);
//        int girth = new GirthSize().calculate(g);
//        int diameter = new Diameter().calculate(g);
//        Assertions.assertEquals(girth, n);
//        Assertions.assertEquals(diameter, n);
//        Assertions.assertEquals(numOfVertices, n);
//        Assertions.assertEquals(numOfEdges, n);
//        Assertions.assertEquals(maxDegree, n);
//        Assertions.assertEquals(minDegree, n);
//    }

    @Test
    public void testSunletCrownGraph() {
        SunletCrownGraph.n = 10;
        SunletCrownGraph varCrownGraph = new SunletCrownGraph();
        GraphModel g = varCrownGraph.generateGraph();
        int n = SunletCrownGraph.n;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        //assume n >= 3
        Assertions.assertEquals(girth, n);//???
        Assertions.assertEquals(diameter, 2 + n/2);
        Assertions.assertEquals(numOfVertices, 2*n);
        Assertions.assertEquals(numOfEdges, 2*n);
        Assertions.assertEquals(maxDegree, 3);
        Assertions.assertEquals(minDegree, 1);
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
        //assume n >= 3
        if(n == 3) Assertions.assertEquals(girth, 6);
        else Assertions.assertEquals(girth, 4);
        Assertions.assertEquals(diameter, 3);
        Assertions.assertEquals(numOfVertices, 2*n);
        Assertions.assertEquals(numOfEdges, n*(n-1));
        Assertions.assertEquals(maxDegree, n - 1);
        Assertions.assertEquals(minDegree, n - 1);
    }

    @Test
    public void testPrismGraph() {
        PrismGraph.n = 12;
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
        Assertions.assertEquals(girth, 4);
        Assertions.assertEquals(diameter, n/2 + 1);
        Assertions.assertEquals(numOfVertices, 2*n);
        Assertions.assertEquals(numOfEdges, 3*n);
        Assertions.assertEquals(maxDegree, 3);
        Assertions.assertEquals(minDegree, 3);
    }

    @Test
    public void testBananaTreeGenerator() {
        BananaTreeGenerator.n = 4;
        BananaTreeGenerator.k = 10;
        BananaTreeGenerator varBananaTreeGenerator = new BananaTreeGenerator();
        GraphModel g = varBananaTreeGenerator.generateGraph();
        int n = BananaTreeGenerator.n;
        int k = BananaTreeGenerator.k;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, 0);
        Assertions.assertEquals(diameter, 4);
        Assertions.assertEquals(numOfVertices, n*k + 1);
        Assertions.assertEquals(numOfEdges, n*k);
        Assertions.assertEquals(maxDegree, Math.max(n,k));
        Assertions.assertEquals(minDegree, 1);
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
        TadpoleGenerator.k = 4;
        TadpoleGenerator varTadpoleGenerator = new TadpoleGenerator();
        GraphModel g = varTadpoleGenerator.generateGraph();
        int n = TadpoleGenerator.n;
        int k = TadpoleGenerator.k;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, n);
        Assertions.assertEquals(diameter, n/2 + k);
        Assertions.assertEquals(numOfVertices, k+n);
        Assertions.assertEquals(numOfEdges, k+n);
        Assertions.assertEquals(maxDegree, 3);
        Assertions.assertEquals(minDegree, 1);
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
        Assertions.assertEquals(girth, 3);
        Assertions.assertEquals(diameter, n/2 + n%2);
        Assertions.assertEquals(numOfVertices, 2*n);
        Assertions.assertEquals(numOfEdges, 4*n);
        Assertions.assertEquals(maxDegree, 4);
        Assertions.assertEquals(minDegree, 4);
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
        Assertions.assertEquals(girth, 4);
        Assertions.assertEquals(diameter, 4);
        Assertions.assertEquals(numOfVertices, 2*n+1);
        Assertions.assertEquals(numOfEdges, 3*n);
        Assertions.assertEquals(maxDegree, n);
        Assertions.assertEquals(minDegree, 2);
    }

    @Test
    public void testTreeGenerator() {
        TreeGenerator.depth = 4;
        TreeGenerator.degree = 4;
        TreeGenerator varTreeGenerator = new TreeGenerator();
        GraphModel g = varTreeGenerator.generateGraph();
        int depth = TreeGenerator.depth;
        int degree = TreeGenerator.degree;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, 0);
        Assertions.assertEquals(diameter, 2*depth);
        Assertions.assertEquals(numOfVertices, (Math.pow(degree, depth + 1) - 1) / (degree - 1));
        Assertions.assertEquals(numOfEdges, ((Math.pow(degree, depth + 1) - 1) / (degree - 1)) - 1);
        Assertions.assertEquals(maxDegree, degree + 1);
        Assertions.assertEquals(minDegree, 1);
    }

    @Test
    public void testRegularGraphGenerator() {
        RegularGraphGenerator.n = 10;
        RegularGraphGenerator.deg = 3;
        RegularGraphGenerator varRegularGraphGenerator = new RegularGraphGenerator();
        GraphModel g = varRegularGraphGenerator.generateGraph();
        int n = RegularGraphGenerator.n;
        int deg = RegularGraphGenerator.deg;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, deg + 1);
//        Assertions.assertEquals(diameter, n); ? do not know how to calculate
        Assertions.assertEquals(numOfVertices, n);
        Assertions.assertEquals(numOfEdges, (n*deg)/2);
        Assertions.assertEquals(maxDegree, deg);
        Assertions.assertEquals(minDegree, deg);
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
        Assertions.assertEquals(girth, 3);
        Assertions.assertEquals(diameter, 2);
        Assertions.assertEquals(numOfVertices, 2*n);
        Assertions.assertEquals(numOfEdges, n*(2*n-1) - n);
        Assertions.assertEquals(maxDegree, 2*n-2);
        Assertions.assertEquals(minDegree, 2*n-2);
    }

    @Test
    public void testKmnGenerator() {
        KmnGenerator.n = 10;
        KmnGenerator.m = 4;
        KmnGenerator varKmnGenerator = new KmnGenerator();
        GraphModel g = varKmnGenerator.generateGraph();
        int n = KmnGenerator.n;
        int m = KmnGenerator.m;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, 4);
        Assertions.assertEquals(diameter, 2);
        Assertions.assertEquals(numOfVertices, n+m);
        Assertions.assertEquals(numOfEdges, n*m);
        Assertions.assertEquals(maxDegree, Math.max(m,n));
        Assertions.assertEquals(minDegree, Math.min(m,n));
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
        Assertions.assertEquals(girth, 4);
        Assertions.assertEquals(diameter, n/2);
        Assertions.assertEquals(numOfVertices, 2*n);
        Assertions.assertEquals(numOfEdges, 3*n);
        Assertions.assertEquals(maxDegree, 3);
        Assertions.assertEquals(minDegree, 3);
    }

    @Test
    public void testKmnoGenerator() {
        KmnoGenerator.n = 10;
        KmnoGenerator.m = 4;
        KmnoGenerator.o = 3;
        KmnoGenerator varKmnoGenerator = new KmnoGenerator();
        GraphModel g = varKmnoGenerator.generateGraph();
        int n = KmnoGenerator.n;
        int m = KmnoGenerator.m;
        int o = KmnoGenerator.o;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, 3);
        Assertions.assertEquals(diameter, 2);
        Assertions.assertEquals(numOfVertices, n + m + o);
        Assertions.assertEquals(numOfEdges, n*m + n*o + m*o);
        int maxdeg = 0, mindeg  = 0;
        if(m >= n && m >= o) {
            maxdeg = m + Math.max(n,o);
            mindeg = n + o;
        } else if(n >= m && n >= o) {
            maxdeg = n + Math.max(m,o);
            mindeg = m + o;
        } else if(o > n && o > m) {
            maxdeg = o + Math.max(n,m);
            mindeg = n + m;
        }
        Assertions.assertEquals(maxDegree, maxdeg);
        Assertions.assertEquals(minDegree, mindeg);
    }

    // Returns factorial of n
    int fact(int n)
    {
        int res = 1;
        for (int i = 2; i <= n; i++)
            res = res * i;
        return res;
    }
    
    int nCk(int n, int k) {
        return fact(n)/(fact(n-k)*fact(k));
    }

    @Test
    public void testKndKneserGraphGenerator() {
        KndKneserGraphGenerator.n = 10;
        KndKneserGraphGenerator.d = 4;
        KndKneserGraphGenerator varKndKneserGraphGenerator = new KndKneserGraphGenerator();
        GraphModel g = varKndKneserGraphGenerator.generateGraph();
        int n = KndKneserGraphGenerator.n;
        int d = KndKneserGraphGenerator.d;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
//        Assertions.assertEquals(girth, n);
//        Assertions.assertEquals(diameter, Math.ceil((d-1)/(n-2*d)) + 2);
//        Assertions.assertEquals(numOfVertices, nCk(n,d));
//        Assertions.assertEquals(numOfEdges, nCk(n,d)*nCk(n-d,d)/2);
//        Assertions.assertEquals(maxDegree, n);
//        Assertions.assertEquals(minDegree, n);
    }

    @Test
    public void testKneserGraphGenerator() {
        KneserGraphGenerator.n = 5;
        KneserGraphGenerator.k = 2;
        KneserGraphGenerator varKneserGraphGenerator = new KneserGraphGenerator();
        GraphModel g = varKneserGraphGenerator.generateGraph();
        int n = KneserGraphGenerator.n;
        int k = KneserGraphGenerator.k;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
//        Assertions.assertEquals(girth, n);
        Assertions.assertEquals(diameter, Math.ceil((k-1)/(n-2*k)) + 1);
        Assertions.assertEquals(numOfVertices, nCk(n,k));
        Assertions.assertEquals(numOfEdges, nCk(n,k)*nCk(n-k,k)/2);
        Assertions.assertEquals(maxDegree, nCk(n-k,k));
        Assertions.assertEquals(minDegree, nCk(n-k,k));
    }

    @Test
    public void testPmnGenerator() {
        PmnGenerator.n = 10;
        PmnGenerator.m = 4;
        PmnGenerator varPmnGenerator = new PmnGenerator();
        GraphModel g = varPmnGenerator.generateGraph();
        int n = PmnGenerator.n;
        int m = PmnGenerator.m;
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, 4);
        Assertions.assertEquals(diameter, n + m - 2);
        Assertions.assertEquals(numOfVertices, n*m);
        Assertions.assertEquals(numOfEdges, 2*n*m - n - m);
        Assertions.assertEquals(maxDegree, 4);
        Assertions.assertEquals(minDegree, 2);
    }

    @Test
    public void testFlowerGraph() {
        FlowerGraph.n = 3;
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
        Assertions.assertEquals(numOfVertices, 2*n+1);
        Assertions.assertEquals(numOfEdges, 4*n);
        Assertions.assertEquals(maxDegree, 2*n);
        Assertions.assertEquals(minDegree, 2);
        Assertions.assertEquals(girth, 3);
        Assertions.assertEquals(diameter, 2);
    }

}