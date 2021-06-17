//package renderer;
//
//import elements.Camera;
//import primitives.Color;
//import primitives.Ray;
//
//import java.awt.*;
//import java.util.List;
//import java.util.MissingFormatArgumentException;
//import java.util.MissingResourceException;
//
//public class Render {
//    private ImageWriter _imageWriter=null;
//    //private Scene _scene;
//    private Camera _camera=null;
//    private BaseRayTracer _rayTracerBase=null;
//    private  boolean FlagDOP;
//
//    /**
//     * Builder set functions
//     * @param imageWriter
//     * @return
//     */
//    public Render setImageWriter(ImageWriter imageWriter) {
//        _imageWriter = imageWriter;
//        return this;
//    }
//
//
//
//    public Render setCamera(Camera camera) {
//        _camera = camera;
//        return this;
//    }
//
//    public Render setRayTracer(BaseRayTracer rayTracer) {
//        _rayTracerBase = rayTracer;
//        return this;
//
//    }
//
//    public Render setFlagDOP(boolean flagDOP) {
//        FlagDOP = flagDOP;
//        return this;
//    }
//
//    /**
//     * For each pixel in the view plane
//     * get color from it and Color the pixel
//     */
//    public void renderImage() {
//        try {
//            if (_imageWriter == null /*|| _scene == null*/ || _camera == null || _rayTracerBase == null) {
//                throw new MissingFormatArgumentException("missing resource");
//            }
//            int nX = _imageWriter.getNx();
//            int nY = _imageWriter.getNy();
//            for (int i = 0; i < nY; i++) {
//                for (int j = 0; j < nX; j++) {
//                    List<Ray> rays = _camera.constructRaysThroughPixel(nX, nY, j, i );//
//
//                    _imageWriter.writePixel(j, i, _rayTracerBase.traceRays(rays));
//                }
//            }
//        }
//        catch(MissingResourceException e){
//            throw new UnsupportedOperationException("Not implemented yet");
//        }
//    }
//
//    /**
//     * Activates the function writePixel for each point
//     * @param interval
//     * @param color
//     */
//    public void printGrid(int interval, Color color) {
//        int nX = _imageWriter.getNx();
//        int nY = _imageWriter.getNy();
//        for (int i = 0; i < nY; i++) {
//            for (int j = 0; j < nX; j++) {
//                if (i % interval == 0 || j % interval == 0) {
//                    _imageWriter.writePixel(j, i, color);
//                }
//            }
//        }
//    }
//
//    /**
//     * Activates the function writeToImage
//     */
//    public void writeToImage() {
//        _imageWriter.writeToImage();
//    }
//}
package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.util.List;
import java.util.MissingFormatArgumentException;
import java.util.MissingResourceException;

import static primitives.Ray.rayRandomBeam;

/**
 * Renderer class is responsible for generating pixel color map from a graphic
 * scene, using ImageWriter class
 *
 * @author Dan
 */
public class Render {
    private Camera camera;
    private ImageWriter imageWriter;
    private BaseRayTracer tracer;
    private static final String RESOURCE_ERROR = "Renderer resource not set";
    private static final String RENDER_CLASS = "Render";
    private static final String IMAGE_WRITER_COMPONENT = "Image writer";
    private static final String CAMERA_COMPONENT = "Camera";
    private static final String RAY_TRACER_COMPONENT = "Ray tracer";

    private int threadsCount = 0;
    private static final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
    private boolean print = false; // printing progress percentage
    private boolean flagDOP = false;
    /**
     * Set multi-threading <br>
     * - if the parameter is 0 - number of cores less 2 is taken
     *
     * @param threads number of threads
     * @return the Render object itself
     */
//    public Render setMultithreading(int threads) {
//        if (threads < 0)
//            throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
//        if (threads != 0)
//            this.threadsCount = threads;
//        else {
//            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
//            this.threadsCount = cores <= 2 ? 1 : cores;
//        }
//        return this;
//    }

    /**
     * Set debug printing on
     *
     * @return the Render object itself
     */
//    public Render setDebugPrint() {
//        print = true;
//        return this;
//    }
    public Render setFlagDOP(boolean flagDOP) {
        this.flagDOP = flagDOP;
        return this;
    }

    /**
     * Pixel is an internal helper class whose objects are associated with a Render
     * object that they are generated in scope of. It is used for multithreading in
     * the Renderer and for follow up its progress.<br/>
     * There is a main follow up object and several secondary objects - one in each
     * thread.
     *
     * @author Dan
     */
    private class Pixel {
        private long maxRows = 0;
        private long maxCols = 0;
        private long pixels = 0;
        public volatile int row = 0;
        public volatile int col = -1;
        private long counter = 0;
        private int percents = 0;
        private long nextCounter = 0;

        /**
         * The constructor for initializing the main follow up Pixel object
         *
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            this.maxRows = maxRows;
            this.maxCols = maxCols;
            this.pixels = (long) maxRows * maxCols;
            this.nextCounter = this.pixels / 100;
            if (Render.this.print)
                System.out.printf("\r %02d%%", this.percents);
        }


        /**
         * Default constructor for secondary Pixel objects
         */
        public Pixel() {
        }


