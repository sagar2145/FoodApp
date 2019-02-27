package com.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sample.entity.PurchaseItem;
import com.sample.entity.PurchaseOrderHeader;
import com.sample.s.PurchaseOrderImpl;

import response.Response;
@Service
@RestController
@RequestMapping(value="/purchase", produces = "application/json" )
public class PurchaseOrderService {
@Autowired
	PurchaseOrderImpl purchaseImpl;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
	public Response showOrders(@RequestBody PurchaseOrderHeader purchaseOrderHeader){
	return 	purchaseImpl.addPurchaseOrder(purchaseOrderHeader);
		
	}
	@RequestMapping(value = "/getorder", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PurchaseOrderHeader> getOrders(){
		return purchaseImpl.GetPurchaseOrder();
	}
	
	@RequestMapping(value = "/addorder", method = RequestMethod.POST, consumes = "application/json")
	public Response addOrders(@RequestBody PurchaseOrderHeader purchaseOrderHeader){
		return purchaseImpl.saveAll(purchaseOrderHeader);
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = "application/json")
	public Response editOrders(@RequestBody PurchaseOrderHeader purchaseOrderHeader){
		return purchaseImpl.editpurchaseOrder(purchaseOrderHeader);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
	public Response deleteOrder(@RequestBody PurchaseOrderHeader purchaseOrderHeader){
		return purchaseImpl.deleteOrder(purchaseOrderHeader.getDocument_number());
	}
	
	@RequestMapping(value = "/edititems", method = RequestMethod.POST, consumes = "application/json")
	public Response editItems(@RequestBody PurchaseItem purchaseItem){
		return purchaseImpl.editPurchaseItem(purchaseItem);
	}
	
	@RequestMapping(value = "/getall", method = RequestMethod.POST, consumes = "application/json")
	public List<PurchaseOrderHeader> getALL(@RequestBody PurchaseOrderHeader purchaseOrderHeader){
		return purchaseImpl.getAll1(purchaseOrderHeader);
	}
	
	@RequestMapping(value = "/getall121", method = RequestMethod.GET, produces = "application/json")
	public List<PurchaseOrderHeader> getALL(){
		return purchaseImpl.getAll121();
	}
	@RequestMapping(value = "/getitem", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PurchaseItem> getItems(){
		return purchaseImpl.getPurchaseItem();
	}
	
	/*@RequestMapping(value = "/getorder1", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PurchaseOrderHeader> getorder(){
		return purchaseImpl.getPurchase();
	}*/
	@RequestMapping(value = "/getbyid", method = RequestMethod.POST, consumes = "application/json")
	public List<PurchaseOrderHeader> getbyId(@RequestBody PurchaseOrderHeader purchaseOrderHeader){
		return purchaseImpl.getById(purchaseOrderHeader.getDocument_number());
	}
	@RequestMapping(value = "/getitembyid", method = RequestMethod.POST, consumes = "application/json")
	public List<PurchaseItem> getbyid(@RequestBody PurchaseItem purchaseItem){
		System.out.println("PurchaseOrderService.getbyid()"+purchaseItem);
		return purchaseImpl.getbyId(purchaseItem.getItemId());
	}
	
	/*@RequestMapping(value = "/deleteItem", method = RequestMethod.POST, consumes = "application/json")
	public Response deleteItems(@RequestBody PurchaseItem purchaseItem){
		return purchaseImpl.deleteItems(purchaseItem.getItemId());
	}*/
	
	@RequestMapping(value = "/deleteitem", method = RequestMethod.POST, consumes = "application/json")
	public Response deleteOrder(@RequestBody PurchaseItem purchaseItem){
		return purchaseImpl.deleteitem(purchaseItem.getItemId());
	}
	
	@RequestMapping(value = "/updateall", method = RequestMethod.POST, consumes = "application/json")
	public Response update(@RequestBody PurchaseOrderHeader purchaseOrderHeader){
		return purchaseImpl.updateAll(purchaseOrderHeader);
	}
	
	@RequestMapping(value = "/getall123", method = RequestMethod.GET, produces = "application/json")
	public List<PurchaseOrderHeader> getALL123(){
		return purchaseImpl.getall123();
	}
}
