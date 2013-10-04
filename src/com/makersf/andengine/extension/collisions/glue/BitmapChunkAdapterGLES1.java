package com.makersf.andengine.extension.collisions.glue;

import android.graphics.Bitmap;

import com.makersf.andengine.extension.collisions.pixelperfect.masks.IBitmap;

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
		final int offsetFromTextureY = y - mTextureY;
		final int correctY = mTextureY + (mTextureHeight - offsetFromTextureY);
		return mBitmap.getPixel(x, correctY);
	}

}
