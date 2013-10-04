package com.makersf.andengine.extension.collisions.pixelperfect.masks;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.andengine.util.adt.DataConstants;

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

	private static final int HEADER_INTESTATION = ('M' << 24) | ('A' << 16) | ('S' << 8) | ('K');

	public static void writeMaskToSDCardAsImage(IPixelPerfectMask pMask, String pRelativePathFromSDRoot, String pFileName) throws IOException {
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
				throw e;
			}
		} else {
			throw new IOException("The external storage is not mounted. " + state);
		}
	}

	public static void writeMaskToSDCardAsBytes(IPixelPerfectMask pMask, String pRelativePathFromSDRoot, String pFileName) throws IOException {
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			File sdCard = Environment.getExternalStorageDirectory();
			File dir = new File (sdCard.getAbsolutePath() + pRelativePathFromSDRoot);
			dir.mkdirs();
			File file = new File(dir, pFileName);
			try {
				FileOutputStream fos = new FileOutputStream(file);
				DataOutputStream dos = new DataOutputStream(fos);

				ByteBuffer byteBuffer = ByteBuffer.allocate(pMask.getWidth() * pMask.getHeight() / DataConstants.BITS_PER_BYTE + 4 * DataConstants.BYTES_PER_INT);
				byteBuffer.position(0);

				int headerInfo;
				//an int in order to store version, flags or other information in the future
				/*
				 * first byte = version number
				 * 23° bit = endianess (1 = big endian)
				 * the remaining 22 bit are free for future use.
				 */
				headerInfo = (1 << 24) | (1 << 23);
				byteBuffer.putInt(HEADER_INTESTATION);
				byteBuffer.putInt(headerInfo);
				byteBuffer.putInt(pMask.getWidth());
				byteBuffer.putInt(pMask.getHeight());

				int counter = 0;
				byte container = 0;
				for(int x = 0; x < pMask.getWidth(); x++) {
					for(int y = 0; y < pMask.getHeight(); y++) {
						container += (pMask.isSolid(x, y) ? 1 : 0) << counter;
						counter++;
						if(counter > 7) {
							counter = 0;
							byteBuffer.put(container);
							container = 0;
						}
					}
				}

				dos.write(byteBuffer.array());
				dos.close();
			} catch (IOException e) {
				throw e;
			}
		} else {
			throw new IOException("The external storage is not mounted. " + state);
		}
	}

	public static IPixelPerfectMask readMaskFromSDCardAsBytes(String pRelativePathFromSDRoot, String pFileName, IPixelPerfectMaskFactory pFactory) throws IOException {
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			File sdCard = Environment.getExternalStorageDirectory();
			File file = new File (sdCard.getAbsolutePath() + File.separator + pRelativePathFromSDRoot + File.separator + pFileName);
			try {
				FileInputStream fis = new FileInputStream(file);
				DataInputStream dis = new DataInputStream(fis);

				final int headerIntestation = dis.readInt();
				if(headerIntestation != HEADER_INTESTATION) {
					dis.close();
					throw new IOException("The file is not a valid mask file.");
				}

				final int headerInfos = dis.readInt();
				if((headerInfos & 0xff000000) >> 24 != 1) {
					dis.close();
					throw new IOException("Unsupported mask version.");
				}

				final int width = dis.readInt();
				final int height = dis.readInt();

				byte[] byteArray = new byte[width * height / DataConstants.BITS_PER_BYTE];
				dis.read(byteArray);
				dis.close();

				ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);

				return pFactory.getIPixelPerfectMask(width, height, byteBuffer);
			} catch (IOException e) {
				throw e;
			}
		} else {
			throw new IOException("The external storage is not mounted. " + state);
		}
	}

	public static IPixelPerfectMask readMaskFromSDCardAsBytes(String pRelativePathFromSDRoot, String pFileName) throws IOException {
		return readMaskFromSDCardAsBytes(pRelativePathFromSDRoot, pFileName, new PixelPerfectMaskFactory());
	}

	public static boolean compare(IPixelPerfectMask pA, IPixelPerfectMask pB) {
		if(pA.getWidth() != pB.getWidth() || pA.getHeight() != pB.getHeight())
			return false;
		for(int x = 0; x < pA.getWidth(); x++)
			for(int y = 0; y < pA.getHeight(); y++)
				if(pA.isSolid(x, y) != pB.isSolid(x, y))
					return false;
		return true;
	}
}
