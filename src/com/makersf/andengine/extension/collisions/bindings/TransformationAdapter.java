package com.makersf.andengine.extension.collisions.bindings;

import java.lang.reflect.Field;

import org.andengine.util.adt.transformation.Transformation;

import android.util.Log;

public class TransformationAdapter {

	private static Field aField;
	private static Field bField;
	private static Field cField;
	private static Field dField;
	private static Field txField;
	private static Field tyField;
	static {
		try {
			aField = Transformation.class.getDeclaredField("a");
			bField = Transformation.class.getDeclaredField("b");
			cField = Transformation.class.getDeclaredField("c");
			dField = Transformation.class.getDeclaredField("d");
			txField = Transformation.class.getDeclaredField("tx");
			tyField = Transformation.class.getDeclaredField("ty");
			aField.setAccessible(true);
			bField.setAccessible(true);
			cField.setAccessible(true);
			dField.setAccessible(true);
			txField.setAccessible(true);
			tyField.setAccessible(true);
		} catch (NoSuchFieldException e) {
			Log.e("AndEngineCollisionExtension", "The implementation of Transformation changed. " +
					"Wait for a new update of the library or revert to a previous update of AndEngine. " +
					"Or you can always reimplement this code!");
		}
	}

	public static com.makersf.frameworks.shared.collisioncore.pixelperfect.Transformation adapt(Transformation pTransform) {
		try {
			final float a = aField.getFloat(pTransform); /* x scale */
			final float b = bField.getFloat(pTransform); /* y skew */
			final float c = cField.getFloat(pTransform); /* x skew */
			final float d = dField.getFloat(pTransform); /* y scale */
			final float tx = txField.getFloat(pTransform); /* x translation */
			final float ty = tyField.getFloat(pTransform); /* y translation */
			
			//it's not a typo that they are not in order!
			return new com.makersf.frameworks.shared.collisioncore.pixelperfect.Transformation(a, d, c, b, tx, ty);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Should never happen: Java already check that the type passed is a Transformation!", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Should never happen: the fields are set as accessible in the initializzation", e);
		}
	}
}
