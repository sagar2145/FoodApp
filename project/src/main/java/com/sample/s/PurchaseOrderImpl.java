package com.sample.s;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.DAO.PurchaseDAO;
import com.sample.DTO.PurchaseItemDto;
import com.sample.DTO.PurchaseOrderHeaderDto;
import com.sample.entity.PurchaseItem;
import com.sample.entity.PurchaseOrderHeader;

import response.Response;

@Service
public class PurchaseOrderImpl {

	@Autowired
	PurchaseDAO purchaseDAO;
	
	
	@Transactional
	public Response addPurchaseOrder(PurchaseOrderHeaderDto purchaseOrderHeaderDto)
	{
		return purchaseDAO.addPurchaseOrder(purchaseOrderHeaderDto);
	}
	
	@Transactional
	public List<PurchaseOrderHeaderDto> GetPurchaseOrder(){
		return purchaseDAO.GetPurchaseOrder();
	}
	
	@Transactional
	public Response saveAll(PurchaseOrderHeaderDto purchaseOrderHeaderDto){
		return purchaseDAO.saveAll(purchaseOrderHeaderDto);
	}
	@Transactional
	public Response editpurchaseOrder(PurchaseOrderHeaderDto purchaseOrderHeaderDto){
		return purchaseDAO.editpurchaseOrder(purchaseOrderHeaderDto);
	}
	@Transactional
	public Response updatePurchaseOrder(PurchaseOrderHeaderDto purchaseOrderHeaderDto){
		return purchaseDAO.updatePurchaseOrder(purchaseOrderHeaderDto);
	}
	@Transactional
	public Response deleteOrder(PurchaseOrderHeaderDto purchaseOrderHeaderDto){
		return purchaseDAO.deleteOrder(purchaseOrderHeaderDto);
	}
	
	@Transactional
	public List<PurchaseOrderHeaderDto>  getAll121(){
		return purchaseDAO.getAll121();
	}
	
	@Transactional
	public List<PurchaseItemDto> getPurchaseItem(){
	return purchaseDAO.getPurchaseItem();	
	}
	
	@Transactional
	public List<PurchaseOrderHeaderDto> getOrderById(PurchaseOrderHeaderDto  purchaseOrderHeaderDto){
		return purchaseDAO.getOrderById(purchaseOrderHeaderDto);
	}
	@Transactional
	public List<PurchaseItemDto> getItembyId(PurchaseItemDto purchaseItemDto){
		return purchaseDAO.getItembyId(purchaseItemDto);
	}
	
	@Transactional
	public Response deleteitem(PurchaseItemDto purchaseItemDto){
		return purchaseDAO.deleteitem(purchaseItemDto);
	}
	@Transactional
	public Response updateAll(PurchaseOrderHeaderDto purchaseOrderHeader){
		return purchaseDAO.updateAll(purchaseOrderHeader);
	}
	
	@Transactional
	public List<PurchaseItemDto> getItem(){
		return purchaseDAO.getItem();
	}
	@Transactional
	public Response editItems(PurchaseItemDto purchaseItemDto){
		return purchaseDAO.editItems(purchaseItemDto);
	}
	@Transactional
	public List<PurchaseItem> getOrderABC(){
		return purchaseDAO.getOrderABC();
	}
}

/*@Transactional
public List<PurchaseOrderHeader>  getAll1(PurchaseOrderHeader purchaseOrderHeader){
	return purchaseDAO.getAll(purchaseOrderHeader);
}*/
/*@Transactional
public List<PurchaseOrderHeader> getall123(){
	return purchaseDAO.getall123();
}*/
/*@Transactional
public Response editPurchaseItem(PurchaseItemDto purchaseItemDto){
	return purchaseDAO.editPurchaseItem(purchaseItemDto);
}*/
/*@Transactional
public List<PurchaseOrderHeader> getPurchase(){
	return purchaseDAO.getPurchase();
}*/
/*@Transactional
public Response deleteItems(int itemId){
	return purchaseDAO.deleteItems(itemId);
}*/