package com.makersf.andengine.extension.collisions.entity.sprite;

import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.vbo.VertexBufferObject.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.makersf.andengine.extension.collisions.entity.shape.IPixelPerfectShape;
import com.makersf.andengine.extension.collisions.opengl.texture.region.PixelPerfectTextureRegion;
import com.makersf.andengine.extension.collisions.pixelperfect.PixelPerfectCollisionChecker;
import com.makersf.andengine.extension.collisions.pixelperfect.PixelPerfectMask;

public class PixelPerfectSprite extends Sprite implements IPixelPerfectShape{

	public PixelPerfectSprite(float pX, float pY, float pWidth, float pHeight,
			PixelPerfectTextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager);
	}

	public PixelPerfectSprite(float pX, float pY, float pWidth, float pHeight,
			PixelPerfectTextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			DrawType pDrawType, ShaderProgram pShaderProgram) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager,
				pDrawType, pShaderProgram);
	}

	@Override
	public PixelPerfectMask getPixelPerfectMask() {
		return ((PixelPerfectTextureRegion)mTextureRegion).getPixelMask();
	}
	
	public <T extends RectangularShape&IPixelPerfectShape>boolean collidesWith(final T pOtherPPShape) {
		return super.collidesWith(pOtherPPShape) && 
				PixelPerfectCollisionChecker.checkCollision(this, this.getPixelPerfectMask(), pOtherPPShape, pOtherPPShape.getPixelPerfectMask());
	}

}
