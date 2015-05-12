// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.platform;

import graphtea.graph.graph.GraphPoint;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionOccuredData;
import graphtea.platform.extension.ExtensionLoader;
import graphtea.platform.lang.ArrayX;
import graphtea.platform.lang.FromStringProvider;
import graphtea.platform.preferences.lastsettings.StorableOnExit;

import java.awt.*;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

/**
 * some usefill and handy static utilities.
 *
 * @author Azin Azadi
 * @author Reza Mohammadi mohammadi
 */
public class StaticUtils {

    /**
     * The linear Operation AX+B
     *
     * @param a
     * @param b
     * @param x
     * @return
     * @throws Exception
     */
    public Double[] linearOperation(Double[] a, Double[] b, Double[] x)
            throws Exception {
        if ((a.length != b.length) || (a.length != x.length)) {
            throw new Exception("Problem in dimensions!");
        }
        int n = a.length;
        Double[] result = new Double[n];
        for (int i = 0; i < n; i++)
            result[i] = (double) (a[i] * x[i] + b[i]);
        return result;
    }

    public static void putInJar(File directory, JarOutputStream jos, String prefix) throws IOException, FileNotFoundException, Exception {
        FileInputStream fis;
        File[] files = directory.listFiles();

        for (int n = 0; n < files.length; n++) {

            if (files[n].isDirectory()) {
                putInJar(files[n], jos, prefix + files[n].getName() + "/");
            } else {
                jos.putNextEntry(new JarEntry(prefix + files[n].getName()));
                fis = new FileInputStream(files[n]);

                copyStream(fis, jos);

                fis.close();
                jos.closeEntry();
            }
        }
    }

    public static void copyFile(File in, File out) throws Exception {
        FileInputStream fis = new FileInputStream(in);
        FileOutputStream fos = new FileOutputStream(out);
        byte[] buf = new byte[1024];
        int i = 0;
        while ((i = fis.read(buf)) != -1) {
            fos.write(buf, 0, i);
        }
        fis.close();
        fos.close();
    }

    public static void copyStream(InputStream is, OutputStream out) throws Exception {
        byte[] buf = new byte[1024];
        int i = 0;
        while ((i = is.read(buf)) != -1) {
            out.write(buf, 0, i);
        }
    }

    /**
     * @param classname
     * @param data
     * @return the actual object which is a 'classname' object and have toString: 'data'
     */
    public static Object fromString(String classname, String data) {
        Object o = data;
        if ("null".equalsIgnoreCase(data)
                && !(String.class.getName().equals(classname)))
            return null;

        if (String.class.getName().equals(classname)) return data;

        if (Integer.class.getName().equals(classname)) return new Integer(data);

        if (BigInteger.class.getName().equals(classname)) return new BigInteger(data);

        if (Double.class.getName().equals(classname)) return new Double(data);

        if (Character.class.getName().equals(classname)) return new Character(data.charAt(0));

        if (Color.class.getName().equals(classname)) return color(data);

        if (BigDecimal.class.getName().equals(classname)) return new BigDecimal(data);

        if (Boolean.class.getName().equals(classname)) return new Boolean(data);

        if (classname.equals(Font.class.getName())) return str2Font(data);

        if (classname.equals(ArrayX.class.getName())) return ArrayX.fromString(data);

        if (classname.equals(File.class.getName())) return new File(data);
        
        FromStringProvider fromStringProvider = pr.get(classname);
        if (fromStringProvider == null)
            return null;
        else{
            return fromStringProvider.fromString(data);
        }
    }

    static HashMap<String, FromStringProvider> pr = new HashMap<String, FromStringProvider>();

    /**
     * Adds a new FromStringProvider to current ones.
     *
     * @param className
     * @param pro
     */
    public static void setFromStringProvider(String className, FromStringProvider pro) {
        pr.put(className, pro);
    }

    private static Color color(String data) {
        Color o;
        data = data.replace('[', ' ');
        data = data.replace(']', ' ');
        data = data.replace('.', ' ');
        data = data.replace('.', ' ');
        data = data.replace('.', ' ');
        data = data.replace('=', ' ');
        data = data.replace('=', ' ');
        data = data.replace('=', ' ');
        data = data.replace(',', ' ');
        data = data.replace(',', ' ');
        data = data.replace(',', ' ');
        Scanner sc = new Scanner(data);
        sc.next("java");
        sc.next("awt");
        sc.next("Color");
        sc.next("r");
        int r = sc.nextInt();
        sc.next();
        int g = sc.nextInt();
        sc.next();
        int b = sc.nextInt();
        sc.close();
        o = new Color(r, g, b);
        return o;
    }

