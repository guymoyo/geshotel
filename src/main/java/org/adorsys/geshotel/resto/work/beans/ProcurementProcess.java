package org.adorsys.geshotel.resto.work.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONSerializer;

import org.adorsys.geshotel.domain.ext.IdGenerator;
import org.adorsys.geshotel.domain.security.SecurityUtil;
import org.adorsys.geshotel.resto.domain.BarRestauState;
import org.adorsys.geshotel.resto.domain.Inventory;
import org.adorsys.geshotel.resto.domain.InventoryItem;
import org.adorsys.geshotel.resto.domain.Procurement;
import org.adorsys.geshotel.resto.domain.ProcurementItem;
import org.adorsys.geshotel.resto.domain.Product;
import org.springframework.orm.jpa.JpaOptimisticLockingFailureException;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.transaction.annotation.Transactional;
@RooJavaBean
public class ProcurementProcess {
	private boolean isProcurementSelected;
	private boolean isProcurementInited;
	private boolean isProcurementClosed;
	private static ProcurementProcess procurementProcess;
	private static Object synchroneObject__ = new Object();
	private List<Procurement> openedProcurements ;
	public static ProcurementProcess getInstance(){
		if(procurementProcess == null){
			synchronized (synchroneObject__) {
				if(procurementProcess == null){
					procurementProcess = new ProcurementProcess();
				}
			}
		}
		return procurementProcess ;
	}
	@Transactional(rollbackFor=Exception.class)
	public Procurement initProcurementProcess(){
		Procurement openedProcurement = Procurement.findOpenedProcurement();
		
//		if(!this.isProcurementInited || this.isProcurementClosed){
		if(openedProcurement == null){
			Procurement procurement = new Procurement();
			procurement.setAmount(BigDecimal.ZERO);
			procurement.setDateOfProcurement(new Date());
			procurement.setProcurementKey(IdGenerator.generateId());
			procurement.setIsLatestProcurement(Boolean.FALSE);
			procurement.setUserAccount(SecurityUtil.getBaseUser());
			procurement.setProcurementState(BarRestauState.OPENED);
			procurement.persist();
			procurement.flush();
			return procurement;
		}else {
			return openedProcurement;
		}
	}
	public void findUserOpenedProcurement(){
		
	}
	@Transactional(rollbackFor=Exception.class)
	public void addProcurementItem(ProcurementItem procurementItem,Procurement procurement)throws Exception{
		if(procurementItem == null) throw new NullPointerException("The procurement item is Empty or Null");
		procurementItem.persist();
		procurementItem.flush();
//		Procurement procurement = Procurement.findOpenedProcurements().iterator().next();
		procurement.setAmount(procurement.getAmount().add(procurementItem.getAmount()));
		procurement.merge().flush();
		Inventory inventory = Inventory.findCurrentInventory()/*SaleProcess.getInstance().getInventory()*/;
		System.out.println("\n \n [Procurement Process] Updating Inventory "+inventory);
		if(inventory != null){
			updateInvItemAfterProcItemAdded(procurementItem, inventory);
		}
//		this.procurementItems.add(procurementItem);
	}
	@Transactional(rollbackFor=JpaOptimisticLockingFailureException.class)
	public Procurement closeProcurementProcess(String procurementKey){
		//Je recherche le dernier approvisionement et je le met a false : Il n'est plus le dernier approvisionement. 
		List<Procurement> procurements = Procurement.findProcurementsByIsLatestProcurementNot(Boolean.FALSE).getResultList();
		System.out.println("\n \n Latests Procurements : "+procurements);
		if(procurements != null && !procurements.isEmpty()){
			Procurement next = procurements.iterator().next();
			next.setIsLatestProcurement(Boolean.FALSE);
			next.merge().flush();
			System.out.println("\n \n Merging the previous procurement to false");
		}
		//Je marque l'approvisionement courant comme etant le dernier approvionsement.
		Procurement procurement = Procurement.findOpenedProcurement();
		List<ProcurementItem> procurementItems = ProcurementItem.findProcurementItemsByProcurement(procurement).getResultList();
		for (ProcurementItem procurementItem : procurementItems) {
			Product product = Product.findProduct(procurementItem.getProduct().getId());
			product.setQty(product.getQty().add(procurementItem.getQty()));
			product.merge().flush();
		}
		procurement.setIsLatestProcurement(Boolean.TRUE);
		procurement.setProcurementState(BarRestauState.CLOSED);
		procurement.merge().flush();
		return procurement;
	}
	public void suspendProcurment(){
		Procurement procurement = Procurement.findOpenedProcurement();
		procurement.setProcurementState(BarRestauState.SUSPENDED);
		procurement.merge().flush();
	}
	public ProcurementItem findPreviousProcurementItem(Product product) throws Exception{
		checkProcurementInited();
		List<Procurement> procurements = Procurement.findProcurementsByIsLatestProcurementNot(Boolean.FALSE).getResultList();
		if(procurements == null  || procurements.isEmpty()) throw new NullPointerException("No Previous Procurement Found");
		Procurement next = procurements.iterator().next();
		ProcurementItem procurementItem;
		try {
			procurementItem = ProcurementItem.findProcurementItemsByProcurementAndProduct(next, product).getResultList().iterator().next();
		} catch (Exception e) {
			throw new Exception("No Previous Procurement Item found");
		}
		return procurementItem;
	}
	private void checkProcurementInited() {
		if(!this.isProcurementInited) throw new IllegalStateException("The Procurement is not yet initialized");
	}
	@Transactional(rollbackFor=Exception.class)
	public void cancelAllProcurementProcess() throws Exception{
		checkProcurementInited();
		List<ProcurementItem> procurementItems = ProcurementItem.findCurrentProcurementItems();
		for (ProcurementItem var : procurementItems) {
			var.remove();
		}
		//TODO :try to revert inventory
		procurementItems.clear();
		Procurement.findOpenedProcurement().remove();
	}
	public void updateInvItemAfterProcItemAdded(ProcurementItem procurementItem,Inventory inventory){
		if(procurementItem == null) return ;
		InventoryItem item = InventoryItem.findInventoryItemsByInventoryAndDesignationEquals(inventory, procurementItem.getProduct().getTitle()).getResultList().iterator().next();
		if(item == null){
			//create new Inventory Item and store It.
			InventoryItem inventoryItem = new InventoryItem();
			inventoryItem.setDesignation(procurementItem.getProduct().getTitle());
			inventoryItem.setEntry(procurementItem.getQty());
			inventoryItem.setSales(BigDecimal.ZERO);
			inventoryItem.setFinalStock(null);
			inventoryItem.setInitialStock(null);
			inventoryItem.setInventory(inventory);
			inventoryItem.setProduct(procurementItem.getProduct());
			inventoryItem.persist();
			inventoryItem.flush();
			System.out.println("Done");
			return ;
		}
		System.out.println("\n \n Item [procurement process] : "+item);
		BigDecimal augend = item.getEntry();
		if(augend == null) {
			augend = procurementItem.getQty();
		}else{
			augend = procurementItem.getQty().add(augend);
		}
		item.setEntry(augend);
		item.merge().flush();
	}
	public void updateProductAfterProcurementItem(ProcurementItem procurementItem){
		if(procurementItem == null) return ;
		Product findProduct = Product.findProduct(procurementItem.getProduct().getId());
		if(findProduct.getQty() == null) findProduct.setQty(BigDecimal.ZERO);
		findProduct.setQty(findProduct.getQty().add(procurementItem.getQty()));
		findProduct.merge().flush();
	}
	public String findAllProcurementItemToJson(String procurementKey)throws Exception {
		Procurement procurement = Procurement.findProcurementsByProcurementKeyEquals(procurementKey).getSingleResult();
		List<ProcurementItem> findAllProcurementItems = ProcurementItem.findProcurementItemsByProcurement(procurement).getResultList();
		return JSONSerializer.toJSON(findAllProcurementItems).toString();
	}
}