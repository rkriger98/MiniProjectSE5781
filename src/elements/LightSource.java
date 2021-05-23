package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * interface LightSource
 */
public interface LightSource {



    /**
     * getter
     * @param p
     * @return color
     */
    public Color getIntensity(Point3D p);

    /**
     * getter
     * @param p
     * @return vector
     */
    public Vector getL(Point3D p);

}
