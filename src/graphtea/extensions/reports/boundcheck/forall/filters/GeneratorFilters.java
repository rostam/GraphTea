package graphtea.extensions.reports.boundcheck.forall.filters;

import graphtea.extensions.reports.boundcheck.forall.IterProgressBar;
import graphtea.extensions.reports.boundcheck.forall.ToCall;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.library.util.Pair;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.extension.Extension;
import graphtea.platform.extension.ExtensionLoader;
import graphtea.platform.lang.ArrayX;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.graphgenerator.GraphGenerator;
import graphtea.plugins.graphgenerator.core.SimpleGeneratorInterface;
import graphtea.plugins.graphgenerator.core.extension.GraphGeneratorExtension;
import graphtea.ui.extension.AbstractExtensionAction;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

/**
 * Created by rostam on 14.10.15.
 * Generator Filters
 */
public class GeneratorFilters {
    public static HashMap<String, AbstractAction> hm = ExtensionLoader.loadedInstances;
    public static HashMap<String, String>  nameToClass= new HashMap<>();
    public static final String NoGenerator = "No Generator";

    public static ArrayX<String> getGenFilters() {
        ArrayX ax = new ArrayX(NoGenerator);
        hm.keySet().stream().filter(s -> s.contains("graphtea.extensions.generators.")).forEach(s -> {
            Extension ext = ((AbstractExtensionAction) hm.get(s)).getTarget();
            ax.addValidValue(ext.getName());
            nameToClass.put(ext.getName(), s);
        });
        return ax;
    }

    public static RenderTable generateGraphs(String name,ToCall f,String bound) {
        RenderTable retForm = new RenderTable();
        Extension ext = ((AbstractExtensionAction) hm.get(
                nameToClass.get(name))).getTarget();
        Vector<JTextField> v = new Vector<>();
        JPanel myPanel = new JPanel();
        Parametrizable o = (Parametrizable) ext;
        Vector<String> names = new Vector<>();
        String fieldName = "";
        for (Field ff : o.getClass().getFields()) {
            Parameter anot = ff.getAnnotation(Parameter.class);
            if (anot != null) {
                JTextField xField = new JTextField(5);
                v.add(xField);
                fieldName = anot.name();
                myPanel.add(new JLabel(anot.name() + ":"));
                myPanel.add(xField);
                names.add(anot.name());
            }
        }
        int output = JOptionPane.showConfirmDialog(null, myPanel,
                "Please enter bound values for graph vertices:", JOptionPane.OK_CANCEL_OPTION);
        if (output == JOptionPane.OK_OPTION) {
            Vector<Pair<Integer, Integer>> res = new Vector<>();
            for (int i = 0; i < v.size(); i++) {
                Scanner sc = new Scanner(v.get(i).getText());
                if(i==0) {
                    sc.useDelimiter(":");
                    res.add(new Pair<>(
                            Integer.parseInt(sc.next()),
                            Integer.parseInt(sc.next())));
                }
            }
            int from = res.get(0).first;
            int to = res.get(0).second;
            IterProgressBar pb = new IterProgressBar(to-from+1);
            pb.setVisible(true);
            for (int i = from; i <= to; i++) {
                try {
                    o.getClass().getDeclaredField(names.get(0)).set(o, i);
                    GraphModel g;
                    if(ext instanceof SimpleGeneratorInterface)
                      g = GraphGenerator.getGraph(false, (SimpleGeneratorInterface) ext);
                    else
                      g = ((GraphGeneratorExtension) ext).generateGraph();
                    pb.setValue(i);
                    pb.validate();
                    RenderTable ret=(RenderTable)f.f(g);
                    if(retForm.size()==1) {
                        Vector<String> tts = new Vector<>();
                        tts.add(fieldName);
                        tts.addAll(ret.getTitles());
                        retForm.setTitles(tts);
                    }
                    Vector<Object> results = new Vector<>();
                    results.add(i);
                    results.addAll(ret.poll());
                    retForm.add(results);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        }
        return retForm;
    }
}
