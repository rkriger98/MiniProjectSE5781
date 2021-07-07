package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * Tube class represents a tube in 3D Cartesian coordinate system
 * extends RadialGeometry
 */
public class Tube extends RadialGeometry {

    /**
     *  represents the Tube height
     */
    final Ray _axisRay;

    /**
     *  tube c-tor that gets a radius and a ray
     * @param radius the tube radius (RadialGeometry)
     * @param axisRay the tube ray
     */
    public Tube(double radius,Ray axisRay) {
        super(radius);
        _axisRay = axisRay;
    }

    /**
     * override func get normal
     * @param P point "looking" at tube
     * @return
     */
    @Override
    public Vector getNormal(Point3D P) {
        Point3D P0 = _axisRay.getP0();
        Vector v = _axisRay.getDir();
        Vector P0_P = P.subtract(P0);

        double t = v.dotProduct(P0_P);

        if(isZero(t)) {
            return P0_P.normalized();
        }
        Point3D O = P0.add(v.scale(t));

        if(O.equals(P)){
            throw new IllegalArgumentException("point cannot be the reference point of the tube");
        }

        Vector n = P.subtract(O);
        return n.normalize();
    }

    /**
     * override findGeoIntersections func
     * @param ray
     * @param maxDis
     * @return
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray,double maxDis) {
        return null;
    }
}