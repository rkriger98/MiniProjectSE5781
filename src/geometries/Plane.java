package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Plane implements Geometry {
    final Point3D _q0;
    final Vector _normal;

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


    //getters
    public Point3D getQ0() {
        return _q0;
    }


    /**
     * @deprecated  use {@link #getNormal(Point3D)} with null as parameter.
     * @return normal
     */
    @Deprecated
    public Vector getNormal() {
        return _normal;
    }

    /**
     *
     * @param point3D dummy point not use for flat geometries
     *              should be assigned null value
     * @return normal to the plane
     */
    @Override
    public Vector getNormal(Point3D point3D) {
        return _normal;
    }
}
