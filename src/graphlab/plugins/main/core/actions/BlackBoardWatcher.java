// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.core.actions;

import graphlab.graph.atributeset.GraphAttrSet;
import graphlab.graph.graph.Edge;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.Vertex;
import graphlab.graph.old.GStroke;
import graphlab.platform.Application;
import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.platform.core.Listener;
import graphlab.plugins.main.core.actions.edge.AddEdge;
import graphlab.plugins.main.core.actions.vertex.AddVertex;
import graphlab.ui.UIUtils;

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
        Application gg = new graphlab.platform.Application();
        //blackboard.getData(Application.APPLICATION_INSTANCE);
        gg.plugger = new graphlab.platform.plugin.Plugger(bbd);

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
    HashMap<String, Vertex> knownLogs = new HashMap<String, Vertex>();
    HashMap<StackTraceElement, Vertex> knownTraces = new HashMap<StackTraceElement, Vertex>();
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
        Vertex _ = knownLogs.get(name);
        if (_ == null || !g.containsVertex(_)) {
            _ = addVertex();
            _.setLabel(name);
            _.setColor(2);
            knownLogs.put(name, _);
        }
        return _;
    }

    private Vertex addVertex() {
        return AddVertex.addVertexToRandomPosition(g);
    }

    HashMap<Vertex, Vertex> methodOwners = new HashMap<Vertex, Vertex>();
    HashMap<String, Vertex> classes = new HashMap<String, Vertex>();
    HashMap<String, HashMap<String, Vertex>> methods = new HashMap<String, HashMap<String, Vertex>>();

    private Vertex getClassVertex(StackTraceElement ste) {
        String clazz = ste.getClassName();
        String method = ste.getMethodName();
        Vertex _class = classes.get(clazz);
        if (methods.get(clazz) == null)
            methods.put(clazz, new HashMap<String, Vertex>());
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
        
        Edge e = null;
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
