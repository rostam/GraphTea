package graphtea.extensions.reports.hamilton;

/**
 * Created by rostam on 27.01.15.
 * Hamiltonian Cycle
 */
import java.util.Arrays;
import java.util.Random;

public class HamiltonianCycle

{
    public boolean seen(int v) {
        for (int i = 0; i < cycleSize - 1; i++)
            if (cycle[i] == v)
                return true;
        return false;
    }

    public int[] HamiltonCycle(int[][] g) {
        numOfVertices = g.length;
        cycle = new int[numOfVertices];
        Arrays.fill(cycle, -1);
        adjMat = g;
        try {
            cycle[0] = 0;
            cycleSize = 1;
            dfs(0);
            return null;
        } catch (Exception e) {
            return cycle;
        }
    }

    public void dfs(int startingVertex) throws Exception {
        if (adjMat[startingVertex][0] == 1 && cycleSize == numOfVertices)
            throw new Exception("Solution found");
        if (cycleSize == numOfVertices)
            return;

        for (int aVertex = 0; aVertex < numOfVertices; aVertex++) {
            if (adjMat[startingVertex][aVertex] == 1) {
                cycle[cycleSize++] = aVertex;

                adjMat[startingVertex][aVertex] = 0;
                adjMat[aVertex][startingVertex] = 0;

                if (!seen(aVertex))
                    dfs(aVertex);

                adjMat[startingVertex][aVertex] = 1;
                adjMat[aVertex][startingVertex] = 1;

                cycle[--cycleSize] = -1;
            }
        }
    }

    private int numOfVertices;
    private int cycleSize;
    private int[] cycle;
    private int[][] adjMat;

}
