package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Tube implements Geometry  {

    Ray axisRay;
    double radius;

    @Override
    public Vector getNormal(Point3D point3D) {
        return null;
    }
}
