
import graphtea.extensions.generators.*;
import graphtea.extensions.io.g6format.LoadGraph6Format;
import graphtea.extensions.reports.*;
import graphtea.extensions.reports.basicreports.*;
import graphtea.extensions.reports.clique.*;
import graphtea.extensions.reports.others.*;
import graphtea.extensions.reports.spanningtree.*;
import graphtea.extensions.reports.spectralreports.maxflowmincut.*;
import graphtea.extensions.reports.topological.*;
import graphtea.extensions.reports.spectralreports.*;
import graphtea.extensions.reports.connectivity.*;
import graphtea.extensions.reports.energy.*;
import graphtea.extensions.reports.hamilton.*;
import graphtea.extensions.reports.matching.*;
import graphtea.graph.graph.GraphModel;
import org.junit.jupiter.api.Assertions;
import graphtea.graph.graph.SubGraph;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

public class ReportsTest {
    GraphModel peterson = GeneralizedPetersonGenerator.generateGeneralizedPeterson(5,2);
    GraphModel circle3 = CircleGenerator.generateCircle(3);
    GraphModel circle4 = CircleGenerator.generateCircle(4);
    GraphModel circle5 = CircleGenerator.generateCircle(5);
    GraphModel complete4 = CompleteGraphGenerator.generateCompleteGraph(4);
    GraphModel complete5 = CompleteGraphGenerator.generateCompleteGraph(5);
    GraphModel TreeForRadiusDiameterTest1 =
            new LoadGraph6Format()
                    .read(new File("examples/tree_for_radius_diameter_test_1.g6"));
    GraphModel TreeForRadiusDiameterTest2 =
            new LoadGraph6Format()
                    .read(new File("examples/tree_for_radius_diameter_test_2.g6"));

    GraphModel antiprism3 = AntiprismGraph.generateAntiprismGraph(3);

    @Test
    public void testGreedyColoring() {
        HeuristicGreedyColoringNumber hgc = new HeuristicGreedyColoringNumber();
        Assertions.assertEquals(hgc.calculate(circle3),3);
        Assertions.assertEquals(hgc.calculate(circle4),2);
        Assertions.assertEquals(hgc.calculate(circle5),3);
        Assertions.assertEquals(hgc.calculate(complete4),4);
        Assertions.assertEquals(hgc.calculate(complete5),5);
        Assertions.assertEquals(hgc.calculate(peterson),3);
    }

    @Test
    public void testMaxCliqueNumber() {
        MaxCliqueSize mcs =  new MaxCliqueSize();
        Assertions.assertEquals(mcs.calculate(circle3),3);
        Assertions.assertEquals(mcs.calculate(circle4),2);
        Assertions.assertEquals(mcs.calculate(circle5),2);
        Assertions.assertEquals(mcs.calculate(complete4),4);
        Assertions.assertEquals(mcs.calculate(complete5),5);
        Assertions.assertEquals(mcs.calculate(peterson),2);
        LoadGraph6Format loadGraph6Format = new LoadGraph6Format();
        GraphModel g = loadGraph6Format.read(new File("examples/example.g6"));
        Assertions.assertEquals(mcs.calculate(g),3);
    }

    @Test
    public void testChromaticNumber() {
        ChromaticNumber varChromaticNumber = new ChromaticNumber();
    }

    @Test
    public void testMaxCut() {
        MaxCut varMaxCut = new MaxCut();
    }

    @Test
    public void testMaxIndependentSetReport() {
        MaxIndependentSetReport varMaxIndependentSetReport = new MaxIndependentSetReport();
    }

    @Test
    public void testWienerDiameterReport() {
        WienerDiameterReport varWienerDiameterReport = new WienerDiameterReport();
    }

    @Test
    public void testColoringReport() {
        ColoringReport varColoringReport = new ColoringReport();
    }

    @Test
    public void testRandomMatching() {
        RandomMatching varRandomMatching = new RandomMatching();
    }

    @Test
    public void testMSTPrimExtension() {
        MSTPrimExtension varMSTPrimExtension = new MSTPrimExtension();
    }

