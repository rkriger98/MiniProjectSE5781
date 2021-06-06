package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

public abstract class BaseRayTracer {

    protected Scene _scene;

    /**
     * constructor
     * @param scene
     */
    public BaseRayTracer(Scene scene) {
        if(scene==null){
            throw new IllegalArgumentException("scene can not be null");
        }
        _scene = scene;
    }

    /**
     * abstract function
     * @param ray
     * @return color
     */
    public abstract Color traceRay(Ray ray);
}