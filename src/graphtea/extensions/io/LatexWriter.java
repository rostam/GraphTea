// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.io;

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


    public void write(File file, GraphModel graph) {
        FileWriter output;
        try {
            output = new FileWriter(file);
            GRect r = graph.getAbsBounds();
            output.write(
                    "\\documentclass[12pt,bezier]{article}\n" +
                            "\\textwidth = 15 cm\n" +
                            "\\textheight = 21.2 cm\n" +
                            "\\oddsidemargin = 0 cm\n" +
                            "\\evensidemargin = 0 cm\n" +
                            "\\topmargin = -1 cm\n" +
                            "\\parskip = 1.5 mm\n" +
                            "\\parindent = 5 mm\n" +
                            "%\n" +
                            "\\def\\bfG{\\mbox{\\boldmath$G$}}\n" +
                            "\n" +
                            "\\title{" + graph.getLabel() + "}\n" +
                            "\\input{epsf}\n" +
                            "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n" +
                            "\\pagestyle{plain}\n" +
                            "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n" +
                            "\\def\\emline#1#2#3#4#5#6{\\put(#4,#5){\\special{em:lineto}}}\n" +
                            "\\def\\newpic#1{}\n" +
                            "%\n" +
                            "\n" +
                            "\\author{GraphTea}\n" +
                            "%\n" +
                            "\\date{}\n" +
                            "\n" +
                            "\\begin{document}\n" +
                            "\n" +
                            "\\begin{figure}[h]\n" +
                            "%\n" +
                            "\\def\\emline#1#2#3#4#5#6{%\n" +
                            "%\n" +
                            "\\put(#1,#2){\\special{em:moveto}}%\n" +
                            "%\n" +
                            "\\put(#4,#5){\\special{em:lineto}}}\n" +
                            "%\n" +
                            "\\def\\newpic#1{}\n" +
                            "%\n" +
                            "%\\pagestyle{empty}\n" +
                            "%\n" +
                            "%\\begin{document}\n" +
                            "%\n" +
                            "\\unitlength 0.7mm\n" +
                            "%\n" +
                            "\\special{em:linewidth 0.4pt}\n" +
                            "%\n" +
                            "\\linethickness{0.4pt}\n" +
                            "%\n" +
                            "\\begin{picture}(150,150)(0,0)\n" +
                            "%\n" +
                            "%Vertices\n");

            StringBuilder vertices = new StringBuilder(" ");
            for (Vertex vm : graph)
                vertices.append("\\put(").append((vm.getLocation().getX() / r.getMaxX()) * 100).append(",").append((vm.getLocation().getY() / r.getMaxY()) * 100).append("){\\circle*{2}}\n");
            output.write(vertices.toString());

            StringBuilder edges = new StringBuilder();
            Iterator<Edge> em = graph.edgeIterator();
            while (em.hasNext()) {
                Edge e = em.next();
                final GPoint sx = e.source.getLocation();
                if (!graph.isEdgesCurved()) {
                    edges.append("%Edge Label:").append(e.getLabel()).append("\n");
                    edges.append("\\emline{").append((sx.getX() / r.getMaxX()) * 100).append("}{").append((sx.getY() / r.getMaxY()) * 100).append("}{1}{").append((e.target.getLocation().getX() / r.getMaxX()) * 100).append("}{").append((e.target.getLocation().getY() / r.getMaxY()) * 100).append("}{2}\n");
                } else {
                    edges.append("%Edge Label:").append(e.getLabel()).append("\n");
                    double centerx, centery;
                    centerx = (sx.getX() + e.target.getLocation().getX()) / 2;
                    centery = (sx.getY() + e.target.getLocation().getY()) / 2;
                    double cx = ((centerx + e.getCurveControlPoint().getX()) / r.getMaxX()) * 100;
                    double cy = ((e.getCurveControlPoint().getY() + centery) / r.getMaxY()) * 100;
                    edges.append("\\bezier{500}(").append((sx.getX() / r.getMaxX()) * 100).append(",").append((sx.getY() / r.getMaxY()) * 100).append(")(").append(cx).append(",").append(cy).append(")(").append((e.target.getLocation().getX() / r.getMaxX()) * 100).append(",").append((e.target.getLocation().getY() / r.getMaxY()) * 100).append(")\n");
                }
            }
            output.write(edges.toString());
            output.write("\n" +
                    "\\end{picture}\n" +
                    "\\end{figure}\n" +
                    "\\end{document}\n"
            );

            output.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDescription() {
        return "Latex File Format";
    }
}
