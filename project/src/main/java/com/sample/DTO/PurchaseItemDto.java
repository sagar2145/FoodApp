package com.sample.DTO;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.sample.entity.PurchaseOrderHeader;

public class PurchaseItemDto {

	private int itemId;
	
	@ManyToOne(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name = "document_number")
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
