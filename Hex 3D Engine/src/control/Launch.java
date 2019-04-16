package control;

import java.awt.Dimension;

import math.Vector3D;
import math.VectorMath;
import model.Mesh;
import model.Object3D;
import model.Scene;
import view.Camera;
import view.MainWindow;

public class Launch {

	public static void main(String[] args) {
		
		//Low resolution mode to help with testing performance (Reduces ray tracing load)
		boolean lowResMode = true;
		
		String fileName = "susanne.obj";		//Filename of .obj (Only supported file type) file
		ModelReader reader = new ModelReader();		//Reader for the .obj file
		Mesh importMesh = reader.importMesh(fileName);		//Convert .obj file to a mesh
		
		//Master scene
		Scene scene = new Scene();
		
		//Add Main Camera with ray resolution dependent on low resolution mode on/off
		Camera camera;
		if(!lowResMode) {
			camera = new Camera(new Vector3D(-5,0,0), new Vector3D(1,0,0), 2, new Dimension(800,550), new Dimension(800,550), (float) (Math.PI/2));
		} else {//(600,413)
			camera = new Camera(new Vector3D(-5,0,0), new Vector3D(1,0,0), 2, new Dimension(600,413), new Dimension(800,550), (float) (Math.PI/2));
		}
		
		scene.addObject(camera);
		scene.setMainCamera(camera);
		
		//Create the object supporting the imported mesh
		Object3D object = new Object3D(new Vector3D(0,0,0), new Vector3D(1,0,0));
		
		object.setMesh(importMesh);
		
		//Transform from Blender coordinates
		object.rotate(VectorMath.normalise(new Vector3D(1,0,0)), (float) (Math.PI/2));
		object.rotate(VectorMath.normalise(new Vector3D(0,0,1)), (float) (-Math.PI/2));
		
		// Add object to scene
		scene.addObject(object);
		
		//Setup and show window
		MainWindow window = new MainWindow(810, 585, camera, object);
		
		window.displayWindow();

		while(true) {
			//Loop over re-rendering scene (Currently no frame-rate stabilisation
			window.getGamePanel().setImage(scene.getSceneImage());
			window.repaint();
			
		}

	}

}
