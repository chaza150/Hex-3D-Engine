package view;

import javax.swing.JFrame;

import control.DragRotateListener;
import model.Object3D;

public class MainWindow extends JFrame{
	private static final long serialVersionUID = -4744384025709235341L;
	
	GamePanel panel = new GamePanel();
	
	public MainWindow(int width, int height, Camera camera, Object3D dragObject) {
		super("Hex 3D Engine");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(width, height);
		this.setResizable(false);
		this.setContentPane(panel);
		
		DragRotateListener drl = new DragRotateListener(camera, dragObject);
		this.addMouseMotionListener(drl);
		this.addMouseListener(drl);
	}
	
	public void displayWindow() {
		this.setVisible(true);
	}
	
	public GamePanel getGamePanel() {
		return panel;
	}

}
