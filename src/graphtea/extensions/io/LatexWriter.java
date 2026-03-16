// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.io;
import graphtea.platform.core.exception.ExceptionHandler;

import graphtea.graph.graph.*;
import graphtea.plugins.main.saveload.core.GraphIOException;
import graphtea.plugins.main.saveload.core.extension.GraphWriterExtension;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author  Mohammad Ali Rostami
 * @email rostamiev@gmail.com
 */
public class LatexWriter implements GraphWriterExtension{

    public String getName() {
        return "Latex";
    }

    public String getExtension() {
        return "tex";
    }


    public void write(File file, GraphModel graph) throws GraphIOException {
        // Use try-with-resources so the FileWriter is always closed
        try (FileWriter output = new FileWriter(file)) {
            GRect r = graph.getAbsBounds();
            // Preamble: uses standard LaTeX picture environment.
            // \qbezier draws edges and works with all modern compilers (pdflatex,
            // xelatex, lualatex).  The old em:moveto / em:lineto specials only
            // worked with the emtex DVI driver and are silently ignored by pdflatex.
            output.write(
                    "\\documentclass[12pt,bezier]{article}\n" +
                            "\\textwidth = 15 cm\n" +
                            "\\textheight = 21.2 cm\n" +
                            "\\oddsidemargin = 0 cm\n" +
                            "\\evensidemargin = 0 cm\n" +
                            "\\topmargin = -1 cm\n" +
                            "\\parskip = 1.5 mm\n" +
                            "\\parindent = 5 mm\n" +
                            "\\def\\bfG{\\mbox{\\boldmath$G$}}\n" +
                            "\n" +
                            "\\title{" + graph.getLabel() + "}\n" +
                            "\\pagestyle{plain}\n" +
                            "\\author{GraphTea}\n" +
                            "\\date{}\n" +
                            "\n" +
                            "\\begin{document}\n" +
                            "\n" +
                            "\\begin{figure}[h]\n" +
                            "\\unitlength 0.7mm\n" +
                            "\\linethickness{0.4pt}\n" +
                            "\\begin{picture}(150,150)(0,0)\n" +
                            "%Vertices\n");

            StringBuilder vertices = new StringBuilder(" ");
            for (Vertex vm : graph)
                vertices.append("\\put(")
                        .append((vm.getLocation().getX() / r.getMaxX()) * 100)
                        .append(",")
                        .append((vm.getLocation().getY() / r.getMaxY()) * 100)
                        .append("){\\circle*{2}}\n");
            output.write(vertices.toString());

            StringBuilder edges = new StringBuilder();
            Iterator<Edge> em = graph.edgeIterator();
            while (em.hasNext()) {
                Edge e = em.next();
                final GPoint sx = e.source.getLocation();
                double x1 = (sx.getX() / r.getMaxX()) * 100;
                double y1 = (sx.getY() / r.getMaxY()) * 100;
                double x2 = (e.target.getLocation().getX() / r.getMaxX()) * 100;
                double y2 = (e.target.getLocation().getY() / r.getMaxY()) * 100;

                edges.append("%Edge Label:").append(e.getLabel()).append("\n");
                if (!graph.isEdgesCurved()) {
                    // Straight edge: \qbezier with midpoint as control point draws a
                    // straight line and works with pdflatex / xelatex / lualatex.
                    // (The old em:moveto / em:lineto specials only work with emtex.)
                    double mx = (x1 + x2) / 2;
                    double my = (y1 + y2) / 2;
                    edges.append("\\qbezier(")
                            .append(x1).append(",").append(y1).append(")(")
                            .append(mx).append(",").append(my).append(")(")
                            .append(x2).append(",").append(y2).append(")\n");
                } else {
                    double centerx = (sx.getX() + e.target.getLocation().getX()) / 2;
                    double centery = (sx.getY() + e.target.getLocation().getY()) / 2;
                    double cx = ((centerx + e.getCurveControlPoint().getX()) / r.getMaxX()) * 100;
                    double cy = ((centery + e.getCurveControlPoint().getY()) / r.getMaxY()) * 100;
                    edges.append("\\qbezier(")
                            .append(x1).append(",").append(y1).append(")(")
                            .append(cx).append(",").append(cy).append(")(")
                            .append(x2).append(",").append(y2).append(")\n");
                }
            }
            output.write(edges.toString());
            output.write("\n" +
                    "\\end{picture}\n" +
                    "\\end{figure}\n" +
                    "\\end{document}\n");

        } catch (IOException e) {
            ExceptionHandler.catchException(e);
        }
    }

    public String getDescription() {
        return "Latex File Format";
    }
}
