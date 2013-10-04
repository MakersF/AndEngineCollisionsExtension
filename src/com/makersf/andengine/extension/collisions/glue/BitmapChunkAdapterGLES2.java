package com.makersf.andengine.extension.collisions.glue;

import android.graphics.Bitmap;

import com.makersf.andengine.extension.collisions.pixelperfect.masks.IBitmap;

public class BitmapChunkAdapterGLES2 implements IBitmap {

	private final Bitmap mBitmap;

	public BitmapChunkAdapterGLES2(Bitmap pBitmap) {
		mBitmap = pBitmap;
	}

	@Override
	public int getPixel(int x, int y) {
		return mBitmap.getPixel(x, y);
	}

}
