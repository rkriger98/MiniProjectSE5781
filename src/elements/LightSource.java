package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * todo
 */
public interface LightSource  {
    /**
     * todo
     * @param p
     * @return
     */
    public Color getIntensity(Point3D p);

    /**
     * todo
     * @param p
     * @return
     */
    public Vector getL(Point3D p);

}
