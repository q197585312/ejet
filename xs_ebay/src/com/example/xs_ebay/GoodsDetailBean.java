package com.example.xs_ebay;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author xs {"ProductName": "",
 * "Price": "17.800",
 * "MOQ": "0",
 * "ProductAttr":[
 * {"AttrName": " Style","AtrValue": "Antique"},
 * {"AttrName": "Plastic","AtrValue": "Imitation Wood Color"},
 * {"AttrName": "brand","AtrValue": "100"},
 * {"AttrName": "Category","AtrValue": "The Alarm Clock"},
 * {"AttrName": "Color","AtrValue": "White Wood Color, Coffee Color Wood"}
 * ],
 * "ProductMainPic":[
 * {"ImageUrl": "http://i02.c.aliimg.com/img/ibank/2014/168/974/1347479861_2082826702.jpg"}
 * ,{"ImageUrl": "http://i03.c.aliimg.com/img/ibank/2014/778/974/1347479877_2082826702.jpg"},
 * {"ImageUrl": "http://i03.c.aliimg.com/img/ibank/2014/398/974/1347479893_2082826702.jpg"},
 * {"ImageUrl": "http://i01.c.aliimg.com/img/ibank/2014/038/974/1347479830_2082826702.jpg"},
 * {"ImageUrl": "http://i01.c.aliimg.com/img/ibank/2014/628/974/1347479826_2082826702.jpg"}],
 * "ProductDescPic":[
 * {"ImageUrl": "http://img02.taobaocdn.com/imgextra/i2/55404443/T21yrZXjBXXXXXXXXX_!!55404443.jpg"},
 * {"ImageUrl": "http://img02.taobaocdn.com/imgextra/i2/55404443/T2fEPZXaxaXXXXXXXX_!!55404443.jpg"},
 * {"ImageUrl": "http://img04.taobaocdn.com/imgextra/i4/55404443/T2so_ZXkRXXXXXXXXX_!!55404443.jpg"},
 * {"ImageUrl": "http://img03.taobaocdn.com/imgextra/i3/55404443/T2O3YZXfFaXXXXXXXX_!!55404443.jpg"},
 * {"ImageUrl": "http://img02.taobaocdn.com/imgextra/i2/55404443/T2BQ2YXg8aXXXXXXXX_!!55404443.jpg"},
 * {"ImageUrl": "http://img01.taobaocdn.com/imgextra/i1/55404443/T2sDLZXolXXXXXXXXX_!!55404443.jpg"},
 * {"ImageUrl": "http://img04.taobaocdn.com/imgextra/i4/55404443/T2SRDZXkVXXXXXXXXX_!!55404443.jpg"},
 * {"ImageUrl": "http://img02.taobaocdn.com/imgextra/i2/55404443/T2wojZXoJXXXXXXXXX_!!55404443.jpg"},
 * {"ImageUrl": "http://img03.taobaocdn.com/imgextra/i3/55404443/T2mAPoXaxaXXXXXXXX_!!55404443.jpg"}]
 * }]}
 * 
 */
public class GoodsDetailBean implements Serializable {
	private String ProductName;
	private List<Attr> ProductAttr;
	private List<Pic> ProductMainPic;
	private List<Pic> ProductDescPic;
	private String Price;
	private String MOQ;
	private String ItemNo;



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

	public String getProductName() {
		return ProductName;
	}

	public void setProductName(String productName) {
		ProductName = productName;
	}

	public List<Attr> getProductAttr() {
		return ProductAttr;
	}

	public void setProductAttr(List<Attr> productAttr) {
		ProductAttr = productAttr;
	}

	public List<Pic> getProductMainPic() {
		return ProductMainPic;
	}

	public void setProductMainPic(List<Pic> productMainPic) {
		ProductMainPic = productMainPic;
	}

	public List<Pic> getProductDescPic() {
		return ProductDescPic;
	}

	public void setProductDescPic(List<Pic> productDescPic) {
		ProductDescPic = productDescPic;
	}

	@Override
	public String toString() {
		return "GoodsDetailBean [ProductName=" + ProductName + ", ProductAttr="
				+ ProductAttr + ", ProductMainPic=" + ProductMainPic
				+ ", ProductDescPic=" + ProductDescPic + ", Price=" + Price
				+ ", MOQ=" + MOQ + ", ItemNo=" + ItemNo + "]";
	}

	

}
