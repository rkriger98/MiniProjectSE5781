package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 *
 */
public class SpotLight extends PointLight implements LightSource {

    private final Vector _dir;

    /**
     * c-tor
     *
     * @param intensity
     * @param position
     */
    protected SpotLight(Color intensity, Point3D position,Vector dir) {
        super(intensity, position);
        this._dir=dir.normalized();

    }




    /**
     * Models point light source with direction (such as a luxo lamp)
     * Intensity (I0)
     * Position (PL)
     * Direction dir (Vector) - normalized
     * Attenuation factors
     * @param p
     * @return
     */
    @Override
    public Color getIntensity(Point3D p) {

        double cosTetha=_dir.dotProduct(getL(p));
        Color intensity=super.getIntensity(p);
        return (intensity.scale(Math.max(0,cosTetha)));
    }




}
