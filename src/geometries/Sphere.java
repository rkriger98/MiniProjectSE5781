package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Point3D.PointZERO;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Sphere class represents a sphere in 3D Cartesian coordinate system
 * extends the RadialGeometry class
 */
public class Sphere extends RadialGeometry {

    /**
     * represents the sphere center point
     */
    final protected Point3D _center;

    public Point3D getCenter() {
        return _center;
    }

    /**
     * Sphere c-tor that gets a point and a radius
     *
     * @param center the center point
     * @param radius the radius of the ray
     */
    public Sphere(double radius, Point3D center) {
        super(radius);
        _center = center;
    }

    /**
     * override func get normal
     *
     * @param p point on the sphere
     * @return the normal vector of the Sphere
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
     * override function that gets a ray and checks the Intersections of the ray with the sphere or spheres
     *
     * @param ray- a ray from @primitives.Ray
     * @return a List of 3D Points with the values of all the Intersections points of the ray and the sphere or spheres.
     * if there are no Intersections points, the function returns null
     */
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDis) {

        try {
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
            if (t1 <= 0 && t2 <= 0) {
                return null;
            }

            Vector vt1 = v.scale(t1);
            Vector vt2 = v.scale(t2);

            if (alignZero(t1) > 0 && alignZero(t2) > 0) {

                if (alignZero(t1 - maxDis) <= 0 && alignZero(t2 - maxDis) <= 0) {

                    Point3D P1 = P0.add(vt1);
                    Point3D P2 = P0.add(vt2);

                    return List.of(new GeoPoint(this, P1), new GeoPoint(this, P2));
                }

            }
            if (t1 > 0) {

                if (alignZero(t1 - maxDis) <= 0) {
                    Point3D P1 = P0.add(vt1);
                    return List.of(new GeoPoint(this, P1));
                }

            }
            if (t2 > 0) {
                if (alignZero(t2 - maxDis) <= 0) {
                    Point3D P2 = P0.add(vt2);
                    return List.of(new GeoPoint(this, P2));

                }


            }
        } catch (Exception E) {
            return null;
        }
        return null;
    }

}