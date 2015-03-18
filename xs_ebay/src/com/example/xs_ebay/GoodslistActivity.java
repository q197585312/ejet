package com.example.xs_ebay;

import java.util.HashMap;
import java.util.Map;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.xs_lib.adapter.MyBaseAdapter.BSBaseViewHolder;
import com.xs_lib.imp.BaseAdapterImp;
import com.xs_lib.imp.EatBaseThreadHandler;
import com.xs_lib.imp.GridViewContent;
import com.xs_lib.imp.ItemCLickImp;
import com.xs_lib.imp.ListViewContent;
import com.yfc_lib.bean.BaseResponse;
import com.yfc_lib.bean.RequestBean;
import com.yfc_lib.util.HttpAnalyze;
import com.yfc_lib.util.Logger;
import com.yfc_lib.util.StringTool;
import com.yfc_lib.util.VolleyUtil;
import com.yfc_lib.volley.toolbox.ImageLoader;
import com.yfc_lib.volley.toolbox.ImageLoader.ImageListener;

public class GoodslistActivity extends BaseTopActivity {
	CategoryBean topBean;
	Map<String, CategoryBean> topHistory = new HashMap<String, CategoryBean>();
	ListViewContent listString;
	GridViewContent<CategoryBean> gvContent;

	@ViewInject(R.id.my_list_content)
	ListView list;
	@ViewInject(R.id.goods_list_gv)
	GridView gv;
	@ViewInject(R.id.goods_list_top_categray)
	TextView top_categray_tv;

	@Override
	protected int setBaseContentView() {
		return R.layout.goodslist;
	}

	@Override
	protected void init() {
		super.init();
		top_categray_tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				gotoTopClick(arg0);

			}
		});
		leftTv.setText("first");
		gvContent = new GridViewContent<CategoryBean>();
		gvContent.setView(this, gv);
		listString = new ListViewContent<String>();
		listString.setView(this, list);
		listString.setItemIdAndBaseAdapter(android.R.layout.simple_list_item_1,
				new BaseAdapterImp<CategoryBean>() {
					@Override
					public void bindData(View view, BSBaseViewHolder holder,
							CategoryBean model, int position) {
						holder.tv1.setText(model.getCategoryName());
						holder.tv1.setTextColor(getResources().getColor(
								R.color.word_black_bold));
					}

					@Override
					public void inflateAfter(View view, BSBaseViewHolder holder) {
						holder.tv1 = (TextView) view
								.findViewById(android.R.id.text1);
					}
				});

		getCategryList(null);
		listString.setItemClick(new ItemCLickImp<CategoryBean>() {
			@Override
			public void itemCLick(CategoryBean t, int position) {
				topHistory.put(t.getCategoryID(), topBean);
				topBean = t;
				getCategryList(t);
			}
		});
	}

	protected void gotoTopClick(View v) {
		if (topBean == null) {

		} else {
			topBean = topHistory.get(topBean.getCategoryID());
		}
		getCategryList(topBean);
	}

	protected void getCategryList(final CategoryBean bean) {
		if (bean != null)
			top_categray_tv.setText(bean.getCategoryName() + "");
		else
			top_categray_tv.setText("all categray");
		listString.setThread(new EatBaseThreadHandler<CategoryBean>(context) {

			@Override
			protected BaseResponse<CategoryBean> analyze(String arg0) {
				BaseResponse<CategoryBean> response = null;
				try {
					response = new HttpAnalyze().analyze(arg0,
							new TypeToken<BaseResponse<CategoryBean>>() {
							}.getType());
				} catch (Exception e) {
					Logger.e(e.getMessage());
					errorEnd(e.getMessage());
					return null;
				}
				return response;
			}

			@Override
			protected RequestBean getRequestBean() {
				Map<String, String> map = new HashMap<String, String>();
				String Parameter = "";
				if (bean != null) {
					Parameter = StringTool.getParameterStr("categoryid", ""
							+ bean.getCategoryID());
				}
				return new RequestBean("http://192.168.0.200:8008/getcategory/"
						+ Parameter, map);
			}

			@Override
			protected void successListEnd(BaseResponse<CategoryBean> respone) {
				if (respone.getMess().equals("Product")) {
					if (list.getVisibility() == View.VISIBLE)
						list.setVisibility(View.GONE);
					if (gv.getVisibility() == View.GONE)
						gv.setVisibility(View.VISIBLE);
					getGvProduct(respone);
				} else {
					if (list.getVisibility() == View.GONE)
						list.setVisibility(View.VISIBLE);
					if (gv.getVisibility() == View.VISIBLE)
						gv.setVisibility(View.GONE);
					listString.setData(respone.getList());
				}

			}
		});
	}

	protected void getGvProduct(BaseResponse<CategoryBean> respone) {

		gvContent.setItemIdAndBaseAdapter(R.layout.gv_item_iv_base,
				new BaseAdapterImp<CategoryBean>() {
					@Override
					public void bindData(View view, BSBaseViewHolder holder,
							CategoryBean model, int position) {
						holder.ll1.setVisibility(View.VISIBLE);
						holder.tv1.setText("MQQ:" + model.getMOQ());
						holder.tv2.setText("Price:" + model.getPrice());
						holder.tv1.setTextColor(getResources().getColor(
								R.color.word_orange));
						holder.tv2.setTextColor(getResources().getColor(
								R.color.word_orange));
						ImageListener ivListener = ImageLoader
								.getImageListener(holder.img1,
										R.drawable.default_head_img,
										R.drawable.default_head_img);
						VolleyUtil.getImageLoader(context).get(
								model.getProductPic(), ivListener);
					}

					@Override
					public void inflateAfter(View view, BSBaseViewHolder holder) {
						holder.img1 = (ImageView) view
								.findViewById(R.id.gv_item_iv1);
						holder.ll1 = (LinearLayout) view
								.findViewById(R.id.gv_item_bottom_ll);
						holder.tv1 = (TextView) view
								.findViewById(R.id.gv_item_bottom_tv1);
						holder.tv2 = (TextView) view
								.findViewById(R.id.gv_item_bottom_tv2);

					}
				});
		gvContent.setData(respone.getList());
	}
}
