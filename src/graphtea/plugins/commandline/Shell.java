// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.commandline;

import bsh.EvalError;
import bsh.Interpreter;
import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.Listener;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.plugins.commandline.commands.EdgeCommands;
import graphtea.plugins.commandline.commands.GraphCommands;
import graphtea.plugins.commandline.commands.NativeCommands;
import graphtea.plugins.commandline.commands.VertexCommands;
import graphtea.plugins.commandline.parsers.InwardCommandParser;
import graphtea.plugins.main.GraphData;
import graphtea.ui.UIUtils;

import java.util.HashMap;

/**
 * as far as i could figure out by now, this class controls the shell of the program( :p heh, smart! )
 * when the shell is called, current blackboard object is passed and class' constructor assigns it to the
 * bb parameter
 * @author Mohamad Ali Rostami
 * @email ma.rostami@yahoo.com
 */

public class Shell {
    public static final String event = UIUtils.getUIEventKey("RunShell");
    static final String NAME = "SHELL BLKBRD";
    int newvar_maker = 0;
    GraphData data;
    Interpreter main_interpreter;
    public ShellConsole ext_console;
    BlackBoard bb;
    String evaluations;
    //it may be a HashMap containing all of the commands
    public HashMap<String, Class> code_completion_dictionary = new HashMap<String, Class>();
    public InwardCommandParser parser;

    public Shell(BlackBoard blackBoard) {
        data = new GraphData(blackBoard);
        this.bb = blackBoard;
    }

    public Object evaluate(String s) {
        System.out.println(s);
        evaluations += s;
        try {
            return main_interpreter.eval(s);
        } catch (EvalError evalError) {
            evalError.printStackTrace();
        }
        return null;
    }

    public void addCodeCompletionDictionary(String s, Class c) {
        code_completion_dictionary.put(s, c);
    }

    public Object evaluateCommand(String s, String name, String abbr) {
        return parser.evaluateCommand(s, name, abbr);
    }

    public void set_variable(String s, Object o) {
        try {
            main_interpreter.set(s, o);
        } catch (EvalError evalError) {
            evalError.printStackTrace();
        }
    }

    public Object get(String s) {
        try {
            return main_interpreter.get(s);
        } catch (EvalError evalError) {
            evalError.printStackTrace();
        }
        return null;
    }

    public String getEvaluations() {
        return evaluations;
    }

    public String newVariable() {
        newvar_maker++;
        return "_newvar_" + newvar_maker;
    }


    public void performJob(String name) {

        bb.addListener(GraphAttrSet.name, new Listener() {
            public void keyChanged(String key, Object value) {
                GraphModel gm = bb.getData(GraphAttrSet.name);
                try {
                    main_interpreter.set(gm.getLabel(), gm);
                } catch (EvalError evalError) {
                    evalError.printStackTrace();
                }
            }
        });
        evaluations += "clr(){console.clear();}";
        evaluations = "import graphtea.graph.graph.*;" + evaluations;

        ext_console = (ShellConsole) UIUtils.getComponent(bb, "ShellSideBar");
        ext_console.shell = this;
        final ShellConsole console = ext_console;
        main_interpreter = new Interpreter(console);
        parser = new InwardCommandParser(main_interpreter, this);
        parser.addCommands(new GraphCommands(bb));
//        parser.addCommands(new ShellServerCommands(bb));
        parser.addCommands(new VertexCommands(bb));
        parser.addCommands(new EdgeCommands(bb));
        parser.addCommands(new NativeCommands(bb));
        evaluations = InwardCommandParser.evaluations;
        ShellCodeCompletion code_completion = new ShellCodeCompletion(main_interpreter, parser.commands, parser.abbrs, code_completion_dictionary);
        console.setNameCompletion(code_completion);

        new Thread() {
            public void run() {
                try {
                    main_interpreter.set("abbreviations", parser.abbrs);
                    main_interpreter.set("code_completion_dictionary", code_completion_dictionary);
                    main_interpreter.set("evaluations", evaluations);
                    main_interpreter.set("console", console);
                    main_interpreter.set("blackboard", bb);
                    main_interpreter.set("graphdata", new GraphData(bb));
                    parser.abbrs.put("_clr", "clr");
//                    main_interpreter.set("me", Shell.this);
                    main_interpreter.eval(evaluations);
                }
                catch (EvalError evalError) {
                    evalError.printStackTrace();
                }
                main_interpreter.run();

            }
        }.start();

        //for not printing 'by niemeyer'
       new Thread() {

            public void run() {
                try {
                    Thread.sleep(1000);
                    console.clear();
                    console.print("bsh % ");
                } catch (InterruptedException e) {
                    ExceptionHandler.catchException(e);
                }
            }
        }.start();
    }

    /**
     * @param b
     * @return the available shell for b. (normally the working shell of application)
     */
    public static Shell getCurrentShell(BlackBoard b) {
        return b.getData(NAME);
    }

}