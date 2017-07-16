package util;

import math.Transformation;
import shape.Shape;
import shape.TriangleMesh;
import shape.TriangleOfMesh;

import java.util.*;

public class BoxSplitter {

    public static List<Shape> splitShape(Transformation transformation, TriangleMesh mesh) {
        if (mesh.getTriangles().size() == 1) {
            return Collections.singletonList(mesh);
        }
        List<Shape> shapes = new ArrayList<>();

        List<TriangleMesh> objects = new ArrayList<>();
        for(TriangleMesh shape : getShapes(transformation, mesh, Axis.X_AXIS)) {
            objects.addAll(getShapes(transformation, shape, Axis.Y_AXIS));
        }
        List<TriangleMesh> objects2 = new ArrayList<>();
        for(TriangleMesh object : objects) {
            objects2.addAll(getShapes(transformation, object, Axis.X_AXIS));
        }
        List<TriangleMesh> objects3 = new ArrayList<>();
        for(TriangleMesh object : objects2) {
            objects3.addAll(getShapes(transformation, object, Axis.Y_AXIS));
        }
        for(TriangleMesh object : objects3) {
            shapes.addAll(getShapes(transformation, object, Axis.X_AXIS));
        }

        return shapes;
    }

    private static List<TriangleMesh> getShapes(Transformation transformation, TriangleMesh compoundObject, Axis axis) {
        List<TriangleMesh> shapes = new ArrayList<>();

        ArrayList<TriangleOfMesh> shapeList = new ArrayList<>(compoundObject.getTriangles());
        shapeList.sort(getAxisComparator(axis));

        int middle = shapeList.size() / 2;
        TriangleMesh leftMesh = new TriangleMesh(transformation);
        TriangleMesh rightMesh = new TriangleMesh(transformation);

        for(int i = 0; i< shapeList.size(); i++) {
            if (i < middle) {
                leftMesh.addTriangle(shapeList.get(i));
            } else {
                rightMesh.addTriangle(shapeList.get(i));
            }
        }

        shapes.add(leftMesh);
        shapes.add(rightMesh);
        return shapes;
    }

    private static Comparator<Shape> getAxisComparator(Axis axis) {
        if (Axis.X_AXIS.equals(axis)) {
            return (o1, o2) -> Double.compare(o2.getBoundingBox().getMiddle().x, o1.getBoundingBox().getMiddle().x);
        } else if (Axis.Y_AXIS.equals(axis)) {
            return (o1, o2) -> Double.compare(o2.getBoundingBox().getMiddle().y, o1.getBoundingBox().getMiddle().y);
        } else {
            return (o1, o2) -> Double.compare(o2.getBoundingBox().getMiddle().z, o1.getBoundingBox().getMiddle().z);
        }
    }

    private enum Axis {
        X_AXIS,
        Y_AXIS,
        Z_AXIS;
    }
}
