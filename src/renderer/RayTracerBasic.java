package renderer;

import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

public class RayTracerBasic extends RayTracerBase {

    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * Returns color to each point
     *
     * @param ray
     * @return
     */
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = _scene._geometries.findGeoIntersections(ray);
        if (intersections != null) {
            GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
            return calcColor(closestPoint);
        }
        return _scene._background;
    }

    /**
     * returns the color
     *
     * @param point
     * @return
     */
    private Color calcColor(GeoPoint point) {
        return _scene._ambientLight.getIntensity()
                .add(point.geometry.getEmission());
                //.add(calcLocalEffects(point, ray));
    }

    private Color calcLocalEffects(GeoPoint point, Ray ray) {
        Vector v = ray.getDir();
        Vector n = point.geometry.getNormal(point.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) {
            return Color.BLACK;
        }
        double nShininess = point.geometry.getMaterial()._nShininess;
        double kD = point.geometry.getMaterial()._kD;
        double kS = point.geometry.getMaterial()._kS;
        Color color = Color.BLACK;
        for (LightSource lightSource : _scene._lights) {
            Vector l = lightSource.getL(point.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) {
                Color lightIntensity = lightSource.getIntensity(point.point);
                color = color.add(calcDiffusive(kD, l, n, lightIntensity),
                        calcSpecular(kS, l, n, v, nShininess, lightIntensity));
            }
        }
        return color;
    }

    /**
     *
     * @param kD
     * @param l
     * @param n
     * @param lightIntensity
     * @return
     */
    private Color calcDiffusive(double kD, Vector l, Vector n, Color lightIntensity) {
        double lN;
        try {
            lN = l.normalized().dotProduct(n.normalized());
        } catch (Exception exception) {
            return lightIntensity.scale(kD);
        }
        return lightIntensity.scale(Math.abs(lN) * kD);
    }

    /**
     * calculates the specular color
     * @param kS
     * @param l
     * @param n
     * @param v
     * @param nShininess
     * @param lightIntensity
     * @return
     */
    private Color calcSpecular(double kS, Vector l, Vector n, Vector v, double nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(l.dotProduct(n) * 2));
        double vR;
        try {
            vR = v.scale(-1).normalized().dotProduct(r.normalized());
        } catch (Exception exception) {
            return lightIntensity.scale(0);
        }
        return lightIntensity.scale(kS * Math.pow(Math.max(0, vR), nShininess));
    }
}
