package model;

import java.util.ArrayList;

import math.Triangle;
import math.Vector3D;
import math.VectorMath;

public class Mesh {
	
	private ArrayList<Vector3D> vertices = new ArrayList<>();
	private ArrayList<Triangle> triangles = new ArrayList<>();
	
	public boolean addVertex(Vector3D vertex) {
		return vertices.add(vertex);
	}
	
	public boolean addTriangle(Vector3D v1, Vector3D v2, Vector3D v3) {
		return triangles.add(new Triangle(v1,v2,v3));
	}
	
	public boolean addTriangle(int v1, int v2, int v3) {
		return triangles.add(new Triangle(vertices.get(v1),vertices.get(v2),vertices.get(v3)));
	}
	
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
	
	public ArrayList<Triangle> getTriangles(){
		return triangles;
	}

	public void translate(Vector3D moveVector) {
		for(Vector3D vertex : vertices) {
			vertex.setX(vertex.getX() + moveVector.getX());
			vertex.setY(vertex.getY() + moveVector.getY());
			vertex.setZ(vertex.getZ() + moveVector.getZ());
		}
	}

}
