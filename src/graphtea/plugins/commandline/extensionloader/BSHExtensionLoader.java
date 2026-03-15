// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.commandline.extensionloader;

import graphtea.platform.core.BlackBoard;
import graphtea.platform.extension.Extension;
import graphtea.platform.extension.UnknownExtensionLoader;
import graphtea.plugins.commandline.Shell;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * This class loads extensions from .bsh files in extensions folder.
 */
public class BSHExtensionLoader implements UnknownExtensionLoader {
    // \graphtea\..\*.ext
    //    private String extFileName;
    Shell shell;

    public BSHExtensionLoader(Shell shell) {
        this.shell = shell;
    }

    public Extension ExtensionLoader(String extFileName) {
        StringBuilder eval = new StringBuilder();
        int bracketCount = 0;
        boolean lineComment = false;
        try (Scanner s = new Scanner(new File(extFileName))) {
            do {
                String line = s.nextLine();
                if (!lineComment) {
                    if (line.contains("/*")) {
                        line = line.substring(0, line.indexOf("/*"));
                        lineComment = true;
                    }
                    if (line.contains("//")) {
                        line = line.substring(0, line.indexOf("//"));
                    }
                    if (line.startsWith("package")) {
                        // do nothing
                    } else if (line.startsWith("public class")) {
                        line = line.substring(12); // removing "public class"
                        String[] tokens = line.split("[\\s,]+");
                        int ti = 0;
                        while (ti < tokens.length && !tokens[ti].equals("implements")) ti++;
                        ti++; // skip "implements" token
                        if (ti < tokens.length) {
                            eval.append("ex = new ").append(tokens[ti++]).append(" ()");
                            while (ti < tokens.length) eval.append(" ").append(tokens[ti++]);
                        }
                        eval.append("\n");
                    } else {
                        eval.append(line).append("\n");
                    }
                }
                if (line.contains("*/")) {
                    lineComment = false;
                    line = line.substring(line.indexOf("*/") + 2);
                    if (line.startsWith("package")) {
                        // do nothing
                    } else if (line.startsWith("public class")) {
                        line = line.substring(12); // removing "public class"
                        String[] parts = line.split(" +");
                        int pi = 0;
                        if (pi < parts.length) {
                            eval.append("ex = new ").append(parts[pi++]).append(" ()");
                            while (pi < parts.length) eval.append(" ").append(parts[pi++]);
                        }
                        eval.append("\n");
                    } else {
                        eval.append(line).append("\n");
                    }
                }
            } while (s.hasNextLine());
        } catch (FileNotFoundException e) {
            System.err.println("File not Found");
        }

        StringBuilder other = new StringBuilder();
        StringBuilder assignment = new StringBuilder();
        boolean isAssignment = false;
        int i = 0;
        try (Scanner s = new Scanner(eval.toString())) {
            while (s.hasNextLine()) {
                String l = s.nextLine();
                if ((l.contains("ex = new") && bracketCount == 0) || isAssignment) {
                    if (!l.isEmpty()) assignment.append(l).append("\n");
                    if (!isAssignment) {
                        i = bracketCount + countBrackets(l);
                        isAssignment = true;
                    }
                    bracketCount += countBrackets(l);
                    if (i > bracketCount) isAssignment = false;
                } else {
                    if (!l.isEmpty()) other.append(l).append("\n");
                    bracketCount += countBrackets(l);
                }
            }
        }

        shell.evaluate(other.toString());
        shell.evaluate(assignment.toString() + ";");
        shell.evaluate("import graphtea.ui.extension.*;");
        return (Extension) shell.evaluate("return (Extension)ex;");
    }

    private static int countBrackets(String l) {
        String l2 = l;
        String l1 = l;
        int count = 0;
        while (l1.contains("{")) {
            l1 = l1.substring(l1.indexOf("{") + 1);
            count++;
        }
        while (l2.contains("}")) {
            l2 = l2.replaceFirst("}", "-");
            count--;
        }
        return count;
    }


    public Extension load(File file, BlackBoard blackboard) {
        if (file.getName().endsWith(".bsh"))
            return ExtensionLoader(file.getAbsolutePath());
        return null;
    }
}
