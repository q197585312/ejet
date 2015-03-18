package com.yfc_lib.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.MimeTypeMap;

import com.handmark.pulltorefresh.library.LoadingLayoutProxy;
import com.handmark.pulltorefresh.library.PullToRefreshAdapterViewBase;

public class AppTools {
	public static void initListViewData(PullToRefreshAdapterViewBase<?> listView) {
		LoadingLayoutProxy loadingLayoutProxy = (LoadingLayoutProxy) listView
			.getLoadingLayoutProxy();
		loadingLayoutProxy.setTheType(0);
		
		loadingLayoutProxy.setPullLoadingDrawableVisibility(View.VISIBLE);
		loadingLayoutProxy.setPullLabel("下拉刷新");
		loadingLayoutProxy.setPullImageResource(0);
		
		loadingLayoutProxy.setRefreshingLoadingDrawableVisibility(View.VISIBLE);
		loadingLayoutProxy.setReleaseLabel("放开以刷新...");
		loadingLayoutProxy.setReleaseImageResource(0);
		
		loadingLayoutProxy.setReleaseLoadingDrawableVisibility(View.VISIBLE);
		loadingLayoutProxy.setRefreshingLabel("正在刷新");
		loadingLayoutProxy.setRefreshingImageResource(0);
		
		loadingLayoutProxy.setTheType(1);
		
		loadingLayoutProxy.setPullLoadingDrawableVisibility(View.VISIBLE);
		loadingLayoutProxy.setPullLabel("上拉加载更多");
		loadingLayoutProxy.setPullImageResource(0);
		
		loadingLayoutProxy.setRefreshingLoadingDrawableVisibility(View.VISIBLE);
		loadingLayoutProxy.setReleaseLabel("放开以加载更多…");
		loadingLayoutProxy.setReleaseImageResource(0);
		
		loadingLayoutProxy.setReleaseLoadingDrawableVisibility(View.VISIBLE);
		loadingLayoutProxy.setRefreshingLabel("正在加载");
		loadingLayoutProxy.setRefreshingImageResource(0);
	}
	
