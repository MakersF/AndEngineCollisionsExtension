package com.makersf.andengine.extension.collisions.pixelperfect.masks;

import org.andengine.util.adt.pool.GenericPool;


public class RectangularPixelPerfectMaskPool extends GenericPool<RectangularPixelPerfectMask>{

	private static RectangularPixelPerfectMaskPool mInstance;

	private RectangularPixelPerfectMaskPool() {
		super();
	}

	public static RectangularPixelPerfectMaskPool getInstance() {
		if(mInstance == null)
			mInstance = new RectangularPixelPerfectMaskPool();
		return mInstance;
	}

	@Override
	protected RectangularPixelPerfectMask onAllocatePoolItem() {
		return new RectangularPixelPerfectMask(0, 0);
	}

}
