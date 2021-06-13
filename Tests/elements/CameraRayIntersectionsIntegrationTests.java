package elements;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CameraRayIntersectionsIntegrationTests {

    Camera cam1 = new Camera(Point3D.PointZERO, new Vector(0, 0, -1), new Vector(0, -1, 0));
    Camera cam2 = new Camera(new Point3D(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, -1, 0));

    /**
     * Integration function between creating rays from a camera and  geometric bodies + test
     * @param camera
     * @param intersectable
     * @param expected
     */
    void assertCountIntersections(Camera camera, Intersectable intersectable, int expected) {

        int x = 3;
        int y = 3;
        int count = 0;
        List<Point3D> points = new LinkedList<>();

        camera.setViewPlaneSize(3, 3);
        camera.setDistance(1);

        for (int i = 0; i < x; ++i) {
            for (int j = 0; j < y; ++j) {
                List<Point3D> list = intersectable.findIntersections(camera.constructRayThroughPixel(x, y, j, i,camera.getDistance(),camera.getWidth(),camera.getFocalDistance()));
                if (list != null) {
                    points.addAll(list);
                }
            }
            count += points.size();
        }

        if ((points.size() != expected) && (points != null)) {
            assertEquals (expected, count, "Incorrect number of points");
        }
    }


    @Test
    public void cameraRaySphereIntegration() {

        // TC01: Small Sphere 2 points
        assertCountIntersections(cam1, new Sphere(1, new Point3D(0, 0, -3)), 2);

        // TC02: Big Sphere 18 points
        assertCountIntersections(cam2, new Sphere(2.5, new Point3D(0, 0, -2.5)), 18);

        // TC03: Medium Sphere 10 points
        assertCountIntersections(cam2, new Sphere(2, new Point3D(0, 0, -2)), 10);

        // TC04: Big Sphere 9 points
        assertCountIntersections(cam1, new Sphere(4, new Point3D(0, 0, -2)), 9);

        // TC05: Sphere Behind view plan 0 Points
        assertCountIntersections(cam1, new Sphere(0.5, new Point3D(0, 0, 1)), 0);
    }

    @Test
    public void cameraRayPlaneIntegration() {

        // TC0: The plane is paralleled to the view plane 9 point
        assertCountIntersections(cam1, new Plane(new Point3D(0, 0, -10), new Vector(0, 0, 1)), 9);

        // TC1: The plane is tilted slightly downwards 9 point
        assertCountIntersections(cam1, new Plane(new Point3D(0, 0, -10), new Vector(0, 1, 3)), 9);

        // TC2: The plane is tilted downwards 6 point
        assertCountIntersections(cam1, new Plane(new Point3D(0, 0, -10), new Vector(0, 5, 1)), 6);

    }

    @Test
    public void cameraRayTriangleIntegration() {

        // TC0: Small Triangle 1 point
        assertCountIntersections(cam1, new Triangle(new Point3D(0, 1, -2), new Point3D(1, -1, -2), new Point3D(-1, -1, -2)), 1);

        // TC1: Big Triangle 2 point
        assertCountIntersections(cam1, new Triangle(new Point3D(1, -1, -2), new Point3D(-1, -1, -2), new Point3D(0, 20, -2)), 2);
    }
}
