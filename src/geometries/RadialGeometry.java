package geometries;

/**
 * //TODO Racheli will  put comments here
 */
public abstract class RadialGeometry {
    final protected double _radius;

    public RadialGeometry(double radius) {
        _radius = radius;
    }

    public double getRadius() {
        return _radius;
    }
}
