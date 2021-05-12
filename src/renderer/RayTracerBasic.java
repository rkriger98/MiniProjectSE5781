package renderer;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public class RayTracerBasic extends RayTracerBase {

    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * Returns color to each point
     * @param ray
     * @return
     */
    @Override
    public Color traceRay(Ray ray) {
        List<Point3D> intersections = _scene.geometries.findIntersections(ray);
        if (intersections != null) {
            Point3D closestPoint = ray.getClosestPoint(intersections);
            return calcColor(closestPoint);
        }
        return _scene.background;
    }

    /**
     * returns the color
     * @param point
     * @return
     */
    private Color calcColor(Point3D point) {
        return _scene.ambientLight.getIntensity();
    }
}
