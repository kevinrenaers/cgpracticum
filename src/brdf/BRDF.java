package brdf;

import shape.ShadeRec;
import util.RGBColor;

public interface BRDF {

    RGBColor getF(ShadeRec shadeRec);

    RGBColor getReflectance(ShadeRec shadeRec);
}
