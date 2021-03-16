package geometries;

import primitives.Point3D;
import primitives.Vector;


public class Sphere implements Geometry  {
    Point3D _center;
    double _radius;

    /**
     * c-tor
     * @param center
     * @param radius
     */
    public Sphere(double radius,Point3D center) {
        _radius = radius;
        _center = center;
    }

    /**
     * override func get normal
     * @param p
     * @return normal to the Sphere
     */
    @Override
    public Vector getNormal(Point3D p) {
        if (p.equals(_center)){
            throw new IllegalArgumentException("ERROR: p equals center");
        }
        Vector vector = p.subtract(_center);
        return vector.normalize();
    }
}
