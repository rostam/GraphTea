// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.platform.extension;

import graphtea.platform.StaticUtils;
import graphtea.platform.core.exception.ExceptionHandler;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author azin azadi

 */
public class ExtensionClassLoader extends ClassLoader {
    public Map<String, byte[]> classesData = new HashMap<String, byte[]>();
    Map<String, Class> classes = new HashMap<String, Class>();
    Vector<File> unknownFiles = new Vector<File>();
    public static URLClassLoader classLoader;
    public static ClassLoader cl;

    public ExtensionClassLoader(String dirPath) {
        loadClasses(dirPath);
    }

    public Collection<Class> getLoadedClasses() {
        return classes.values();
    }

    public Vector<File> getUnknownFilesFound() {
        return unknownFiles;
    }


    Vector<URL> urls;

    private void loadClassFiles(String dir, String pack) {
        urls = new Vector<URL>();
        File file = new File(dir);
        if (!file.exists())
            return;
        File files[] = file.listFiles();
        for (File f1:files){
            if (f1.getName().endsWith(".zip")){
                unZip(f1.getAbsolutePath(), f1.getParentFile().getAbsolutePath());
            }
        }
        files = file.listFiles();
        for (File file1 : files)
            if (file1.isDirectory())
                loadClassFiles(dir + "/" + file1.getName(), pack + "." + file1.getName());
            else if (file1.getName().endsWith(".class"))
                try {
                    String name;
                    if (pack.length() > 0)
                        name = pack.substring(1) + ".";
                    else
                        name = "";
                    name = name + file1.getName().substring(0, file1.getName().length() - 6);
                    byte data[] = new byte[(int) file1.length()];
                    FileInputStream fis = new FileInputStream(file1);
                    fis.read(data);
                    classesData.put(name, data);
                    urls.add(file1.toURL());
                }
                catch (FileNotFoundException e) {
                    ExceptionHandler.catchException(e);
//                    ExceptionHandler.handleException(e);
                }
                catch (IOException e) {
                    ExceptionHandler.catchException(e);
//                    ExceptionHandler.handleException(e);
                }
            else {
                unknownFiles.add(file1);
            }

    }

    private void loadClasses(String dirPath) {
        loadClassFiles(dirPath, "");
//        defineClasses();
    }

    public Class findClass(String name) throws ClassNotFoundException {
        Class ret = null;
        if (!classesData.containsKey(name))
            ret = getParent().loadClass(name);
        if (ret == null && !classes.containsKey(name)) {
            byte data[] = (byte[]) classesData.get(name);
            Class c = defineClass(name, data, 0, data.length);
            classes.put(name, c);
        }
        ret = classes.get(name);
        if (ret == null)
            return classLoader.loadClass(name);
        else
            return ret;
    }

    public Collection getClassesImplementing(Class cl) {
        Collection col = new Vector();
        for (Map.Entry<String, Class> entry1 : classes.entrySet()) {
            Map.Entry entry = (Map.Entry) entry1;
            Class c = (Class) entry.getValue();
            if (StaticUtils.isImplementing(c, cl))
                col.add(c);
        }

        return col;
    }

    public static void copyInputStream(InputStream in, OutputStream out)
    throws IOException
    {
      byte[] buffer = new byte[1024];
      int len;

      while((len = in.read(buffer)) >= 0)
        out.write(buffer, 0, len);

      in.close();
      out.close();
    }


    /**
     * this part of code has get from: http://www.devx.com/getHelpOn/10MinuteSolution/20447
     * @param zipFileName
     */
    public static void unZip(String zipFileName, String destDir) {
        System.out.println(zipFileName);
      Enumeration entries;
      ZipFile zipFile;

      try {
        zipFile = new ZipFile(zipFileName);

        entries = zipFile.entries();

        while(entries.hasMoreElements()) {
          ZipEntry entry = (ZipEntry)entries.nextElement();

          if(entry.isDirectory()) {
            // Assume directories are stored parents first then children.
//            System.err.println("Extracting directory: " + entry.getName());
            // This is not robust, just for demonstration purposes.
            (new File(destDir+File.separator+entry.getName())).mkdirs();
            continue;
          }

          System.out.println("Extracting file: " + destDir+entry.getName());
          copyInputStream(zipFile.getInputStream(entry),
             new BufferedOutputStream(new FileOutputStream(destDir+File.separator+entry.getName())));
        }

        zipFile.close();
      } catch (IOException ioe) {
        System.err.println("Unhandled exception:");
        ioe.printStackTrace();
        return;
      }
    }


}
