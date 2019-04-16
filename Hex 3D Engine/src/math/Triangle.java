package math;

import java.awt.Rectangle;

import view.Camera;

public class Triangle extends Polygon{

	/**
	 * Create a triangle from its three vertices
	 * @param p1
	 * @param p2
	 * @param p3
	 */
	public Triangle(Vector3D p1, Vector3D p2, Vector3D p3) {
		super(new Vector3D[]{p1,p2,p3});
	}
	
	/**
	 * @return 3D vector from vertex A to vertex B
	 */
	public Vector3D getAB() {
		return new Vector3D(vertices.get(0),vertices.get(1));
	}
	
	/**
	 * @return 3D vector from vertex A to vertex C
	 */
	public Vector3D getAC() {
		return new Vector3D(vertices.get(0),vertices.get(2));
	}
	
	/**
	 * @return 3D vector from vertex C to vertex B
	 */
	public Vector3D getCB() {
		return new Vector3D(vertices.get(2),vertices.get(1));
	}
	
	/**
	 * @return 3D vector perpendicular to CB
	 */
	public Vector3D getPerpVectorA() {
		return VectorMath.subtract(getAB(), VectorMath.project(getAB(), getCB()));
	}
	
	/**
	 * @return 3D vector perpendicular to AC
	 */
	public Vector3D getPerpVectorB() {
		return VectorMath.add(VectorMath.negate(getAB()), VectorMath.project(getAB(), getAC()));
	}
	
	/**
	 * @return The triangle's plane
	 */
	public Plane getPlane() {
		return new Plane(vertices.get(0), getAB(), getAC());
	}
	
	/**
	 * Calculate the rectangular bounds of a triangle when projected to the camera plane
	 * @param camera Camera to project onto
	 * @return The rectangular bound of this triangle (Coords centred on camera centre)
	 */
	public Rectangle getProjectionBounds(Camera camera) {
		
		Vector3D camPosition = camera.getPosition();
		float camOrigin = camera.getOriginDistance();
		
		//Distances to vertices parallel to camera direction
		float az = VectorMath.dot(VectorMath.subtract(vertices.get(0), camPosition), camera.getDirection());
		float bz = VectorMath.dot(VectorMath.subtract(vertices.get(1), camPosition), camera.getDirection());
		float cz = VectorMath.dot(VectorMath.subtract(vertices.get(2), camPosition), camera.getDirection());
		
		//Projected distance of vertices in the camera's right direction
		float ax = VectorMath.dot(VectorMath.subtract(vertices.get(0), camPosition), camera.getRightVector()) * camOrigin / az;
		float bx = VectorMath.dot(VectorMath.subtract(vertices.get(1), camPosition), camera.getRightVector()) * camOrigin / bz;
		float cx = VectorMath.dot(VectorMath.subtract(vertices.get(2), camPosition), camera.getRightVector()) * camOrigin / cz;
		
		//Projected distance of vertices in the camera's up direction
		float ay = VectorMath.dot(VectorMath.subtract(vertices.get(0), camPosition), camera.getUpVector()) * camOrigin / az;
		float by = VectorMath.dot(VectorMath.subtract(vertices.get(1), camPosition), camera.getUpVector()) * camOrigin / bz;
		float cy = VectorMath.dot(VectorMath.subtract(vertices.get(2), camPosition), camera.getUpVector()) * camOrigin / cz;
		
		//Rectangular bounds dimensions
		float maxY = Math.max(ay, Math.max(by, cy));
		float minY = Math.min(ay, Math.min(by, cy));
		float maxX = Math.max(ax, Math.max(bx, cx));
		float minX = Math.min(ax, Math.min(bx, cx));
		
		return new Rectangle((int)minX, (int)maxY, (int)(maxX - minX), (int)(maxY - minY));
	}

}
