// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.core.actions;

import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.graph.old.GStroke;
import graphtea.platform.Application;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.Listener;
import graphtea.plugins.main.core.actions.edge.AddEdge;
import graphtea.plugins.main.core.actions.vertex.AddVertex;
import graphtea.ui.UIUtils;

import java.util.HashMap;

/**
 * @author azin azadi
 */
public class BlackBoardWatcher extends AbstractAction {
    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public BlackBoardWatcher(BlackBoard bb) {
        super(bb);
        listen4Event(UIUtils.getUIEventKey("debug blackboard"));    
        
    }

    public void performAction(String eventName, Object value) {
        GraphModel g = blackboard.getData(GraphAttrSet.name);
        final BlackBoardDebug bbd = new BlackBoardDebug(g);
        new Thread(){
            public void run() {
        Application gg = new graphtea.platform.Application();
        //blackboard.getData(Application.APPLICATION_INSTANCE);
        gg.plugger = new graphtea.platform.plugin.Plugger(bbd);

        gg.run(bbd);
            }
        }.start();
    }
}

class BlackBoardDebug extends BlackBoard {
    GraphModel g;

    public BlackBoardDebug(GraphModel g) {
        this.g = g;
    }

    /**
     * searchs in the stackTrace and returns the
     * first nonblackboard classname.callingmethod.linenumber
     */
    private StackTraceElement getCallingMethod() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int n = 2;
        StackTraceElement st = stackTrace[n];
        String c = st.getClassName();
        
        while (c.contains("BlackBoard") || c.contains("AbstractAction")) {
           System.out.println(stackTrace[n]);
            ++n;
            c = stackTrace[n].getClassName();
        }
        return stackTrace[n];
//        return "*"+clazz+"."+s.getMethodName();//+":"+s.getLineNumber();
    }

    //    HashMap<String, Vertex> knownPlaces=new HashMap<String, Vertex>();
    HashMap<String, Vertex> knownLogs = new HashMap<>();
    HashMap<StackTraceElement, Vertex> knownTraces = new HashMap<>();
//    public Log getLog(String name) {
//        addEdge(getCallingMethod(),name);
//        return super.getLog(name);
//    }

    public void setData(String name, Object value) {
        addEdge(getCallingMethod(), name, true, false);
        super.setData(name, value);
    }

    @Override
	public void addListener(String key, Listener listener) {
        addEdge(getCallingMethod(), key, true, true);
		super.addListener(key, listener);
	}

	@Override
	protected void fireListeners(String key, Object newValue) {
        addEdge(getCallingMethod(), key, false, true);
		super.fireListeners(key, newValue);
	}

	public <t> t getData(String name) {
        addEdge(getCallingMethod(), name, false, false);
        return (t) super.getData(name);
    }

    private Vertex getLogVertex(String name) {
        Vertex vn = knownLogs.get(name);
        if (vn == null || !g.containsVertex(vn)) {
            vn = addVertex();
            vn.setLabel(name);
            vn.setColor(2);
            knownLogs.put(name, vn);
        }
        return vn;
    }

    private Vertex addVertex() {
        return AddVertex.addVertexToRandomPosition(g);
    }

    HashMap<Vertex, Vertex> methodOwners = new HashMap<>();
    HashMap<String, Vertex> classes = new HashMap<>();
    HashMap<String, HashMap<String, Vertex>> methods = new HashMap<>();

    private Vertex getClassVertex(StackTraceElement ste) {
        String clazz = ste.getClassName();
        String method = ste.getMethodName();
        Vertex _class = classes.get(clazz);
        methods.putIfAbsent(clazz, new HashMap<>());
        Vertex _method = methods.get(clazz).get(method);
        Vertex _methodParent = methodOwners.get(_method);
        if (_class == null || !g.containsVertex(_class)) {
            _class = addVertex();
            _class.setLabel(clazz.substring(clazz.lastIndexOf(".") + 1));
            _class.setColor(4);
            classes.put(clazz, _class);
        }
        if (_method == null || _methodParent != _class || !g.containsVertex(_method)) {
            _method = addVertex();
            methodOwners.put(_method, _class);
            methods.get(clazz).put(method, _method);
            AddEdge.doJob(g, _class, _method).setLabel("");
            _method.setLabel(method + "()");
            _method.setColor(3);

        }
        return _method;
    }

    private Edge addEdge(StackTraceElement ste, String logName, boolean set, boolean listener) {
        g.setShowChangesOnView(true);
        
        Edge e;
        if (set)
            e= AddEdge.doJob(g, getClassVertex(ste), getLogVertex(logName));
        else
            e= AddEdge.doJob(g, getLogVertex(logName),getClassVertex(ste));
        e.setLabel(ste.getLineNumber() + "");
        e.setWeight(1 + e.getWeight());
        if (set && !listener)
            e.setColor(5);
        //else
        //    e.setColor(2);
        if (listener)
        	e.setStroke(GStroke.dashed);
        g.setShowChangesOnView(false);
        return e;
    }
//    public Variable getVar(String name) {
////        addEdge(getCallingMethod(),name);
//        return super.getVar(name);
//    }
}
