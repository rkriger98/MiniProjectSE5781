package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

public class Tube extends RadialGeometry implements Geometry  {

  final Ray _axisRay;

    public Tube(double radius,Ray axisRay) {
        super(radius);
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

        if(O.equals(P)){
            throw new IllegalArgumentException("point cannot be the reference point of the tube");
        }

        Vector n = P.subtract(O);
        return n.normalize();
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}
