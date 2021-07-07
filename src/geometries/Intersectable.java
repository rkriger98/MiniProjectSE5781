package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;
import java.util.stream.Collectors;

/**
 * interface that will be used in all Geometry shapes
 *
 */
public interface Intersectable {

    /**
     * GeoPoint class a point on a shape
     * geometry - a geometry shape
     * point - a point on the shape
     */
    public static class GeoPoint {

        public Geometry geometry;
        public Point3D point;

        /**
         * c-tor for GeoPoint
         *
         * @param geometry a geometry shape
         * @param point a point on the shape
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }

        /**
         * Checks if two geometries are equal
         * @param o
         * @return
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry.equals(geoPoint.geometry) &&
                    point.equals(geoPoint.point);
        }


    }

    /**
     * find all intersection points from the ray
     * @param ray the ray pointing to
     * @return intersection points
     */
    default List<Point3D> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).collect(Collectors.toList());
    }


    /**
     * function that gets a ray and checks the Intersections of the ray with the shape or shapes
     *
     * @param ray- a ray from @primitives.Ray
     * @return a List of 3D Points with the values of all the Intersections points of the ray and the shape or shapes.
     * if there are no Intersections points, the function returns null
     */
    default List <GeoPoint> findGeoIntersections(Ray ray){
        return findGeoIntersections(ray,Double.POSITIVE_INFINITY);
    }

    List <GeoPoint> findGeoIntersections(Ray ray,double maxDis);
}