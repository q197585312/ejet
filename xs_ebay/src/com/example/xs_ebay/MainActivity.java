package com.example.xs_ebay;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.viewpagerindicator.CirclePageIndicator;
import com.xs_lib.adapter.MyBaseAdapter.BSBaseViewHolder;
import com.xs_lib.imp.BaseAdapterImp;
import com.xs_lib.imp.GridViewContent;
import com.xs_lib.imp.ItemCLickImp;
import com.yfc_lib.util.VolleyUtil;
import com.yfc_lib.volley.toolbox.ImageLoader;
import com.yfc_lib.volley.toolbox.ImageLoader.ImageListener;

public class MainActivity extends BaseTopActivity {
	GridViewContent<String> gvContent;
	@ViewInject(R.id.goods_display_gv)
	GridView gv;
	@ViewInject(R.id.top_vp)
	ViewPager top_vp;
	@ViewInject(R.id.top_cpi)
	CirclePageIndicator topcpi;
	@ViewInject(R.id.goods_display_gv)
	GridView top_gv;
	private ViewPagerAdapter vpAdapter;

	@Override
	protected int setBaseContentView() {
		return R.layout.activity_main_home;
	}

	@Override
	protected void init() {
		super.init();
		initTopVp();
		leftTv.setText("main");
		rightRl.setVisibility(View.VISIBLE);
		rightTv.setCompoundDrawablesWithIntrinsicBounds(0, 0,
				R.drawable.lib_menu_gray_small, 0);
		rightTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				gotoList(arg0);

			}
		});
		gvContent = new GridViewContent<String>();
		gvContent.setView(this, gv);
		gvContent.setItemIdAndBaseAdapter(R.layout.gv_item_iv_base,
				new BaseAdapterImp<String>() {
					@Override
					public void bindData(View view, BSBaseViewHolder holder,
							String model, int position) {
						ImageListener ivListener = ImageLoader
								.getImageListener(holder.img1,
										R.drawable.default_head_img,
										R.drawable.default_head_img);
						VolleyUtil
								.getImageLoader(context)
								.get("http://b.hiphotos.baidu.com/image/h%3D360/sign=06cf2ded7f1ed21b66c928e39d6eddae/b21bb051f8198618b721539b48ed2e738bd4e64f.jpg",
										ivListener);
					}

					@Override
					public void inflateAfter(View view, BSBaseViewHolder holder) {
						holder.img1 = (ImageView) view
								.findViewById(R.id.gv_item_iv1);
					}
				});
		List<String> lists = new ArrayList<String>();
		lists.add("");
		lists.add("");
		lists.add("");
		lists.add("");
		lists.add("");
		lists.add("");
		lists.add("");
		lists.add("");
		lists.add("");

		gvContent.setData(lists);
		gvContent.setItemClick(new ItemCLickImp<String>() {
			@Override
			public void itemCLick(String t, int position) {
				gotoDetail(t);
			}
		});
	}

	protected void gotoList(View arg0) {
		Intent intent = new Intent(context, GoodsGvlistActivity.class);
		startActivity(intent);

	}

	protected void gotoDetail(String t) {
		Intent intent = new Intent(context, GoodsDetailActivity.class);
		startActivity(intent);

	}

	private void initTopVp() {

		ArrayList<String> imageList = new ArrayList<String>();
		imageList
				.add("http://g.hiphotos.baidu.com/image/w%3D310/sign=0fc8355357fbb2fb342b5e137f4a2043/3b87e950352ac65c461e5b7df9f2b21193138aa0.jpg");
		imageList
				.add("http://g.hiphotos.baidu.com/image/w%3D310/sign=0fc8355357fbb2fb342b5e137f4a2043/3b87e950352ac65c461e5b7df9f2b21193138aa0.jpg");
		imageList
				.add("http://g.hiphotos.baidu.com/image/w%3D310/sign=0fc8355357fbb2fb342b5e137f4a2043/3b87e950352ac65c461e5b7df9f2b21193138aa0.jpg");
		imageList
				.add("http://g.hiphotos.baidu.com/image/w%3D310/sign=0fc8355357fbb2fb342b5e137f4a2043/3b87e950352ac65c461e5b7df9f2b21193138aa0.jpg");
		imageList
				.add("http://g.hiphotos.baidu.com/image/w%3D310/sign=0fc8355357fbb2fb342b5e137f4a2043/3b87e950352ac65c461e5b7df9f2b21193138aa0.jpg");
		imageList
				.add("http://g.hiphotos.baidu.com/image/w%3D310/sign=0fc8355357fbb2fb342b5e137f4a2043/3b87e950352ac65c461e5b7df9f2b21193138aa0.jpg");
		vpAdapter = new ViewPagerAdapter(imageList, MainActivity.this, 0,
				new Handler());
		top_vp.setAdapter(vpAdapter);

		// } else {
		// holder = (TopViewHolder) topView.getTag();
		// }
		// topcpi.setFlag(true);
		topcpi.setViewPager(top_vp);

	}

	public void clickFirst(View v) {
		Intent intent = new Intent(context, GoodslistActivity.class);
		startActivity(intent);
	}
}
