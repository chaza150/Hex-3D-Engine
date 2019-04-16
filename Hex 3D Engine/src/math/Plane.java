package math;

public class Plane {
	
	private Vector3D normal; //Normal vector to the plane (normalised)
	private Vector3D origin; //Position of point on the plane
	
	/**
	 * Create a plane using a point on the plane and
	 * two independent vectors
	 * @param origin Point on the plane
	 * @param vect1 One independent vector
	 * @param vect2 Other independent vector
	 */
	public Plane(Vector3D origin, Vector3D vect1, Vector3D vect2) {
		this.origin = origin;
		this.normal = VectorMath.normalise(VectorMath.cross(VectorMath.normalise(vect1), VectorMath.normalise(vect2)));
	}
	
	/**
	 * Create a plane using a point on the plane and
	 * a normal vector
	 * @param origin Point on the plane
	 * @param normal normal to the plane
	 */
	public Plane(Vector3D origin, Vector3D normal) {
		this.origin = origin;
		this.normal = VectorMath.normalise(normal);
	}
	
	/**
	 * Calculate the distance of a point from the plane (+ve in direction of normal)
	 * @param pointPosition Point to calculate to
	 * @return Distance from the plane (+ve in direction of normal)
	 */
	public float distanceFromPlane(Vector3D pointPosition) {
		return VectorMath.dot(normal, VectorMath.subtract(pointPosition, origin));
	}

	public Vector3D getNormal() {
		return normal;
	}

	public void setNormal(Vector3D normal) {
		this.normal = VectorMath.normalise(normal);
	}

	public Vector3D getOrigin() {
		return origin;
	}

	public void setOrigin(Vector3D origin) {
		this.origin = origin;
	}

}
