package elements;

import primitives.Color;

public class AmbientLight extends Light {

    /**
     * parameter constructor
     * @param iA
     * @param kA
     */
    public AmbientLight(Color iA, double kA) {
        super(iA.scale(kA));
    }

    /**
     * default constructor
     */
    public AmbientLight() {
        super(Color.BLACK);
    }
}
