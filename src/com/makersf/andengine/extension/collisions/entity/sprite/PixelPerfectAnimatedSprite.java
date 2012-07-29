package com.makersf.andengine.extension.collisions.entity.sprite;

import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.vbo.VertexBufferObject.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.makersf.andengine.extension.collisions.entity.shape.IPixelPerfectShape;
import com.makersf.andengine.extension.collisions.opengl.texture.region.PixelPerfectTiledTextureRegion;
import com.makersf.andengine.extension.collisions.pixelperfect.PixelPerfectCollisionChecker;
import com.makersf.andengine.extension.collisions.pixelperfect.PixelPerfectMask;

public class PixelPerfectAnimatedSprite extends AnimatedSprite implements IPixelPerfectShape{

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
	public PixelPerfectMask getPixelPerfectMask() {
		return ((PixelPerfectTiledTextureRegion)mTextureRegion).getPixelMask(getCurrentTileIndex());
	}

	public <T extends RectangularShape&IPixelPerfectShape>boolean collidesWith(final T pOtherPPShape) {
		return super.collidesWith(pOtherPPShape) && 
				PixelPerfectCollisionChecker.checkCollision(this, this.getPixelPerfectMask(), pOtherPPShape, pOtherPPShape.getPixelPerfectMask());
	}
}