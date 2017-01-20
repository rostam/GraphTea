package graphtea.graph.graph;

/**
 * Created by rostam on 24.05.16.
 *
 */
public class GRect {
    public double x, y, w, h;

    public GRect() {
        setRect(0, 0, 0, 0);
    }

    public GRect(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void setRect(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }


    double getX() {
        return x;
    }

    double getY() {
        return y;
    }

    public double getWidth() {
        return w;
    }

    public double getHeight() {
        return h;
    }

    public double getMinX() {
        return this.getX();
    }

    public double getMinY() {
        return this.getY();
    }

    public void add(double newx, double newy) {
        double minx = Math.min(getX(), newx);
        double maxx = Math.max(getMaxX(), newx);
        double miny = Math.min(getY(), newy);
        double maxy = Math.max(getMaxY(), newy);
        setRect(minx, miny, maxx - minx, maxy - miny);
    }

    public void add(GPoint p) {
        this.add(p.x, p.y);
    }

    public double getMaxX() {
        return getX() + getWidth();
    }

    public double getMaxY() {
        return getY() + getHeight();
    }

    public boolean inside(GPoint p) {
        return inside(p.x,p.y);
    }

    public boolean inside(double var1, double var2) {
        double var3 = w;
        double var4 = h;
        double var5 = this.x;
        double var6 = this.y;
        if (var1 >= var5 && var2 >= var6) {
            var3 += var5;
            var4 += var6;
            return (var3 < var5 || var3 > var1) && (var4 < var6 || var4 > var2);
        } else {
            return false;
        }
    }

    public GRect getBounds() {
        double var1 = this.getWidth();
        double var3 = this.getHeight();
        if(var1 >= 0.0D && var3 >= 0.0D) {
            double var5 = this.getX();
            double var7 = this.getY();
            double var9 = Math.floor(var5);
            double var11 = Math.floor(var7);
            double var13 = Math.ceil(var5 + var1);
            double var15 = Math.ceil(var7 + var3);
            return new GRect((int)var9, (int)var11, (int)(var13 - var9), (int)(var15 - var11));
        } else {
            return new GRect();
        }
    }

    public void grow(int var1, int var2) {
        long var3 = (long)this.x;
        long var5 = (long)this.y;
        long var7 = (long)this.w;
        long var9 = (long)this.h;
        var7 += var3;
        var9 += var5;
        var3 -= (long)var1;
        var5 -= (long)var2;
        var7 += (long)var1;
        var9 += (long)var2;
        if(var7 < var3) {
            var7 -= var3;
            if(var7 < -2147483648L) {
                var7 = -2147483648L;
            }

            if(var3 < -2147483648L) {
                var3 = -2147483648L;
            } else if(var3 > 2147483647L) {
                var3 = 2147483647L;
            }
        } else {
            if(var3 < -2147483648L) {
                var3 = -2147483648L;
            } else if(var3 > 2147483647L) {
                var3 = 2147483647L;
            }

            var7 -= var3;
            if(var7 < -2147483648L) {
                var7 = -2147483648L;
            } else if(var7 > 2147483647L) {
                var7 = 2147483647L;
            }
        }

        if(var9 < var5) {
            var9 -= var5;
            if(var9 < -2147483648L) {
                var9 = -2147483648L;
            }

            if(var5 < -2147483648L) {
                var5 = -2147483648L;
            } else if(var5 > 2147483647L) {
                var5 = 2147483647L;
            }
        } else {
            if(var5 < -2147483648L) {
                var5 = -2147483648L;
            } else if(var5 > 2147483647L) {
                var5 = 2147483647L;
            }

            var9 -= var5;
            if(var9 < -2147483648L) {
                var9 = -2147483648L;
            } else if(var9 > 2147483647L) {
                var9 = 2147483647L;
            }
        }

        this.reshape((int)var3, (int)var5, (int)var7, (int)var9);
    }

    public void reshape(int var1, int var2, int var3, int var4) {
        this.x = var1;
        this.y = var2;
        this.w = var3;
        this.h = var4;
    }

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

    public void add(GRect var1) {
        double var2 = Math.min(this.getMinX(), var1.getMinX());
        double var4 = Math.max(this.getMaxX(), var1.getMaxX());
        double var6 = Math.min(this.getMinY(), var1.getMinY());
        double var8 = Math.max(this.getMaxY(), var1.getMaxY());
        this.setRect(var2, var6, var4 - var2, var8 - var6);
    }

}
