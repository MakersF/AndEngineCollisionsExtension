package com.makersf.andengine.extension.collisions.bindings;

import com.makersf.frameworks.shared.collisioncore.pixelperfect.masks.implementations.IBitmap;

import android.graphics.Bitmap;


public class BitmapChunkAdapterGLES1 implements IBitmap {

	private final Bitmap mBitmap;

	public BitmapChunkAdapterGLES1(Bitmap pBitmap) {
		mBitmap = pBitmap;
	}

	@Override
	public int getPixel(int offsetX, int offsetY, int baseX, int baseY,
			int width, int height) {
		int y = baseY + offsetY;
		int x = baseX + offsetX;
		return mBitmap.getPixel(x, y);
	}

}
