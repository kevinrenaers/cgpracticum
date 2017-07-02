package util;

import math.Transformation;
import shape.CompoundObject;
import shape.Shape;
import shape.TriangleMesh;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BoxSplitter {

    public static List<Shape> splitShape(Transformation transformation, CompoundObject compoundObject) {
        if (compoundObject.getShapes().size() == 1) {
            return Collections.singletonList(compoundObject);
        }
        List<Shape> shapes = new ArrayList<>();

        ArrayList<Shape> shapeList = new ArrayList<>(compoundObject.getShapes());
        shapeList.sort(getAxisComparator());

        int middle = shapeList.size() / 2;
        TriangleMesh leftMesh = new TriangleMesh(transformation);
        TriangleMesh rightMesh = new TriangleMesh(transformation);

        for(int i = 0; i< shapeList.size(); i++) {
            if (i < middle) {
                leftMesh.addShape(shapeList.get(i));
            } else {
                rightMesh.addShape(shapeList.get(i));
            }
        }
        assert leftMesh.getShapes().size() + rightMesh.getShapes().size() == compoundObject.getShapes().size();

        shapes.add(leftMesh);
        shapes.add(rightMesh);
        return shapes;
    }

    private static Comparator<Shape> getAxisComparator() {
        return (o1, o2) -> Double.compare(o2.getBoundingBox().getMiddle().y, o1.getBoundingBox().getMiddle().y);
    }
}
