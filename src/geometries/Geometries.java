package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Composite class for all geometries
 */
public class Geometries implements Intersectable {
    List<Intersectable> _intersectables = null;



    public Geometries() {
        _intersectables = new LinkedList<>();
    }

    public Geometries(Intersectable... intersectables) {
        _intersectables = new LinkedList<>();
        add(intersectables);
    }

    public void add(Intersectable... intersectables) {
//        for (Intersectable item: intersectables) {
//              _intersectables.add(item);
//        }
        if (_intersectables == null) {
            _intersectables = new LinkedList<>();
        }
        _intersectables.addAll(Arrays.asList(intersectables));
    }


    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        List<GeoPoint> result = null;

        for (Intersectable item : _intersectables) {
            List<GeoPoint> itemIntersectionsPoints = item.findGeoIntersections(ray);
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