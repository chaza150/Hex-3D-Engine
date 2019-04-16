package graphics;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

public class View {
	
	private BufferManager bufferManager; //Manager for view's buffers
	private int originDistance;	// Effective distance of camera projection plane from position
	
	/**
	 * Creates a view with a number of buffers to be written to,
	 * a different ray buffer and output image resolution and
	 * a variable field of view.
	 * @param bufferDepth Number of buffers needed
	 * @param bufferResolution Resolution of rays to be projected
	 * @param outputResolution Resolution of output image to be produced
	 * @param fieldOfView Angle in radians for total field of view
	 */
	public View(int bufferDepth, Dimension bufferResolution, Dimension outputResolution, float fieldOfView) {
		this.originDistance = (int) (bufferResolution.getWidth()/(2*Math.tan(fieldOfView/2)));
		this.bufferManager = new BufferManager(bufferDepth, bufferResolution.width, bufferResolution.height, outputResolution.width, outputResolution.height);
	}
	
	/**
	 * Creates a view with a number of buffers to be written to,
	 * the same buffer and output resolutions and variable field of view.
	 * @param bufferDepth Number of buffers needed
	 * @param resolution resolution of ray buffer and output image
	 * @param fieldOfView Angle in radians for total field of view
	 */
	public View(int bufferDepth, Dimension resolution, int fieldOfView) {
		this.originDistance = (int) (resolution.getWidth()/(2*Math.tan(fieldOfView/2)));
		this.bufferManager = new BufferManager(bufferDepth, resolution.width, resolution.height, resolution.width, resolution.height);
	}
	
	/**
	 * @return The output image for the current view
	 */
	public BufferedImage getOutputImage() {
		return bufferManager.outputCurrentImage();
	}
	
	/**
	 * @return Effective distance of camera projection plane from position
	 */
	public int getOriginDistance() {
		return originDistance;
	}
	
	/**
	 * Update buffer manager's pointer
	 */
	public void update() {
		bufferManager.incrementCurrentPointer();
	}
	
	public int getBufferWidth() {
		return bufferManager.getBufferWidth();
	}
	
	public int getBufferHeight() {
		return bufferManager.getBufferHeight();
	}
	
	/**
	 * @return BufferManager for this view
	 */
	public BufferManager getBufferManager() {
		return bufferManager;
	}
	
	
	
	
	
	

}
