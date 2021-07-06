package elements;


import primitives.Color;

/**
 * class light
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
     * @return intensity of the light
     */
    public Color getIntensity() {
        return _intensity;
    }
}
