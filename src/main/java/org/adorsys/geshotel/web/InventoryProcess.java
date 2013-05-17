package org.adorsys.geshotel.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adorsys.geshotel.domain.UserAccount;
import org.adorsys.geshotel.domain.security.SecurityUtil;
import org.adorsys.geshotel.resto.domain.BarRestauState;
import org.adorsys.geshotel.resto.domain.Inventory;
import org.adorsys.geshotel.resto.domain.InventoryItem;
import org.adorsys.geshotel.resto.domain.PeriodConf;
import org.adorsys.geshotel.resto.domain.Product;
import org.adorsys.geshotel.resto.domain.ProductType;
import org.adorsys.geshotel.resto.work.beans.InventoryDateComparator;
import org.adorsys.geshotel.resto.work.beans.SaleProcess;
import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.ListUtils;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;
@RooToString
@RooJavaBean
public class InventoryProcess {
	private List<ProductType> productTypes = new ArrayList<ProductType>();
	private List<Product> products = new ArrayList<Product>();
	private List<InventoryItem> inventoryItems = ListUtils.lazyList(new ArrayList<InventoryItem>(), FactoryUtils.instantiateFactory(InventoryItem.class));;
//	private Inventory inventory = null;
	private boolean isProcessDone;
	private boolean isInited ;
	private ProductType productType;
	private List<InventoryItem> specificInvItem = null;
	private int index ;
	private Map<ProductType, Product> productTypeAndProducts = new HashMap<ProductType, Product>();
	private Map<ProductType, List<Product>> productsByTypes = new HashMap<ProductType, List<Product>>();
	private Map<Inventory, InventoryItem> inventoryAndInvItems = new HashMap<Inventory, InventoryItem>();	
	
	private static InventoryProcess inventoryProcess = null;
	private static Object objectSynchrone__ = new Object();//cet object n'est purement que technique. Utilise pour la synchronisation
	private  InventoryProcess(List<Product> pProducts) {
		if(inventoryProcess == null){
		this.productTypes = ProductType.findAllProductTypes();
		this.products = pProducts;
		initProductTypeAndProducts(productTypes, products);
		Inventory inventory = newInventory("Inventaire du Bar", new Date(), Boolean.FALSE, SecurityUtil.getBaseUser().getPeriodConf(), SecurityUtil.getBaseUser());
		makeInventoryItemFromProduct(products, inventory);
		initInventoryAndInvItem(inventory, inventoryItems);
		this.isProcessDone = false;
		}
	}
	private InventoryProcess(){
		this(Product.findAllProducts());
	}
	public static InventoryProcess getInstance(){
		if(inventoryProcess == null || (inventoryProcess.isProcessDone == true)){
			//en cas d'appels multithread.
			synchronized(objectSynchrone__){ 
				if(inventoryProcess == null ){
					inventoryProcess = new InventoryProcess(Product.findAllProducts());
					return inventoryProcess;
				}else if( (inventoryProcess.isProcessDone == true) && Inventory.countInventorys() > 0){
					//load the latest inventory
					List<Inventory> inventories = Inventory.findAllInventorys();
				/*	int index = 0;
					Iterator<Inventory> iterator = inventories.iterator();
					while(iterator.hasNext()){
						index++;
					}
					Inventory inv = inventories.get(index);*/
					Collections.sort(inventories, new InventoryDateComparator());//sort to get the latest inventory.
					Inventory inv = inventories.get(inventories.size()-1);
					//load all product mathing
					List<InventoryItem> resultList = InventoryItem.findInventoryItemsByInventory(inv).getResultList();
					List<Product> arrayListProducts = new ArrayList<Product>();
					for (InventoryItem inventoryItem : resultList) {
						Product next = Product.findProductsByTitleEquals(inventoryItem.getDesignation()).getResultList().iterator().next();
						next.setQty(inventoryItem.getFinalStock());
						arrayListProducts.add(next);
					}
					inventoryProcess = new InventoryProcess(arrayListProducts);
					return inventoryProcess;
				}
			}
		}
		return inventoryProcess;
	}
	private void initProductTypeAndProducts(List<ProductType> types,List<Product> products)throws NullPointerException{
		for (Product product : products) {
			ProductType type = product.getProductType();
			for (ProductType productType : types) {
				if(productType.equals(type)){
					this.productTypeAndProducts.put(type, product);
					break;
				}
			}
		}
	}
	public Inventory findInventoryFromSaleProcess(){
		Inventory inventoryFromSaleProcess = Inventory.findCurrentInventory();/*SaleProcess.getInstance().getInventory()*/;
		if(inventoryFromSaleProcess == null ){
			inventoryFromSaleProcess = SaleProcess.getInstance().openInventory(false);
		}
		return inventoryFromSaleProcess;
	}
	private void initProductByTypes(List<ProductType> productTypes,List<Product> products){
		
	}
	private void makeInventoryItemFromProduct(List<Product> pProducts,Inventory inv)throws NullPointerException{
		for (Product product : pProducts) {
			InventoryItem inventoryItem = new InventoryItem();
			inventoryItem.setDesignation(product.getTitle());
			inventoryItem.setInitialStock(product.getQty());
			inventoryItem.setInventory(inv);
			inventoryItem.persist();
			inventoryItem.flush();
//			this.inventoryItems.add(inventoryItem);
		}
	}
	public Inventory newInventory(String title, Date inventoryDate, Boolean isClosed,
			PeriodConf periodConf, UserAccount userAccount) {
		Inventory inventory = new Inventory();
		inventory.setInventoryDate(inventoryDate);
		inventory.setIsClosed(isClosed);
		inventory.setPeriodConf(periodConf);
		inventory.setTitle(title);
		inventory.setUserAccount(userAccount);
		inventory.setInventoryState(BarRestauState.OPENED);
		inventory.persist();
		inventory.flush();
		return inventory;
	}
	private void initInventoryAndInvItem(Inventory pInventory,List<InventoryItem> pInventoryItems)throws NullPointerException{
		for (InventoryItem inventoryItem : pInventoryItems) {
			this.inventoryAndInvItems.put(pInventory, inventoryItem);
		}
	}
	
