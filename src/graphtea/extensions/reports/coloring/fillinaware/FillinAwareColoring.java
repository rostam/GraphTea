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
    titles.add("Fillin");
    titles.add("Rows");
    titles.add("NNZ");
    titles.add("F");
    titles.add("C");
    titles.add("add");
    ret.setTitles(titles);
    File dir = new File(getPathOfMats());
    File[] directoryListing = dir.listFiles();
    if (directoryListing != null) {
      for (File f : directoryListing) {
        try {
          System.out.println("file:" + f.getAbsolutePath());
          Matrix m = MM.loadMatrixFromSPARSE(f);
          SpMat sm = new SpMat(m);
          generateResults(ret, f, sm, "Nat", "Nat", 10);
          generateResults(ret, f, sm, "MinDeg", "MinDeg", 10);
          generateResults(ret, f, sm, "Metis","Metis",10);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    //MatrixHeatMap hm = new MatrixHeatMap(mmDiv8);
    return ret;
  }

  private void generateResults(RenderTable ret, File f, SpMat  sm,
                               String ordILU, String ordCol,int k) {
    ColInf cinf = computeOneMatrix(sm,ordILU,ordCol,k);
    Vector<Object> results = new Vector<>();
    results.add(f.getName()+","+ordILU+","+ordCol);
    results.add(cinf.F*1.0);
    results.addAll(cinf.getVec());
    ret.add(results);
  }

  public ColInf computeOneMatrix(SpMat mm, String ordILU,String ordCol,int k) {
    SpMat block = mm.sparsify(k);
    GraphModel gILU = Helper.getGraphOfILU(block);
    GraphModel gCol = Helper.getGraphOfColoring(block);
    Vector<Integer> orderILU = HCol.ordering(gCol, ordILU, block);
    Vector<Integer> orderCol = HCol.ordering(gCol, ordCol, block);
    SpMat Fmat = Helper.SILU(gILU, el, orderILU, block);
    int F=Fmat.nnz()-block.nnz();
    int C= HCol.colorRest(gCol, orderCol);
    SpMat Pmat = Helper.getPotReqEdges(gCol, mm, block);
    int P = Pmat.nnz();
    SpMat addM = Helper.getAddReqEdges(gILU, mm, block, Pmat, orderILU);
    int add = addM.nnz();
    saveMatriices(block,Fmat,addM,ordILU,ordCol);
    return new ColInf(block.rows(),block.nnz(),F,C,P,add);
  }

  public void saveMatriices(SpMat block, SpMat fillin,
                            SpMat add, String ordILU,String ordCol) {
    try {
      MM.saveMtxFormat(new File("mats/m10" + ordILU + ordCol+ ".mtx"), block);
      MM.saveMtxFormat(new File("mats/F10" + ordILU + ordCol+ ".mtx"),fillin);
      MM.saveMtxFormat(new File("mats/add10" + ordILU + ordCol+ ".mtx"),add);
    } catch (IOException e) {
      e.printStackTrace();
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
