package brdf;

import shape.ShadeRec;
import util.RGBColor;

public class LambertianBRDF implements BRDF {

    private RGBColor color;
    private double kd;

    @Override
    public RGBColor getF(ShadeRec shadeRec) {
        double s = kd / Math.PI;
        return color.scale(s);
    }

    @Override
    public RGBColor getReflectance(ShadeRec shadeRec) {
        return color.scale(kd);
    }

    public void setColor(RGBColor color) {
        this.color = color;
    }

    public void setKd(double kd) {
        this.kd = kd;
    }
}
