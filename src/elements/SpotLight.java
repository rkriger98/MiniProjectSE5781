package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import static primitives.Util.isZero;

public class SpotLight extends PointLight{
    private final Vector _direction;
    /**
     * constructor
     *  @param intensity
     * @param position
     * @param direction
     */
    public SpotLight(Color intensity, Point3D position, Vector direction) {
        super(intensity, position);
        _direction = direction.normalized();
    }

    /**
     *  The get function to get the point's color
     * @param p
     * @return color
     */
    @Override
    public Color getIntensity(Point3D p) {
       double cosTetha=_direction.dotProduct(getL(p).normalized());
        Color intensity=super.getIntensity(p);
        return intensity.scale(Math.max(0,cosTetha));

    }
}
