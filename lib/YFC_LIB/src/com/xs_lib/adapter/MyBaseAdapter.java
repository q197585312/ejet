package com.xs_lib.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yfc_lib.activity.R;
import com.yfc_lib.util.VolleyUtil;
import com.yfc_lib.volley.toolbox.ImageLoader;
import com.yfc_lib.widget.CircleImageView;

/**
 * 
 * 
 * @Filename MyBaseAdapter.java
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
public abstract class MyBaseAdapter<T> extends BaseAdapter {

	protected LayoutInflater mInflater;
	private List<T> mList;
	protected Context mContext;
	protected int itemResid;
	private ImageLoader mImageLoader;

	public MyBaseAdapter(Context context, List<T> mList) {
		this(context, mList, R.layout.base_item);
	}

	public MyBaseAdapter(Context context, List<T> mList, int itemResid) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);
		this.setmList(mList);
		this.itemResid = itemResid;
		mImageLoader = VolleyUtil.getImageLoader(context);
	}

	@Override
	public int getCount() {
		int count = getmList().size();
		return count;
	}

	public void addItems(int index, List<T> users) {
		getmList().addAll(index, users);
		notifyDataSetChanged();
	}

	public void addItems(List<T> users) {
		getmList().addAll(users);
		notifyDataSetChanged();
	}

	public void addItem(T user) {
		getmList().add(user);
		notifyDataSetChanged();
	}

	public void updateItems(List<T> users) {
		if (users != null) {
			setmList(users);
			notifyDataSetChanged();
		}
	}

	public Context getContext() {
		return this.mContext;
	}

	@Override
	public T getItem(int position) {
		return getmList().get(position);
	}

	public void updateItem(int index, T item) {
		getmList().remove(index);
		getmList().add(index, item);
		notifyDataSetChanged();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		BSBaseViewHolder holder = null;
		if (convertView == null) {
			view = mInflater.inflate(itemResid, parent, false);
			holder = new BSBaseViewHolder();

			holder.rl1 = (RelativeLayout) view
					.findViewById(R.id.base_item_left_rl);

			holder.tv1 = (TextView) view
					.findViewById(R.id.base_item_left_tv);
			holder.ll1 = (LinearLayout) view
					.findViewById(R.id.base_item_left_ll);
			holder.img1 = (ImageView) view
					.findViewById(R.id.base_item_left_ll_iv);
			holder.cimg1 = (CircleImageView) view
					.findViewById(R.id.base_item_left_ll_civ);
			holder.tv2 = (TextView) view
					.findViewById(R.id.base_item_left_ll_tv1);
			holder.tv3 = (TextView) view
					.findViewById(R.id.base_item_left_ll_tv2);

			holder.rl2 = (RelativeLayout) view
					.findViewById(R.id.base_item_near_right_rl);
			holder.rl3 = (RelativeLayout) view
					.findViewById(R.id.base_item_right_rl);
			holder.tv4 = (TextView) view
					.findViewById(R.id.base_item_near_right_tv);
			holder.img2 = (ImageView) view
					.findViewById(R.id.base_item_near_right_iv);

			holder.tv5 = (TextView) view
					.findViewById(R.id.base_item_right_tv);
			holder.img3 = (ImageView) view
					.findViewById(R.id.base_item_right_iv);

			holder.rl4 = (RelativeLayout) view
					.findViewById(R.id.base_item_bottom_rl);
			holder.tv6 = (TextView) view
					.findViewById(R.id.base_item_bottom_tv);
			inflateAfter(view, holder);
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (BSBaseViewHolder) view.getTag();
		}

		bindData(view, holder, position);
		return view;
	}

	public void inflateAfter(View view, BSBaseViewHolder holder) {
	}

	public LayoutInflater getInflater() {
		return this.mInflater;
	}

	public abstract void bindData(View view, BSBaseViewHolder holder, T model,
			int position);

	public void bindData(View view, BSBaseViewHolder holder, int position) {
		bindData(view, holder, getItem(position), position);
	}

	public List<T> getmList() {
		return mList;
	}

	public void setmList(List<T> mList) {
		this.mList = mList;
		notifyDataSetChanged();
	}

	public class BSBaseViewHolder {

		public TextView tv1, tv2, tv3,tv4,tv5,tv6;
		public ImageView img1, img2, img3;
		public CircleImageView cimg1, cimg2;
		public LinearLayout ll1, ll2;
		public View v1, v2;
		public RelativeLayout rl1, rl2,rl3,rl4;
		public GridView gv1;
		public ProgressBar pb1;
	}

	public void clear() {
		this.mList = new ArrayList<T>();
		notifyDataSetChanged();

	}

}
