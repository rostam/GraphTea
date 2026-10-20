// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.main.saveload.core.extension;

import graphtea.graph.graph.GraphModel;

import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * The file each open graph was last read from or written to, so that <b>File &rsaquo; Save</b>
 * can write it again without asking for a name.
 *
 * <p>Graphs are held weakly: closing a tab lets its graph, and its entry here, go.
 */
public final class GraphFiles {
    private static final Map<GraphModel, File> FILES = Collections.synchronizedMap(new WeakHashMap<>());

    private GraphFiles() {
    }

    /**
     * @param g a graph
     * @return the file it was last read from or written to, or null if it has none
     */
    public static File get(GraphModel g) {
        return g == null ? null : FILES.get(g);
    }

    /**
     * @param g    a graph
     * @param file the file it was just read from or written to
     */
    public static void remember(GraphModel g, File file) {
        if (g != null && file != null) {
            FILES.put(g, file);
        }
    }

    /**
     * @param file      a file
     * @param extension a file name extension without the dot, such as "tea"
     * @return whether the file's name ends with that extension, ignoring case
     */
    public static boolean hasExtension(File file, String extension) {
        return file.getName().toLowerCase().endsWith("." + extension.toLowerCase());
    }
}
