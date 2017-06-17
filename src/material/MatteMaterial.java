package material;

import brdf.LambertianBRDF;
import light.Light;
import math.Vector;
import shape.ShadeRec;
import util.RGBColor;

import java.util.List;

public class MatteMaterial implements Material {

    private LambertianBRDF ambientBRDF;
    private LambertianBRDF diffuseBRDF;

    public MatteMaterial() {
        this.ambientBRDF = new LambertianBRDF();
        this.diffuseBRDF = new LambertianBRDF();
    }

    public void setKa(double ka) {
        ambientBRDF.setKd(ka);
    }

    public void setKd(double kd) {
        diffuseBRDF.setKd(kd);
    }

    public void setCd(RGBColor color) {
        ambientBRDF.setColor(color);
        diffuseBRDF.setColor(color);
    }

    @Override
    public RGBColor shade(ShadeRec shadeRec) {
        RGBColor L = ambientBRDF.getReflectance(shadeRec).multiply(shadeRec.getWorld().getAmbientLight().getColor());
        List<Light> lights = shadeRec.getWorld().getLights();
        for (Light light : lights) {
            Vector wi = light.getDirection(shadeRec);
            double nDotWi = shadeRec.getNormal().dot(wi);

            if (nDotWi > 0.0) {
                L = L.add(diffuseBRDF.getF(shadeRec).multiply(light.getColor().scale(nDotWi)));
            }
        }
        return L;
    }
}
