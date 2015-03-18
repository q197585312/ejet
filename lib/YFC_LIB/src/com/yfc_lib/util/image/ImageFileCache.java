package com.yfc_lib.util.image;

import java.io.File;
import java.io.Serializable;

import android.content.Context;

public class ImageFileCache implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private File cacheDir;
	private Context context;

	private String cacheDirName;

	public File getCacheDir() {
		// 如果有SD卡则在SD卡中建一个目录存放缓存的图片
		// 没有SD卡就放在系统的缓存目录中
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			// 新建图片缓存目录
			cacheDir = new File(
					android.os.Environment.getExternalStorageDirectory()
							+ File.separator + cacheDirName, "Images");
		} else {
			cacheDir = context.getCacheDir();
		}

		if (!cacheDir.exists() || !cacheDir.isDirectory()) {
			cacheDir.mkdirs();
		}
		return cacheDir;
	}

	public void setCacheDir(File cacheDir) {
		this.cacheDir = cacheDir;
	}

	public ImageFileCache(Context context) {
		this.context = context;
	}

	public void clear(File cacheDir) {
		File[] files = cacheDir.listFiles();
		if (files == null)
			return;
		for (File f : files)
			f.delete();
	}
}
