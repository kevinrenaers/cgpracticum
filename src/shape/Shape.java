package shape;

import material.Material;
import math.Ray;

/**
 * Interface which should be implemented by all {@link Shape}s.
 * 
 * @author Niels Billen
 * @version 1.0
 */
public interface Shape {

	static final double K_EPSILON = 2;
	/**
	 * Returns whether the given {@link Ray} intersects this {@link Shape}.
	 * False when the given ray is null.
	 * 
	 * @param ray
	 *            the ray to intersect with.
	 * @return true when the given {@link Ray} intersects this {@link Shape}.
	 */
	public boolean intersect(Ray ray, ShadeRec shadeRec);

	void setMaterial(Material material);

	Material getMaterial();
}
