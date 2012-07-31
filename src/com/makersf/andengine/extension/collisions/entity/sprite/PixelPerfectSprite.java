package com.makersf.andengine.extension.collisions.entity.sprite;

import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.vbo.VertexBufferObject.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.makersf.andengine.extension.collisions.entity.shape.IPixelPerfectShape;
import com.makersf.andengine.extension.collisions.opengl.texture.region.PixelPerfectTextureRegion;
import com.makersf.andengine.extension.collisions.pixelperfect.PixelPerfectCollisionChecker;
import com.makersf.andengine.extension.collisions.pixelperfect.IPixelPerfectMask;
import com.makersf.andengine.extension.collisions.pixelperfect.RectangularPixelPerfectMask;

public class PixelPerfectSprite extends Sprite implements IPixelPerfectShape{

	private RectangularPixelPerfectMask mReusableRectangularPixelPerfectMask = new RectangularPixelPerfectMask(0, 0);
	
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
	public IPixelPerfectMask getPixelPerfectMask() {
		return ((PixelPerfectTextureRegion)mTextureRegion).getPixelMask();
	}
	
	public boolean collidesWith(final RectangularShape pOtherShape) {
		if(super.collidesWith(pOtherShape))
		{
			if(pOtherShape instanceof IPixelPerfectShape)
				return PixelPerfectCollisionChecker.checkCollision(this, this.getPixelPerfectMask(), pOtherShape, ((IPixelPerfectShape)pOtherShape).getPixelPerfectMask());
			else
			{
				//syncronized since if you check 2 different RectShapes in 2 different thread, the mReusableRecShape can be changed while it is still used by the other thread
				//but i think it is really unlikely that parellal check can occour, then this syncronization is almost for free
				synchronized (mReusableRectangularPixelPerfectMask) {
					mReusableRectangularPixelPerfectMask.setTo((int) pOtherShape.getWidth(), (int) pOtherShape.getHeight());
					return PixelPerfectCollisionChecker.checkCollision(this, this.getPixelPerfectMask(), pOtherShape, mReusableRectangularPixelPerfectMask);
				}
			}
		}
		else
			return false;
	}

}
