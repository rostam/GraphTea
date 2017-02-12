// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.commandline.parsers;


import graphtea.plugins.commandline.Shell;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Mohammad Ali Rostami
 * @email rostamiev@gmail.com
 */

public class DefaultParser implements ExtParser {
    HashMap<String, String> methods = new HashMap<>();
    boolean is_initialized = false;
    Shell shell;

    public DefaultParser(Shell shell) {
        this.shell = shell;
    }


    public String getName() {
        return "default parser";
    }

    String clearStringOfNewline(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '\n') {
                String t = s.substring(0, s.indexOf('\n'));
                if (s.indexOf('\n') != -1
                        && s.indexOf('\n') + 1 <= s.length())
                    s = t + s.substring(s.indexOf('\n') + 1);
                else
                    s = t;
            }
        }
        return s;
    }

    public int count(String s, char c) {
        int count = 0;
        for (int i = 0; i < s.length(); i++)
            if (s.charAt(i) == c) count++;
        return count;
    }

    public String correct(String s) {
        if (!s.contains("(")) return s;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                int fp, ep;
                if (s.substring(0, i).contains(",")) {
                    fp = s.substring(0, i).lastIndexOf(",") + 1;
                    if (s.substring(fp, i).contains("["))
                        fp = fp + s.substring(fp, i).lastIndexOf("[") + 1;
                } else if (s.substring(0, i).contains("[")) fp = s.lastIndexOf("[") + 1;
                else fp = 0;
                ep = eatExtra(s, i, '(', ')');

                Object o = eval(s.substring(fp, ep + 1));
                String t1;
                if (o instanceof String)
                    t1 = s.substring(0, fp) + "\"" + o + "\"";
                else t1 = s.substring(0, fp) + o;
                String t2 = "";
                if (ep != s.length()) t2 = s.substring(ep + 1);
                s = t1 + t2;
                i = t1.length();

            }
        }
        return s;
    }

    Object eval(String s) {
        shell.evaluate("___ = " + s.trim() + ";");
        return shell.get("___");
    }

    public Object[] parseSet(String sets) {
        sets = sets.trim();
        HashSet set = new HashSet();
        boolean aa = false;

        if (!sets.contains(",") && !sets.contains("[")) {
            shell.evaluate("___ = " + sets.trim());
            set.add(shell.get("___"));
            return set.toArray();
        }

        for (int i = 0; i < sets.length(); i++) {

            if (sets.charAt(i) == '[') {
                String t = sets.substring(i);
                int j = eatExtraBracket(t, 0);
                set.add(parseSet(sets.substring(i + 1
                        , i + j)));
                if (!sets.substring(i + 1).contains(","))
                    aa = true;
                i = i + j;

                aa = true;
            }

            if (sets.charAt(i) == ',') {
                if (sets.substring(0, i).contains(",")) {
                    String s = "___ = "
                            + sets.substring
                            (
                                    sets.substring(0, i).lastIndexOf(',') + 1
                                    , i).trim();
                    shell.evaluate(s);
                } else {
                    String s1 = "___ = "
                            + sets.substring(0
                            , i).trim();
                    shell.evaluate(s1);
                }
                set.add(shell.get("___"));
            }
        }
        if (!aa) {
            String iii = sets.substring(sets.lastIndexOf(",") + 1);
            shell.evaluate("___ = " + iii.trim());
            set.add(shell.get("___"));
        }
        return set.toArray();
    }


    public void initialize() {
        shell.set_variable("temp_this", this);
        shell.evaluate("Object[] parseSet(String s) {" +
                "s = temp_this.correct(s);" +
                "return temp_this.parseSet(s);" +
                "}");
        //methods.put("graph", "graph(String s){print(s);}");
    }


    public void add(String name, String method) {
        shell.evaluate(method);
    }

    public int eatExtraBracket(String s, int start) {
        return eatExtra(s, start, '[', ']');
    }

    public int eatExtra(String s, int start, char c1, char c2) {
        int lb = 1, rb = 0;
        for (int i = start + 1; i < s.length(); i++) {
            if (s.charAt(i) == c1) lb++;
            if (s.charAt(i) == c2) rb++;
            if (lb == rb) return i;
        }
        return -1;
    }

    public String parse(String statement) {
        if (statement.startsWith("[")) return ";";
        statement = clearStringOfNewline(statement);
        if (!is_initialized) initialize();
        if (statement.contains("[")) {
            int bracketIndex = statement.indexOf("[");
            String p = statement.substring(0, bracketIndex);
            p = p.trim();
            if (p.length() != 0) {
                if (p.charAt(p.length() - 1) == '='
                        || p.charAt(p.length() - 1) == '(' || p.charAt(p.length() - 1) == ',') {
                    int rbracet = eatExtraBracket(statement, bracketIndex);
                    statement =
                            statement.substring(0, bracketIndex)
                                    +
                                    "parseSet(\""
                                    +
                                    statement.substring(bracketIndex + 1
                                            , rbracet)
                                    + "\")" + statement.substring(rbracet + 1);
                }
            }
        }
//        System.out.println("modifying statement: " + statement);
        return statement;
    }
}
