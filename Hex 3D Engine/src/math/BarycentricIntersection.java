package math;

public class BarycentricIntersection extends Intersection{
	
	//Barycentric coordinates (a + b + c = 1 & correspond to triangle vertices)
	private Vector3D barycentricCoords;

	/**
	 * Create a Barycentric (Triangle-based) intersection
	 * @param incident Incident ray vector
	 * @param position Vector position of intersection point
	 * @param normal Normal of triangle in question
	 * @param barycentricCoords //Barycentric coordinates of intersect
	 */
	public BarycentricIntersection(Vector3D incident, Vector3D position, Vector3D normal, Vector3D barycentricCoords) {
		super(incident, position, normal);
		this.barycentricCoords = barycentricCoords;
	}
	
	/**
	 * @return Barycentric coordinates in 3D vector form
	 */
	public Vector3D getBarycentricCoords() {
		return barycentricCoords;
	}
}
