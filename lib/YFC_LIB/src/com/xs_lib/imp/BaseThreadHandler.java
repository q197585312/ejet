package com.xs_lib.imp;

import java.util.Map;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.yfc_lib.bean.RequestBean;
import com.yfc_lib.util.AppTools;
import com.yfc_lib.util.HttpAnalyze;
import com.yfc_lib.util.Logger;
import com.yfc_lib.util.VolleyUtil;
import com.yfc_lib.volley.AuthFailureError;
import com.yfc_lib.volley.Response.ErrorListener;
import com.yfc_lib.volley.Response.Listener;
import com.yfc_lib.volley.VolleyError;
import com.yfc_lib.volley.toolbox.CacheStringRequest;

public abstract class BaseThreadHandler<T> implements ThreadImp {
	protected boolean	isLoading	= false;
	protected Context	context;
	
	public BaseThreadHandler(Context context) {
		super();
		this.context = context;
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
	
	protected void getHttpData(final Object obj, final RequestBean bean) {
		if (bean == null) {
			return;
		}
		CacheStringRequest infoRequest = new CacheStringRequest(bean.getMethod(), bean.getUrl(),
			new Listener<String>() {
				@Override
				public void onResponse(String arg0) {
					Logger.d("Success", arg0);
					T response = analyze(arg0);
					if (response != null)
						successEnd(response);
				}
			}, new ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError arg0) {
					String error = arg0.getMessage() == null ? "" : arg0.getMessage();
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
	
	protected T analyze(String arg0) {
		HttpAnalyze analyze = new HttpAnalyze();
		T respone = null;
		try {
			respone = analyze.analyze(arg0, new TypeToken<T>() {
			}.getType());
		} catch (Exception e) {
			errorEnd(e);
			respone = null;
		}
		return respone;
		
	}
	
	@Override
	public void threadStart(Object obj) {
		isLoading = true;
		RequestBean bean = getRequestBean();
		getHttpData(obj, bean);
		
	}
	
	protected RequestBean getRequestBean() {
		return null;
	}
	
	@Override
	public void successEnd(Object obj) {
		isLoading = false;
		successResponeEnd((T) obj);
	}
	
	protected abstract void successResponeEnd(T respone);
	
	@Override
	public void errorEnd(Object obj) {
		isLoading = false;
	}
	
	public void setRequestBean(RequestBean reqBean, Object obj) {
		getHttpData(obj, reqBean);
	}
}
