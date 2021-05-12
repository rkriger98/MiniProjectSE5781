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
     *
     * @param point3D
     * @return null
     */
    @Override
    public Vector getNormal(Point3D point3D) {
        return null;
    }

    /**
     * toString method
     * @return
     */
    @Override
    public String toString() {
        return "Cylinder{" +
                "_height=" + _height +
                ", _radius=" + _radius +
                ", _axisRay=" + _axisRay +
                '}';
    }


    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return super.findIntersections(ray);
    }
}

