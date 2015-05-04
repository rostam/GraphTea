package graphtea.library.util;

/**
administration of geodetic ellipsoids of revolution and spheres.
author    hehl@tfh-berlin.de
version   17-May-2005
*/
public final class Ellipsoid {
  private String _name; // name of ellipsoid
  private double _a,_f; // semimajor axis and flattening

/**
shortcuts for pre-defined ellipsoids of revolution.
*/
        public static enum ELL { BESSEL, HAYFORD, WGS84 }
        private static final String classname = "Ellipsoid";

/** parameters of Bessel ellipsoid */
  static final public Ellipsoid BESSEL = new Ellipsoid();

/** parameters of Hayford ellipsoid */
  static final public Ellipsoid HAYFORD = new Ellipsoid(ELL.HAYFORD);

/** parameters of WGS84 ellipsoid */
  static final public Ellipsoid WGS84 = new Ellipsoid(ELL.WGS84);

/** */
private void init(String n, double a, double f) throws Msg {
        String r = classname+".init";
  _name = n;
  _a = a;
  _f = f;
        if(null == n || n.equals(""))
                throw new Msg(r,"name must not be empty");
        if(Math.abs(a-6365.E3)>5.E3)
                throw new Msg(r,String.format("invalid semimajor axis a= %.3f m",a));
        if(!isSphere() && (e2() < 0.006 || e2() > 0.008))
      throw new Msg(r,"invalid f; 1/f="+1./f());
}

/**
default constructor - initializes the Bessel ellipsoid.
*/
public Ellipsoid() {
        this(ELL.BESSEL);
}

  private Ellipsoid(ELL id) {
        try {
                switch(id) {
                        case HAYFORD:
                                                                init("Hayford",6378388.0,1./297.0); break;
                        case WGS84:
                                                                init("WGS84",6378137.0,1/298.257222); break;
                        default:        init("Bessel",6377397.155,1./299.15281285);
                }
        }
        catch(Exception ex) {}
  }

/**
generates a sphere of given radius.
@param radius radius  of the sphere
*/
  public Ellipsoid(double radius) throws Msg {
    init("sphere",radius,0.0);
  }

/**
copy constructor.
@param e a valid ellipsoid
*/
  public Ellipsoid(Ellipsoid e) {
        try {
                init(e.name(),e.a(),e.f());
        }
        catch(Exception ex) {}
  }

/**
creates a named sphere with given radius.
@param n name of the sphere
@param radius radius in meters
*/
  public Ellipsoid(String n, double radius) throws Msg {
    init(n,radius,0.0);
  }

/**
creates a named ellipsoid from semimajor axis and flattening.
@param n name of ellipsoid
@param a semimajor axis in meters
@param f flattening
*/
        public Ellipsoid(String n, double a, double f) throws Msg {
                init(n,a,f);
        }

/** @return the name of the current ellipsoid */
  public String name() { return(_name); }

/** @return the full name of the current ellipsoid */
  public String fullname() { return(name()+(isSphere() ? " sphere" : " ellipsoid")); }

/** @return the semimajor axis (in meters) of the current ellipsoid */
  public double a()  { return(_a); }

/** @return the semiminor axis (in meters) of the current ellipsoid */
  public double b()  { return(a()*(1.-f())); }

/** @return the flattening of the current ellipsoid */
  public double f()  { return(_f); }

/** @return the metric flattening of the current ellipsoid */
  public double a_minus_b()  { return(a()*f()); }

/** @return the square of the first numerical eccentricity */
  public double e2() { return(f()*(2.-f())); }

/** @return the first numerical eccentricity */
  public double e() { return(Math.sqrt(e2())); }

/** @return true if ellipsoid is a sphere */
  public boolean isSphere() { return(0.0 == f()); }

/**
@param phirad the ellipsoidal latitude in radians
@return the reduced latitude in radians
*/
  public double phi2beta(double phirad) {
    return(Math.atan(Math.tan(phirad)*(1.-f())));
  }

/**
@return the ellipsoidal latitude in radians
@param betarad the reduced latitude in radians
*/
  public double beta2phi(double betarad) {
    return(Math.atan(Math.tan(betarad)/(1.-f())));
  }

/** @return a description of the current ellipsoid */
  public String toString() {
    String result = name() +
        (isSphere() ? " sphere\n R=" : " ellipsoid\n a=")+
       String.format("%.3f",a())+" m";
    if(!isSphere()) result += "; 1/f= "+
      String.format("%.3f",1./f())+"; e2="+
      String.format("%.5f",e2());
    return result;
  }

/** test routine */
  public static void demo() {
                System.out.println("*********************************");
                System.out.println("*** Test of Class 'Ellipsoid' ***");
                System.out.println("*********************************");
                System.out.println();

    System.out.println(HAYFORD);
    try {
        System.out.println(new Ellipsoid("Test",6.3E6,1./300.));
      double phi = 50.0;
            final double RHODEG = 180./Math.PI;
            Ellipsoid ell = BESSEL;
            System.out.println("on "+ell.name()+":  phi="+phi+" deg --> beta="+
        ell.phi2beta(phi/RHODEG)*RHODEG+" deg");
                }
                catch(Msg msg) {}
  }

} // end class Ellipsoid
