package shape;

import material.Material;
import math.Point;
import math.Ray;
import math.Transformation;
import math.Vector;

public class Plane implements Shape {

    private Transformation transformation;
    private Point p;
    private Vector normal;
    private Material material;

    Plane(Transformation transformation, Point p, Vector normal) {
        this.transformation = transformation;
        this.p = p;
        this.normal = normal;
    }

    @Override
    public boolean intersect(Ray ray, ShadeRec shadeRec) {
        Ray transformed = transformation.transformInverse(ray);
        Vector aMinO = p.subtract(transformed.origin);
        double numerator = aMinO.dot(normal);
         double denominator = transformed.direction.dot(normal);
        double t = numerator / denominator;

        if (t > K_EPSILON) {
            shadeRec.setT(t);
            shadeRec.setNormal(normal);
            shadeRec.setHitPoint(transformed.origin.add(transformed.direction.scale(t)));
            return true;
        }
        return false;
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
