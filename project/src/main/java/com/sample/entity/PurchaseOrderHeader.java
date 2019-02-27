package com.sample.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="PurchaseOrderHeader")

public class PurchaseOrderHeader {
               

                @Id
                @GenericGenerator(name = "generator", strategy = "increment")
                @GeneratedValue(generator = "generator") 
                @Column(name="document_number")
                private int document_number;
                
                @JsonBackReference
                @OneToMany(fetch = FetchType.EAGER, mappedBy ="headerDetails",cascade = CascadeType.ALL)
                private List<PurchaseItem> purchaseItemList;

               
                @Column(name="company_code") 
                private String company_code;

                @Column(name="status") 
                private String status;

                @Column(name="vendor") 
                private String vendor;

                @Column(name="currency") 
                private String currency;

                public int getDocument_number() {
                                return document_number;
                }

                public void setDocument_number(int document_number) {
                                this.document_number = document_number;
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

                public List<PurchaseItem> getPurchaseItemList() {
					return purchaseItemList;
				}

				public void setPurchaseItemList(List<PurchaseItem> purchaseItemList) {
					this.purchaseItemList = purchaseItemList;
				}

				public PurchaseOrderHeader() {
                                
                }

}


