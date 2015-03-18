package com.yfc_lib.receiver;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yfc_lib.util.NetUtil;

public class NetBroadcastReceiver extends BroadcastReceiver {
	public static ArrayList<EventHandler>	mListeners			= new ArrayList<EventHandler>();
	private static String					NET_CHANGE_ACTION	= "android.net.conn.CONNECTIVITY_CHANGE";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(NET_CHANGE_ACTION)) {
			int state = NetUtil.getNetworkState(context);
			// 通知接口完成加载
			if (mListeners.size() > 0) {
				for (EventHandler handler : mListeners) {
					handler.onNetChange(state);
				}
			}
		}
	}
	
	public static interface EventHandler {
		public void onNetChange(int state);
	}
}
