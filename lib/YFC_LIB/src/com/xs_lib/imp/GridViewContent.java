package com.xs_lib.imp;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;

import com.xs_lib.adapter.MyBaseAdapter;

/**
 * 
 *                       
 * @Filename ContentView.java
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

public class GridViewContent<T> implements ContentViewImp<T> {
	protected GridView			lv;
	protected MyBaseAdapter<T>	listAdapter;
	protected Context			context;
	int							rid;
	int							page;
	int							total;
	private List<T>				list;
	private BaseAdapterImp<T>	adapterImp;
	
	@Override
	public void setView(Context context, View view) {
		this.context = context;
		this.lv = (GridView) view;
		page = 1;
		
	}
	
	public void setItemIdAndBaseAdapter(int rid, BaseAdapterImp<T> adapterImp) {
		this.rid = rid;
		this.adapterImp = adapterImp;
		initDate();
	}
	
	public void setBaseAdapter(BaseAdapterImp<T> adapterImp) {
		this.adapterImp = adapterImp;
	}
	
	private void initDate() {
		listAdapter = new MyBaseAdapter<T>(context, new ArrayList<T>(), rid) {
			
			@Override
			public void bindData(	View view,
									MyBaseAdapter.BSBaseViewHolder holder,
									T model, int position) {
				adapterImp.bindData(view, holder, model, position);
			}
			
			@Override
			public void inflateAfter(View view, BSBaseViewHolder holder) {
				super.inflateAfter(view, holder);
				adapterImp.inflateAfter(view, holder);
			}
			
		};
		lv.setAdapter(listAdapter);
		
	}
	
	public void setItemClick(final ItemCLickImp<T> itemclick) {
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				T modle = (T) parent.getItemAtPosition(position);
				itemclick.itemCLick(modle, position);
			}
		});
	}
	
	@Override
	public void setData(Object obj) {
		List<T> listData = (List<T>) obj;
		listAdapter.setmList(listData);
		
	}
	public void updateItem(int poistion,T item){
		listAdapter.updateItem(poistion, item);
	}
	
	public void AddData(List<T> listData) {
		listAdapter.addItems(listData);
	}
	
	public void clearAdapter() {
		listAdapter.clear();
	}
	
	public void setThread(ThreadImp thread) {
		thread.startThread(null);
	}
	
	public void setItemId(int itemId) {
		this.rid = itemId;
	}
	
}
