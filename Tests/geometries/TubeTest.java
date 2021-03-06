package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {

    @Test
    void getNormal() {
        Tube tb = new Tube(1, new Ray(new Point3D(1,0,0),new Vector(-1,0,0)));
        Vector v = tb.getNormal(new Point3D(0,1,0));
        assertEquals(new Vector(0,1,0), v, "ERROR, function does not work correctly");
    }
}