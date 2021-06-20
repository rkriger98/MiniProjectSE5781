package elements;

import geometries.*;
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

    @Test
    public void DOP1() {
        Scene scene = new Scene("Test scene");
        Camera camera = new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0))
                .setDistance(1000)
                .setViewPlaneSize(30, 30)
                .setDepthOfFiled(15, 0.3, 200);
        scene.setBackground(new Color(java.awt.Color.BLACK));
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        int x = 0, y = 10, z = 0;
        for (int i = 0; i < 6; i++) {
            scene.geometries.add(
                    new Sphere(2, new Point3D(x, y - 2 * i, z + i * 5))
                            .setEmission(new Color(java.awt.Color.MAGENTA))
                            .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                    new Sphere(2, new Point3D(x + 3, y - 2 * i, z + i * 5))
                            .setEmission(new Color(java.awt.Color.MAGENTA))
                            .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                    new Sphere(2, new Point3D(x - 3, y - 2 * i, z + i * 5))
                            .setEmission(new Color(java.awt.Color.MAGENTA))
                            .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3))
            );
        }

        scene.lights.add(new SpotLight(new Color(1000, 600, 0), new Point3D(-100, 100, -500), new Vector(-1, 1, 2))
                .setKl(0.0004).setKq(0.0000006));

        Render render = new Render()
                .setImageWriter(new ImageWriter("DOP1", 500, 500)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene))
                .setFlagDOP(true);
        render.renderImage();
        render.writeToImage();


    }



    @Test
    public void DOP3() {

        Scene scene = new Scene("Test scene");
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(200, 200).setDistance(1000);
        //.setDepthOfFiled(10, 0.3, 200);

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.geometries.add( //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                new Sphere(30, new Point3D(60, 50, -50)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point3D(60, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));

        ImageWriter imageWriter = new ImageWriter("DOP31", 600, 600);
        Render render = new Render()
                .setFlagDOP(true)
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.writeToImage();
    }


    @Test
    public void New() {
        Scene scene = new Scene("Test scene");
        Camera camera = new Camera(new Point3D(-120, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0))
                .setDistance(1000)
                .setViewPlaneSize(350, 350);
       // .setDepthOfFiled(15, 0.08, 200);
        scene.setBackground(new Color(java.awt.Color.BLACK));
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0));

        scene.geometries.add(

                new Plane(new Point3D(-200, 300, 0), new Vector(0, 5, 0))
                        .setEmission(new Color(100, 100, 100))//
                        .setMaterial(new Material().setKd(1).setKs(1).setShininess(20).setKt(0.8))

        );
        for (int i = 1; i < 7; i++) {
            scene.geometries.add(


                    new Sphere(45, new Point3D(100 + -i * 100, 3, i * 200)) //
                            .setEmission(new Color(20, 0, 0))//
                            .setMaterial(new Material().setKd(1).setKs(1).setShininess(100)), //


                    new Sphere(30, new Point3D(100 + -i * 100, -70, i * 200)) //
                            .setEmission(new Color(0, 20, 0)) //
                            .setMaterial(new Material().setKd(1).setKs(1).setShininess(100)), //

                    new Sphere(15, new Point3D(100 + -i * 100, -115,  i * 200)) //
                            .setEmission(new Color(0, 0, 20)) //
                            .setMaterial(new Material().setKd(1).setKs(1).setShininess(100))//


            );



        }



       scene.lights.add(new DirectionalLight(new Color(50, 50, 50), new Vector(50, 180, 600)));
        Color c=new Color(java.awt.Color.YELLOW);
        scene.lights.add(new DirectionalLight(new Color(20,0,20), new Vector(50, 180, 600)));

      //  scene.lights.add(new PointLight(new Color(50, 50, 50), new Point3D(100 , -50,0)));




        Render render = new Render()
                .setImageWriter(new ImageWriter("New", 500, 500)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene))
                .setFlagDOP(true);
        render.renderImage();
        render.writeToImage();


    }
}





