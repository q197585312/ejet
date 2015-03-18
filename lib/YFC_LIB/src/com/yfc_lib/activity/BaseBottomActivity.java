package com.yfc_lib.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yfc_lib.listener.BaseBottomOnClickListener;

/**
 * 必须引用 layout="@layout/top_bar" layout="@layout/bottom_bar"
 * 
 * @author yfc
 * 
 */
public abstract class BaseBottomActivity extends BaseActivity {
	protected abstract ArrayList<BottomData> setBottomData();
	
	// bottom
	protected LinearLayout						bottomRootRl;
	protected RelativeLayout					rl1;
	protected RelativeLayout					rl2;
	protected RelativeLayout					rl3;
	protected RelativeLayout					rl4;
	protected RelativeLayout					rl5;
	
	protected TextView							lineTv;
	
	protected TextView							tv1;
	protected TextView							tv2;
	protected TextView							tv3;
	protected TextView							tv4;
	protected TextView							tv5;
	
	protected ImageView							iv1;
	protected ImageView							iv2;
	protected ImageView							iv3;
	protected ImageView							iv4;
	protected ImageView							iv5;
	
	protected List<BaseBottomOnClickListener>	baseBottomOnClickListenerList;
	
	protected ArrayList<BottomData>				bottomDataList;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		baseBottomOnClickListenerList = new ArrayList<BaseBottomOnClickListener>();
		
		try {
			centerLoadingLl = (LinearLayout) findViewById(R.id.base_center_loading_ll);
			centerLoadingPb = (ProgressBar) findViewById(R.id.base_center_loading_pb);
			centerLoadingTv = (TextView) findViewById(R.id.base_center_loading_tv);
		} catch (Exception e) {
		}
		
		bottomRootRl = (LinearLayout) findViewById(R.id.bottom_bar_root_ll);
		
		rl1 = (RelativeLayout) findViewById(R.id.bottom_bar_rl1);
		rl2 = (RelativeLayout) findViewById(R.id.bottom_bar_rl2);
		rl3 = (RelativeLayout) findViewById(R.id.bottom_bar_rl3);
		rl4 = (RelativeLayout) findViewById(R.id.bottom_bar_rl4);
		rl5 = (RelativeLayout) findViewById(R.id.bottom_bar_rl5);
		
		lineTv = (TextView) findViewById(R.id.bottom_bar_line);
		
		tv1 = (TextView) findViewById(R.id.bottom_bar_tv1);
		tv2 = (TextView) findViewById(R.id.bottom_bar_tv2);
		tv3 = (TextView) findViewById(R.id.bottom_bar_tv3);
		tv4 = (TextView) findViewById(R.id.bottom_bar_tv4);
		tv5 = (TextView) findViewById(R.id.bottom_bar_tv5);
		
		iv1 = (ImageView) findViewById(R.id.bottom_bar_iv1);
		iv2 = (ImageView) findViewById(R.id.bottom_bar_iv2);
		iv3 = (ImageView) findViewById(R.id.bottom_bar_iv3);
		iv4 = (ImageView) findViewById(R.id.bottom_bar_iv4);
		iv5 = (ImageView) findViewById(R.id.bottom_bar_iv5);
		
		bottomDataList = setBottomData();
		
