package elements;

import primitives.Coordinate;
import primitives.Point3D;
import primitives.Vector;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

import static primitives.Ray.rayRandomBeam;
import static primitives.Util.isZero;

public class Camera {
    private Point3D _p0;
    private Vector _vUp;
    private Vector _vTo;

    private Vector _vRight;
    private double _width;
    private double _height;
    private double _distance;

    /*
     *  focalDistance - the distance of the  focus.
     *  aperture      - the radius of the aperture.
     *  numOfRays     - number of rays that will be in the beam from every pixels area (in addition to the original ray).
     */
    private double _focalDistance;
    private double _aperture;
    private int _numOfRays;


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
     * @return vUp, vTo, vRight, Aperture, NumOfRays, FocalDistance
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

    public double getAperture() {
        return _aperture;
    }

    public int getNumOfRays() {
        return _numOfRays;
    }

    public double getFocalDistance() {
        return _focalDistance;
    }

    public double getWidth() {
        return _width;
    }

    public double getHeight() {
        return _height;
    }

    public double getDistance() {
        return _distance;
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

    /**
     * setter of Depth of filed. if Depth of filed function is called the camera will be focused for a specific distance.
     * if Depth of filed will not be called the camera will be focused on the whole scene equally.
     *
     * @param focalDistance - the distance of the  focus.
     * @param aperture      - the radius of the aperture.
     * @param numOfRays     - number of rays that will be in the beam from every pixels area (in addition to the original ray).
     */
    public Camera setDepthOfFiled(double focalDistance, double aperture, int numOfRays) {
        _focalDistance = focalDistance;
        _aperture = aperture;
        _numOfRays = numOfRays;
        return this;
    }

    /**
     * Constructing a ray through a pixel
     * @param nX
     * @param nY
     * @param j
     * @param i
     * @return the ray
     */
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