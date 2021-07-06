package scene;

import elements.AmbientLight;
import elements.LightSource;
import geometries.Geometries;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * class scene- represents the geometries, angle, shadow and more
 */
public class Scene {

    /**
     * name- scene name
     * background- scene background color
     * ambientLight- scene ambientLight
     * geometries- list of geometry shapes
     * lights- list of the Light Sources
     */
    private final String _name;
    public Color background=Color.BLACK;;
    public AmbientLight ambientLight=new AmbientLight(Color.BLACK,0);
    public Geometries geometries=null;
    public List<LightSource> lights=new LinkedList<LightSource>();


    /**
     * c-tor
     * @param name
     */
    public Scene(String name) {
        _name = name;
        geometries = new Geometries();
    }

    /**
     * builders
     * @param background and ambientLight and geometries and lights
     * @return Scene
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    public Scene getLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}
