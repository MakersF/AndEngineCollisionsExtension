package com.makersf.andengine.extension.collisions.pixelperfect;

/**
 * <p>This class is basically a java-space replacement for the native {@link android.graphics.Matrix} class.</p>
 * 
 * <p>Math taken from <a href="http://www.senocular.com/flash/tutorials/transformmatrix/">senocular.com</a>.</p>
 * 
 * This class represents an affine transformation with the following matrix:
 * <pre> [ a , b , 0 ]
 * [ c , d , 0 ]
 * [ tx, ty, 1 ]</pre>
 * where:
 * <ul>
 *  <li><b>a</b> is the <b>x scale</b></li>
 *  <li><b>b</b> is the <b>y skew</b></li>
 *  <li><b>c</b> is the <b>x skew</b></li>
 *  <li><b>d</b> is the <b>y scale</b></li>
 *  <li><b>tx</b> is the <b>x translation</b></li>
 *  <li><b>ty</b> is the <b>y translation</b></li>
 * </ul>
 *
 * This code is largely copied by <a href="http://www.andengine.org/">Nicolas Gramlich's AndEngine</a>
 * with a few modifications and additions
 */
public class Transformation {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final float DEG_TO_RAD = (float) Math.PI / 180.f;
	// ===========================================================
	// Fields
	// ===========================================================

	protected float a = 1.0f; /* x scale */
	protected float b = 0.0f; /* y skew */
	protected float c = 0.0f; /* x skew */
	protected float d = 1.0f; /* y scale */
	protected float tx = 0.0f; /* x translation */
	protected float ty = 0.0f; /* y translation */

	// ===========================================================
	// Constructors
	// ===========================================================

	public Transformation() {

	}

