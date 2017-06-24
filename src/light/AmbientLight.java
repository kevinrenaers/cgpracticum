package light;

import math.Vector;
import shape.ShadeRec;
import util.RGBColor;

public class AmbientLight implements Light {

    private RGBColor color;
    private double ls;

    public AmbientLight() {
        this.color = new RGBColor(1);
        this.ls = 1.0;
    }

    @Override
    public Vector getDirection(ShadeRec shadeRec) {
        return new Vector();
    }

    @Override
    public RGBColor getRadiance() {
        return color.scale(ls);
    }

    @Override
    public void setLs(double ls) {
        this.ls = ls;
    }
}
