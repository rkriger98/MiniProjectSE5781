package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/**
 * Plane Triangle represents a triangle in 3D Cartesian coordinate system
 * extends the Polygon class
 */
public class Triangle extends Polygon{

    /**
     * Triangle c-tor that gets three points
     */
    public Triangle(Point3D p1, Point3D p2,Point3D p3) {
        super(p1, p2, p3);
    }

    /**
     * calls the super findGeoIntersections method
     * @param ray
     * @return intersection point
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray,double maxDis) {
        return super.findGeoIntersections(ray, maxDis);
    }
}