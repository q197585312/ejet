package com.example.xs_ebay;

import java.util.ArrayList;
import java.util.List;

import com.yfc_lib.util.VolleyUtil;
import com.yfc_lib.volley.toolbox.ImageLoader;
import com.yfc_lib.volley.toolbox.ImageLoader.ImageListener;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;


public class ViewPagerAdapter extends PagerAdapter {
	private ArrayList<String>		pathList;
	private int[]					arrayList;
	private List<RelativeLayout>	pictures;
	private Context					context;
	/**
	 * 
	 * @param pathList
	 *            图片集合
	 * @param context
	 * @param type
	 *            0 --> 网络图片 1 --> 本地图片
	 * @param deleteHandler
	 *            是否需要进入删除图片模式 null --> 不需要 else --> 需要
	 * @param canClick
	 *            是否需要点击进入全屏看图模式
	 */
	@SuppressWarnings("unchecked")
	public ViewPagerAdapter(ArrayList<String> pathList, Context context, int type,
							final Handler deleteHandler) {
		super();
		this.arrayList = null;
		this.pathList = (ArrayList<String>) pathList.clone();
		this.context = context;
		
		pictures = new ArrayList<RelativeLayout>();
		for (final String path : pathList) {
			
			RelativeLayout root = new RelativeLayout(context);
			RelativeLayout.LayoutParams rootParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
			root.setLayoutParams(rootParams);
			
			RelativeLayout imageRl = new RelativeLayout(context);
			RelativeLayout.LayoutParams imageRlParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
			imageRl.setLayoutParams(imageRlParams);
			
			ImageView im = new ImageView(context);
			im.setScaleType(ScaleType.FIT_XY);
			RelativeLayout.LayoutParams imLayoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
			imLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
			im.setLayoutParams(imLayoutParams);
			ImageListener ivListener = ImageLoader.getImageListener(im,
					R.drawable.default_head_img, R.drawable.default_head_img);
				VolleyUtil.getImageLoader(context).get(path, ivListener);
			
			imageRl.addView(im);
			root.addView(imageRl);
			
			if (deleteHandler != null) {
				RelativeLayout rl = new RelativeLayout(context);
				RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(100, 100);
				rlParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				rlParams.topMargin = 90;
				rl.setLayoutParams(rlParams);
				
				ImageView deleteim = new ImageView(context);
				RelativeLayout.LayoutParams deleteimParams = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
				deleteimParams.addRule(RelativeLayout.CENTER_IN_PARENT);
				deleteim.setLayoutParams(deleteimParams);
				
				deleteim.setImageResource(0);
				deleteim.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						deleteHandler.obtainMessage(0, path).sendToTarget();
					}
				});
				
				rl.addView(deleteim);
				root.addView(rl);
			}
			pictures.add(root);
		}
	}
	
	@Override
	public int getCount() {
		if (arrayList != null) {
			return arrayList.length;
		} else if (pathList != null) {
			return pathList.size();
		}
		return 0;
	}
	
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(pictures.get(position));
	}
	
	public static final int	CLICK	= 1001;
	
	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		View view = pictures.get(position);
		
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (onItemClickListener != null) {
					onItemClickListener.OnItemClick(position, v);
				}
			}
		});
		
		container.addView(view);
		
		return view;
	}
	
	private OnItemClickListener	onItemClickListener;
	
	public OnItemClickListener getOnItemClickListener() {
		return onItemClickListener;
	}
	
	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}
	
	public interface OnItemClickListener {
		public void OnItemClick(int position, View v);
	}
}
