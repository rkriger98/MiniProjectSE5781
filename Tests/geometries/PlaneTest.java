package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

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
}