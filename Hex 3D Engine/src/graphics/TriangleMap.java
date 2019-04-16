package graphics;

import java.util.ArrayList;

import math.Triangle;
import math.Vector3D;
import math.VectorMath;
import view.Camera;

public class TriangleMap{
	
	private ArrayList<Triangle> nonTrivialTris = new ArrayList<>();
	private ArrayList<Triangle> trivialTris = new ArrayList<>();
	
	Camera camera;
	
	public TriangleMap(Camera camera) {
		this.camera = camera;
	}
	
	public void add(Triangle triangle) {
		if(!isInFront(triangle.getVertex(0))) {
			if(!isInFront(triangle.getVertex(1)) && !isInFront(triangle.getVertex(2))) {
				System.out.println("Help");
				return;
			} else {
				nonTrivialTris.add(triangle);
			}
		} else {
			trivialTris.add(triangle);
		}
	}
	
	private boolean isInFront(Vector3D vector) {
		return VectorMath.dot(VectorMath.subtract(vector, camera.getPosition()), camera.getDirection()) > 0;
	}
	
	public ArrayList<Triangle> getNonTrivialTris(){
		return nonTrivialTris;
	}

	public ArrayList<Triangle> getTrivialTris(){
		return trivialTris;
	}
}
