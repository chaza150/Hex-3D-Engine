package math;

import java.util.ArrayList;

public class Polygon {
	
	protected ArrayList<Vector3D> vertices = new ArrayList<>(); //vertices included in the polygon
	
	/**
	 * Create a polygon with set vertices from array
	 * @param vertices vertex array
	 */
	public Polygon(Vector3D[] vertices) {
		for(Vector3D vert : vertices) {
			this.vertices.add(vert);
		}
	}
	
	/**
	 * @return ArrayList of vertex positions
	 */
	public ArrayList<Vector3D> getVertices() {
		return vertices;
	}
	
	/**
	 * @param index index of vertex to retrieve
	 * @return Position of indexed vertex
	 */
	public Vector3D getVertex(int index) {
		return vertices.get(index);
	}

}
