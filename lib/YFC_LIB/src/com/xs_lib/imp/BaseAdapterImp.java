package com.xs_lib.imp;

import android.view.View;

import com.xs_lib.adapter.MyBaseAdapter;
import com.xs_lib.adapter.MyBaseAdapter.BSBaseViewHolder;

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
 * @History <li>Author: xs</li> <li>Date: 2015年1月27日</li> <li>Version: 1.0</li>
 *          <li>Content: create</li>
 * 
 */
public interface BaseAdapterImp<T> {
	public void bindData(View view, MyBaseAdapter.BSBaseViewHolder holder,
			T model, int position);

	public void inflateAfter(View view, BSBaseViewHolder holder);

}
