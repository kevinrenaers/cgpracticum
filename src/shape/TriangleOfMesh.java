package shape;

import material.Material;
import math.*;

public class TriangleOfMesh implements Shape {

    private final Transformation transformation;
    private final Point p0;
    private final Point p1;
    private final Point p2;
    private final Vector normal0;
    private final Vector normal1;
    private final Vector normal2;
    private Material material;

    public TriangleOfMesh(Transformation transformation,Point[] points, Vector[] normals) {
        this.transformation = transformation;
        this.p0 = points[0];
        this.p1 = points[1];
        this.p2 = points[2];
        this.normal0 = normals[0];
        this.normal1 = normals[1];
        this.normal2 = normals[2];
    }

    @Override
    public boolean intersect(Ray ray, ShadeRec shadeRec) {
        Ray transformed = transformation.transformInverse(ray);

        Vector o = transformed.origin.toVector3D();

        double a = p0.x - p1.x;
        double b = p0.x - p2.x;
        double c = transformed.direction.x;
        double d = p0.x - o.x;
        double e = p0.y - p1.y;
        double f = p0.y - p2.y;
        double g = transformed.direction.y;
        double h = p0.y - o.y;
        double i = p0.z - p1.z;
        double j = p0.z - p2.z;
        double k = transformed.direction.z;
        double l = p0.z - o.z;

        double m = f * k - g * j;
        double n = h * k - g * l;
        double p = f * l - h * j;
        double q = g * i - e * k;
        double s = e * j - f * i;

        double invDenom = 1.0 / (a * m + b * q + c * s);

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
        Matrix transposeOfInverse = this.transformation.getInverseTransformationMatrix().transpose();
        shadeRec.setNormal(transposeOfInverse.transform(interpolatePhong(beta, gamma)));
        shadeRec.setHitPoint(transformed.origin.add(transformed.direction.scale(t)));

        return true;
    }

    private Vector interpolatePhong(double beta, double gamma) {
        Vector normal = normal0.scale((1 - beta - gamma))
                .add(normal1.scale(beta))
                .add(normal2.scale(gamma));
        return normal.normalize();
    }

    @Override
    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public BoundingBox getBoundingBox() {
        double delta = 0.000001;

        double px0 = Math.min(Math.min(p0.x, p1.x), p2.x) - delta;
        double px1 = Math.max(Math.max(p0.x, p1.x), p2.x) + delta;
        double py0 = Math.min(Math.min(p0.y, p1.y), p2.y) - delta;
        double py1 = Math.max(Math.max(p0.y, p1.y), p2.y) + delta;
        double pz0 = Math.min(Math.min(p0.z, p1.z), p2.z) - delta;
        double pz1 = Math.max(Math.max(p0.z, p1.z), p2.z) + delta;

        return new BoundingBox(transformation, new Point(px0, py0, pz0), new Point(px1, py1, pz1));
    }
}
