package graphtea.extensions;

import javax.swing.*;

/**
 * Written by Farshad Toosi (farshad.toosi@lero.ie)
 */
public class RandomTree {

	
	int size;
	
	public RandomTree(int size) {
		this.size = size;
	}
	
	private static boolean checkSize(int size){
		if(size<2) {
			JOptionPane.showMessageDialog(null, "null", "The input too small or negetive.", size);
			return false;
		}
		else
			return true;
	}
	
	public int [][] getEdgeList() {
		
		if(checkSize(this.size)) {
			int [][] edge = new int [2][this.size-1];
			edge[0][0] = 0;
			edge[1][0] = 1;
			int counter = 1;
			for(int i=2;i<this.size;i++) {
				int random = (int) (Math.random()*i);
				edge[0][counter] = random;
				edge[1][counter] = i;
				counter++;
			}
			
			return edge;
		}
		else
			return null;
	}
	
}
