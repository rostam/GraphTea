package graphtea.extensions.reports.boundcheck.forall.iterators;

import graphtea.extensions.reports.boundcheck.forall.IterProgressBar;
import graphtea.extensions.reports.boundcheck.forall.filters.GeneratorFilters;
import graphtea.graph.graph.GraphModel;
import graphtea.library.util.Pair;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.extension.Extension;
import graphtea.platform.extension.ExtensionLoader;
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
 * Created by rostam on 31.10.15.
 * GraphModel iterator on generators
 */
public class GraphGeneratorIterator extends GraphModelIterator {

    HashMap<String, AbstractAction> hm = ExtensionLoader.loadedInstances;
    HashMap<String, String> nameToClass = GeneratorFilters.nameToClass;
    private int from = 0, to = 0;
    int cnt;
    Parametrizable o;
    Extension ext;
    Vector<String> names;
    IterProgressBar pb;

    public GraphGeneratorIterator(String name) {
        ext = ((AbstractExtensionAction) hm.get(
                nameToClass.get(name))).getTarget();
        Vector<JTextField> v = new Vector<>();
        JPanel myPanel = new JPanel();
        o = (Parametrizable) ext;
        names = new Vector<>();
        for (Field ff : o.getClass().getFields()) {
            Parameter anot = ff.getAnnotation(Parameter.class);
            if (anot != null) {
                JTextField xField = new JTextField(5);
                v.add(xField);
                myPanel.add(new JLabel(anot.name() + ":"));
                myPanel.add(xField);
                names.add(anot.name());
            }
        }
        int output = JOptionPane.showConfirmDialog(null, myPanel,
                "Please enter bound values for graph vertices:", JOptionPane.OK_CANCEL_OPTION);
        if (output == JOptionPane.OK_OPTION) {
            Vector<Pair<Integer, Integer>> res = new Vector<>();
            for (JTextField aV : v) {
                Scanner sc = new Scanner(aV.getText());
                sc.useDelimiter(":");
                res.add(new Pair<>(
                        Integer.parseInt(sc.next()),
                        Integer.parseInt(sc.next())));

            }
            from = res.get(0).first;
            to = res.get(0).second;
            pb = new IterProgressBar(to - from + 1);
            pb.setVisible(true);
        }

        this.cnt = from;
    }

    public int size() {
        return to-from+1;
    }

    @Override
    public int getCount() {
        return cnt-2;
    }

    @Override
    public boolean hasNext() {
        return cnt<=to+1;
    }

    @Override
    public GraphModel next() {
        GraphModel g = null;
        try {
            o.getClass().getDeclaredField(names.get(0)).set(o, cnt);
            if (ext instanceof SimpleGeneratorInterface)
                g = GraphGenerator.getGraph(false, (SimpleGeneratorInterface) ext);
            else
                g = ((GraphGeneratorExtension) ext).generateGraph();

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        cnt++;
        pb.setValue(cnt);
        pb.validate();
        if(cnt == to-1) pb.setVisible(false);
        return g;
    }

    @Override
    public void remove() {

    }
}
