package elements;

import primitives.Color;

/**
 * Light class
 */
class Light {

    protected Color _intensity;

    /**
     * c-tor
     *
     * @param intensity
     */
    protected Light(Color intensity) {
        _intensity = intensity;
    }

    /**
     * getter
     *
     * @return _intensity
     */
    public Color getIntensity() {
        return _intensity;
    }
}
