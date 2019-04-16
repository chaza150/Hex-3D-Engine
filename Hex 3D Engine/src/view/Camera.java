package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import graphics.BufferManager;
import graphics.TriangleMap;
import graphics.View;
import math.BarycentricIntersection;
import math.Triangle;
import math.Vector3D;
import math.VectorMath;
import model.Object3D;

public class Camera extends Object3D {
	
	private View view; // View of the camera to define interface between physical camera and buffer managers
	
	/**
	 * Create a Camera Object
	 * @param position Position vector of camera
	 * @param direction Vector direction of camera
	 * @param bufferDepth Number of buffers to deal with in manager
	 * @param bufferResolution Dimensions of ray resolution
	 * @param outputResolution Dimensions of output image
	 * @param fieldOfView Angle in radians for field of view
	 */
	public Camera(Vector3D position, Vector3D direction, int bufferDepth, Dimension bufferResolution, Dimension outputResolution, float fieldOfView) {
		super(position, direction);
		this.view = new View(bufferDepth, bufferResolution, outputResolution, fieldOfView);
	}
	
	/**
	 * Calculate the vector from origin to the camera plane
	 * @return origin vector of camera
	 */
	public Vector3D getOriginVector() {
		return VectorMath.scale(this.direction, view.getOriginDistance());
	}
	
	/**
	 * Calculate the ray vector x component
	 * @param x x-index of new ray
	 * @return ray vector x component
	 */
	public Vector3D getRayX(int x) {
		return VectorMath.scale(rightVector, x);
	}
	
	/**
	 * Calculate the ray vector y component
	 * @param y y-index of new ray
	 * @return ray vector y component
	 */
	public Vector3D getRayY(int y) {
		return VectorMath.scale(upVector, y);
	}
	
	/**
	 * Calculate the ray vector going from origin to an indexed position on the ZBuffer
	 * @param x x-index on zbuffer
	 * @param y y-index on zbuffer
	 * @return Total ray vector
	 */
	public Vector3D getRay(int x, int y) {
		return VectorMath.add(getOriginVector(), VectorMath.add(getRayX(x), getRayY(y)));
	}
	
	/**
	 * Draw a list of triangles on this camera's ZBuffer
	 * @param triangles
	 */
	public void drawTriangles(ArrayList<Triangle> triangles) {
		BufferManager bufferManager = view.getBufferManager();
		TriangleMap triMap = new TriangleMap(this);
		
		//Filter out the triangles into relevant sections using a TriangleMap
		for(Triangle triangle : triangles) {
			triMap.add(triangle);
		}
		
		//For non-trivial/non-mappable iterate over whole ray buffer space
		for(int i = 0; i < view.getBufferWidth(); i++) {
			for(int j = 0; j < view.getBufferHeight(); j++) {
				Vector3D ray = getRay(i-(view.getBufferWidth()/2),(view.getBufferHeight()/2)-j);
				//For each ray, check intersections with all nontrivial triangles
				for(Triangle triangle : triMap.getNonTrivialTris()) {
					BarycentricIntersection intersection = VectorMath.triangleIntersection(this.position, ray, triangle);
					if(intersection != null) {
						//Draw the ray intersection to a ZBuffer with colour depending on barycentric coordinates
						Vector3D bCoords = intersection.getBarycentricCoords();
						bufferManager.drawToBuffer(i, j, intersection.getIncident().magnitude(), (new Color(bCoords.getX(), bCoords.getY(), bCoords.getZ())).getRGB());
					}
				}
			}
		}
		
		//For trivial triangles, iterate only over rays mapping to rectangular projected bounds on camera plane
		for(Triangle triangle : triMap.getTrivialTris()) {
			//Calculate projection bounds
			Rectangle bounds = triangle.getProjectionBounds(this);
			bounds.setLocation((int)bounds.getX(), (int)((view.getBufferHeight()/2)-bounds.getY()));
			for(int i = (int) Math.max(0, bounds.getMinX() - 1 + view.getBufferWidth()/2 ); i < view.getBufferWidth() && i < bounds.getMaxX() + (view.getBufferWidth()/2) + 1 ; i++) {
				for(int j = (int) Math.max(0, bounds.getMinY() - 1); j < view.getBufferHeight() && j < bounds.getMaxY() + 1 ; j++) {
					//Calculate ray and intersection
					Vector3D ray = getRay(i-(view.getBufferWidth()/2),(view.getBufferHeight()/2)-j);
					BarycentricIntersection intersection = VectorMath.triangleIntersection(this.position, ray, triangle);
					if(intersection != null) {
						//Draw the ray intersection to a ZBuffer with colour depending on barycentric coordinates
						Vector3D bCoords = intersection.getBarycentricCoords();
						bufferManager.drawToBuffer(i, j, intersection.getIncident().magnitude(), (new Color(bCoords.getX(), bCoords.getY(), bCoords.getZ())).getRGB());
					}
				}
			}
		}
	}
	
	/**
	 * Fabricate output image of camera
	 * @return Compiled output Buffered Image
	 */
	public BufferedImage outputImage() {
		view.update();
		return view.getBufferManager().outputCurrentImage();
	}
	
	//Retrieve origin distance of view 
	public int getOriginDistance() {
		return view.getOriginDistance();
	}

}
