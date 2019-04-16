package model;

import java.util.ArrayList;

import math.Triangle;
import math.Vector3D;
import math.VectorMath;

public class Object3D {
	
	protected Vector3D position; //Absolute Position
	protected Vector3D direction; //Absolute Direction
	protected Vector3D rightVector; //Absolute Direction of right
	protected Vector3D upVector; //Absolute Direction of up
	
	private Object3D parent = null; //Parent of object
	private ArrayList<Object3D> children = new ArrayList<>(); //Children objects
	
	private Mesh mesh = null; //Graphics mesh of object
	
	/**
	 * Create a 3D object with no parent
	 * @param position Global position in scene
	 * @param direction Global Direction in scene
	 */
	public Object3D(Vector3D position, Vector3D direction) {
		this.direction = VectorMath.normalise(direction);
		this.rightVector = VectorMath.getRightVector(this.direction);
		this.upVector = VectorMath.getUpVector(this.direction, this.rightVector);
		this.position = position;
	}
	
	/**
	 * Create a 3D object with a parent
	 * @param parent Parent object
	 * @param position Global position in scene
	 * @param direction Global Direction in scene
	 */
	public Object3D(Object3D parent, Vector3D position, Vector3D direction) {
		this.parent = parent;
		this.direction = VectorMath.normalise(direction);
		this.rightVector = VectorMath.getRightVector(this.direction);
		this.upVector = VectorMath.getUpVector(this.direction, this.rightVector);
		this.position = position;
	}
	
	//Set the object's parent
	public void setParent(Object3D parent) {
		this.parent = parent;
	}
	
	/**
	 * Add a child to this object
	 * @param child Child object to add
	 * @return True if added successfully
	 */
	public boolean addChild(Object3D child) {
		return children.add(child);
	}
	
	/**
	 * Remove a child from this object
	 * @param child Child object to remove
	 * @return True if removed successfully
	 */
	public boolean removeChild(Object3D child) {
		return children.remove(child);
	}
	
	/**
	 * Set the mesh of this object
	 * @param mesh Graphics mesh to add to the object
	 */
	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
	}
	
	/**
	 * Rotate object, along with its mesh
	 * @param axis Axis to rotate around
	 * @param angle Angle in radians to rotate through
	 */
	public void rotate(Vector3D axis, float angle) {
		this.direction = VectorMath.rotate(direction, axis, angle);
		this.direction.normalise();
		this.rightVector = VectorMath.getRightVector(this.direction);
		this.upVector = VectorMath.getUpVector(this.direction, this.rightVector);
		rotateMesh(axis, angle);
		
		for(Object3D child : children) {
			child.rotate(position, axis, angle);
		}
	}
		
	/**
	 * Rotate object, along with its mesh
	 * @param origin Position of origin to rotate around
	 * @param axis Axis to rotate around
	 * @param angle Angle in radians to rotate through
	 */
	public void rotate(Vector3D origin, Vector3D axis, float angle) {
		Vector3D oa = new Vector3D(origin, position);
		oa = VectorMath.rotate(oa, axis, angle);
		this.position = VectorMath.add(oa, origin);
		this.direction = VectorMath.rotate(direction, axis, angle);
		this.direction.normalise();
		this.rightVector = VectorMath.getRightVector(this.direction);
		this.upVector = VectorMath.getUpVector(this.direction, this.rightVector);
		rotateMesh(origin, axis, angle);
	}
	
	//Rotate the object's mesh
	private void rotateMesh(Vector3D origin, Vector3D axis, float angle) {
		if(mesh != null) {
			mesh.rotate(origin, axis, angle);
		}
	}
	
	//Rotate the object's mesh
	private void rotateMesh(Vector3D axis, float angle) {
		if(mesh != null) {
			mesh.rotate(position, axis, angle);
		}
	}
	
	/**
	 * Translate the object, with its mesh
	 * @param moveVector vector to translate by
	 */
	public void translate(Vector3D moveVector) {
		this.position = VectorMath.add(position, moveVector);
		mesh.translate(moveVector);
	}
	
	/**
	 * Get triangles of this mesh and all children objects
	 * @return list of all triangles of this and children
	 */
	public ArrayList<Triangle> getTriangles(){
		ArrayList<Triangle> triangles = new ArrayList<Triangle>();
		if(mesh != null) {
			triangles.addAll(mesh.getTriangles());
		}
		for(Object3D child : children) {
			triangles.addAll(child.getTriangles());
		}
		return triangles;
	}
	
	public Vector3D getDirection() {
		return direction;
	}
	
	public Vector3D getRightVector() {
		return rightVector;
	}
	
	public Vector3D getUpVector() {
		return upVector;
	}
	
	public Vector3D getPosition() {
		return position;
	}

}
