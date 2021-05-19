package elements;

import primitives.Color;

public class AmbientLight {
    private final Color _intensity;

    /**
     * parameter constructor
     * @param iA
     * @param kA
     */
    public AmbientLight(Color iA, double kA) {
        _intensity = iA.scale(kA);
    }

    /**
     * getter
     * @return
     */
    public Color getIntensity() {
        return _intensity;
    }
}
