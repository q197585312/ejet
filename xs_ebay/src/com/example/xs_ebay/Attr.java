package com.example.xs_ebay;

import java.io.Serializable;

public class Attr implements Serializable {
	String AttrName;
	String AttrValue;
	public String getAttrName() {
		return AttrName;
	}
	public void setAttrName(String attrName) {
		AttrName = attrName;
	}
	public String getAttrValue() {
		return AttrValue;
	}
	public void setAttrValue(String attrValue) {
		AttrValue = attrValue;
	}
	@Override
	public String toString() {
		return "Attr [AttrName=" + AttrName + ", AttrValue=" + AttrValue + "]";
	}
	
}
