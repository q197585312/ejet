package com.example.xs_ebay;

import java.io.Serializable;

/**
 * 
 * @author xs {"CategoryName": "Apparel","CategoryID": "1"}
 */
public class CategoryBean implements Serializable {
	private String CategoryName;
	private String CategoryID;
	public String getCategoryName() {
		return CategoryName;
	}
	public void setCategoryName(String categoryName) {
		CategoryName = categoryName;
	}
	public String getCategoryID() {
		return CategoryID;
	}
	public void setCategoryID(String categoryID) {
		CategoryID = categoryID;
	}
	@Override
	public String toString() {
		return "CategoryBean [CategoryName=" + CategoryName + ", CategoryID="
				+ CategoryID + "]";
	}
	
}
