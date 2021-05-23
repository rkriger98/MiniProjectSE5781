package primitives;

public class Material {
    public double _kD = 0d, _kS = 0d;
    public double _nShininess = 1d;

    /**
     * Build setters methods
     * @param kD
     * @return
     */
    public Material setkD(double kD) {
        _kD = kD;
        return this;
    }

    public Material setkS(double kS) {
        _kS = kS;
        return this;
    }

    public Material setnShininess(double nShininess) {
        _nShininess = nShininess;
        return this;
    }
}
