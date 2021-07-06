package elements;

import primitives.Color;

/***
 * class Ambient Light paints all the points in a specific color.
 */
public class AmbientLight extends Light {
    /**
     * default c-tor
     */
    public AmbientLight() {
        super(Color.BLACK);
    }

    /**
     * c-tor
     * parameter constructor
     * @param iA-intensity
     * @param kA-
     */
    public AmbientLight(Color iA, double kA) {
        super(iA.scale(kA));
    }


}