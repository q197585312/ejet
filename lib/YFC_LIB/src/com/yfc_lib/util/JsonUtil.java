package com.yfc_lib.util;

import com.google.gson.Gson;

public class JsonUtil {
	public synchronized static <T> T analyze(String result, Class<T> c) {
		return new Gson().fromJson(result, c);
	}
}
