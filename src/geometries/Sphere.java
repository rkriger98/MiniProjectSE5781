package geometries;

import primitives.Point3D;
import primitives.Vector;


public class Sphere implements Geometry  {
    Point3D center;
    double radius;

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
