// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.commandline.parsers;

import bsh.Interpreter;
import graphtea.plugins.commandline.Shell;


/**
 * @author Mohammad Ali Rostami
 * @email rostamiev@gmail.com
 */

//overload_multiply(a1,a2)
public class OperatorParser extends DefaultParser {
    public OperatorParser(Interpreter interpreter, Shell shell) {
        super(shell);
    }
}

/*  public OperatorParser(Interpreter interpreter , BSHFile file) {
    super(interpreter);
    try {
        interpreter.source(file.getAbsolutePath());
    } catch (IOException e) {
        ExceptionHandler.catchException(e);
    } catch (EvalError evalError) {
        evalError.printStackTrace();
    }
}

public void add(BSHFile file)
{
    try {
        interpreter.source( file.getAbsolutePath());
    } catch (IOException e) {
        ExceptionHandler.catchException(e);  //To change body of catch statement use File | Settings | File Templates.
    } catch (EvalError evalError) {
        evalError.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
}

public String parse(String s)
{
    String previous = "";
    for(int i=0;i < s.length();i++)
    {
        if(s.charAt(i) == '*')
        {
            int number = 0;
            for(int j=i; j >= 0; j--)
            {
                if(s.charAt(j) == ' ')
                {

                }
            }
        }

        previous = "";
    }
    return super.parse(s);
}
}
*/