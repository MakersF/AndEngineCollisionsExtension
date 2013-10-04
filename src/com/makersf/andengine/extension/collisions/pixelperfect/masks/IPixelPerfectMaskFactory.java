package com.makersf.andengine.extension.collisions.pixelperfect.masks;

import java.nio.ByteBuffer;

public interface IPixelPerfectMaskFactory {

	public IPixelPerfectMask getIPixelPerfectMask(int pWidth, int pHeight, ByteBuffer pByteBuffer);
}
