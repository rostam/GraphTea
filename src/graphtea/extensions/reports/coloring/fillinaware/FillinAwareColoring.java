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
  @Parameter(name="k from ILU(k)")
  public int el = 2;

  public static String getPathOfMats() {
    String cur = null;
    try {
      cur = new java.io.File(".").getCanonicalPath();
    } catch (IOException e) {
      e.printStackTrace();
    }

    if(System.getProperty("os.name").contains("Win")) {
      cur =cur + "\\matrices\\";
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
    titles.add(" NNZ+F+C k=1 ");
    titles.add(" NNZ+F+C k=10 ");
    titles.add(" NNZ+F+C k=n/32 ");
    titles.add(" NNZ+F+C k=n/8 ");
    ret.setTitles(titles);
    File dir = new File(getPathOfMats());
    File[] directoryListing = dir.listFiles();
    if (directoryListing != null) {
      for (File f : directoryListing) {
        try {
          System.out.println("file:"+f.getAbsolutePath());
          Matrix mm= MM.loadMatrixFromSPARSE(f);
          ret.add(computeForOneMatrix(f.getName(),mm));
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    //MatrixHeatMap hm = new MatrixHeatMap(mmDiv8);
    return ret;
  }

  public Vector<Object> computeForOneMatrix(String fileName, Matrix mm) {

    Vector<Object> results = new Vector<>();
    Matrix mm1=Sparsify.sparsify(mm, 1);
    Matrix mm10=Sparsify.sparsify(mm, 10);
    Matrix mmDiv32=Sparsify.sparsify(mm, (int) Math.floor(mm.getColumnDimension()/32));
    Matrix mmDiv8=Sparsify.sparsify(mm, (int) Math.floor(mm.getColumnDimension()/8));

    //int fillin   =0;//Helper.getFillinMinDeg(Helper.getGraphOfILU(mm),el);
    int fillin1  =Helper.getFillinMinDeg(Helper.getGraphOfILU(mm1), el);
    int fillin10 =Helper.getFillinMinDeg(Helper.getGraphOfILU(mm10), el);
    int fillinDivide32 =Helper.getFillinMinDeg(Helper.getGraphOfILU(mmDiv32), el);
    int fillinDivide8 =Helper.getFillinMinDeg(Helper.getGraphOfILU(mmDiv8), el);

    GraphModel gCol  = Helper.getGraphOfColoring(mm);
    GraphModel gCol1 = Helper.getGraphOfColoringRestricted(mm, mm1);
    GraphModel gCol10= Helper.getGraphOfColoringRestricted(mm, mm10);
    GraphModel gColDiv32= Helper.getGraphOfColoringRestricted(mm, mmDiv32);
    GraphModel gColDiv8= Helper.getGraphOfColoringRestricted(mm, mmDiv8);

    results.add(fileName);
    //results.add(Helper.numOfNonzeros(mm)  +"+"+fillin  +"+"
    //        +HeuristicColoring.colorRestricted(gCol, HeuristicColoring.getOrdering(gCol,"MaxDegree")));
    results.add(mm.getColumnDimension()*1.0);
    results.add(MM.NNZ(mm1) +"+"+fillin1 +"+"
            +HeuristicColoring.colorRestricted(gCol1, HeuristicColoring.getOrdering(gCol,"MaxDegree")));
    results.add(MM.NNZ(mm10)+"+"+fillin10+"+"
            +HeuristicColoring.colorRestricted(gCol10, HeuristicColoring.getOrdering(gCol,"MaxDegree")));
    results.add(MM.NNZ(mmDiv32)+"+"+fillinDivide32
            +"+"+HeuristicColoring.colorRestricted(gColDiv32, HeuristicColoring.getOrdering(gCol,"MaxDegree")));
    results.add(MM.NNZ(mmDiv8)+"+"+fillinDivide8+"+"
            +HeuristicColoring.colorRestricted(gColDiv8, HeuristicColoring.getOrdering(gCol,"MaxDegree")));

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
