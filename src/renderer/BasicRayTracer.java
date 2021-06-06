package renderer;

import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

public class BasicRayTracer extends BaseRayTracer {


    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final double INITIAL_K = 1.0;


    public BasicRayTracer(Scene scene) {
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
            return calcColor(closestPoint,ray)
                    .add(_scene.ambientLight.getIntensity()
                    .add(closestPoint.geometry.getEmission()));
        }
        return _scene.background;
    }

    /**
     * Fill environmental lighting color of the scene
     * returns the color
     * @param
     * @return
     */
    private Color calcColor(GeoPoint closestPoint, Ray ray) {
        return calcColor(closestPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(_scene.ambientLight.getIntensity());
    }


    /**
     *
     * @param intersection
     * @param ray
     * @param level
     * @param k
     * @return
     */

    private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
        Color color = intersection.geometry.getEmission();
        color = color.add(calcLocalEffects(intersection, ray, k));
        return 1 == level ? color : color.add(calcGlobalEffects(intersection,ray.getDir(),level,k));
    }


    /**
     *TODO
     * @param
     * @param ray
     * @return
     */
    private Color calcLocalEffects(GeoPoint geopoint, Ray ray, double k) {
        Vector v = ray.getDir ();
        Vector n = geopoint.geometry.getNormal(geopoint.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) {
            return Color.BLACK;
        }
        Material material = geopoint.geometry.getMaterial();
        int nShininess = material.nShininess;
        double kd = material.kD;
        double ks = material.kS;
        Color color = Color.BLACK;
        for (LightSource lightSource : _scene.lights) {
            Vector l = lightSource.getL(geopoint.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                double ktr = transparency(lightSource, l, n, geopoint);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(geopoint.point).scale(ktr);
                    color = color.add(calcDiffusive(material.kD, l, n, lightIntensity),
                            calcSpecular(material.kS, l, n, v, nShininess, lightIntensity));
                }

               /* if (unshaded2(lightSource,l,n,geopoint)){
                    Color lightIntensity = lightSource.getIntensity(geopoint.point);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }*/
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

    /**
     *
     *  במקום מכיוון התאורה לגוף גוף לכיווון התאורה
     * @param l
     * @param n
     * @param geopoint
     * @return
     */
    private boolean unshaded1(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source

        Ray lightRay = new Ray(geopoint.point,lightDirection,n);
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(lightRay);//בודק אם מישהו חותך אותו סימן שאני לא מוצל
        if(intersections == null){
            return true;
        }
        double lightDistance = light.getDistance(geopoint.point);
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geopoint.point)-lightDistance) <= 0)
                return false;
        }
        return true;

        //return intersections == null;
    }

    /**
     *
     * @param light
     * @param l
     * @param n
     * @param geopoint
     * @return
     */
    private boolean unshaded2(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
//        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : - DELTA);
//        Point3D point = geopoint.point.add(delta);
        Ray lightRay = new Ray(geopoint.point,lightDirection,n);

        double dis=light.getDistance(geopoint.point);

        List<GeoPoint> intersections = _scene.geometries
                .findGeoIntersections(lightRay, dis);
        return intersections == null;
    }



    private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, double k) {
        Color color = Color.BLACK; Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        double kkr = k * material.kR;
        if (kkr > MIN_CALC_COLOR_K)
            color = calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.kR, kkr);
        double kkt = k * material.kT;
        if (kkt > MIN_CALC_COLOR_K)
            color = color.add(
                    calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.kT, kkt));
        return color;
    }

    /**
     *
     * @param point
     * @param v
     * @param n
     * @return
     */
    private Ray constructRefractedRay(Point3D point, Vector v, Vector n) {
        return new Ray(point,v,n);
    }

    /**
     * Calculation of a reflected ray
     * 𝒓 = 𝒗 − 𝟐 ∙ (𝒗 ∙ 𝒏 )∙𝒏
     * @param point
     * @param v
     * @param n
     * @return
     */
    private Ray constructReflectedRay(Point3D point, Vector v, Vector n) {

        Vector r=v.subtract(n.scale(2*v.dotProduct(n)));
        return new Ray(point,r);

    }

    private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {
        GeoPoint gp = findClosestIntersection (ray);
        return (gp == null ? _scene.background : calcColor(gp, ray, level-1, kkx)
        ).scale(kx);
    }

    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> pointList=_scene.geometries.findGeoIntersections(ray);
        return ray.findClosestGeoPoint(pointList);
    }

    /**
     *
     * @param light
     * @param l
     * @param n
     * @param geopoint
     * @return
     */
    private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.point, lightDirection, n);
        double lightDistance = light.getDistance(geopoint.point);
        var intersections = _scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) return 1.0;
        double ktr = 1.0;
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geopoint.point)-lightDistance) <= 0) {
                ktr *= gp.geometry.getMaterial().kT;
                if (ktr < MIN_CALC_COLOR_K) return 0.0;
            }
        }
        return ktr;
    }


}