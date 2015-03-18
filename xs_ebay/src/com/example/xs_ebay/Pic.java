package com.example.xs_ebay;

import java.io.Serializable;

public class Pic implements Serializable {
	String ImageUrl;

	public String getImageUrl() {
		return ImageUrl;
	}

	public void setImageUrl(String imageUrl) {
		ImageUrl = imageUrl;
	}

	@Override
	public String toString() {
		return "Pic [ImageUrl=" + ImageUrl + "]";
	}

}
