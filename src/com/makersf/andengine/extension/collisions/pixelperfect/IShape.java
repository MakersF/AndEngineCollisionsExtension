package com.makersf.andengine.extension.collisions.pixelperfect;

public interface IShape {
	/**
	 * 
	 * @return The transformation that brings from reference system of the this IShape to the
	 * reference system of the scene.
	 */
	public Transformation getLocalToSceneTransformation();
	/**
	 * An example could be: This IShape is in position (1,1) in scene coordinates. A point is in
	 * position (1,2) in scene coordinates. Thus, the point is in coordinate (0,1) in this IShape coordinates.
	 * @return The transformation that brings from the reference system of the scene to the
	 * reference system of this IShape
	 */
	public Transformation getSceneToLocalTransformation();
	public float getWidth();
	public float getHeight();
}
