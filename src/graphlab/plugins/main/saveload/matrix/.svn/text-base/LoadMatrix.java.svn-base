// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.saveload.matrix;

import graphlab.graph.graph.GraphModel;
import graphlab.plugins.main.saveload.SaveLoadPluginMethods;
import graphlab.plugins.main.saveload.core.GraphIOException;
import graphlab.plugins.main.saveload.core.extension.GraphReaderExtension;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

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
}


