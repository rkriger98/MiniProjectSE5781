package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/**
 * TODO
 */
public interface Intersectable {
    /**
     * find all intersection points from the ray
     * @param ray the ray pointing to
     * @return intersection points
     */
    List<Point3D> findIntersections(Ray ray);
}
