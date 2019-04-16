package graphics;

import java.awt.Color;

public class ZPixel {
	
	private int rgb; //Colour of pixel in integer form
	private float z; // current z-depth of pixel
	private boolean isClear; // Check for overwriting possibility regardless of z-depth
	
	/**
	 * @param red red value in rgb up to 255
	 * @param green green value in rgb up to 255
	 * @param blue blue value in rgb up to 255
	 * @param z initial z-depth
	 * @param isClear default value to start with (Whether to be overwritten)
	 */
	public ZPixel(int red, int green, int blue, float z, boolean isClear) {
		this.rgb = (red<<16)|(green<<8)|(blue);
		this.z = z;
		this.isClear = isClear;
	}
	
	/**
	 * 
	 * @param red red value in rgb up to 255
	 * @param green green value in rgb up to 255
	 * @param blue blue value in rgb up to 255
	 */
	public void setRGB(int red, int green, int blue) {
		if(red <= 255 && red >= 0 && green <= 255 && green >= 0 && blue <= 255 && blue >= 0) {
			this.rgb = (red<<16)|(green<<8)|(blue);
		} else {
			System.err.println("Pixel Error: Value greater than 255 or less than 0 given for a colour");
		}
	}
	
	/**
	 * Set RGB value to integer
	 * @param rgb integer to set rgb
	 */
	public void setRGB(int rgb) {
			this.rgb = rgb;
	}

	public void setZ(float z) {
		this.z = z;
	}
	
	public float getZ() {
		return z;
	}
	
	public int getRed() {
		return (this.rgb>>16)&0x0ff;
	}
	
	public int getGreen() {
		return (this.rgb>>8)&0x0ff;
	}
	
	public int getBlue() {
		return (this.rgb)&0x0ff;
	}
	
	/**
	 * Get current integer colour of pixel
	 * @return integer colour
	 */
	public int getRGB() {
		if(isClear) {
			return (Color.MAGENTA.getRGB());
		} else {
			return this.rgb;
		}
	}
	
	public boolean isClear() {
		return isClear;
	}
	
	public void setClear(boolean isClear) {
		this.isClear = isClear;
	}
	

}
