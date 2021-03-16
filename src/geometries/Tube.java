package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

public class Tube implements Geometry  {

  final Ray _axisRay;
  final double _radius;

    public Tube(double radius,Ray axisRay) {
        _radius = radius;
        _axisRay = axisRay;
     }

    /**
     * override func get normal
     * @param P point "looking" at tube
     * @return null
     */
    @Override
    public Vector getNormal(Point3D P) {
        Point3D P0 = _axisRay.getP0();
        Vector v = _axisRay.getDir();
        Vector P0_P = P.subtract(P0);

        double t = v.dotProduct(P0_P);

        if(isZero(t)) {
            return P0_P;
        }
        Point3D O = P0.add(v.scale(t));

        Vector n = P.subtract(O);
        return n.normalize();
    }
}
