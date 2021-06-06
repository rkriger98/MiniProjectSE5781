package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * interface LightSource
 */
public interface LightSource  {
    /**
     * The get function to get the point's color
     * @param p
     * @return color
     */
    public Color getIntensity(Point3D p);

    /**
     * The get function to get the direction's color
     * @param p
     * @return direction
     */
    public Vector getL(Point3D p);

    public double getDistance(Point3D p);

}
