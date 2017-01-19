package graphtea.extensions.reports.coloring;

import Jama.Matrix;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.ArrayX;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class Coloring implements GraphReportExtension,Parametrizable {

  //bls block size
  public RenderTable color() {
    int blockSize = 100;
    int Mode2=0;

   if (Ordering!=0){if ((Coloring==3)||(Coloring==3)||(Coloring==3)||(Coloring==3)||(Coloring==3)){Ordering+=3;}}

    if (Mode == 3) {
      if (rho != 0) {
        Mode = 2 * rho;
      }
    }
    if (Mode == 0) {
      if (rho != 0) {
        Mode2 = 2 * rho;
      }
    }

    if (RequiredPattern == 3) {
      if (bls != 0) {
        blockSize = bls;
      }
    }

    Matrix mm = new Matrix(5,5);
    try {
      File f = new File("matrices/"+file);
      mm=MM.loadMatrixFromSPARSE(f);
    } catch (IOException e) {
      e.printStackTrace();
    }
    int rows = mm.getRowDimension();
    int cols = mm.getColumnDimension();

    GraphModel g = new GraphModel(false);
    Vector<Integer> V_r = new Vector<>();
    Vector<Integer> V_c = new Vector<>();

    for(int i=0; i < rows;i++) {
      Vertex v = new Vertex();
      g.addVertex(v);
      V_r.add(v.getId());
    }

    for(int i=0; i < cols;i++) {
      Vertex v = new Vertex();
      g.addVertex(v);
      V_c.add(v.getId());
    }

    int count = 0;
    for(int i=0;i<rows;i++) {
      for (int j = 0; j < cols; j++) {
        if (mm.get(i, j) != 0) {
          g.addEdge(new Edge(g.getVertex(i), g.getVertex(rows + j)));
          count++;
        }
      }
    }

    //Initialize required pattern
    int entries_pattern = 0;
    //If edge e \in E_S then edge_property edge_weight=1 else
    switch (RequiredPattern) {
      case 0:
        System.out.printf("No required pattern.");
        break;
      case 1:
        System.out.println("Diagonal");
        for(Edge e : g.getEdges()) {
          if(e.source.getId() + rows == e.target.getId()) {
            e.setWeight(1);
            entries_pattern++;
          } else {
            e.setWeight(0);
          }
        }

        System.out.println("Entries_pattern: " + entries_pattern);
        System.out.println("Density_pattern:_" + ((entries_pattern /(rows*1.0)))*100.0);
        break;
      case 2:
        System.out.println("Full");
        for(Edge e:g.getEdges()) {
          e.setWeight(1);
          entries_pattern++;
        }
        System.out.println("Entries_pattern: " +  entries_pattern);
        System.out.println("Density_pattern: " + (entries_pattern / g.getEdgesCount() * 100.0));
        break;
      case 3:
        System.out.println("BlockDiagonal");
        for(Edge e: g.getEdges()) {
          int RowCoordinate = e.source.getId()+rows;
          int ColumnCoordinate = e.target.getId();
          int RelativeDistance = RowCoordinate - ColumnCoordinate;
          int RowBlock = RowCoordinate / blockSize;
          int ColumnBlock = ColumnCoordinate / blockSize;
          if ((RelativeDistance < blockSize) && (RelativeDistance > -blockSize) && (RowBlock == ColumnBlock)) {
            e.setWeight(1);
            entries_pattern++;
          }
        }
        System.out.println("Entries_pattern: " + entries_pattern);
        System.out.println("Density_pattern:_" + (entries_pattern / rows * 100.0));
        break;
    }

    System.out.println("Ordering: " + Ordering);
    System.out.println("Coloring" + Coloring);
    System.out.println("RequiredPattern: " + RequiredPattern);
    System.out.println("Mode" + Mode);

    //Presorting of the vertices
    if (Coloring == 1 || Coloring == 2 || Coloring == 3 || Coloring == 4 ||
            Coloring == 5 || Coloring == 6) {
      switch (Ordering) {
        case 0:
          System.out.println("No ordering");
          break;
        case 1:
          System.out.println("LFO");
          if (Coloring == 1) {
            V_c = OrderingHeuristics.LFO(g, V_c);
          } else if (Coloring == 2) {
            V_r = OrderingHeuristics.LFO(g, V_r);
          } else if (Coloring == 5 || Coloring == 6) {
            V_r = OrderingHeuristics.LFO(g, V_r);
            V_c = OrderingHeuristics.LFO(g, V_c);
          }
          break;
        case 2:
          System.out.println("SLO");
          if (Coloring == 1) {
            V_c = OrderingHeuristics.SLO(g, V_c);
          } else if (Coloring == 2) {
            V_r = OrderingHeuristics.SLO(g, V_r);
          } else if (Coloring == 5) {
            V_r = OrderingHeuristics.SLO(g, V_r);
            V_c = OrderingHeuristics.SLO(g, V_c);
          }
          break;
        case 3:
          System.out.println("IDO");
          if (Coloring == 1) {
            V_c = OrderingHeuristics.IDO(g, V_c);
          } else if (Coloring == 2) {
            V_r = OrderingHeuristics.IDO(g, V_r);
          } else if (Coloring == 5) {
            V_r = OrderingHeuristics.IDO(g, V_r);
            V_c = OrderingHeuristics.IDO(g, V_c);
          }
          break;
        case 4:
          System.out.println("LFOrestricted");
          if (Coloring == 3) {
            V_c = OrderingHeuristics.LFOrestricted(g, V_c);
          } else if (Coloring == 4) {
            V_r = OrderingHeuristics.LFOrestricted(g, V_r);
          } else if (Coloring == 6) {
            V_r = OrderingHeuristics.LFOrestricted(g, V_r);
            V_c = OrderingHeuristics.LFOrestricted(g, V_c);
          }
          break;
        case 5:
          System.out.println("SLOrestricted");
          if (Coloring == 3) {
            V_c = OrderingHeuristics.SLOrestricted(g, V_c);
          } else if (Coloring == 4) {
            V_r = OrderingHeuristics.SLOrestricted(g, V_r);
          } else if (Coloring == 6) {
            V_r = OrderingHeuristics.SLOrestricted(g, V_r);
            V_c = OrderingHeuristics.SLOrestricted(g, V_c);
          }
          break;
        case 6:
          System.out.println("IDOrestricted");
          if (Coloring == 3) {
            V_c = OrderingHeuristics.IDOrestricted(g, V_c);
          } else if (Coloring == 4) {
            V_r = OrderingHeuristics.IDOrestricted(g, V_r);
          } else if (Coloring == 6) {
            V_r = OrderingHeuristics.IDOrestricted(g, V_r);
            V_c = OrderingHeuristics.IDOrestricted(g, V_c);
          }
          break;
      }
    }
    int num_colors=0;
    switch (Coloring) {
      case 1:
        System.out.println("PartialD2ColoringCols");
        num_colors =ColorAlgs.PartialD2Coloring(g,V_c);
        break;
      case 2:
        System.out.println("PartialD2ColoringRows");
        num_colors =ColorAlgs.PartialD2Coloring(g,V_r);
        break;
      case 3:
        System.out.println("PartialD2ColoringRestrictedCols");
        num_colors =ColorAlgs.PartialD2ColoringRestricted(g, V_c);
        break;
      case 4:
        System.out.println("PartialD2ColoringRestrictedRows");
        num_colors =ColorAlgs.PartialD2ColoringRestricted(g, V_r);
        break;
      case 5:
        System.out.println("StarBicoloringScheme");
        num_colors=ColorAlgs.StarBicoloringScheme(g,V_r,V_c,Mode,Mode2);
        break;
      case 6:
        System.out.println("StarBicoloringSchemeRestricted");
        num_colors=ColorAlgs.StarBicoloringSchemeRestricted(g, V_r, V_c, Mode, Mode2);
        break;
      case 7:
        System.out.println("StarBicoloringSchemeDynamicOrdering");
        num_colors=ColorAlgs.StarBicoloringSchemeDynamicOrdering(g, V_r, V_c, Mode, Ordering, Mode2);
        break;
      case 8:
        System.out.println("StarBicoloringSchemeCombinedVertexCoverColoring");
        num_colors=ColorAlgs.StarBicoloringSchemeCombinedVertexCoverColoring(g,V_r,V_c,Mode);
        break;
      case 9:
        System.out.println("StarBicoloringSchemeDynamicOrderingRestricted");
        num_colors=ColorAlgs.StarBicoloringSchemeDynamicOrderingRestricted(g, V_r, V_c, Mode, Ordering, Mode2);
        break;
      case 10:
        System.out.println("StarBicoloringSchemeCombinedVertexCoverColoringRestricted");
        num_colors=ColorAlgs.StarBicoloringSchemeCombinedVertexCoverColoringRestricted(g,V_r,V_c,Mode);
        break;
    }

    RenderTable rt = new RenderTable();
    Vector<String> names = new Vector<>();
    Vector<Object> results = new Vector<>();
    names.add("Matrix");
    results.add(file);
    names.add("Size");
    results.add(V_r.size() + "*" + V_c.size());
    names.add("Algorithm");
    results.add(colorings.getValue());
    names.add("Ordering");
    results.add(ord.getValue());
    names.add("Block Size");
    results.add(bls+"");
    names.add("Num Of Colors");
    results.add(num_colors+"");
    rt.setTitles(names);
    rt.add(results);
    return rt;
  }

  public int Coloring = 1;
  public int Ordering= 1;
  public int Mode = 3;
  public int RequiredPattern=1;
  @Parameter(name = "Algorithm", description = "PartialD2ColoringColumns -> 1\n" +
          "PartialD2ColoringRows -> 2;\n" +
          "PartialD2RestrictedColumns -> 3;\n" +
          "PartialD2RestrictedRows -> 4;\n" +
          "StarBicoloringScheme -> 5;\n" +
          "StarBicoloringSchemeRestricted -> 6;\n" +
          "StarBicoloringSchemeDynamicOrdering -> 7;\n" +
          " StarBicoloringSchemeCombinedVertexCoverColoring -> 8;\n" +
          "StarBicoloringSchemeDynamicOrderingRestricted -> 9;\n" +
          "StarBicoloringSchemeCombinedVertexCoverColoringRestricted -> 10;\n")
  public ArrayX<String> colorings = new ArrayX<>("PartialD2ColoringColumns","PartialD2ColoringRows", "PartialD2RestrictedColumns" ,
          "PartialD2RestrictedRows", "StarBicoloringScheme" ,
          "StarBicoloringSchemeRestricted" ,"StarBicoloringSchemeDynamicOrdering" ,
          " StarBicoloringSchemeCombinedVertexCoverColoring","StarBicoloringSchemeDynamicOrderingRestricted" ,
          "StarBicoloringSchemeCombinedVertexCoverColoringRestricted");

  @Parameter(name = "Ordering", description = "[LFO|SLO|IDO]")
  public ArrayX<String> ord= new ArrayX<>("LFO","SLO","IDO");
  //public int Ordering= 1;
  @Parameter(name = "Independent Set", description = "[Best 3|Variant 0]")
  public ArrayX<String> iset= new ArrayX<>("Best","Variant");
  //public int Mode = 3;
  @Parameter(name = "Pattern", description = "[Full 2 |Diagonal 1| BlockDiagonal 3]")
  public ArrayX<String> pat= new ArrayX<>("Diagonal","Full","BlockDiagonal");
  //public int RequiredPattern=1;
  @Parameter(name = "Matrix File", description = "")
  public String file = "hor_131.mtx";
  @Parameter(name = "rho", description = "")
  public int rho = 1;
  @Parameter(name = "Block Size", description = "")
  public int bls = 3;

  @Override
  public Object calculate(GraphModel g) {
    if(ord.getValue().equals("LFO")) Ordering=1;
    if(ord.getValue().equals("SLO")) Ordering=2;
    if(ord.getValue().equals("IDO")) Ordering=3;
    if(iset.getValue().equals("Best")) Mode=3;
    if(iset.getValue().equals("Variant")) Mode=0;
    if(pat.getValue().equals("Full")) RequiredPattern=2;
    if(pat.getValue().equals("Diagonal")) RequiredPattern=1;
    if(pat.getValue().equals("BlockDiagonal")) RequiredPattern=3;
    if(colorings.getValue().equals("PartialD2ColoringColumns")) Coloring=1;
    if(colorings.getValue().equals("PartialD2ColoringRows")) Coloring=2;
    if(colorings.getValue().equals("PartialD2RestrictedColumns")) Coloring=3;
    if(colorings.getValue().equals("PartialD2RestrictedRows")) Coloring=4;
    if(colorings.getValue().equals("StarBicoloringScheme")) Coloring=5;
    if(colorings.getValue().equals("StarBicoloringSchemeRestricted")) Coloring=6;
    if(colorings.getValue().equals("StarBicoloringSchemeDynamicOrdering")) Coloring=7;
    if(colorings.getValue().equals("StarBicoloringSchemeCombinedVertexCoverColoring")) Coloring=8;
    if(colorings.getValue().equals("StarBicoloringSchemeDynamicOrderingRestricted")) Coloring=9;
    if(colorings.getValue().equals("StarBicoloringSchemeCombinedVertexCoverColoringRestricted")) Coloring=10;

    return color();
  }

  @Override
  public String getCategory() {
    return "Bipartite Coloring";
  }

  @Override
  public String getName() {
    return "Bipartite Graph Coloring";
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
