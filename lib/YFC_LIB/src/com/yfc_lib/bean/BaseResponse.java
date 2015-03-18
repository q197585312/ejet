package com.yfc_lib.bean;

import java.io.Serializable;
import java.util.List;

public class BaseResponse<T> implements Serializable {

	/**
	 * { "status": 403, // 状态码(200,为成功,403为失败，404为接口没找到，500为服务器异常) "mess": "",
	 * // 错误或提示消息(可用以登录注册界面的提示信息) "time": 1414121748212, // 本次请求返回的时间 "size": 0,
	 * // 本次请求结果包含数据总记录数 "total": 0, // 本次请求总共数据记录数（用于分页） "list": [{},...] //
	 * 所有返回对象存放位置 }
	 */
	private static final long serialVersionUID = 1L;
	/** 错误或提示消息(可用以登录注册界面的提示信息) */
	protected String mess;
	/** 状态码(200,为成功,403为失败，404为接口没找到，500为服务器异常 */
	protected int status;
	/** 本次请求返回的时间 */
	protected String time;
	/** 本次请求结果包含数据总记录数 */
	protected int size;
	/** 本次请求总共数据记录数（用于分页） */
	protected int total;
	/** 所有返回对象存放位置 */
	List<T> list;

	public String getMess() {
		return mess;
	}

	public void setMess(String mess) {
		this.mess = mess;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
