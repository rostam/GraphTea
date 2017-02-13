// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public interface UIHandler {

    /**
     * An empty element event handling method.
     *
     * @param meta value or null
     */
    void handle_sidebar(final Attributes meta) throws SAXException;

    /**
     * An empty element event handling method.
     *
     * @param meta value or null
     */
    void handle_action(final Attributes meta) throws SAXException;

    /**
     * A container element start event handling method.
     *
     * @param meta attributes
     */
    void start_submenu(final Attributes meta) throws SAXException;

    /**
     * A container element end event handling method.
     */
    void end_submenu() throws SAXException;

    /**
     * An empty element event handling method.
     *
     * @param meta value or null
     */
    void handle_bar(final Attributes meta) throws SAXException;

    /**
     * A container element start event handling method.
     *
     * @param meta attributes
     */
    void start_toolbar(final Attributes meta) throws SAXException;

    /**
     * A container element end event handling method.
     */
    void end_toolbar() throws SAXException;

    /**
     * A container element start event handling method.
     *
     * @param meta attributes
     */
    void start_toolbars(final Attributes meta) throws SAXException;

    /**
     * A container element end event handling method.
     */
    void end_toolbars() throws SAXException;

    /**
     * An empty element event handling method.
     *
     * @param meta value or null
     */
    void handle_tool(final Attributes meta) throws SAXException;

    /**
     * An empty element event handling method.
     *
     * @param meta value or null
     */
    void handle_menu(final Attributes meta) throws SAXException;

    /**
     * A container element start event handling method.
     *
     * @param meta attributes
     */
    void start_menues(final Attributes meta) throws SAXException;

    /**
     * A container element end event handling method.
     */
    void end_menues() throws SAXException;

    /**
     * An empty element event handling method.
     *
     * @param meta value or null
     */
    void handle_body(final Attributes meta) throws SAXException;
}
