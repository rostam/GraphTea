// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.graph.ui;
import graphtea.platform.core.exception.ExceptionHandler;

import graphtea.platform.core.BlackBoard;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.DefaultCaret;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;
import java.awt.*;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.HashMap;

/**
 * this class is able to show a HTML text,
 * It's main capability is to handle hyperlinks,
 * the default operation when user clicks on a link is to open the link,
 * It is also possible to add other modes, for example when the
 * link address is: "someClassName?handler=PerformExtension" it is possible to run an extension,
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
 */
public class GHTMLPageComponent extends JScrollPane implements HyperlinkListener {
    private final BlackBoard blackboard;
    private static final HashMap<String, HyperlinkHandler> handlers = new HashMap<>();
    JEditorPane jta;

    static {
        registerHyperLinkHandler("external", new ExternalLinkHandler());
        registerHyperLinkHandler("dialog", new DialogLinkHandler());
    }

    public GHTMLPageComponent(BlackBoard b) {
        blackboard = b;

        jta = new JEditorPane();
        jta.setEditable(false);
        jta.addHyperlinkListener(this);
        jta.setContentType("text/html");
        // Without this, HTML that names no colour or font is drawn in Times and in the look
        // and feel's foreground, which on a dark desktop theme is light grey on this white
        // background: the text simply vanished.
        jta.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
        jta.setBackground(Color.white);
        jta.setForeground(new Color(0x2B2B2B));
        setViewportView(jta);
        setBorder(BorderFactory.createEmptyBorder());
    }

    public void setPage(URL page) {
        try {
            jta.setPage(page);
        } catch (IOException e) {
            ExceptionHandler.catchException(e);
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
        try {
            jta.getEditorKit().read(
                    new StringReader("<html><body>" + html + "</body></html>"),
                    jta.getDocument(),
                    jta.getDocument().getLength());
        } catch (IOException | BadLocationException e) {
            ExceptionHandler.catchException(e);
        }
        jta.validate();
    }

    /**
     * @param protocol for example in "PerformExtension:" protocol will be "PerformExtension"
     * @param h The hyperlink handler
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
            } catch (IOException ex) {
                ExceptionHandler.catchException(ex);
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

