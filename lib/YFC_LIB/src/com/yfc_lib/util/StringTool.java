package com.yfc_lib.util;

public class StringTool {
	public static String getParameterStr(String... strings) {
		String result = "";
		if (strings != null && strings.length % 2 == 0) {
			for (int i = 0; i < strings.length - 1; i += 2) {
				String key = strings[i];
				String value = strings[i + 1];
				if (key != null && !"".equals(key) && value != null
						&& !"".equals(value)) {
					if (i != 0 && !"".equals(result)) {
						result += "&";
					}
					result += strings[i] + "=" + strings[i + 1];
				}
			}
			if (!"".equals(result)) {
				result = "?" + result;
			}
		}
		return result;
	}
}
