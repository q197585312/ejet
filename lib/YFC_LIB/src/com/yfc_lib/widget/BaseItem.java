package com.yfc_lib.widget;

import com.yfc_lib.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class BaseItem {
	public abstract void setWidget(Context context);
	
	private Context			context;
	private LayoutInflater	inflater;
	
	private RelativeLayout	rootRl;
	
	private RelativeLayout	leftRl;
	private RelativeLayout	nearRightRl;
	private RelativeLayout	rightRl;
	private RelativeLayout	bottomRl;
	
	private TextView		leftTv;
	private LinearLayout	leftLl;
	private ImageView		leftLlIv;
	private TextView		leftLlTv1;
	private TextView		leftLlTv2;
	
	private TextView		nearRightTv;
	private ImageView		nearRightIv;
	
	private TextView		rightTv;
	private ImageView		rightIv;
	
	private TextView		bottomTv;
	
	public BaseItem(Context context) {
		super();
		this.context = context;
		inflater = LayoutInflater.from(context);
		
		initWidget();
	}
	
	private void initWidget() {
		rootRl = (RelativeLayout) inflater.inflate(R.layout.base_item, null);
		leftRl = (RelativeLayout) rootRl.findViewById(R.id.base_item_left_rl);
		nearRightRl = (RelativeLayout) rootRl.findViewById(R.id.base_item_near_right_rl);
		rightRl = (RelativeLayout) rootRl.findViewById(R.id.base_item_right_rl);
		bottomRl = (RelativeLayout) rootRl.findViewById(R.id.base_item_bottom_rl);
		
		leftTv = (TextView) rootRl.findViewById(R.id.base_item_left_tv);
		leftLl = (LinearLayout) rootRl.findViewById(R.id.base_item_left_ll);
		leftLlIv = (ImageView) rootRl.findViewById(R.id.base_item_left_ll_iv);
		leftLlTv1 = (TextView) rootRl.findViewById(R.id.base_item_left_ll_tv1);
		leftLlTv2 = (TextView) rootRl.findViewById(R.id.base_item_left_ll_tv2);
		
		nearRightTv = (TextView) rootRl.findViewById(R.id.base_item_near_right_tv);
		nearRightIv = (ImageView) rootRl.findViewById(R.id.base_item_near_right_iv);
		
		rightTv = (TextView) rootRl.findViewById(R.id.base_item_right_tv);
		rightIv = (ImageView) rootRl.findViewById(R.id.base_item_right_iv);
		
		bottomTv = (TextView) rootRl.findViewById(R.id.base_item_bottom_tv);
		
		bottomRl.setVisibility(View.GONE);
		
		setWidget(context);
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
	
	public RelativeLayout getLeftRl() {
		return leftRl;
	}
	
	public void setLeftRl(RelativeLayout leftRl) {
		this.leftRl = leftRl;
	}
	
	public RelativeLayout getNearRightRl() {
		return nearRightRl;
	}
	
	public void setNearRightRl(RelativeLayout nearRightRl) {
		this.nearRightRl = nearRightRl;
	}
	
	public RelativeLayout getRightRl() {
		return rightRl;
	}
	
	public void setRightRl(RelativeLayout rightRl) {
		this.rightRl = rightRl;
	}
	
	public RelativeLayout getBottomRl() {
		return bottomRl;
	}
	
	public void setBottomRl(RelativeLayout bottomRl) {
		this.bottomRl = bottomRl;
	}
	
	public TextView getLeftTv() {
		return leftTv;
	}
	
	public void setLeftTv(TextView leftTv) {
		this.leftTv = leftTv;
	}
	
	public LinearLayout getLeftLl() {
		return leftLl;
	}
	
	public void setLeftLl(LinearLayout leftLl) {
		this.leftLl = leftLl;
	}
	
	public ImageView getLeftLlIv() {
		return leftLlIv;
	}
	
	public void setLeftLlIv(ImageView leftLlIv) {
		this.leftLlIv = leftLlIv;
	}
	
	public TextView getLeftLlTv1() {
		return leftLlTv1;
	}
	
	public void setLeftLlTv1(TextView leftLlTv1) {
		this.leftLlTv1 = leftLlTv1;
	}
	
	public TextView getLeftLlTv2() {
		return leftLlTv2;
	}
	
	public void setLeftLlTv2(TextView leftLlTv2) {
		this.leftLlTv2 = leftLlTv2;
	}
	
	public TextView getNearRightTv() {
		return nearRightTv;
	}
	
	public void setNearRightTv(TextView nearRightTv) {
		this.nearRightTv = nearRightTv;
	}
	
	public ImageView getNearRightIv() {
		return nearRightIv;
	}
	
	public void setNearRightIv(ImageView nearRightIv) {
		this.nearRightIv = nearRightIv;
	}
	
	public TextView getRightTv() {
		return rightTv;
	}
	
	public void setRightTv(TextView rightTv) {
		this.rightTv = rightTv;
	}
	
	public ImageView getRightIv() {
		return rightIv;
	}
	
	public void setRightIv(ImageView rightIv) {
		this.rightIv = rightIv;
	}
	
	public TextView getBottomTv() {
		return bottomTv;
	}
	
	public void setBottomTv(TextView bottomTv) {
		this.bottomTv = bottomTv;
	}
}
