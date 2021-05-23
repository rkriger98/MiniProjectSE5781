package scene;

import elements.AmbientLight;
import elements.LightSource;
import geometries.Geometries;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

public class Scene {

    private final String _name;
    public Color _background = Color.BLACK;
    public AmbientLight _ambientLight;
    public Geometries _geometries;
    public List <LightSource> _lights = new LinkedList <LightSource>();

    public Scene(String name) {
        _name = name;
        _geometries = new Geometries();
    }

    /**
     * chaining methods
     * @param background
     * @return
     */
    public Scene setBackground(Color background) {
        _background = background;
        return this;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        _ambientLight = ambientLight;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        _geometries = geometries;
        return this;
    }

    public Scene setLights(List<LightSource> lights) {
        _lights = lights;
        return this;
    }
}
