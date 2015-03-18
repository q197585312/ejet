package com.xs_lib.imp;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xs_lib.adapter.MyBaseAdapter;
import com.xs_lib.adapter.MyBaseAdapter.BSBaseViewHolder;
import com.yfc_lib.activity.R;
import com.yfc_lib.widget.LoadingToast;

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

public class RefreshListViewContent<T> implements ContentViewImp<T> {
	private PullToRefreshListView	plt;
	private MyBaseAdapter<T>		listAdapter;
	private Context					context;
	int								rid;
	int								page;
	int								total;
	private List<T>					list;
	private ThreadImp				thread;
	private BaseAdapterImp<T>		adapterImp;
	
	@Override
	public void setView(Context context, View view) {
		this.context = context;
		this.plt = (PullToRefreshListView) view;
		page = 1;
		plt.setOnRefreshListener(onRefreshListener);
	}
	
	public List<T> getList() {
		return list;
	}
	
	public void setList(List<T> list) {
		this.list = list;
		setAdapter(page, list);
		listAdapter.notifyDataSetChanged();
	}
	
	public int getTotal() {
		return total;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}
	
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	
	public void setItemIdAndBaseAdapter(int rid, BaseAdapterImp<T> adapterImp) {
		this.rid = rid;
		this.adapterImp = adapterImp;
		initDate();
	}
	
	public void setBaseAdapter(BaseAdapterImp<T> adapterImp) {
		this.adapterImp = adapterImp;
	}
	
	public void initDate() {
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
		plt.setAdapter(listAdapter);
	}
	
	public void setItemClick(final ItemCLickImp<T> itemclick) {
		plt.setOnItemClickListener(new OnItemClickListener() {
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
		list = listData;
		listAdapter.setmList(listData);
	}
	
	public void AddData(List<T> listData) {
		listAdapter.addItems(listData);
	}
	
	public void updateItem(int poistion, T item) {
		listAdapter.updateItem(poistion, item);
	}
	
	public void clearAdapter() {
		listAdapter.clear();
	}
	
	@Override
	public void setThread(ThreadImp thread) {
		this.thread = thread;
		thread.startThread(1);
		
	}
	
	public void setItemId(int itemId) {
		this.rid = itemId;
	}
	
	public void setAdapter(int page, List<T> list) {
		if (page == 1) {
			listAdapter.setmList(list);
		} else if (page > 1) {
			listAdapter.addItems(list);
		} else {
			listAdapter.clear();
		}
	}
	
	public void successList(int total, List<T> list) {
		plt.onRefreshComplete();
		setAdapter(page, list);
		this.total = total;
		this.page = page++;
		if (total > listAdapter.getCount()) {
			plt.setMode(Mode.BOTH);
		} else {
			plt.setMode(Mode.PULL_FROM_START);
		}
		
	}
	
	public void error(String msg) {
		plt.onRefreshComplete();
	}
	
	public void getThreadData(int page) {
		thread.startThread(page);
	}
	
	/**
	 * listview 刷新
	 * 
	 */
	private OnRefreshListener<ListView>	onRefreshListener	= new OnRefreshListener<ListView>() {
																@Override
																public void onRefresh(	PullToRefreshBase<ListView> refreshView) {
																	if (refreshView.isHeaderShown()) {
																		page = 1;
																		getThreadData(page);
																	} else {
																		// 上拉加载更多 业务代码
																		if (total > listAdapter
																			.getCount()) {
																			page++;
																			getThreadData(page);
																		} else {
																			handler
																				.sendEmptyMessage(0);
																		}
																	}
																}
																
															};
	@SuppressLint("HandlerLeak")
	private Handler						handler				= new Handler() {
																public void handleMessage(	android.os.Message msg) {
																	switch (msg.what) {
																		case 0:
																			showTips(
																				R.drawable.tips_smile,
																				"最后一页了");
																			plt.onRefreshComplete();
																			break;
																		default:
																			break;
																	}
																};
															};
	protected LoadingToast				toast;
	
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
			toast = LoadingToast.makeText(context, msgResId, LoadingToast.LENGTH_SHORT);
		}
		toast.show();
		toast.setIcon(iconResId);
		toast.setText(msgResId);
	}
	
}
