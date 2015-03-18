package com.example.xs_ebay;

import java.io.Serializable;

/**
 * 
 * @author xs {"CategoryName": "Apparel","CategoryID": "1"}+
 *         {ProductPic": "http://
 *         i00.c.aliimg.com/img/ibank/2014/894/994/1274499498_2082826702.
 *         jpg","Price": "17.800","MOQ": "0","ItemNo": "470595"}
 */
public class CategoryBean implements Serializable {
	private String CategoryName;
	private String CategoryID;
	private String ProductPic;
	private String Price;
	private String MOQ;
	private String ItemNo;

	public String getProductPic() {
		return ProductPic;
	}

	public void setProductPic(String productPic) {
		ProductPic = productPic;
	}

	public String getPrice() {
		return Price;
	}

	public void setPrice(String price) {
		Price = price;
	}

	public String getMOQ() {
		return MOQ;
	}

	public void setMOQ(String mOQ) {
		MOQ = mOQ;
	}

	public String getItemNo() {
		return ItemNo;
	}

	public void setItemNo(String itemNo) {
		ItemNo = itemNo;
	}

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
				+ CategoryID + ", ProductPic=" + ProductPic + ", Price="
				+ Price + ", MOQ=" + MOQ + ", ItemNo=" + ItemNo + "]";
	}

}
