package com.yfc_lib.util.image;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.yfc_lib.choose_multiple_image.ImgFileListActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

public class ChooseImageUtil {
	private static ImageFileCache ifc;

	@SuppressLint("InlinedApi")
	public static Intent getChooseImageOneIntent() {
		Intent intent = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
		} else {
			intent = new Intent();
		}

		intent.setType("image/*");
		intent.putExtra("return-data", true);
		intent.setAction(Intent.ACTION_GET_CONTENT);
		return intent;
	}

	public static Intent getChooseImageMultipleIntent(Context context,
			int maxSelectCount) {
		Intent intent = new Intent(context, ImgFileListActivity.class);
		if (maxSelectCount > 0) {
			intent.putExtra(ImgFileListActivity.MAX_SELECT_COUNT,
					maxSelectCount);
		}
		return intent;
	}

	public static CaptureImageBean getCaptureImageIntent(Context context) {
		if (ifc == null) {
			ifc = new ImageFileCache(context);
			PackageManager pm = context.getPackageManager();
			String cacheDirName = context.getApplicationInfo().loadLabel(pm)
					.toString();
			ifc.setCacheDir(new File(cacheDirName));
		}

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		String imagePath = ifc.getCacheDir().getAbsolutePath() + File.separator
				+ getNowDate() + ".png";

		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(imagePath)));

		CaptureImageBean cib = new ChooseImageUtil().new CaptureImageBean(
				imagePath, intent);
		return cib;
	}

	private static String getNowDate() {
		return new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH)
				.format(new Date());
	}

	public class CaptureImageBean implements Serializable {
		private static final long serialVersionUID = 1L;
		private String path;
		private Intent Intent;

		public CaptureImageBean() {
			super();
		}

		public CaptureImageBean(String path, android.content.Intent intent) {
			super();
			this.path = path;
			Intent = intent;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public Intent getIntent() {
			return Intent;
		}

		public void setIntent(Intent intent) {
			Intent = intent;
		}
	}
}
