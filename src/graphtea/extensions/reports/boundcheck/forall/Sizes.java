package graphtea.extensions.reports.boundcheck.forall;

import java.util.HashMap;

/**
 * Created by rostam on 25.10.15.
 * The actual sizes of sets of graphs
 */
public class Sizes {
    public static HashMap<String, Integer> sizes = new HashMap<>();

    static {
        sizes.put("all2",1);
        sizes.put("all3",2);
        sizes.put("all4",6);
        sizes.put("all5",21);
        sizes.put("all6",112);
        sizes.put("all7",853);
        sizes.put("all8",11117);
        sizes.put("all9",261080);
        sizes.put("all10",11716571);

        sizes.put("tree2",1);
        sizes.put("tree3",1);
        sizes.put("tree4",2);
        sizes.put("tree5",3);
        sizes.put("tree6",6);
        sizes.put("tree7",11);
        sizes.put("tree8",23);
        sizes.put("tree9",47);
        sizes.put("tree10",106);
        sizes.put("tree11",235);

        sizes.put("tree12",551);
        sizes.put("tree13",1301);
        sizes.put("tree14",3159);
        sizes.put("tree15",7741);
        sizes.put("tree16",19320);
        sizes.put("tree17",48629);
        sizes.put("tree18",123867);
        sizes.put("tree19",317955);
        sizes.put("tree20",823065);

        sizes.put("tree21",2144505);
        sizes.put("tree22",5623756);
        sizes.put("tree23",14828074);
    }
}