    /**
     * converts a String that generated with standard toString() of a font object to a Font Object
     * a sample input is: "java.awt.Font[family=Dialog,name=tahoma,style=plain,size=12]"
     */
    public static Font str2Font(String fontToStringed) {
        String data = fontToStringed;
        data = data.replace('[', ' ');
        data = data.replace(']', ' ');
        data = data.replace('.', ' ');
        data = data.replace('.', ' ');
        data = data.replace('.', ' ');
        data = data.replace('=', ' ');
        data = data.replace('=', ' ');
        data = data.replace('=', ' ');
        data = data.replace(',', ' ');
        data = data.replace(',', ' ');
        data = data.replace(',', ' ');
        Scanner sc = new Scanner(data);
        sc.next("java");
        sc.next("awt");
        sc.next("Font");
        sc.next("family");
        String family = sc.next();
        sc.next("name");
        String name = sc.next();
        sc.next("style");
        String _style = sc.next();
        sc.next("size");
        int size = sc.nextInt();
        int style;

//        this is the original part of to string that creates the style string
//        if (isBold()) {
//            strStyle = isItalic() ? "bolditalic" : "bold";
//        } else {
//            strStyle = isItalic() ? "italic" : "plain";
//        }

        if (_style.equals("bolditalic"))
            style = Font.BOLD | Font.ITALIC;
        else if (_style.equals("italic"))
            style = Font.ITALIC;
        else if (_style.equals("bold"))
            style = Font.BOLD;
        else
            style = Font.PLAIN;
        Font f = new Font(name, style, size);
        return f;
    }


    /**
     * loads(includes its automatically generated menues, ...) a single extension into application
     *
     * @param s
     * @param blackboard
     */
    public static void loadSingleExtension(Class s) {
        Object extension = ExtensionLoader.loadExtension(s);
        if (extension != null) {
            StorableOnExit.SETTINGS.registerSetting(extension, "Extention Options");
            ExtensionLoader.handleExtension(Application.blackboard, extension);
        }
    }

    /**
     * return the Application Instance which created b during initialization
     *
     * @param blackboard
     * @return
     */
    public static Application getApplicationInstance(BlackBoard blackboard) {
        return blackboard.getData(Application.APPLICATION_INSTANCE);
    }

    public static void addExceptiontoLog(Throwable e, BlackBoard b) {
        ExceptionOccuredData ee = new ExceptionOccuredData(e);
        if (b != null)
            b.setData(ExceptionOccuredData.EVENT_KEY, ee);
        e.printStackTrace();
    }

    public static void addExceptionLog(Throwable e) {
        addExceptiontoLog(e, Application.getBlackBoard());
    }

    public static boolean isImplementing(Class cl, Class inter) {
        if (cl.equals(inter))
            return true;
        Class[] interfaces = cl.getInterfaces();
        for (Class anInterface : interfaces)
            if (isImplementing(anInterface, inter))
                return true;

        return false;
    }

    public static void browse(String url) {
        BareBonesBrowserLaunch.openURL(url);
    }
}

class BareBonesBrowserLaunch {

    static final String[] browsers = {"google-chrome", "firefox", "opera",
            "epiphany", "konqueror", "conkeror", "midori", "kazehakase", "mozilla"};
    static final String errMsg = "Error attempting to launch web browser";

    public static void openURL(String url) {
        try {  //attempt to use Desktop library from JDK 1.6+
            Class<?> d = Class.forName("java.awt.Desktop");
            d.getDeclaredMethod("browse", new Class[]{java.net.URI.class}).invoke(
                    d.getDeclaredMethod("getDesktop").invoke(null),
                    new Object[]{java.net.URI.create(url)});
            //above code mimicks:  java.awt.Desktop.getDesktop().browse()
        } catch (Exception ignore) {  //library not available or failed
            String osName = System.getProperty("os.name");
            try {
                if (osName.startsWith("Mac OS")) {
                    Class.forName("com.apple.eio.FileManager").getDeclaredMethod(
                            "openURL", new Class[]{String.class}).invoke(null,
                            new Object[]{url});
                } else if (osName.startsWith("Windows"))
                    Runtime.getRuntime().exec(
                            "rundll32 url.dll,FileProtocolHandler " + url);
                else { //assume Unix or Linux
                    String browser = null;
                    for (String b : browsers)
                        if (browser == null && Runtime.getRuntime().exec(new String[]
                                {"which", b}).getInputStream().read() != -1)
                            Runtime.getRuntime().exec(new String[]{browser = b, url});
                    if (browser == null)
                        throw new Exception(Arrays.toString(browsers));
                }
            } catch (Exception e) {
            }
        }
    }

}