    @Test
    public void testDegreeDistance() {
        DegreeDistance varDegreeDistance = new DegreeDistance();
    }

    @Test
    public void testHarmonicIndex() {
        HarmonicIndex varHarmonicIndex = new HarmonicIndex();
    }

    @Test
    public void testEccentricConnectivityIndex() {
        EccentricConnectiveIndex varEccentricConnectivityIndex = new EccentricConnectiveIndex();
    }

    @Test
    public void testZagrebCoindex() {
        ZagrebCoindex varZagrebCoindex = new ZagrebCoindex();
    }

    @Test
    public void testM3Final() {
        M3Final varM3Final = new M3Final();
    }

    @Test
    public void testISIBound() {
        ISIBound varISIBound = new ISIBound();
    }

    @Test
    public void testReciprocalDegreeDistance() {
        ReciprocalDegreeDistance varReciprocalDegreeDistance = new ReciprocalDegreeDistance();
    }

    @Test
    public void testPathZagrebIndex() {
        PathZagrebIndex varPathZagrebIndex = new PathZagrebIndex();
    }

    @Test
    public void testWeightedWienerIndex() {
        WeightedWienerIndex varWeightedWienerIndex = new WeightedWienerIndex();
    }

    @Test
    public void testEdgesDegreesList() {
        EdgesDegreesList varEdgesDegreesList = new EdgesDegreesList();
    }

    @Test
    public void testZagrebCoindexSelectedEdges() {
        ZagrebCoindexSelectedEdges varZagrebCoindexSelectedEdges = new ZagrebCoindexSelectedEdges();
    }

    @Test
    public void testEM1UpperBound() {
        EM1UpperBound varEM1UpperBound = new EM1UpperBound();
    }

    @Test
    public void testInde() {
        Inde varInde = new Inde();
    }

    @Test
    public void testAGIndex() {
        AGIndex varAGIndex = new AGIndex();
    }

    @Test
    public void testIncrementalZagrebIndexSelectedEdges() {
        IncrementalZagrebIndexSelectedEdges varIncrementalZagrebIndexSelectedEdges = new IncrementalZagrebIndexSelectedEdges();
    }

    @Test
    public void testAdditiveHarary() {
        AdditiveHarary varAdditiveHarary = new AdditiveHarary();
    }

    @Test
    public void testFinalNewM2Lower() {
        FinalNewM2Lower varFinalNewM2Lower = new FinalNewM2Lower();
    }

    @Test
    public void testECIConjecture() {
        ECIConjecture varECIConjecture = new ECIConjecture();
    }

    @Test
    public void testPB() {
        PB varPB = new PB();
    }

    @Test
    public void testWienerPolarityIndex() {
        WienerPolarityIndex varWienerPolarityIndex = new WienerPolarityIndex();
        Assertions.assertEquals(varWienerPolarityIndex.calculate(peterson),0);
    }

    @Test
    public void testSDD() {
        SDD varSDD = new SDD();
    }

    @Test
    public void testHyperWienerIndex() {
        HyperWienerIndex varHyperWienerIndex = new HyperWienerIndex();
    }

    @Test
    public void testGutmanIndex() {
        GutmanIndex varGutmanIndex = new GutmanIndex();
    }

    @Test
    public void testEM1LowerBound() {
        EM1LowerBound varEM1LowerBound = new EM1LowerBound();
    }

    @Test
    public void testConnectiveEccentricIndex() {
        ConnectiveEccentricIndex varConnectiveEccentricIndex = new ConnectiveEccentricIndex();
    }

    @Test
    public void testInverseSum() {
        InverseSum varInverseSum = new InverseSum();
    }

    @Test
    public void testM3CompIndCoindConjecture() {
        M3CompIndCoindConjecture varM3CompIndCoindConjecture = new M3CompIndCoindConjecture();
    }

    @Test
    public void testHarary() {
        Harary varHarary = new Harary();
    }

    @Test
    public void testZagrebIndex() {
        ZagrebIndex varZagrebIndex = new ZagrebIndex();
//        Assertions.assertEquals(varZagrebIndex.calculate(peterson).get(0),"");
    }

