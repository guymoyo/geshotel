package org.adorsys.geshotel.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.adorsys.geshotel.resto.domain.Inventory;
import org.adorsys.geshotel.resto.domain.InventoryItem;
import org.adorsys.geshotel.resto.domain.Product;
import org.adorsys.geshotel.resto.domain.ProductType;
import org.adorsys.geshotel.resto.work.beans.SaleProcess;
import org.aspectj.bridge.Message;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value="/inventoryprocess")	
public class InventoryProcessController {
	
	@RequestMapping(method=RequestMethod.GET,params="form")
	public String start(HttpServletRequest request,HttpServletResponse response,Model uiModel){
		InventoryProcess instance = InventoryProcess.getInstance();
		System.out.println("\n \n Inv Process  "+instance.getInventoryItems().toString());
		uiModel.addAttribute("inventoryprocess", instance);
		return "inventoryprocess/inventorys/create";
	}
	@RequestMapping(method=RequestMethod.GET,params="showcurrent")
	public String showCurrentInventory(HttpServletRequest request,HttpServletResponse response,Model uiModel){
		Inventory inventory = InventoryProcess.getInstance().findInventoryFromSaleProcess();
		List<InventoryItem> inventoryItems = InventoryItem.findInventoryItemsByInventory(inventory).getResultList();
		uiModel.addAttribute("inventoryItems", inventoryItems);
		uiModel.addAttribute("inventory", inventory);
		return "inventoryprocess/inventorys/showCurrentInventory";
	}
	@RequestMapping(method=RequestMethod.POST)
	public String create(InventoryProcess inventoryProcess,HttpServletRequest request,Model uiModel){
		InventoryProcess instance = InventoryProcess.getInstance();
		instance.setInventoryItems(inventoryProcess.getInventoryItems());
//		instance.updateInventoryItems(inventoryProcess.getInventoryItems());
/*		for (InventoryItem inventoryItem : instance.getInventoryItems()) {
			inventoryItem.merge();
			inventoryItem.flush();
		}*/
		instance.closeProcess(true);
		System.out.println("\n \n Inv Process  "+inventoryProcess.getInventoryItems().toString());
		uiModel.addAttribute("inventoryprocess", instance);
		return "inventoryprocess/inventorys/show";
	}
	@RequestMapping(method=RequestMethod.POST,value="/form")
	public String nextForm(InventoryProcess process, HttpServletRequest request,HttpServletResponse response,Model uiModel) throws Exception{
		ProductType productType = process.getProductType();
		List<Product> products = process.getProducts();
		List<Product> match = new ArrayList<Product>();
		for (Product product : products) {
			if(productType.equals(product.getProductType())) match.add(product);
		}
		List<InventoryItem> invMatching = new ArrayList<InventoryItem>();
		for (Product product : match) {
			List<InventoryItem> inventoryItems = process.getInventoryItems();
			for (InventoryItem inventoryItem : inventoryItems) {
				if(product.getTitle().equals(inventoryItem.getDesignation())) invMatching.add(inventoryItem); 
			}
		}
		process.setSpecificInvItem(invMatching);
		return "redirect:/inventoryprocess?form";
	}
	@RequestMapping(params="close",method=RequestMethod.GET	)
	public String closeInventory(@RequestParam("close")Long inventoryId,Model uiModel,HttpServletRequest httpServletRequest){
		Inventory inventory = InventoryProcess.getInstance().closeProcess(inventoryId);
		uiModel.addAttribute("inventory", inventory);
		uiModel.addAttribute("inventoryItems", InventoryItem.findInventoryItemsByInventory(inventory).getResultList());
		return "inventoryprocess/inventorys/showClosedInventory";
	}
	@RequestMapping(value="/{inventoryId}/test",method = RequestMethod.GET)
	public String testInventory(@PathVariable("inventoryId") Long inventoryId,HttpServletRequest request,Model uiModel){
		Inventory inventory = Inventory.findInventory(inventoryId);
		String message = null;
		if(inventory.checkIsCorrectInventory()){
			message = "Test succeded";
		}else{
			message = "Test Failed. Please retry the Inventory";
		}
		uiModel.addAttribute("message", message);
		uiModel.addAttribute("inventoryprocess", InventoryProcess.getInstance());
		return "inventoryprocess/inventorys/show";
	}
	@ModelAttribute(value="producttypes")
	public Collection<ProductType> populateProductType(){
		return InventoryProcess.getInstance().getProductTypes();
	}
	@ModelAttribute("specificInvItem")
	public Collection<InventoryItem> populateSpecificInvItem(){
		return InventoryProcess.getInstance().getSpecificInvItem();
	}
}
