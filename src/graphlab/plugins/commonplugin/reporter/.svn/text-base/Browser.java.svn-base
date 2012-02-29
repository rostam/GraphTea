// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.commonplugin.reporter;

import graphlab.ui.components.utils.GFrameLocationProvider;
import graphlab.platform.core.exception.ExceptionHandler;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

public class Browser extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = -9157914659496711380L;

    protected JTextField urlField;
    protected JEditorPane browserPane;

    public static String header = ""
            + "<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"en\" xml:lang=\"en\">\n" +
            "<head>\n" +
            " <title>New Ticket - GraphLab - Trac</title><link rel=\"start\" href=\"http://graphlab.sharif.ir/trac/graphlab/wiki\" /><link rel=\"search\" href=\"http://graphlab.sharif.ir/trac/graphlab/search\" /><link rel=\"help\" href=\"http://graphlab.sharif.ir/trac/graphlab/wiki/TracGuide\" /><link rel=\"stylesheet\" href=\"http://graphlab.sharif.ir/trac/graphlab/chrome/common/css/trac.css\" type=\"text/css\" /><link rel=\"stylesheet\" href=\"http://graphlab.sharif.ir/trac/graphlab/chrome/common/css/ticket.css\" type=\"text/css\" /><link rel=\"icon\" href=\"http://graphlab.sharif.ir/trac/graphlab/chrome/common/trac.ico\" type=\"image/x-icon\" /><link rel=\"shortcut icon\" href=\"http://graphlab.sharif.ir/trac/graphlab/chrome/common/trac.ico\" type=\"image/x-icon\" /><style type=\"text/css\">\n" +
            "</style>\n" +
            " <script type=\"text/javascript\" src=\"http://graphlab.sharif.ir/trac/graphlab/chrome/common/js/trac.js\"></script>\n" +
            "</head>\n" +
            "<body>\n" +
            "<div id=\"content\" class=\"ticket\">\n" +
            "<h1>Create New Ticket</h1>\n" +
            "\n" +
            "\n" +
            "<form id=\"newticket\" method=\"post\" action=\"http://graphlab.sharif.ir/trac/newticket#preview\"><div><input name=\"__FORM_TOKEN\" value=\"aef7815afa05dc29c165b2d6\" type=\"hidden\"></div>\n" +
            " \n" +
            "  <div class=\"field\">\n" +
            "   <label for=\"reporter\">Your email or username:</label><br>\n" +
            "   <input id=\"reporter\" name=\"reporter\" size=\"40\" value=\"anonymous\" type=\"text\"><br>\n" +
            "  </div>\n" +
            " \n" +
            " <div class=\"field\">\n" +
            "\n" +
            "  <label for=\"summary\">Short summary:</label><br>\n" +
            "  <input id=\"summary\" name=\"summary\" size=\"80\" value=\"\" type=\"text\">\n" +
            " </div>\n" +
            "  <div class=\"field\"><label for=\"type\">Type:</label> \n" +
            " <select size=\"1\" id=\"type\" name=\"type\">\n" +
            "   <option selected=\"selected\">defect</option>\n" +
            "   <option>enhancement</option>\n" +
            "\n" +
            "   <option>task</option>\n" +
            " </select>\n" +
            "  </div>\n" +
            " <div class=\"field\">\n" +
            "  <label for=\"description\">Full description (you may use <a tabindex=\"42\" href=\"/trac/wiki/WikiFormatting\">WikiFormatting</a> here):</label><br>\n" +
            "  <div class=\"wikitoolbar\"><a tabindex=\"400\" title=\"Bold text: '''Example'''\" id=\"strong\" href=\"#\"></a><a tabindex=\"400\" title=\"Italic text: ''Example''\" id=\"em\" href=\"#\"></a><a tabindex=\"400\" title=\"Heading: == Example ==\" id=\"heading\" href=\"#\"></a><a tabindex=\"400\" title=\"Link: [http://www.example.com/ Example]\" id=\"link\" href=\"#\"></a><a tabindex=\"400\" title=\"Code block: {{{ example }}}\" id=\"code\" href=\"#\"></a><a tabindex=\"400\" title=\"Horizontal rule: ----\" id=\"hr\" href=\"#\"></a><a tabindex=\"400\" title=\"New paragraph\" id=\"np\" href=\"#\"></a><a tabindex=\"400\" title=\"Line break: [[BR]]\" id=\"br\" href=\"#\"></a></div><textarea id=\"description\" name=\"description\" class=\"wikitext\" rows=\"10\" cols=\"78\">";

    public static String footer = ""
            + "</textarea>\n" +
            "\n" +
            " </div>\n" +
            "\n" +
            " <fieldset id=\"properties\">\n" +
            "  <legend>Ticket Properties</legend>\n" +
            "  <input name=\"action\" value=\"create\" type=\"hidden\">\n" +
            "  <input name=\"status\" value=\"new\" type=\"hidden\">\n" +
            "  <table><tbody><tr>\n" +
            "     <th class=\"col1\"><label for=\"priority\">Priority:</label></th>\n" +
            "\n" +
            "     <td><select id=\"priority\" name=\"priority\"><option>blocker</option><option>critical</option><option selected=\"selected\">major</option><option>minor</option><option>trivial</option></select></td>\n" +
            "     <th class=\"col2\"><label for=\"milestone\">Milestone:</label></th>\n" +
            "     <td><select id=\"milestone\" name=\"milestone\"><option></option><option>milestone1</option><option>milestone2</option><option>milestone3</option><option>milestone4</option></select></td></tr><tr>\n" +
            "     <th class=\"col1\"><label for=\"component\">Component:</label></th>\n" +
            "\n" +
            "     <td><select id=\"component\" name=\"component\"><option selected=\"selected\">component1</option><option>component2</option></select></td>\n" +
            "     <th class=\"col2\"><label for=\"version\">Version:</label></th>\n" +
            "     <td><select id=\"version\" name=\"version\"><option></option><option>2.0</option><option>1.0</option></select></td></tr><tr>\n" +
            "     <th class=\"col1\"><label for=\"keywords\">Keywords:</label></th>\n" +
            "     <td><input id=\"keywords\" name=\"keywords\" value=\"\" type=\"text\"></td>\n" +
            "     <th class=\"col2\"><label for=\"owner\">Assign to:</label></th>\n" +
            "\n" +
            "     <td><input id=\"owner\" name=\"owner\" value=\"\" type=\"text\"></td></tr><tr>\n" +
            "     <th class=\"col1\"><label for=\"cc\">Cc:</label></th>\n" +
            "     <td><input id=\"cc\" name=\"cc\" value=\"\" type=\"text\"></td><th class=\"col2\"></th><td></td></tr>\n" +
            "  </tbody></table>\n" +
            " </fieldset>\n" +
            "\n" +
            " <script type=\"text/javascript\" src=\"/trac/chrome/common/js/wikitoolbar.js\"></script>\n" +
            "\n" +
            " <p>\n" +
            "\n" +
            "  <label><input name=\"attachment\" type=\"checkbox\">\n" +
            "    I have files to attach to this ticket\n" +
            "  </label>\n" +
            " </p>\n" +
            "\n" +
            " <div class=\"buttons\">\n" +
            "  <input name=\"preview\" value=\"Preview\" accesskey=\"r\" type=\"submit\">&nbsp;\n" +
            "  <input value=\"Submit ticket\" type=\"submit\">\n" +
            " </div>\n" +
            "</form>\n" +
            "\n" +
            "<div id=\"help\">\n" +
            " <strong>Note:</strong> See <a href=\"/trac/wiki/TracTickets\">TracTickets</a> for help on using tickets.\n" +
            "</div>\n" +
            "</div>" +
            " </body>\n" +
            "</html>\n" +
            "";

    public Browser() {
        super("Browser");
        setPreferredSize(GFrameLocationProvider.getPopUpSize());
        setLocation(GFrameLocationProvider.getPopUpLocation());
        // browser pane
        browserPane = new JEditorPane();
        browserPane.setContentType("text/html");
        browserPane.setEditable(false);
        browserPane.addHyperlinkListener(new HyperActive());

        // create content pane
        JPanel contentPane = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(browserPane);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.setPreferredSize(new Dimension(500, 300));
        setContentPane(contentPane);
    }

    public static boolean browse(URL url) {
        try {
            Desktop.getDesktop().browse(url.toURI());
            return true;
        } catch (Exception e) {
            ExceptionHandler.catchException(e);
            return false;
        }
    }

    public boolean browseInternal(URL url) {
        try {
            browserPane.setPage(url);
            return true;
        } catch (IOException e) {
            System.err.println("Attempted to read a bad URL: " + url);
            return false;
        }
    }

    public static boolean browse(String htmlText) {
        try {
            File temp = File.createTempFile("graphlab", ".html");
            temp.deleteOnExit();
            BufferedWriter out = new BufferedWriter(new FileWriter(temp));
            out.write(htmlText);
            out.close();
            browse(temp.toURL());
            return true;
        } catch (IOException e) {
            ExceptionHandler.catchException(e);
            return false;
        }
    }

    public boolean browseInternal(String htmlText) {
        browserPane.setText(htmlText);
        System.out.println(htmlText);
        return true;
    }
}