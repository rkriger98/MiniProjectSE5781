package geometries;

/**
 * Radial Geometry class is an abstract class for different radial shapes in 3D Cartesian coordinate system
 */
public abstract class RadialGeometry extends Geometry {
    protected final double _radius;

    /**
     * c-tor for RadialGeometry
     * @param radius
     */
    public RadialGeometry(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException(("radius cannot be <= 0"));
        }
        _radius = radius;
    }

    /**
     * getter for radius
     * @return radius of the Radial Geometry
     */
    public double getRadius() {
        return _radius;
    }
}