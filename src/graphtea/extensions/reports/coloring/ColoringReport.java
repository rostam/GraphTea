package graphtea.extensions.reports.coloring;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public class ColoringReport implements GraphReportExtension, Parametrizable {
    @Parameter(name = "Matrix")
    public String matrix = "mats/nos3.mtx";
    @Parameter(name = "Coloring Algorithm")
    public String algorithm = "PartialD2RestrictedColumns";
    @Parameter(name = "Ordering")
    public String ord = "LFO";
    @Parameter(name="Block Size")
    public String blockSize = "10";

  @Override
  public Object calculate(GraphModel g) {
      ProcessBuilder process = new ProcessBuilder("./Coloring", algorithm,
              ord, "Best", "1.0", "BlockDiagonal", blockSize, matrix);
      System.out.println(process.command());
      Process p = null;
      try {
          p = process.start();
          p.waitFor();
      } catch (IOException e) {
          e.printStackTrace();
      } catch (InterruptedException e) {
          e.printStackTrace();
      }

      BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
      String out = "",line = "";
      Vector<String> titles = new Vector<>();
      Vector<Object> values = new Vector<>();
      titles.add("Matrix");
      values.add(matrix);
      try {
          while ((line = bri.readLine()) != null) {
              out+=line+"\n";
              System.out.println(line);
              if(line.contains("Colors")
                      || line.contains("Rows")
                      || line.contains("Required")) {
                  titles.add(line.substring(0, line.indexOf(":")));
                  values.add(line.substring(line.indexOf(":") + 2));
              }
              if(line.contains("Additionally Required:")) break;
          }
      } catch (IOException e) {
          e.printStackTrace();
      }
      RenderTable rt = new RenderTable();
      rt.setTitles(titles);
      rt.add(values);


      return rt;
  }

  @Override
  public String getCategory() {
    return "Bipartite Coloring";
  }

  @Override
  public String getName() {
    return "PreCol 1.0";
  }

  @Override
  public String getDescription() {
    return "Ordering Comparison";
  }

    @Override
    public String checkParameters() {
        return null;
    }
}
