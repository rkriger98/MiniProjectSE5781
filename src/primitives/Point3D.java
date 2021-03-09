package primitives;

import java.util.Objects;

/**
 * basic geometric object for 3D point
 */
    public class Point3D {
    final Coordinate _x;
    final Coordinate _y;
    final Coordinate _z;

    public static Point3D PointZERO=new Point3D(0,0,0);

    /**
     *
     * @param x coordinatefor x axis
     * @param y coordinatefor y axis
     * @param z coordinatefor z axis
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this (x.coord,y.coord, z.coord);
    }

    public Point3D(double x, double y, double z) {
        _x = new Coordinate(x);
        _y = new Coordinate(y);
        _z = new Coordinate(z);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return _x.equals(point3D._x) && _y.equals(point3D._y) && _z.equals(point3D._z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_x.coord, _y.coord, _z.coord);
    }

    /**
     *
     * @param point3D
     * @return distanceSquared of a point
     */
    public double distanceSquared(Point3D point3D) {
        double x1= _x.coord;
        double y1= _y.coord;
        double z1= _z.coord;
        double x2= point3D._x.coord;
        double y2= point3D._y.coord;
        double z2= point3D._z.coord;

        return ((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1)+(z2-z1)*(z2-z1));
    }

    /**
     *
     * @param point3D
     * @return distance
     */
    public double distance(Point3D point3D){
        return Math.sqrt(distanceSquared(point3D));
    }

    /**
     *
     * @param vector
     * @return the sum of point and a vector
     */
    public Point3D add(Vector vector){
        return new Point3D(
                this._x.coord + vector._head._x.coord,
                this._y.coord + vector._head._y.coord,
                this._z.coord + vector._head._z.coord);
    }

    /**
     *
     * @param point3D
     * @return subtract between point and vector
     */
    public Vector subtract(Point3D point3D){
          if(this.distance(PointZERO) < point3D.distance(PointZERO))
            return new Vector(
                    point3D._x.coord - this._x.coord,
                    point3D._y.coord - this._y.coord,
                    point3D._z.coord - this._z.coord);
        else
            return new Vector(
                    this._x.coord - point3D._x.coord,
                    this._y.coord - point3D._y.coord,
                    this._z.coord - point3D._z.coord);

    }
}
