package primitives;

import java.util.Objects;

public class Ray {
    Point3D p0;
    Vector dir;

    //constructor
    public Ray(Point3D p0, Vector dir) {
        this.p0 = new Point3D(p0._x, p0._y, p0._z);
        this.dir = new Vector(dir.normalize()._head);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }

    @Override
    public int hashCode() {
        return Objects.hash(p0, dir);
    }
}
