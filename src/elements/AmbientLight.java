package elements;

import primitives.Color;

public class AmbientLight extends Light {
    /**
     * TODO
     */
    public AmbientLight() {
        super(Color.BLACK);
    }

    /**
     * parameter constructor
     * @param iA
     * @param kA
     */
    public AmbientLight(Color iA, double kA) {
        super(iA.scale(kA));
    }


}