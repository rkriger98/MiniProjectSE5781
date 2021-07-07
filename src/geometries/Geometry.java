package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 * Geometry interface for Geometries package
 */
public abstract class Geometry implements Intersectable{

    /**
     * Emission - Geometry Color
     * Material - Geometry material
     */
    protected Color _emission = Color.BLACK;
    private Material  _material=new Material();

    /**
     * function that gets a point on a shape surface and returns the normal from that point
     * assumes the point is on the shape surface (no input check)
     *
     * @param point3D on a shape
     * @return the normal vector from that point
     */
    public abstract Vector getNormal(Point3D point3D);

    /**
     * build func
     * sets the Emission - Geometry Color
     * @param emission
     * @return
     */
    public Geometry setEmission(Color emission) {
        _emission = emission;
        return this;
    }

    /**
     * build func
     * sets the geometry material
     * @param material
     * @return
     */
    public Geometry setMaterial(Material material) {
        _material = material;
        return this;
    }

    /**
     * getter for the geometry color
     *
     * @return emission color - geometry color
     */
    public Color getEmission() {
        return _emission;
    }

    /**
     * getter for the geometry Material
     * @return geometry Material
     */
    public Material getMaterial() {
        return _material;
    }
}