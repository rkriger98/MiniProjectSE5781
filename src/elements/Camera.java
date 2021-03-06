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
     * @param vUp The opposite axis to the y-axis
     * @param vTo The axis exiting the camera towards the scene
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
     * @return vUp, vTo, vRight, Aperture, NumOfRays, FocalDistance,width,height,_distance
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
     * @param width of the viewPlane
     * @param height of the viewPlane
     * @return Camera
     */
    public Camera setViewPlaneSize(double width, double height){
        _width=width;
        _height=height;
        return this;
    }

    /**
     *
     * @param distance from the viewPlane
     * @return Camera
     */
    public Camera setDistance(double distance){
        _distance=distance;
        return this;
    }

    /**
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
     * @param nX-number of cells left to right
     * @param nY-number of cells up to down
     * @param j-index of width cell
     * @param i-index of height cell
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


    /**
     * builds a beam of Rays from the area of a pixel through a specific point on the focal plane
     *
     * @param nX             - number of cells left to right
     * @param nY             - number of cells up to down
     * @param j              - index of width cell
     * @param i              - index of height cell
     * @return - a list of rays that contains the beam of rays
     */
    public List<Ray> constructRaysThroughPixel(int nX, int nY, int j, int i) {
        Ray ray = constructRayThroughPixel(nX, nY, j, i);
        Point3D pij = ray.getPoint(_distance / (_vTo.dotProduct(ray.getDir())));
        Point3D f = ray.getPoint((_focalDistance + _distance) / (_vTo.dotProduct(ray.getDir())));//focal point
        List<Ray> result = rayRandomBeam(pij, f, _aperture, _numOfRays, _vRight, _vUp);
        result.add(new Ray(pij, ray.getDir()));
        return result;
    }

}