    @Test
    public void testInverseDegree() {
        InverseDegree varInverseDegree = new InverseDegree();
    }

    @Test
    public void testExp() {
        Exp varExp = new Exp();
    }

    @Test
    public void testModifiedFirstZagrebConnectionIndex() {
        ModifiedFirstZagrebConnectionIndex varModifiedFirstZagrebConnectionIndex = new ModifiedFirstZagrebConnectionIndex();
    }

    @Test
    public void testIncrementalZagrebCoindex() {
        IncrementalZagrebCoindex varIncrementalZagrebCoindex = new IncrementalZagrebCoindex();
    }

    @Test
    public void testEccentricityComplexityIndex() {
        EccentricityComplexityIndex varEccentricityComplexityIndex = new EccentricityComplexityIndex();
    }

    @Test
    public void testEdgeDegree() {
        EdgeDegree varEdgeDegree = new EdgeDegree();
        Assertions.assertEquals(varEdgeDegree.calculate(peterson),0);
    }

    @Test
    public void testConnectivityEccentricityIndex() {
        ConnectivityEccentricityIndex varConnectivityEccentricityIndex = new ConnectivityEccentricityIndex();
    }

    @Test
    public void testEccentricityConnectivityIndex() {
        EccentricConnectiveIndex varEccentricityConnectivityIndex = new EccentricConnectiveIndex();
    }

    @Test
    public void testWienerIndex() {
        WienerIndex varWienerIndex = new WienerIndex();
        Assertions.assertEquals(varWienerIndex.calculate(circle3),3);
        Assertions.assertEquals(varWienerIndex.calculate(circle4),8);
        Assertions.assertEquals(varWienerIndex.calculate(circle5),15);
        Assertions.assertEquals(varWienerIndex.calculate(complete4),6);
        Assertions.assertEquals(varWienerIndex.calculate(complete5),10);
        Assertions.assertEquals(varWienerIndex.calculate(antiprism3),18);
    }

    @Test
    public void testVariableZagrebIndex() {
        VariableZagrebIndex varVariableZagrebIndex = new VariableZagrebIndex();
    }

    @Test
    public void testTotalEccentricityIndex() {
        TotalEccentricityIndex varTotalEccentricityIndex = new TotalEccentricityIndex();
    }

    @Test
    public void testHyperZagrebIndex() {
        HyperZagrebIndex varHyperZagrebIndex = new HyperZagrebIndex();
    }

    @Test
    public void testM3BoundConjecture() {
        M3BoundConjecture varM3BoundConjecture = new M3BoundConjecture();
    }

    @Test
    public void testHyperCheck() {
        HyperCheck varHyperCheck = new HyperCheck();
    }

    @Test
    public void testAllCheck() {
        AllCheck varAllCheck = new AllCheck();
    }

    @Test
    public void testEM1Lower() {
        EM1Lower varEM1Lower = new EM1Lower();
    }

    @Test
    public void testSecondMixZagrebIndex() {
        SecondMixZagrebIndex varSecondMixZagrebIndex = new SecondMixZagrebIndex();
    }

    @Test
    public void testLanzhou() {
        Lanzhou varLanzhou = new Lanzhou();
    }

    @Test
    public void testZagrebIndexSelectedEdges() {
        ZagrebIndexSelectedEdges varZagrebIndexSelectedEdges = new ZagrebIndexSelectedEdges();
    }

    @Test
    public void testMWienerIndex() {
        MWienerIndex varMWienerIndex = new MWienerIndex();
    }

    @Test
    public void testEM2UpperBound() {
        EM2UpperBound varEM2UpperBound = new EM2UpperBound();
    }

    @Test
    public void testIncrementalVariableZagrebIndex() {
        IncrementalVariableZagrebIndex varIncrementalVariableZagrebIndex = new IncrementalVariableZagrebIndex();
    }

    @Test
    public void testIncrementalZagrebCoindexSelectedEdges() {
        IncrementalZagrebCoindexSelectedEdges varIncrementalZagrebCoindexSelectedEdges = new IncrementalZagrebCoindexSelectedEdges();
    }

