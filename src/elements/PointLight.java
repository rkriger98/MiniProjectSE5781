package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import java.awt.geom.Arc2D;

/**
 *
 */
public class PointLight extends Light implements LightSource {
    private final Point3D _position;
    private double _Kc=1;
    private double _Kl=0;
    private double _Kq=0;
    /**
     * c-tor
     *
     * @param intensity
     */
    protected PointLight(Color intensity, Point3D position, double Kc, double Kl , double Kq) {
        super(intensity);
        _position = new Point3D(position);
        _Kc =_Kc;
        _Kl = Kl;
        _Kq = Kq;
    }


    protected PointLight(Color intensity, Point3D position) {
        super(intensity);
        _position = new Point3D(position);

    }





    /**
     *Models omni-directional point source (such as a bulb)
     * Intensity (I0)
     * Position (PL)
     * Factors (kc,ki,kq) for attenuation with distance (d)
     * @param p
     * @return color
     */
    @Override
    public Color getIntensity(Point3D p) {

        //double dsquared = p.distanceSquared(_position);
        //double d = p.distance(_position);
        double d = _position.distance(p);
        double attenuation=1d/(_Kc+ _Kl* d + _Kq* d*d);
        return _intensity.scale(attenuation);


    }

    /**
     *
     * @param p
     * @return the direction of the light
     */
    @Override
    public Vector getL(Point3D p) {

            return p.subtract(this._position).normalized();
    }


    /**
     *
     */
    public PointLight setKc(double _Kc) {
        this._Kc = _Kc;
        return this;
    }

    public PointLight setKl(double _Kl) {
        this._Kl = _Kl;
        return this;
    }

    public PointLight setKq(double _Kq) {
        this._Kq = _Kq;
        return this;
    }



}
