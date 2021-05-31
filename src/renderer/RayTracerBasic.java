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
     * Fill  environmental lighting color of the scene
     * returns the color
     * @param
     * @return
     */
    private Color calcColor(GeoPoint goPoint,Ray ray) {

        return _scene.ambientLight.getIntensity()
                .add(goPoint.geometry.getEmission())
// add calculated light contribution from all light sources)
                .add(calcLocalEffects(goPoint, ray));
    }


    /**
     *TODO
     * @param
     * @param ray
     * @return
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray) {
        Vector v = ray.getDir ();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return Color.BLACK;
        Material material = geoPoint.geometry.getMaterial();
        int nShininess = material.nShininess;
        double kd = material.kD;
        double ks = material.kS;
        Color color = Color.BLACK;
        for (LightSource lightSource : _scene.lights) {
            Vector l = lightSource.getL(geoPoint.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color lightIntensity = lightSource.getIntensity(geoPoint.point);
                color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity));
            }
        }
        return color;
    }


    /**
     * The Phong Reflectance Model
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
     * The Phong Reflectance Model
     * @param kd
     * @param l
     * @param n
     * @param lightIntensity
     * @return
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity)
    {
        double ln=Math.abs(l.dotProduct(n));
        return lightIntensity.scale(kd*ln);
    }

}