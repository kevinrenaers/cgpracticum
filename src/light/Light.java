package light;

import math.Vector;
import shape.ShadeRec;
import util.RGBColor;

public interface Light {

    Vector getDirection(ShadeRec shadeRec);

    RGBColor getRadiance();

    void setLs(double ls);
}
