package math;

public class Vector3D {
	
	private float x; //X-Component
	private float y; //Y-Component
	private float z; //Z-Component
	
	/**
	 * Create a vector from separate components
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vector3D(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Create a vector going from one point to another
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vector3D(Vector3D p1, Vector3D p2) {
		 Vector3D newVect = VectorMath.subtract(p1, p2);
		 this.x = newVect.getX();
		 this.y = newVect.getY();
		 this.z = newVect.getZ();
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
	
	/**
	 * Rotate the vector around an arbitrary vector axis
	 * @param axis Vector axis around which to rotate
	 * @param angle Angle in radians by which to rotate
	 */
	public void rotate(Vector3D axis, float angle) {
		Vector3D v = VectorMath.rotate(this, VectorMath.normalise(axis), angle);
		this.x = v.getX();
		this.y = v.getY();
		this.z = v.getZ();
	}
	
	/**
	 * Extend this vector by another
	 * @param addVector Vector to add
	 */
	public void add(Vector3D addVector) {
		this.x += addVector.getX();
		this.y += addVector.getY();
		this.z += addVector.getZ();
	}
	
	/**
	 * Reverse the components of this vector
	 */
	public void reverse() {
		this.x = -x;
		this.y = -y;
		this.z = -z;
	}
	
	/**
	 * @return Length (magnitude) of this vector
	 */
	public float magnitude() {
		return (float) Math.sqrt(x*x + y*y + z*z);
	}
	
	/**
	 * Scale this vector by a factor
	 * @param factor float by which to scale this vector
	 */
	public void scale(float factor) {
		this.x *= factor;
		this.y *= factor;
		this.z *= factor;
	}
	
	/**
	 * Normalise (Set magnitude to 1) this vector
	 */
	public void normalise() {
		scale(1/(magnitude()));
	}
	
	

}
