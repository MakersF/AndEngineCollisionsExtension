package com.makersf.andengine.extension.collisions.pixelperfect;

import android.graphics.Bitmap;

public class PixelPerfectMask {
	
	final private int PIXEL_PER_BLOCK = 32*32;
	final boolean[] mBitsBlock; //each block holds 32x32 pixel
	final boolean[][] mBlocksFlag; //each block can be Empty, Not Fully Filled or Filled.
	//a more intelligent thing would be to make spatial splits based on the "Solid" pixels(to have the max number of rectangles empty of fully filled) 
	
	public PixelPerfectMask(Bitmap bitmap, float pTextureX, float pTextureY,
			float pTextureWidth, float pTextureHeight, int pAlphaThreshold) {
		
		int textWidth = (int) pTextureWidth;
		int textHeight = (int) pTextureHeight;
		int X = (int) pTextureX;
		int Y = (int) pTextureY;
		
		if(pAlphaThreshold<0 || pAlphaThreshold>255)
			throw new IllegalArgumentException("pAlphaThreshold should be in [0,255] range. " + pAlphaThreshold + " provided.");
		
		mBitsBlock = new boolean[(int) (pTextureWidth * pTextureHeight)];
		
		//2 bits are needed in order to store 3 possible cases (E, NFF,F)
		int cols = (textHeight + 31) / 32;
		int rows = (textWidth + 31) / 32;
		mBlocksFlag = new boolean[cols*rows][2];
		
		for(int r = 0; r < rows; r++) {
			for(int c = 0; c < cols; c++) {
				
				int base_x = X + c * 32;
				int base_y = Y + r * 32;
				
				int block = r * rows + c;
				int previousPixels = block * PIXEL_PER_BLOCK;
				mBlocksFlag[block][0] = true;
				
				for(int x = 0; x < 32; x++) {
					for(int y = 0; y < 32; y++) {
						boolean solid = false;
						
						try {
							solid = ((bitmap.getPixel(base_x + x, base_y + y) >> 24) & 0x000000FF) > pAlphaThreshold;
						} catch(IllegalArgumentException e){
							solid = false;
						}
						
						mBitsBlock[previousPixels + x * 32 + y] = solid;
						mBlocksFlag[block][0] &= solid;//if at the end is 1 -> the block is full
						mBlocksFlag[block][1] |= solid;//if at the end is 0 -> the block is empty
					}
				}
			}
		}
	}

}
