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
	
	private View view;
	
	public Camera(Vector3D position, Vector3D direction, int bufferDepth, Dimension bufferResolution, Dimension outputResolution, float fieldOfView) {
		super(position, direction);
		this.view = new View(bufferDepth, bufferResolution, outputResolution, fieldOfView);
	}
	
	public Vector3D getOriginVector() {
		return VectorMath.scale(this.direction, view.getOriginDistance());
	}
	
	public Vector3D getRayX(int x) {
		return VectorMath.scale(rightVector, x);
	}
	
	public Vector3D getRayY(int y) {
		return VectorMath.scale(upVector, y);
	}
	
	public Vector3D getRay(int x, int y) {
		return VectorMath.add(getOriginVector(), VectorMath.add(getRayX(x), getRayY(y)));
	}
	
	public void drawTriangles(ArrayList<Triangle> triangles) {
		BufferManager bufferManager = view.getBufferManager();
		TriangleMap triMap = new TriangleMap(this);
		
		for(Triangle triangle : triangles) {
			triMap.add(triangle);
		}
		
		for(int i = 0; i < view.getBufferWidth(); i++) {
			for(int j = 0; j < view.getBufferHeight(); j++) {
				Vector3D ray = getRay(i-(view.getBufferWidth()/2),(view.getBufferHeight()/2)-j);
				for(Triangle triangle : triMap.getNonTrivialTris()) {
					BarycentricIntersection intersection = VectorMath.triangleIntersection(this.position, ray, triangle);
					if(intersection != null) {
						Vector3D bCoords = intersection.getBarycentricCoords();
						bufferManager.drawToBuffer(i, j, intersection.getIncident().magnitude(), (new Color(bCoords.getX(), bCoords.getY(), bCoords.getZ())).getRGB());
					}
				}
			}
		}
		
		for(Triangle triangle : triMap.getTrivialTris()) {
			Rectangle bounds = triangle.getProjectionBounds(this);
			bounds.setLocation((int)bounds.getX(), (int)((view.getBufferHeight()/2)-bounds.getY()));
			for(int i = (int) Math.max(0, bounds.getMinX() - 1 + view.getBufferWidth()/2 ); i < view.getBufferWidth() && i < bounds.getMaxX() + (view.getBufferWidth()/2) + 1 ; i++) {
				for(int j = (int) Math.max(0, bounds.getMinY() - 1); j < view.getBufferHeight() && j < bounds.getMaxY() + 1 ; j++) {
					Vector3D ray = getRay(i-(view.getBufferWidth()/2),(view.getBufferHeight()/2)-j);
					BarycentricIntersection intersection = VectorMath.triangleIntersection(this.position, ray, triangle);
					if(intersection != null) {
						Vector3D bCoords = intersection.getBarycentricCoords();
						bufferManager.drawToBuffer(i, j, intersection.getIncident().magnitude(), (new Color(bCoords.getX(), bCoords.getY(), bCoords.getZ())).getRGB());
					}
				}
			}
		}
	}
	
	public BufferedImage outputImage() {
		view.update();
		return view.getBufferManager().outputCurrentImage();
	}
	
	public int getOriginDistance() {
		return view.getOriginDistance();
	}

}
