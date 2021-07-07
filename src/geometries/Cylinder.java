package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Cylinder class represents a cylinder in 3D Cartesian coordinate system
 * extends the Tube class
 */

public class Cylinder extends Tube{
    double _height;

    /**
     * Cylinder c-tor that gets two numbers and a ray
     *
     * @param radius
     * @param axisRay
     * @param height
     */
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


    /**
     * override findIntersections method
     * @param ray
     * @return list of intersection points with the ray
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return super.findIntersections(ray);
    }
}