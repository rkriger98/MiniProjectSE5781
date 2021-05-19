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
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> result = null;

        for (Intersectable item : _intersectables) {
            List<Point3D> itemIntesectionPoints = item.findIntersections(ray);
            if (itemIntesectionPoints != null) {
                if (result == null) {
                    result = new LinkedList<>();
                }
                result.addAll(itemIntesectionPoints);
            }
        }

        return result;
    }
}
