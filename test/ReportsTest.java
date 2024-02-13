
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
        Assertions.assertEquals(varChromaticNumber.calculate(peterson),0);
    }

    @Test
    public void testMaxCut() {
        MaxCut varMaxCut = new MaxCut();
        Assertions.assertEquals(varMaxCut.calculate(peterson),0);
    }

    @Test
    public void testMaxIndependentSetReport() {
        MaxIndependentSetReport varMaxIndependentSetReport = new MaxIndependentSetReport();
        Assertions.assertEquals(varMaxIndependentSetReport.calculate(peterson).get(0),new SubGraph());
    }

    @Test
    public void testWienerDiameterReport() {
        WienerDiameterReport varWienerDiameterReport = new WienerDiameterReport();
        Assertions.assertEquals(varWienerDiameterReport.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testColoringReport() {
        ColoringReport varColoringReport = new ColoringReport();
    }

    @Test
    public void testRandomMatching() {
        RandomMatching varRandomMatching = new RandomMatching();
        Assertions.assertEquals(varRandomMatching.calculate(peterson).get(0),new Object());
    }

    @Test
    public void testMSTPrimExtension() {
        MSTPrimExtension varMSTPrimExtension = new MSTPrimExtension();
        Assertions.assertEquals(varMSTPrimExtension.calculate(peterson),new SubGraph());
    }

    @Test
    public void testDegreeDistance() {
        DegreeDistance varDegreeDistance = new DegreeDistance();
        Assertions.assertEquals(varDegreeDistance.calculate(peterson),0);
    }

    @Test
    public void testHarmonicIndex() {
        HarmonicIndex varHarmonicIndex = new HarmonicIndex();
        Assertions.assertEquals(varHarmonicIndex.calculate(peterson).get(0),"");
    }

    @Test
    public void testEccentricConnectivityIndex() {
        EccentricConnectiveIndex varEccentricConnectivityIndex = new EccentricConnectiveIndex();
        Assertions.assertEquals(varEccentricConnectivityIndex.calculate(peterson),"");
    }

    @Test
    public void testZagrebCoindex() {
        ZagrebCoindex varZagrebCoindex = new ZagrebCoindex();
        Assertions.assertEquals(varZagrebCoindex.calculate(peterson).get(0),"");
    }

    @Test
    public void testM3Final() {
        M3Final varM3Final = new M3Final();
        Assertions.assertEquals(varM3Final.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testISIBound() {
        ISIBound varISIBound = new ISIBound();
        Assertions.assertEquals(varISIBound.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testReciprocalDegreeDistance() {
        ReciprocalDegreeDistance varReciprocalDegreeDistance = new ReciprocalDegreeDistance();
        Assertions.assertEquals(varReciprocalDegreeDistance.calculate(peterson),0);
    }

    @Test
    public void testPathZagrebIndex() {
        PathZagrebIndex varPathZagrebIndex = new PathZagrebIndex();
        Assertions.assertEquals(varPathZagrebIndex.calculate(peterson).get(0),"");
    }

    @Test
    public void testWeightedWienerIndex() {
        WeightedWienerIndex varWeightedWienerIndex = new WeightedWienerIndex();
    }

    @Test
    public void testEdgesDegreesList() {
        EdgesDegreesList varEdgesDegreesList = new EdgesDegreesList();
        Assertions.assertEquals(varEdgesDegreesList.calculate(peterson).get(0),0);
    }

    @Test
    public void testZagrebCoindexSelectedEdges() {
        ZagrebCoindexSelectedEdges varZagrebCoindexSelectedEdges = new ZagrebCoindexSelectedEdges();
        Assertions.assertEquals(varZagrebCoindexSelectedEdges.calculate(peterson).get(0),"");
    }

    @Test
    public void testEM1UpperBound() {
        EM1UpperBound varEM1UpperBound = new EM1UpperBound();
        Assertions.assertEquals(varEM1UpperBound.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testInde() {
        Inde varInde = new Inde();
        Assertions.assertEquals(varInde.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testAGIndex() {
        AGIndex varAGIndex = new AGIndex();
        Assertions.assertEquals(varAGIndex.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testIncrementalZagrebIndexSelectedEdges() {
        IncrementalZagrebIndexSelectedEdges varIncrementalZagrebIndexSelectedEdges = new IncrementalZagrebIndexSelectedEdges();
        Assertions.assertEquals(varIncrementalZagrebIndexSelectedEdges.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testAdditiveHarary() {
        AdditiveHarary varAdditiveHarary = new AdditiveHarary();
        Assertions.assertEquals(varAdditiveHarary.calculate(peterson),0);
    }

    @Test
    public void testFinalNewM2Lower() {
        FinalNewM2Lower varFinalNewM2Lower = new FinalNewM2Lower();
        Assertions.assertEquals(varFinalNewM2Lower.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testECIConjecture() {
        ECIConjecture varECIConjecture = new ECIConjecture();
        Assertions.assertEquals(varECIConjecture.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testPB() {
        PB varPB = new PB();
        Assertions.assertEquals(varPB.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testWienerPolarityIndex() {
        WienerPolarityIndex varWienerPolarityIndex = new WienerPolarityIndex();
        Assertions.assertEquals(varWienerPolarityIndex.calculate(peterson),0);
    }

    @Test
    public void testSDD() {
        SDD varSDD = new SDD();
        Assertions.assertEquals(varSDD.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testHyperWienerIndex() {
        HyperWienerIndex varHyperWienerIndex = new HyperWienerIndex();
        Assertions.assertEquals(varHyperWienerIndex.calculate(peterson),0);
    }

    @Test
    public void testGutmanIndex() {
        GutmanIndex varGutmanIndex = new GutmanIndex();
        Assertions.assertEquals(varGutmanIndex.calculate(peterson),0);
    }

    @Test
    public void testEM1LowerBound() {
        EM1LowerBound varEM1LowerBound = new EM1LowerBound();
        Assertions.assertEquals(varEM1LowerBound.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testConnectiveEccentricIndex() {
        ConnectiveEccentricIndex varConnectiveEccentricIndex = new ConnectiveEccentricIndex();
        Assertions.assertEquals(varConnectiveEccentricIndex.calculate(peterson),"");
    }

    @Test
    public void testInverseSum() {
        InverseSum varInverseSum = new InverseSum();
        Assertions.assertEquals(varInverseSum.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testM3CompIndCoindConjecture() {
        M3CompIndCoindConjecture varM3CompIndCoindConjecture = new M3CompIndCoindConjecture();
        Assertions.assertEquals(varM3CompIndCoindConjecture.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testHarary() {
        Harary varHarary = new Harary();
        Assertions.assertEquals(varHarary.calculate(peterson),0);
    }

    @Test
    public void testZagrebIndex() {
        ZagrebIndex varZagrebIndex = new ZagrebIndex();
//        Assertions.assertEquals(varZagrebIndex.calculate(peterson).get(0),"");
    }

    @Test
    public void testInverseDegree() {
        InverseDegree varInverseDegree = new InverseDegree();
        Assertions.assertEquals(varInverseDegree.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testExp() {
        Exp varExp = new Exp();
        Assertions.assertEquals(varExp.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testModifiedFirstZagrebConnectionIndex() {
        ModifiedFirstZagrebConnectionIndex varModifiedFirstZagrebConnectionIndex = new ModifiedFirstZagrebConnectionIndex();
        Assertions.assertEquals(varModifiedFirstZagrebConnectionIndex.calculate(peterson).get(0),"");
    }

    @Test
    public void testIncrementalZagrebCoindex() {
        IncrementalZagrebCoindex varIncrementalZagrebCoindex = new IncrementalZagrebCoindex();
        Assertions.assertEquals(varIncrementalZagrebCoindex.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testEccentricityComplexityIndex() {
        EccentricityComplexityIndex varEccentricityComplexityIndex = new EccentricityComplexityIndex();
        Assertions.assertEquals(varEccentricityComplexityIndex.calculate(peterson),0);
    }

    @Test
    public void testEdgeDegree() {
        EdgeDegree varEdgeDegree = new EdgeDegree();
        Assertions.assertEquals(varEdgeDegree.calculate(peterson),0);
    }

    @Test
    public void testConnectivityEccentricityIndex() {
        ConnectivityEccentricityIndex varConnectivityEccentricityIndex = new ConnectivityEccentricityIndex();
        Assertions.assertEquals(varConnectivityEccentricityIndex.calculate(peterson),0);
    }

    @Test
    public void testEccentricityConnectivityIndex() {
        EccentricConnectiveIndex varEccentricityConnectivityIndex = new EccentricConnectiveIndex();
        Assertions.assertEquals(varEccentricityConnectivityIndex.calculate(peterson),0);
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
        Assertions.assertEquals(varVariableZagrebIndex.calculate(peterson).get(0),"");
    }

    @Test
    public void testTotalEccentricityIndex() {
        TotalEccentricityIndex varTotalEccentricityIndex = new TotalEccentricityIndex();
        Assertions.assertEquals(varTotalEccentricityIndex.calculate(peterson),0);
    }

    @Test
    public void testHyperZagrebIndex() {
        HyperZagrebIndex varHyperZagrebIndex = new HyperZagrebIndex();
        Assertions.assertEquals(varHyperZagrebIndex.calculate(peterson).get(0),"");
    }

    @Test
    public void testM3BoundConjecture() {
        M3BoundConjecture varM3BoundConjecture = new M3BoundConjecture();
        Assertions.assertEquals(varM3BoundConjecture.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testHyperCheck() {
        HyperCheck varHyperCheck = new HyperCheck();
        Assertions.assertEquals(varHyperCheck.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testAllCheck() {
        AllCheck varAllCheck = new AllCheck();
        Assertions.assertEquals(varAllCheck.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testEM1Lower() {
        EM1Lower varEM1Lower = new EM1Lower();
        Assertions.assertEquals(varEM1Lower.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testSecondMixZagrebIndex() {
        SecondMixZagrebIndex varSecondMixZagrebIndex = new SecondMixZagrebIndex();
        Assertions.assertEquals(varSecondMixZagrebIndex.calculate(peterson).get(0),"");
    }

    @Test
    public void testLanzhou() {
        Lanzhou varLanzhou = new Lanzhou();
        Assertions.assertEquals(varLanzhou.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testZagrebIndexSelectedEdges() {
        ZagrebIndexSelectedEdges varZagrebIndexSelectedEdges = new ZagrebIndexSelectedEdges();
        Assertions.assertEquals(varZagrebIndexSelectedEdges.calculate(peterson).get(0),"");
    }

    @Test
    public void testMWienerIndex() {
        MWienerIndex varMWienerIndex = new MWienerIndex();
        Assertions.assertEquals(varMWienerIndex.calculate(peterson),0);
    }

    @Test
    public void testEM2UpperBound() {
        EM2UpperBound varEM2UpperBound = new EM2UpperBound();
        Assertions.assertEquals(varEM2UpperBound.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testIncrementalVariableZagrebIndex() {
        IncrementalVariableZagrebIndex varIncrementalVariableZagrebIndex = new IncrementalVariableZagrebIndex();
        Assertions.assertEquals(varIncrementalVariableZagrebIndex.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testIncrementalZagrebCoindexSelectedEdges() {
        IncrementalZagrebCoindexSelectedEdges varIncrementalZagrebCoindexSelectedEdges = new IncrementalZagrebCoindexSelectedEdges();
        Assertions.assertEquals(varIncrementalZagrebCoindexSelectedEdges.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testEM2LowerBound() {
        EM2LowerBound varEM2LowerBound = new EM2LowerBound();
        Assertions.assertEquals(varEM2LowerBound.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testcomparision() {
        comparision varcomparision = new comparision();
        Assertions.assertEquals(varcomparision.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testExponential() {
        Exponential varExponential = new Exponential();
        Assertions.assertEquals(varExponential.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testRandicIndex() {
        RandicIndex varRandicIndex = new RandicIndex();
        Assertions.assertEquals(varRandicIndex.calculate(peterson).get(0),"");
    }

    @Test
    public void testAutographixConj() {
        AutographixConj varAutographixConj = new AutographixConj();
        Assertions.assertEquals(varAutographixConj.calculate(peterson).get(0),"");
    }

    @Test
    public void testSumConnectivityIndex() {
        SumConnectivityIndex varSumConnectivityIndex = new SumConnectivityIndex();
        Assertions.assertEquals(varSumConnectivityIndex.calculate(peterson).get(0),"");
    }

    @Test
    public void testWinerPolarity() {
        WinerPolarity varWinerPolarity = new WinerPolarity();
        Assertions.assertEquals(varWinerPolarity.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testMere() {
        Mere varMere = new Mere();
        Assertions.assertEquals(varMere.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testIncrementalZagrebIndex() {
        IncrementalZagrebIndex varIncrementalZagrebIndex = new IncrementalZagrebIndex();
        Assertions.assertEquals(varIncrementalZagrebIndex.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testWienerPolarity() {
        WienerPolarity varWienerPolarity = new WienerPolarity();
        Assertions.assertEquals(varWienerPolarity.calculate(peterson),0);
    }

    @Test
    public void testThirdZagrebIndex() {
        ThirdZagrebIndex varThirdZagrebIndex = new ThirdZagrebIndex();
        Assertions.assertEquals(varThirdZagrebIndex.calculate(peterson).get(0),"");
    }

    @Test
    public void testISIUpper() {
        ISIUpper varISIUpper = new ISIUpper();
        Assertions.assertEquals(varISIUpper.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testMultiplicativeHarary() {
        MultiplicativeHarary varMultiplicativeHarary = new MultiplicativeHarary();
        Assertions.assertEquals(varMultiplicativeHarary.calculate(peterson),0);
    }

    @Test
    public void testBalabanIndex() {
        BalabanIndex varBalabanIndex = new BalabanIndex();
        Assertions.assertEquals(varBalabanIndex.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testMostar() {
        Mostar varMostar = new Mostar();
        Assertions.assertEquals(varMostar.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testMaxCliqueExtension() {
        MaxCliqueExtension varMaxCliqueExtension = new MaxCliqueExtension();
        Assertions.assertEquals(varMaxCliqueExtension.calculate(peterson).get(0),new SubGraph());
    }

    @Test
    public void testKF_Wiener() {
        KF_Wiener varKF_Wiener = new KF_Wiener();
        Assertions.assertEquals(varKF_Wiener.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testLEL_vs_KF() {
        LEL_vs_KF varLEL_vs_KF = new LEL_vs_KF();
        Assertions.assertEquals(varLEL_vs_KF.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testMixSignlessLaplacianEnergy() {
        MixSignlessLaplacianEnergy varMixSignlessLaplacianEnergy = new MixSignlessLaplacianEnergy();
        Assertions.assertEquals(varMixSignlessLaplacianEnergy.calculate(peterson).get(0),"");
    }

    @Test
    public void testSignlessLaplacianEstrada() {
        SignlessLaplacianEstrada varSignlessLaplacianEstrada = new SignlessLaplacianEstrada();
        Assertions.assertEquals(varSignlessLaplacianEstrada.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testCograph() {
        Cograph varCograph = new Cograph();
        Assertions.assertEquals(varCograph.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testEnergy() {
        Energy varEnergy = new Energy();
//        Assertions.assertEquals(varEnergy.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testLaplacianEstrada() {
        LaplacianEstrada varLaplacianEstrada = new LaplacianEstrada();
        Assertions.assertEquals(varLaplacianEstrada.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testAllEnergies() {
        AllEnergies varAllEnergies = new AllEnergies();
        Assertions.assertEquals(varAllEnergies.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testConjecture() {
        Conjecture varConjecture = new Conjecture();
        Assertions.assertEquals(varConjecture.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testUpperBounds() {
        UpperBounds varUpperBounds = new UpperBounds();
        Assertions.assertEquals(varUpperBounds.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testLinear() {
        Linear varLinear = new Linear();
        Assertions.assertEquals(varLinear.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testEstrada() {
        Estrada varEstrada = new Estrada();
        Assertions.assertEquals(varEstrada.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testComplement() {
        Complement varComplement = new Complement();
        Assertions.assertEquals(varComplement.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testNewLowerBounds() {
        NewLowerBounds varNewLowerBounds = new NewLowerBounds();
    }

    @Test
    public void testDominationNumber() {
        DominationNumber varDominationNumber = new DominationNumber();
        Assertions.assertEquals(varDominationNumber.calculate(peterson),0);
    }

    @Test
    public void testAllPairShortestPathsWithoutWeight() {
        AllPairShortestPathsWithoutWeight varAllPairShortestPathsWithoutWeight = new AllPairShortestPathsWithoutWeight();
        Assertions.assertEquals(varAllPairShortestPathsWithoutWeight.calculate(peterson).iterator().next().get(0),0);
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
        Assertions.assertEquals(varNumOfConnectedComponents.calculate(peterson),0);
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
        Assertions.assertEquals(varVerticesDegreesList.calculate(peterson).get(0),0);
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
        Assertions.assertEquals(varSubTreeCounting.calculate(peterson).iterator().next().get(0),0);
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
        Assertions.assertEquals(varNumOfStars.calculate(peterson),0);
        Assertions.assertEquals(varNumOfStars.calculate(circle4),0);
        Assertions.assertEquals(varNumOfStars.calculate(circle5),0);
        Assertions.assertEquals(varNumOfStars.calculate(complete4), 0);
        Assertions.assertEquals(varNumOfStars.calculate(complete5), 0);
    }

    @Test
    public void testMaxOfIndSets() {
        MaxOfIndSets varMaxOfIndSets = new MaxOfIndSets();
        Assertions.assertEquals(varMaxOfIndSets.calculate(peterson),0);
    }

    @Test
    public void testAdjacencyMatrix() {
        SpectrumOfAdjacencyMatrix varAdjacencyMatrix = new SpectrumOfAdjacencyMatrix();
        Assertions.assertEquals(varAdjacencyMatrix.calculate(peterson).get(0),"");
    }

    @Test
    public void testNumOfIndSets() {
        NumOfIndSets varNumOfIndSets = new NumOfIndSets();
        Assertions.assertEquals(varNumOfIndSets.calculate(peterson),0);
    }

    @Test
    public void testPathsofLengthTwo() {
        PathsofLengthTwo varPathsofLengthTwo = new PathsofLengthTwo();
        Assertions.assertEquals(varPathsofLengthTwo.calculate(peterson),30);//???
        Assertions.assertEquals(varPathsofLengthTwo.calculate(circle4),4);
        Assertions.assertEquals(varPathsofLengthTwo.calculate(circle5),5);
        Assertions.assertEquals(varPathsofLengthTwo.calculate(complete4), 15);
        Assertions.assertEquals(varPathsofLengthTwo.calculate(complete5), 45);
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
        Assertions.assertEquals(varNumOfQuadrangle.calculate(complete5), 12);
    }

    @Test
    public void testTotalNumOfStars() {
        TotalNumOfStars varTotalNumOfStars = new TotalNumOfStars();
        Assertions.assertEquals(varTotalNumOfStars.calculate(peterson).get(0),"");
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
        Assertions.assertEquals(varKConnected.calculate(peterson),0);
    }

    @Test
    public void testSignlessLaplacianEnergy() {
        SignlessLaplacianEnergy varSignlessLaplacianEnergy = new SignlessLaplacianEnergy();
        Assertions.assertEquals(varSignlessLaplacianEnergy.calculate(peterson),"");
    }

    @Test
    public void testEigenValues() {
        EigenValues varEigenValues = new EigenValues();
        Assertions.assertEquals(varEigenValues.calculate(peterson).get(0),"");
    }

    @Test
    public void testLaplacianEnergyLike() {
        LaplacianEnergyLike varLaplacianEnergyLike = new LaplacianEnergyLike();
        Assertions.assertEquals(varLaplacianEnergyLike.calculate(peterson),"");
    }

    @Test
    public void testEccentricityMatrixOfGraph() {
        EccentricityMatrixOfGraph varEccentricityMatrixOfGraph = new EccentricityMatrixOfGraph();
        Assertions.assertEquals(varEccentricityMatrixOfGraph.calculate(peterson).get(0),"");
    }

    @Test
    public void testLaplacianEnergy() {
        LaplacianEnergy varLaplacianEnergy = new LaplacianEnergy();
        Assertions.assertEquals(varLaplacianEnergy.calculate(peterson),"");
    }

    @Test
    public void testLaplacianOfGraph() {
        LaplacianOfGraph varLaplacianOfGraph = new LaplacianOfGraph();
        Assertions.assertEquals(varLaplacianOfGraph.calculate(peterson).get(0),"");
    }

    @Test
    public void testKirchhoffIndex() {
        KirchhoffIndex varKirchhoffIndex = new KirchhoffIndex();
        Assertions.assertEquals(varKirchhoffIndex.calculate(peterson),"");
    }

    @Test
    public void testSignlessLaplacianOfGraph() {
        SignlessLaplacianOfGraph varSignlessLaplacianOfGraph = new SignlessLaplacianOfGraph();
        Assertions.assertEquals(varSignlessLaplacianOfGraph.calculate(peterson).get(0),"");
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
        Assertions.assertEquals(varMaxMatchingExtension.calculate(peterson).get(0),new Object());
    }

    @Test
    public void testHamiltonianCycleExtension() {
        HamiltonianCycleExtension varHamiltonianCycleExtension = new HamiltonianCycleExtension();
        Assertions.assertEquals(varHamiltonianCycleExtension.calculate(peterson),new SubGraph());
    }

    @Test
    public void testHamiltonianPathExtension() {
        HamiltonianPathExtension varHamiltonianPathExtension = new HamiltonianPathExtension();
        Assertions.assertEquals(varHamiltonianPathExtension.calculate(peterson),new SubGraph());
    }

    @Test
    public void testSzegedIndex() {
        SzegedIndex varSzegedIndex = new SzegedIndex();
        Assertions.assertEquals(varSzegedIndex.calculate(peterson),0);
    }

    @Test
    public void testAllEccen() {
        AllEccen varAllEccen = new AllEccen();
        Assertions.assertEquals(varAllEccen.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testRevisedSzegedIndex() {
        RevisedSzegedIndex varRevisedSzegedIndex = new RevisedSzegedIndex();
        Assertions.assertEquals(varRevisedSzegedIndex.calculate(peterson),0);
    }

    @Test
    public void testMostarIndex() {
        MostarIndex varMostarIndex = new MostarIndex();
        Assertions.assertEquals(varMostarIndex.calculate(peterson),0);
    }

    @Test
    public void testMerrifieldSimmons() {
        MerrifieldSimmons varMerrifieldSimmons = new MerrifieldSimmons();
        Assertions.assertEquals(varMerrifieldSimmons.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testWeightedPiIndex() {
        WeightedPiIndex varWeightedPiIndex = new WeightedPiIndex();
        Assertions.assertEquals(varWeightedPiIndex.calculate(peterson),0);
    }

    @Test
    public void testPeripheralVerticesCount() {
        PeripheralVerticesCount varPeripheralVerticesCount = new PeripheralVerticesCount();
        Assertions.assertEquals(varPeripheralVerticesCount.calculate(peterson),0);
    }

    @Test
    public void testEccentricityEnergy() {
        EccentricityEnergy varEccentricityEnergy = new EccentricityEnergy();
        Assertions.assertEquals(varEccentricityEnergy.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testWeightedSzegedIndex() {
        WeightedSzegedIndex varWeightedSzegedIndex = new WeightedSzegedIndex();
        Assertions.assertEquals(varWeightedSzegedIndex.calculate(peterson),0);
    }

    @Test
    public void testPeripheralWienerIndex() {
        PeripheralWienerIndex varPeripheralWienerIndex = new PeripheralWienerIndex();
        Assertions.assertEquals(varPeripheralWienerIndex.calculate(peterson),0);
    }

    @Test
    public void testPeripheralVertices() {
        PeripheralVertices varPeripheralVertices = new PeripheralVertices();
    }

    @Test
    public void testEccentricity() {
        Eccentricity varEccentricity = new Eccentricity();
        Assertions.assertEquals(varEccentricity.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testPiIndex() {
        PiIndex varPiIndex = new PiIndex();
        Assertions.assertEquals(varPiIndex.calculate(peterson),0);
    }
}