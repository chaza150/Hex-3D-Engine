package graphics;

import java.awt.image.BufferedImage;

public class BufferManager {
	
	private ZBuffer buffers[];			// list of ZBuffers
	private int bufferResolutionWidth;	// Width of ray buffers
	private int bufferResolutionHeight;	// Height of ray buffers
	
	private int outputResolutionWidth;	// Width of output image
	private int outputResolutionHeight; // Height of output image
	
	private int currentBufferPointer;	// Current buffer number
	
	public BufferManager(int bufferDepth, int bufferResolutionWidth, int bufferResolutionHeight, int outputResolutionWidth, int outputResolutionHeight) {
		if(bufferDepth >= 1) {
			this.bufferResolutionWidth = bufferResolutionWidth;
			this.bufferResolutionHeight = bufferResolutionHeight;
			this.outputResolutionWidth = outputResolutionWidth;
			this.outputResolutionHeight = outputResolutionHeight;
			
			//Create buffers as per defaults
			buffers = new ZBuffer[bufferDepth];
			for(int i = 0; i < bufferDepth; i++) {
				buffers[i] = new ZBuffer(bufferResolutionWidth, bufferResolutionHeight);
			}
			
		} else {
			System.err.println("Buffer Error: Creating a buffer with depth less than zero.");
		}
	}
	
	/**
	 * 
	 * @param buffer Buffer to transform to an image
	 * @return Flattened Image of buffer
	 */
	public BufferedImage outputImage(ZBuffer buffer) {
		BufferedImage bufferImage = new BufferedImage(outputResolutionWidth, outputResolutionHeight, BufferedImage.TYPE_INT_RGB);
		
		//Iterate through buffer, map to image
		for(int i = 0; i < outputResolutionWidth; i++) {
			for(int j = 0; j < outputResolutionHeight; j++) {
				bufferImage.setRGB(i, j, buffer.getPixel((i * bufferResolutionWidth)/outputResolutionWidth, (j * bufferResolutionHeight)/outputResolutionHeight).getRGB());
			}
		}
		
		return bufferImage;
	}
	
	/**
	 *  Get the current output buffer
	 * @return current outputable buffer
	 */
	public ZBuffer getCurrentBuffer() {
		return buffers[currentBufferPointer];
	}
	
	/**
	 *  Flatten the current buffer to an output image
	 * @return outputable BufferedImage
	 */
	public BufferedImage outputCurrentImage() {
		return outputImage(buffers[currentBufferPointer]);
	}
	
	/**
	 * Get the ZBuffer to be drawn to
	 * @return current drawable buffer
	 */
	public ZBuffer getDrawingBuffer() {
		return buffers[(currentBufferPointer + 1) % buffers.length];
	}
	
	/**
	 * Move current buffer to the next buffer in line
	 */
	public void incrementCurrentPointer() {
		buffers[currentBufferPointer].clear();
		currentBufferPointer = (currentBufferPointer + 1) % buffers.length;
	}
	
	public int getBufferWidth() {
		return bufferResolutionWidth;
	}
	
	public int getBufferHeight() {
		return bufferResolutionHeight;
	}
	
	/**
	 *  Draw directly to the drawable buffer
	 * @param x
	 * @param y
	 * @param z
	 * @param rgb color integer
	 */
	public void drawToBuffer(int x, int y, float z, int rgb) {
		getDrawingBuffer().setZPixel(x, y, rgb, z);
	}
	
	

}
