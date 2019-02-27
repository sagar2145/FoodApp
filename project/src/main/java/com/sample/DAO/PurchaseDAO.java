package com.sample.DAO;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.sample.DTO.PurchaseOrderHeaderDto;
import com.sample.entity.PurchaseItem;
import com.sample.entity.PurchaseOrderHeader;

import response.Response;
@Repository
public class PurchaseDAO {

	private Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
	
	private SessionFactory sf = configuration.buildSessionFactory();
	private Session s;
	private Transaction tx;
	
	Response response=new Response();
	
	
	public PurchaseOrderHeaderDto exportDao(PurchaseOrderHeader PurchaseOrderHeaderDo ){
		PurchaseOrderHeaderDto orderDto=new PurchaseOrderHeaderDto();
		
		orderDto.setCompany_code(PurchaseOrderHeaderDo.getCompany_code());
		orderDto.setCurrency(PurchaseOrderHeaderDo.getCurrency());
		orderDto.setDocument_number(PurchaseOrderHeaderDo.getDocument_number());
		orderDto.setStatus(PurchaseOrderHeaderDo.getStatus());
		orderDto.setVendor(PurchaseOrderHeaderDo.getVendor());
	/*	orderDto.setPurchaseItemList(PurchaseOrderHeaderDo.getPurchaseItemList());*/
		
		return orderDto;
	}
	public PurchaseOrderHeader importDao(PurchaseOrderHeaderDto orderDto){
		PurchaseOrderHeader orderDo=new PurchaseOrderHeader();
		
	}
	
	// add purchaseorder
	public Response addPurchaseOrder(PurchaseOrderHeader purchaseOrderHeader){
		
		try{
			s=sf.openSession();
			tx=s.beginTransaction();
			s.save(purchaseOrderHeader);
			response.setStatus(200);
			response.setMessage("succesfull");
			tx.commit();
		}
		 catch (Exception e) {
				System.err.println("Exception : " + e.getMessage());

				response.setStatus(500);
				response.setMessage("unsuccesfull");
				tx.rollback();
				
			} finally {
				s.close();
			}
			return response;
	}
	
	// get purchaseOrder
	/*@SuppressWarnings("unchecked")
	public List<PurchaseOrderHeader> GetPurchaseOrder(){
		s=sf.openSession();
		tx=s.beginTransaction();
		List<PurchaseOrderHeader> order=s.createSQLQuery("select * from purchaseorderheader").list();
		tx.commit();
		return order;
	}*/
	public List<PurchaseOrderHeader> GetPurchaseOrder(){
		s=this.sf.openSession();
		List<PurchaseOrderHeader> list=s.createCriteria(PurchaseOrderHeader.class).list();
		return list;
	}
	
	//get purchaseItem
	@SuppressWarnings("unchecked")
	public List<PurchaseItem> getPurchaseItem(){
		s=sf.openSession();
		tx=s.beginTransaction();
/*		List<PurchaseItem> items=s.createSQLQuery("select itemname,itemprice  from purchaseitem").list();
*/		List<PurchaseItem> items=s.createCriteria(PurchaseItem.class).list();
		
		return items;
	}
	
	//save both purchaseOrder and items
	public Response saveAll(PurchaseOrderHeader purchaseOrderHeader){
		try{
			s=sf.openSession();
			tx=s.beginTransaction();
			
			for(int i=0;i<purchaseOrderHeader.getPurchaseItemList().size(); i++){
				purchaseOrderHeader.getPurchaseItemList().get(i).setHeaderDetails(purchaseOrderHeader);
				s.save(purchaseOrderHeader.getPurchaseItemList().get(i));
			}
			response.setStatus(200);
			response.setMessage("succesfull");
			tx.commit();
			
		}
		catch(Exception e){
			
			response.setStatus(500);
			response.setMessage("unsuccesfull");
			tx.rollback();
			
		}
		finally{
			s.close();
		}
		return response;
	}
	
