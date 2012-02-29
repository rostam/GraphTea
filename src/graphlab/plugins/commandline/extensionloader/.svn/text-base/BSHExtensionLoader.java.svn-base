// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.commandline.extensionloader;

import graphlab.platform.core.BlackBoard;
import graphlab.platform.extension.Extension;
import graphlab.platform.extension.UnknownExtensionLoader;
import graphlab.plugins.commandline.Shell;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * This class loads extensions from .bsh files in extensions folder.
 */
public class BSHExtensionLoader implements UnknownExtensionLoader {
    // \graphlab\..\*.ext
    //    private String extFileName;
    Shell shell;

    public BSHExtensionLoader(Shell shell) {
        this.shell = shell;
    }

    public Extension ExtensionLoader(String extFileName) {
//        this.extFileName = extFileName;
        String eval = "";
        Scanner s = null;
        int bracketCount = 0;
        boolean lineComment = false;
        try {
            s = new Scanner(new File(extFileName));
        } catch (FileNotFoundException e) {
            System.err.println("File not Found");
        }
        do {
            String line = s.nextLine();

            if (!lineComment) {
                if (line.contains("/*")) {
                    if (!lineComment)
                        line = line.substring(0, line.indexOf("/*"));
                    lineComment = true;
                }
                if (line.contains("//")) {
                    if (!lineComment)
                        line = line.substring(0, line.indexOf("//"));
                }
                if (line.startsWith("package")) {
                    // do nothing
                } else if (line.startsWith("public class")) {
                    line = line.substring(12); // removing public class
                    StringTokenizer st = new StringTokenizer(line, " ,");
                    while (!st.nextToken().equals("implements")) ;
                    eval += "ex = new " + st.nextToken() + " ()";
                    while (st.hasMoreTokens()) {
                        eval += " " + st.nextToken();
                    }
                    eval += "\n";
//                    eval = eval.replaceFirst("{","(){");
                } else
                    eval += line + "\n";

            }
            if (line.contains("*/")) {
                lineComment = false;
                line = line.substring(line.indexOf("*/") + 2);
                if (line.startsWith("package")) {
                    // do nothing
                } else if (line.startsWith("public class")) {
                    line = line.substring(12); // removing public class
                    StringTokenizer st = new StringTokenizer(line, " ");
                    eval += "ex = new " + st.nextToken() + " ()";
                    while (st.hasMoreTokens()) {
                        eval += " " + st.nextToken();
                    }
                    eval += "\n";
//                        eval = eval.replaceFirst("{","(){");
                } else
                    eval += line + "\n";
            }
        }
        while (s.hasNextLine());
//        eval+=";";
        String other = "";
        s = new Scanner(eval);
        String assignment = "";
        boolean isAssignment = false;
        int i = 0;
        while (s.hasNextLine()) {
            String l = s.nextLine();
            if ((l.contains("ex = new") && bracketCount == 0) || (isAssignment)) {
                if (!l.equals(""))
                    assignment += l + "\n";
                if (!isAssignment) {
                    i = bracketCount + countBrackets(l);
                    isAssignment = true;
                }
                bracketCount += countBrackets(l);
                if (i > bracketCount)
                    isAssignment = false;
            } else {
                if (!l.equals(""))
                    other += l + "\n";
                bracketCount += countBrackets(l);

            }

        }
//        System.err.println("Other");
//        System.err.println("*******\n" + other + "\n*******");
//        System.err.println("Assignment");
//        System.err.println("*******\n" + assignment + "\n*******");
//        System.out.println("eval ******* \n " + eval);

        shell.evaluate(other);
        shell.evaluate(assignment + ";");
        shell.evaluate("import graphlab.ui.extension.*;");
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
