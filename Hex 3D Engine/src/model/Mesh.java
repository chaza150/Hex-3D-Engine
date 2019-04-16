package model;

import java.util.ArrayList;

import math.Triangle;
import math.Vector3D;
import math.VectorMath;

public class Mesh {
	
	private ArrayList<Vector3D> vertices = new ArrayList<>(); //List of all vertices in mesh
	private ArrayList<Triangle> triangles = new ArrayList<>(); //List of all triangles in mesh
	
	/**
	 * Add a vertex to the mesh
	 * @param vertex
	 * @return true if successfully added to mesh
	 */
	public boolean addVertex(Vector3D vertex) {
		return vertices.add(vertex);
	}
	
	/**
	 * Add a triangle to the mesh by way of the three vertices
	 * @param v1
	 * @param v2
	 * @param v3
	 * @return True if added successfully to mesh
	 */
	public boolean addTriangle(Vector3D v1, Vector3D v2, Vector3D v3) {
		vertices.add(v1);
		vertices.add(v2);
		vertices.add(v3);
		return addTriangle(vertices.size()-3,vertices.size()-2,vertices.size()-1);
	}
	
	/**
	 * Add a triangle to the mesh by addressing the vertex indices of its vertices
	 * @param v1 index of vertex 1
	 * @param v2 index of vertex 2
	 * @param v3 index of vertex 3
	 * @return True if added successfully to mesh
	 */
	public boolean addTriangle(int v1, int v2, int v3) {
		return triangles.add(new Triangle(vertices.get(v1),vertices.get(v2),vertices.get(v3)));
	}
	
	/**
	 * Rotate entire mesh
	 * @param origin Origin to revolve around
	 * @param axis Unit vector axis 
	 * @param angle Angle in radians to rotate by
	 */
	public void rotate(Vector3D origin, Vector3D axis, float angle) {
		for(Vector3D vertex : vertices) {
			Vector3D ov = VectorMath.subtract(vertex,  origin);
			ov = VectorMath.rotate(ov, axis, angle);
			ov = VectorMath.add(ov, origin);
			vertex.setX(ov.getX());
			vertex.setY(ov.getY());
			vertex.setZ(ov.getZ());
		}
	}
	
	/**
	 * Get all triangles in the mesh
	 * @return list of mesh triangles
	 */
	public ArrayList<Triangle> getTriangles(){
		return triangles;
	}

	/**
	 * Translate entire mesh
	 * @param moveVector Vector to translate through
	 */
	public void translate(Vector3D moveVector) {
		for(Vector3D vertex : vertices) {
			vertex.setX(vertex.getX() + moveVector.getX());
			vertex.setY(vertex.getY() + moveVector.getY());
			vertex.setZ(vertex.getZ() + moveVector.getZ());
		}
	}

}
