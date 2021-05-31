package elements;


import primitives.Color;

/**
 * TODO
 */
abstract class Light {
    protected final Color _intensity;

    /**
     * c-tor
     * @param intensity
     */
    protected Light(Color intensity) {
        this._intensity = intensity;
    }

    /**
     * getter
     * @return intensity
     */
    public Color getIntensity() {
        return _intensity;
    }
}
