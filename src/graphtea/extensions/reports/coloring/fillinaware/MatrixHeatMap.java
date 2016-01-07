package graphtea.extensions.reports.coloring.fillinaware;

import Jama.Matrix;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by rostam on 07.01.16.
 */
public class MatrixHeatMap extends JFrame {
    Matrix m;
    public MatrixHeatMap (Matrix m) {
        this.m = m;
        this.setVisible(true);
        int height = m.getRowDimension();
        int width = m.getColumnDimension();
        this.setSize(new Dimension(width,height));
        this.setLocationRelativeTo(null);
    }

    public void paint(Graphics g) {
        int height = m.getRowDimension();
        int width = m.getColumnDimension();
        BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        for ( int rc = 0; rc < height; rc++ ) {
            for ( int cc = 0; cc < width; cc++ ) {
                if(m.get(rc,cc) != 0 ) {
                    img.setRGB(cc, rc, Color.WHITE.getRGB());
                }
            }
        }
        g.drawImage(img,0,0,width,height,null);
    }
}
