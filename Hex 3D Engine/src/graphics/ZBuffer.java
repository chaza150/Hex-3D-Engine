package graphics;

public class ZBuffer {
	
	ZPixel pixels[][];	//ZPixel array representing screen divisions
	
	/**
	 * Create a ZBuffer with instantiated pixels
	 * @param bufferWidth Width of ZBuffer in ZPixels
	 * @param bufferHeight Height of ZBuffer in ZPixels
	 */
	public ZBuffer(int bufferWidth, int bufferHeight) {
		pixels = new ZPixel[bufferWidth][bufferHeight];
		
		//Instantiate the pixel array
		for(int i = 0; i < bufferWidth; i++) {
			for(int j = 0; j < bufferHeight; j++) {
				pixels[i][j] = new ZPixel(0,0,0,0,true);
			}
		}
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return ZPixel at (x,y)
	 */
	public ZPixel getPixel(int x, int y) {
		return pixels[x][y];
	}
	
	/**
	 * Set ZPixel value at (x,y), not overwriting pixels with lower z-values
	 * @param x x-index of pixel to set
	 * @param y y-index of pixel to set
	 * @param rgb colour of pixel to set to
	 * @param z z-depth of pixel to be set for overwrite checking
	 */
	public void setZPixel(int x, int y, int rgb, float z) {
		if(z <= pixels[x][y].getZ() || pixels[x][y].isClear()) {
			pixels[x][y].setRGB(rgb);
			pixels[x][y].setZ(z);
			pixels[x][y].setClear(false);
		}
	}
	
	/**
	 * Set all ZPixels in buffer as clear pixels
	 */
	public void clear() {
		for(int i = 0; i < pixels.length; i++) {
			for(int j = 0; j < pixels[0].length; j++) {
				pixels[i][j].setClear(true);
			}
		}
	}
}
