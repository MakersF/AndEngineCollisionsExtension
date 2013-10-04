package com.makersf.andengine.extension.collisions.opengl.texture.region;

import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.ResourceBitmapTextureAtlasSource;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.util.call.Callback;

import android.content.res.AssetManager;
import android.content.res.Resources;

/**
 * 
 * @author Francesco Zoffoli
 * @since 01.08.2012
 *
 */
public class PixelPerfectTextureRegionFactory{
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private static String sAssetBasePath = "";

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	/**
	 * @param pAssetBasePath must end with '<code>/</code>' or have <code>.length() == 0</code>.
	 */
	public static void setAssetBasePath(final String pAssetBasePath) {
		if(pAssetBasePath.endsWith("/") || pAssetBasePath.length() == 0) {
			PixelPerfectTextureRegionFactory.sAssetBasePath = pAssetBasePath;
		} else {
			throw new IllegalArgumentException("pAssetBasePath must end with '/' or be lenght zero.");
		}
	}

	public static void reset() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("");
	}
	
	/**
	 * 
	 * @param pTexture
	 * @param pContext
	 * @param pAssetPath
	 * @param pTexturePositionX
	 * @param pTexturePositionY
	 * @param pAlphaThreshold Must be between 0 and 255. If the alpha of the pixel > pAlphaThreshold the pixel is set not-colliding
	 * @return
	 */
	public static PixelPerfectTextureRegion createFromAsset(final BitmapTextureAtlas pTexture, final AssetManager pAssetManager, final String pAssetPath, final int pTexturePositionX, final int pTexturePositionY, final int pAlphaThreshold) {
        final IBitmapTextureAtlasSource textureSource = AssetBitmapTextureAtlasSource.create(pAssetManager, sAssetBasePath + pAssetPath);
        return createFromSource(pTexture, textureSource, pTexturePositionX, pTexturePositionY, false, pAlphaThreshold);
    }
	
    public static PixelPerfectTextureRegion createFromAsset(final BitmapTextureAtlas pTexture, final AssetManager pAssetManager, final String pAssetPath, final int pTexturePositionX, final int pTexturePositionY, final boolean pRotated, final int pAlphaThreshold) {
        final IBitmapTextureAtlasSource textureSource = AssetBitmapTextureAtlasSource.create(pAssetManager, sAssetBasePath + pAssetPath);
        return createFromSource(pTexture, textureSource, pTexturePositionX, pTexturePositionY, pRotated, pAlphaThreshold);
    }
    
    public static PixelPerfectTiledTextureRegion createTiledFromAsset(final BitmapTextureAtlas pTexture, final AssetManager pAssetManager, final String pAssetPath, final int pTexturePositionX, final int pTexturePositionY, final int pTileColumns, final int pTileRows, final int pAlphaThreshold) {
        final IBitmapTextureAtlasSource textureSource = AssetBitmapTextureAtlasSource.create(pAssetManager, sAssetBasePath + pAssetPath);
        return createTiledFromSource(pTexture, textureSource, pTexturePositionX, pTexturePositionY, pTileColumns, pTileRows, pAlphaThreshold);
    }
    
    public static PixelPerfectTextureRegion createFromResource(final BitmapTextureAtlas pBitmapTextureAtlas, final Resources pResources, final int pDrawableResourceID, final int pTextureX, final int pTextureY, final boolean pRotated, final int pAlphaThreshold) {
		final IBitmapTextureAtlasSource bitmapTextureAtlasSource = ResourceBitmapTextureAtlasSource.create(pResources, pDrawableResourceID);
		return PixelPerfectTextureRegionFactory.createFromSource(pBitmapTextureAtlas, bitmapTextureAtlasSource, pTextureX, pTextureY, pRotated, pAlphaThreshold);
	}
    
    public static PixelPerfectTiledTextureRegion createTiledFromResource(final BitmapTextureAtlas pBitmapTextureAtlas, final Resources pResources, final int pDrawableResourceID, final int pTextureX, final int pTextureY, final int pTileColumns, final int pTileRows, final int pAlphaThreshold) {
		final IBitmapTextureAtlasSource bitmapTextureAtlasSource = ResourceBitmapTextureAtlasSource.create(pResources, pDrawableResourceID);
		return PixelPerfectTextureRegionFactory.createTiledFromSource(pBitmapTextureAtlas, bitmapTextureAtlasSource, pTextureX, pTextureY, pTileColumns, pTileRows, pAlphaThreshold);
	}
    
    public static PixelPerfectTextureRegion createFromSource(final BitmapTextureAtlas pTexture, final IBitmapTextureAtlasSource pTextureSource, final int pTexturePositionX, final int pTexturePositionY, final boolean pRotated, final int pAlphaThreshold) {
        final PixelPerfectTextureRegion textureRegion = new PixelPerfectTextureRegion(pTexture, pTexturePositionX, pTexturePositionY, pTextureSource.getTextureWidth(), pTextureSource.getTextureHeight(), pRotated);
        pTexture.addTextureAtlasSource(pTextureSource, pTexturePositionX, pTexturePositionY);
        textureRegion.buildMask(pTextureSource, pAlphaThreshold, pTexture.getBitmapTextureFormat().getBitmapConfig());
        return textureRegion;
    }

    public static PixelPerfectTiledTextureRegion createTiledFromSource(final BitmapTextureAtlas pTexture, final IBitmapTextureAtlasSource pTextureSource, final int pTexturePositionX, final int pTexturePositionY, final int pTileColumns, final int pTileRows, final int pAlphaThreshold) {
        final PixelPerfectTiledTextureRegion tiledTextureRegion = PixelPerfectTiledTextureRegion.create(pTexture, pTexturePositionX, pTexturePositionY, pTextureSource.getTextureWidth(), pTextureSource.getTextureHeight(), pTileColumns, pTileRows, false);
        pTexture.addTextureAtlasSource(pTextureSource, pTexturePositionX, pTexturePositionY);
        tiledTextureRegion.buildMask(pTextureSource, 0, 0, pTextureSource.getTextureWidth(), pTextureSource.getTextureHeight(), pTileColumns, pTileRows, false, pAlphaThreshold, pTexture.getBitmapTextureFormat().getBitmapConfig());
        return tiledTextureRegion;
    }
    
	// ===========================================================
	// Methods using BuildableTexture
	// ===========================================================
    
    public static PixelPerfectTextureRegion createFromAsset(final BuildableBitmapTextureAtlas pBuildableBitmapTextureAtlas, final AssetManager pAssetManager, final String pAssetPath, final boolean pRotated, final int pAlphaThreshold) {
		final IBitmapTextureAtlasSource bitmapTextureAtlasSource = AssetBitmapTextureAtlasSource.create(pAssetManager, PixelPerfectTextureRegionFactory.sAssetBasePath + pAssetPath);
		return PixelPerfectTextureRegionFactory.createFromSource(pBuildableBitmapTextureAtlas, bitmapTextureAtlasSource, pRotated, pAlphaThreshold);
	}
    
    public static PixelPerfectTiledTextureRegion createTiledFromAsset(final BuildableBitmapTextureAtlas pBuildableBitmapTextureAtlas, final AssetManager pAssetManager, final String pAssetPath, final int pTileColumns, final int pTileRows, final boolean pRotated, final int pAlphaThreshold) {
		final IBitmapTextureAtlasSource bitmapTextureAtlasSource = AssetBitmapTextureAtlasSource.create(pAssetManager, PixelPerfectTextureRegionFactory.sAssetBasePath + pAssetPath);
		return PixelPerfectTextureRegionFactory.createTiledFromSource(pBuildableBitmapTextureAtlas, bitmapTextureAtlasSource, pTileColumns, pTileRows, pRotated, pAlphaThreshold);
	}
    
    public static PixelPerfectTextureRegion createFromResource(final BuildableBitmapTextureAtlas pBuildableBitmapTextureAtlas, final Resources pResources, final int pDrawableResourceID, final boolean pRotated, final int pAlphaThreshold) {
		final IBitmapTextureAtlasSource bitmapTextureAtlasSource = ResourceBitmapTextureAtlasSource.create(pResources, pDrawableResourceID);
		return PixelPerfectTextureRegionFactory.createFromSource(pBuildableBitmapTextureAtlas, bitmapTextureAtlasSource, pRotated, pAlphaThreshold);
	}
    
    public static PixelPerfectTiledTextureRegion createTiledFromResource(final BuildableBitmapTextureAtlas pBuildableBitmapTextureAtlas, final Resources pResources, final int pDrawableResourceID, final int pTileColumns, final int pTileRows, final boolean pRotated, final int pAlphaThreshold) {
		final IBitmapTextureAtlasSource bitmapTextureAtlasSource = ResourceBitmapTextureAtlasSource.create(pResources, pDrawableResourceID);
		return PixelPerfectTextureRegionFactory.createTiledFromSource(pBuildableBitmapTextureAtlas, bitmapTextureAtlasSource, pTileColumns, pTileRows, pRotated, pAlphaThreshold);
	}
    
    public static PixelPerfectTextureRegion createFromSource(final BuildableBitmapTextureAtlas pBuildableBitmapTextureAtlas, final IBitmapTextureAtlasSource pBitmapTextureAtlasSource, final boolean pRotated, final int pAlphaThreshold) {
    	final PixelPerfectTextureRegion textureRegion = new PixelPerfectTextureRegion(pBuildableBitmapTextureAtlas, 0, 0, pBitmapTextureAtlasSource.getTextureWidth(), pBitmapTextureAtlasSource.getTextureHeight(), pRotated);
    	pBuildableBitmapTextureAtlas.addTextureAtlasSource(pBitmapTextureAtlasSource, new Callback<IBitmapTextureAtlasSource>() {
			@Override
			public void onCallback(final IBitmapTextureAtlasSource pCallbackValue) {
				textureRegion.setTexturePosition(pCallbackValue.getTextureX(), pCallbackValue.getTextureY());
				BitmapTextureFormat bitmatTextureFormat = BitmapTextureFormat.fromPixelFormat(pBuildableBitmapTextureAtlas.getPixelFormat());
				textureRegion.buildMask(pCallbackValue, pAlphaThreshold, bitmatTextureFormat.getBitmapConfig());
			}
		});
		return textureRegion;
	}
    
    public static PixelPerfectTiledTextureRegion createTiledFromSource(final BuildableBitmapTextureAtlas pBuildableTextureAtlas, final IBitmapTextureAtlasSource pTextureAtlasSource, final int pTileColumns, final int pTileRows, final boolean pRotated, final int pAlphaThreshold) {
		final PixelPerfectTiledTextureRegion tiledTextureRegion = PixelPerfectTiledTextureRegion.create(pBuildableTextureAtlas, 0, 0, pTextureAtlasSource.getTextureWidth(), pTextureAtlasSource.getTextureHeight(), pTileColumns, pTileRows, false);
		pBuildableTextureAtlas.addTextureAtlasSource(pTextureAtlasSource, new Callback<IBitmapTextureAtlasSource>() {
			@Override
			public void onCallback(final IBitmapTextureAtlasSource pCallbackValue) {
				final int tileWidth = pTextureAtlasSource.getTextureWidth() / pTileColumns;
				final int tileHeight = pTextureAtlasSource.getTextureHeight() / pTileRows;
				
				for(int tileColumn = 0; tileColumn < pTileColumns; tileColumn++) {
					for(int tileRow = 0; tileRow < pTileRows; tileRow++) {
						final int tileIndex = tileRow * pTileColumns + tileColumn;

						final int x = pCallbackValue.getTextureX() + tileColumn * tileWidth;
						final int y = pCallbackValue.getTextureY() + tileRow * tileHeight;
						
						tiledTextureRegion.setTexturePosition(tileIndex, x, y);
						BitmapTextureFormat bitmatTextureFormat = BitmapTextureFormat.fromPixelFormat(pBuildableTextureAtlas.getPixelFormat());
						tiledTextureRegion.buildTileMask(tileIndex, pCallbackValue, pRotated, pAlphaThreshold, bitmatTextureFormat.getBitmapConfig());
					}
				}
				
			}
		});
		return tiledTextureRegion;
	}
}