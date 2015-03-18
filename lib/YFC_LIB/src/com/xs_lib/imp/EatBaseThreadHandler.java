package com.xs_lib.imp;

import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yfc_lib.activity.R;
import com.yfc_lib.bean.BaseResponse;
import com.yfc_lib.bean.RequestBean;
import com.yfc_lib.util.AppTools;
import com.yfc_lib.util.VolleyUtil;
import com.yfc_lib.volley.AuthFailureError;
import com.yfc_lib.volley.Response.ErrorListener;
import com.yfc_lib.volley.Response.Listener;
import com.yfc_lib.volley.VolleyError;
import com.yfc_lib.volley.toolbox.CacheStringRequest;

public abstract class EatBaseThreadHandler<T> implements ThreadImp {
	
	protected boolean				isLoading	= false;
	protected Context				context;
	private PullToRefreshListView	plv;
	
	public EatBaseThreadHandler(Context context) {
		super();
		this.context = context;
	}
	
	public EatBaseThreadHandler(Context context, PullToRefreshListView plv) {
		super();
		this.context = context;
		this.plv = plv;
	}
	
	@Override
	public void startThread(Object obj) {
		if (isLoading)
			return;
		if (!AppTools.isNetConnected(context)) {
			errorEnd("没有网络");
		}
		threadStart(obj);
	}
	
	@Override
	public void threadStart(Object obj) {
		final RequestBean bean = getRequestBean();
		getHttpData(obj, bean);
		if (plv != null)
			plv.setRefreshing(true);
		isLoading = true;
	}
	
	@Override
	public void errorEnd(Object obj) {
		isLoading = false;
		if (plv != null)
			plv.setRefreshing(false);
	}
	
	protected void getHttpData(final Object obj, final RequestBean bean) {
		if (bean == null) {
			return;
		}
		
		CacheStringRequest infoRequest = new CacheStringRequest(bean.getMethod(), bean.getUrl(),
			new Listener<String>() {
				@Override
				public void onResponse(String arg0) {
					BaseResponse<T> response = analyze(arg0);
					if (response != null && response.getStatus() == 200)
						successEnd(response);
				}
			}, new ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError arg0) {
					String error = "网络连接错误";
					errorEnd(error);
				}
			}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = bean.getParams();
				if (obj != null) {
					int page = (Integer) obj;
					if (page > 0) {
						params.put("size", "10");
						params.put("page", page + "");
					}
				}
				super.setCacheParams(params);
				return params;
			}
		};
		infoRequest.setTag(context);
		VolleyUtil.getQueue(context).add(infoRequest);
	}
	
	protected abstract BaseResponse<T> analyze(String arg0);
	
	protected RequestBean getRequestBean() {
		return null;
	}
	
	@Override
	public void successEnd(Object obj) {
		isLoading = false;
		hhh.sendEmptyMessage(0);
		if (obj != null) {
			BaseResponse<T> respone = (BaseResponse<T>) obj;
			successListEnd(respone);
		}
	}
	
	@SuppressLint("HandlerLeak")
	private Handler	hhh	= new Handler() {
							public void handleMessage(android.os.Message msg) {
								switch (msg.what) {
									case 0:
										if (plv != null) {
											plv.setRefreshing(false);
											plv.onRefreshComplete();
										}
										break;
									default:
										break;
								}
							};
						};
	
	protected abstract void successListEnd(BaseResponse<T> respone);
	
	public void setRequestBean(RequestBean reqBean, Object obj) {
		getHttpData(obj, reqBean);
	}
}
