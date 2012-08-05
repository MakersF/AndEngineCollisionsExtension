package com.makersf.andengine.extension.collisions.pixelperfect.masks;

/**
 * 
 * @author Francesco Zoffoli
 * @since 01.08.2012
 *
 */
public class RectangularPixelPerfectMask implements IPixelPerfectMask {

	int mWidth;
	int mHeight;

	public RectangularPixelPerfectMask(final int pWidth, final int pHeight) {
		mWidth = pWidth;
		mHeight = pWidth;
	}

	@Override
	public boolean isSolid(int pX, int pY) {
		if( 0<=pX && pX<=mWidth &&
				0<=pY && pY<=mHeight)
			return true;
		return false;
	}

	public void setTo(final int pWidth, final int pHeight) {
		mWidth = pWidth;
		mHeight = pWidth;
	}

	@Override
	public int getWidth() {
		return mWidth;
	}

	@Override
	public int getHeight() {
		return mHeight;
	}

}
