package com.xs_lib.imp;

import android.content.Context;
import android.view.View;

/**
 * 
 *                       
 * @Filename ContentViewImp.java
 *
 * @Description 
 *
 * @Version 1.0
 *
 * @Author xs
 *
 * @Email 197585312@qq.com
 *       
 * @History
 *<li>Author: xs</li>
 *<li>Date: 2015年1月27日</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public interface ContentViewImp<T> {
	public void setView(Context context, View view);
	
	public void setData(Object obj);
	
	public void setThread(ThreadImp thread);
}
