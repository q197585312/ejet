package com.example.xs_ebay;

import java.util.HashMap;
import java.util.Map;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.xs_lib.adapter.MyBaseAdapter.BSBaseViewHolder;
import com.xs_lib.imp.BaseAdapterImp;
import com.xs_lib.imp.EatBaseThreadHandler;
import com.xs_lib.imp.ItemCLickImp;
import com.xs_lib.imp.ListViewContent;
import com.yfc_lib.bean.BaseResponse;
import com.yfc_lib.bean.RequestBean;
import com.yfc_lib.util.HttpAnalyze;
import com.yfc_lib.util.Logger;
import com.yfc_lib.util.StringTool;

public class GoodslistActivity extends BaseTopActivity {
	ListViewContent listString;
	@ViewInject(R.id.my_list_content)
	ListView list;

	@OnItemClick(R.id.my_list_content)
	@Override
	protected int setBaseContentView() {
		return R.layout.goodslist;
	}

	@Override
	protected void init() {
		super.init();
		leftTv.setText("first");
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
				getCategryList(t);
			}
		});

	}

	protected void getCategryList(final CategoryBean bean) {
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
				String Parameter="";
				if (bean != null){
					Parameter=StringTool.getParameterStr("categoryid",""+ bean.getCategoryID());
				}
				return new RequestBean(
						"http://192.168.0.200:8008/getcategory/"+Parameter, map);
			}

			@Override
			protected void successListEnd(BaseResponse<CategoryBean> respone) {
				listString.setData(respone.getList());

			}
		});
	}

	protected void gotoList2(CategoryBean t) {
		getCategryList(t);

	}
}
