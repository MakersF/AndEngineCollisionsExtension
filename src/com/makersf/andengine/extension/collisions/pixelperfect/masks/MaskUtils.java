package com.makersf.andengine.extension.collisions.pixelperfect.masks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;

/**
 * 
 * @author Francesco Zoffoli
 * @since 01.08.2012
 *
 */
public class MaskUtils {

	public static void writeMaskToSDCard(IPixelPerfectMask pMask, String pRelativePathFromSDRoot, String pFileName) {
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			File sdCard = Environment.getExternalStorageDirectory();
			File dir = new File (sdCard.getAbsolutePath() + pRelativePathFromSDRoot);
			dir.mkdirs();
			File file = new File(dir, pFileName + ".png");
			try {
				FileOutputStream fos = new FileOutputStream(file);

				Bitmap bmp = Bitmap.createBitmap(pMask.getWidth(), pMask.getHeight(), Bitmap.Config.ARGB_8888);

				for(int x = 0; x < pMask.getWidth(); x++) {
					for(int y = 0; y < pMask.getHeight(); y++) {
						bmp.setPixel(x, y, pMask.isSolid(x, y) ? Color.BLACK : Color.WHITE);
					}
				}

				bmp.compress(CompressFormat.PNG, 100, fos);
				bmp.recycle();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
