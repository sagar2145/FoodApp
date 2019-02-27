package com.sample.DTO;

import com.sample.entity.PurchaseOrderHeader;

public class PurchaseItemDto {

	private int itemId;
	private PurchaseOrderHeader headerDetails;
	private String itemName;
	private String itemPrice;
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public PurchaseOrderHeader getHeaderDetails() {
		return headerDetails;
	}
	public void setHeaderDetails(PurchaseOrderHeader headerDetails) {
		this.headerDetails = headerDetails;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}

	
}
