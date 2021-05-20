package renderer;

import geometries.Intersectable.GeoPoint;
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
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(ray);
        if (intersections != null) {
            GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
            return calcColor(closestPoint);
        }
        return _scene.background;
    }

    /**
     * returns the color
     * @param point
     * @return
     */
    private Color calcColor(GeoPoint point) {
        return _scene.ambientLight.getIntensity()
                .add(point.geometry.getEmission());
                //.add(calcLocalEffects(point, ray));
    }
}