	public void addFillInventoryFromCommand(InventoryCmd cmd){
		
	}
	@Transactional
	public void updateInventoryItems(List<InventoryItem> items){
		this.setInventoryItems(items);
		for (InventoryItem inventoryItem : this.inventoryItems) {
			inventoryItem.merge();
			inventoryItem.flush();
		}
	}
	@Transactional
	public Inventory closeProcess(boolean done){
		List<InventoryItem> currentInventoryItems = InventoryItem.findCurrentInventoryItems();
		for (InventoryItem inventoryItem : currentInventoryItems) {
			System.out.println("\n \n Inventory Item closing ... \t "+inventoryItem);
			checkInventoryValue(inventoryItem);
			inventoryItem.setFinalStock(inventoryItem.getInitialStock().add(inventoryItem.getEntry()).subtract(inventoryItem.getSales()));
			inventoryItem.merge();
			inventoryItem.flush();
		}
		Inventory currentInventory = Inventory.findCurrentInventory();
		currentInventory.setInventoryState(BarRestauState.CLOSED);
		currentInventory.setIsClosed(Boolean.TRUE);
		currentInventory.merge().flush();
		this.setIsProcessDone(done);
		SaleProcess.getInstance().setInventoryIsOpened(false);
		return currentInventory;
	}
	public Inventory closeProcess(Long inventoryId){
		Inventory inventory = Inventory.findInventory(inventoryId);
		List<InventoryItem> inventoryItems = InventoryItem.findInventoryItemsByInventory(inventory).getResultList();
		for (InventoryItem inventoryItem : inventoryItems) {
			System.out.println("\n \n Inventory Item closing ... \t "+inventoryItem);
			checkInventoryValue(inventoryItem);
			inventoryItem.setFinalStock(inventoryItem.getInitialStock().add(inventoryItem.getEntry()).subtract(inventoryItem.getSales()));
			inventoryItem.merge();
			inventoryItem.flush();
		}
		inventory.setInventoryState(BarRestauState.CLOSED);
		inventory.setIsClosed(Boolean.TRUE);
		inventory.merge();
		inventory.flush();
		this.setIsProcessDone(true);
		SaleProcess.getInstance().setInventoryIsOpened(false);
		return inventory;
	}
	private void checkInventoryValue(InventoryItem inventoryItem) {
		if(inventoryItem.getEntry() == null) inventoryItem.setEntry(BigDecimal.ZERO);
		if(inventoryItem.getSales() == null ) inventoryItem.setSales(BigDecimal.ZERO);
	}
}
