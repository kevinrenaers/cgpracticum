package shape;

import material.Material;
import math.Point;
import math.Ray;
import math.Transformation;
import math.Vector;

import java.util.ArrayList;
import java.util.List;

public class TriangleMesh implements Shape {

    private final Transformation transformation;
    private List<TriangleOfMesh> triangles;
    private Material material;
    private BoundingBox boundingBox;

    public TriangleMesh(Transformation transformation) {
        this.transformation = transformation;
        this.triangles = new ArrayList<>();
    }

    public void addTriangle(TriangleOfMesh triangle) {
        triangles.add(triangle);
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
        for (Shape shape : triangles) {
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
            shadeRec.setMaterial(material);
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

    public BoundingBox getBoundingBox() {
        if (boundingBox == null) {
            boundingBox = computeBoundingBox();
        }
        return boundingBox;
    }

    private BoundingBox computeBoundingBox() {
        Point p0 = computeMinCoords();
        Point p1 = computeMaxCoords();
        return new BoundingBox(transformation, p0, p1);
    }

    private Point computeMinCoords(){
        double p0X = Double.MAX_VALUE;
        double p0Y = Double.MAX_VALUE;
        double p0Z = Double.MAX_VALUE;

        BoundingBox temp;
        for (TriangleOfMesh triangle : triangles) {
            temp = triangle.getBoundingBox();

            if (temp.getP0().x < p0X) {
                p0X = temp.getP0().x;
            }
            if (temp.getP0().y < p0Y) {
                p0Y = temp.getP0().y;
            }
            if (temp.getP0().z < p0Z) {
                p0Z = temp.getP0().z;
            }
        }

        return new Point(p0X - K_EPSILON, p0Y -K_EPSILON, p0Z -K_EPSILON);
    }

    private Point computeMaxCoords(){
        double p1X = Double.MIN_VALUE;
        double p1Y = Double.MIN_VALUE;
        double p1Z = Double.MIN_VALUE;

        BoundingBox temp;
        for(TriangleOfMesh shape: triangles){
            temp = shape.getBoundingBox();

            if(temp.getP1().x > p1X){
                p1X = temp.getP1().x;
            }
            if(temp.getP1().y > p1Y){
                p1Y = temp.getP1().y;
            }
            if(temp.getP1().z > p1Z){
                p1Z = temp.getP1().z;
            }
        }

        return new Point(p1X + K_EPSILON, p1Y + K_EPSILON, p1Z + K_EPSILON);
    }

    public List<TriangleOfMesh> getTriangles() {
        return triangles;
    }
}