    @Test
    public void testEM2LowerBound() {
        EM2LowerBound varEM2LowerBound = new EM2LowerBound();
    }

    @Test
    public void testcomparision() {
        comparision varcomparision = new comparision();
    }

    @Test
    public void testExponential() {
        Exponential varExponential = new Exponential();
    }

    @Test
    public void testRandicIndex() {
        RandicIndex varRandicIndex = new RandicIndex();
    }

    @Test
    public void testAutographixConj() {
        AutographixConj varAutographixConj = new AutographixConj();
    }

    @Test
    public void testSumConnectivityIndex() {
        SumConnectivityIndex varSumConnectivityIndex = new SumConnectivityIndex();
    }

    @Test
    public void testWinerPolarity() {
        WinerPolarity varWinerPolarity = new WinerPolarity();
    }

    @Test
    public void testMere() {
        Mere varMere = new Mere();
    }

    @Test
    public void testIncrementalZagrebIndex() {
        IncrementalZagrebIndex varIncrementalZagrebIndex = new IncrementalZagrebIndex();
    }

    @Test
    public void testWienerPolarity() {
        WienerPolarity varWienerPolarity = new WienerPolarity();
        Assertions.assertEquals(varWienerPolarity.calculate(peterson),0);
    }

    @Test
    public void testThirdZagrebIndex() {
        ThirdZagrebIndex varThirdZagrebIndex = new ThirdZagrebIndex();
    }

    @Test
    public void testISIUpper() {
        ISIUpper varISIUpper = new ISIUpper();
    }

    @Test
    public void testMultiplicativeHarary() {
        MultiplicativeHarary varMultiplicativeHarary = new MultiplicativeHarary();
    }

    @Test
    public void testBalabanIndex() {
        BalabanIndex varBalabanIndex = new BalabanIndex();
    }

    @Test
    public void testMostar() {
        Mostar varMostar = new Mostar();
    }

    @Test
    public void testMaxCliqueExtension() {
        MaxCliqueExtension varMaxCliqueExtension = new MaxCliqueExtension();
    }

    @Test
    public void testKF_Wiener() {
        KF_Wiener varKF_Wiener = new KF_Wiener();
    }

    @Test
    public void testLEL_vs_KF() {
        LEL_vs_KF varLEL_vs_KF = new LEL_vs_KF();
    }

    @Test
    public void testMixSignlessLaplacianEnergy() {
        MixSignlessLaplacianEnergy varMixSignlessLaplacianEnergy = new MixSignlessLaplacianEnergy();
    }

    @Test
    public void testSignlessLaplacianEstrada() {
        SignlessLaplacianEstrada varSignlessLaplacianEstrada = new SignlessLaplacianEstrada();
    }

    @Test
    public void testCograph() {
        Cograph varCograph = new Cograph();
    }

    @Test
    public void testEnergy() {
        Energy varEnergy = new Energy();
//        Assertions.assertEquals(varEnergy.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testLaplacianEstrada() {
        LaplacianEstrada varLaplacianEstrada = new LaplacianEstrada();
    }

    @Test
    public void testAllEnergies() {
        AllEnergies varAllEnergies = new AllEnergies();
    }

    @Test
    public void testConjecture() {
        Conjecture varConjecture = new Conjecture();
    }

    @Test
    public void testUpperBounds() {
        UpperBounds varUpperBounds = new UpperBounds();
    }

    @Test
    public void testLinear() {
        Linear varLinear = new Linear();
    }

    @Test
    public void testEstrada() {
        Estrada varEstrada = new Estrada();
    }

    @Test
    public void testComplement() {
        Complement varComplement = new Complement();
    }

    @Test
    public void testNewLowerBounds() {
        NewLowerBounds varNewLowerBounds = new NewLowerBounds();
    }

    @Test
    public void testDominationNumber() {
        DominationNumber varDominationNumber = new DominationNumber();
    }

    @Test
    public void testAllPairShortestPathsWithoutWeight() {
        AllPairShortestPathsWithoutWeight varAllPairShortestPathsWithoutWeight = new AllPairShortestPathsWithoutWeight();
    }

