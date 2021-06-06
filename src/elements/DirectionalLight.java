package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 *class DirectionalLight to apply directional lighting
 */
public class DirectionalLight extends Light implements LightSource{
    final private Vector _direction;

    /**
     * constructor
     * @param intensity
     * @param direction
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        _direction = direction.normalized();
    }

    /**
     * The get function to get the direction of the lighting The get function to get the point's color
     * @param p
     * @return color
     */
    @Override
    public Color getIntensity(Point3D p) {
        return _intensity;
    }

    /**
     * The get function to get the direction of the lighting
     * @param p
     * @return direction
     */
    @Override
    public Vector getL(Point3D p) {
        return _direction.normalized();
    }

    /**
     *
     * @return
     */
    @Override
    public double getDistance(Point3D point) {
        return Double.POSITIVE_INFINITY;
    }
}
