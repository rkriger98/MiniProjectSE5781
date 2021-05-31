package primitives;

/**
 * todo
 */
public class Material {

    public double _Kd = 0;
    public double _Ks = 0;
    public int _nShininess = 0;

    /*public Material(double kD, double kS, int nShininess) {
        this._Kd = kD;
        this._Ks = kS;
        this._nShininess = nShininess;
    }*/

    public Material() {

    }



    public Material setKd(double _Kd) {
        this._Kd = _Kd;
        return  this;
    }
    public Material setKs(double _Ks) {
        this._Ks = _Ks;
        return this;
    }
    public Material setShininess(int _nShininess) {
        this._nShininess = _nShininess;
        return this;
    }

    public double getKd() {
        return _Kd;
    }
    public double getKs() {
        return _Ks;
    }
    public int getShininess() {
        return _nShininess;
    }


}