    @Test
    public void testIsBipartite() {
        IsBipartite varIsBipartite = new IsBipartite();
        Assertions.assertFalse(varIsBipartite.calculate(peterson));
        Assertions.assertTrue(varIsBipartite.calculate(circle4));
        Assertions.assertFalse(varIsBipartite.calculate(circle5));
        Assertions.assertFalse(varIsBipartite.calculate(complete4));
        Assertions.assertFalse(varIsBipartite.calculate(complete5));
    }

    @Test
    public void testNumOfConnectedComponents() {
        NumOfConnectedComponents varNumOfConnectedComponents = new NumOfConnectedComponents();
    }

    @Test
    public void testIsEulerian() {
        IsEulerian varIsEulerian = new IsEulerian();
        Assertions.assertFalse(varIsEulerian.calculate(peterson));
        Assertions.assertTrue(varIsEulerian.calculate(circle4));
        Assertions.assertTrue(varIsEulerian.calculate(circle5));
        Assertions.assertFalse(varIsEulerian.calculate(complete4));
        Assertions.assertTrue(varIsEulerian.calculate(complete5));
    }

    @Test
    public void testGirthSize() {
        GirthSize varGirthSize = new GirthSize();
        Assertions.assertEquals(varGirthSize.calculate(peterson),5);
        Assertions.assertEquals(varGirthSize.calculate(circle4),4);
        Assertions.assertEquals(varGirthSize.calculate(circle5),5);
        Assertions.assertEquals(varGirthSize.calculate(complete4), 3);
        Assertions.assertEquals(varGirthSize.calculate(complete5), 3);
    }

    @Test
    public void testNumOfTriangles() {
        NumOfTriangles varNumOfTriangles = new NumOfTriangles();
        Assertions.assertEquals(varNumOfTriangles.calculate(peterson),0);
        Assertions.assertEquals(varNumOfTriangles.calculate(circle4),0);
        Assertions.assertEquals(varNumOfTriangles.calculate(circle5),0);
        Assertions.assertEquals(varNumOfTriangles.calculate(complete4), 4);
        Assertions.assertEquals(varNumOfTriangles.calculate(complete5), 10);
    }

    @Test
    public void testVerticesDegreesList() {
        VerticesDegreesList varVerticesDegreesList = new VerticesDegreesList();
    }

    @Test
    public void testNumOfEdges() {
        NumOfEdges varNumOfEdges = new NumOfEdges();
        Assertions.assertEquals(varNumOfEdges.calculate(peterson),15);
        Assertions.assertEquals(varNumOfEdges.calculate(circle4),4);
        Assertions.assertEquals(varNumOfEdges.calculate(circle5),5);
        Assertions.assertEquals(varNumOfEdges.calculate(complete4), 6);
        Assertions.assertEquals(varNumOfEdges.calculate(complete5), 10);
    }

    @Test
    public void testSubTreeCounting() {
        SubTreeCounting varSubTreeCounting = new SubTreeCounting();
    }

    @Test
    public void testMaxAndMinDegree() {
        MaxAndMinDegree varMaxAndMinDegree = new MaxAndMinDegree();
        ArrayList<Integer> maxmin = varMaxAndMinDegree.calculate(peterson);
        Assertions.assertEquals(maxmin.get(0),3);
        Assertions.assertEquals(maxmin.get(1),3);

        maxmin = varMaxAndMinDegree.calculate(circle4);
        Assertions.assertEquals(maxmin.get(0),2);
        Assertions.assertEquals(maxmin.get(1),2);

        maxmin = varMaxAndMinDegree.calculate(circle5);
        Assertions.assertEquals(maxmin.get(0),2);
        Assertions.assertEquals(maxmin.get(1),2);

        maxmin = varMaxAndMinDegree.calculate(complete4);
        Assertions.assertEquals(maxmin.get(0),3);
        Assertions.assertEquals(maxmin.get(1),3);

        maxmin = varMaxAndMinDegree.calculate(complete5);
        Assertions.assertEquals(maxmin.get(0),4);
        Assertions.assertEquals(maxmin.get(1),4);
    }

    @Test
    public void testNumOfStars() {
        NumOfStars varNumOfStars = new NumOfStars();
    }

