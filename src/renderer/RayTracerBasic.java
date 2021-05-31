package renderer;

import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

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
            return calcColor(closestPoint,ray);
        }
        return _scene.background;
    }

    /**
     * returns the color
     * @param point
     * @return
     */
    private Color calcColor(GeoPoint point,Ray ray) {

       Color emissionColor=point.geometry.getEmission();
        Color basicColor=_scene.ambientLight.getIntensity().add(emissionColor);
        return basicColor.add(calcLocalEffects(point,ray));

    }


    /**
     *TODO
     * @param intersection
     * @param ray
     * @return
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        Vector v = ray.getDir ();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return Color.BLACK;
        Material material = intersection.geometry.getMaterial();
        int nShininess = material.getShininess();
        double kd = material.getKd(), ks = material.getKs();
        Color color = Color.BLACK;
        for (LightSource lightSource : _scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color lightIntensity = lightSource.getIntensity(intersection.point);
                color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity));
            }
        }
        return color;
    }

    /**
     * TODO
     * @param ks
     * @param l
     * @param n
     * @param v
     * @param nShininess
     * @param lightIntensity
     * @return
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v,  double nShininess,  Color lightIntensity)
    {
        Vector r=l.subtract(n.scale(l.dotProduct(n)*2));
        double vrMinus=v.scale(-1).dotProduct(r);
        double vrn=Math.pow(vrMinus,nShininess);
        return lightIntensity.scale(ks*vrn);



    }

    /**
     * TODO
     * @return
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity)
    {
        double lN=Math.abs(l.dotProduct(n));
        return lightIntensity.scale(kd*lN);
    }

}