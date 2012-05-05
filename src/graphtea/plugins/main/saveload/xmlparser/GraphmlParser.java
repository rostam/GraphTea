// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.saveload.xmlparser;

import org.xml.sax.*;

/**
 * The class reads XML documents according to specified DTD and
 * translates all related events into GraphmlHandler events.
 * <p>Usage sample:
 * <pre>
 *    GraphmlParser parser = new GraphmlParser(...);
 *    parser.parse(new InputSource("..."));
 * </pre>
 * <p><b>Warning:</b> the class is machine generated. DO NOT MODIFY</p>
 */
public class GraphmlParser implements ContentHandler {

    private java.lang.StringBuffer buffer;

    private GraphmlHandler handler;

    private java.util.Stack context;

    private EntityResolver resolver;

    /**
     * Creates a parser instance.
     *
     * @param handler  handler interface implementation (never <code>null</code>
     * @param resolver SAX entity resolver implementation or <code>null</code>.
     *                 It is recommended that it could be able to resolve at least the DTD.
     */
    public GraphmlParser(final GraphmlHandler handler, final EntityResolver resolver) {

        this.handler = handler;
        this.resolver = resolver;
        buffer = new StringBuffer(111);
        context = new java.util.Stack();

    }

    /**
     * This SAX interface method is implemented by the parser.
     */
    public final void setDocumentLocator(Locator locator) {
    }

    /**
     * This SAX interface method is implemented by the parser.
     */
    public final void startDocument() throws SAXException {
    }

    /**
     * This SAX interface method is implemented by the parser.
     */
    public final void endDocument() throws SAXException {
    }

    /**
     * This SAX interface method is implemented by the parser.
     */
    public final void startElement(java.lang.String ns, java.lang.String name, java.lang.String qname, Attributes attrs) throws SAXException {

        dispatch(true);
        context.push(new Object[]{qname, new org.xml.sax.helpers.AttributesImpl(attrs)});
        if ("edge".equals(qname)) {
            handler.start_edge(attrs);
        } else if ("locator".equals(qname)) {
            handler.handle_locator(attrs);
        } else if ("node".equals(qname)) {
            handler.start_node(attrs);
        } else if ("graph".equals(qname)) {
            handler.start_graph(attrs);
        } else if ("endpoint".equals(qname)) {
            handler.start_endpoint(attrs);
        } else if ("graphml".equals(qname)) {
            handler.start_graphml(attrs);
        } else if ("hyperedge".equals(qname)) {
            handler.start_hyperedge(attrs);
        } else if ("port".equals(qname)) {
            handler.start_port(attrs);
        }
    }

    /**
     * This SAX interface method is implemented by the parser.
     */
    public final void endElement(java.lang.String ns, java.lang.String name, java.lang.String qname) throws SAXException {

        dispatch(false);
        context.pop();
        if ("edge".equals(qname)) {
            handler.end_edge();
        } else if ("node".equals(qname)) {
            handler.end_node();
        } else if ("graph".equals(qname)) {
            handler.end_graph();
        } else if ("endpoint".equals(qname)) {
            handler.end_endpoint();
        } else if ("graphml".equals(qname)) {
            handler.end_graphml();
        } else if ("hyperedge".equals(qname)) {
            handler.end_hyperedge();
        } else if ("port".equals(qname)) {
            handler.end_port();
        }
    }

    /**
     * This SAX interface method is implemented by the parser.
     */
    public final void characters(char[] chars, int start, int len) throws SAXException {

        buffer.append(chars, start, len);
    }

    /**
     * This SAX interface method is implemented by the parser.
     */
    public final void ignorableWhitespace(char[] chars, int start, int len) throws SAXException {
    }

    /**
     * This SAX interface method is implemented by the parser.
     */
    public final void processingInstruction(java.lang.String target, java.lang.String data) throws SAXException {
    }

    /**
     * This SAX interface method is implemented by the parser.
     */
    public final void startPrefixMapping(final java.lang.String prefix, final java.lang.String uri) throws SAXException {
    }

    /**
     * This SAX interface method is implemented by the parser.
     */
    public final void endPrefixMapping(final java.lang.String prefix) throws SAXException {
    }

    /**
     * This SAX interface method is implemented by the parser.
     */
    public final void skippedEntity(java.lang.String name) throws SAXException {
    }

