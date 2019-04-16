package view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1860979716621182121L;
	
	BufferedImage sceneImage = new BufferedImage(20,20,1); //Default (PreRendering) placeholder image
	
	/**
	 * Set the current image to be painted to panel
	 * @param sceneImage Image to be displayed
	 */
	public void setImage(BufferedImage sceneImage) {
		this.sceneImage = sceneImage;
	}
	
	//Overwrite for painting the panel,
	//draws image and rotate instructions
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(sceneImage, 0, 0, null);
		g.drawString("Click and drag to rotate", 20, 40);
	}
	
	

}
