package com.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sample.DTO.PurchaseItemDto;
import com.sample.DTO.PurchaseOrderHeaderDto;
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
	
    // add only purchaseOrderHeader
	@RequestMapping(value = "/addOrder", method = RequestMethod.POST, consumes = "application/json")
	public Response showOrders(@RequestBody PurchaseOrderHeaderDto purchaseOrderHeaderDto){
	return 	purchaseImpl.addPurchaseOrder(purchaseOrderHeaderDto);
		
	}
	// add both purchaseOrderHeader and purchaseItem
	@RequestMapping(value = "/addAll", method = RequestMethod.POST, consumes = "application/json")
	public Response addOrders(@RequestBody PurchaseOrderHeaderDto purchaseOrderHeaderDto){
		return purchaseImpl.saveAll(purchaseOrderHeaderDto);
	}
	// update purchaseOrderHeader
	@RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = "application/json")
	public Response editOrders(@RequestBody PurchaseOrderHeaderDto purchaseOrderHeaderDto){
		return purchaseImpl.editpurchaseOrder(purchaseOrderHeaderDto);
	}
	
	@RequestMapping(value = "/updateOrder", method = RequestMethod.POST, consumes = "application/json")
	public Response updatePurchaseOrder(@RequestBody PurchaseOrderHeaderDto purchaseOrderHeaderDto){
		return purchaseImpl.updatePurchaseOrder(purchaseOrderHeaderDto);
	}
	
	//get only PurchaseOrderHeader
	@RequestMapping(value = "/getOrder", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PurchaseOrderHeaderDto> getOrders(){
		return purchaseImpl.GetPurchaseOrder();
	}
	
	
	//get only purchaseItem //not working
	@RequestMapping(value = "/getitem", method = RequestMethod.GET, consumes = "application/json")
	public List<PurchaseItemDto> getItemById(){
		return purchaseImpl.getItem();
	}
	
	//get both purchaseOrderHeader and purchaseItem
	@RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = "application/json")
	public List<PurchaseOrderHeaderDto> getALL(){
		return purchaseImpl.getAll121();
	}
	
	//get purchaseItem without header details
	@RequestMapping(value = "/getItems", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PurchaseItemDto> getItems(){
		return purchaseImpl.getPurchaseItem();
	}
	
	@RequestMapping(value = "/getItemABC", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PurchaseItem> getorderABC(){
		return purchaseImpl.getOrderABC();
	}
	// get purchaseOrderHeader using document_number
	@RequestMapping(value = "/getOrderById", method = RequestMethod.POST, consumes = "application/json")
	public List<PurchaseOrderHeaderDto> getbyId(@RequestBody PurchaseOrderHeaderDto purchaseOrderHeaderDto){
		return purchaseImpl.getOrderById(purchaseOrderHeaderDto);
	}
	
	//get purchaseItem including headreDetails with itemId 
	@RequestMapping(value = "/getItemById", method = RequestMethod.POST, consumes = "application/json")
	public List<PurchaseItemDto> getbyid(@RequestBody PurchaseItemDto purchaseItemDto){
		System.out.println("PurchaseOrderService.getItembyid()"+purchaseItemDto);
		return purchaseImpl.getItembyId(purchaseItemDto);
	}
	
	// delete purchaseItem using itemId
	@RequestMapping(value = "/deleteItem", method = RequestMethod.POST, consumes = "application/json")
	public Response deleteOrder(@RequestBody PurchaseItemDto purchaseItemDto){
		return purchaseImpl.deleteitem(purchaseItemDto);
	}
	
	// delete purchaseOrderHeader and purchaseItem using document_number
	@RequestMapping(value = "/deleteOrder", method = RequestMethod.POST, consumes = "application/json")
	public Response deleteOrder(@RequestBody PurchaseOrderHeaderDto purchaseOrderHeaderDto){
		return purchaseImpl.deleteOrder(purchaseOrderHeaderDto);
	}
	
	//update both purchaseOrderHeader and purchaseItem
	@RequestMapping(value = "/updateAll", method = RequestMethod.POST, consumes = "application/json")
	public Response update(@RequestBody PurchaseOrderHeaderDto purchaseOrderHeader){
		return purchaseImpl.updateAll(purchaseOrderHeader);
	}	
	
	// update only purchaseItem
	@RequestMapping(value = "/updateItems", method = RequestMethod.POST, consumes = "application/json")
	public Response updateItems(@RequestBody PurchaseItemDto purchaseItemDto){
		return purchaseImpl.editItems(purchaseItemDto);
	}	
}

/*@RequestMapping(value = "/getall", method = RequestMethod.POST, consumes = "application/json")
public List<PurchaseOrderHeader> getALL(@RequestBody PurchaseOrderHeader purchaseOrderHeader){
	return purchaseImpl.getAll1(purchaseOrderHeader);
}
@RequestMapping(value = "/getall123", method = RequestMethod.GET, produces = "application/json")
public List<PurchaseOrderHeader> getALL123(){
	return purchaseImpl.getall123();
}*/
/*@RequestMapping(value = "/editItems", method = RequestMethod.POST, consumes = "application/json")
public Response editItems(@RequestBody PurchaseItemDto purchaseItemDto){
	return purchaseImpl.editPurchaseItem(purchaseItemDto);
}*/