	// edit purchaseOrder
	public Response editpurchaseOrder(PurchaseOrderHeader purchaseOrderHeader){
		try{
			s=sf.openSession();
			tx=s.beginTransaction();
			s.createSQLQuery("update purchaseorderheader set purchaseOrderHeader.company_code=company_code,purchaseOrderHeader.status=status,purchaseOrderHeader.vendor=vendor,purchaseOrderHeader.currency=currency where purchaseOrderHeader.document_number=purchaseOrderHeader.document_number");
			
//			s.update(purchaseOrderHeader);
			tx.commit();
			response.setStatus(200);
			response.setMessage("updated");
		}
		catch (Exception e) {
			tx.rollback();
			response.setStatus(500);
			response.setMessage("not updated");
		} finally {
			s.close();
		}
		return response;
	}
	
	//delete order
	public Response deleteOrder(int num){
		try{
			s=sf.openSession();
		
		tx=s.beginTransaction();
		
	PurchaseOrderHeader purchaseOrderHeader=s.get(PurchaseOrderHeader.class, num);
	s.delete(purchaseOrderHeader);
	tx.commit();
	response.setStatus(200);
	response.setMessage("deleted");
		}
		catch(Exception e){
			tx.rollback();
			response.setStatus(500);
			response.setMessage("not deleted");
		}
	return response;
	}
	
	//update purchaseItem
	public Response editPurchaseItem(PurchaseItem purchaseItem){
		try{
			s=sf.openSession();
			tx=s.beginTransaction();
			s.createSQLQuery("update purchaseitem set purchaseitem.itemname=itemname,purchaseitem.itemprice=itemprice where purchaseitem.itemId=purchaseitem.itemId");
			s.update(purchaseItem);
			tx.commit();
			response.setStatus(200);
			response.setMessage("items edited");
		}
		catch(Exception e){
			tx.rollback();
			response.setStatus(500);
			response.setMessage("items not updated");
		}
		finally{
			s.close();	
		}
		return response;
	}
	
	//getall from both tables;
	public List<PurchaseOrderHeader>  getAll121(){
		s=sf.openSession();
		tx=s.beginTransaction();
		CriteriaBuilder cb = s.getCriteriaBuilder();
	    CriteriaQuery<PurchaseOrderHeader> cq = cb.createQuery(PurchaseOrderHeader.class);
	    Root<PurchaseOrderHeader> rootEntry = cq.from(PurchaseOrderHeader.class);
	    CriteriaQuery<PurchaseOrderHeader> all = cq.select(rootEntry);
	 
	    TypedQuery<PurchaseOrderHeader> allQuery = s.createQuery(all);
//	    tx.commit();
	    return allQuery.getResultList();
		
//		
//		List<PurchaseOrderHeader> list=s.createSQLQuery(" select itemname,itemprice,currency,vendor,status,company_code,itemid,purchaseitem.document_number  from purchaseitem left join  purchaseorderheader on purchaseitem.document_number=purchaseorderheader.document_number;").list();
		
//	return list;
	}
		// get purchaseOrder with items
	public List<PurchaseOrderHeader>  getAll(PurchaseOrderHeader purchaseOrderHeader){
		s=sf.openSession();
		tx=s.beginTransaction();
		List<Object[]> items=s.createSQLQuery("select company_code,currency,status,vendor from purchaseOrderHeader where document_number ="+purchaseOrderHeader.getDocument_number()).list();
	List<PurchaseOrderHeader> list=new ArrayList<PurchaseOrderHeader>();
	for(Object[] obj:items){
		PurchaseOrderHeader purchaseOrderHeader1=new PurchaseOrderHeader();
		
		purchaseOrderHeader1.setCurrency((String) obj[0]);
		purchaseOrderHeader1.setStatus((String) obj[1]);
		purchaseOrderHeader1.setVendor((String) obj[2]);	
		purchaseOrderHeader1.setCompany_code((String) obj[3]);
		
		list.add(purchaseOrderHeader1);
	}
	tx.commit();
	return list;
	}
	public List<PurchaseOrderHeader> getall123(){
		s=sf.openSession();
		tx=s.beginTransaction();
		
		List<PurchaseOrderHeader> list=s.createSQLQuery(" select purchaseitem.document_number, company_code,status,vendor,currency,itemname,itemprice from purchaseitem inner join  purchaseorderheader on purchaseitem.document_number=purchaseorderheader.document_number").list();
	tx.commit();
	return list;
	}
	//get purchase order by document_number
	public List<PurchaseOrderHeader> getById(int  document_number){
		s=sf.openSession();
		tx=s.beginTransaction();
		Criteria ct=s.createCriteria(PurchaseOrderHeader.class);
		ct.add(Restrictions.eq("document_number", document_number));
		List<PurchaseOrderHeader> list=ct.list();
		tx.commit();
		
/*		List<PurchaseOrderHeader> res= s.createSQLQuery("select * from purchaseorderheader where document_number =document_number").list();
*/		
		return list;
	}

