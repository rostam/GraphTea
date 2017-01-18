// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.commandline.util.codecompletionutils;

import bsh.EvalError;
import bsh.Interpreter;
import graphtea.platform.parameter.Parameter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Vector;

/**
 * @author Mohammad Ali Rostami
 * @email rostamiev@gmail.com
 */
public class CodeCompletionUtils {
    public static Vector<String> complete(HashMap<String, String> abbrs, String part) {
        Vector<String> ret = new Vector<String>();
        if (abbrs.get(part) != null)
            ret.add(abbrs.get(part) + "(");
        else {
            for (String t : abbrs.keySet())
                if (t.startsWith(part))
                    ret.add(t + " : " + abbrs.get(t));
            if (ret.size() == 1)
                ret.set(0, ret.get(0).substring(ret.get(0).indexOf(":") + 2));
        }
        return ret;
    }

    /**
     * point completion
     */
    public static Vector<String> complete(String part, Interpreter interpreter) {
        int pointCount = 0;
        Vector<String> ret = new Vector<String>();
        for (int i = 0; i < part.length(); i++)
            if (part.charAt(i) == '.') pointCount++;

        if (pointCount == 1) {
            try {
                Class c = interpreter.get(part.substring(0, part.lastIndexOf("."))).getClass();
                String t = part.substring(part.indexOf(".") + 1);
                if (c.getMethods() != null)
                    for (Method m : c.getMethods())
                        if (m.getName().startsWith(t))
                            ret.add(part.substring(0, part.lastIndexOf(".")) + "." + m.getName());
            } catch (Exception e) {
                //evalError.printStackTrace();
            }
        } else {
            try {
                Class c = interpreter.eval(part.substring(0, part.lastIndexOf("."))).getClass();
                String t = part.substring(part.lastIndexOf(".") + 1);
                for (Method m : c.getMethods())
                    if (m.getName().startsWith(t))
                        ret.add(part.substring(0, part.lastIndexOf(".")) + "." + m.getName());
            } catch (Exception e) {
                //evalError.printStackTrace();
            }
        }
        return ret;
    }

    //argumentCompletion
    public static Vector<String> complete(String part
            , Interpreter interpreter, HashMap<String, Method> commands
            , HashMap<String, Class> ext_commands) {
        Vector<String> ret = new Vector<String>();
        if (part.contains(".")) {
            Method[] ms = new Method[0];
            try {
                ms = interpreter.eval(part.substring(0, part.lastIndexOf("."))).getClass().getMethods();
            } catch (EvalError evalError) {
                //evalError.printStackTrace();
            }

            for (Method m : ms) {
                String result = "";
                result += part;
                if (m.getName().equals(part.substring(part.lastIndexOf(".") + 1, part.length() - 1))) {
                    for (Class c : m.getParameterTypes())
                        result += (c.getSimpleName() + ",");
                    if (!result.equals(part))
                        result = result.substring(0, result.length() - 1) + ");";
                    else result += ");";
                    ret.add(result);
                }
            }
        } else {
            if (commands.containsKey(part.substring(0, part.length() - 1))) {
                for (String t : commands.keySet()) {
                    String result = "";
                    result += part;
                    if (t.equals(part.substring(0, part.length() - 1))) {
                        Method method = commands.get(t);
                        Annotation[][] pA = method.getParameterAnnotations();
                        int index = 0;
                        for (Annotation[] v : pA) {
                            for (Annotation a : v) {
                                if (a.annotationType().equals(Parameter.class)) {
                                    Parameter pn = (Parameter) a;

                                    String type = method.getParameterTypes()[index].getSimpleName();
                                    result += (pn.name() + "(" + type + "), ");
                                }
                            }
                            index++;
                        }

                        if (!result.equals(part)) {
                            result = result.substring(0, result.length() - 1);
                            result = result.substring(0, result.length() - 1) + ");";
                        } else result += ");";
                        ret.add(result);
                    }
                }

            } else {
                Vector<String> ret1 = new Vector<String>();
                for (String t : ext_commands.keySet()) {
                    String result = part;

                    if (ext_commands.get(t) == null) {
                        if (t.startsWith(part))
                            ret.add(t);
                    } else if (t.equals(part.substring(0, part.length() - 1))) {
                        Class clazz = ext_commands.get(t);
                        //Method method = commands.get(t);
                        for (Field f : clazz.getFields()) {
                            Parameter p = f.getAnnotation(Parameter.class);
                            if (p != null) {
                                String type = f.getType().getSimpleName();
                                result += (p.name() + "(" + type + "), ");
                            }
                        }

                        if (!result.equals(part)) {
                            result = result.substring(0, result.length() - 1);
                            result = result.substring(0, result.length() - 1) + ");";
                        } else result += ");";
                        ret1.add(result);
                    }
                }
                ret.addAll(ret1);
            }
        }
        return ret;
    }
}
