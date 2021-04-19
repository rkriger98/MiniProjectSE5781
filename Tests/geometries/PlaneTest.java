package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    /**
     * test that checks if th constructor finds the correct normal
     */
    @Test
    void testConstructor() {
        Point3D p1 = new Point3D(0, 1, 1);
        Point3D p2 = new Point3D(-2, 1, 0);
        Point3D p3 = new Point3D(5, 0, 2);

        Plane pl = new Plane(p1, p2, p3);
        Vector v = pl.getNormal();
        assertEquals(new Vector(-1 / Math.sqrt(14), -3 / Math.sqrt(14), 2 / Math.sqrt(14)), v, "ERROR, constructor does not work correctly");
    }

    @Test
    void findIntersections() {
        Plane plane = new Plane(new Point3D(1, 0, 0), new Vector (0, 0, 1));
        List<Point3D> result = plane.findIntersections(
                new Ray(new Point3D(-2, 0.5, -1),
                        new Vector(1, 1, 1)));
            // ============ Equivalence Partitions Tests ==============
            // TC01:Ray intersects the plane (1 point)

            assertEquals( 1,result.size(),"Wrong number of points");
            assertEquals(List.of(new Point3D(-1, 1.5, 0)), result,"Ray intersects the plane");

            // TC02:Ray does not intersect the plane (0 points)
            assertEquals(null, plane.findIntersections(
                    new Ray(new Point3D(3, 1, 0.5),
                            new Vector(1, 1, 1))),"Ray not intersects the plane");

            // =============== Boundary Values Tests ==================

            // ****Group: Ray is parallel to the plane
            // TC03: Ray included in the plane (0 points)
            assertEquals(null, plane.findIntersections(new Ray(
                            new Point3D(3, 1, 0),
                            new Vector(1, 1, 0))),"Ray in the plane");

            // TC04: Ray not included in the plane (0 points)
            assertEquals(null, plane.findIntersections(
                    new Ray(new Point3D(3, 1, 0.5),
                            new Vector(1, 1, 0))),"Ray not included in the plane");

            // ****Group: Ray is orthogonal to the plane
            //TC05: Ray starts before the plane (1 ptions)
            result = plane.findIntersections(new Ray(new Point3D(-4, 3, -2), new Vector(0, 0, 1)));
            assertEquals(1, result.size(),"Wrong number of points");
            assertEquals(List.of(new Point3D(-4, 3, 0)), result,"Ray is orthogonal to the plane and starts before the plane");

            //TC06: Ray starts in the plane (0 ptions)
            assertEquals(null, plane.findIntersections(
                    new Ray(new Point3D(3, 1, 0),
                            new Vector(0, 0, 1))),"Ray is orthogonal to the plane and starts in the plane");

            //TC07: Ray starts after the plane (0 ptions)
            assertEquals(null, plane.findIntersections(
                    new Ray(new Point3D(3, 1, 1),
                            new Vector(0, 0, 1))),"Ray is orthogonal to the plane and starts after the plane");

            // ****Group: Special cases
            //TC08:Ray is neither orthogonal nor parallel to and begins at the plane (0 options)
            assertEquals(null, plane.findIntersections(
                    new Ray(new Point3D(3, 1, 0),
                            new Vector(0, 1, 1))),"Ray begins at the plane");
            //TC09:
            assertEquals(null, plane.findIntersections(
                    new Ray(new Point3D(1, 0, 0),
                            new Vector(0, 1, 2))),"Ray begins at the plane");




    }
}