package shape;

import material.Material;
import math.Point;
import math.Ray;
import math.Transformation;
import math.Vector;

public class Triangle implements Shape {

    private final Transformation transformation;
    private final Point p0;
    private final Point p1;
    private final Point p2;
    private final Vector normal;
    private Material material;

    public Triangle(Transformation transformation, Point p0, Point p1, Point p2) {
        this.transformation = transformation;
        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
        normal = (p1.subtract(p0)).cross(p2.subtract(p0)).normalize();
    }

    @Override
    public boolean intersect(Ray ray, ShadeRec shadeRec) {
        Ray transformed = transformation.transformInverse(ray);

        Vector o = transformed.origin.toVector3D();

        double a = p0.x - p1.x;
        double b = p0.x - p2.x;
        double c = ray.direction.x;
        double d = p0.x - o.x;
        double e = p0.y - p1.y;
        double f = p0.y - p2.y;
        double g = ray.direction.y;
        double h = p0.y - o.y;
        double i = p0.z - p1.z;
        double j = p0.z - p2.z;
        double k = ray.direction.z;
        double l = p0.z - o.z;

        double m = f * k - g * j;
        double n = h * k - g * l;
        double p = f * l - h * j;
        double q = g * i - e * k;
        double s = e * j - f * i;

        double invDenom = 1 / (a * m + b * q + c * s);

        double el = d * m - b * n - c * p;
        double beta = el * invDenom;

        if (beta < 0.0) {
            return false;
        }

        double r = e * l - h *i;
        double e2 = a * n + d * q + c * r;
        double gamma = e2 * invDenom;

        if (gamma < 0.0) {
            return false;
        }

        if (beta + gamma > 1.0) {
            return false;
        }

        double e3 = a * p - b * r + d * s;
        double t = e3 * invDenom;

        if (t < K_EPSILON) {
            return false;
        }

        shadeRec.setT(t);
        shadeRec.setNormal(normal);
        shadeRec.setHitPoint(transformed.origin.add(transformed.direction.scale(t)));

        return true;
    }

    @Override
    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public Material getMaterial() {
        return material;
    }
}
