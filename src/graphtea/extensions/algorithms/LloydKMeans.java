package graphtea.extensions.algorithms;
/**
 * http://frodriguez.webs.com/Java%20Algo/kmeans.txt
 * 
  * Lloyd's Algorithm - A greedy approximation to the kmeans clustering problem (NP-HARD), this is similar to
  *                     finding the centers of the voronoi cells of a tessellation that has the input points
  *                     distributed as evenly as possible throughout the cells.
  *
  * Steps:
  * 		1) It guesses an initial location for the center of each cluster
  *         2) It assigns each input point to one cluster (the one with the closest center)
  *			3) For each cluster, move its center to the centroid of all its assigned points.
  *			4) Repeat steps 2-3 until no change occurs.
  *
  * Returns: An array of Clusters. If you need exactly k clusters you may have to run it several times since
  *          some clusters may die out during the algorithm, this shouldn't be a problem because this function
  *          usually runs very fast.
  */
 
import graphtea.graph.graph.GraphPoint;

import java.util.ArrayList;
import java.util.Arrays;

public class LloydKMeans {

		// Lloyd's Algorithm (for K-Means Clustering)
	public static Cluster[] cluster(GraphPoint[] p, int k) {
		GraphPoint[] center = new GraphPoint[k];
		int[] parent = new int[p.length];

		Arrays.fill(parent, -1);

		// find an initial "fair" placement for the k clusters
		// places them radialy around the centroid
		double sx = 0., sy = 0.;
		for (int i = 0; i < p.length; i++) {
			sx += p[i].x;
			sy += p[i].y;
		}

		sx /= p.length;
		sy /= p.length;

		for (int i = 0; i < k; i++)
			center[i] = new GraphPoint((int) (sx + 100 * Math.random()),
					(int) (sy + 100 * Math.random()));

		while (true) // improve on those centers with each iteration
		{
			boolean improved = false;

			// partition the GraphPoints into k clusters
			for (int x = 0; x < p.length; x++) {
				double bestDist = Double.MAX_VALUE;
				int inx = -1;
				for (int i = 0; i < k; i++) {
					double d = p[x].distance(center[i]);
					if (inx == -1 || d < bestDist) {
						inx = i;
						bestDist = d;
					}
				}

				if (parent[x] != inx)
					improved = true; // something was altered

				parent[x] = inx; // add GraphPoint to the proper cluster
			}

			if (!improved)
				break;

			// now improve by updating the center of the clusters
			double[] sumX = new double[k];
			double[] sumY = new double[k];
			int[] count = new int[k];

			for (int i = 0; i < p.length; i++) {
				sumX[parent[i]] += p[i].x;
				sumY[parent[i]] += p[i].y;
				count[parent[i]]++;
			}

			for (int i = 0; i < k; i++)
				center[i] = new GraphPoint((int) (sumX[i] / count[i]),
						(int) (sumY[i] / count[i]));
		}

		ArrayList<Cluster> ret = new ArrayList<Cluster>();
		for (int i = 0; i < k; i++)
			ret.add(new Cluster(center[i]));

		for (int i = 0; i < p.length; i++)
			ret.get(parent[i]).member.add(p[i]);

		// remove all empty clusters (due to bad initial placement)
		for (int i = 0; i < ret.size(); i++)
			if (ret.get(i).member.size() == 0)
				ret.remove(i--);

		return ret.toArray(new Cluster[0]); // the result may not have exactly k
											// clusters since some clusters may
											// have been superfluous
	}

}

// ===================================================================================



