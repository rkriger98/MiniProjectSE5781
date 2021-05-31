package scene;

import elements.AmbientLight;
import elements.LightSource;
import geometries.Geometries;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * class scene
 */
public class Scene {

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
     * chaining methods
     * @param background
     * @return
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
