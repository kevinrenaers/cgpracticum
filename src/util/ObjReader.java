package util;

import math.Point;
import math.Transformation;
import math.Vector;
import shape.TriangleMesh;
import shape.TriangleOfMesh;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ObjReader {

    private final String fileName;

    private List<Point> points = new ArrayList<>();
    private List<Vector> normals = new ArrayList<>();
    private TriangleMesh triangleMesh;

    public ObjReader(String fileName) {
        this.fileName = fileName;
    }

    public TriangleMesh readFile(Transformation transformation) {
        triangleMesh = new TriangleMesh(transformation);
        try {
            InputStream stream = getClass().getResourceAsStream("resources/" + fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                parseLine(line, transformation);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return triangleMesh;
    }

    private void parseLine(String line, Transformation transformation) {
        if (line.contains("  ")) {
            line = line.replace("  ", " ");
        }
        String[] vals = line.split(" ");
        switch (vals[0]) {
            case "v":
                parseVertex(vals);
                break;
            case "vn":
                parseVertexNormal(vals);
                break;
            case "f":
                parseFaces(vals, transformation);
                break;
            default:
                break;
        }
    }

    private void parseVertex(String[] vals) {
        points.add(new Point(Double.parseDouble(vals[1]), Double.parseDouble(vals[2]), Double.parseDouble(vals[3])));
    }

    private void parseVertexNormal(String[] vals) {
        try {
            normals.add(new Vector(Double.parseDouble(vals[1]), Double.parseDouble(vals[2]), Double.parseDouble(vals[3])));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("blub");
        }
    }

    private void parseFaces(String[] vals, Transformation transformation) {
        if (vals.length == 4) {
            createTriangle(vals, transformation);
        } else if (vals.length > 4) {
            for (int i = 2; i < vals.length - 1; i++) {
                String[] temp = {vals[0], vals[1], vals[i], vals[i + 1]};
                createTriangle(temp, transformation);
            }
        }
    }

    private void createTriangle(String[] vals, Transformation transformation) {
        boolean normals = true;
        Point[] trianglePoints = new Point[3];
        Vector[] triangleNormals = new Vector[3];
        for (int i = 1; i <= 3; i++) {
            String val = vals[i];
            if (val.contains("/")) {
                String[] items = val.split("/");
                trianglePoints[i - 1] = points.get(Integer.parseInt(items[0]) - 1);
                triangleNormals[i - 1] = this.normals.get(Integer.parseInt(items[2]) - 1);

            } else {
                normals = false;
                trianglePoints[i - 1] = points.get(Integer.parseInt(val) - 1);
            }
        }
        TriangleOfMesh triangle = new TriangleOfMesh(transformation, trianglePoints, triangleNormals);
        triangleMesh.addShape(triangle);
    }
}
