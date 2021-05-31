package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;


public class Sphere extends RadialGeometry{

    final protected Point3D _center;

    public Point3D getCenter() {
        return _center;
    }

    /**
     * c-tor
     *
     * @param center
     * @param radius
     */
    public Sphere(double radius, Point3D center) {
        super(radius);
        _center = center;
    }

    /**
     * override func get normal
     *
     * @param p
     * @return normal to the Sphere
     */
    @Override
    public Vector getNormal(Point3D p) {
        if (p.equals(_center)) {
            throw new IllegalArgumentException("ERROR: p equals center");
        }
        Vector vector = p.subtract(_center);
        return vector.normalize();
    }

    /**
     * @return tostring of sphere
     */

    @Override
    public String toString() {
        return "Sphere{" +
                "_radius=" + _radius +
                ", _center=" + _center +
                '}';
    }


    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        Point3D P0 = ray.getP0();
        Vector v = ray.getDir();

        if (_center.equals(P0)) {
            throw new IllegalArgumentException("ray origin cannot be at sphere's center");
        }

        Vector u = _center.subtract(P0);

        double tm = alignZero(u.dotProduct(v));
        double d = alignZero(Math.sqrt(u.lengthSquared() - tm * tm));

        if (d > _radius) {
            return null;
        }
        double th = alignZero(Math.sqrt(_radius * _radius - d * d));

        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        if (t1 > 0 && t2 > 0) {
            Point3D P1 = P0.add(v.scale(t1));
            Point3D P2 = P0.add(v.scale(t2));

            return List.of(new GeoPoint(this,P1),new GeoPoint(this,P2));
        }
        else if (t1 > 0){
            Point3D P1 = P0.add(v.scale(t1));
            return List.of(new GeoPoint(this,P1));
        }
        else if (t2 >= 0){
            Point3D P2 = P0.add(v.scale(t2));
            return List.of(new GeoPoint(this,P2));
        }
        return null;
    }
}