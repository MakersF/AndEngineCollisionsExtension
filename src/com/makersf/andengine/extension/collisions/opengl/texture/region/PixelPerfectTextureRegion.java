package com.makersf.andengine.extension.collisions.opengl.texture.region;

import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.region.TextureRegion;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

import com.makersf.andengine.extension.collisions.pixelperfect.PixelPerfectMask;

public class PixelPerfectTextureRegion extends TextureRegion {

	// ===========================================================
	// Constants
	// ===========================================================

	private static final float SCALE_DEFAULT = 1;
	
	// ===========================================================
	// Fields
	// ===========================================================
	
	private PixelPerfectMask mMask;
	// ===========================================================
	// Constructors
	// ===========================================================
	
	public PixelPerfectTextureRegion(final ITexture pTexture, final float pTextureX, final float pTextureY, final float pTextureWidth, final float pTextureHeight, final boolean pRotated) {
		super(pTexture, pTextureX, pTextureY, pTextureWidth, pTextureHeight, SCALE_DEFAULT, pRotated);
	}
	
	public PixelPerfectTextureRegion(ITexture pTexture, float pTextureX,
			float pTextureY, float pTextureWidth, float pTextureHeight, boolean pRotated, PixelPerfectMask pMask) {
		super(pTexture, pTextureX, pTextureY, pTextureWidth, pTextureHeight, SCALE_DEFAULT,
				pRotated);
		this.mMask = pMask;//no need to make a copy of it since it is never changed
	}

	public void buildMask(IBitmapTextureAtlasSource pTextureSource, final int pAlphaThreshold, final Config pBitmapConfig) {
		Bitmap bitmap = pTextureSource.onLoadBitmap(pBitmapConfig);
		if(mRotated)
			mMask = new PixelPerfectMask(bitmap, mTextureX, mTextureY, mTextureWidth, mTextureHeight, pAlphaThreshold);
		else
			mMask = new PixelPerfectMask(bitmap, mTextureY, mTextureX, mTextureHeight, mTextureWidth, pAlphaThreshold);
	}
	
	public PixelPerfectMask getPixelMask() {
		if(mMask != null)
			return mMask;
		else
			throw new IllegalAccessError("The mask has not build yet");
	}
	
	@Override
	public PixelPerfectTextureRegion deepCopy() {
		if(this.mRotated) {
			return new PixelPerfectTextureRegion(this.mTexture, this.mTextureX, this.mTextureY, this.mTextureHeight, this.mTextureWidth, this.mRotated, this.mMask);
		} else {
			return new PixelPerfectTextureRegion(this.mTexture, this.mTextureY, this.mTextureX, this.mTextureWidth, this.mTextureHeight, this.mRotated, this.mMask);
		}
	}
}
