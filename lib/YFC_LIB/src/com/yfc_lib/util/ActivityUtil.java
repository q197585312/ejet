package com.yfc_lib.util;

import java.util.ArrayList;

import android.app.Activity;

public class ActivityUtil {
	private static ArrayList<Activity> activityList;

	public static void addToList(Activity activity) {
		try {
			if (activityList == null) {
				activityList = new ArrayList<Activity>();
			}
			boolean flag = true;
			for (int i = 0; i < activityList.size(); i++) {
				Activity a = activityList.get(i);
				if (a != null && a.equals(activity)) {
					flag = false;
					break;
				}
			}
			if (flag) {
				activityList.add(activity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void exit() {
		try {
			for (int i = 0; i < activityList.size(); i++) {
				Activity a = activityList.get(i);
				if (a != null) {
					a.finish();
				}
			}
			activityList = null;
			// System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
