package com.example.xs_ebay;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.xiao.oia.view.MyListView;
import com.xs_lib.adapter.MyBaseAdapter.BSBaseViewHolder;
import com.xs_lib.imp.BaseAdapterImp;
import com.xs_lib.imp.ListViewContent;

public class GoodsDetailActivity extends BaseTopActivity {
	ListViewContent listString;
	@ViewInject(R.id.detail_list_attribute)
	MyListView mylv;

	@Override
	protected int setBaseContentView() {
		return R.layout.activity_goods_details;
	}

	@Override
	protected void init() {
		super.init();
		leftTv.setText("detail");
		listString = new ListViewContent<String>();
		listString.setView(this, mylv);
		listString.setItemIdAndBaseAdapter(R.layout.list_item_text2,
				new BaseAdapterImp<String>() {
					@Override
					public void bindData(View view, BSBaseViewHolder holder,
							String model, int position) {
						holder.tv1.setText("attribute");
						holder.tv2.setText("value");
					}

					@Override
					public void inflateAfter(View view, BSBaseViewHolder holder) {
						holder.tv1 = (TextView) view
								.findViewById(R.id.text_left);
						holder.tv2 = (TextView) view
								.findViewById(R.id.text_right);
					}
				});
		List<String> list = new ArrayList<String>();
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		listString.setData(list);
	}
}
