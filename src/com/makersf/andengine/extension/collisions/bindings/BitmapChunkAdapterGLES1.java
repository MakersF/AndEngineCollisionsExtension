package com.makersf.andengine.extension.collisions.bindings;

import com.makersf.frameworks.shared.collisioncore.pixelperfect.masks.implementations.IBitmap;

import android.graphics.Bitmap;


public class BitmapChunkAdapterGLES1 implements IBitmap {

	private final int mTextureY;
	private final int mTextureHeight;
	private final Bitmap mBitmap;

	public BitmapChunkAdapterGLES1(Bitmap pBitmap, int pTextureY, int pTextureHeight) {
		mBitmap = pBitmap;
		mTextureY = pTextureY;
		mTextureHeight = pTextureHeight;
	}

	@Override
	public int getPixel(int x, int y) {
		// The texture must be flipped vertically due the coordinate systems differences
		final int offsetFromTextureY = y - mTextureY;
		final int correctY = mTextureY + (mTextureHeight - offsetFromTextureY);
		return mBitmap.getPixel(x, correctY);
	}

}
