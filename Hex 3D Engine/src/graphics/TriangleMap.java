package graphics;

import java.util.ArrayList;

import math.Triangle;
import math.Vector3D;
import math.VectorMath;
import view.Camera;

public class TriangleMap{
	
	/**
	 * List of triangles where at one or two vertices
	 * are behind the camera's position, making them 
	 * not have an easy mapping to the camera projection plane.
	 */
	private ArrayList<Triangle> nonTrivialTris = new ArrayList<>();
	
	/**
	 * List of triangles where all vertices are in
	 * front of the camera's position and so have an
	 * easy mapping to the camera's projection plane
	 * and thus an easily computable rectangular bound.
	 */
	private ArrayList<Triangle> trivialTris = new ArrayList<>();
	
	Camera camera; // Camera to judge triviality
	
	/**
	 * Make a TriangleMap for use with this camera
	 * @param camera Camera to use for triviality testing
	 */
	public TriangleMap(Camera camera) {
		this.camera = camera;
	}
	
	/**
	 * Add a triangle to the TriangleMap.
	 * Filter the triangle into the correct
	 * list for later processing on output.
	 * Dispose if behind camera.
	 * @param triangle Triangle to add to the map
	 */
	public void add(Triangle triangle) {
		//Check if the triangle vertices are in front of the camera
		if(!isInFront(triangle.getVertex(0))) {
			if(!isInFront(triangle.getVertex(1)) && !isInFront(triangle.getVertex(2))) {
				//if all behind the camera, then dispose
				return;
			} else {
				//if one or two behind camera, non-trivial
				nonTrivialTris.add(triangle);
			}
		} else {
			//if all three in front of camera, trivial
			trivialTris.add(triangle);
		}
	}
	
	/**
	 * Check if a vertex is currently in front of the camera
	 * @param vertex Vertex to check 
	 * @return True if strictly in front, False if behind
	 */
	private boolean isInFront(Vector3D vertex) {
		return VectorMath.dot(VectorMath.subtract(vertex, camera.getPosition()), camera.getDirection()) > 0;
	}
	
	/**
	 * Get all non-trivial triangles from the mapping
	 * @return List of non-trivial triangles in the mapping
	 */
	public ArrayList<Triangle> getNonTrivialTris(){
		return nonTrivialTris;
	}

	/**
	 * Get all trivial triangles from the mapping
	 * @return List of trivial triangles in the mapping
	 */
	public ArrayList<Triangle> getTrivialTris(){
		return trivialTris;
	}
}
