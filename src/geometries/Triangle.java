package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/**
 * TODO
 */

public class Triangle extends Polygon{

    //c-tor
    public Triangle(Point3D p1, Point3D p2,Point3D p3) {
        super(p1, p2, p3);
    }

    /**
     * @param ray
     * @return intersection point
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return super.findGeoIntersections(ray);
    }

    /**
     * triangle toString
     * @return string
     */

    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + _vertices +
                ", plane=" + _plane +
                '}';
    }


}
