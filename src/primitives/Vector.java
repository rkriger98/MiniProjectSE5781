package primitives;

import java.util.Objects;

import  static primitives.Point3D.PointZERO;

public class Vector {
    Point3D _head;

    /**
     * primary used c-tor
     * @param head point3d
     */
    public Vector(Point3D head) {

        if (head.equals(PointZERO)) {
            throw new IllegalArgumentException("head cannot be Point(0,0,0)");
        }
        _head = head;
    }

    /**
     * c-tor for x, y, z coordinates
     * @param x coordinate for x axis
     * @param y coordinate for y axis
     * @param z coordinate for z axis
     */
    public Vector(double x, double y, double z) {
        this(new Point3D( x,y,z));
    }

    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        this(new Point3D(x,y,z));
    }

    //getter
    public Point3D getHead() {
        return new Point3D(_head._x, _head._y, _head._z);
    }

    /**
     *
     * @param vector
     * @return sum of two vectors
     */
    public Vector add(Vector vector) {
        return new Vector(
                this._head._x.coord + vector._head._x.coord,
                this._head._y.coord + vector._head._y.coord,
                this._head._z.coord + vector._head._z.coord);
    }

    /**
     *
     * @param vector
     * @return subtract between two vectors
     */
    public Vector subtract(Vector vector) {
        return new Vector(
                this._head._x.coord - vector._head._x.coord,
                this._head._y.coord - vector._head._y.coord,
                this._head._z.coord - vector._head._z.coord);
    }

    /**
     *
     * @param scalar
     * @return double a scalar with a vector
     */
    public Vector scale(double scalar) {
        if(scalar * this._head._x.coord==0&&scalar * this._head._y.coord==0&&scalar * this._head._z.coord==0){
            int wt;
        }
        return new Vector(
                scalar * this._head._x.coord,
                scalar * this._head._y.coord,
                scalar * this._head._z.coord);
    }

    /**
     *
     * @param vector
     * @return dotProduct between two vectors
     */
    public Double dotProduct(Vector vector) {
        return  (vector._head._x.coord * this._head._x.coord+
                vector._head._y.coord * this._head._y.coord+
                vector._head._z.coord * this._head._z.coord);
    }

    /**
     *
     * @param vector
     * @return crossProduct between two vectors
     */
    public Vector crossProduct(Vector vector) {
        return new Vector(
                this._head._y.coord * vector._head._z.coord - this._head._z.coord * vector._head._y.coord,
                this._head._z.coord * vector._head._x.coord - this._head._x.coord * vector._head._z.coord,
                this._head._x.coord * vector._head._y.coord - this._head._y.coord * vector._head._x.coord);
    }

    /**
     *
     * @return length Squared of vector
     */
    public double lengthSquared() {
        return this._head.distanceSquared(PointZERO);
    }

    /**
     *
     * @return length of vector
     */
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    /**
     *
     * @return thw class vector after normal
     */
    public Vector normalize() {
        double length = this.length();
        _head = new Point3D(
                (this._head._x.coord) / length,
                (this._head._y.coord) / length,
                (this._head._z.coord) / length);
        return this;
    }

    /**
     *
     * @return the normal
     */
    public Vector normalized() {
        double length = this.length();
        return new Vector(
                (this._head._x.coord) / length,
                (this._head._y.coord) / length,
                (this._head._z.coord) / length);
    }

    /**
     * override func checks if two vectors are equal
     *
     * @param o
     * @return true if its equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return _head.equals(vector._head);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_head);
    }

}