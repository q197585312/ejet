package com.example.xs_ebay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.viewpagerindicator.CirclePageIndicator;
import com.xiao.oia.view.MyListView;
import com.xs_lib.adapter.MyBaseAdapter.BSBaseViewHolder;
import com.xs_lib.imp.BaseAdapterImp;
import com.xs_lib.imp.EatBaseThreadHandler;
import com.xs_lib.imp.ListViewContent;
import com.yfc_lib.bean.BaseResponse;
import com.yfc_lib.bean.RequestBean;
import com.yfc_lib.util.HttpAnalyze;
import com.yfc_lib.util.Logger;
import com.yfc_lib.util.StringTool;
import com.yfc_lib.util.Constants.KEY;

public class GoodsDetailActivity extends BaseTopActivity {
	ListViewContent<Attr> listString;
	@ViewInject(R.id.detail_list_attribute)
	MyListView mylv;
	@ViewInject(R.id.detail_top_vp)
	ViewPager top_vp;
	@ViewInject(R.id.detail_bottom_vp)
	ViewPager bottom_vp;
	@ViewInject(R.id.detail_top_cpi)
	CirclePageIndicator top_cpi;
	@ViewInject(R.id.detail_bottom_cpi)
	CirclePageIndicator bottom_cpi;
	CategoryBean bean;
	@Override
	protected int setBaseContentView() {
		return R.layout.activity_goods_details;
	}

	@Override
	protected void init() {
		super.init();
		bean = (CategoryBean) getIntent().getSerializableExtra(
				KEY.INTENT_DATA_KEY);
		getDetails(bean);
		leftTv.setText("detail");
		listString = new ListViewContent<Attr>();
		listString.setView(this, mylv);
		listString.setItemIdAndBaseAdapter(R.layout.list_item_text2,
				new BaseAdapterImp<Attr>() {
					@Override
					public void bindData(View view, BSBaseViewHolder holder,
							Attr model, int position) {
						holder.tv1.setText(model.getAttrName()+":");
						holder.tv2.setText(model.getAttrValue());
					}

					@Override
					public void inflateAfter(View view, BSBaseViewHolder holder) {
						holder.tv1 = (TextView) view
								.findViewById(R.id.text_left);
						holder.tv2 = (TextView) view
								.findViewById(R.id.text_right);
					}
				});
	
	}

	private void getDetails(final CategoryBean bean) {
		EatBaseThreadHandler<GoodsDetailBean> threadDetail = new EatBaseThreadHandler<GoodsDetailBean>(
				context) {

			@Override
			protected void successListEnd(BaseResponse<GoodsDetailBean> respone) {
				showDetail(respone);
			}

			@Override
			protected BaseResponse<GoodsDetailBean> analyze(String arg0) {
				BaseResponse<GoodsDetailBean> response = null;
				try {
					response = new HttpAnalyze().analyze(arg0,
							new TypeToken<BaseResponse<GoodsDetailBean>>() {
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
					Parameter = StringTool.getParameterStr("Itemno",
							"" + bean.getItemNo());
				}
				return new RequestBean("http://192.168.0.200:8008/getdetail/"
						+ Parameter, map);
			}
		};
		threadDetail.startThread(null);
	}

	protected void showDetail(BaseResponse<GoodsDetailBean> respone) {
		GoodsDetailBean detail = respone.getList().get(0);
		if (detail == null)
			return;
		ArrayList<String> imageMainList = new ArrayList<String>();
		for (Pic item : detail.getProductMainPic()) {
			imageMainList.add(item.getImageUrl());
		}
		ArrayList<String> imageDesList = new ArrayList<String>();
		for (Pic item : detail.getProductDescPic()) {
			imageDesList.add(item.getImageUrl());
		}
		initVp(top_vp, top_cpi, imageMainList);
		initVp(bottom_vp, bottom_cpi, imageDesList);
		listString.setData(detail.getProductAttr());
	}

	private void initVp(ViewPager vp, CirclePageIndicator cpi,
			ArrayList<String> imageList) {
		ViewPagerAdapter vpAdapter = new ViewPagerAdapter(imageList, context,
				0, new Handler());
		vp.setAdapter(vpAdapter);
		cpi.setViewPager(vp);
	}

}
