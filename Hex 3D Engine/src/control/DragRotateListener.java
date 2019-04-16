package control;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import math.Vector3D;
import math.VectorMath;
import model.Object3D;
import view.Camera;

public class DragRotateListener implements MouseMotionListener, MouseListener {
	
	private Camera camera;	// Main scene Camera to judge rotation
	private Object3D dragObject;	// Object to be rotated on drag
	private Point origin = null;	// Coordinate acting as s=drag start point
	private boolean isDragged;		// Check for whether currently dragging

	public DragRotateListener(Camera camera, Object3D dragObject) {
		this.camera = camera;
		this.dragObject = dragObject;
	}

	//On drag: Rotate the object about an axis defined by drag direction
	@Override
	public void mouseDragged(MouseEvent e) {
		
		//If not already dragging, set origin
		if(!isDragged) {
			origin = new Point(e.getX(), e.getY());
			isDragged = true;
		} else {
			//If dragging, calculate difference in coords & 3D space
			int deltaX = e.getX() - (int) origin.getX();
			int deltaY = -(e.getY() - (int) origin.getY());
			Vector3D upDrag = VectorMath.scale(camera.getUpVector(), deltaY);
			Vector3D rightDrag = VectorMath.scale(camera.getRightVector(), deltaX);
			Vector3D dragVector = VectorMath.add(rightDrag, upDrag);
			
			//Calculate rotation axis perpendicular to drag
			Vector3D rotateAxisVector = VectorMath.rotate(dragVector, camera.getDirection(), (float)-Math.PI/2);
			float dragDistance = (float) Math.sqrt(deltaX*deltaX + deltaY*deltaY)/100;
			if(dragDistance != 0) {
				//Rotate the object
				dragObject.rotate(rotateAxisVector, dragDistance);
			}
			isDragged = false;
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	//On release: Disengage dragging
	@Override
	public void mouseReleased(MouseEvent e) {
		isDragged = false;
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
