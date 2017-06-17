package material;

import shape.ShadeRec;
import util.RGBColor;

public interface Material {

    RGBColor shade(ShadeRec shadeRec);

    default RGBColor areaLightShade(ShadeRec shadeRec) {
        return new RGBColor(0);
    }

    default RGBColor pathShade(ShadeRec shadeRec) {
        return new RGBColor(0);
    }
}
