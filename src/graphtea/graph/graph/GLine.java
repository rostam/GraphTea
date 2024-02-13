package graphtea.graph.graph;

/**
 * Created by rostam on 20.01.17.
 * @author M. Ali Rostami
 */
public class GLine {

    double x1,y1,x2,y2;

    public GLine(double x1, double y1, double x2, double y2) {
        this.x1=x1;this.y1=y1;this.x2=x2;this.y2=y2;
    }

    public double getX1() { return x1; }

    public double getY1() {return y1;}

    public GPoint getP1() {return new GPoint(x1,y1);}

    public double getX2() {return x2;}

    public double getY2() {return y2;}

    public GPoint getP2() {return new GPoint(x2,y2);}

    public static double ptLineDistSq(double var0, double var2, double var4, double var6, double var8, double var10) {
        var4 -= var0;
        var6 -= var2;
        var8 -= var0;
        var10 -= var2;
        double var12 = var8 * var4 + var10 * var6;
        double var14 = var12 * var12 / (var4 * var4 + var6 * var6);
        double var16 = var8 * var8 + var10 * var10 - var14;
        if(var16 < 0.0D) {
            var16 = 0.0D;
        }

        return var16;
    }

    public double ptLineDistSq(GPoint var1) {
        return ptLineDistSq(this.getX1(), this.getY1(), this.getX2(), this.getY2(), var1.getX(), var1.getY());
    }
}
