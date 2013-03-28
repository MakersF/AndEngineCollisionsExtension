package com.makersf.andengine.extension.collisions.pixelperfect.masks;

import java.nio.ByteBuffer;

public class CustomPixelPerfectMask implements IPixelPerfectMask {

	final boolean[][] mBitsBlock;

	public CustomPixelPerfectMask(final int pWidth, final int pHeight, ByteBuffer pSource) {
		pSource.position(0);

		mBitsBlock = new boolean[pWidth][pHeight];
		byte container = 0;
		for(int i = 0; i < pWidth * pHeight; i ++) {
			if(i % 8 == 0)
				container = pSource.get();
			int x = i / pHeight;
			int y = i % pHeight;
			mBitsBlock[x][y] = (container >> (i % 8) & 1) == 1;
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
