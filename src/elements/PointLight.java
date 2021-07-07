package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class PointLight - Point Light lighting the objects
 */
public class PointLight extends Light implements LightSource{
    /**
     * kC-The fixed lighting coefficient
     * KL-Linear lighting coefficient
     * KQ-The square light coefficient
     */
    private final Point3D _position;
    private double _Kc =1;
    private double _Kl =0;
    private double _Kq =0;
    /**
     * constructor
     * @param intensity
     * @param position
     */
    public PointLight(Color intensity, Point3D position) {
        super(intensity);
        _position = position;
    }

    /**
     *  The get function to get the point's color
     * @param p
     * @return
     */
    @Override
    public Color getIntensity(Point3D p) {
       double d=_position.distance(p);
        double attenuation=1d/(_Kc + _Kl *d+ _Kq *d*d);
        return _intensity.scale(attenuation);
    }

    /**
     * The get function to get the direction of the lighting
     * @param p
     * @return
     */
    @Override
    public Vector getL(Point3D p) {
        return p.subtract(_position).normalized();
    }

    /**
     *
     * @param p
     * @return distance
     */
    @Override
    public double getDistance(Point3D p) {
       return  _position.distance(p);
    }

    /**
     * builders
     * @param kl and Kq and Kc
     * @return PointLight
     */
    public PointLight setKl(double kl) {
        _Kl = kl;
        return this;
    }

    public PointLight setKq(double kq) {
        _Kq = kq;
        return this;
    }

    public PointLight setKc(double kc) {
        _Kc = kc;
        return this;
    }
}