    private void dispatch(final boolean fireOnlyIfMixed) throws SAXException {

        if (fireOnlyIfMixed && buffer.length() == 0) return; //skip it

        Object[] ctx = (Object[]) context.peek();
        String here = (String) ctx[0];
        Attributes attrs = (Attributes) ctx[1];
        if ("key".equals(here)) {
            if (fireOnlyIfMixed) throw new IllegalStateException("Unexpected characters() event! (Missing DTD?)");
            handler.handle_key(buffer.length() == 0 ? null : buffer.toString(), attrs);
        } else if ("data".equals(here)) {
            if (fireOnlyIfMixed) throw new IllegalStateException("Unexpected characters() event! (Missing DTD?)");
            handler.handle_data(buffer.length() == 0 ? null : buffer.toString(), attrs);
        } else if ("desc".equals(here)) {
            if (fireOnlyIfMixed) throw new IllegalStateException("Unexpected characters() event! (Missing DTD?)");
            handler.handle_desc(buffer.length() == 0 ? null : buffer.toString(), attrs);
        } else {
            //do not care
        }
        buffer.delete(0, buffer.length());
    }

    /**
     * The recognizer entry method taking an InputSource.
     *
     * @param input InputSource to be parsed.
     * @throws java.io.IOException on I/O error.
     * @throws SAXException        propagated exception thrown by a DocumentHandler.
     * @throws javax.xml.parsers.ParserConfigurationException
     *                             a parser satisfining requested configuration can not be created.
     * @throws javax.xml.parsers.FactoryConfigurationRrror
     *                             if the implementation can not be instantiated.
     */
    public void parse(final InputSource input) throws SAXException, javax.xml.parsers.ParserConfigurationException, java.io.IOException {

        parse(input, this);
    }

    /**
     * The recognizer entry method taking a URL.
     *
     * @param url URL source to be parsed.
     * @throws java.io.IOException on I/O error.
     * @throws SAXException        propagated exception thrown by a DocumentHandler.
     * @throws javax.xml.parsers.ParserConfigurationException
     *                             a parser satisfining requested configuration can not be created.
     */
    public void parse(final java.net.URL url) throws SAXException, javax.xml.parsers.ParserConfigurationException, java.io.IOException {

        parse(new InputSource(url.toExternalForm()), this);
    }

    /**
     * The recognizer entry method taking an Inputsource.
     *
     * @param input InputSource to be parsed.
     * @throws java.io.IOException on I/O error.
     * @throws SAXException        propagated exception thrown by a DocumentHandler.
     * @throws javax.xml.parsers.ParserConfigurationException
     *                             a parser satisfining requested configuration can not be created.
     * @throws javax.xml.parsers.FactoryConfigurationRrror
     *                             if the implementation can not be instantiated.
     */
    public static void parse(final InputSource input, final GraphmlHandler handler) throws SAXException, javax.xml.parsers.ParserConfigurationException, java.io.IOException {

        parse(input, new GraphmlParser(handler, null));
    }

    /**
     * The recognizer entry method taking a URL.
     *
     * @param url URL source to be parsed.
     * @throws java.io.IOException on I/O error.
     * @throws SAXException        propagated exception thrown by a DocumentHandler.
     * @throws javax.xml.parsers.ParserConfigurationException
     *                             a parser satisfining requested configuration can not be created.
     * @throws javax.xml.parsers.FactoryConfigurationRrror
     *                             if the implementation can not be instantiated.
     */
    public static void parse(final java.net.URL url, final GraphmlHandler handler) throws SAXException, javax.xml.parsers.ParserConfigurationException, java.io.IOException {

        parse(new InputSource(url.toExternalForm()), handler);
    }

    private static void parse(final InputSource input, final GraphmlParser recognizer) throws SAXException, javax.xml.parsers.ParserConfigurationException, java.io.IOException {

        javax.xml.parsers.SAXParserFactory factory = javax.xml.parsers.SAXParserFactory.newInstance();
        factory.setValidating(false);  //the code was generated according DTD
        factory.setNamespaceAware(false);  //the code was generated according DTD
        XMLReader parser = factory.newSAXParser().getXMLReader();
        parser.setContentHandler(recognizer);
        parser.setErrorHandler(recognizer.getDefaultErrorHandler());
        if (recognizer.resolver != null) parser.setEntityResolver(recognizer.resolver);
        parser.parse(input);
    }

    /**
     * Creates default error handler used by this parser.
     *
     * @return org.xml.sax.ErrorHandler implementation
     */
    protected ErrorHandler getDefaultErrorHandler() {

        return new ErrorHandler() {
            public void error(SAXParseException ex) throws SAXException {
                if (context.isEmpty()) System.err.println("Missing DOCTYPE.");
                throw ex;
            }

            public void fatalError(SAXParseException ex) throws SAXException {
                throw ex;
            }

            public void warning(SAXParseException ex) throws SAXException {
                // ignore
            }
        };

    }
}