	//get purchaseitem by itemid
	public List<PurchaseItem> getbyId(int itemId){
		s=sf.openSession();
		tx=s.beginTransaction();
//		List<PurchaseItem> item= s.createSQLQuery("select * from PurchaseItem where itemId =itemId").list();
//		
		Criteria ct=s.createCriteria(PurchaseItem.class);
		ct.add(Restrictions.eq("itemId", itemId));
		List<PurchaseItem> item=ct.list();
		tx.commit();
		return item;
	}
	
	//delete items from purchaseitem
	public Response deleteItems(int itemId){
		try{s=sf.openSession();
		tx=s.beginTransaction();
		Query query=s.createSQLQuery("delete from purchaseitem where itemId=itemId");
		query.executeUpdate();
		response.setStatus(200);
		response.setMessage("succesfull");
		tx.commit();
		}
		catch(Exception e){
			response.setStatus(500);
			response.setMessage("unsuccsefull");
			tx.rollback();
		}
		finally{
			s.clear();
		}
		return response;
	}
	
	
	public Response deleteitem(int itemId){
		try{
			s=sf.openSession();
			tx=s.beginTransaction();
			PurchaseItem purchaseItem=s.get(PurchaseItem.class, itemId);
			s.delete(purchaseItem);
			tx.commit();
			response.setStatus(200);
			response.setMessage("deleted");
				}
				catch(Exception e){
					tx.rollback();
					response.setStatus(500);
					response.setMessage("not deleted");
				}
			return response;
		}
	
	/*public Response updateAll(PurchaseOrderHeader purchaseOrderHeader){
		try{
			s=sf.openSession();
			tx=s.beginTransaction();
		
			s.createSQLQuery(" update purchaseorderheader set purchaseorderheader.currency=currency,purchaseorderheader.status=status,purchaseorderheader.vendor=vendor,purchaseorderheader.company_code=company_codewhere purchaseorderheader.document_number=purchaseorderheader.document_number");
			s.update(purchaseOrderHeader);
		Query query=s.createSQLQuery("update purchaseitem set purchaseitem.itemname=itemname,purchaseitem.itemprice=itemprice where purchaseitem.document_number=purchaseitem.document_number");
		query.executeUpdate();
		
		tx.commit();
		response.setStatus(200);
		response.setMessage("updated");
		}
		catch(Exception e){
			tx.rollback();
			response.setStatus(500);
			response.setMessage("not updated");
		}
		finally{
			s.close();
		}
		return response;
	}*/
	public Response updateAll(PurchaseOrderHeader purchaseOrderHeader){
		try{
			s=sf.openSession();
			tx=s.beginTransaction();
		Query query=s.createSQLQuery("UPDATE purchaseitem,purchaseorderheader  SET purchaseitem.itemprice=itemprice,purchaseitem.itemname=itemname,purchaseorderheader.currency=currency,purchaseorderheader.status=status,purchaseorderheader.vendor=vendor,purchaseorderheader.company_code=company_code where purchaseitem.document_number=purchaseorderheader.document_number and purchaseorderheader.document_number=purchaseorderheader.document_number");
/*			Query query=s.createSQLQuery("update purchaseorderheader set purchaseorderheader.currency=currency,purchaseorderheader.status=status,purchaseorderheader.vendor=vendor,purchaseorderheader.company_code=company_code where document_number=purchaseorderheader.document_number");
*/	     
		s.update(purchaseOrderHeader);	
		
			//System.err.println("try block response",+response);
			tx.commit();
			response.setStatus(200);
			response.setMessage("updated");
			System.err.println("try block response"+purchaseOrderHeader);

		}
		catch(Exception e){
			tx.rollback();
			response.setStatus(500);
			response.setMessage("not updated");
		}
		finally{
			s.close();
		}
		return response;
	}
	
	
}

