package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;

import java.awt.*;
import java.util.List;
import java.util.MissingFormatArgumentException;
import java.util.MissingResourceException;

public class Render {
    private ImageWriter _imageWriter=null;
    //private Scene _scene;
    private Camera _camera=null;
    private BaseRayTracer _rayTracerBase=null;
    private  boolean FlagDOP;

    /**
     * Builder set functions
     * @param imageWriter
     * @return
     */
    public Render setImageWriter(ImageWriter imageWriter) {
        _imageWriter = imageWriter;
        return this;
    }



    public Render setCamera(Camera camera) {
        _camera = camera;
        return this;
    }

    public Render setRayTracer(BaseRayTracer rayTracer) {
        _rayTracerBase = rayTracer;
        return this;

    }




    public Render setFlagDOP(boolean flagDOP) {
        this.FlagDOP = flagDOP;
        return this;
    }

    /**
     * For each pixel in the view plane
     * get color from it and Color the pixel
     */
    public void renderImage() {
        try {
            if (_imageWriter == null /*|| _scene == null*/ || _camera == null || _rayTracerBase == null) {
                throw new MissingFormatArgumentException("missing resource");
            }
            int nX = _imageWriter.getNx();
            int nY = _imageWriter.getNy();
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {

                    List<Ray> rayList = _camera.constructRaysThroughPixel(nX, nY, j, i);
                    Color temp =_rayTracerBase.traceRays(rayList);
                    _imageWriter.writePixel(j, i, temp);
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