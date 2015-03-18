package com.yfc_lib.util.image;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

public class ImageUtil {
	
	public static final int	COMPRESS_SIZE	= 400;
	
	public static String	cacheDirName	= "";
	
	public synchronized static String getCacheDir(Context context) {
		File cacheDir = null;
		// 如果有SD卡则在SD卡中建一个目录存放缓存的图片
		// 没有SD卡就放在系统的缓存目录中
		if (android.os.Environment.getExternalStorageState().equals(
			android.os.Environment.MEDIA_MOUNTED)) {
			// 新建图片缓存目录
			cacheDir = new File(
				android.os.Environment.getExternalStorageDirectory() + cacheDirName, "Images");
		} else {
			cacheDir = context.getCacheDir();
		}
		
		if (!cacheDir.exists() || !cacheDir.isDirectory()) {
			cacheDir.mkdirs();
		}
		return cacheDir.getAbsolutePath();
	}
	
	public synchronized static File saveBitmap(String path, Bitmap mBitmap, boolean flag) {
		File f = null;
		try {
			f = new File(path);
			FileOutputStream fOut = new FileOutputStream(f);
			if (flag) {
				if (!f.exists() || !f.isFile()) {
					f.createNewFile();
				}
				mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
			} else {
				if (!f.exists() || !f.isFile()) {
					f.createNewFile();
					mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
				}
			}
			fOut.flush();
			fOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}
	
	public synchronized static Bitmap getBitmap(File f, int size) {
		Bitmap bm = null;
		final int REQUIRED_SIZE = size;
		try {
			BitmapFactory.Options o2 = null;
			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);
			
			// Find the correct scale value. It should be the power of 2.
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}
			// decode with inSampleSize
			o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			bm = BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return bm;
	}
	
	public synchronized static Bitmap compressImage(Bitmap image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int options = 100;
		image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		
		while (baos.toByteArray().length / 1024 > COMPRESS_SIZE) { // 循环判断如果压缩后图片是否大于指定大小,大于继续压缩
			options -= 5;// 每次都减少5
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			
			if (options <= 0) {
				image.compress(Bitmap.CompressFormat.JPEG, 5, baos);
				break;
			}
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}
	
	/**
	 * 获取图片的后缀名
	 * 
	 * @param url
	 *            图片网址
	 * @return
	 */
	public synchronized static String getImageSuffix(String url) {
		String result = ".png";
		try {
			String[] temp = url.split("/");
			result = temp[temp.length - 1];
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/*
	 * 旋转图片
	 * 
	 * @param angle
	 * 
	 * @param bitmap
	 * 
	 * @return Bitmap
	 */
	private synchronized static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
			bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}
	
	/**
	 * 读取图片属性：旋转的角度
	 * 
	 * @param path
	 *            图片绝对路径
	 * @return degree旋转的角度
	 */
	private synchronized static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
				ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
				default:
					degree = 0;
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}
	
	public synchronized static void handleBitmap(String path) {
		try {
			int degree = readPictureDegree(path);
			if (degree != 0) {
				BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
				bitmapOptions.inSampleSize = 4;
				Bitmap bitmap = BitmapFactory.decodeFile(path, bitmapOptions);
				bitmap = rotaingImageView(degree, bitmap);
				saveBitmap(path, bitmap, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized static String compressImage(String imagePath, Context context) {
		String result = "";
		File f = new File(imagePath);
		if (f.length() / 1024 > ImageUtil.COMPRESS_SIZE) {
			String saveDir = getCacheDir(context);
			String path = f.getAbsolutePath();
			Bitmap bitmap = getBitmap(f, 800);
			bitmap = compressImage(bitmap);
			String saveName = path.hashCode() + getImageSuffix(path);
			File imageFile = saveBitmap(saveDir + File.separator + saveName, bitmap, true);
			result = imageFile.getAbsolutePath();
		} else {
			result = f.getAbsolutePath();
		}
		return result;
	}
	
	public synchronized static boolean checkExists(String path) {
		boolean result = false;
		File f = new File(path);
		if (f.exists() && f.isFile()) {
			result = true;
		}
		return result;
	}
}
