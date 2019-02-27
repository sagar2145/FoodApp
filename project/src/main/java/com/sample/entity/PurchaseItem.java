package com.sample.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
@Entity
@Table(name="PurchaseItem")
public class PurchaseItem 
{
	@Id  
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator") 
	@Column(name="itemId")
	private int itemId;
	
	 @JsonBackReference
	@ManyToOne(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name = "document_number")
	private PurchaseOrderHeader headerDetails;

	@Column(name="itemName")
	private String itemName;
	
	@Column(name="itemPrice")
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

	@Override
	public String toString() {
		return "PurchaseItem [itemId=" + itemId + ", headerDetails=" + headerDetails + ", itemName=" + itemName
				+ ", itemPrice=" + itemPrice + "]";
	}

}
