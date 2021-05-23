package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class PointLight extends Light implements LightSource {

    private Point3D _position;
    private double _kC = 1d, _kL = 0d, _kQ = 0d;

    /**
     * parameter c-tor
     *
     * @param intensity
     * @param position
     * @param kC
     * @param kL
     * @param kQ
     */
    public PointLight(Color intensity, Point3D position, double kC , double kL, double kQ) {
        super(intensity);
        _position = new Point3D(position);
        _kC = kC;
        _kL = kL;
        _kQ = kQ;
    }

    public PointLight(Color intensity, Point3D position) {
        super(intensity);
        _position = position;
    }

    /**
     * @param p
     * @return intensity
     */
    @Override
    public Color getIntensity(Point3D p) {
        double factor = _kC;
        double distance;
        try {
            distance = _position.distance(p);
            factor += _kL * distance + _kQ * distance * distance;
        } catch (Exception exception) {}
        return _intensity.scale(1 / factor);
    }

    /**
     * get override func
     *
     * @param p
     * @return the direction of the lighting
     */
    @Override
    public Vector getL(Point3D p) {
//        if (p.equals(_position)) {
//            return null;
//        }
//        return p.subtract(_position).normalize();
        try {
            return _position.subtract(p).normalized();//p.subtract(_position).normalized();
        }
        catch (Exception exception){
            return null;
        }
    }

    /**
     * Build function setters
     *
     * @param kC
     * @return
     */
    public PointLight setkC(double kC) {
        _kC = kC;
        return this;
    }

    public PointLight setkL(double kL) {
        _kL = kL;
        return this;
    }

    public PointLight setkQ(double kQ) {
        _kQ = kQ;
        return this;
    }
}
