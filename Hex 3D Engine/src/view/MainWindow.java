package view;

import javax.swing.JFrame;

import control.DragRotateListener;
import model.Object3D;

public class MainWindow extends JFrame{
	private static final long serialVersionUID = -4744384025709235341L;
	
	GamePanel panel = new GamePanel(); //Graphics panel for rendering
	
	/**
	 * Create a new window with a main camera and draggable object to control
	 * @param width Width of the new window
	 * @param height Height of the new window
	 * @param camera Camera to act as the main camera to the scene for dragging purposes
	 * @param dragObject Object to be made rotatable by dragging through the window
	 */
	public MainWindow(int width, int height, Camera camera, Object3D dragObject) {
		super("Hex 3D Engine");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(width, height);
		this.setResizable(false);
		this.setContentPane(panel);
		
		//Add mouse dragging/object rotate listeners
		DragRotateListener drl = new DragRotateListener(camera, dragObject);
		this.addMouseMotionListener(drl);
		this.addMouseListener(drl);
	}
	
	/**
	 * Show the window
	 */
	public void displayWindow() {
		this.setVisible(true);
	}
	
	// Get the content panel of this window
	public GamePanel getGamePanel() {
		return panel;
	}

}
