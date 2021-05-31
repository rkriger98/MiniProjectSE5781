package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 * Geometry interface for Geometries package
 */

public abstract class Geometry implements Intersectable{
    protected Color _emission = new Color(Color.BLACK);
    public abstract Vector getNormal(Point3D point3D);
    private Material  material=new Material();

    /**
     * todo
     * @param emission
     * @return
     */
    public Geometry setEmission(Color emission) {
        _emission = emission;
        return this;
    }

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * todo
     * @return
     */
    public Color getEmission() {
        return _emission;
    }

    public Material getMaterial() {
        return material;
    }
}