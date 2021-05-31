package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 *
 */
public class DirectionalLight extends Light implements LightSource {
   private final Vector _dir;

    /**
     * c-tor
     *
     * @param intensity
     */
    public DirectionalLight(Color intensity,Vector dir) {
        super(intensity);
        _dir=dir.normalized();
    }

    /**
     *
     * @param p
     * @return color
     */
    @Override
    public Color getIntensity(Point3D p) {
        return  _intensity;
    }

    /**
     *
     * @param p
     * @return direction of the light
     */
    @Override
    public Vector getL(Point3D p) {
        return _dir;
    }
}




