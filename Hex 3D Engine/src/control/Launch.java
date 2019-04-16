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
		
		String fileName = "susanne.obj";		//Filename of .obj (Only supported file type) file
		ModelReader reader = new ModelReader();	//Reader for the .obj file
		Mesh importMesh = reader.importMesh(fileName);	//Convert .obj file to a mesh
		
		//Master scene
		Scene scene = new Scene();
		
		//Add Main Camera
		Camera camera = new Camera(new Vector3D(-6,0,0), new Vector3D(1,0,0), 2, new Dimension(1200,700), new Dimension(1200,700), (float) (Math.PI/1.8));
		
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
		MainWindow window = new MainWindow(1210, 735, camera, object);
		
		window.displayWindow();

		while(true) {
			//Loop over re-rendering scene (Currently no frame-rate stabilisation
			window.getGamePanel().setImage(scene.getSceneImage());
			window.repaint();
			
		}

	}

}
