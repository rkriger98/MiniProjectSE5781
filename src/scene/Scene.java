package scene;

import elements.AmbientLight;
import geometries.Geometries;
import primitives.Color;

public class Scene {

    private final String _name;
    public Color background;
    public AmbientLight ambientLight;
    public Geometries geometries;

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
}