	/**
	 * 去除特殊字符或将所有中文标号替换为英文标号
	 * 
	 * @param str
	 * @return
	 */
	public static String stringFilter(String str) {
		str = str.replaceAll("【", "[").replaceAll("】", "]").replaceAll("！", "!")
			.replaceAll("：", ":");// 替换中文标号
		String regEx = "[『』]"; // 清除掉特殊字符
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
	
	/**
	 * 半角转换为全角
	 * 
	 * @param input
	 * @return
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return stringFilter(new String(c));
	}
	
	public static boolean saveObjectData(String name, Object obj, Context context) {
		boolean result = false;
		try {
			FileOutputStream stream = context.openFileOutput(name, Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(stream);
			oos.writeObject(obj);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static Object getObjectData(String name, Context context) {
		Object obj = null;
		try {
			FileInputStream stream = context.openFileInput(name);
			ObjectInputStream ois = new ObjectInputStream(stream);
			obj = (Object) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
		Bitmap output = null;
		try {
			output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(output);
			canvas.drawARGB(0, 0, 0, 0);
			
			final int color = 0xFFD3C9C2;
			final Paint paint = new Paint();
			paint.setAntiAlias(true);
			paint.setColor(color);
			
			final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
			final RectF rectF = new RectF(rect);
			
			canvas.drawRoundRect(rectF, pixels, pixels, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);
			paint.setXfermode(null);
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		return output;
	}
	
	public static Bitmap toRectangle(Bitmap bitmap) {
		Bitmap output = null;
		try {
			output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(output);
			canvas.drawARGB(0, 0, 0, 0);
			
			final int color = 0xFF919191;
			final Paint paint = new Paint();
			paint.setAntiAlias(true);
			paint.setStyle(Paint.Style.STROKE);
			paint.setColor(color);
			
			final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
			final Rect rect2 = new Rect(0, 0, bitmap.getWidth() - 1, bitmap.getHeight() - 1);
			
			canvas.drawRect(rect, paint);
			canvas.drawBitmap(bitmap, rect2, rect2, paint);
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		return output;
	}
	
	/***
	 * 取得起止日期间的天数集合
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static List<String> getDayList(String startDate, String endDate) {
		String dateFormat = "yyyy-MM-dd";
		SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
		List<String> al = new ArrayList<String>();
		if (startDate.equals(endDate)) {
			// IF起始日期等于截止日期,仅返回起始日期一天
			al.add(startDate);
		} else if (startDate.compareTo(endDate) < 0) {
			// IF起始日期早于截止日期,返回起止日期的每一天
			while (startDate.compareTo(endDate) < 0) {
				al.add(startDate);
				try {
					Long l = format.parse(startDate).getTime();
					startDate = format.format(l + 3600 * 24 * 1000);// +1天
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			// IF起始日期晚于截止日期,仅返回起始日期一天
			al.add(startDate);
		}
		return al;
	}
	
	public static int getDaysByMonth(String time) {
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy年MM月", Locale.ENGLISH);
		try {
			rightNow.setTime(simpleDate.parse(time));
		} catch (Exception e) {
			e.printStackTrace();
		}
		int days = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);
		return days;
	}
	
	public static List<Integer> getWeekDay(String str) {
		List<Integer> list = new ArrayList<Integer>();
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int week = c.get(Calendar.WEEK_OF_MONTH);
		int nReturn = c.get(Calendar.DAY_OF_WEEK) - 1;
		list.add(week);
		list.add(nReturn);
		return list;
	}
	
	public static int getScreenWidth(Activity act) {
		DisplayMetrics metric = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric.widthPixels;
	}
	
	public static float getDensity(Activity act) {
		DisplayMetrics metric = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric.density;
	}
	
	public static int getScreenHeight(Activity act) {
		DisplayMetrics metric = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric.heightPixels;
	}
	
	public static String getNowDate() {
		return new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH).format(new Date());
	}
	
	/**
	 * 发送广播
	 * 
	 * @param context
	 * @param type
	 */
	public static void sendBroadcast(Context context, String type) {
		Intent mIntent = new Intent(type);
		context.sendBroadcast(mIntent);
	}
	
	/**
	 * 获取版本号
	 * 
	 * @return 当前应用的版本号
	 */
	public static int getVersionCode(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * 
	 * @param url
	 * @param context
	 */
	@SuppressLint("NewApi")
	public static boolean downloadApk(String url, Context context) {
		boolean result = false;
		try {
			DownloadManager downloadManager = (DownloadManager) context
				.getSystemService(Context.DOWNLOAD_SERVICE);
			
			Uri resource = Uri.parse(url);
			Request request = new Request(resource);
			MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton(); // 获取文件类型实例
			String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap
				.getFileExtensionFromUrl(url)); // 获取文件类型
			request.setMimeType(mimeString); // 制定下载文件类型
			request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE
											| DownloadManager.Request.NETWORK_WIFI);
			
			request.setVisibleInDownloadsUi(true);
			
			int index = url.lastIndexOf("/");
			String fname = url.substring(index + 1); // 获取文件名
			request.setDestinationInExternalPublicDir("/download/", fname);
			
			// 制定下载的目录里
			
			long id = downloadManager.enqueue(request);
			saveApkDownloadId(id, context);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void saveApkDownloadId(long id, Context context) {
		SharedPreferences preferences = context.getSharedPreferences("HST_Merchant_UserInfo",
			Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putLong("apkDownloadId", id);
		editor.commit();
	}
	
	public static long queryApkDownloadId(Context context) {
		SharedPreferences preferences = context.getSharedPreferences("HST_Merchant_UserInfo",
			Context.MODE_PRIVATE);
		return preferences.getLong("apkDownloadId", 0);
	}
	
	public static void installApk(String path, Context context) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.fromFile(new File(path)),
			"application/vnd.android.package-archive");
		context.startActivity(intent);
	}
	
	/**
	 * 检测网络是否连接
	 * 
	 * @return
	 */
	public static boolean isNetConnected(Context c) {
		if (c == null)
			return true;
		ConnectivityManager cm = (ConnectivityManager) c
			.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm != null) {
			NetworkInfo[] infos = cm.getAllNetworkInfo();
			if (infos != null) {
				for (NetworkInfo ni : infos) {
					if (ni.isConnected()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static boolean exit() {
		boolean result = false;
		if ((System.currentTimeMillis() - Constants.EXIT_TIME) > Constants.EXIT_MAX_TIME) {
			Constants.EXIT_TIME = System.currentTimeMillis();
			result = true;
		}
		return result;
	}
	
	public static boolean isNeedAlert(Context context, String type) {
		boolean result = false;
		SharedPreferences preferences = context.getSharedPreferences(type, Context.MODE_PRIVATE);
		result = preferences.getBoolean("flag", true);
		if (result) {
			Editor editor = preferences.edit();
			editor.putBoolean("flag", false);
			editor.commit();
		}
		return result;
	}
	
}
