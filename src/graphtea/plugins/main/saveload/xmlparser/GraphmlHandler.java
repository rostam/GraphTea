// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.saveload.xmlparser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public interface GraphmlHandler {

    /**
     * A data element event handling method.
     *
     * @param data value or null
     * @param meta attributes
     */
    void handle_key(final java.lang.String data, final Attributes meta);

    /**
     * A container element start event handling method.
     *
     * @param meta attributes
     */
    void start_edge(final Attributes meta);

    /**
     * A container element end event handling method.
     */
    void end_edge();

    /**
     * An empty element event handling method.
     *
     * @param meta value or null
     */
    void handle_locator(final Attributes meta);

    /**
     * A data element event handling method.
     *
     * @param data value or null
     * @param meta attributes
     */
    void handle_data(final java.lang.String data, final Attributes meta);

    /**
     * A container element start event handling method.
     *
     * @param meta attributes
     */
    void start_node(final Attributes meta);

    /**
     * A container element end event handling method.
     */
    void end_node();

    /**
     * A container element start event handling method.
     *
     * @param meta attributes
     */
    void start_graph(final Attributes meta);

    /**
     * A container element end event handling method.
     */
    void end_graph();

    /**
     * A container element start event handling method.
     *
     * @param meta attributes
     */
    void start_endpoint(final Attributes meta);

    /**
     * A container element end event handling method.
     */
    void end_endpoint();

    /**
     * A container element start event handling method.
     *
     * @param meta attributes
     */
    void start_graphml(final Attributes meta);

    /**
     * A container element end event handling method.
     */
    void end_graphml();

    /**
     * A container element start event handling method.
     *
     * @param meta attributes
     */
    void start_hyperedge(final Attributes meta);

    /**
     * A container element end event handling method.
     */
    void end_hyperedge();

    /**
     * A container element start event handling method.
     *
     * @param meta attributes
     */
    void start_port(final Attributes meta);

    /**
     * A container element end event handling method.
     */
    void end_port();

    /**
     * A data element event handling method.
     *
     * @param data value or null
     * @param meta attributes
     */
    void handle_desc(final java.lang.String data, final Attributes meta);
}
