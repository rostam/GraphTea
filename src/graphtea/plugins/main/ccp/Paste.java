// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.ccp;

import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.event.GraphEvent;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.SubGraph;
import graphtea.graph.graph.Vertex;
import graphtea.graph.ui.GTabbedGraphPane;
import graphtea.graph.ui.GraphRectRegionSelect;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.plugins.main.saveload.xmlparser.GraphmlHandlerImpl;
import graphtea.plugins.main.saveload.xmlparser.GraphmlParser;
import graphtea.plugins.main.select.Select;
import graphtea.ui.UIUtils;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author rouzbeh
 */
public class Paste extends AbstractAction {
    public static final String event = UIUtils.getUIEventKey("Paste");
    public static String status = "";
    GraphRectRegionSelect graphRectRegionSelector = new GraphRectRegionSelect(blackboard) {

        public void onMouseMoved(GraphEvent data) {
//            _onMouseMoved(data);
        }

        public void onDrop(GraphEvent data) {
            _onDrop(data);
        }
    };

    public Paste(BlackBoard bb) {
        super(bb);
        this.listen4Event(event);
    }

    public void performAction(String eventName, Object value) {
        GTabbedGraphPane.showNotificationMessage("Select The Paste Region", blackboard, true);
        graphRectRegionSelector.startSelectingRegion();
    }

    void _onDrop(GraphEvent data) {
        GTabbedGraphPane.showNotificationMessage("", blackboard, true);
        GraphModel gg = new GraphModel();
        GraphModel g = blackboard.getData(GraphAttrSet.name);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Clipboard cb = tk.getSystemClipboard();
        Transferable content = cb.getContents(this);

        if (content == null) {
            //          nothing to paste
            tk.beep();
            return;
        }

//      we only accept string or plain text data
        if (content.isDataFlavorSupported(DataFlavor.stringFlavor) ||
                content.isDataFlavorSupported(DataFlavor.getTextPlainUnicodeFlavor())) {
            String strData = null;
            InputStream stream = null;
            try {
                //              representation class is a String, so use that
                strData = (String) content.getTransferData(DataFlavor.stringFlavor);
            } catch (Exception e1) {
                try {
                    //                  representation class is an input stream, so
                    //                  leave the strData variable as null to be checked
                    //                  by the code below
                    stream = (InputStream) content.getTransferData(DataFlavor.getTextPlainUnicodeFlavor());
                } catch (Exception e2) {
                    //                  it was something we could handle but it didn't
                    //                   want to be retrieved, too bad
                    tk.beep();
//                    return;
                }
            }


            if (strData != null) {
                //              data was a string, create a byte array and a
                //              byte array input stream and read it
                byte[] bytes = strData.getBytes();
                ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                try {
                    GraphmlHandlerImpl gi = new GraphmlHandlerImpl(gg);
                    GraphmlParser.parse(new InputSource(bais), gi);
                    //                    PasteHandlerImpl ghi = new PasteHandlerImpl(gg);
                    //                    PasteParser.parse(new InputSource(bais), ghi);
                    bais.close();

                } catch (Exception e) {
                    if (e.getClass().equals(SAXParseException.class)) {
                        tk.beep();
                        ExceptionHandler.catchException(e);
                    } else {
                        ExceptionHandler.catchException(e);
                    }
                }
            } else if (stream != null) {
                //                  data was an input stream, read that directly
                try {
                    GraphmlHandlerImpl phi = new GraphmlHandlerImpl(gg);
                    GraphmlParser.parse(new InputSource(stream), phi);
                    stream.close();
                } catch (Exception e) {

                }
            }


        } else {
            tk.beep();
        }
//        Point p;
//        //adds the pasted graph from temp graph to main graph
//        if(blackboard.get(GraphClickData.name) != null){
//            GraphClickData gcd = blackboard.get(GraphClickData.name);
//            p = gcd.me.getPoint();
//
//        }
//        else{
//            p=new Point(200,200);
//        }
        HashSet<Vertex> toBeSelectedVertices = new HashSet<Vertex>();
        HashSet<Edge> toBeSelectedEdges = new HashSet<Edge>();

        for (Vertex vm : gg) {
            toBeSelectedVertices.add(vm);
        }
        for (Iterator<Edge> em = gg.edgeIterator(); em.hasNext();) {
            toBeSelectedEdges.add(em.next());
        }
        g.addSubGraph(gg, graphRectRegionSelector.getCurrentRect().getBounds());
//        ClearSelection.clearSelected(gg.blackboard);
        //        ClearSelection.clearSelected(g.blackboard);
        //selects the inserted edges & vertices
        SubGraph sd = new SubGraph();
        for (Vertex v : toBeSelectedVertices) {
            sd.vertices.add(v);
        }
        for (Edge e : toBeSelectedEdges) {
            sd.edges.add(e);
        }
        Select.setSelection(blackboard, sd);

//        if the prev. operation was cut, the clipboard should be cleand
        if (status.equalsIgnoreCase("cut")) {
            String _data = "";
            StringSelection string = new StringSelection(_data);
            cb.setContents(string, string);
            try {
                cb.getContents(this).getTransferData(DataFlavor.stringFlavor).toString();
            } catch (UnsupportedFlavorException e) {
                ExceptionHandler.catchException(e);
            } catch (IOException e) {
                ExceptionHandler.catchException(e);
            }
        }
    }
}
