package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Class for all geometries
 * implements the Intersectable interface
 */
public class Geometries implements Intersectable {

    /**
     * a arrayList that includes a few Geometry shapes
     */
    List<Intersectable> _intersectables = null;

    /**
     *c-tor that builds a empty list
     */
    public Geometries() {
        _intersectables = new LinkedList<>();
    }

    /**
     * c-tor that builds a list from a given array
     * @param intersectables a group of Geometry shapes given as a array
     */
    public Geometries(Intersectable... intersectables) {
        _intersectables = new LinkedList<>();
        add(intersectables);
    }

    /**
     * adds a shape (or more) to the list
     * @param intersectables a Geometry shape or a group of shapes given as a array
     */
    public void add(Intersectable... intersectables) {
//        for (Intersectable item: intersectables) {
//              _intersectables.add(item);
//        }
        if (_intersectables == null) {
            _intersectables = new LinkedList<>();
        }
        _intersectables.addAll(Arrays.asList(intersectables));
    }


    /**
     * override findGeoIntersections method
     * @param ray
     * @param maxDis
     * @return function that gets a ray and checks the Intersections of the ray with the shape or shapes
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray,double maxDis) {
        List<GeoPoint> result = null;

        for (Intersectable item : _intersectables) {
            List<GeoPoint> itemIntersectionsPoints = item.findGeoIntersections(ray,maxDis);
            if (itemIntersectionsPoints != null) {
                if (result == null) {
                    result = new LinkedList<>();
                }
                result.addAll(itemIntersectionsPoints);
            }
        }
        return result;
    }
}