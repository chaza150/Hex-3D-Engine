package model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import math.Triangle;
import view.Camera;

public class Scene {
	
	private ArrayList<Object3D> objects = new ArrayList<>(); // List of no-parent objects
	
	private Camera mainCamera = null; //Main scene camera
	
	/**
	 * Set the camera by which the scene is rendered
	 * @param camera New main camera
	 */
	public void setMainCamera(Camera camera) {
		this.mainCamera = camera;
	}
	
	/**
	 * Add object to the scene
	 * @param object Object to add
	 */
	public void addObject(Object3D object) {
		objects.add(object);
	}
	
	/**
	 * Retrieve all triangles in the scene
	 * @return list of all scene triangles
	 */
	public ArrayList<Triangle> getTriangles(){
		ArrayList<Triangle> sceneTriangles = new ArrayList<>();
		for(Object3D object : objects) {
			sceneTriangles.addAll(object.getTriangles());
		}
		return sceneTriangles;
	}
	
	/**
	 * Render the scene through the scene camera
	 * @return Rendered BufferedImage of scene
	 */
	public BufferedImage getSceneImage() {
		mainCamera.drawTriangles(getTriangles());
		return mainCamera.outputImage();
	}
	
	

}