	public Transformation(float xScale, float yScale, float xSkew, float ySkew, float xTranslation, float yTranslation) {
		a = xScale;
		b = ySkew;
		c = xSkew;
		d = yScale;
		tx = xTranslation;
		ty = yTranslation;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public String toString() {
		return "Transformation{[" + this.a + ", " + this.c + ", " + this.tx + "][" + this.b + ", " + this.d + ", " + this.ty + "][0.0, 0.0, 1.0]}";
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public final void reset() {
		this.setToIdentity();
	}

	public final void setToIdentity() {
		this.a = 1.0f;
		this.d = 1.0f;

		this.b = 0.0f;
		this.c = 0.0f;
		this.tx = 0.0f;
		this.ty = 0.0f;
	}

	public final void setTo(final Transformation pTransformation) {
		this.a = pTransformation.a;
		this.d = pTransformation.d;

		this.b = pTransformation.b;
		this.c = pTransformation.c;
		this.tx = pTransformation.tx;
		this.ty = pTransformation.ty;
	}

	public final void preTranslate(final float pX, final float pY) {
		this.tx += pX * this.a + pY * this.c;
		this.ty += pX * this.b + pY * this.d;
	}

	public final void postTranslate(final float pX, final float pY) {
		this.tx += pX;
		this.ty += pY;
	}

	public final Transformation setToTranslate(final float pX, final float pY) {
		this.a = 1.0f;
		this.b = 0.0f;
		this.c = 0.0f;
		this.d = 1.0f;
		this.tx = pX;
		this.ty = pY;

		return this;
	}

	public final void preRotate(final float pAngle) {
		final float angleRad = DEG_TO_RAD * pAngle;

		final float sin = (float) Math.sin(angleRad);
		final float cos = (float) Math.cos(angleRad);

		final float localA = this.a;
		final float localB= this.b;
		final float localC = this.c;
		final float localD = this.d;

		this.a = cos * localA + sin * localC;
		this.b = cos * localB + sin * localD;
		this.c = cos * localC - sin * localA;
		this.d = cos * localD - sin * localB;
	}

	public final void postRotate(final float pAngle) {
		final float angleRad = DEG_TO_RAD * pAngle;

		final float sin = (float) Math.sin(angleRad);
		final float cos = (float) Math.cos(angleRad);

		final float localA = this.a;
		final float localB = this.b;
		final float localC = this.c;
		final float localD = this.d;
		final float localTX = this.tx;
		final float localTY = this.ty;

		this.a = localA * cos - localB * sin;
		this.b = localA * sin + localB * cos;
		this.c = localC * cos - localD * sin;
		this.d = localC * sin + localD * cos;
		this.tx = localTX * cos - localTY * sin;
		this.ty = localTX * sin + localTY * cos;
	}

	public final Transformation setToRotate(final float pAngle) {
		final float angleRad = DEG_TO_RAD * pAngle;

		final float sin = (float) Math.sin(angleRad);
		final float cos = (float) Math.cos(angleRad);

		this.a = cos;
		this.b = sin;
		this.c = -sin;
		this.d = cos;
		this.tx = 0.0f;
		this.ty = 0.0f;

		return this;
	}

	public final void preScale(final float pScaleX, final float pScaleY) {
		this.a *= pScaleX;
		this.b *= pScaleX;
		this.c *= pScaleY;
		this.d *= pScaleY;
	}

	public final void postScale(final float pScaleX, final float pScaleY) {
		this.a = this.a * pScaleX;
		this.b = this.b * pScaleY;
		this.c = this.c * pScaleX;
		this.d = this.d * pScaleY;
		this.tx = this.tx * pScaleX;
		this.ty = this.ty * pScaleY;
	}

	public final Transformation setToScale(final float pScaleX, final float pScaleY) {
		this.a = pScaleX;
		this.b = 0.0f;
		this.c = 0.0f;
		this.d = pScaleY;
		this.tx = 0.0f;
		this.ty = 0.0f;

		return this;
	}

	public final void preSkew(final float pSkewX, final float pSkewY) {
		final float tanX = (float) Math.tan(-DEG_TO_RAD * pSkewX);
		final float tanY = (float) Math.tan(-DEG_TO_RAD * pSkewY);

		final float localA = this.a;
		final float localB = this.b;
		final float localC = this.c;
		final float localD = this.d;
		final float localTX = this.tx;
		final float localTY = this.ty;

		this.a = localA + tanY * localC;
		this.b = localB + tanY * localD;
		this.c = tanX * localA + localC;
		this.d = tanX * localB + localD;
		this.tx = localTX;
		this.ty = localTY;
	}

	public final void postSkew(final float pSkewX, final float pSkewY) {
		final float tanX = (float) Math.tan(-DEG_TO_RAD * pSkewX);
		final float tanY = (float) Math.tan(-DEG_TO_RAD * pSkewY);

		final float localA = this.a;
		final float localB = this.b;
		final float localC = this.c;
		final float localD = this.d;
		final float localTX = this.tx;
		final float localTY = this.ty;

		this.a = localA + localB * tanX;
		this.b = localA * tanY + localB;
		this.c = localC + localD * tanX;
		this.d = localC * tanY + localD;
		this.tx = localTX + localTY * tanX;
		this.ty = localTX * tanY + localTY;
	}

	public final Transformation setToSkew(final float pSkewX, final float pSkewY) {
		this.a = 1.0f;
		this.b = (float) Math.tan(-DEG_TO_RAD * pSkewY);
		this.c = (float) Math.tan(-DEG_TO_RAD * pSkewX);
		this.d = 1.0f;
		this.tx = 0.0f;
		this.ty = 0.0f;

		return this;
	}

	public final void postConcat(final Transformation pTransformation) {
		this.postConcat(pTransformation.a, pTransformation.b, pTransformation.c, pTransformation.d, pTransformation.tx, pTransformation.ty);
	}

	private void postConcat(final float pA, final float pB, final float pC, final float pD, final float pTX, final float pTY) {
		final float localA = this.a;
		final float localB = this.b;
		final float localC = this.c;
		final float localD = this.d;
		final float localTX = this.tx;
		final float localTY = this.ty;

		this.a = localA * pA + localB * pC;
		this.b = localA * pB + localB * pD;
		this.c = localC * pA + localD * pC;
		this.d = localC * pB + localD * pD;
		this.tx = localTX * pA + localTY * pC + pTX;
		this.ty = localTX * pB + localTY * pD + pTY;
	}

	public final void preConcat(final Transformation pTransformation) {
		this.preConcat(pTransformation.a, pTransformation.b, pTransformation.c, pTransformation.d, pTransformation.tx, pTransformation.ty);
	}

	private void preConcat(final float pA, final float pB, final float pC, final float pD, final float pTX, final float pTY) {
		final float localA = this.a;
		final float localB = this.b;
		final float localC = this.c;
		final float localD = this.d;
		final float localTX = this.tx;
		final float localTY = this.ty;

		this.a = pA * localA + pB * localC;
		this.b = pA * localB + pB * localD;
		this.c = pC * localA + pD * localC;
		this.d = pC * localB + pD * localD;
		this.tx = pTX * localA + pTY * localC + localTX;
		this.ty = pTX * localB + pTY * localD + localTY;
	}

	public final void transform(final float[] pVertices) {
		int count = pVertices.length >> 1;
		int i = 0;
		int j = 0;
		while(--count >= 0) {
			final float x = pVertices[i++];
			final float y = pVertices[i++];
			pVertices[j++] = x * this.a + y * this.c + this.tx;
			pVertices[j++] = x * this.b + y * this.d + this.ty;
		}
	}

	/**
	* Transform the given vertices without taking into account the translation.
	* @param pTransformation The transformation to use
	* @param pVertices The vertices to transform
	*/
	public void transformNormal(final float[] pVertices) {
		int count = pVertices.length >> 1;
		int i = 0;
		int j = 0;
		while(--count >= 0) {
			final float x = pVertices[i++];
			final float y = pVertices[i++];
			pVertices[j++] = x * this.a + y * this.c;
			pVertices[j++] = x * this.b + y * this.d;
		}
	}

	public Transformation invert(final Transformation pInverseContainer) {

		final float det = this.a*this.d - this.b*this.c;
	
		pInverseContainer.a = this.d / det;
		pInverseContainer.b = -this.b / det;
		pInverseContainer.c = -this.c / det;
		pInverseContainer.d = this.a / det;
		pInverseContainer.tx = (this.c*this.ty - this.d*this.tx) / det;
		pInverseContainer.ty = (-this.a*this.ty + this.b*this.tx) / det;
	
		return pInverseContainer;
	}
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}