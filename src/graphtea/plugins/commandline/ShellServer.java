// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.commandline;

import bsh.ConsoleInterface;
import bsh.Interpreter;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * @author Mohamad Ali Rostami
 * @email mamaliam@gmail.com
 */

public class ShellServer {
    public static Thread thread;
    Shell shell;

    public ShellServer(BlackBoard bb, Shell shell) {
        this.shell = shell;
    }

    public void performJob(String eventKey, Object val) {
        thread = new Thread() {
            public void run() {
                try {
                    ServerSocket ss = new ServerSocket(1234);
                    final Socket s = ss.accept();
                    ConsoleInterface ci = new ConsoleInterface() {
                        Reader r = new InputStreamReader(s.getInputStream());

                        public Reader getIn() {
                            try {
                                return new InputStreamReader(s.getInputStream());
                            } catch (IOException e) {
                                ExceptionHandler.catchException(e);
                            }
                            return null;

                        }

                        public PrintStream getOut() {
                            try {
                                return new PrintStream(s.getOutputStream());
                            } catch (IOException e) {
                                ExceptionHandler.catchException(e);  //To change body of catch statement use File | Settings | File Templates.
                            }
                            return null;
                        }

                        public PrintStream getErr() {
                            try {
                                return new PrintStream(s.getOutputStream());
                            } catch (IOException e) {
                                ExceptionHandler.catchException(e);  //To change body of catch statement use File | Settings | File Templates.
                            }
                            return null;
                        }

                        public void println(Object object) {

                            this.getOut().println(object);
                        }

                        public void print(Object object) {
                            this.getOut().print(object);
                        }

                        public void error(Object object) {
                            this.getOut().println(object);
                        }
                    };

                    Interpreter interpreter = new Interpreter(ci);
                    interpreter.eval(shell.getEvaluations());
                    interpreter.set("me", shell.get("me"));
                    interpreter.set("current_interpreter", interpreter);
                    interpreter.run();
                } catch (Exception e) {
                    ExceptionHandler.catchException(e);
                }
            }
        };
        thread.start();
    }
}
