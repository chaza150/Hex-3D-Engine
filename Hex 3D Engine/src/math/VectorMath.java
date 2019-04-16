package math;

public class VectorMath {
	
	/**
	 * Dot product of two vectors
	 * @param vect1
	 * @param vect2
	 * @return Dot product
	 */
	public static float dot(Vector3D vect1, Vector3D vect2) {
		return 	(vect1.getX()*vect2.getX()) + 
				(vect1.getY()*vect2.getY()) + 
				(vect1.getZ()*vect2.getZ());
	}
	
	/**
	 * Cross product of two vectors
	 * @param vect1
	 * @param vect2
	 * @return Cross product
	 */
	public static Vector3D cross(Vector3D vect1, Vector3D vect2) {
		return new Vector3D(vect1.getY()*vect2.getZ() - vect1.getZ()*vect2.getY(), 
							vect1.getZ()*vect2.getX() - vect1.getX()*vect2.getZ(),
							vect1.getX()*vect2.getY() - vect1.getY()*vect2.getX());
	}
	
	/**
	 * Calculate magnitude of a vector
	 * @param vector
	 * @return Magnitude
	 */
	public static float magnitude(Vector3D vector) {
		return (float) Math.sqrt(vector.getX()*vector.getX() + vector.getY()*vector.getY() + vector.getZ()*vector.getZ());
	}
	
	/**
	 * Scale a vector by a factor
	 * @param vector Vector to scale
	 * @param factor
	 * @return Resultant scaled vector
	 */
	public static Vector3D scale(Vector3D vector, float factor) {
		return new Vector3D(vector.getX()*factor, vector.getY()*factor, vector.getZ()*factor);
	}
	
	/**
	 * Calculate normalised vector from another
	 * @param vector
	 * @return normalised vector
	 */
	public static Vector3D normalise(Vector3D vector) {
		return scale(vector, 1/(magnitude(vector)));
	}
	
	/**
	 * Calculate reversed vector
	 * @param vector 
	 * @return reversed vector
	 */
	public static Vector3D negate(Vector3D vector) {
		return scale(vector, -1f);
	}
	
	/**
	 * Add two vectors
	 * @param vect1
	 * @param vect2
	 * @return sum vector
	 */
	public static Vector3D add(Vector3D vect1, Vector3D vect2) {
		return new Vector3D(vect1.getX()+vect2.getX(), vect1.getY()+vect2.getY(), vect1.getZ()+vect2.getZ());
	}
	
	/**
	 * Subtract one vector from another
	 * @param vect1 Vector to be subtracted from
	 * @param vect2 Vector to be subtracted
	 * @return subtracted vector
	 */
	public static Vector3D subtract(Vector3D vect1, Vector3D vect2) {
		return add(vect1, negate(vect2));
	}
	
	/**
	 * Calculate rotation of vector around an arbitrary vector axis
	 * @param vector Vector to rotate
	 * @param axis Vector axis
	 * @param angle Angle in radians to rotate by
	 * @return Rotated vector
	 */
	public static Vector3D rotate(Vector3D vector, Vector3D axis, float angle) {
		float x = vector.getX();
		float y = vector.getY();
		float z = vector.getZ();
		
		Vector3D a = normalise(axis);
		
		float ax = a.getX();
		float ay = a.getY();
		float az = a.getZ();
		
		//Cosines and Sines
		float c = (float) Math.cos(angle);
		float s = (float) Math.sin(angle);
		
		float finalX = x*(ax*ax*(1-c) + c) + y*(ax*ay*(1-c) - az*s) + z*(ax*az*(1-c) + ay*s);
		float finalY = x*(ax*ay*(1-c) + az*s) + y*(ay*ay*(1-c) + c) + z*(ay*az*(1-c) - ax*s);
		float finalZ = x*(ax*az*(1-c) - ay*s) + y*(ay*az*(1-c) + ax*s) + z*(az*az*(1-c) + c);
		
		return new Vector3D(finalX, finalY, finalZ);
	}
	
