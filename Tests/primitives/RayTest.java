package primitives;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {
    Ray ray = new Ray(Point3D.PointZERO,new Vector(0,0,1));

    @Test
    void getClosestPointEP() {
        List<Point3D> point3DS =new LinkedList<>();

        point3DS.add(new Point3D(-1100,70,100));
        point3DS.add(new Point3D(23,60,1000));
        point3DS.add(new Point3D(0,5,1));
        point3DS.add(new Point3D(-20,60,50));
        point3DS.add(new Point3D(0,0,-100));

        assertEquals(point3DS.get(2),ray.findClosestPoint(point3DS),"supposed to be 2");
    }

    @Test
    void getClosestPointBVA1() {
        assertNull(ray.findClosestPoint(null),"supposed to be null");
    }

    @Test
    void getClosestPointBVA2() {
        List<Point3D> point3DS =new LinkedList<>();

        point3DS.add(new Point3D(0,5,1));
        point3DS.add(new Point3D(-1100,70,100));
        point3DS.add(new Point3D(23,60,1000));
        point3DS.add(new Point3D(-20,60,50));
        point3DS.add(new Point3D(0,0,-100));

        assertEquals(point3DS.get(0),ray.findClosestPoint(point3DS),"supposed to be 0");
    }

    @Test
    void getClosestPointBVA13() {
        List<Point3D> point3DS =new LinkedList<>();

        point3DS.add(new Point3D(-1100,70,100));
        point3DS.add(new Point3D(23,60,1000));
        point3DS.add(new Point3D(-20,60,50));
        point3DS.add(new Point3D(0,0,-100));
        point3DS.add(new Point3D(0,5,1));

        assertEquals(point3DS.get(4),ray.findClosestPoint(point3DS),"supposed to be 4");
    }
}