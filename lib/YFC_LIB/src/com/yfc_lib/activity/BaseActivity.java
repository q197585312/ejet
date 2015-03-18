package com.yfc_lib.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yfc_lib.receiver.NetBroadcastReceiver;
import com.yfc_lib.receiver.NetBroadcastReceiver.EventHandler;
import com.yfc_lib.util.ActivityUtil;
import com.yfc_lib.util.AppTools;
import com.yfc_lib.util.NetUtil;
import com.yfc_lib.widget.LoadingDialog;
import com.yfc_lib.widget.LoadingToast;

/**
 * 
 * @author yfc
 * 
 */
public abstract class BaseActivity extends FragmentActivity {
	protected abstract int setBaseContentView();
	
	protected InputMethodManager	inputManager;
	
	protected LoadingDialog			dialog;
	protected LoadingToast			toast;
	
	private EventHandler			topEventHandler;
	
	protected LinearLayout			centerLoadingLl;
	protected ProgressBar			centerLoadingPb;
	protected ImageView				centerLoadingIv;
	protected TextView				centerLoadingTv;
	private View					hiddenView;
	
	public EventHandler getTopEventHandler() {
		return topEventHandler;
	}
	
	public void setTopEventHandler(EventHandler topEventHandler) {
		this.topEventHandler = topEventHandler;
	}
	
	public void showLoading(View hiddenView) {
		showLoading("正在努力加载...", hiddenView);
	}
	
	public void showLoadError(int imgId, String msg) {
		try {
			centerLoadingLl.setVisibility(View.VISIBLE);
			centerLoadingIv.setVisibility(View.VISIBLE);
			centerLoadingPb.setVisibility(View.GONE);
			centerLoadingIv.setImageResource(imgId);
			centerLoadingTv.setText(msg);
		} catch (Exception e) {
		}
	}
	
	public void showLoading(String msg, View hiddenView) {
		try {
			if (hiddenView != null) {
				this.hiddenView = hiddenView;
				this.hiddenView.setVisibility(View.GONE);
			}
			centerLoadingLl.setVisibility(View.VISIBLE);
			centerLoadingIv.setVisibility(View.GONE);
			centerLoadingPb.setVisibility(View.VISIBLE);
			centerLoadingTv.setText(msg);
		} catch (Exception e) {
		}
	}
	
	public void hiddenLoading() {
		try {
			if (hiddenView != null) {
				hiddenView.setVisibility(View.VISIBLE);
			}
			centerLoadingLl.setVisibility(View.GONE);
		} catch (Exception e) {
		}
	}
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		setContentView(setBaseContentView());
		
		inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		
		ActivityUtil.addToList(this);
		
		NetBroadcastReceiver.mListeners.add(eventHandler);
		
		try {
			centerLoadingLl = (LinearLayout) findViewById(R.id.base_center_loading_ll);
			centerLoadingPb = (ProgressBar) findViewById(R.id.base_center_loading_pb);
			centerLoadingIv = (ImageView) findViewById(R.id.base_center_loading_iv);
			centerLoadingTv = (TextView) findViewById(R.id.base_center_loading_tv);
		} catch (Exception e) {
		}
	}
	
	@Override
	protected void onDestroy() {
		NetBroadcastReceiver.mListeners.remove(eventHandler);
		super.onDestroy();
	}
	
	private boolean	isNeedExit;
	
	public boolean isNeedExit() {
		return isNeedExit;
	}
	
	public void setNeedExit(boolean isNeedExit) {
		this.isNeedExit = isNeedExit;
	}
	
	@Override
	public void onBackPressed() {
		if (isNeedExit) {
			if (AppTools.exit()) {
				showTips(0, "再按一次退出程序");
			} else {
				ActivityUtil.exit();
			}
		} else {
			super.onBackPressed();
		}
	}
	
	private EventHandler	eventHandler	= new EventHandler() {
												@Override
												public void onNetChange(int state) {
													if (topEventHandler != null) {
														topEventHandler.onNetChange(state);
													}
													switch (state) {
														case NetUtil.NETWORN_NONE:
															showTips(R.drawable.tips_error,
																"请检查您的网络连接");
															break;
														case NetUtil.NETWORN_MOBILE:
															showTips(0, "连接到数据网络");
															break;
														case NetUtil.NETWORN_WIFI:
															showTips(0, "连接到无线网络");
															break;
														default:
															break;
													}
												}
											};
	
	/**
	 * showTips(R.drawable.tips_success, (String) msg.obj);
	 * 
	 * @param iconResId
	 * @param msgResId
	 */
	public void showTips(int iconResId, String msgResId) {
		if (toast != null) {
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				toast.cancel();
			}
		} else {
			toast = LoadingToast.makeText(this, msgResId, LoadingToast.LENGTH_SHORT);
		}
		toast.show();
		toast.setIcon(iconResId);
		toast.setText(msgResId);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			// 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
			View v = getCurrentFocus();
			if (isShouldHideInput(v, ev)) {
				hideSoftInput(v.getWindowToken());
			}
		}
		return super.dispatchTouchEvent(ev);
	}
	
	/**
	 * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
	 * 
	 * @param v
	 * @param event
	 * @return
	 */
	public boolean isShouldHideInput(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] l = { 0, 0 };
			v.getLocationInWindow(l);
			int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
			if (event.getX() > left && event.getX() < right && event.getY() > top
				&& event.getY() < bottom) {
				// 点击EditText的事件，忽略它。
				return false;
			} else {
				return true;
			}
		}
		// 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
		return false;
	}
	
	/**
	 * 多种隐藏软件盘方法的其中一种
	 * 
	 * @param token
	 */
	public void hideSoftInput(IBinder token) {
		if (token != null) {
			InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
}
