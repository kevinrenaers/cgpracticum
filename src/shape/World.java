package shape;

import camera.Camera;
import camera.PerspectiveCamera;
import light.AmbientLight;
import light.Light;
import light.PointLight;
import material.Material;
import material.MatteMaterial;
import math.Point;
import math.Ray;
import math.Transformation;
import math.Vector;
import util.BoxSplitter;
import util.ObjReader;
import util.RGBColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class World {

    private Camera camera;
    private final List<Shape> shapes = new ArrayList<>();
    private final List<Light> lights = new ArrayList<>();
    private Light ambientLight;

    public final RGBColor backgroundColor = RGBColor.convertToRGBColor(Color.black);

    public World(int width, int height) {
        camera = new PerspectiveCamera(width, height,
                new Point(0,0,-150), new Vector(0, 0, 1), new Vector(0, 1, 0), 90);


        PointLight pl = new PointLight(new Point(0, 0, -155));
        lights.add(pl);

        this.ambientLight = new AmbientLight();


        // initialize the scene
        Transformation t1 = Transformation.createTranslation(0, 0, 14);
        t1 = t1.append(Transformation.createRotationX(250));
        t1 = t1.append(Transformation.createRotationZ(210));
        Transformation t2 = Transformation.createTranslation(4, -4, 12);
        Transformation t3 = Transformation.createTranslation(-4, -4, 12);
        Transformation t4 = Transformation.createTranslation(4, 4, 12);
        Transformation t5 = Transformation.createTranslation(-4, 4, 12);

        MatteMaterial matteMaterial = new MatteMaterial();
        matteMaterial.setKa(0.25);
        matteMaterial.setKd(0.65);
        matteMaterial.setCd(RGBColor.convertToRGBColor(Color.GREEN));

//        Sphere sphere = new Sphere(t1, 5);
//        sphere.setMaterial(matteMaterial);
//        shapes.add(sphere);
//        shapes.add(new Sphere(t2, 4));
//        shapes.add(new Sphere(t3, 4));
//        shapes.add(new Sphere(t4, 4));
//        shapes.add(new Sphere(t5, 4));
//        shapes.add(new Plane(t1, new Point(1, 5, -100), new Vector(1, 1, 1)));
//        Triangle triangle1 = new Triangle(t1, new Point(-10, 0, 0), new Point(5, 5, 5), new Point(10, 3, 5));
//        triangle1.setMaterial(matteMaterial);
//        shapes.add(triangle1);
        List<Shape> triangleMeshes = BoxSplitter.splitShape(t1, new ObjReader("minicooper.obj").readFile(t1));
        for (Shape triangleMesh : triangleMeshes) {
//        TriangleMesh triangleMesh = new ObjReader("minicooper.obj").readFile(t1);
            triangleMesh.setMaterial(matteMaterial);
            shapes.add(triangleMesh);
        }
    }

    public ShadeRec trackRay(Ray ray) {
        double tMin = Double.MAX_VALUE;
        ShadeRec sr = new ShadeRec(this);
        ShadeRec shadeRec = new ShadeRec(this);
        boolean hasHitObject = false;
        Vector normal = null;
        Point hitpoint = null;
        Material material = null;
        for (Shape shape : shapes) {
            if (shape.intersect(ray, shadeRec)) {
                if (shadeRec.getT() < tMin) {
                    hasHitObject = true;
                    normal = shadeRec.getNormal();
                    hitpoint = shadeRec.getHitPoint();
                    material = shape.getMaterial();
                }
            }
        }
        if (hasHitObject) {
            sr.setHasHitObject(true);
            sr.setHitPoint(hitpoint);
            sr.setNormal(normal);
            sr.setT(tMin);
            sr.setMaterial(material);
            sr.setRay(ray);
            return sr;
        }

        return sr;
    }

    public Camera getCamera() {
        return camera;
    }

    public List<Light> getLights() {
        return lights;
    }

    public Light getAmbientLight() {
        return ambientLight;
    }
}
