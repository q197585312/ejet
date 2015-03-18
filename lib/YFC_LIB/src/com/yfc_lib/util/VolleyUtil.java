package com.yfc_lib.util;

import android.content.Context;

import com.yfc_lib.cache.BitmapCache;
import com.yfc_lib.volley.RequestQueue;
import com.yfc_lib.volley.toolbox.ImageLoader;
import com.yfc_lib.volley.toolbox.Volley;

public class VolleyUtil {
	private volatile static RequestQueue	requestQueue;
	private volatile static ImageLoader		imageLoader;
	
	/** 返回RequestQueue单例 **/
	public static RequestQueue getQueue(Context context) {
		synchronized (VolleyUtil.class) {
			if (requestQueue == null) {
				requestQueue = Volley.newRequestQueue(context.getApplicationContext());
			}
		}
		return requestQueue;
	}
	
	/** 返回ImageLoader单例 **/
	public static ImageLoader getImageLoader(Context context) {
		synchronized (VolleyUtil.class) {
			if (imageLoader == null) {
				imageLoader = new ImageLoader(VolleyUtil.getQueue(context), new BitmapCache());
			}
		}
		return imageLoader;
	}
}
