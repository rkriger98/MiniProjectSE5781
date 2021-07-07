package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.Objects;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Plane class represents a plane in 3D Cartesian coordinate system
 */
public class Plane extends Geometry {

    /**
     * point represents a point on the plane
     * normal represents the normal vector to the plane
     */
    final Point3D _q0;
    final Vector _normal;

    /**
     * c-tor of plane class, gets a point and normal vector
     * @param p0 the point on the plan
     * @param normal the normal vector
     */
    public Plane(Point3D p0, Vector normal) {
        _q0 = p0;
        _normal = normal.normalized();
    }

    /**
     * Constructor of Plane from 3 points on its surface
     * the points are ordered from right to left
     * forming an arc in right direction
     *
     * @param p1
     * @param p2
     * @param p3
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        _q0 = p1;

        Vector U = p2.subtract(p1);
        Vector V = p3.subtract(p1);

        Vector N = U.crossProduct(V);

        N.normalize();

        //right hand rule
        _normal = N;
    }


    /**
     * getter
     * @return the point on the plane
     */
    public Point3D getQ0() {
        return _q0;
    }


    /**
     * getter
     * @return the normal
     */
    @Deprecated
    public Vector getNormal() {
        return _normal;
    }

    /**
     * getter
     * @param point3D dummy point not use for flat geometries
     * should be assigned null value
     * @return normal to the plane
     */
    @Override
    public Vector getNormal(Point3D point3D) {
        return _normal;
    }

    /**
     * override function that gets a ray and checks the Intersections of the ray with the plan or plans
     *
     * @param ray- a ray from @primitives.Ray
     * @return a List of 3D Points with the values of all the Intersections points of the ray and the plan or plans.
     * if there are no Intersections points, the function returns null
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray,double maxDis) {
        try {
            Point3D P0 = ray.getP0();
            Vector v = ray.getDir();

            Vector n = _normal;

            if (_q0.equals(P0)) {
                return null;
            }

            Vector P0_Q0 = _q0.subtract(P0);

            //numerator
            double nP0Q0 = alignZero(n.dotProduct(P0_Q0));

            //
            if (isZero(nP0Q0)) {
                return null;
            }

            //denominator
            double nv = alignZero(n.dotProduct(v));

            // ray is lying in the plane axis
            if (isZero(nv)) {
                return null;
            }

            double t = alignZero(nP0Q0 / nv);

            if (t <= 0) {
                return null;
            }

            if (alignZero(t - maxDis) <= 0) {
                Point3D p = ray.getPoint(t);
                return List.of(new GeoPoint(this, p));
            }
        }catch (Exception e){
            return null;
        }

      return null;
    }

    /**
     * plane toString
     * @return
     */
    @Override
    public String toString() {
        return "Plane{" +
                "_q0=" + _q0 +
                ", _normal=" + _normal +
                '}';
    }

    /**
     * Checks if two plans are equal
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plane plane = (Plane) o;
        return _q0.equals(plane._q0) && _normal.equals(plane._normal);
    }

}