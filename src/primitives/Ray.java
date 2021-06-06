package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.isZero;



public class Ray {
    private static final double DELTA = 0.1;

    private final Point3D _p0;
    private final Vector _dir;



    /**
     * c-tor
     *
     * @param p0  point3d
     * @param dir vector
     */
    public Ray(Point3D p0, Vector dir) {
        _p0 = p0;
        _dir = dir.normalized();
    }

    public Ray(Point3D point, Vector dir, Vector n) {
        Vector delta = n.scale(n.dotProduct(dir) > 0 ? DELTA : -DELTA);
        Point3D p = point.add(delta);
        _p0 = p;
        _dir = dir.normalized();

    }



    /**
     * @return point po
     */
    public Point3D getP0() {
        return _p0;
    }

    /**
     * @return vector Direction
     */
    public Vector getDir() {
        return _dir;
    }

    /**
     * @param delta
     * @return point po * scale
     */
    public Point3D getPoint(double delta) {
        if (isZero(delta)) {
            return _p0;
        }
        return _p0.add(_dir.scale(delta));
    }

    public GeoPoint findClosestGeoPoint(List<GeoPoint> points) {
        GeoPoint minPoint = null;
        if (points == null) {
            return null;
        }
        double distance = Double.POSITIVE_INFINITY;
        for (GeoPoint geo : points) {
            double temp = geo.point.distance(_p0);
            if (temp < distance) {
                distance = temp;
                minPoint = geo;
            }
        }
        return minPoint;
    }

    /**
     * @param points
     * @return the closest point to _p0
     */
    public Point3D findClosestPoint(List<Point3D> points) {
        Point3D minPoint = null;
        if (points == null) {
            return null;
        }
        double distance = Double.POSITIVE_INFINITY;
        for (Point3D p : points) {
            double temp = p.distance(_p0);
            if (temp < distance) {
                distance = temp;
                minPoint = p;
            }
        }
        return minPoint;
    }

    /**
     * @param o
     * @return true if its equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return _p0.equals(ray._p0) && _dir.equals(ray._dir);
    }

    /**
     * @return ray toString
     */
    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + _p0 +
                ", dir=" + _dir +
                '}';
    }
}