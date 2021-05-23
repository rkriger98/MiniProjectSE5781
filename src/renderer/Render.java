package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

import java.util.MissingFormatArgumentException;
import java.util.MissingResourceException;

public class Render {
    private ImageWriter _imageWriter;
    private Scene _scene = null;
    private Camera _camera;
    private RayTracerBase _rayTracerBase;


    /**
     * Builder set functions
     * @param imageWriter
     * @return
     */
    public Render setImageWriter(ImageWriter imageWriter) {
        _imageWriter = imageWriter;
        return this;
    }

    public Render setScene(Scene scene) {
        _scene = scene;
        return this;
    }

    public Render setCamera(Camera camera) {
        _camera = camera;
        return this;
    }

    public Render setRayTracer(RayTracerBase rayTracer) {
        _rayTracerBase = rayTracer;
        return this;

    }

    /**
     * For each pixel in the view plane
     * get color from it and Color the pixel
     */
    public void renderImage() {
        try {
            if (_imageWriter == null || /*_scene == null ||*/ _camera == null || _rayTracerBase == null) {
                throw new MissingFormatArgumentException("missing resource");
            }
            int nX = _imageWriter.getNx();
            int nY = _imageWriter.getNy();
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    Ray ray = _camera.constructRayThroughPixel(nX, nY, j, i);
                    _imageWriter.writePixel(j, i, _rayTracerBase.traceRay(ray));
                }
            }
        }
        catch(MissingResourceException e){
           throw new UnsupportedOperationException("Not implemented yet");
            }
    }

    /**
     * Activates the function writePixel for each point
     * @param interval
     * @param color
     */
    public void printGrid(int interval, Color color) {
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    _imageWriter.writePixel(j, i, color);
                }
            }
        }
    }

    /**
     * Activates the function writeToImage
     */
    public void writeToImage() {
        _imageWriter.writeToImage();
    }
}
