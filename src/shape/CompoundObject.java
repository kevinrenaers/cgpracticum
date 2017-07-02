package shape;

import material.Material;
import math.Point;
import math.Ray;
import math.Transformation;

import java.util.ArrayList;
import java.util.List;

public class CompoundObject implements Shape {

    private final Transformation transformation;
    private List<Shape> shapes;
    private BoundingBox boundingBox = null;

    CompoundObject(Transformation transformation) {
        this.transformation = transformation;
        shapes = new ArrayList<>();
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    @Override
    public boolean intersect(Ray ray, ShadeRec shadeRec) {
        return false;
    }

    @Override
    public void setMaterial(Material material) {

    }

    @Override
    public Material getMaterial() {
        return null;
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    @Override
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
        for (Shape shape : shapes) {
            temp = shape.getBoundingBox();

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
        for(Shape shape: shapes){
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
}
