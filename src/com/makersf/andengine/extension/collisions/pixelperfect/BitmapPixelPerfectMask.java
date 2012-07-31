package com.makersf.andengine.extension.collisions.pixelperfect;

import android.graphics.Bitmap;

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
				mBitsBlock[x][y] = ((bitmap.getPixel(X + x, Y + y) >> 24) & 0x000000FF) > pAlphaThreshold;
			}
		}
	}
	
	public boolean isSolid(final int pX, final int pY) {
		try {
			return mBitsBlock[pX][pY];
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

}
