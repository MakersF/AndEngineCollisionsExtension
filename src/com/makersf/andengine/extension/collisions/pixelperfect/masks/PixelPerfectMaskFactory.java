package com.makersf.andengine.extension.collisions.pixelperfect.masks;

import java.nio.ByteBuffer;

public class PixelPerfectMaskFactory implements IPixelPerfectMaskFactory {

	@Override
	public IPixelPerfectMask getIPixelPerfectMask(int pWidth, int pHeight,
			ByteBuffer pByteBuffer) {
		return new CustomPixelPerfectMask(pWidth, pHeight, pByteBuffer);
	}

}