        public long getCounter() {
            return counter;
        }

        public void setCounter(long counter) {
            this.counter = counter;
        }


        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object
         * - this function is critical section for all the threads, and main Pixel
         * object data is the shared data of this critical section.<br/>
         * The function provides next pixel number each call.
         *
         * @param target target secondary Pixel object to copy the row/column of the
         *               next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print,
         * if it is -1 - the task is finished, any other value - the progress
         * percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col;
            ++this.counter;
            if (col < this.maxCols) {
                target.row = this.row;
                target.col = this.col;
                if (Render.this.print && this.counter == this.nextCounter) {
                    ++this.percents;
                    this.nextCounter = this.pixels * (this.percents + 1) / 100;
                    return this.percents;
                }
                return 0;
            }
            ++row;
            if (row < this.maxRows) {
                col = 0;
                target.row = this.row;
                target.col = this.col;
                if (Render.this.print && this.counter == this.nextCounter) {
                    ++this.percents;
                    this.nextCounter = this.pixels * (this.percents + 1) / 100;
                    return this.percents;
                }
                return 0;
            }
            return -1;
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         *
         * @param target target secondary Pixel object to copy the row/column of the
         *               next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percent = nextP(target);
            if (Render.this.print && percent > 0)
                synchronized (this) {
                    notifyAll();
                }
            if (percent >= 0)
                return true;
            if (Render.this.print)
                synchronized (this) {
                    notifyAll();
                }
            return false;
        }

