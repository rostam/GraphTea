package graphtea.extensions.reports.spectralreports;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import graphtea.graph.graph.GraphModel;
import graphtea.plugins.reports.extension.GraphReportExtension;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;



/**
 * Description here.
 *
 * @author Hooman Mohajeri Moghaddam
 */
public class LaplacianOfGraph implements GraphReportExtension  {


	boolean inDegree;
	/**
	 * Round func
	 * @param value
	 * @param decimalPlace
	 * @return rounded value of the input to the number of decimalPlace
	 */
	private double round(double value, int decimalPlace) {
		double power_of_ten = 1;
		while (decimalPlace-- > 0)
			power_of_ten *= 10.0;
		return Math.round(value * power_of_ten)
		/ power_of_ten;
	}

	/**
	 * Undirected Laplacian.
	 * @param A the Adjacency matrix of the graph
	 * @return Laplacian of the graph
	 */
	private Matrix getLaplacian(Matrix A)
	{
		//double[][] res=new double[g.numOfVertices()][g.numOfVertices()];


		int n=A.getArray().length;
		double[][] ATemp = A.getArray();

		Matrix D = new Matrix(n,n);
		double[][] DTemp = D.getArray();
		int sum; 
		if(inDegree)
		{
			for(int i=0; i<n ; i++ )
			{
				sum = 0 ;
				for(int j=0; j<n ; j++)
				{
					sum+=ATemp[j][i];
				}
				DTemp[i][i]=sum;
			}
		}
		else
		{
			for(int i=0; i<n ; i++ )
			{
				sum = 0 ;
				for(int j=0; j<n ; j++)
				{
					sum+=ATemp[i][j];
				}
				DTemp[i][i]=sum;
			}
		}

		return D.minus(A);
	}

	//
	private ArrayList<String> ShowLaplacian(Matrix A)
	{
		ArrayList<String> result = new ArrayList<String>();		

		double[][] Lap = getLaplacian(A).getArray();

		result.add(new String("Laplacian Matrix:"));
		for(double[] k : Lap)
		{
			result.add( Arrays.toString(k));
		}
		return result;
	}


	/**
	 * Gets the eigen values and vectors of the graph and returns them as an array of strings.
	 * @param matrix the Adjacency matrix of the graph
	 * @return Laplacian of the graph
	 */
	private ArrayList<String> getEigenValuesAndVectors(Matrix matrix)
	{
		ArrayList<String> result = new ArrayList<String>();
		result.add(new String("Eigen Value Decomposition:"));
		EigenvalueDecomposition ed = getLaplacian(matrix).eig();
		double rv[] = ed.getRealEigenvalues();
		double iv[] = ed.getImagEigenvalues();
		for (int i = 0; i < rv.length; i++)
			if (iv[i] != 0)
				result.add("" + round(rv[i], 3) + " + " + round(iv[i], 3) + "i");
			else
				result.add("" + round(rv[i], 3));
		result.add("Eigen Vectors:\n");
		double[][] eigenVectors = ed.getV().getArray();
		for (int k = 0; k < eigenVectors.length; k++)
			result.add(Arrays.toString(round(eigenVectors[k], 3)));
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
		return "Spectrum of Laplacian";
	}

	public String getDescription() {
		return "The Laplacian matrix associated with the graph";
	}

	public Object calculate(GraphModel g) {

		try {
			if(g.isDirected())
			{
				int a = JOptionPane.showOptionDialog(null, "Do you want to use in or out degrees for calculation of the Laplacian Matrix?", "Laplacian", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"In Degrees", "Out Degrees"}, "In Degrees");

				if (a== -1)
					return null;
				else if(a==0)
					inDegree = true;
				else
					inDegree = false;
			}
			Matrix A = g.getWeightedAdjacencyMatrix();
			ArrayList<String> calc = new ArrayList<String>(ShowLaplacian(A));
			calc.addAll(getEigenValuesAndVectors(A));
			return(calc);
		} catch (Exception e) {
			// TODO Auto-generated catch block

		}
		return null;

	}

	@Override
	public String getCategory() {
		return "Spectral";
	}

}
