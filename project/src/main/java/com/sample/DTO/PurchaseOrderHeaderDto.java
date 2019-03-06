package com.sample.DTO;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.sample.entity.PurchaseItem;

public class PurchaseOrderHeaderDto {

	private int document_number;
	
    @OneToMany(fetch = FetchType.EAGER, mappedBy ="headerDetails",cascade = CascadeType.ALL)
	private List<PurchaseItem> purchaseItemList;
    
    private String company_code;
    private String status;
    private String vendor;
    private String currency;
	public int getDocument_number() {
		return document_number;
	}
	public void setDocument_number(int document_number) {
		this.document_number = document_number;
	}
	public List<PurchaseItem> getPurchaseItemList() {
		return purchaseItemList;
	}
	public void setPurchaseItemList(List<PurchaseItem> purchaseItemList) {
		this.purchaseItemList = purchaseItemList;
	}
	public String getCompany_code() {
		return company_code;
	}
	public void setCompany_code(String company_code) {
		this.company_code = company_code;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}

    
}
