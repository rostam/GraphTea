package graphtea.extensions.reports.spanningtree;

public class MSTPrim {
    public int[] prim(int g[][]) {
        int[] parent = new int[g.length];
        int[] id    = new int[g.length];
        boolean[] MST = new boolean[g.length];

        for (int i = 0; i < g.length; i++) {
            id[i] = 1000000;
            MST[i] = false;
        }

        id[0] = 0;
        parent[0] = -1;

        for (int count = 0; count < g.length - 1; count++) {
            int u = minimumID(id, MST);
            MST[u] = true;
            for (int v = 0; v < g.length; v++)
                if (g[u][v] != 0 && !MST[v] && (g[u][v] < id[v])) {
                    parent[v] = u;
                    id[v] = g[u][v];
                }
        }
        return parent;
    }

    public int minimumID(int id[], boolean MST[])
    {
        int min = 10000, minIndex=0;
        for (int v = 0; v < id.length; v++) {
            if (!MST[v] && id[v] < min) {
                min = id[v];
                minIndex = v;
            }
        }
        return minIndex;
    }
}