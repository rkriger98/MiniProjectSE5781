package elements;

import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.BasicRayTracer;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

public class DepthOfFieldTests {
    /**
     * Produce a picture of a row of spheres lighted by a spot light.
     * with Depth of filed with focal distance of 10, aperture 0f 1 and than 0.5, and 15 rays in the beam
     */
    @Test
    public void depth10() {
        Scene scene = new Scene("Test scene");
        Camera camera = new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)) //
                .setViewPlaneSize(30, 30).setDistance(1000);
        camera.setDistance(1000);
        scene.setBackground(new Color(java.awt.Color.BLACK));
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));
        int x = 0, y = 10, z = 0;
        for (int i = 0; i < 7; i++) {
            scene.geometries.add(
                    new Sphere(2, new Point3D(x, y - 2 * i, z + i * 5)) //
                            .setEmission(new Color(java.awt.Color.MAGENTA)) //
                            .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(1)), //
                    new Sphere(2, new Point3D(x + 3, y - 2 * i, z + i * 5)) //
                            .setEmission(new Color(java.awt.Color.BLUE)) //
                            .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(1)), //
                    new Sphere(2, new Point3D(x - 3, y - 2 * i, z + i * 5)) //
                            .setEmission(new Color(java.awt.Color.GREEN)) //

                            .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(1)));


        }

        scene.lights.add(
                new SpotLight(new Color(java.awt.Color.WHITE), new Point3D(-100, 100, -500), new Vector(-1, 10, 2))
                .setKl(0.0004).setKq(0.0000006)
        );

        camera.setDepthOfFiled(1, 0.7, 50);
        Render render = new Render(). //
                setImageWriter(new ImageWriter("DepthOfField", 500, 500)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage2();
        render.writeToImage();
    }

    @Test
    public void sphereTriangleInitial4() {
        scene.geometries.add( //
                new Sphere(60, new Point3D(0, 0, -200)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), //
                new Triangle(new Point3D(-70, -40, 0), new Point3D(-40, -70, 0), new Point3D(-68, -68, -4)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
        );
        scene.lights.add( //
                new SpotLight(new Color(400, 240, 0), new Point3D(-85, -85, 110), new Vector(1, 1, -3)) //
                        .setKl(1E-5).setKq(1.5E-7));

        Render render = new Render(). //
                setImageWriter(new ImageWriter("depth1", 400, 400)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }
}