// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.main.saveload.core;

import java.io.IOException;

/**
 * The exception thrown if an error occurs during importer or exporter operations.
 *
 * @author azin azadi

 */
public class GraphIOException extends IOException {
    /**
     *
     */
    private static final long serialVersionUID = -5888787393937657454L;

    public GraphIOException(String message) {
        super(message);
    }
}
