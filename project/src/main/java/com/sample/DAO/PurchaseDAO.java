package com.sample.DAO;
import java.util.ArrayList;
import java.util.List;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.sample.DTO.PurchaseItemDto;
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
			orderDto.setPurchaseItemList(PurchaseOrderHeaderDo.getPurchaseItemList());

		return orderDto;
	}
	public PurchaseOrderHeader importDao(PurchaseOrderHeaderDto orderDto){
		PurchaseOrderHeader orderDo=new PurchaseOrderHeader();
			orderDo.setCompany_code(orderDto.getCompany_code());
			orderDo.setCurrency(orderDto.getCurrency());
			orderDo.setDocument_number(orderDto.getDocument_number());
			orderDo.setStatus(orderDto.getStatus());
			orderDo.setVendor(orderDto.getVendor());
			orderDo.setPurchaseItemList(orderDto.getPurchaseItemList());

		return orderDo;
	}
	public PurchaseItemDto exportDao(PurchaseItem PurchaseItemDo){
		PurchaseItemDto itemDto=new PurchaseItemDto();
			itemDto.setItemName(PurchaseItemDo.getItemName());
			itemDto.setItemPrice(PurchaseItemDo.getItemPrice());
			itemDto.setItemId(PurchaseItemDo.getItemId());
			itemDto.setHeaderDetails(PurchaseItemDo.getHeaderDetails());

		return itemDto;

	}

	public PurchaseItem importDao(PurchaseItemDto itemDto){
		PurchaseItem itemDo=new PurchaseItem();
			itemDo.setItemName(itemDto.getItemName());
			itemDo.setItemPrice(itemDto.getItemPrice());
			itemDo.setItemId(itemDto.getItemId());
			itemDo.setHeaderDetails(itemDto.getHeaderDetails());
		return itemDo;

	}
	// add only purchaseOrderHeader 
	public Response addPurchaseOrder(PurchaseOrderHeaderDto purchaseOrderHeaderDto){

		try{
			s=sf.openSession();
			tx=s.beginTransaction();
			PurchaseOrderHeader orderDo=importDao(purchaseOrderHeaderDto);
			s.save(orderDo);
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

	
// get purchaseOrderHeader without purchaseItem
	@SuppressWarnings("deprecation")
	public List<PurchaseOrderHeaderDto> GetPurchaseOrder(){
		List<PurchaseOrderHeaderDto> orderDtoList=null;
		try{
			s=this.sf.openSession();
			tx=s.beginTransaction();
			@SuppressWarnings("unchecked")
			List<PurchaseOrderHeader> OrderDoList=s.createCriteria(PurchaseOrderHeader.class).list();
			if(OrderDoList!=null && OrderDoList.size()>0 ){
				orderDtoList=new ArrayList<PurchaseOrderHeaderDto>();
				PurchaseOrderHeaderDto orderDto=null;
				for(PurchaseOrderHeader order:OrderDoList){
					orderDto=exportDao(order);
					orderDto.setPurchaseItemList(null);
					orderDtoList.add(orderDto);
				}
				
			}
		}catch(Exception e){
			System.err.println("error " + e.getMessage());
		}finally{
			s.close();
		}

		return orderDtoList;
	}

	//get purchaseItem without headerDetails
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<PurchaseItemDto> getPurchaseItem(){
		List<PurchaseItemDto> itemDtoList=null;
		try{
			s=sf.openSession();
			tx=s.beginTransaction();
			Criteria crit =s.createCriteria(PurchaseItem.class);
			/*ProjectionList p1=Projections.projectionList();
			p1.add(Projections.property("itemName"));
			p1.add(Projections.property("itemPrice"));
			p1.add(Projections.property("itemId"));
			crit.setProjection(p1);*/
			List<PurchaseItem> itemDoList = crit.list();
			if(itemDoList!=null&&itemDoList.size()>0){
			itemDtoList=new ArrayList<PurchaseItemDto>();
			PurchaseItemDto itemDto=null;
			for(PurchaseItem item:itemDoList){
				itemDto=exportDao(item);
				itemDto.setHeaderDetails(null);
				itemDtoList.add(itemDto);
			}}
			
/*			itemDto.setHeaderDetails(null);
*/		}
		catch(Exception e){
			System.err.println("error " + e.getMessage());
		}
				finally{
			 s.close();
		 }


		return itemDtoList;
	}

	//save both purchaseOrderHeader and purchaseItems
	public Response saveAll(PurchaseOrderHeaderDto purchaseOrderHeaderDto){
		try{
			s=sf.openSession();
			tx=s.beginTransaction();
			PurchaseOrderHeader orderDo=importDao(purchaseOrderHeaderDto);
			for(int i=0;i<orderDo.getPurchaseItemList().size(); i++){
				orderDo.getPurchaseItemList().get(i).setHeaderDetails(orderDo);
				s.save(orderDo.getPurchaseItemList().get(i));
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

	// update purchaseOrderHeader using query
	public Response updatePurchaseOrder(PurchaseOrderHeaderDto purchaseOrderHeaderDto){
		try{
			s=sf.openSession();
			tx=s.beginTransaction();
			PurchaseOrderHeader orderDo=importDao(purchaseOrderHeaderDto);
			@SuppressWarnings("rawtypes")
			Query query=s.createQuery("update PurchaseOrderHeader set company_code= :company_code,"
					+ "status= :status, vendor= :vendor, currency= :currency "
					+ "where document_number= :document_number");
			query.setParameter("company_code",orderDo.getCompany_code());
			query.setParameter("status",orderDo.getCompany_code());
			query.setParameter("vendor",orderDo.getVendor());
			query.setParameter("currency",orderDo.getCurrency());
			query.setParameter("document_number",orderDo.getDocument_number());
			query.executeUpdate();
			response.setStatus(200);
			response.setMessage("updated");
		}catch(Exception e){
			System.out.println(e.getMessage());
			tx.rollback();
			response.setStatus(500);
			response.setMessage("not updated");
			
		}finally{
			s.close();
		}
		
		
		return response;
		
	}
	// update only purchaseOrderHeader
	public Response editpurchaseOrder(PurchaseOrderHeaderDto purchaseOrderHeaderDto){
		try{
			s=sf.openSession();
			tx=s.beginTransaction();
			PurchaseOrderHeader purchaseorderheader=importDao(purchaseOrderHeaderDto);
			s.createSQLQuery("update purchaseorderheader set purchaseOrderHeader.company_code=company_code,purchaseOrderHeader.status=status,purchaseOrderHeader.vendor=vendor,purchaseOrderHeader.currency=currency where purchaseOrderHeader.document_number=purchaseOrderHeader.document_number");

			s.update(purchaseorderheader);
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

		//update only purchaseItems
		public Response editItems(PurchaseItemDto purchaseItemDto){
			try{
				s=sf.openSession();
				tx=s.beginTransaction();
				PurchaseItem itemDo=importDao(purchaseItemDto);
				@SuppressWarnings("rawtypes")
				Query query=s.createQuery("update PurchaseItem set itemName= :itemName, itemPrice= :itemPrice where itemId= :itemId");
			    
				query.setParameter("itemName",itemDo.getItemName());
				query.setParameter("itemPrice",itemDo.getItemPrice());
				query.setParameter("itemId", itemDo.getItemId());
				query.executeUpdate();
			response.setStatus(200);
			response.setMessage("updated");
			}catch (Exception e) {
				System.err.println("error " + e.getMessage());
				response.setStatus(500);
				response.setMessage("not updated");
				tx.rollback();
			}finally{
				s.close();
			}
			
			
			
			return response;
			
		}
	//delete purchaseOrderHeader and purchaseItem using document_number
	public Response deleteOrder(PurchaseOrderHeaderDto purchaseOrderHeaderDto){
		try{
			s=sf.openSession();
			tx=s.beginTransaction();

			PurchaseOrderHeader orderDo=importDao(purchaseOrderHeaderDto);
			PurchaseOrderHeader purchaseOrderHeader=s.get(PurchaseOrderHeader.class, orderDo.getDocument_number());
			
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

	// delete purchaseItem and purchaseOrderHeader using itemId
	public Response deleteitem(PurchaseItemDto purchaseItemDto){
		try{
			s=sf.openSession();
			tx=s.beginTransaction();
			PurchaseItem itemDo=importDao(purchaseItemDto);
			PurchaseItem purchaseItem=s.get(PurchaseItem.class, itemDo.getItemId());
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
	
	
	//get purchase order by document_number
	@SuppressWarnings("deprecation")
	public List<PurchaseOrderHeaderDto> getOrderById(PurchaseOrderHeaderDto  purchaseOrderHeaderDto){
		List<PurchaseOrderHeaderDto> orderDtoList=null;
		
		try{s=sf.openSession();
		tx=s.beginTransaction();
		PurchaseOrderHeader orderDo=importDao(purchaseOrderHeaderDto);
		Criteria ct=s.createCriteria(PurchaseOrderHeader.class);
		ct.add(Restrictions.eq("document_number", orderDo.getDocument_number()));
		@SuppressWarnings("unchecked")
		List<PurchaseOrderHeader> listDo=ct.list();
		orderDtoList=new ArrayList<PurchaseOrderHeaderDto>();
		PurchaseOrderHeaderDto orderDto=null;
		if(listDo!=null &&listDo.size()>0)
			for(PurchaseOrderHeader order:listDo){
				orderDto=exportDao(order);
				
			}
		orderDtoList.add(orderDto);
			tx.commit();
		
		}
		catch(Exception e){
			System.err.println("error " + e.getMessage());
		}
		finally{
			s.close();
		}
		/*		List<PurchaseOrderHeader> res= s.createSQLQuery("select * from purchaseorderheader where document_number =document_number").list();
		 */		
		return orderDtoList;
	}

	//get purchaseItem by itemId
	public List<PurchaseItemDto> getItembyId(PurchaseItemDto purchaseItemDto){
		List<PurchaseItemDto> itemDtoList=null;
		try{

			s=sf.openSession();
			tx=s.beginTransaction();
			//		List<PurchaseItem> item= s.createSQLQuery("select * from PurchaseItem where itemId =itemId").list();
			//		
			PurchaseItem itemDo1=importDao(purchaseItemDto);
			@SuppressWarnings("deprecation")
			Criteria ct=s.createCriteria(PurchaseItem.class);
			ct.add(Restrictions.eq("itemId", itemDo1.getItemId()));
			@SuppressWarnings("unchecked")
			List<PurchaseItem> itemDo=ct.list();
			if(itemDo!=null &&itemDo.size()>0){
				itemDtoList=new ArrayList<PurchaseItemDto>();
				PurchaseItemDto itemDto=null;
				for(PurchaseItem item:itemDo){
					itemDto=exportDao(item);
					itemDtoList.add(itemDto);
				}
				tx.commit();
			}

		}catch(Exception e){
			System.err.println(e.getMessage());
		}
		finally{
			s.close();
		}
		return itemDtoList;
	}
	
// update both purchaseOrderHeader and purchaseItem
	public Response updateAll(PurchaseOrderHeaderDto purchaseOrderHeaderDto){
		try{
			s=sf.openSession();
			tx=s.beginTransaction();
			PurchaseOrderHeader orderDo=importDao(purchaseOrderHeaderDto);
			for(PurchaseItem item:orderDo.getPurchaseItemList()){
				item.setHeaderDetails(orderDo);
				s.update(item);
			}
			orderDo.setPurchaseItemList(null);
			
			s.update(orderDo);
			tx.commit();
			response.setStatus(200);
			response.setMessage("updated");
			System.err.println("try block response"+orderDo);

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

	//get both purchaseOrderHeader and purchaseItem
		public List<PurchaseOrderHeaderDto>  getAll121(){
			List<PurchaseOrderHeaderDto> orderDto=null;
			try{
				s=sf.openSession();
				tx=s.beginTransaction();
				CriteriaBuilder cb = s.getCriteriaBuilder();
				CriteriaQuery<PurchaseOrderHeader> cq = cb.createQuery(PurchaseOrderHeader.class);
				Root<PurchaseOrderHeader> rootEntry = cq.from(PurchaseOrderHeader.class);
				CriteriaQuery<PurchaseOrderHeader> all = cq.select(rootEntry);

				List<PurchaseOrderHeader> allQuery = s.createQuery(all).list();
				if(allQuery!=null&&allQuery.size()>0){
					PurchaseOrderHeaderDto orderDto1=null;
					orderDto=new ArrayList<PurchaseOrderHeaderDto>();
					for(PurchaseOrderHeader order:allQuery){
						orderDto1=exportDao(order);
						orderDto.add(orderDto1);
					}
				}
				//	    tx.commit();za
			}catch(Exception e){
				System.err.println(e.getMessage());
			}finally{
				s.clear();
			}
			return orderDto;

			
		}
		// not working
		@SuppressWarnings("unchecked")
		public List<PurchaseItemDto> getItem(){
			List<PurchaseItemDto> itemDtoList=null;
			
			try{
				s=sf.openSession();
				tx=s.beginTransaction();
				@SuppressWarnings("rawtypes")
				Query query=(Query) s.createSQLQuery("select itemName,itemPrice from PurchaseItem ").list();

				
				
				List<PurchaseItem> itemDoList=query.list();
				if(itemDoList!=null && itemDoList.size()>0){
					itemDtoList=new ArrayList<PurchaseItemDto>();
					PurchaseItemDto itemDto=null ;
					for(PurchaseItem item:itemDoList){
						itemDto=exportDao(item);
						itemDtoList.add(itemDto);
					}
				}
			}catch (Exception e) {
				System.err.println("error " + e.getMessage());
			}finally{
				s.close();
			}
			return itemDtoList;
		}
		
		@SuppressWarnings({ "deprecation", "unchecked" })
		public List<PurchaseItem> getOrderABC(){
			
			Criteria crit =s.createCriteria(PurchaseItem.class);
			ProjectionList p1=Projections.projectionList();
			p1.add(Projections.property("itemName"));
			p1.add(Projections.property("itemPrice"));
			p1.add(Projections.property("itemId"));
			crit.setProjection(p1);
			List<PurchaseItem> itemDoList = crit.list();
			return itemDoList;
		}
}
/*Query query= (Query) s.createSQLQuery("UPDATE purchaseitem,purchaseorderheader "
+ " SET purchaseitem.itemprice= :itemprice,"
+ " purchaseitem.itemname= :itemname,"
+ "purchaseorderheader.currency= :currency,"
+ "purchaseorderheader.status= :status,"
+ "purchaseorderheader.vendor= :vendor,"
+ "purchaseorderheader.company_code= :company_code "
+ "where purchaseitem.document_number= :purchaseorderheader.document_number"
+ " and purchaseorderheader.document_number= :purchaseorderheader.document_number").list();
		*/

/*// get purchaseOrder with items notb required
public List<PurchaseOrderHeader>  getAll(PurchaseOrderHeader purchaseOrderHeader){
	s=sf.openSession();
	tx=s.beginTransaction();
	@SuppressWarnings("unchecked")
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

//not required
public List<PurchaseOrderHeader> getall123(){
	s=sf.openSession();
	tx=s.beginTransaction();

	@SuppressWarnings("unchecked")
	List<PurchaseOrderHeader> list=s.createSQLQuery(" select purchaseitem.document_number,"
			+ " company_code,status,vendor,currency,itemname,itemprice"
			+ " from purchaseitem inner join  purchaseorderheader on purchaseitem.document_number=purchaseorderheader.document_number").list();
	tx.commit();
	return list;
}


*/	
/*//update purchaseItem
		public Response editPurchaseItem(PurchaseItemDto purchaseItemDto){
			try{
				s=sf.openSession();
				tx=s.beginTransaction();
				PurchaseItem purchaseitem=importDao(purchaseItemDto);
				
				s.createSQLQuery("update purchaseitem set purchaseitem.itemname=itemname,purchaseitem.itemprice=itemprice where purchaseitem.itemId=purchaseitem.itemId");
				s.update(purchaseitem);
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
		}*/
/*Criteria cr = s.createCriteria(PurchaseItem.class)
.setProjection(Projections.projectionList()
  .add(Projections.property("itemName"), "itemName")
  .add(Projections.property("itemPrice"), "itemPrice"))

.setResultTransformer(Transformers.aliasToBean(PurchaseItem.class));
*/
/*Criteria crit =s.createCriteria(PurchaseItem.class);
ProjectionList p1=Projections.projectionList();
p1.add(Projections.property("itemName"));
p1.add(Projections.property("itemPrice"));
p1.add(Projections.property("itemId"));
crit.setProjection(p1);
List<PurchaseItem> itemDoList = crit.list();*/

/*
public List<PurchaseItemDto> getPurchaseItem(){
	List<PurchaseItemDto> itemDtoList=null;
	try{
		s=sf.openSession();
		tx=s.beginTransaction();
		Criteria crit =s.createCriteria(PurchaseItem.class);
		ProjectionList p1=Projections.projectionList();
		p1.add(Projections.property("itemName"));
		p1.add(Projections.property("itemPrice"));
		p1.add(Projections.property("itemId"));
		crit.setProjection(p1);
		List<Object[]> itemDoList = crit.list();
		//System.out.println("PurchaseDAO.getPurchaseItem()"+itemDoList);
		if(itemDoList!=null&&itemDoList.size()>0){
			System.out.println("PurchaseDAO.getPurchaseItem() Data :"+itemDoList);
		itemDtoList=new ArrayList<PurchaseItemDto>();
		PurchaseItemDto itemDto=null;
			itemDtoList=new ArrayList<PurchaseItemDto>();
		for(Object[] item:itemDoList){
			PurchaseItemDto dto=new PurchaseItemDto();
			System.out.print(" "+item[0]);
			System.out.print(" "+item[1]);
			System.out.print(" "+item[2]);
			System.out.println();
			dto.setItemName((String)item[0]);
			dto.setItemPrice((String)item[1]);
			dto.setItemId((Integer)item[2]);

			itemDtoList.add(dto);
		}
		}
			itemDto.setHeaderDetails(null);
		}
	catch(Exception e){
		System.err.println("error " + e.getMessage());
	}
			finally{
		 s.close();
	 }
	return itemDtoList;
}*/