package geometries;

public abstract class RadialGeometry extends Geometry {
    protected final double _radius;

    public RadialGeometry(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException(("radius cannot be <= 0"));
        }
        _radius = radius;
    }

    /**
     * @return getRadius double
     */
    public double getRadius() {
        return _radius;
    }
}