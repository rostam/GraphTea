// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.saveload.matrix;

import graphtea.graph.graph.GraphModel;
import graphtea.plugins.main.saveload.SaveLoadPluginMethods;
import graphtea.plugins.main.saveload.core.GraphIOException;
import graphtea.plugins.main.saveload.core.extension.GraphReaderExtension;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

/**
 * @author Hooman Mohajeri Moghaddam
 */
public class LoadMatrix implements GraphReaderExtension {

	public boolean accepts(File file) {
		return SaveLoadPluginMethods.getExtension(file).equals(getExtension());
	}

	public String getName() {
		return "Matrix";
	}

	public String getExtension() {
		return "mat";
	}

	public GraphModel read(File file) throws GraphIOException {
		try {
			return loadMatrix(file);
		} catch (IOException e) {
			throw new GraphIOException(e.getMessage());
		}
	}

	public String getDescription() {
		return "Matrix File Format";
	}

	public static GraphModel loadMatrix(File selectedFile) throws IOException {
		GraphModel g;
		int a = JOptionPane.showConfirmDialog(null, "Do you want to load directed graph?", "Load Graph",  JOptionPane.YES_NO_OPTION);

		if (a== JOptionPane.YES_OPTION)
			g= new GraphModel();
		else
			g= new GraphModel(false);
		
		FileReader in = new FileReader(selectedFile);
		BufferedReader br = new BufferedReader(in);
		String _, s = "";
		while ((_ = br.readLine()) != null) s += _ + "\n";
		Matrix.Matrix2Graph(Matrix.String2Matrix(s), g);
		return g;
	}

	public static GraphModel loadMatrix(File selectedFile, boolean isDirected) throws IOException {
		GraphModel g = new GraphModel(isDirected);
		FileReader in = new FileReader(selectedFile);
		BufferedReader br = new BufferedReader(in);
		String _, s = "";
		while ((_ = br.readLine()) != null) s += _ + "\n";
		Matrix.Matrix2Graph(Matrix.String2Matrix(s), g);
		return g;
	}

	public static Vector<GraphModel> loadMatrixes(File selectedFile, boolean isDirected) throws IOException {
		Vector<GraphModel> gs = new Vector<>();
		Scanner sc =new Scanner(selectedFile);
		sc.nextLine();
		String g="";

		while (sc.hasNextLine()) {
			String l = sc.nextLine();
			if(!l.equals("")){
				g+=l+"\n";
			} else {
				if(!g.equals("")) {
					GraphModel tmp = new GraphModel(isDirected);
					Matrix.Matrix2Graph(Matrix.String2Matrix(g), tmp);
					gs.add(tmp);
					g = "";
				}
			}
		}
		return gs;
	}
}


