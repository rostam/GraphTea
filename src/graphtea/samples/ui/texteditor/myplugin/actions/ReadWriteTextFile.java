// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.samples.ui.texteditor.myplugin.actions;
import graphtea.platform.core.exception.ExceptionHandler;

import java.io.*;

// this file is taken from: http://www.javapractices.com/Topic42.cjp
public class ReadWriteTextFile {

    /**
     * Fetch the entire contents of a text file, and return it in a String.
     * This style of implementation does not throw Exceptions to the caller.
     *
     * @param aFile is a file which already exists and can be read.
     */
    static public String getContents(File aFile) {
        //...checks on aFile are elided
        StringBuilder contents = new StringBuilder();

        try (BufferedReader input = new BufferedReader(new FileReader(aFile))) {
            String line;
            while ((line = input.readLine()) != null) {
                contents.append(line).append(System.lineSeparator());
            }
        } catch (IOException ex) {
            ExceptionHandler.catchException(ex);
        }
        return contents.toString();
    }

    /**
     * Change the contents of text file in its entirety, overwriting any
     * existing text.
     * <p/>
     * This style of implementation throws all exceptions to the caller.
     *
     * @param aFile is an existing file which can be written to.
     * @throws IllegalArgumentException if param does not comply.
     * @throws FileNotFoundException    if the file does not exist.
     * @throws IOException              if problem encountered during write.
     */
    static public void setContents(File aFile, String aContents)
            throws FileNotFoundException, IOException {
        if (aFile == null) {
            throw new IllegalArgumentException("File should not be null.");
        }
        if (!aFile.exists()) {
            throw new FileNotFoundException("File does not exist: " + aFile);
        }
        if (!aFile.isFile()) {
            throw new IllegalArgumentException("Should not be a directory: " + aFile);
        }
        if (!aFile.canWrite()) {
            throw new IllegalArgumentException("File cannot be written: " + aFile);
        }

        aContents = aContents.replaceAll("\n", System.lineSeparator());
        try (Writer output = new BufferedWriter(new FileWriter(aFile))) {
            output.write(aContents);
        }
    }
}