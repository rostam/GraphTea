package graphtea.extensions.reports.coloring;

import Jama.Matrix;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.graph.graph.Vertex;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class ColoringBlockSizeReport implements GraphReportExtension {

  public static void main(String[] args) {
    int res = color(1, 1, 1, 1, 1, 3, new File("./matrices/hor_131.mtx"));
    System.out.println("No. of Colors: " + res);
  }


  public static int color(int Coloring, int Ordering, int Mode,
                          int RequiredPattern, int rho,
                          int blockSize, File file) {
    int Mode2=0;
    if (Ordering != 0) {
      if(Coloring==3) {
        Ordering += 3;
      }
    }

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

    Matrix mm = new Matrix(5,5);
    try {
      mm=MM.loadMatrixFromSPARSE(file);
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
      v.setColor(-1);
      g.addVertex(v);
      V_r.add(v.getId());
    }

    for(int i=0; i < cols;i++) {
      Vertex v = new Vertex();
      v.setColor(-1);
      g.addVertex(v);
      V_c.add(v.getId());
    }

    for(int i=0;i<rows;i++) {
      for(int j=0;j<cols;j++) {
        if(mm.get(i,j)==1) {
          g.addEdge(new Edge(g.getVertex(i), g.getVertex(rows + j)));
        }
      }
    }

    //new GraphData(Application.getBlackBoard()).core.showGraph(g);

    //Initialize required pattern
    //If edge e \in E_S then edge_property edge_weight=1 else
    switch (RequiredPattern) {
      case 0:
        break;
      case 1:
        for(Edge e : g.getEdges()) {
          if(Math.abs(e.source.getId()-e.target.getId())==rows) {
            e.setWeight(1);
          }
        }

        break;
      case 2:
        for(Edge e:g.getEdges()) {
          e.setWeight(1);
        }
        break;
      case 3:
        for(Edge e: g.getEdges()) {
          int RowCoordinate = e.source.getId()+rows;
          int ColumnCoordinate = e.target.getId();
          int RelativeDistance = RowCoordinate - ColumnCoordinate;
          int RowBlock = RowCoordinate / blockSize;
          int ColumnBlock = ColumnCoordinate / blockSize;
          if ((RelativeDistance < blockSize) && (RelativeDistance > -blockSize) && (RowBlock == ColumnBlock)) {
            e.setWeight(1);
          }
        }
        break;
    }

    //Presorting of the vertices
    if (Coloring == 1 || Coloring == 2 || Coloring == 3 || Coloring == 4 ||
            Coloring == 5 || Coloring == 6) {
      switch (Ordering) {
        case 0:
          break;
        case 1:
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

    //Coloring of the vertices
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
        System.out.println("StarBicoloringSchemeRestricted "
                + V_r.size() + " " +V_c.size() );
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

    return num_colors;
  }


  @Override
  public Object calculate(GraphModel g) {
    RenderTable ret = new RenderTable();

    Vector<String> titles = new Vector<>();
    titles.add(" Matrix ");
    for (int j = 1; j <= 8; j++) {
      titles.add(" " + 10 * j + " ");
    }
    ret.setTitles(titles);

    File dir = new File("./matrices");
    File[] directoryListing = dir.listFiles();
    if (directoryListing != null) {
      for (File child : directoryListing) {
        Vector<Object> v = new Vector<>();
        v.add(child.getName());
        for (int j = 1; j <= 8; j++) {
          int res = color(10, 1, 3, 3, 1, 10*j, child);
          v.add(res);
        }
        ret.add(v);
      }
    } else {
      System.out.println("error");
      // Handle the case where dir is not really a directory.
      // Checking dir.isDirectory() above would not be sufficient
      // to avoid race conditions with another process that deletes
      // directories.
    }

    //bls block size
    //int res = color();
    return ret;
  }

  @Override
  public String getCategory() {
    return "Bipartite Coloring";
  }

  @Override
  public String getName() {
    return "Block Size Comparison";
  }

  @Override
  public String getDescription() {
    return "Block Size Comparison";
  }

}