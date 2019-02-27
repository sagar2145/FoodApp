package com.sample.s;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.DAO.PurchaseDAO;
import com.sample.entity.PurchaseItem;
import com.sample.entity.PurchaseOrderHeader;

import response.Response;

@Service
public class PurchaseOrderImpl {

	@Autowired
	PurchaseDAO purchaseDAO;
	
	
	@Transactional
	public Response addPurchaseOrder(PurchaseOrderHeader purchaseOrderHeader)
	{
		return purchaseDAO.addPurchaseOrder(purchaseOrderHeader);
	}
	
	@Transactional
	public List<PurchaseOrderHeader> GetPurchaseOrder(){
		return purchaseDAO.GetPurchaseOrder();
	}
	
	@Transactional
	public Response saveAll(PurchaseOrderHeader purchaseOrderHeader){
		return purchaseDAO.saveAll(purchaseOrderHeader);
	}
	@Transactional
	public Response editpurchaseOrder(PurchaseOrderHeader purchaseOrderHeader){
		return purchaseDAO.editpurchaseOrder(purchaseOrderHeader);
	}
	@Transactional
	public Response deleteOrder(int num){
		return purchaseDAO.deleteOrder(num);
	}
	@Transactional
	public Response editPurchaseItem(PurchaseItem purchaseItem){
		return purchaseDAO.editPurchaseItem(purchaseItem);
	}
	
	@Transactional
	public List<PurchaseOrderHeader>  getAll1(PurchaseOrderHeader purchaseOrderHeader){
		return purchaseDAO.getAll(purchaseOrderHeader);
	}
	
	@Transactional
	public List<PurchaseOrderHeader>  getAll121(){
		return purchaseDAO.getAll121();
	}
	
	@Transactional
	public List<PurchaseItem> getPurchaseItem(){
	return purchaseDAO.getPurchaseItem();	
	}
	
	/*@Transactional
	public List<PurchaseOrderHeader> getPurchase(){
		return purchaseDAO.getPurchase();
	}*/
	@Transactional
	public List<PurchaseOrderHeader> getById(int  document_number){
		return purchaseDAO.getById(document_number);
	}
	@Transactional
	public List<PurchaseItem> getbyId(int id){
		return purchaseDAO.getbyId(id);
	}
	@Transactional
	public Response deleteItems(int itemId){
		return purchaseDAO.deleteItems(itemId);
	}
	@Transactional
	public Response deleteitem(int itemId){
		return purchaseDAO.deleteitem(itemId);
	}
	@Transactional
	public Response updateAll(PurchaseOrderHeader purchaseOrderHeader){
		return purchaseDAO.updateAll(purchaseOrderHeader);
	}
	
	@Transactional
	public List<PurchaseOrderHeader> getall123(){
		return purchaseDAO.getall123();
	}
}