    @Test
    public void testMaxOfIndSets() {
        MaxOfIndSets varMaxOfIndSets = new MaxOfIndSets();
    }

    @Test
    public void testAdjacencyMatrix() {
        SpectrumOfAdjacencyMatrix varAdjacencyMatrix = new SpectrumOfAdjacencyMatrix();
    }

    @Test
    public void testNumOfIndSets() {
        NumOfIndSets varNumOfIndSets = new NumOfIndSets();
    }

    @Test
    public void testPathsofLengthTwo() {
        PathsofLengthTwo varPathsofLengthTwo = new PathsofLengthTwo();
        Assertions.assertEquals(varPathsofLengthTwo.calculate(peterson),30);//???
        Assertions.assertEquals(varPathsofLengthTwo.calculate(circle4),4);
        Assertions.assertEquals(varPathsofLengthTwo.calculate(circle5),5);
        Assertions.assertEquals(varPathsofLengthTwo.calculate(complete4), 12);
    }

    @Test
    public void testRadius() {
        Radius varRadius = new Radius();
        Assertions.assertEquals(varRadius.calculate(TreeForRadiusDiameterTest1),2);
        Assertions.assertEquals(varRadius.calculate(TreeForRadiusDiameterTest2),2);
        Assertions.assertEquals(varRadius.calculate(circle5),2);
        Assertions.assertEquals(varRadius.calculate(complete4), 1);
        Assertions.assertEquals(varRadius.calculate(complete5), 1);
    }

    @Test
    public void testDiameter() {
        Diameter varDiameter = new Diameter();
        Assertions.assertEquals(varDiameter.calculate(peterson),2);
        Assertions.assertEquals(varDiameter.calculate(circle4),2);
        Assertions.assertEquals(varDiameter.calculate(circle5),2);
        Assertions.assertEquals(varDiameter.calculate(complete4), 1);
        Assertions.assertEquals(varDiameter.calculate(complete5), 1);
        Assertions.assertEquals(varDiameter.calculate(TreeForRadiusDiameterTest1),4);
        Assertions.assertEquals(varDiameter.calculate(TreeForRadiusDiameterTest2),3);
    }

    @Test
    public void testNumOfVertices() {
        NumOfVertices varNumOfVertices = new NumOfVertices();
        Assertions.assertEquals(varNumOfVertices.calculate(peterson),10);
        Assertions.assertEquals(varNumOfVertices.calculate(circle4),4);
        Assertions.assertEquals(varNumOfVertices.calculate(circle5),5);
        Assertions.assertEquals(varNumOfVertices.calculate(complete4), 4);
        Assertions.assertEquals(varNumOfVertices.calculate(complete5), 5);
    }

    @Test
    public void testNumOfQuadrangle() {
        NumOfQuadrangle varNumOfQuadrangle = new NumOfQuadrangle();
        Assertions.assertEquals(varNumOfQuadrangle.calculate(peterson),0);
        Assertions.assertEquals(varNumOfQuadrangle.calculate(circle4),1);
        Assertions.assertEquals(varNumOfQuadrangle.calculate(circle5),0);
        Assertions.assertEquals(varNumOfQuadrangle.calculate(complete4), 3);//???
        Assertions.assertEquals(varNumOfQuadrangle.calculate(complete5), 15);
    }

    @Test
    public void testTotalNumOfStars() {
        TotalNumOfStars varTotalNumOfStars = new TotalNumOfStars();
    }

    @Test
    public void testNumOfVerticesWithDegK() {
        NumOfVerticesWithDegK.k = 3;
        NumOfVerticesWithDegK varNumOfVerticesWithDegK = new NumOfVerticesWithDegK();
        Assertions.assertEquals(varNumOfVerticesWithDegK.calculate(peterson),10);
        Assertions.assertEquals(varNumOfVerticesWithDegK.calculate(circle4),0);
        Assertions.assertEquals(varNumOfVerticesWithDegK.calculate(circle5),0);
        Assertions.assertEquals(varNumOfVerticesWithDegK.calculate(complete4), 4);//???
        Assertions.assertEquals(varNumOfVerticesWithDegK.calculate(complete5), 0);
    }

