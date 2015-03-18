package com.example.xs_ebay;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class BaseTopActivity extends
		com.yfc_lib.activity.BaseTopActivity {
	public MyApplication myApplication;
	protected Context context;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		context = this;
		myApplication = (MyApplication) getApplication();
		rootRl.setBackgroundColor(getResources()
				.getColor(R.color.bg_gary_light));
		leftTv.setTextColor(getResources().getColor(R.color.word_black));
		centerTv.setTextColor(getResources().getColor(R.color.word_black));
		rightTv.setTextColor(getResources().getColor(R.color.word_black));
		leftTv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_left,
				0, 0, 0);
		setOffLinelisten(true);
		init();
	}

	protected void init() {
		leftTv.setCompoundDrawablePadding(5);
		leftTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				leftClick(arg0);
			}
		});
	}

	protected void leftClick(View v) {
		finish();
	}
}
