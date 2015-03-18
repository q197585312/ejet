package com.yfc_lib.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yfc_lib.activity.R;
import com.yfc_lib.receiver.NetBroadcastReceiver;
import com.yfc_lib.receiver.NetBroadcastReceiver.EventHandler;
import com.yfc_lib.util.NetUtil;
import com.yfc_lib.widget.LoadingDialog;
import com.yfc_lib.widget.LoadingToast;

/**
 * 
 * @author yfc
 * 
 */
public abstract class BaseFragment extends Fragment {
	protected LoadingDialog	dialog;
	protected LoadingToast	toast;
	
	private EventHandler	topEventHandler;
	
	protected LinearLayout	centerLoadingLl;
	protected ProgressBar	centerLoadingPb;
	protected ImageView		centerLoadingIv;
	protected TextView		centerLoadingTv;
	private View			hiddenView;
	
	protected abstract int setBaseContentView();
	
	public EventHandler getTopEventHandler() {
		return topEventHandler;
	}
	
	public void setTopEventHandler(EventHandler topEventHandler) {
		this.topEventHandler = topEventHandler;
	}
	
	public BaseFragment() {
		super();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		NetBroadcastReceiver.mListeners.add(eventHandler);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(setBaseContentView(), container, false);
		try {
			centerLoadingLl = (LinearLayout) rootView.findViewById(R.id.base_center_loading_ll);
			centerLoadingPb = (ProgressBar) rootView.findViewById(R.id.base_center_loading_pb);
			centerLoadingIv = (ImageView) rootView.findViewById(R.id.base_center_loading_iv);
			centerLoadingTv = (TextView) rootView.findViewById(R.id.base_center_loading_tv);
		} catch (Exception e) {
		}
		return rootView;
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
			toast = LoadingToast.makeText(getActivity(), msgResId, LoadingToast.LENGTH_SHORT);
		}
		toast.show();
		toast.setIcon(iconResId);
		toast.setText(msgResId);
	}
	
	private EventHandler	eventHandler	= new EventHandler() {
												@Override
												public void onNetChange(int state) {
													if (topEventHandler != null) {
														topEventHandler.onNetChange(state);
													}
												}
											};
}
