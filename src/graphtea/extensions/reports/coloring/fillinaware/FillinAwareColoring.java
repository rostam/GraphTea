package graphtea.extensions.reports.coloring.fillinaware;

import Jama.Matrix;
import graphtea.extensions.reports.coloring.MM;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.graph.graph.Vertex;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class FillinAwareColoring implements GraphReportExtension,Parametrizable {
  @Parameter(name = "Matrix File", description = "")
  public String file = "abb313.mtx";
  @Parameter(name="k from ILU(k)")
  public int el = 2;

  @Override
  public Object calculate(GraphModel g) {
    Matrix mm = new Matrix(5,5);
    try {
      File f = new File("matrices/"+file);
      System.out.println("file"+f.getAbsolutePath());
      mm= MM.loadMatrixFromSPARSE(f);
    } catch (IOException e) {
      e.printStackTrace();
    }
    Matrix mm1=Sparsify.sparsify(mm, 1);
    Matrix mm10=Sparsify.sparsify(mm, 10);
    Matrix mmDiv32=Sparsify.sparsify(mm, (int) Math.floor(mm.getColumnDimension()/32));
    Matrix mmDiv8=Sparsify.sparsify(mm, (int) Math.floor(mm.getColumnDimension()/8));
    //MatrixHeatMap hm = new MatrixHeatMap(mmDiv8);

    int fillin   =0;//Helper.getFillinMinDeg(Helper.getGraphOfILU(mm),el);
    int fillin1  =Helper.getFillinMinDeg(Helper.getGraphOfILU(mm1),el);
    int fillin10 =Helper.getFillinMinDeg(Helper.getGraphOfILU(mm10),el);
    int fillinDivide32 =Helper.getFillinMinDeg(Helper.getGraphOfILU(mmDiv32),el);
    int fillinDivide8 =Helper.getFillinMinDeg(Helper.getGraphOfILU(mmDiv8),el);

    RenderTable ret = new RenderTable();
    Vector<String> titles = new Vector<>();
    titles.add(" NNZ+Fillin+Col (without blocking) ");
    titles.add(" NNZ+Fillin+ColRes k=1 ");
    titles.add(" NNZ+Fillin+ColRes k=10 ");
    titles.add(" NNZ+Fillin+ColRes k=n/32 ");
    titles.add(" NNZ+Fillin+ColRes k=n/8 ");
    ret.setTitles(titles);
    Vector<Object> results = new Vector<>();

    GraphModel gCol  = Helper.getGraphOfColoring(mm);
    GraphModel gCol1 = Helper.getGraphOfColoringRestricted(mm, mm1);
    GraphModel gCol10= Helper.getGraphOfColoringRestricted(mm,mm10);
    GraphModel gColDiv32= Helper.getGraphOfColoringRestricted(mm,mmDiv32);
    GraphModel gColDiv8= Helper.getGraphOfColoringRestricted(mm,mmDiv8);

    results.add(Helper.numOfNonzeros(mm)  +"+"+fillin  +"+"
            +HeuristicColoring.colorRestricted(gCol, HeuristicColoring.getOrdering(gCol,"MaxDegree")));
    results.add(Helper.numOfNonzeros(mm1) +"+"+fillin1 +"+"
            +HeuristicColoring.colorRestricted(gCol1, HeuristicColoring.getOrdering(gCol,"MaxDegree")));
    results.add(Helper.numOfNonzeros(mm10)+"+"+fillin10+"+"
            +HeuristicColoring.colorRestricted(gCol10, HeuristicColoring.getOrdering(gCol,"MaxDegree")));
    results.add(Helper.numOfNonzeros(mmDiv32)+"+"+fillinDivide32
            +"+"+HeuristicColoring.colorRestricted(gColDiv32, HeuristicColoring.getOrdering(gCol,"MaxDegree")));
    results.add(Helper.numOfNonzeros(mmDiv8)+"+"+fillinDivide8+"+"
            +HeuristicColoring.colorRestricted(gColDiv8, HeuristicColoring.getOrdering(gCol,"MaxDegree")));
    ret.add(results);
    return ret;
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
