package com.makersf.andengine.extension.collisions.glue;

import org.andengine.entity.shape.IShape;

import com.makersf.andengine.extension.collisions.pixelperfect.Transformation;

public class ShapeAdapter implements com.makersf.andengine.extension.collisions.pixelperfect.IShape {

	private final IShape mShape;

	public ShapeAdapter(IShape pShape) {
		mShape = pShape;
	}

	@Override
	public Transformation getLocalToSceneTransformation() {
		return TransformationAdapter.adapt(mShape.getLocalToSceneTransformation());
	}

	@Override
	public Transformation getSceneToLocalTransformation() {
		return TransformationAdapter.adapt(mShape.getSceneToLocalTransformation());
	}

	@Override
	public float getWidth() {
		return mShape.getWidth();
	}

	@Override
	public float getHeight() {
		return mShape.getHeight();
	}

}
