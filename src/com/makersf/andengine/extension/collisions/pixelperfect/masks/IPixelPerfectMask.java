package com.makersf.andengine.extension.collisions.pixelperfect.masks;

/**
 * 
 * @author Francesco Zoffoli
 * @since 01.08.2012
 *
 */
public interface IPixelPerfectMask {

	public boolean isSolid(final int pX, final int pY);
	public int getWidth();
	public int getHeight();
}
