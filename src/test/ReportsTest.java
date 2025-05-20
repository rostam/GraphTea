package test;

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
import graphtea.graph.graph.SubGraph;
import org.junit.Assert;
import org.junit.Test;

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
        Assert.assertEquals(3, hgc.calculate(circle3).intValue());
        Assert.assertEquals(2, hgc.calculate(circle4).intValue());
        Assert.assertEquals(3, hgc.calculate(circle5).intValue());
        Assert.assertEquals(4, hgc.calculate(complete4).intValue());
        Assert.assertEquals(5, hgc.calculate(complete5).intValue());
        Assert.assertEquals(3, hgc.calculate(peterson).intValue());
    }

    @Test
    public void testMaxCliqueNumber() {
        MaxCliqueSize mcs =  new MaxCliqueSize();
        Assert.assertEquals(3, mcs.calculate(circle3).intValue());
        Assert.assertEquals(2, mcs.calculate(circle4).intValue());
        Assert.assertEquals(2, mcs.calculate(circle5).intValue());
        Assert.assertEquals(4, mcs.calculate(complete4).intValue());
        Assert.assertEquals(5, mcs.calculate(complete5).intValue());
        Assert.assertEquals(2, mcs.calculate(peterson).intValue());
        LoadGraph6Format loadGraph6Format = new LoadGraph6Format();
        GraphModel g = loadGraph6Format.read(new File("examples/example.g6"));
        Assert.assertEquals(3, mcs.calculate(g).intValue());
    }

    @Test
    public void testChromaticNumber() {
        ChromaticNumber varChromaticNumber = new ChromaticNumber();
        Assert.assertEquals(3, varChromaticNumber.calculate(peterson).intValue());
    }

    @Test
    public void testMaxCut() {
        MaxCut varMaxCut = new MaxCut();
        Assert.assertTrue(varMaxCut.calculate(peterson) <= 12);
    }

    @Test
    public void testMaxIndependentSetReport() {
        MaxIndependentSetReport varMaxIndependentSetReport = new MaxIndependentSetReport();
        Assert.assertEquals(new SubGraph(), varMaxIndependentSetReport.calculate(peterson).get(0));
    }

    @Test
    public void testWienerDiameterReport() {
        WienerDiameterReport varWienerDiameterReport = new WienerDiameterReport();
        Assert.assertEquals(75, varWienerDiameterReport.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testColoringReport() {
        ColoringReport varColoringReport = new ColoringReport();
    }

    @Test
    public void testRandomMatching() {
        RandomMatching varRandomMatching = new RandomMatching();
        Assert.assertEquals(new Object(), varRandomMatching.calculate(peterson).get(0));
    }

    @Test
    public void testMSTPrimExtension() {
        MSTPrimExtension varMSTPrimExtension = new MSTPrimExtension();
        Assert.assertEquals(new SubGraph(), varMSTPrimExtension.calculate(peterson));
    }

    @Test
    public void testDegreeDistance() {
        DegreeDistance varDegreeDistance = new DegreeDistance();
        Assert.assertEquals(0, varDegreeDistance.calculate(peterson).intValue());
    }

    @Test
    public void testHarmonicIndex() {
        HarmonicIndex varHarmonicIndex = new HarmonicIndex();
        Assert.assertEquals("", varHarmonicIndex.calculate(peterson).get(0));
    }

    @Test
    public void testEccentricConnectivityIndex() {
        EccentricConnectiveIndex varEccentricConnectivityIndex = new EccentricConnectiveIndex();
        Assert.assertEquals(0, varEccentricConnectivityIndex.calculate(peterson).intValue());
    }

    @Test
    public void testZagrebCoindex() {
        ZagrebCoindex varZagrebCoindex = new ZagrebCoindex();
        Assert.assertEquals("", varZagrebCoindex.calculate(peterson).get(0));
    }

    @Test
    public void testM3Final() {
        M3Final varM3Final = new M3Final();
        Assert.assertEquals(0, varM3Final.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testISIBound() {
        ISIBound varISIBound = new ISIBound();
        Assert.assertEquals(0, varISIBound.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testReciprocalDegreeDistance() {
        ReciprocalDegreeDistance varReciprocalDegreeDistance = new ReciprocalDegreeDistance();
        Assert.assertEquals(0, varReciprocalDegreeDistance.calculate(peterson).intValue());
    }

    @Test
    public void testPathZagrebIndex() {
        PathZagrebIndex varPathZagrebIndex = new PathZagrebIndex();
        Assert.assertEquals("", varPathZagrebIndex.calculate(peterson).get(0));
    }

    @Test
    public void testWeightedWienerIndex() {
        WeightedWienerIndex varWeightedWienerIndex = new WeightedWienerIndex();
    }

    @Test
    public void testZagrebCoindexSelectedEdges() {
        ZagrebCoindexSelectedEdges varZagrebCoindexSelectedEdges = new ZagrebCoindexSelectedEdges();
        Assert.assertEquals("", varZagrebCoindexSelectedEdges.calculate(peterson).get(0));
    }

    @Test
    public void testEM1UpperBound() {
        EM1UpperBound varEM1UpperBound = new EM1UpperBound();
        Assert.assertEquals(0, varEM1UpperBound.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testInde() {
        Inde varInde = new Inde();
        Assert.assertEquals(0, varInde.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testAGIndex() {
        AGIndex varAGIndex = new AGIndex();
        Assert.assertEquals(0, varAGIndex.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testIncrementalZagrebIndexSelectedEdges() {
        IncrementalZagrebIndexSelectedEdges varIncrementalZagrebIndexSelectedEdges = new IncrementalZagrebIndexSelectedEdges();
        Assert.assertEquals(0, varIncrementalZagrebIndexSelectedEdges.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testAdditiveHarary() {
        AdditiveHarary varAdditiveHarary = new AdditiveHarary();
        Assert.assertEquals(0, varAdditiveHarary.calculate(peterson).intValue());
    }

    @Test
    public void testFinalNewM2Lower() {
        FinalNewM2Lower varFinalNewM2Lower = new FinalNewM2Lower();
        Assert.assertEquals(0, varFinalNewM2Lower.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testECIConjecture() {
        ECIConjecture varECIConjecture = new ECIConjecture();
        Assert.assertEquals(0, varECIConjecture.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testPB() {
        PB varPB = new PB();
        Assert.assertEquals(0, varPB.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testWienerPolarityIndex() {
        WienerPolarityIndex varWienerPolarityIndex = new WienerPolarityIndex();
        Assert.assertEquals(0, varWienerPolarityIndex.calculate(peterson).intValue());
    }

    @Test
    public void testSDD() {
        SDD varSDD = new SDD();
        Assert.assertEquals(0, varSDD.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testHyperWienerIndex() {
        HyperWienerIndex varHyperWienerIndex = new HyperWienerIndex();
        Assert.assertEquals(0, varHyperWienerIndex.calculate(peterson).intValue());
    }

    @Test
    public void testGutmanIndex() {
        GutmanIndex varGutmanIndex = new GutmanIndex();
        Assert.assertEquals(0, varGutmanIndex.calculate(peterson).intValue());
    }

    @Test
    public void testEM1LowerBound() {
        EM1LowerBound varEM1LowerBound = new EM1LowerBound();
        Assert.assertEquals(0, varEM1LowerBound.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testConnectiveEccentricIndex() {
        ConnectiveEccentricIndex varConnectiveEccentricIndex = new ConnectiveEccentricIndex();
        Assert.assertEquals(0, (int) varConnectiveEccentricIndex.calculate(peterson).doubleValue());
    }

    @Test
    public void testInverseSum() {
        InverseSum varInverseSum = new InverseSum();
        Assert.assertEquals(0, varInverseSum.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testM3CompIndCoindConjecture() {
        M3CompIndCoindConjecture varM3CompIndCoindConjecture = new M3CompIndCoindConjecture();
        Assert.assertEquals(0, varM3CompIndCoindConjecture.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testHarary() {
        Harary varHarary = new Harary();
        Assert.assertEquals(0, varHarary.calculate(peterson).intValue());
    }

    @Test
    public void testZagrebIndex() {
        ZagrebIndex varZagrebIndex = new ZagrebIndex();
//        Assert.assertEquals(varZagrebIndex.calculate(peterson).get(0),"");
    }

    @Test
    public void testInverseDegree() {
        InverseDegree varInverseDegree = new InverseDegree();
        Assert.assertEquals(0, varInverseDegree.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testExp() {
        Exp varExp = new Exp();
        Assert.assertEquals(0, varExp.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testModifiedFirstZagrebConnectionIndex() {
        ModifiedFirstZagrebConnectionIndex varModifiedFirstZagrebConnectionIndex = new ModifiedFirstZagrebConnectionIndex();
        Assert.assertEquals("", varModifiedFirstZagrebConnectionIndex.calculate(peterson).get(0));
    }

    @Test
    public void testIncrementalZagrebCoindex() {
        IncrementalZagrebCoindex varIncrementalZagrebCoindex = new IncrementalZagrebCoindex();
        Assert.assertEquals(0, varIncrementalZagrebCoindex.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testEccentricityComplexityIndex() {
        EccentricityComplexityIndex varEccentricityComplexityIndex = new EccentricityComplexityIndex();
        Assert.assertEquals(0, varEccentricityComplexityIndex.calculate(peterson).intValue());
    }

    @Test
    public void testEdgeDegree() {
        EdgeDegree varEdgeDegree = new EdgeDegree();
        Assert.assertEquals(0, varEdgeDegree.calculate(peterson).intValue());
    }

    @Test
    public void testConnectivityEccentricityIndex() {
        ConnectivityEccentricityIndex varConnectivityEccentricityIndex = new ConnectivityEccentricityIndex();
        Assert.assertEquals(0, varConnectivityEccentricityIndex.calculate(peterson).intValue());
    }

    @Test
    public void testEccentricityConnectivityIndex() {
        EccentricConnectiveIndex varEccentricityConnectivityIndex = new EccentricConnectiveIndex();
        Assert.assertEquals(0, varEccentricityConnectivityIndex.calculate(peterson).intValue());
    }

    @Test
    public void testWienerIndex() {
        WienerIndex varWienerIndex = new WienerIndex();
        Assert.assertEquals(3, varWienerIndex.calculate(circle3).intValue());
        Assert.assertEquals(8, varWienerIndex.calculate(circle4).intValue());
        Assert.assertEquals(15, varWienerIndex.calculate(circle5).intValue());
        Assert.assertEquals(6, varWienerIndex.calculate(complete4).intValue());
        Assert.assertEquals(10, varWienerIndex.calculate(complete5).intValue());
        Assert.assertEquals(18, varWienerIndex.calculate(antiprism3).intValue());
    }

    @Test
    public void testVariableZagrebIndex() {
        VariableZagrebIndex varVariableZagrebIndex = new VariableZagrebIndex();
        Assert.assertEquals("", varVariableZagrebIndex.calculate(peterson).get(0));
    }

    @Test
    public void testTotalEccentricityIndex() {
        TotalEccentricityIndex varTotalEccentricityIndex = new TotalEccentricityIndex();
        Assert.assertEquals(0, varTotalEccentricityIndex.calculate(peterson).intValue());
    }

    @Test
    public void testHyperZagrebIndex() {
        HyperZagrebIndex varHyperZagrebIndex = new HyperZagrebIndex();
        Assert.assertEquals("", varHyperZagrebIndex.calculate(peterson).get(0));
    }

    @Test
    public void testM3BoundConjecture() {
        M3BoundConjecture varM3BoundConjecture = new M3BoundConjecture();
        Assert.assertEquals(0, varM3BoundConjecture.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testHyperCheck() {
        HyperCheck varHyperCheck = new HyperCheck();
        Assert.assertEquals(0, varHyperCheck.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testAllCheck() {
        AllCheck varAllCheck = new AllCheck();
        Assert.assertEquals(0, varAllCheck.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testEM1Lower() {
        EM1Lower varEM1Lower = new EM1Lower();
        Assert.assertEquals(0, varEM1Lower.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testSecondMixZagrebIndex() {
        SecondMixZagrebIndex varSecondMixZagrebIndex = new SecondMixZagrebIndex();
        Assert.assertEquals("", varSecondMixZagrebIndex.calculate(peterson).get(0));
    }

    @Test
    public void testLanzhou() {
        Lanzhou varLanzhou = new Lanzhou();
        Assert.assertEquals(0, varLanzhou.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testZagrebIndexSelectedEdges() {
        ZagrebIndexSelectedEdges varZagrebIndexSelectedEdges = new ZagrebIndexSelectedEdges();
        Assert.assertEquals("", varZagrebIndexSelectedEdges.calculate(peterson).get(0));
    }

    @Test
    public void testMWienerIndex() {
        MWienerIndex varMWienerIndex = new MWienerIndex();
        Assert.assertEquals(0, varMWienerIndex.calculate(peterson).intValue());
    }

    @Test
    public void testEM2UpperBound() {
        EM2UpperBound varEM2UpperBound = new EM2UpperBound();
        Assert.assertEquals(0, varEM2UpperBound.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testIncrementalVariableZagrebIndex() {
        IncrementalVariableZagrebIndex varIncrementalVariableZagrebIndex = new IncrementalVariableZagrebIndex();
        Assert.assertEquals(0, varIncrementalVariableZagrebIndex.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testIncrementalZagrebCoindexSelectedEdges() {
        IncrementalZagrebCoindexSelectedEdges varIncrementalZagrebCoindexSelectedEdges = new IncrementalZagrebCoindexSelectedEdges();
        Assert.assertEquals(0, varIncrementalZagrebCoindexSelectedEdges.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testEM2LowerBound() {
        EM2LowerBound varEM2LowerBound = new EM2LowerBound();
        Assert.assertEquals(0, varEM2LowerBound.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testcomparision() {
        comparision varcomparision = new comparision();
        Assert.assertEquals(0, varcomparision.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testExponential() {
        Exponential varExponential = new Exponential();
        Assert.assertEquals(0, varExponential.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testRandicIndex() {
        RandicIndex varRandicIndex = new RandicIndex();
        Assert.assertEquals("", varRandicIndex.calculate(peterson).get(0));
    }

    @Test
    public void testAutographixConj() {
        AutographixConj varAutographixConj = new AutographixConj();
        Assert.assertEquals("", varAutographixConj.calculate(peterson).get(0));
    }

    @Test
    public void testSumConnectivityIndex() {
        SumConnectivityIndex varSumConnectivityIndex = new SumConnectivityIndex();
        Assert.assertEquals("", varSumConnectivityIndex.calculate(peterson).get(0));
    }

    @Test
    public void testWinerPolarity() {
        WinerPolarity varWinerPolarity = new WinerPolarity();
        Assert.assertEquals(0, varWinerPolarity.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testMere() {
        Mere varMere = new Mere();
        Assert.assertEquals(0, varMere.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testIncrementalZagrebIndex() {
        IncrementalZagrebIndex varIncrementalZagrebIndex = new IncrementalZagrebIndex();
        Assert.assertEquals(0, varIncrementalZagrebIndex.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testWienerPolarity() {
        WienerPolarity varWienerPolarity = new WienerPolarity();
        Assert.assertEquals(0, varWienerPolarity.calculate(peterson).intValue());
    }

    @Test
    public void testThirdZagrebIndex() {
        ThirdZagrebIndex varThirdZagrebIndex = new ThirdZagrebIndex();
        Assert.assertEquals("", varThirdZagrebIndex.calculate(peterson).get(0));
    }

    @Test
    public void testISIUpper() {
        ISIUpper varISIUpper = new ISIUpper();
        Assert.assertEquals(0, varISIUpper.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testMultiplicativeHarary() {
        MultiplicativeHarary varMultiplicativeHarary = new MultiplicativeHarary();
        Assert.assertEquals(0, varMultiplicativeHarary.calculate(peterson).intValue());
    }

    @Test
    public void testBalabanIndex() {
        BalabanIndex varBalabanIndex = new BalabanIndex();
        Assert.assertEquals(0, varBalabanIndex.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testMostar() {
        Mostar varMostar = new Mostar();
        Assert.assertEquals(0, varMostar.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testMaxCliqueExtension() {
        MaxCliqueExtension varMaxCliqueExtension = new MaxCliqueExtension();
        Assert.assertEquals(new SubGraph(), varMaxCliqueExtension.calculate(peterson).get(0));
    }

    @Test
    public void testKF_Wiener() {
        KF_Wiener varKF_Wiener = new KF_Wiener();
        Assert.assertEquals(0, varKF_Wiener.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testLEL_vs_KF() {
        LEL_vs_KF varLEL_vs_KF = new LEL_vs_KF();
        Assert.assertEquals(0, varLEL_vs_KF.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testMixSignlessLaplacianEnergy() {
        MixSignlessLaplacianEnergy varMixSignlessLaplacianEnergy = new MixSignlessLaplacianEnergy();
        Assert.assertEquals("", varMixSignlessLaplacianEnergy.calculate(peterson).get(0));
    }

    @Test
    public void testSignlessLaplacianEstrada() {
        SignlessLaplacianEstrada varSignlessLaplacianEstrada = new SignlessLaplacianEstrada();
        Assert.assertEquals(0, varSignlessLaplacianEstrada.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testCograph() {
        Cograph varCograph = new Cograph();
        Assert.assertEquals(0, varCograph.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testEnergy() {
        Energy varEnergy = new Energy();
//        Assert.assertEquals(varEnergy.calculate(peterson).iterator().next().get(0),0);
    }

    @Test
    public void testLaplacianEstrada() {
        LaplacianEstrada varLaplacianEstrada = new LaplacianEstrada();
        Assert.assertEquals(0, varLaplacianEstrada.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testAllEnergies() {
        AllEnergies varAllEnergies = new AllEnergies();
        Assert.assertEquals(0, varAllEnergies.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testConjecture() {
        Conjecture varConjecture = new Conjecture();
        Assert.assertEquals(0, varConjecture.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testUpperBounds() {
        UpperBounds varUpperBounds = new UpperBounds();
        Assert.assertEquals(0, varUpperBounds.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testLinear() {
        Linear varLinear = new Linear();
        Assert.assertEquals(0, varLinear.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testEstrada() {
        Estrada varEstrada = new Estrada();
        Assert.assertEquals(0, varEstrada.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testComplement() {
        Complement varComplement = new Complement();
        Assert.assertEquals(0, varComplement.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testNewLowerBounds() {
        NewLowerBounds varNewLowerBounds = new NewLowerBounds();
    }

    @Test
    public void testDominationNumber() {
        DominationNumber varDominationNumber = new DominationNumber();
        Assert.assertEquals(0, varDominationNumber.calculate(peterson).intValue());
    }

    @Test
    public void testAllPairShortestPathsWithoutWeight() {
        AllPairShortestPathsWithoutWeight varAllPairShortestPathsWithoutWeight = new AllPairShortestPathsWithoutWeight();
        Assert.assertEquals(0, varAllPairShortestPathsWithoutWeight.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testIsBipartite() {
        IsBipartite varIsBipartite = new IsBipartite();
        Assert.assertFalse(varIsBipartite.calculate(peterson));
        Assert.assertTrue(varIsBipartite.calculate(circle4));
        Assert.assertFalse(varIsBipartite.calculate(circle5));
        Assert.assertFalse(varIsBipartite.calculate(complete4));
        Assert.assertFalse(varIsBipartite.calculate(complete5));
    }

    @Test
    public void testNumOfConnectedComponents() {
        NumOfConnectedComponents varNumOfConnectedComponents = new NumOfConnectedComponents();
        Assert.assertEquals(1, varNumOfConnectedComponents.calculate(peterson).intValue());
    }

    @Test
    public void testIsEulerian() {
        IsEulerian varIsEulerian = new IsEulerian();
        Assert.assertFalse(varIsEulerian.calculate(peterson));
        Assert.assertTrue(varIsEulerian.calculate(circle4));
        Assert.assertTrue(varIsEulerian.calculate(circle5));
        Assert.assertFalse(varIsEulerian.calculate(complete4));
        Assert.assertTrue(varIsEulerian.calculate(complete5));
    }

    @Test
    public void testGirthSize() {
        GirthSize varGirthSize = new GirthSize();
        Assert.assertEquals(5, varGirthSize.calculate(peterson).intValue());
        Assert.assertEquals(4, varGirthSize.calculate(circle4).intValue());
        Assert.assertEquals(5, varGirthSize.calculate(circle5).intValue());
        Assert.assertEquals(3, varGirthSize.calculate(complete4).intValue());
        Assert.assertEquals(3, varGirthSize.calculate(complete5).intValue());
    }

    @Test
    public void testNumOfTriangles() {
        NumOfTriangles varNumOfTriangles = new NumOfTriangles();
        Assert.assertEquals(0, varNumOfTriangles.calculate(peterson).intValue());
        Assert.assertEquals(0, varNumOfTriangles.calculate(circle4).intValue());
        Assert.assertEquals(0, varNumOfTriangles.calculate(circle5).intValue());
        Assert.assertEquals(4, varNumOfTriangles.calculate(complete4).intValue());
        Assert.assertEquals(10, varNumOfTriangles.calculate(complete5).intValue());
    }

    @Test
    public void testNumOfEdges() {
        NumOfEdges varNumOfEdges = new NumOfEdges();
        Assert.assertEquals(15, varNumOfEdges.calculate(peterson).intValue());
        Assert.assertEquals(4, varNumOfEdges.calculate(circle4).intValue());
        Assert.assertEquals(5, varNumOfEdges.calculate(circle5).intValue());
        Assert.assertEquals(6, varNumOfEdges.calculate(complete4).intValue());
        Assert.assertEquals(10, varNumOfEdges.calculate(complete5).intValue());
    }

    @Test
    public void testSubTreeCounting() {
        SubTreeCounting varSubTreeCounting = new SubTreeCounting();
        Assert.assertEquals(0, varSubTreeCounting.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testMaxAndMinDegree() {
        MaxAndMinDegree varMaxAndMinDegree = new MaxAndMinDegree();
        ArrayList<Integer> maxmin = varMaxAndMinDegree.calculate(peterson);
        Assert.assertEquals(3, maxmin.get(0).intValue());
        Assert.assertEquals(3, maxmin.get(1).intValue());

        maxmin = varMaxAndMinDegree.calculate(circle4);
        Assert.assertEquals(2, maxmin.get(0).intValue());
        Assert.assertEquals(2, maxmin.get(1).intValue());

        maxmin = varMaxAndMinDegree.calculate(circle5);
        Assert.assertEquals(2, maxmin.get(0).intValue());
        Assert.assertEquals(2, maxmin.get(1).intValue());

        maxmin = varMaxAndMinDegree.calculate(complete4);
        Assert.assertEquals(3, maxmin.get(0).intValue());
        Assert.assertEquals(3, maxmin.get(1).intValue());

        maxmin = varMaxAndMinDegree.calculate(complete5);
        Assert.assertEquals(4, maxmin.get(0).intValue());
        Assert.assertEquals(4, maxmin.get(1).intValue());
    }

    @Test
    public void testNumOfStars() {
        NumOfStars varNumOfStars = new NumOfStars();
        Assert.assertEquals(30, varNumOfStars.calculate(peterson).intValue());
        Assert.assertEquals(8, varNumOfStars.calculate(circle4).intValue());
        Assert.assertEquals(0, varNumOfStars.calculate(circle5).intValue());
        Assert.assertEquals(0, varNumOfStars.calculate(complete4).intValue());
        Assert.assertEquals(0, varNumOfStars.calculate(complete5).intValue());
    }

    @Test
    public void testMaxOfIndSets() {
        MaxOfIndSets varMaxOfIndSets = new MaxOfIndSets();
        Assert.assertEquals(0, varMaxOfIndSets.calculate(peterson).intValue());
    }

    @Test
    public void testAdjacencyMatrix() {
        SpectrumOfAdjacencyMatrix varAdjacencyMatrix = new SpectrumOfAdjacencyMatrix();
        Assert.assertEquals("", varAdjacencyMatrix.calculate(peterson).get(0));
    }

    @Test
    public void testNumOfIndSets() {
        NumOfIndSets varNumOfIndSets = new NumOfIndSets();
        Assert.assertEquals(0, varNumOfIndSets.calculate(peterson).intValue());
    }

    @Test
    public void testPathsofLengthTwo() {
        PathsofLengthTwo varPathsofLengthTwo = new PathsofLengthTwo();
        Assert.assertEquals(30, varPathsofLengthTwo.calculate(peterson).intValue());//???
        Assert.assertEquals(4, varPathsofLengthTwo.calculate(circle4).intValue());
        Assert.assertEquals(5, varPathsofLengthTwo.calculate(circle5).intValue());
        Assert.assertEquals(15, varPathsofLengthTwo.calculate(complete4).intValue());
        Assert.assertEquals(45, varPathsofLengthTwo.calculate(complete5).intValue());
    }

    @Test
    public void testRadius() {
        Radius varRadius = new Radius();
        Assert.assertEquals(2, varRadius.calculate(TreeForRadiusDiameterTest1).intValue());
        Assert.assertEquals(2, varRadius.calculate(TreeForRadiusDiameterTest2).intValue());
        Assert.assertEquals(2, varRadius.calculate(circle5).intValue());
        Assert.assertEquals(1, varRadius.calculate(complete4).intValue());
        Assert.assertEquals(1, varRadius.calculate(complete5).intValue());
    }

    @Test
    public void testDiameter() {
        Diameter varDiameter = new Diameter();
        Assert.assertEquals(2, varDiameter.calculate(peterson).intValue());
        Assert.assertEquals(2, varDiameter.calculate(circle4).intValue());
        Assert.assertEquals(2, varDiameter.calculate(circle5).intValue());
        Assert.assertEquals(1, varDiameter.calculate(complete4).intValue());
        Assert.assertEquals(1, varDiameter.calculate(complete5).intValue());
        Assert.assertEquals(4, varDiameter.calculate(TreeForRadiusDiameterTest1).intValue());
        Assert.assertEquals(3, varDiameter.calculate(TreeForRadiusDiameterTest2).intValue());
    }

    @Test
    public void testNumOfVertices() {
        NumOfVertices varNumOfVertices = new NumOfVertices();
        Assert.assertEquals(10, varNumOfVertices.calculate(peterson).intValue());
        Assert.assertEquals(4, varNumOfVertices.calculate(circle4).intValue());
        Assert.assertEquals(5, varNumOfVertices.calculate(circle5).intValue());
        Assert.assertEquals(4, varNumOfVertices.calculate(complete4).intValue());
        Assert.assertEquals(5, varNumOfVertices.calculate(complete5).intValue());
    }

    @Test
    public void testNumOfQuadrangle() {
        NumOfQuadrangle varNumOfQuadrangle = new NumOfQuadrangle();
        Assert.assertEquals(0, varNumOfQuadrangle.calculate(peterson).intValue());
        Assert.assertEquals(1, varNumOfQuadrangle.calculate(circle4).intValue());
        Assert.assertEquals(0, varNumOfQuadrangle.calculate(circle5).intValue());
        Assert.assertEquals(3, varNumOfQuadrangle.calculate(complete4).intValue());//???
        Assert.assertEquals(12, varNumOfQuadrangle.calculate(complete5).intValue());
    }

    @Test
    public void testTotalNumOfStars() {
        TotalNumOfStars varTotalNumOfStars = new TotalNumOfStars();
        Assert.assertEquals("", varTotalNumOfStars.calculate(peterson).get(0));
    }

    @Test
    public void testNumOfVerticesWithDegK() {
        NumOfVerticesWithDegK.k = 3;
        NumOfVerticesWithDegK varNumOfVerticesWithDegK = new NumOfVerticesWithDegK();
        Assert.assertEquals(10, varNumOfVerticesWithDegK.calculate(peterson).intValue());
        Assert.assertEquals(0, varNumOfVerticesWithDegK.calculate(circle4).intValue());
        Assert.assertEquals(0, varNumOfVerticesWithDegK.calculate(circle5).intValue());
        Assert.assertEquals(4, varNumOfVerticesWithDegK.calculate(complete4).intValue());//???
        Assert.assertEquals(0, varNumOfVerticesWithDegK.calculate(complete5).intValue());
    }

    @Test
    public void testKConnected() {
        KConnected varKConnected = new KConnected();
        Assert.assertEquals(3, varKConnected.calculate(peterson).intValue());
    }

    @Test
    public void testSignlessLaplacianEnergy() {
        SignlessLaplacianEnergy varSignlessLaplacianEnergy = new SignlessLaplacianEnergy();
        Assert.assertEquals("", varSignlessLaplacianEnergy.calculate(peterson));
    }

    @Test
    public void testEigenValues() {
        EigenValues varEigenValues = new EigenValues();
        Assert.assertEquals("", varEigenValues.calculate(peterson).get(0));
    }

    @Test
    public void testLaplacianEnergyLike() {
        LaplacianEnergyLike varLaplacianEnergyLike = new LaplacianEnergyLike();
        Assert.assertEquals("", varLaplacianEnergyLike.calculate(peterson));
    }

    @Test
    public void testEccentricityMatrixOfGraph() {
        EccentricityMatrixOfGraph varEccentricityMatrixOfGraph = new EccentricityMatrixOfGraph();
        Assert.assertEquals("", varEccentricityMatrixOfGraph.calculate(peterson).get(0));
    }

    @Test
    public void testLaplacianEnergy() {
        LaplacianEnergy varLaplacianEnergy = new LaplacianEnergy();
        Assert.assertEquals("", varLaplacianEnergy.calculate(peterson));
    }

    @Test
    public void testLaplacianOfGraph() {
        LaplacianOfGraph varLaplacianOfGraph = new LaplacianOfGraph();
        Assert.assertEquals("", varLaplacianOfGraph.calculate(peterson).get(0));
    }

    @Test
    public void testKirchhoffIndex() {
        KirchhoffIndex varKirchhoffIndex = new KirchhoffIndex();
        Assert.assertEquals("", varKirchhoffIndex.calculate(peterson));
    }

    @Test
    public void testSignlessLaplacianOfGraph() {
        SignlessLaplacianOfGraph varSignlessLaplacianOfGraph = new SignlessLaplacianOfGraph();
        Assert.assertEquals("", varSignlessLaplacianOfGraph.calculate(peterson).get(0));
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
        Assert.assertEquals(0, varHamiltonianCycleExtension.calculate(peterson).vertices.size());
    }

    @Test
    public void testHamiltonianPathExtension() {
        HamiltonianPathExtension varHamiltonianPathExtension = new HamiltonianPathExtension();
        Assert.assertEquals(0, varHamiltonianPathExtension.calculate(peterson).vertices.size());
    }

    @Test
    public void testSzegedIndex() {
        SzegedIndex varSzegedIndex = new SzegedIndex();
        Assert.assertEquals(0, varSzegedIndex.calculate(peterson).intValue());
    }

    @Test
    public void testAllEccen() {
        AllEccen varAllEccen = new AllEccen();
        Assert.assertEquals(0, varAllEccen.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testRevisedSzegedIndex() {
        RevisedSzegedIndex varRevisedSzegedIndex = new RevisedSzegedIndex();
        Assert.assertEquals(375, varRevisedSzegedIndex.calculate(peterson).intValue());
    }

    @Test
    public void testMostarIndex() {
        MostarIndex varMostarIndex = new MostarIndex();
        Assert.assertEquals(0, varMostarIndex.calculate(peterson).intValue());
    }

    @Test
    public void testMerrifieldSimmons() {
        MerrifieldSimmons varMerrifieldSimmons = new MerrifieldSimmons();
        Assert.assertEquals(0, varMerrifieldSimmons.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testWeightedPiIndex() {
        WeightedPiIndex varWeightedPiIndex = new WeightedPiIndex();
        Assert.assertEquals(540, varWeightedPiIndex.calculate(peterson).intValue());
    }

    @Test
    public void testPeripheralVerticesCount() {
        PeripheralVerticesCount varPeripheralVerticesCount = new PeripheralVerticesCount();
        Assert.assertEquals(0, varPeripheralVerticesCount.calculate(peterson).intValue());
    }

    @Test
    public void testEccentricityEnergy() {
        EccentricityEnergy varEccentricityEnergy = new EccentricityEnergy();
        Assert.assertEquals(10, varEccentricityEnergy.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testWeightedSzegedIndex() {
        WeightedSzegedIndex varWeightedSzegedIndex = new WeightedSzegedIndex();
        Assert.assertEquals(810, varWeightedSzegedIndex.calculate(peterson).intValue());
    }

    @Test
    public void testPeripheralWienerIndex() {
        PeripheralWienerIndex varPeripheralWienerIndex = new PeripheralWienerIndex();
        Assert.assertEquals(75, varPeripheralWienerIndex.calculate(peterson).intValue());
    }

    @Test
    public void testPeripheralVertices() {
        PeripheralVertices varPeripheralVertices = new PeripheralVertices();
    }

    @Test
    public void testEccentricity() {
        Eccentricity varEccentricity = new Eccentricity();
        Assert.assertEquals(9, varEccentricity.calculate(peterson).iterator().next().get(0));
    }

    @Test
    public void testPiIndex() {
        PiIndex varPiIndex = new PiIndex();
        Assert.assertEquals(90, varPiIndex.calculate(peterson).intValue());
    }
}