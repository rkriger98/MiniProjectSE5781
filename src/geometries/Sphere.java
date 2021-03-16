package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;


public class Sphere extends RadialGeometry implements Geometry  {
    final protected Point3D _center;

    public Point3D getCenter() {
        return _center;
    }

    /**
     * c-tor
     * @param center
     * @param radius
     */
    public Sphere(double radius,Point3D center) {
        super(radius);
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

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}
