// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.graph.ui;

import graphtea.platform.core.BlackBoard;
import graphtea.plugins.main.GraphData;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

/**
 * this class is able to show a HTML text,
 * It's main capability is to handle hyperlinks,
 * the default operation when user clicks on a link is to open the link,
 * It is also possible to add other modes, for example when the
 * link address is: "command?handler=BSH" it is possible to run the command,
 * for this a HyperlinkHandler should be registered.
 * One simple handler is also added, if the link address is "http://www.google.com/search?q=Graph&handler=external", google(your url)
 * will be opened in an external viewer like FireFox
 * another added handler is the "yoururl.com,yourtitle?handler=dialog" which opens your url in a new dialog! which
 * the dialog title is yourtitle
 * <p/>
 * (Actually it is not important which character you put before handler(? or & or ...) The only important thing is
 * to make it compatible with http urls)
 *
 * @author Azin Azadi
 * @see graphtea.plugins.commandline.ShellHyperlinkHandler
 */
public class GHTMLPageComponent extends JScrollPane implements HyperlinkListener {
    private BlackBoard blackboard;
    private static HashMap<String, HyperlinkHandler> handlers = new HashMap<String, HyperlinkHandler>();
    JEditorPane jta;

    static {
        registerHyperLinkHandler("external", new ExternalLinkHandler());
        registerHyperLinkHandler("dialog", new DialogLinkHandler());
    }

    public GHTMLPageComponent(BlackBoard b) {
        blackboard = b;

        GraphData gd = new GraphData(b);
        jta = new JEditorPane();
        jta.setEditable(false);
        jta.addHyperlinkListener(this);
        JViewport jvp = new JViewport();

        jta.setBackground(Color.white);
        jvp.add(jta);
        jta.setContentType("text/html");

        this.setViewport(jvp);
    }

    public void setPage(URL page) {
        try {
            jta.setPage(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setHTML(String html) {

        jta.setText(html);
    }

    public JEditorPane getEditorPane() {
        return jta;
    }

    public void autoScroll(){
        DefaultCaret caret = (DefaultCaret) jta.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        jta.getCaret().setVisible(true);
        jta.getCaret().setSelectionVisible(true);
        caret.setDot(jta.getText().length());
    }

    public void appendHTML(String html) {


        StringBuffer sb9 = new StringBuffer();
        String pointtext = jta.getText();

        sb9.append("<html><body>");
        sb9.append(html);
        sb9.append("</body></html>");


        try {
            Document doc = (Document) jta.getDocument();

            ((HTMLEditorKit) jta.getEditorKit()).read(
                    new java.io.StringReader(sb9.toString())
                    , jta.getDocument()
                    , jta.getDocument().getLength());
        } catch (Throwable bl) {
            System.out.println("-- " + bl.getMessage());
        }
        jta.validate();

    }

    /**
     * @param protocol for example in "BSH:" protocol will be "BSH"
     * @param h
     * @see graphtea.plugins.commandline.ShellHyperlinkHandler
     */
    public static void registerHyperLinkHandler(String protocol, HyperlinkHandler h) {
        handlers.put(protocol.toUpperCase(), h);
    }

    public void hyperlinkUpdate(HyperlinkEvent e) {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            String url = e.getDescription();
            if (!url.contains("="))
                normalHyperlinkUpdate(e);
            else {
                HyperlinkHandler han = null;
                String postfix;
                int pi = url.indexOf("handler");
                if (pi != -1) {
                    postfix = url.toUpperCase().substring(pi + 8);
                    han = handlers.get(postfix);
                }
                if (han != null) {
                    String command = url.substring(0, pi - 1);
                    han.handle(command, blackboard, jta.getPage());
                } else
                    normalHyperlinkUpdate(e);
            }
        }
    }

    public void normalHyperlinkUpdate(HyperlinkEvent e) {
        JEditorPane pane = (JEditorPane) e.getSource();
        if (e instanceof HTMLFrameHyperlinkEvent) {
            HTMLFrameHyperlinkEvent evt = (HTMLFrameHyperlinkEvent) e;
            HTMLDocument doc = (HTMLDocument) pane.getDocument();
            doc.processHTMLFrameHyperlinkEvent(evt);
        } else {
            try {
                pane.setPage(e.getURL());
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }


    //------------------------------------------------
    String message;

    public void showNotificationMessage(String message) {
        setHTML(message);
    }

    public void setMessage(String message) {
        setHTML(message);
        this.message = message;

    }


    public void hideNotificationMessage() {
        setHTML(message);
    }

}

