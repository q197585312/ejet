package com.yfc_lib.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yfc_lib.activity.R;
import com.yfc_lib.listener.BaseTopOnClickListener;
import com.yfc_lib.receiver.NetBroadcastReceiver.EventHandler;
import com.yfc_lib.util.NetUtil;

/**
 * 必须引用 layout="@layout/top_bar"
 * 
 * @author yfc
 * 
 */
public abstract class BaseTopFragment extends BaseFragment {
	protected LinearLayout					rootLl;
	protected RelativeLayout				rootRl;
	
	protected RelativeLayout				offLineRl;
	protected TextView						offLineCenterTv;
	protected ImageView						offLineLeftIv;
	protected ImageView						offLineRightIv;
	protected boolean						offLinelisten;
	
	protected RelativeLayout				leftRl;
	protected RelativeLayout				centerRl;
	protected RelativeLayout				nearRightRl;
	protected RelativeLayout				rightRl;
	
	protected TextView						lineTv;
	
	protected TextView						leftTv;
	protected TextView						centerTv;
	protected TextView						nearRightTv;
	protected TextView						rightTv;
	
	protected ImageView						leftIv;
	protected ImageView						centerIv;
	protected ImageView						rightIv;
	
	protected List<BaseTopOnClickListener>	baseTopOnClickListenerList;
	
	protected abstract View onBaseCreateView(LayoutInflater inflater, ViewGroup container,
												Bundle savedInstanceState, View rootView);
	
	public BaseTopFragment() {
		super();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = super.onCreateView(inflater, container, savedInstanceState);
		
		setTopEventHandler(eventHandler);
		
		baseTopOnClickListenerList = new ArrayList<BaseTopOnClickListener>();
		
		rootLl = (LinearLayout) rootView.findViewById(R.id.top_bar_root_ll);
		rootRl = (RelativeLayout) rootView.findViewById(R.id.top_bar_root_rl);
		
		offLineRl = (RelativeLayout) rootView.findViewById(R.id.top_bar_off_line_rl);
		offLineCenterTv = (TextView) rootView.findViewById(R.id.top_bar_off_line_tv);
		offLineLeftIv = (ImageView) rootView.findViewById(R.id.top_bar_off_line_iv1);
		offLineRightIv = (ImageView) rootView.findViewById(R.id.top_bar_off_line_iv2);
		
		rootRl = (RelativeLayout) rootView.findViewById(R.id.top_bar_root_rl);
		
		leftRl = (RelativeLayout) rootView.findViewById(R.id.top_bar_left_rl);
		centerRl = (RelativeLayout) rootView.findViewById(R.id.top_bar_center_rl);
		nearRightRl = (RelativeLayout) rootView.findViewById(R.id.top_bar_near_right_rl);
		rightRl = (RelativeLayout) rootView.findViewById(R.id.top_bar_right_rl);
		
		lineTv = (TextView) rootView.findViewById(R.id.top_bar_line);
		
		leftTv = (TextView) rootView.findViewById(R.id.top_bar_left_tv);
		centerTv = (TextView) rootView.findViewById(R.id.top_bar_center_tv);
		nearRightTv = (TextView) rootView.findViewById(R.id.top_bar_near_right_tv);
		rightTv = (TextView) rootView.findViewById(R.id.top_bar_right_tv);
		
		leftIv = (ImageView) rootView.findViewById(R.id.top_bar_left_iv);
		centerIv = (ImageView) rootView.findViewById(R.id.top_bar_center_iv);
		rightIv = (ImageView) rootView.findViewById(R.id.top_bar_right_iv);
		
		leftRl.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				for (int i = 0; i < baseTopOnClickListenerList.size(); i++) {
					baseTopOnClickListenerList.get(i).OnLeftRlClickListener(v);
				}
			}
		});
		centerRl.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				for (int i = 0; i < baseTopOnClickListenerList.size(); i++) {
					baseTopOnClickListenerList.get(i).OnCenterRlClickListener(v);
				}
			}
		});
		nearRightRl.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				for (int i = 0; i < baseTopOnClickListenerList.size(); i++) {
					baseTopOnClickListenerList.get(i).OnNearRightRlClickListener(v);
				}
			}
		});
		rightRl.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				for (int i = 0; i < baseTopOnClickListenerList.size(); i++) {
					baseTopOnClickListenerList.get(i).OnRightRlClickListener(v);
				}
			}
		});
		
		rootView = onBaseCreateView(inflater, container, savedInstanceState, rootView);
		
		setOffline(NetUtil.getNetworkState(getActivity()));
		
		offLineRl.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				getActivity().startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
			}
		});
		
		return rootView;
	}
	
	protected void setBaseTopOnClickListener(BaseTopOnClickListener baseTopOnClickListener) {
		if (baseTopOnClickListener != null) {
			baseTopOnClickListenerList.add(baseTopOnClickListener);
		}
	}
	
	protected void removeBaseTopOnClickListener(BaseTopOnClickListener baseTopOnClickListener) {
		if (baseTopOnClickListener != null) {
			baseTopOnClickListenerList.remove(baseTopOnClickListener);
		}
	}
	
	protected void show(boolean flag1, boolean flag2, boolean flag3, boolean flag4) {
		if (flag1 || flag2 || flag3 || flag4) {
			rootLl.setVisibility(View.VISIBLE);
		} else {
			rootLl.setVisibility(View.GONE);
		}
		if (flag1) {
			leftRl.setVisibility(View.VISIBLE);
		} else {
			leftRl.setVisibility(View.GONE);
		}
		if (flag2) {
			centerRl.setVisibility(View.VISIBLE);
		} else {
			centerRl.setVisibility(View.GONE);
		}
		if (flag3) {
			nearRightRl.setVisibility(View.VISIBLE);
		} else {
			nearRightRl.setVisibility(View.GONE);
		}
		if (flag4) {
			rightRl.setVisibility(View.VISIBLE);
		} else {
			rightRl.setVisibility(View.GONE);
		}
	}
	
	public void setLeftImage(int id) {
		leftTv.setVisibility(View.GONE);
		leftIv.setVisibility(View.VISIBLE);
		leftIv.setImageResource(id);
	}
	
	public void setCenterImage(int id) {
		centerTv.setVisibility(View.GONE);
		centerIv.setVisibility(View.VISIBLE);
		centerIv.setImageResource(id);
	}
	
	public void setRightImage(int id) {
		rightTv.setVisibility(View.GONE);
		rightIv.setVisibility(View.VISIBLE);
		rightIv.setImageResource(id);
	}
	
	private EventHandler	eventHandler	= new EventHandler() {
												@Override
												public void onNetChange(int state) {
													setOffline(state);
												}
											};
	
	private void setOffline(int state) {
		if (offLinelisten) {
			switch (state) {
				case NetUtil.NETWORN_NONE:
					offLineRl.setVisibility(View.VISIBLE);
					break;
				case NetUtil.NETWORN_MOBILE:
					offLineRl.setVisibility(View.GONE);
					break;
				case NetUtil.NETWORN_WIFI:
					offLineRl.setVisibility(View.GONE);
					break;
				default:
					break;
			}
		} else {
			offLineRl.setVisibility(View.GONE);
		}
	}
	
	public boolean isOffLinelisten() {
		return offLinelisten;
	}
	
	public void setOffLinelisten(boolean offLinelisten) {
		this.offLinelisten = offLinelisten;
		setOffline(NetUtil.getNetworkState(getActivity()));
	}
}