        /**
         * Debug print of progress percentage - must be run from the main thread
         */
        public void print() {
            if (Render.this.print)
                while (this.percents < 100)
                    try {
                        synchronized (this) {
                            wait();
                        }
                        System.out.printf("\r %02d%%", this.percents);
                        System.out.flush();
                    } catch (Exception e) {
                    }
        }
    }

    /**
     * Camera setter
     *
     * @param camera to set
     * @return renderer itself - for chaining
     */
    public Render setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    /**
     * Image writer setter
     *
     * @param imgWriter the image writer to set
     * @return renderer itself - for chaining
     */
    public Render setImageWriter(ImageWriter imgWriter) {
        this.imageWriter = imgWriter;
        return this;
    }

    /**
     * Ray tracer setter
     *
     * @param tracer to use
     * @return renderer itself - for chaining
     */
    public Render setRayTracer(BaseRayTracer tracer) {
        this.tracer = tracer;
        return this;
    }

    /**
     * Produce a rendered image file
     */
    public void writeToImage() {
        if (imageWriter == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);

        imageWriter.writeToImage();
    }

    /**
     * Cast ray from camera in order to color a pixel
     *
     * @param nX  resolution on X axis (number of pixels in row)
     * @param nY  resolution on Y axis (number of pixels in column)
     * @param col pixel's column number (pixel index in row)
     * @param row pixel's row number (pixel index in column)
     */
    private void castRay(int nX, int nY, int col, int row) {
        Pixel pixel = new Pixel();
        pixel.row = row;
        pixel.col = col;

        if (flagDOP == true) {
            Ray ray = camera.constructRayThroughPixel(nX, nY, col, row);
            imageWriter.writePixel(col, row, superSampling(ray, pixel));
        } else {

            Ray ray = camera.constructRayThroughPixel(nX, nY, col, row);
            Color color = tracer.traceRay(ray);
            imageWriter.writePixel(col, row, color);
        }
    }


    /**
     * function that gets a ray and sends to the recursive function
     *
     * @param ray   - a ray from camera to a pixel
     * @param pixel - the current pixel
     * @return - the average color of all relevant rays.
     */
    public Color superSampling(Ray ray, Pixel pixel) {
        if (camera.getNumOfRays() <= 1)
            return tracer.traceRay(ray);

        Color result = tracer._scene.background;
        Point3D pij = ray.getPoint(camera.getDistance() / (camera.getvTo().dotProduct(ray.getDir())));
        Point3D f = ray.getPoint((camera.getFocalDistance() + camera.getDistance()) / (camera.getvTo().dotProduct(ray.getDir())));//focal point
        Color color = rec(camera.getAperture(), camera.getNumOfRays(), pij, f, 3, pixel);
        result = result.add(/*tracer.traceRay(ray)*/color.reduce(pixel.counter));
        return result;
    }

    /**
     * recursive function that divides the aperture circle in to small circles in order to avoid calculating colors that are not relevant
     *
     * @param radius - the radius of  current circle
     * @param num    - maximum number of rays to calculate in current circle
     * @param center - center point of current circle
     * @param target - focal point
     * @param k      - recursive degree
     * @param pixel  - the current pixel
     * @return - the sum of the colors for a specific pixel
     */
    private Color rec(double radius, int num, Point3D center, Point3D target, int k, Pixel pixel) {
        Vector up = camera.getvUp();
        Vector right = camera.getvRight();
        double move = radius / 1.414;
        Color colorA = tracer.traceRay(new Ray(center.add(up.scale(radius)), target.subtract(center.add(up.scale(radius)))));
        Color colorB = tracer.traceRay(new Ray(center.add(right.scale(radius)), target.subtract(center.add(right.scale(radius)))));
        Color colorC = tracer.traceRay(new Ray(center.add(up.scale(-radius)), target.subtract(center.add(up.scale(-radius)))));
        Color colorD = tracer.traceRay(new Ray(center.add(right.scale(-radius)), target.subtract(center.add(right.scale(-radius)))));
        Color colorAB = tracer.traceRay(new Ray(center.add(up.scale(move)).add(right.scale(move)), target.subtract(center.add(up.scale(move)).add(right.scale(move)))));
        Color colorBC = tracer.traceRay(new Ray(center.add(up.scale(-move)).add(right.scale(move)), target.subtract(center.add(up.scale(-move)).add(right.scale(move)))));
        Color colorCD = tracer.traceRay(new Ray(center.add(up.scale(-move)).add(right.scale(-move)), target.subtract(center.add(up.scale(-move)).add(right.scale(-move)))));
        Color colorDA = tracer.traceRay(new Ray(center.add(up.scale(move)).add(right.scale(-move)), target.subtract(center.add(up.scale(move)).add(right.scale(-move)))));
        Color centerColor = tracer.traceRay(new Ray(center, target.subtract(center)));

        Color result = new Color(centerColor);
        result = result.add(colorA, colorB, colorC, colorD, colorAB, colorBC, colorCD, colorDA);
        num = num - 9;
        pixel.setCounter(pixel.getCounter() + 9);

        if (k == 0 || num <= 36) {
            pixel.setCounter(pixel.getCounter() + num);
            result = tracer.traceRays(rayRandomBeam(center, target, radius, num, right, up));
            return result;
        }
        double newRad = radius / (2.414213562);         //R=r(1+√2)
        if (!(colorA.equals(centerColor)) || !(colorAB.equals(centerColor)) || !(colorDA.equals(centerColor)))
            result = result.add(rec(newRad, (num / 4), center.add(up.scale(radius - newRad)), target, k - 1, pixel));
        else {
            result = result.add(colorA.scale(num / 4));
            pixel.setCounter(pixel.getCounter() + num / 4);
        }
        if (!colorB.equals(centerColor) || !colorBC.equals(centerColor) || !colorAB.equals(centerColor))
            result = result.add(rec(newRad, num / 4, center.add(right.scale(radius - newRad)), target, k - 1, pixel));
        else {
            result = result.add(colorB.scale(num / 4));
            pixel.setCounter(pixel.getCounter() + num / 4);
        }
        if (!colorC.equals(centerColor) || !colorCD.equals(centerColor) || !colorBC.equals(centerColor))
            result = result.add(rec(newRad, num / 4, center.add(up.scale(-(radius - newRad))), target, k - 1, pixel));
        else {
            result = result.add(colorC.scale(num / 4));
            pixel.setCounter(pixel.getCounter() + num / 4);
        }
        if (!colorD.equals(centerColor) || !colorDA.equals(centerColor) || !colorCD.equals(centerColor))
            result = result.add(rec(newRad, num / 4, center.add(right.scale(-(radius - newRad))), target, k - 1, pixel));
        else {
            result = result.add(colorD.scale(num / 4));
            pixel.setCounter(pixel.getCounter() + num / 4);
        }

        return result;
    }


    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object - with multi-threading
     */
    private void renderImageThreaded() {
        final int nX = imageWriter.getNx();
        final int nY = imageWriter.getNy();
        final Pixel thePixel = new Pixel(nY, nX);
        // Generate threads
        Thread[] threads = new Thread[threadsCount];
        for (int i = threadsCount - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel();
                while (thePixel.nextPixel(pixel))
                    castRay(nX, nY, pixel.col, pixel.row);
            });
        }
        // Start threads
        for (Thread thread : threads)
            thread.start();

        // Print percents on the console
        thePixel.print();

        // Ensure all threads have finished
        for (Thread thread : threads)
            try {
                thread.join();
            } catch (Exception e) {
            }

        if (print)
            System.out.print("\r100%");
    }

    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object
     */
    public void renderImage() {
        if (imageWriter == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);
        if (camera == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, CAMERA_COMPONENT);
        if (tracer == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, RAY_TRACER_COMPONENT);

        final int nX = imageWriter.getNx();
        final int nY = imageWriter.getNy();
        if (threadsCount == 0)
            for (int i = 0; i < nY; ++i)
                for (int j = 0; j < nX; ++j)
                    castRay(nX, nY, j, i);
        else
            renderImageThreaded();
    }

    /**
     * Create a grid [over the picture] in the pixel color map. given the grid's
     * step and color.
     *
     * @param step  grid's step
     * @param color grid's color
     */
    public void printGrid(int step, Color color) {
        if (imageWriter == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        for (int i = 0; i < nY; ++i)
            for (int j = 0; j < nX; ++j)
                if (j % step == 0 || i % step == 0)
                    imageWriter.writePixel(j, i, color);
    }
}