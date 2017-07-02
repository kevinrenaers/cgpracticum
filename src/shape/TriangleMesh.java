package shape;

import material.Material;
import math.Point;
import math.Ray;
import math.Transformation;
import math.Vector;

public class TriangleMesh extends CompoundObject implements Shape {

    private Material material;

    public TriangleMesh(Transformation transformation) {
        super(transformation);
    }

    @Override
    public boolean intersect(Ray ray, ShadeRec shadeRec) {
        if (!getBoundingBox().intersect(ray, shadeRec)) {
            return false;
        }
        double tMin = Double.MAX_VALUE;
        boolean hasHitObject = false;
        Vector normal = null;
        Point hitpoint = null;
        for (Shape shape : getShapes()) {
            if (shape.intersect(ray, shadeRec)) {
                if (shadeRec.getT() < tMin) {
                    hasHitObject = true;
                    normal = shadeRec.getNormal();
                    hitpoint = shadeRec.getHitPoint();
                    tMin = shadeRec.getT();
                }
            }
        }
        if (hasHitObject) {
            shadeRec.setHasHitObject(true);
            shadeRec.setHitPoint(hitpoint);
            shadeRec.setNormal(normal);
            shadeRec.setT(tMin);
            shadeRec.setMaterial(getMaterial());
            shadeRec.setRay(ray);
        }

        return hasHitObject;
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
