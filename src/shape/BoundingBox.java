package shape;

import material.Material;
import math.Point;
import math.Ray;
import math.Transformation;
import math.Vector;

public class BoundingBox implements Shape {

    private final Transformation transformation;
    private final Point p0;
    private final Point p1;

    BoundingBox(Transformation transformation, Point p0, Point p1) {
        this.transformation = transformation;
        this.p0 = p0;
        this.p1 = p1;
    }

    @Override
    public boolean intersect(Ray ray, ShadeRec shadeRec) {
        Ray transformed = transformation.transformInverse(ray);

        Vector o = transformed.origin.toVector3D();

        double ox = o.x;
        double oy = o.y;
        double oz = o.z;
        double dx = transformed.direction.x;
        double dy = transformed.direction.y;
        double dz = transformed.direction.z;

        double txMin;
        double tyMin;
        double tzMin;
        double txMax;
        double tyMax;
        double tzMax;

        double a = 1.0 / dx;
        if (a >= 0) {
            txMin = (p0.x - ox) * a;
            txMax = (p1.x - ox) * a;
        } else {
            txMin = (p1.x - ox) * a;
            txMax = (p0.x - ox) * a;
        }
        double b = 1.0 / dy;
        if (b >= 0) {
            tyMin = (p0.y - oy) * b;
            tyMax = (p1.y - oy) * b;
        } else {
            tyMin = (p1.y - oy) * b;
            tyMax = (p0.y - oy) * b;
        }
        double c = 1.0 / dz;
        if (c >= 0) {
            tzMin = (p0.z - oz) * c;
            tzMax = (p1.z - oz) * c;
        } else {
            tzMin = (p1.z - oz) * c;
            tzMax = (p0.z - oz) * c;
        }

        double t0;
        double t1;

        if (txMin > tyMin) {
            t0 = txMin;
        } else {
            t0 = tyMin;
        }
        if (tzMin > t0) {
            t0 = tzMin;
        }
        if (txMax < tyMax) {
            t1 = txMax;
        } else {
            t1 = tyMax;
        }
        if (tzMax < t1) {
            t1 = tzMax;
        }
        return (t0 < t1 && t1 > K_EPSILON);
    }

    @Override
    public void setMaterial(Material material) {

    }

    @Override
    public Material getMaterial() {
        return null;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return this;
    }

    Point getP0() {
        return p0;
    }

    Point getP1() {
        return p1;
    }

    public Point getMiddle() {
        double x;
        if (p0.x < p1.x) {
            x = (int) (p0.x + (p1.x - p0.x) / 2);
        } else {
            x = (int) (p1.x + (p0.x - p1.x) / 2);
        }

        double y;
        if (p0.y < p1.y) {
            y = (int) (p0.y + (p1.y - p0.y) / 2);
        } else {
            y = (int) (p1.y + (p0.y - p1.y) / 2);
        }

        double z;
        if (p0.z < p1.z) {
            z = (int) (p0.z + (p1.z - p0.z) / 2);
        } else {
            z = (int) (p1.z + (p0.z - p1.z) / 2);
        }
        return new Point(x, y, z);
    }
}
