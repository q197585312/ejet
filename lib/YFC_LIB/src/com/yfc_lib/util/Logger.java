package com.yfc_lib.util;

public class Logger {
	public static String	tag	= "yfc_lib";
	
	public static void e(String msg) {
		try {
			android.util.Log.e(tag, msg);
		} catch (Exception e) {
			Logger.e(e.getMessage());
		}
	}
	
	public static void i(String msg) {
		try {
			android.util.Log.i(tag, msg);
		} catch (Exception e) {
			Logger.e(e.getMessage());
		}
	}
	
	public static void d(String msg) {
		try {
			android.util.Log.d(tag, msg);
		} catch (Exception e) {
			Logger.e(e.getMessage());
		}
	}
	
	public static void e(String tag, String msg) {
		try {
			android.util.Log.e(tag, msg);
		} catch (Exception e) {
			Logger.e(e.getMessage());
		}
	}
	
	public static void i(String tag, String msg) {
		try {
			android.util.Log.i(tag, msg);
		} catch (Exception e) {
			Logger.e(e.getMessage());
		}
	}
	
	public static void d(String tag, String msg) {
		try {
			android.util.Log.d(tag, msg);
		} catch (Exception e) {
			Logger.e(e.getMessage());
		}
	}
}