		if (bottomDataList != null && bottomDataList.size() > 0) {
			setBottomVisibility();
			if (bottomDataList.get(0).getImageClick() < 0) {
				changeBottomType(true);
				setBottomText();
				changeBottomTextShowType(true, true, true, true, true);
			} else {
				changeBottomType(false);
				changeBottomImageShowType(true, true, true, true, true);
			}
			
			rl1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					for (int i = 0; i < baseBottomOnClickListenerList.size(); i++) {
						baseBottomOnClickListenerList.get(i).OnRL1ClickListener(v);
					}
				}
			});
			rl2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					for (int i = 0; i < baseBottomOnClickListenerList.size(); i++) {
						baseBottomOnClickListenerList.get(i).OnRL2ClickListener(v);
					}
				}
			});
			rl3.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					for (int i = 0; i < baseBottomOnClickListenerList.size(); i++) {
						baseBottomOnClickListenerList.get(i).OnRL3ClickListener(v);
					}
				}
			});
			rl4.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					for (int i = 0; i < baseBottomOnClickListenerList.size(); i++) {
						baseBottomOnClickListenerList.get(i).OnRL4ClickListener(v);
					}
				}
			});
			rl5.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					for (int i = 0; i < baseBottomOnClickListenerList.size(); i++) {
						baseBottomOnClickListenerList.get(i).OnRL5ClickListener(v);
					}
				}
			});
		}
		
	}
	
	private void changeBottomType(boolean flag) {
		tv1.setVisibility(View.GONE);
		tv2.setVisibility(View.GONE);
		tv3.setVisibility(View.GONE);
		tv4.setVisibility(View.GONE);
		tv5.setVisibility(View.GONE);
		
		iv1.setVisibility(View.GONE);
		iv2.setVisibility(View.GONE);
		iv3.setVisibility(View.GONE);
		iv4.setVisibility(View.GONE);
		iv5.setVisibility(View.GONE);
		if (flag) {
			tv1.setVisibility(View.VISIBLE);
			tv2.setVisibility(View.VISIBLE);
			tv3.setVisibility(View.VISIBLE);
			tv4.setVisibility(View.VISIBLE);
			tv5.setVisibility(View.VISIBLE);
		} else {
			iv1.setVisibility(View.VISIBLE);
			iv2.setVisibility(View.VISIBLE);
			iv3.setVisibility(View.VISIBLE);
			iv4.setVisibility(View.VISIBLE);
			iv5.setVisibility(View.VISIBLE);
		}
	}
	
	private void setBottomText() {
		tv1.setText(bottomDataList.get(0).getText());
		tv2.setText(bottomDataList.get(1).getText());
		tv3.setText(bottomDataList.get(2).getText());
		tv4.setText(bottomDataList.get(3).getText());
		tv5.setText(bottomDataList.get(4).getText());
	}
	
	private void setBottomVisibility() {
		for (int i = 0; i < bottomDataList.size(); i++) {
			BottomData bottomData = bottomDataList.get(i);
			switch (i) {
				case 0:
					rl1.setVisibility(bottomData.getVisibility());
					break;
				case 1:
					rl2.setVisibility(bottomData.getVisibility());
					break;
				case 2:
					rl3.setVisibility(bottomData.getVisibility());
					break;
				case 3:
					rl4.setVisibility(bottomData.getVisibility());
					break;
				case 4:
					rl5.setVisibility(bottomData.getVisibility());
					break;
				default:
					break;
			}
		}
	}
	
	protected void changeBottomImageShowType(boolean a, boolean b, boolean c, boolean d, boolean e) {
		if (a) {
			iv1.setImageResource(bottomDataList.get(0).getImageNotClick());
		} else {
			iv1.setImageResource(bottomDataList.get(0).getImageClick());
		}
		if (b) {
			iv2.setImageResource(bottomDataList.get(1).getImageNotClick());
		} else {
			iv2.setImageResource(bottomDataList.get(1).getImageClick());
		}
		if (c) {
			iv3.setImageResource(bottomDataList.get(2).getImageNotClick());
		} else {
			iv3.setImageResource(bottomDataList.get(2).getImageClick());
		}
		if (d) {
			iv4.setImageResource(bottomDataList.get(3).getImageNotClick());
		} else {
			iv4.setImageResource(bottomDataList.get(3).getImageClick());
		}
		if (e) {
			iv5.setImageResource(bottomDataList.get(4).getImageNotClick());
		} else {
			iv5.setImageResource(bottomDataList.get(4).getImageClick());
		}
	}
	
	private ColorStateList getColorStateList(boolean flag, int index) {
		ColorStateList csl = null;
		if (flag) {
			csl = (ColorStateList) getResources().getColorStateList(
				bottomDataList.get(index).getTextColorNotClick());
		} else {
			csl = (ColorStateList) getResources().getColorStateList(
				bottomDataList.get(index).getTextColorClick());
		}
		return csl;
	}
	
	protected void changeBottomTextShowType(boolean a, boolean b, boolean c, boolean d, boolean e) {
		tv1.setTextColor(getColorStateList(a, 0));
		if (a) {
			tv1.setCompoundDrawablesWithIntrinsicBounds(0, bottomDataList.get(0)
				.getTextImgNotClick(), 0, 0);
		} else {
			tv1.setCompoundDrawablesWithIntrinsicBounds(0, bottomDataList.get(0).getTextImgClick(),
				0, 0);
		}
		tv2.setTextColor(getColorStateList(b, 1));
		if (b) {
			tv2.setCompoundDrawablesWithIntrinsicBounds(0, bottomDataList.get(1)
				.getTextImgNotClick(), 0, 0);
		} else {
			tv2.setCompoundDrawablesWithIntrinsicBounds(0, bottomDataList.get(1).getTextImgClick(),
				0, 0);
		}
		tv3.setTextColor(getColorStateList(c, 2));
		if (c) {
			tv3.setCompoundDrawablesWithIntrinsicBounds(0, bottomDataList.get(2)
				.getTextImgNotClick(), 0, 0);
		} else {
			tv3.setCompoundDrawablesWithIntrinsicBounds(0, bottomDataList.get(2).getTextImgClick(),
				0, 0);
		}
		tv4.setTextColor(getColorStateList(d, 3));
		if (d) {
			tv4.setCompoundDrawablesWithIntrinsicBounds(0, bottomDataList.get(3)
				.getTextImgNotClick(), 0, 0);
		} else {
			tv4.setCompoundDrawablesWithIntrinsicBounds(0, bottomDataList.get(3).getTextImgClick(),
				0, 0);
		}
		tv5.setTextColor(getColorStateList(e, 4));
		if (e) {
			tv5.setCompoundDrawablesWithIntrinsicBounds(0, bottomDataList.get(4)
				.getTextImgNotClick(), 0, 0);
		} else {
			tv5.setCompoundDrawablesWithIntrinsicBounds(0, bottomDataList.get(4).getTextImgClick(),
				0, 0);
		}
	}
	
	protected void setBaseBottomOnClickListener(BaseBottomOnClickListener baseOnClickListener) {
		if (baseOnClickListener != null) {
			baseBottomOnClickListenerList.add(baseOnClickListener);
		}
	}
	
	protected void removeBaseBottomOnClickListener(BaseBottomOnClickListener baseOnClickListener) {
		if (baseOnClickListener != null) {
			baseBottomOnClickListenerList.remove(baseOnClickListener);
		}
	}
	
	public class BottomData implements Serializable {
		
		/**
		 * 
		 */
		private static final long	serialVersionUID	= 1L;
		private String				text;
		private int					imageNotClick;
		private int					imageClick;
		private int					textColorNotClick;
		private int					textColorClick;
		private int					textImgNotClick;
		private int					textImgClick;
		private int					visibility;
		
		public BottomData() {
			super();
		}
		
		public BottomData(String text, int imageNotClick, int imageClick, int textColorNotClick,
							int textColorClick, int textImgNotClick, int textImgClick,
							int visibility) {
			super();
			this.text = text;
			this.imageNotClick = imageNotClick;
			this.imageClick = imageClick;
			this.textColorNotClick = textColorNotClick;
			this.textColorClick = textColorClick;
			this.textImgNotClick = textImgNotClick;
			this.textImgClick = textImgClick;
			this.visibility = visibility;
		}
		
		public String getText() {
			return text;
		}
		
		public void setText(String text) {
			this.text = text;
		}
		
		public int getImageNotClick() {
			return imageNotClick;
		}
		
		public void setImageNotClick(int imageNotClick) {
			this.imageNotClick = imageNotClick;
		}
		
		public int getImageClick() {
			return imageClick;
		}
		
		public void setImageClick(int imageClick) {
			this.imageClick = imageClick;
		}
		
		public int getTextColorNotClick() {
			return textColorNotClick;
		}
		
		public void setTextColorNotClick(int textColorNotClick) {
			this.textColorNotClick = textColorNotClick;
		}
		
		public int getTextColorClick() {
			return textColorClick;
		}
		
		public void setTextColorClick(int textColorClick) {
			this.textColorClick = textColorClick;
		}
		
		public int getTextImgNotClick() {
			return textImgNotClick;
		}
		
		public void setTextImgNotClick(int textImgNotClick) {
			this.textImgNotClick = textImgNotClick;
		}
		
		public int getTextImgClick() {
			return textImgClick;
		}
		
		public void setTextImgClick(int textImgClick) {
			this.textImgClick = textImgClick;
		}
		
		public int getVisibility() {
			return visibility;
		}
		
		public void setVisibility(int visibility) {
			this.visibility = visibility;
		}
	}
}
