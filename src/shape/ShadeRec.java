package shape;

import material.Material;
import math.Point;
import math.Ray;
import math.Vector;
import util.RGBColor;

public class ShadeRec {

    private boolean hasHitObject;
    private Point hitPoint;
    private Vector normal;
    private RGBColor color;
    private World world;
    private double t;
    private Ray ray;
    private Material material;

    ShadeRec (World world) {
        this.world = world;
        this.hasHitObject = false;
        this.hitPoint = null;
        this.normal = null;
        this.color = new RGBColor(1);
        this.t = Double.MAX_VALUE;
        this.ray = null;
        this.material = null;
    }

    public boolean hasHitObject() {
        return hasHitObject;
    }

    public void setHasHitObject(boolean hasHitObject) {
        this.hasHitObject = hasHitObject;
    }

    public Point getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(Point hitPoint) {
        this.hitPoint = hitPoint;
    }

    public Vector getNormal() {
        return normal;
    }

    public void setNormal(Vector normal) {
        this.normal = normal;
    }

    public RGBColor getColor() {
        return color;
    }

    public void setColor(RGBColor color) {
        this.color = color;
    }

    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
    }

    public Ray getRay() {
        return ray;
    }

    public void setRay(Ray ray) {
        this.ray = ray;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public World getWorld() {
        return world;
    }
}
