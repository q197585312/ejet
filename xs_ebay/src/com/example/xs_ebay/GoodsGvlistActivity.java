package com.example.xs_ebay;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.xs_lib.adapter.MyBaseAdapter.BSBaseViewHolder;
import com.xs_lib.imp.BaseAdapterImp;
import com.xs_lib.imp.GridViewContent;
import com.yfc_lib.util.VolleyUtil;
import com.yfc_lib.volley.toolbox.ImageLoader;
import com.yfc_lib.volley.toolbox.ImageLoader.ImageListener;

public class GoodsGvlistActivity extends BaseTopActivity {
	GridViewContent gv;
	@ViewInject(R.id.goods_list_gv)
	GridView list;

	@OnItemClick(R.id.my_list_content)
	@Override
	protected int setBaseContentView() {
		return R.layout.activity_goods_gvlist;
	}

	@Override
	protected void init() {
		super.init();
		leftTv.setText("goodslist");
		gv = new GridViewContent<String>();
		gv.setView(this, list);
		gv.setItemIdAndBaseAdapter(R.layout.gv_item_iv_base,
				new BaseAdapterImp<String>() {
					@Override
					public void bindData(View view, BSBaseViewHolder holder,
							String model, int position) {
						holder.ll1.setVisibility(View.VISIBLE);
						holder.tv1.setText("test1");
						holder.tv2.setText("test2");
						holder.tv1.setTextColor(getResources().getColor(
								R.color.word_orange));
						holder.tv2.setTextColor(getResources().getColor(
								R.color.word_orange));
						ImageListener ivListener = ImageLoader
								.getImageListener(holder.img1,
										R.drawable.default_head_img,
										R.drawable.default_head_img);
						VolleyUtil
								.getImageLoader(context)
								.get("http://f.hiphotos.baidu.com/image/pic/item/faf2b2119313b07e1fdbea050ed7912397dd8c5d.jpg",
										ivListener);
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
		List<String> ls = new ArrayList<>();
		ls.add("");
		ls.add("");
		ls.add("");
		ls.add("");
		ls.add("");
		ls.add("");
		ls.add("");
		ls.add("");
		gv.setData(ls);
	}
}
