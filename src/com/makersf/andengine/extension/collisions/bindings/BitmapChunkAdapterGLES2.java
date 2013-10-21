package com.makersf.andengine.extension.collisions.bindings;

import android.graphics.Bitmap;

import com.makersf.frameworks.shared.collisioncore.pixelperfect.masks.implementations.IBitmap;

public class BitmapChunkAdapterGLES2 implements IBitmap {

	private final Bitmap mBitmap;

	public BitmapChunkAdapterGLES2(Bitmap pBitmap) {
		mBitmap = pBitmap;
	}

	@Override
	public int getPixel(int offsetX, int offsetY, int baseX, int baseY,
			int width, int height) {
		int x = baseX + offsetX;
		int flippedY = baseY + height - offsetY - 1;
		return mBitmap.getPixel(x, flippedY);
	}

}