	/**
	 * Calculate the intersection of a ray (Vector & origin) and a plane
	 * @param vectorOrigin Origin of the ray
	 * @param vector Direction of ray
	 * @param plane Plane to be intercepted
	 * @return Intersection of ray and plane (null if no intersect)
	 */
	public static Intersection planeIntersection(Vector3D vectorOrigin, Vector3D vector, Plane plane) {
		Vector3D w = subtract(plane.getOrigin(), vectorOrigin);
		//Multiple of ray unit vector to reach the plane (-ve if away from plane)
		float scale = (dot(w, plane.getNormal()))/(dot(vector, plane.getNormal()));
		if(scale <= 0) {
			//Don't return an intersection if ray is away from plane
			return null;
		} else {
			//Return intersection if ray does intersect
			Vector3D incident = scale(vector, scale);
			Vector3D intersection = add(vectorOrigin, incident);
			return new Intersection(incident, intersection, plane.getNormal());
		}
	}
	
	/**
	 * Calculate the intersection of a ray (Vector & origin) and a triangle
	 * @param vectorOrigin Origin of the ray
	 * @param vector Direction of ray
	 * @param triangle Triangle to be intercepted
	 * @return Barycentric Intersection of ray and triangle (null if no intersect)
	 */
	public static BarycentricIntersection triangleIntersection(Vector3D vectorOrigin, Vector3D vector, Triangle triangle) {
		//Calculate intersect with triangle's plane
		Intersection planeIntersection = planeIntersection(vectorOrigin, vector, triangle.getPlane());
		
		//Convert intersect to barycentric intersect if intersecting the plane
		if(planeIntersection != null) {
			
			Vector3D intersect = planeIntersection.getPosition();
			Vector3D perpA = triangle.getPerpVectorA();
			
			//Calculate barycentric coordinate from A
			float a = 1 - dot(perpA, new Vector3D(triangle.getVertex(0), intersect))/dot(perpA, triangle.getAB());
			
			if(a > 1 || a < 0) {
				return null;
			} else {

				Vector3D perpB = triangle.getPerpVectorB();
				
				//Calculate barycentric coordinate from B
				float b = 1 - dot(perpB, new Vector3D(triangle.getVertex(1), intersect))/dot(perpB, negate(triangle.getAB()));
				
				if(b > 1 || b < 0) {
					return null;
				} else {

					//Calculate barycentric coordinate from C
					float c = 1 - a - b;
					
					if(c > 1 || c < 0) {
						return null;
					} else {
						
						return new BarycentricIntersection(planeIntersection.getIncident(), planeIntersection.getPosition(), planeIntersection.getNormal(), new Vector3D(a,b,c));
					}
				}
			}
		} else {
			return null;
		}
		
	}
	
	/**
	 * Calculate vector projection on a vector
	 * @param vector Vector to project
	 * @param projectionVector Vector to project onto
	 * @return projected vector
	 */
	public static Vector3D project(Vector3D vector, Vector3D projectionVector) {
		Vector3D projVector = normalise(projectionVector);
		float scale = dot(vector, projVector);
		return scale(projVector, scale);
	}
	
	/**
	 * Calculate vector projection onto a plane
	 * @param vector Vector to project
	 * @param plane Plane to project onto
	 * @return projected vector
	 */
	public static Vector3D projectOntoPlane(Vector3D vector, Plane plane) {
		return VectorMath.subtract(vector, VectorMath.project(vector, plane.getNormal()));
	}
	
	/**
	 * Calculate right vector parallel to the ground plane (x,y)
	 * @param forwardVector Direction to derive right from
	 * @return unit vector to the right
	 */
	public static Vector3D getRightVector(Vector3D forwardVector) {
		Vector3D zUp = new Vector3D(0,0,1);
		return VectorMath.normalise(VectorMath.rotate(VectorMath.projectOntoPlane(forwardVector, new Plane(new Vector3D(0,0,0), zUp)), zUp, (float) Math.PI/2));
	}
	
	/**
	 * Calculate up vector from a forward direction
	 * @param forwardVector Direction to derive up from
	 * @return unit vector up
	 */
	public static Vector3D getUpVector(Vector3D forwardVector, Vector3D rightVector) {
		return VectorMath.normalise(VectorMath.cross(forwardVector, rightVector));
	}

}
