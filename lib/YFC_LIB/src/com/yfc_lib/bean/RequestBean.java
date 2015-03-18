package com.yfc_lib.bean;

import java.util.Map;

import com.yfc_lib.volley.Request;

public class RequestBean {
	int method = Request.Method.POST;
	String url = "";
	Map<String, String> params;

	public int getMethod() {
		return method;
	}

	public void setMethod(int method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public RequestBean(int method, String url, Map<String, String> params) {
		super();
		this.method = method;
		this.url = url;
		this.params = params;
	}

	public RequestBean(String url, Map<String, String> params) {
		super();
		this.url = url;
		this.params = params;
	}

}
