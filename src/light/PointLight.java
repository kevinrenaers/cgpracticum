package light;

import math.Point;
import math.Vector;
import shape.ShadeRec;
import util.RGBColor;

public class PointLight implements Light {

    private Point location;
    private RGBColor color;
    private double ls;

    public PointLight(Point location) {
        this.location = location;
        this.color = new RGBColor(1);
        this.ls = 1.0;
    }

    @Override
    public Vector getDirection(ShadeRec shadeRec) {
        return location.subtract(shadeRec.getHitPoint()).normalize();
    }

    @Override
    public RGBColor getColor() {
        return color.scale(ls);
    }

    @Override
    public void setLs(double ls) {
        this.ls = ls;
    }
}
