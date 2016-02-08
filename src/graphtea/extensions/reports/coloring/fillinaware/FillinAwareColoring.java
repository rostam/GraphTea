package graphtea.extensions.reports.coloring.fillinaware;

import Jama.Matrix;
import graphtea.extensions.reports.coloring.MM;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.reports.extension.GraphReportExtension;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class FillinAwareColoring implements GraphReportExtension,Parametrizable {
  //  @Parameter(name = "Matrix File", description = "")
//  public String file = "abb313.mtx";
  @Parameter(name = "k from ILU(k)")
  public int el = 2;

  public static String getPathOfMats() {
    String cur = null;
    try {
      cur = new java.io.File(".").getCanonicalPath();
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (System.getProperty("os.name").contains("Win")) {
      cur = cur + "\\matrices\\";
    } else {
      cur = cur + "/matrices/";
    }

    return cur;
  }

  @Override
  public Object calculate(GraphModel g) {
    RenderTable ret = new RenderTable(1);

    Vector<String> titles = new Vector<>();
    titles.add("Matrix");
    titles.add("Size");
    //titles.add(" NNZ+F+Col (without blocking) ");
    //F = number of fillins in initally required elemetns matrix
    //C = the number of colors in one sided restricted coloring
    //k blocking size
    //P Potentially Required Edges
    titles.add(" NNZ+F+C+P+A k=1 ");
    titles.add(" NNZ+F+C+P+A k=10 ");
    titles.add(" NNZ+F+C+P+A k=n/32 ");
    titles.add(" NNZ+F+C+P+A k=n/8 ");
    ret.setTitles(titles);
    File dir = new File(getPathOfMats());
    File[] directoryListing = dir.listFiles();
    if (directoryListing != null) {
      for (File f : directoryListing) {
        try {
          System.out.println("file:" + f.getAbsolutePath());
          Matrix m = MM.loadMatrixFromSPARSE(f);
          SpMat sm = new SpMat(m);
          ret.add(computeForOneMatrix(f.getName(), sm, "Normal", "Normal",m));
//          ret.add(computeForOneMatrix(f.getName(), sm, "Normal", "MaxDegree",m));
//          ret.add(computeForOneMatrix(f.getName(), sm, "MaxDegree", "MaxDegree",m));
//          ret.add(computeForOneMatrix(f.getName(), sm, "MinDegree", "MinDegree",m));
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    //MatrixHeatMap hm = new MatrixHeatMap(mmDiv8);
    return ret;
  }

  public Vector<Object> computeForOneMatrix(String fileName, SpMat mm,
                                            String orderILU,String orderCol,Matrix m) {
    Vector<Object> results = new Vector<>();
    SpMat mm1 = mm.sparsify(1);
    SpMat mm10 = mm.sparsify(10);
    SpMat mmDiv32 = mm.sparsify((int) Math.floor(mm.rows() / 32));
    SpMat mmDiv8 = mm.sparsify((int) Math.floor(mm.cols() / 8));

    //int fillin   =0;//Helper.getFillinMinDeg(Helper.getGraphOfILU(mm),el);
    GraphModel gILU1 = Helper.getGraphOfILU(mm1);
    GraphModel gILU10 = Helper.getGraphOfILU(mm10);
    GraphModel gILUDiv32 = Helper.getGraphOfILU(mmDiv32);
    GraphModel gILUDiv8 = Helper.getGraphOfILU(mmDiv8);

    GraphModel gCol = Helper.getGraphOfColoring(mm);
    GraphModel gCol1 = Helper.getGraphOfColoringRestricted(mm, mm1);
    GraphModel gCol10 = Helper.getGraphOfColoringRestricted(mm, mm10);
    GraphModel gColDiv32 = Helper.getGraphOfColoringRestricted(mm, mmDiv32);
    GraphModel gColDiv8 = Helper.getGraphOfColoringRestricted(mm, mmDiv8);


    SpMat matF1        = Helper.getFillinMinDeg(gILU1, el, HCol.ordering(gCol, orderILU), orderILU, mm1);
    SpMat matF10       = Helper.getFillinMinDeg(gILU10, el, HCol.ordering(gCol, orderILU),orderILU,mm10);
    SpMat matFDiv32    = Helper.getFillinMinDeg(gILUDiv32, el, HCol.ordering(gCol, orderILU),orderILU,mmDiv32);
    SpMat matFDiv8     = Helper.getFillinMinDeg(gILUDiv8, el, HCol.ordering(gCol, orderILU),orderILU,mmDiv8);

    int F1=matF1.nnz()-mm1.nnz();
    int F10=matF10.nnz()-mm10.nnz();;
    int FDiv32=matFDiv32.nnz()-mmDiv32.nnz();;
    int FDiv8=matFDiv8.nnz()-mmDiv8.nnz();;

    int col1     = HCol.colorRestricted(gCol1, HCol.ordering(gCol, orderCol),orderCol);
    int col10    = HCol.colorRestricted(gCol10, HCol.ordering(gCol, orderCol),orderCol);
    int colDiv32 = HCol.colorRestricted(gColDiv32, HCol.ordering(gCol, orderCol),orderCol);
    int colDiv8  = HCol.colorRestricted(gColDiv8, HCol.ordering(gCol, orderCol),orderCol);


    SpMat mpot1     = Helper.getPotReqEdges(gCol1, mm, mm1);
    SpMat mpot10    = Helper.getPotReqEdges(gCol10, mm, mm10);
    SpMat mpotDiv32 = Helper.getPotReqEdges(gColDiv32, mm, mmDiv32);
    SpMat mpotDiv8  = Helper.getPotReqEdges(gColDiv8, mm, mmDiv8);

    int pot1     = mpot1.nnz();
    int pot10    = mpot10.nnz();
    int potDiv32 = mpotDiv32.nnz();
    int potDiv8  = mpotDiv8.nnz();

    SpMat madd1     = Helper.getAddReqEdges(gILU1, mm, mm1, mpot1, HCol.ordering(gCol, orderILU));
    SpMat madd10    = Helper.getAddReqEdges(gILU10, mm, mm10, mpot10, HCol.ordering(gCol, orderILU));
    SpMat maddDiv32 = Helper.getAddReqEdges(gILUDiv32, mm, mmDiv32, mpotDiv32, HCol.ordering(gCol, orderILU));
    SpMat maddDiv8  = Helper.getAddReqEdges(gILUDiv8, mm, mmDiv8, mpotDiv8, HCol.ordering(gCol, orderILU));

    int add1     = madd1.nnz();
    int add10    = madd10.nnz();
    int addDiv32 = maddDiv32.nnz();
    int addDiv8  = maddDiv8.nnz();

    results.add(fileName + ", OrderOfILU="+orderILU + ", OrderOfCol="+orderCol);
    results.add(mm.rows() * 1.0);
    results.add(mm1.nnz() + "+" + F1 + "+" + col1 + "+" + pot1 + "+" + add1);
    results.add(mm10.nnz() + "+" + F10 + "+" + col10 + "+" + pot10 + "+" + add10);
    results.add(mmDiv32.nnz() + "+" + FDiv32 + "+" + colDiv32 + "+" + potDiv32+ "+" + addDiv32);
    results.add(mmDiv8.nnz() + "+" + FDiv8 + "+" + colDiv8 + "+" + potDiv8 + "+" + addDiv8);

    try {
      MM.saveMtxFormat(new File("mats/m10.mtx"), mm10);
//      Matrix Fmat=Helper.ILUR(m, mm10, matF10);
//      int cnt = 0;
//      for(int i=0;i<Fmat.getRowDimension();i++) {
//        for(int j=0;j<Fmat.getColumnDimension();j++) {
//          if(Fmat.get(i,j) != 0) cnt++;
//        }
//      }
//      System.out.println("bargashte " + cnt) ;
      MM.saveMtxFormat(new File("mats/F10.mtx"),matF10);
      MM.saveMtxFormat(new File("mats/add10.mtx"),madd10);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return results;
  }

  private void printArr(double[][] test) {
    for (double[] aTest : test) {
      for (int j = 0; j < test.length; j++) {
        System.out.print(aTest[j] + " ");
      }
      System.out.println();
    }
  }

  @Override
  public String getCategory() {
    return "Bipartite Coloring";
  }

  @Override
  public String getName() {
    return "Fillin-Aware Graph Coloring ";
  }

  @Override
  public String getDescription() {
    return "Bipartite Graph Coloring";
  }

  @Override
  public String checkParameters() {
    return null;
  }
}
