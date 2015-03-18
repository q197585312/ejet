package com.yfc_lib.listener;

import java.util.ArrayList;

public interface OnGetImageListener {
	void onGetImageOne(String imagePath);
	
	void onGetImageMult(ArrayList<String> imageList);
	
	void onCaptureImage(String imagePath);
	
	void onError(String error);
}