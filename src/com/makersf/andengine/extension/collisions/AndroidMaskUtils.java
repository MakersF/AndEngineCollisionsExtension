package com.makersf.andengine.extension.collisions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;

import com.makersf.frameworks.shared.collisioncore.pixelperfect.masks.IPixelPerfectMask;


public class AndroidMaskUtils {

	public static void writeMaskToSDCardAsImage(IPixelPerfectMask pMask, File pFile) throws IOException {
		FileOutputStream fos = new FileOutputStream(pFile);

		Bitmap bmp = Bitmap.createBitmap(pMask.getWidth(), pMask.getHeight(), Bitmap.Config.ARGB_8888);

		for(int x = 0; x < pMask.getWidth(); x++) {
			for(int y = 0; y < pMask.getHeight(); y++) {
				bmp.setPixel(x, y, pMask.isSolid(x, y) ? Color.BLACK : Color.WHITE);
			}
		}

		bmp.compress(CompressFormat.PNG, 100, fos);
		bmp.recycle();
		fos.close();
	}
}
