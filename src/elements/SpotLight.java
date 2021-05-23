package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class SpotLight extends PointLight implements LightSource{

    private Vector _direction;

    /**
     * c-tor
     *
     * @param intensity
     */
    public SpotLight(Color intensity, Point3D position, double kC, double kL, double kQ, Vector direction) {
        super(intensity, position, kC, kL, kQ);
        _direction = direction;
    }

    public SpotLight(Color intensity, Point3D position, Vector direction) {
        super(intensity, position);
        _direction = direction;
    }

    @Override
    public Color getIntensity(Point3D p){
        Color tempColor = super.getIntensity(p);
        Vector l =getL(p);
        double factor = (l == null ? 0 : Math.max(l.dotProduct(_direction),0));
        return tempColor.scale(factor);
    }

    @Override
    public Vector getL(Point3D p){
        return super.getL(p).normalized();
    }
}
