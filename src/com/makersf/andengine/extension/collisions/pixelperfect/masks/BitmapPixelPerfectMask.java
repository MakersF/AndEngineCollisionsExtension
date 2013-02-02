package com.makersf.andengine.extension.collisions.pixelperfect.masks;

import android.graphics.Bitmap;

/**
 * 
 * @author Francesco Zoffoli
 * @since 01.08.2012
 *
 */
public class BitmapPixelPerfectMask implements IPixelPerfectMask {

	final boolean[][] mBitsBlock;

	public BitmapPixelPerfectMask(Bitmap bitmap, float pTextureX, float pTextureY,
			float pTextureWidth, float pTextureHeight, int pAlphaThreshold) {

		final int bitmaskWidth = (int) pTextureWidth;
		final int bitmaskHeight = (int) pTextureHeight;
		int X = (int) pTextureX;
		int Y = (int) pTextureY;

		if(pAlphaThreshold<0 || pAlphaThreshold>255)
			throw new IllegalArgumentException("pAlphaThreshold should be in [0,255] range. " + pAlphaThreshold + " provided.");

		mBitsBlock = new boolean[bitmaskWidth][bitmaskHeight];

		for(int x = 0; x < bitmaskWidth; x++) {
			for(int y = 0; y < bitmaskHeight; y++) {
				mBitsBlock[x][bitmaskHeight-y-1] = ((bitmap.getPixel(X + x, Y + y) >> 24) & 0x000000FF) > pAlphaThreshold;
			}
		}
	}

	public boolean isSolid(final int pX, final int pY) {
		if(0 <= pX && pX < mBitsBlock.length &&
				0 <= pY && pY < mBitsBlock[0].length)
			return mBitsBlock[pX][pY];
		else
			return false;
	}

	@Override
	public int getWidth() {
		return mBitsBlock.length;
	}

	@Override
	public int getHeight() {
		return mBitsBlock[0].length;
	}
}
