package com.yfc_lib.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yfc_lib.activity.R;

public class BaseItemLine {
	private Context context;
	private LayoutInflater inflater;

	private RelativeLayout rootRl;
	private TextView lineTv;

	private int lineColor;

	public BaseItemLine(Context context, int lineColor) {
		super();
		this.context = context;
		this.lineColor = lineColor;
		inflater = LayoutInflater.from(context);
		initWidget();
	}

	private void initWidget() {
		rootRl = (RelativeLayout) inflater.inflate(R.layout.base_item_line,
				null);
		lineTv = (TextView) rootRl.findViewById(R.id.base_item_line_tv);
		lineTv.setBackgroundColor(context.getResources().getColor(lineColor));
	}

	public RelativeLayout getLine() {
		return rootRl;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public LayoutInflater getInflater() {
		return inflater;
	}

	public void setInflater(LayoutInflater inflater) {
		this.inflater = inflater;
	}

	public RelativeLayout getRootRl() {
		return rootRl;
	}

	public void setRootRl(RelativeLayout rootRl) {
		this.rootRl = rootRl;
	}

	public TextView getLineTv() {
		return lineTv;
	}

	public void setLineTv(TextView lineTv) {
		this.lineTv = lineTv;
	}
}
