package com.makersf.andengine.extension.collisions.pixelperfect;

import android.graphics.Bitmap;

public class PixelPerfectMask {
	
	final boolean[][] mBitsBlock;
	final int mBitmaskWidth;
	final int mBitmaskHeight;
	
	public PixelPerfectMask(Bitmap bitmap, float pTextureX, float pTextureY,
			float pTextureWidth, float pTextureHeight, int pAlphaThreshold) {
		
		mBitmaskWidth = (int) pTextureWidth;
		mBitmaskHeight = (int) pTextureHeight;
		int X = (int) pTextureX;
		int Y = (int) pTextureY;

		if(pAlphaThreshold<0 || pAlphaThreshold>255)
			throw new IllegalArgumentException("pAlphaThreshold should be in [0,255] range. " + pAlphaThreshold + " provided.");

		mBitsBlock = new boolean[mBitmaskWidth][mBitmaskHeight];
			
		
		/*for(int r = 0; r < rows; r++) {
			for(int c = 0; c < cols; c++) {
				
				int base_x = X + c * 32;
				int base_y = Y + r * 32;
				
				int block = r * rows + c;
				
				for(int x = 0; x < 32; x++) {
					for(int y = 0; y < 32; y++) {
						boolean solid = false;
						
						try {
							solid = ((bitmap.getPixel(base_x + x, base_y + y) >> 24) & 0x000000FF) > pAlphaThreshold;
						} catch(IllegalArgumentException e){
							solid = false;
						}
						
						mBitsBlock[previousPixels + x * 32 + y] = solid;
						}
					}
				}
			}
		}*/
	
		for(int x = 0; x < mBitmaskWidth; x++) {
			for(int y = 0; y < mBitmaskHeight; y++) {
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
