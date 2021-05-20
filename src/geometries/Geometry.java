package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Geometry interface for Geometries package
 */

public abstract class Geometry implements Intersectable{
    protected Color _emission = Color.BLACK;
    abstract Vector getNormal(Point3D point3D);

    /**
     * todo
     * @param emission
     * @return
     */
    public Geometry setEmission(Color emission) {
        _emission = emission;
        return this;
    }

    /**
     * todo
     * @return
     */
    public Color getEmission() {
        return _emission;
    }
}

