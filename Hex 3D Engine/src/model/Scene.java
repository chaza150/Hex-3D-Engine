package model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import math.Triangle;
import view.Camera;

public class Scene {
	
	private ArrayList<Object3D> objects = new ArrayList<>();
	
	private Camera mainCamera = null;
	
	public void setMainCamera(Camera camera) {
		this.mainCamera = camera;
	}
	
	public void addObject(Object3D object) {
		objects.add(object);
	}
	
	public ArrayList<Triangle> getTriangles(){
		ArrayList<Triangle> sceneTriangles = new ArrayList<>();
		for(Object3D object : objects) {
			sceneTriangles.addAll(object.getTriangles());
		}
		return sceneTriangles;
	}
	
	public BufferedImage getSceneImage() {
		mainCamera.drawTriangles(getTriangles());
		return mainCamera.outputImage();
	}
	
	

}
