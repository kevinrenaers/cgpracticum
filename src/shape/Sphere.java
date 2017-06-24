package shape;

import material.Material;
import math.*;

/**
 * Represents a three dimensional sphere.
 * 
 * @author Niels Billen
 * @version 1.0
 */
public class Sphere implements Shape {
	private Transformation transformation;
	private final double radius;
	private Material material;

	/**
	 * Creates a new {@link Sphere} with the given radius and which is
	 * transformed by the given {@link Transformation}.
	 * 
	 * @param transformation
	 *            the transformation applied to this {@link Sphere}.
	 * @param radius
	 *            the radius of this {@link Sphere}..
	 * @throws NullPointerException
	 *             when the transformation is null.
	 * @throws IllegalArgumentException
	 *             when the radius is smaller than zero.
	 */
	Sphere(Transformation transformation, double radius) {
		if (transformation == null)
			throw new NullPointerException("the given origin is null!");
		if (radius < 0)
			throw new IllegalArgumentException(
					"the given radius cannot be smaller than zero!");
		this.transformation = transformation;
		this.radius = radius;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see shape.Shape#intersect(geometry3d.Ray3D)
	 */
	@Override
	public boolean intersect(Ray ray, ShadeRec shadeRec) {
		Ray transformed = transformation.transformInverse(ray);

		Vector o = transformed.origin.toVector3D();

		double a = transformed.direction.dot(transformed.direction);
		double b = 2.0 * (transformed.direction.dot(o));
		double c = o.dot(o) - radius * radius;

		double d = b * b - 4.0 * a * c;

		if (d < 0)
			return false;
		double dr = Math.sqrt(d);
		double q = b < 0 ? -0.5 * (b - dr) : -0.5 * (b + dr);

		double t0 = q / a;
		double t1 = c / q;

		if( (t0 >= K_EPSILON && t1 >= K_EPSILON && t1 >= t0) || (t0 >= K_EPSILON && t1 < K_EPSILON)){
			shadeRec.setT(t0);
            Point localHitPoint = transformed.origin.add(transformed.direction.scale(t0));
            Vector localNormal = localHitPoint.toVector3D().normalize();
            Matrix transposeOfInverse = this.transformation.getInverseTransformationMatrix().transpose();
            shadeRec.setNormal(transposeOfInverse.transform(localNormal));
            shadeRec.setHitPoint(ray.origin.add(ray.direction.scale(t0)));
            return true;
		}
		if( (t0 >= K_EPSILON && t1 >= K_EPSILON && t0 > t1) || (t1 >= K_EPSILON && t0 < K_EPSILON)){
			shadeRec.setT(t1);
            Point localHitPoint = transformed.origin.add(transformed.direction.scale(t1));
            Vector localNormal = localHitPoint.toVector3D().normalize();
            Matrix transposeOfInverse = this.transformation.getInverseTransformationMatrix().transpose();
            shadeRec.setNormal(transposeOfInverse.transform(localNormal));
            shadeRec.setHitPoint(ray.origin.add(ray.direction.scale(t1)));
            return true;
		}

		return t0 >= 0 || t1 >= 0;
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
        return null;
    }
}
