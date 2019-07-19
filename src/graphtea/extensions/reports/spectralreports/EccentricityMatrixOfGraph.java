package graphtea.extensions.reports.spectralreports;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.plugins.reports.extension.GraphReportExtension;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;


/**
 * Description here.
 *
 * @author Hooman Mohajeri Moghaddam
 */
public class EccentricityMatrixOfGraph implements GraphReportExtension  {


	boolean inDegree;
	/**
	 * Round func
	 * @param value The value
	 * @param decimalPlace The decimal place
	 * @return rounded value of the input to the number of decimalPlace
	 */
	private double round(double value, int decimalPlace) {
		double power_of_ten = 1;
		while (decimalPlace-- > 0)
			power_of_ten *= 10.0;
		return Math.round(value * power_of_ten)
		/ power_of_ten;
	}

	private ArrayList<String> showEccentricityMatrix(Matrix A)
	{
		ArrayList<String> result = new ArrayList<>();

		double[][] Lap = A.getArray();

		result.add("Eccentricity Matrix:");
		for(double[] k : Lap)
		{
			result.add( Arrays.toString(k));
		}
		return result;
	}


	/**
	 * Gets the eigen values and vectors of the graph and returns them as an array of strings.
	 * @param matrix the Adjacency matrix of the graph
	 * @return
	 */
	private ArrayList<String> getEigenValuesAndVectors(Matrix matrix)
	{
		ArrayList<String> result = new ArrayList<>();
		result.add("Eigen Value Decomposition:");
		EigenvalueDecomposition ed = matrix.eig();
		double rv[] = ed.getRealEigenvalues();
		double iv[] = ed.getImagEigenvalues();
		for (int i = 0; i < rv.length; i++)
			if (iv[i] != 0)
				result.add("" + round(rv[i], 5) + " + " + round(iv[i], 5) + "i");
			else
				result.add("" + round(rv[i], 5));
		result.add("Eigen Vectors:\n");
		double[][] eigenVectors = ed.getV().getArray();
		for (int k = 0; k < eigenVectors.length; k++)
			result.add(Arrays.toString(round(eigenVectors[k], 5)));
		return result;
	}

	private double[] round (double[] array, int prec)
	{
		double[] res=array;
		for(int i=0;i<array.length;i++)
			res[i]=round(res[i],prec);
		return res;

	}
	public String getName() {
		return "Spectrum of Eccentricity Matrix";
	}

	public String getDescription() {
		return "The eccentricity matrix associated with the graph";
	}

	public Object calculate(GraphModel g) {

		try {
			if(g.isDirected())
			{
				int a = JOptionPane.showOptionDialog(null, "Do you want to use in or out degrees for calculation of the eccentricity Matrix?", "Eccentricity Matrix", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"In Degrees", "Out Degrees"}, "In Degrees");

				if (a== -1)
					return null;
				else if(a==0)
					inDegree = true;
				else
					inDegree = false;
			}
			Matrix A = eccentricityMatrix(g, getAllPairsShortestPathWithoutWeight(g));
			ArrayList<String> calc = new ArrayList<>(showEccentricityMatrix(A));
			calc.addAll(getEigenValuesAndVectors(A));
			return(calc);
		} catch (Exception e) {
			// TODO Auto-generated catch block

		}
		return null;

	}

	public int eccentricity(GraphModel g, int v, Integer[][] dist) {
		int max_dist = 0;
		for(int j=0;j < g.getVerticesCount();j++) {
			if(max_dist < dist[v][j]) {
				max_dist = dist[v][j];
			}
		}
		return max_dist;
	}

	public Matrix eccentricityMatrix(GraphModel g, Integer[][] dist) {
		Matrix m = new Matrix(g.getVerticesCount(),g.getVerticesCount());
		for(int i=0;i < g.getVerticesCount();i++) {
			for(int j=0;j < g.getVerticesCount();j++) {
				int ecc_i = eccentricity(g, i, dist);
				int ecc_j = eccentricity(g, j, dist);
				if(dist[i][j] == Math.min(ecc_i, ecc_j)) {
					m.set(i,j,dist[i][j]);
				}
			}
		}
		return m;
	}

	public Integer[][] getAllPairsShortestPathWithoutWeight(final GraphModel g) {
		final Integer dist[][] = new Integer[g.numOfVertices()][g.numOfVertices()];
		Iterator<Edge> iet = g.edgeIterator();
		for (int i = 0; i < g.getVerticesCount(); i++)
			for (int j = 0; j < g.getVerticesCount(); j++)
				dist[i][j] = g.numOfVertices();

		for (Vertex v : g)
			dist[v.getId()][v.getId()] = 0;

		while (iet.hasNext()) {
			Edge edge = iet.next();
			dist[edge.target.getId()][edge.source.getId()] = 1;
			dist[edge.source.getId()][edge.target.getId()] = 1;
		}

		for (Vertex v : g)
			for (Vertex u : g)
				for (Vertex w : g) {
					if ((dist[v.getId()][w.getId()] + dist[w.getId()][u.getId()]) < dist[v.getId()][u.getId()])
						dist[v.getId()][u.getId()] = dist[v.getId()][w.getId()] + dist[w.getId()][u.getId()];
				}

		return dist;
	}

	@Override
	public String getCategory() {
		return "Spectral";
	}

}
