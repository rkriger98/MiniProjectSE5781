package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;

import java.util.List;
import java.util.MissingFormatArgumentException;
import java.util.MissingResourceException;

public class Render {
    private ImageWriter _imageWriter = null;
    //private Scene _scene;
    private Camera _camera = null;
    private BaseRayTracer _rayTracerBase = null;

    /**
     * Builder set functions
     *
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
                    Ray ray = _camera.constructRayThroughPixel(nX, nY, j, i);
                    _imageWriter.writePixel(j, i, _rayTracerBase.traceRay(ray));
                }
            }
        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException("Not implemented yet");
        }
    }

    public void renderImage2() {
        try {
            if (_imageWriter == null /*|| _scene == null*/ || _camera == null || _rayTracerBase == null) {
                throw new MissingFormatArgumentException("missing resource");
            }
            int nX = _imageWriter.getNx();
            int nY = _imageWriter.getNy();
            double dist = _camera.getDistance();
            double width = _camera.getWidth();
            double height = _camera.getHeight();
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    List<Ray> rays = _camera.constructRaysThroughPixel(nX, nY, j, i, dist, width, height);
                        _imageWriter.writePixel(j, i, _rayTracerBase.traceRays(rays));
                }
            }
        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException("Not implemented yet");
        }
    }


    /**
     * Activates the function writePixel for each point
     *
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