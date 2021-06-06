package elements;

import primitives.Color;

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
     * @param iA
     * @param kA
     */
    public AmbientLight(Color iA, double kA) {
        super(iA.scale(kA));
    }


}