package com.makersf.andengine.extension.collisions.entity.sprite;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.makersf.andengine.extension.collisions.bindings.ShapeAdapter;
import com.makersf.andengine.extension.collisions.entity.shape.IPixelPerfectShape;
import com.makersf.andengine.extension.collisions.opengl.texture.region.PixelPerfectTiledTextureRegion;
import com.makersf.frameworks.shared.collisioncore.pixelperfect.PixelPerfectCollisionChecker;
import com.makersf.frameworks.shared.collisioncore.pixelperfect.masks.IPixelPerfectMask;
import com.makersf.frameworks.shared.collisioncore.pixelperfect.masks.implementations.RectangularPixelPerfectMask;
import com.makersf.frameworks.shared.collisioncore.pixelperfect.masks.implementations.RectangularPixelPerfectMaskPool;


/**
 * 
 * @author Francesco Zoffoli
 * @since 01.08.2012
 *
 */
public class PixelPerfectAnimatedSprite extends AnimatedSprite implements IPixelPerfectShape{
	
	private static boolean USE_PIXELPERFECT_COLLISION_FOR_EVERY_RECTANGULAR_SHAPE = true;

	private IPixelPerfectMask[] mPixelMasks;

	public PixelPerfectAnimatedSprite(float pX, float pY, PixelPerfectTiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion,
				pVertexBufferObjectManager);
		final int tileCount = pTiledTextureRegion.getTileCount();
		mPixelMasks = new IPixelPerfectMask[tileCount];
		for(int i = 0; i < tileCount; i++) {
			mPixelMasks[i] = pTiledTextureRegion.getPixelMask(i);
		}
	}

	public PixelPerfectAnimatedSprite(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, IPixelPerfectMask[] pPixelMasks,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion,
				pVertexBufferObjectManager);
		if(pTiledTextureRegion.getTileCount() != pPixelMasks.length)
			throw new IllegalArgumentException("The number of PixelMasks must be equal to the number of tiles!");

		mPixelMasks = pPixelMasks;
	}

	public PixelPerfectAnimatedSprite(float pX, float pY, float pWidth,
			float pHeight, PixelPerfectTiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pWidth, pHeight, pTiledTextureRegion,
				pVertexBufferObjectManager);
		final int tileCount = pTiledTextureRegion.getTileCount();
		mPixelMasks = new IPixelPerfectMask[tileCount];
		for(int i = 0; i < tileCount; i++) {
			mPixelMasks[i] = pTiledTextureRegion.getPixelMask(i);
		}
	}
	
	public PixelPerfectAnimatedSprite(float pX, float pY, float pWidth,
			float pHeight, PixelPerfectTiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			DrawType pDrawType, ShaderProgram pShaderProgram) {
		super(pX, pY, pWidth, pHeight, pTiledTextureRegion, pVertexBufferObjectManager,
				pDrawType, pShaderProgram);
		final int tileCount = pTiledTextureRegion.getTileCount();
		mPixelMasks = new IPixelPerfectMask[tileCount];
		for(int i = 0; i < tileCount; i++) {
			mPixelMasks[i] = pTiledTextureRegion.getPixelMask(i);
		}
	}

	@Override
	public IPixelPerfectMask getPixelPerfectMask() {
		return mPixelMasks[getCurrentTileIndex()];
	}

	public IPixelPerfectMask getPixelMask(final int pTileIndex) {
		return mPixelMasks[pTileIndex];
	}

	public boolean collidesWith(final IShape pOtherShape) {
		if(super.collidesWith(pOtherShape))
		{
			if(pOtherShape instanceof IPixelPerfectShape)
				return PixelPerfectCollisionChecker.checkCollision(new ShapeAdapter(this), this.getPixelPerfectMask(), new ShapeAdapter(pOtherShape), ((IPixelPerfectShape)pOtherShape).getPixelPerfectMask());
			else
			{
				if(!(
						USE_PIXELPERFECT_COLLISION_FOR_EVERY_RECTANGULAR_SHAPE &&
						(pOtherShape instanceof Rectangle || pOtherShape instanceof Sprite))
					)
					return true;
				
				RectangularPixelPerfectMaskPool rectangularPixelPerfectMaskPool = RectangularPixelPerfectMaskPool.getInstance();
				
				RectangularPixelPerfectMask reusableRectangularPixelPerfectMask = rectangularPixelPerfectMaskPool.obtainPoolItem();
				reusableRectangularPixelPerfectMask.setTo((int) pOtherShape.getWidth(), (int) pOtherShape.getHeight());
				
				boolean result = PixelPerfectCollisionChecker.checkCollision(new ShapeAdapter(this), this.getPixelPerfectMask(), new ShapeAdapter(pOtherShape), reusableRectangularPixelPerfectMask);
				
				rectangularPixelPerfectMaskPool.recyclePoolItem(reusableRectangularPixelPerfectMask);
				
				return result;
			}
		}
		else
			return false;
	}

	public static void setUsePixelPerfectCollisionForEveryRectangularShape(final boolean pUsePixelPerfectCollisionForEveryRectangularShape) {
		USE_PIXELPERFECT_COLLISION_FOR_EVERY_RECTANGULAR_SHAPE = pUsePixelPerfectCollisionForEveryRectangularShape;
	}

}
