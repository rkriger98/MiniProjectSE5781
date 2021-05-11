package elements;

import primitives.Point3D;
import primitives.Vector;
import primitives.Ray;

import static primitives.Util.isZero;

public class Camera {
    private Point3D _p0;
    private Vector _vUp;
    private Vector _vTo;

    private Vector _vRight;
    private double _width;
    private double _height;
    private double _distance;


    /**
     *
     * @param p0 location of the camera
     * @param vUp y
     * @param vTo z
     */
    public Camera(Point3D p0, Vector vTo,  Vector vUp) {
        _p0 = p0;
        _vUp = vUp.normalized();
        _vTo = vTo.normalized();
        if(!isZero(vUp.dotProduct(vTo))){
            throw new IllegalArgumentException("Vectors are not ortogonal.");
        }
        _vRight=_vTo.crossProduct(_vUp);
    }

    /**
     * getters
     * @return vUp, vTo, vRight
     */
    public Vector getvUp() {
        return _vUp;
    }

    public Vector getvTo() {
        return _vTo;
    }

    public Vector getvRight() {
        return _vRight;
    }


    /**
     * borrowing from Builder pattern
     * @param width
     * @param height
     * @return
     */
    public Camera setViewPlaneSize(double width, double height){
        _width=width;
        _height=height;
        return this;
    }

    public Camera setDistance(double distance){
        _distance=distance;
        return this;
    }


    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        Point3D Pc = _p0.add(_vTo.scale(_distance));

        double Rx = _width / nX;
        double Ry = _height / nY;

        Point3D Pij = Pc;

        double Xj = (j - (nX - 1) / 2d) * Rx;
        double Yi = -(i - (nY - 1) / 2d) * Ry;

        if (isZero(Xj) && isZero(Yi)) {
            return new Ray(_p0, Pij.subtract(_p0));
        }
        if (isZero(Xj)) {
            Pij = Pij.add(_vUp.scale(Yi));
            return new Ray(_p0, Pij.subtract(_p0));
        }
        if (isZero(Yi)) {
            Pij = Pij.add(_vRight.scale(Xj));
            return new Ray(_p0, Pij.subtract(_p0));
        }

        Pij = Pij.add(_vRight.scale(Xj).add(_vUp.scale(Yi)));
        return new Ray(_p0, Pij.subtract(_p0));
    }
}
