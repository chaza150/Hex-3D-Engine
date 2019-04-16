package math;

public class Intersection {
	
	private Vector3D incident; // Incident ray vector
	private Vector3D position; // Vector position of plane intersect
	private Vector3D normal; // Normal to the plane being intersected
	
	/**
	 * Create a standard intersection
	 * @param incident Incident ray vector
	 * @param position Vector position of intersection point
	 * @param normal Normal of plane in question
	 */
	public Intersection(Vector3D incident, Vector3D position, Vector3D normal) {
		this.incident = incident;
		this.normal = normal;
		this.position = position;
	}
	
	public Vector3D getIncident() {
		return incident;
	}

	public void setIncident(Vector3D incident) {
		this.incident = incident;
	}

	public Vector3D getPosition() {
		return position;
	}

	public void setPosition(Vector3D position) {
		this.position = position;
	}

	public Vector3D getNormal() {
		return normal;
	}

	public void setNormal(Vector3D normal) {
		this.normal = normal;
	}

}
