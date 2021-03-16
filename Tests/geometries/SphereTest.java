package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    /**
     * test checks if get normal works correctly
     */
    @Test
    void getNormal() {
        Sphere sp = new Sphere(3, new Point3D(1,2,3));
        Vector v = sp.getNormal(new Point3D(1,1,3));
        assertEquals(new Vector(0,-1,0), v, "ERROR, function does not work correctly");
    }
}