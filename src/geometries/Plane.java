package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Plane implements Geometry  {
    Point3D q0;
    Vector normal;

    /**
     * c-tors
     * @param q0
     * @param normal
     */
    public Plane(Point3D q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal;
    }

    public Plane(Point3D q0 ,Point3D q1, Point3D q2) {
        this.q0 = q0;
        this.normal = null;
    }

    //getters
    public Point3D getQ0() {
        return q0;
    }

    public Vector getNormal() {
        return normal;
    }

    /**
     * override func get normal
     * @param point3D
     * @return null
     */
    @Override
    public Vector getNormal(Point3D point3D) {
        return null;
    }
}
