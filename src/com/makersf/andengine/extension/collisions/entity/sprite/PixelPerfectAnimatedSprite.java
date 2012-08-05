package com.makersf.andengine.extension.collisions.entity.sprite;

import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.vbo.VertexBufferObject.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.makersf.andengine.extension.collisions.entity.shape.IPixelPerfectShape;
import com.makersf.andengine.extension.collisions.opengl.texture.region.PixelPerfectTiledTextureRegion;
import com.makersf.andengine.extension.collisions.pixelperfect.PixelPerfectCollisionChecker;
import com.makersf.andengine.extension.collisions.pixelperfect.masks.IPixelPerfectMask;
import com.makersf.andengine.extension.collisions.pixelperfect.masks.RectangularPixelPerfectMask;
import com.makersf.andengine.extension.collisions.pixelperfect.masks.RectangularPixelPerfectMaskPool;

/**
 * 
 * @author Francesco Zoffoli
 * @since 01.08.2012
 *
 */
public class PixelPerfectAnimatedSprite extends AnimatedSprite implements IPixelPerfectShape{
	
	public PixelPerfectAnimatedSprite(float pX, float pY, PixelPerfectTiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion,
				pVertexBufferObjectManager);
	}
	
	public PixelPerfectAnimatedSprite(float pX, float pY, float pWidth,
			float pHeight, PixelPerfectTiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pWidth, pHeight, pTiledTextureRegion,
				pVertexBufferObjectManager);
	}
	
	public PixelPerfectAnimatedSprite(float pX, float pY, float pWidth,
			float pHeight, PixelPerfectTiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			DrawType pDrawType, ShaderProgram pShaderProgram) {
		super(pX, pY, pWidth, pHeight, pTiledTextureRegion, pVertexBufferObjectManager,
				pDrawType, pShaderProgram);
	}

	@Override
	public IPixelPerfectMask getPixelPerfectMask() {
		return ((PixelPerfectTiledTextureRegion)mTextureRegion).getPixelMask(getCurrentTileIndex());
	}

	public IPixelPerfectMask getPixelMask(final int pTileIndex) {
		return ((PixelPerfectTiledTextureRegion)mTextureRegion).getPixelMask(pTileIndex);
	}

	public boolean collidesWith(final RectangularShape pOtherShape) {
		if(super.collidesWith(pOtherShape))
		{
			if(pOtherShape instanceof IPixelPerfectShape)
				return PixelPerfectCollisionChecker.checkCollision(this, this.getPixelPerfectMask(), pOtherShape, ((IPixelPerfectShape)pOtherShape).getPixelPerfectMask());
			else
			{
				RectangularPixelPerfectMaskPool rectangularPixelPerfectMaskPool = RectangularPixelPerfectMaskPool.getInstance();
				
				RectangularPixelPerfectMask reusableRectangularPixelPerfectMask = rectangularPixelPerfectMaskPool.obtainPoolItem();
				reusableRectangularPixelPerfectMask.setTo((int) pOtherShape.getWidth(), (int) pOtherShape.getHeight());
				
				boolean result = PixelPerfectCollisionChecker.checkCollision(this, this.getPixelPerfectMask(), pOtherShape, reusableRectangularPixelPerfectMask);
				
				rectangularPixelPerfectMaskPool.recyclePoolItem(reusableRectangularPixelPerfectMask);
				
				return result;
			}
		}
		else
			return false;
	}
}
