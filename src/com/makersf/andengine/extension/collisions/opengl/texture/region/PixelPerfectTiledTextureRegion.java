package com.makersf.andengine.extension.collisions.opengl.texture.region;

import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.graphics.Bitmap.Config;

import com.makersf.andengine.extension.collisions.pixelperfect.PixelPerfectMask;

public class PixelPerfectTiledTextureRegion extends TiledTextureRegion {
	
	PixelPerfectMask[] mMasks;
	
	public PixelPerfectTiledTextureRegion(final ITexture pTexture, final boolean pPerformSameTextureSanityCheck, final PixelPerfectMask[] pPixelMasks, final ITextureRegion ... pTextureRegions) {
		super(pTexture, pPerformSameTextureSanityCheck, pTextureRegions);
		mMasks = pPixelMasks;
	}

	public static PixelPerfectTiledTextureRegion create(final ITexture pTexture, final int pTextureX, final int pTextureY, final int pTextureWidth, final int pTextureHeight, final int pTileColumns, final int pTileRows, final boolean pRotated) {
		final ITextureRegion[] textureRegions = new ITextureRegion[pTileColumns * pTileRows];

		final int tileWidth = pTextureWidth / pTileColumns;
		final int tileHeight = pTextureHeight / pTileRows;

		for(int tileColumn = 0; tileColumn < pTileColumns; tileColumn++) {
			for(int tileRow = 0; tileRow < pTileRows; tileRow++) {
				final int tileIndex = tileRow * pTileColumns + tileColumn;

				final int x = pTextureX + tileColumn * tileWidth;
				final int y = pTextureY + tileRow * tileHeight;
				textureRegions[tileIndex] = new TextureRegion(pTexture, x, y, tileWidth, tileHeight, pRotated);
			}
		}

		return new PixelPerfectTiledTextureRegion(pTexture, false, null, textureRegions);
	}
	
	public void buildMask(final IBitmapTextureAtlasSource pTextureSource, final int pTextureX, final int pTextureY, final int pTextureWidth, final int pTextureHeight, final int pTileColumns, final int pTileRows,final boolean pRotated, final int pAlphaThreshold, final Config pBitmapConfig) {
		mMasks = new PixelPerfectMask[mTileCount];
		final int tileWidth = pTextureWidth / pTileColumns;
		final int tileHeight = pTextureHeight / pTileRows;
		
		for(int tileColumn = 0; tileColumn < pTileColumns; tileColumn++) {
			for(int tileRow = 0; tileRow < pTileRows; tileRow++) {
				final int tileIndex = tileRow * pTileColumns + tileColumn;
				final int x = pTextureX + tileColumn * tileWidth;
				final int y = pTextureY + tileRow * tileHeight;
				
				if(!pRotated)
					mMasks[tileIndex] = new PixelPerfectMask(pTextureSource.onLoadBitmap(pBitmapConfig), x, y, tileWidth, tileHeight, pAlphaThreshold);
				else
					mMasks[tileIndex] = new PixelPerfectMask(pTextureSource.onLoadBitmap(pBitmapConfig), y, x, tileHeight, tileWidth, pAlphaThreshold);
			}
		}
	}

	public void buildTileMask(final int pTileIndex, final IBitmapTextureAtlasSource pTextureSource, final boolean pRotated, final int pAlphaThreshold, final Config pBitmapConfig) {
		if(mMasks == null)
			mMasks = new PixelPerfectMask[mTileCount];
		if(!pRotated)
			mMasks[pTileIndex] = new PixelPerfectMask(pTextureSource.onLoadBitmap(pBitmapConfig), getTextureX(pTileIndex), getTextureY(pTileIndex), getWidth(pTileIndex), getHeight(pTileIndex), pAlphaThreshold);
		else
			mMasks[pTileIndex] = new PixelPerfectMask(pTextureSource.onLoadBitmap(pBitmapConfig), getTextureY(pTileIndex),getTextureX(pTileIndex), getHeight(pTileIndex), getWidth(pTileIndex), pAlphaThreshold);
	}
	
	@Override
	public PixelPerfectTiledTextureRegion deepCopy() {
		final int tileCount = this.mTileCount;

		final ITextureRegion[] textureRegions = new ITextureRegion[tileCount];

		for(int i = 0; i < tileCount; i++) {
			textureRegions[i] = this.mTextureRegions[i].deepCopy();
		}

		return new PixelPerfectTiledTextureRegion(this.mTexture, false, this.mMasks, textureRegions);
	}
	
	public PixelPerfectMask getCurrentPixelMask() {
		if(mMasks[mCurrentTileIndex] != null)
			return mMasks[mCurrentTileIndex];
		else
			throw new IllegalAccessError("The mask has not build yet");
	}
	
	public PixelPerfectMask getPixelMask(final int pTileIndex) {
		if(mMasks[pTileIndex] != null)
			return mMasks[pTileIndex];
		else
			throw new IllegalAccessError("The mask has not build yet");
	}
}
