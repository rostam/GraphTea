// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

/*
 * @(#)ExampleFileFilter.java	1.16 04/07/26
 */
package graphtea.plugins.main.saveload;


import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * A convenience implementation of FileFilter that filters out
 * all files except for those type extensions that it knows about.
 * <p/>
 * Extensions are of the type ".foo", which is typically found on
 * Windows and Unix boxes, but not on Macinthosh. Case is ignored.
 * <p/>
 * Example - create a new filter that filerts out all files
 * but gif and jpg image files:
 * <p/>
 * JFileChooser chooser = new JFileChooser();
 * ExampleFileFilter filter = new ExampleFileFilter(
 * new String{"gif", "jpg"}, "JPEG & GIF Images")
 * chooser.addChoosableFileFilter(filter);
 * chooser.showOpenDialog(this);
 *
 * @author Jeff Dinkins
 * @version 1.16 07/26/04
 */
public class ExampleFileFilter extends FileFilter {

    private static final String TYPE_UNKNOWN = "Type Unknown";
    private static final String HIDDEN_FILE = "Hidden File";

    private Hashtable filters = null;
    private String description = null;
    private String fullDescription = null;
    private boolean useExtensionsInDescription = true;

    /**
     * Creates a file filter. If no filters are added, then all
     * files are accepted.
     *
     * @see #addExtension
     */
    public ExampleFileFilter() {
        this.filters = new Hashtable();
    }

    /**
     * Creates a file filter that accepts files with the given extension.
     * Example: new ExampleFileFilter("jpg");
     *
     * @see #addExtension
     */
    public ExampleFileFilter(String extension) {
        this(extension, null);
    }

    /**
     * Creates a file filter that accepts the given file type.
     * Example: new ExampleFileFilter("jpg", "JPEG Image Images");
     * <p/>
     * Note that the "." before the extension is not needed. If
     * provided, it will be ignored.
     *
     * @see #addExtension
     */
    public ExampleFileFilter(String extension, String description) {
        this();
        if (extension != null) addExtension(extension);
        if (description != null) setDescription(description);
    }

    /**
     * Creates a file filter from the given string array.
     * Example: new ExampleFileFilter(String {"gif", "jpg"});
     * <p/>
     * Note that the "." before the extension is not needed adn
     * will be ignored.
     *
     * @see #addExtension
     */
    public ExampleFileFilter(String[] filters) {
        this(filters, null);
    }

    /**
     * Creates a file filter from the given string array and defaultValue.
     * Example: new ExampleFileFilter(String {"gif", "jpg"}, "Gif and JPG Images");
     * <p/>
     * Note that the "." before the extension is not needed and will be ignored.
     *
     * @see #addExtension
     */
    public ExampleFileFilter(String[] filters, String description) {
        this();
        for (String filter : filters) {
            // add filters one by one
            addExtension(filter);
        }
        if (description != null) setDescription(description);
    }

    /**
     * Return true if this file should be shown in the directory pane,
     * false if it shouldn't.
     * <p/>
     * Files that begin with "." are ignored.
     *
     * @see graphtea.plugins.main.saveload.SaveLoadPluginMethods#getExtension
     * @see FileFilter#accept
     */
    public boolean accept(File f) {
        if (f != null) {
            if (f.isDirectory()) {
                return true;
            }
            String extension = SaveLoadPluginMethods.getExtension(f);
            return extension != null && filters.get(SaveLoadPluginMethods.getExtension(f)) != null;
        }
        return false;
    }

    /**
     * Adds a filetype "dot" extension to filter against.
     * <p/>
     * For example: the following code will create a filter that filters
     * out all files except those that end in ".jpg" and ".tif":
     * <p/>
     * ExampleFileFilter filter = new ExampleFileFilter();
     * filter.addExtension("jpg");
     * filter.addExtension("tif");
     * <p/>
     * Note that the "." before the extension is not needed and will be ignored.
     */
    public void addExtension(String extension) {
        if (filters == null) {
            filters = new Hashtable(5);
        }
        filters.put(extension.toLowerCase(), this);
        fullDescription = null;
    }


    /**
     * Returns the human readable defaultValue of this filter. For
     * example: "JPEG and GIF Image Files (*.jpg, *.gif)"
     *
     * @see setDescription
     * @see setExtensionListInDescription
     * @see isExtensionListInDescription
     * @see FileFilter#getDescription
     */
    public String getDescription() {
        if (fullDescription == null) {
            if (description == null || isExtensionListInDescription()) {
                fullDescription = description == null ? "(" : description + " (";
                // build the defaultValue from the extension list
                Enumeration extensions = filters.keys();
                if (extensions != null) {
                    fullDescription += "." + extensions.nextElement();
                    while (extensions.hasMoreElements()) {
                        fullDescription += ", ." + extensions.nextElement();
                    }
                }
                fullDescription += ")";
            } else {
                fullDescription = description;
            }
        }
        return fullDescription;
    }

    /**
     * Sets the human readable defaultValue of this filter. For
     * example: filter.setDescription("Gif and JPG Images");
     *
     * @see setDescription
     * @see setExtensionListInDescription
     * @see isExtensionListInDescription
     */
    public void setDescription(String description) {
        this.description = description;
        fullDescription = null;
    }

    /**
     * Determines whether the extension list (.jpg, .gif, etc) should
     * show up in the human readable defaultValue.
     * <p/>
     * Only relevant if a defaultValue was provided in the constructor
     * or using setDescription();
     *
     * @see getDescription
     * @see setDescription
     * @see isExtensionListInDescription
     */
    public void setExtensionListInDescription(boolean b) {
        useExtensionsInDescription = b;
        fullDescription = null;
    }

    /**
     * Returns whether the extension list (.jpg, .gif, etc) should
     * show up in the human readable defaultValue.
     * <p/>
     * Only relevent if a defaultValue was provided in the constructor
     * or using setDescription();
     *
     * @see getDescription
     * @see setDescription
     * @see setExtensionListInDescription
     */
    public boolean isExtensionListInDescription() {
        return useExtensionsInDescription;
    }
}
