package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Cylinder extends Tube implements Geometry {

    double _height;

    public Cylinder(double radius, Ray axisRay, double height) {
        super(radius, axisRay);
        _height = height;
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

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}