    @Test
    public void testKConnected() {
        KConnected varKConnected = new KConnected();
    }

    @Test
    public void testSignlessLaplacianEnergy() {
        SignlessLaplacianEnergy varSignlessLaplacianEnergy = new SignlessLaplacianEnergy();
    }

    @Test
    public void testEigenValues() {
        EigenValues varEigenValues = new EigenValues();
    }

    @Test
    public void testLaplacianEnergyLike() {
        LaplacianEnergyLike varLaplacianEnergyLike = new LaplacianEnergyLike();
    }

    @Test
    public void testEccentricityMatrixOfGraph() {
        EccentricityMatrixOfGraph varEccentricityMatrixOfGraph = new EccentricityMatrixOfGraph();
    }

    @Test
    public void testLaplacianEnergy() {
        LaplacianEnergy varLaplacianEnergy = new LaplacianEnergy();
    }

    @Test
    public void testLaplacianOfGraph() {
        LaplacianOfGraph varLaplacianOfGraph = new LaplacianOfGraph();
    }

    @Test
    public void testKirchhoffIndex() {
        KirchhoffIndex varKirchhoffIndex = new KirchhoffIndex();
    }

    @Test
    public void testSignlessLaplacianOfGraph() {
        SignlessLaplacianOfGraph varSignlessLaplacianOfGraph = new SignlessLaplacianOfGraph();
    }

    @Test
    public void testGomoryHuTree() {
        GomoryHuTree varGomoryHuTree = new GomoryHuTree();
    }

    @Test
    public void testMinimumCut() {
        MinimumCut varMinimumCut = new MinimumCut();
    }

    @Test
    public void testMaximumFlow() {
        MaximumFlow varMaximumFlow = new MaximumFlow();
    }

    @Test
    public void testMaxMatchingExtension() {
        MaxMatchingExtension varMaxMatchingExtension = new MaxMatchingExtension();
    }

    @Test
    public void testHamiltonianCycleExtension() {
        HamiltonianCycleExtension varHamiltonianCycleExtension = new HamiltonianCycleExtension();
    }

    @Test
    public void testHamiltonianPathExtension() {
        HamiltonianPathExtension varHamiltonianPathExtension = new HamiltonianPathExtension();
    }

    @Test
    public void testSzegedIndex() {
        SzegedIndex varSzegedIndex = new SzegedIndex();
    }

    @Test
    public void testAllEccen() {
        AllEccen varAllEccen = new AllEccen();
    }

    @Test
    public void testRevisedSzegedIndex() {
        RevisedSzegedIndex varRevisedSzegedIndex = new RevisedSzegedIndex();
    }

    @Test
    public void testMostarIndex() {
        MostarIndex varMostarIndex = new MostarIndex();
    }

    @Test
    public void testMerrifieldSimmons() {
        MerrifieldSimmons varMerrifieldSimmons = new MerrifieldSimmons();
    }

    @Test
    public void testWeightedPiIndex() {
        WeightedPiIndex varWeightedPiIndex = new WeightedPiIndex();
    }

    @Test
    public void testPeripheralVerticesCount() {
        PeripheralVerticesCount varPeripheralVerticesCount = new PeripheralVerticesCount();
    }

    @Test
    public void testEccentricityEnergy() {
        EccentricityEnergy varEccentricityEnergy = new EccentricityEnergy();
    }

    @Test
    public void testWeightedSzegedIndex() {
        WeightedSzegedIndex varWeightedSzegedIndex = new WeightedSzegedIndex();
    }

    @Test
    public void testPeripheralWienerIndex() {
        PeripheralWienerIndex varPeripheralWienerIndex = new PeripheralWienerIndex();
    }

    @Test
    public void testPeripheralVertices() {
        PeripheralVertices varPeripheralVertices = new PeripheralVertices();
    }

    @Test
    public void testEccentricity() {
        Eccentricity varEccentricity = new Eccentricity();
    }

    @Test
    public void testPiIndex() {
        PiIndex varPiIndex = new PiIndex();
    }
}