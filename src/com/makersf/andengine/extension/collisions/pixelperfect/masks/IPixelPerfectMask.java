package com.makersf.andengine.extension.collisions.pixelperfect.masks;

public interface IPixelPerfectMask {

	public boolean isSolid(final int pX, final int pY);
	public int getWidth();
	public int getHeight();
}
