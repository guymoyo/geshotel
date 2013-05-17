package org.adorsys.geshotel.resto.work.beans;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.adorsys.geshotel.booking.domain.Invoice;
import org.adorsys.geshotel.booking.domain.InvoiceItem;
import org.adorsys.geshotel.booking.domain.InvoiceState;
import org.adorsys.geshotel.booking.domain.Reservation;
import org.adorsys.geshotel.domain.Customer;
import org.adorsys.geshotel.domain.Employee;
import org.adorsys.geshotel.domain.Hotel;
import org.adorsys.geshotel.domain.ext.DateConfig;
import org.adorsys.geshotel.domain.ext.DateConfigPeriod;
import org.adorsys.geshotel.domain.ext.IdGenerator;
import org.adorsys.geshotel.domain.security.SecurityUtil;
import org.adorsys.geshotel.resto.domain.BarRestauInvoice;
import org.adorsys.geshotel.resto.domain.BarRestauState;
import org.adorsys.geshotel.resto.domain.CustomerType;
import org.adorsys.geshotel.resto.domain.Inventory;
import org.adorsys.geshotel.resto.domain.InventoryItem;
import org.adorsys.geshotel.resto.domain.KitchenOrder;
import org.adorsys.geshotel.resto.domain.KitchenOrderItem;
import org.adorsys.geshotel.resto.domain.Product;
import org.adorsys.geshotel.resto.domain.Sale;
import org.adorsys.geshotel.resto.domain.SaleItem;
import org.adorsys.geshotel.resto.work.exception.MoreProductSaledThanInStockException;
import org.adorsys.geshotel.resto.work.exception.UnableToAddInventoryItem;
import org.joda.time.DateTime;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@RooJavaBean
public class SaleProcess {
	public static int INVOICE_NUMBER = 0 ;
	private Logger logger = Logger.getLogger("sale_process");
	private List<Sale> printedSales = new ArrayList<Sale>();
	private static SaleProcess saleProcess;
	private boolean inventoryIsOpened ;
	private static Object synchroneObject__ = new Object();
	
	private SaleProcess() {	}

	public static SaleProcess getInstance() {
		if (saleProcess == null) {
			synchronized (synchroneObject__) {
				if (saleProcess == null) {
					saleProcess = new SaleProcess();
				}
			}
		}
		return saleProcess;
	}
	public void addSale(Sale  sale)	{
		sale.setSaleKey(IdGenerator.generateId());
		sale.setTotalAmount(BigDecimal.ZERO);
		sale.setRecordDate(new Date());
		sale.setUserNameDescription("Created By : "	
				+ SecurityUtil.getUserName() + ", At : " + new Date());
		List<Hotel> allHotels = Hotel.findAllHotels();
		Hotel hotel = null;
		if(allHotels == null || allHotels.isEmpty()) {
			sale.setTax(BigDecimal.ZERO);
		}else {
			hotel = allHotels.iterator().next();
			
		}
		if(hotel == null || hotel.getTVA() == null){
			sale.setTax(BigDecimal.ZERO);
		}else {
			sale.setTax(hotel.getTVA());
		}
		sale.setSaleState(BarRestauState.OPENED);	
		sale.persist();
//		sales.put(sale, new ArrayList<SaleItem>());
		openInventory(inventoryIsOpened);
	}
	public Inventory openInventory(boolean inventoryIsOpened){
		if(this.inventoryIsOpened) return null;
		Inventory inventory = new Inventory();
		inventory.setInventoryDate(new Date());
		inventory.setIsClosed(Boolean.FALSE);
		inventory.setPeriodConf(SecurityUtil.getBaseUser().getPeriodConf());
		inventory.setTitle("Inventaire du "+new Date()+" \t Par "+SecurityUtil.getUserName());
		inventory.setUserAccount(SecurityUtil.getBaseUser());
		inventory.setInventoryState(BarRestauState.OPENED);
		inventory.persist();
		inventory.flush();
//		this.inventory = inventory;
		this.inventoryIsOpened = true;
		initInventoryItemInitialStock(inventory);
		return inventory;
	}
	public Inventory initInventoryItemInitialStock(Inventory inventory){
		List<Product> findAllProducts = Product.findAllProducts();
		for (Product product : findAllProducts) {
			InventoryItem inventoryItem = new InventoryItem();
			inventoryItem.setDesignation(product.getTitle());
			inventoryItem.setEntry(null);//will be added after every procurement
			inventoryItem.setSales(null);//will be incremented after every sales
			inventoryItem.setFinalStock(null);//will be added at the user's period end.
			inventoryItem.setInitialStock(product.getQty());
			inventoryItem.setInventory(inventory);
			inventoryItem.setProduct(product);
			inventoryItem.persist();
			inventoryItem.flush();
		}
		return inventory;
	}
	public Inventory closeInventory(Inventory inventory){
		List<InventoryItem> inventoryItems = InventoryItem.findInventoryItemsByInventory(inventory).getResultList();
		for (InventoryItem inventoryItem : inventoryItems) {
			inventoryItem.setFinalStock(inventoryItem.getInitialStock().subtract(inventoryItem.getSales()).add(inventoryItem.getEntry()));
			inventoryItem.merge().flush();
		}
		inventory.setInventoryState(BarRestauState.CLOSED);
		inventory.setIsClosed(true);
		inventory.merge().flush();
		this.inventoryIsOpened = false;
		return inventory;
	}
	@Transactional
	public void addCommand(SaleItemCommand saleItemCommand) throws MoreProductSaledThanInStockException, UnableToAddInventoryItem {
		SaleItem newSaleItem = saleItemCommand.newSaleItem();
		Product product = Product.findProductsByProductKeyEquals(
				newSaleItem.getProduct().getProductKey()).getSingleResult();
		BigDecimal qty = product.getQty();
		if (qty.compareTo(newSaleItem.getQty()) >= 0) {
			qty = qty.subtract(newSaleItem.getQty());
			product.setQty(qty);
			product.merge();
			product.flush();
		}else{
			throw new MoreProductSaledThanInStockException(qty, newSaleItem.getQty(), qty);
		}
//		newSaleItem.flush();
		newSaleItem.persist();
		Sale sale = newSaleItem.getSale();
		List<SaleItem> saleItems = SaleItem.findSaleItemsBySale(sale).getResultList();
		/*Map<Sale, List<SaleItem>> sales = SaleProcess.getInstance().getSales();
		List<SaleItem> saleItems = sales.get(sale);*/
		Assert.notNull(saleItems, "Null Sale items aren't required");
		saleItems.add(newSaleItem);
		sale.getSaleItem().add(newSaleItem);
		sale.merge();
		sale.flush();
		System.out.println("Sale Merged : " + sale.getSaleItem());
		actualiseSalesInSaleProcess(sale, false);
		updateInventoryItemAfterSaleItemAdded(newSaleItem, getInventoryOrInit());
	}

	private Inventory getInventoryOrInit() {
		Inventory inventory = Inventory.findCurrentInventory();
		if(inventory == null){
			this.openInventory(true);
			inventory = Inventory.findCurrentInventory();
		}
		return inventory;
	}
	public void addCommandAndGenerateKitchenOrder(SaleItemCommand saleItemCommand) throws MoreProductSaledThanInStockException, UnableToAddInventoryItem{
		addCommand(saleItemCommand);
		//si c'est un produit du bar, On sort de la methode : on n'ajoute pas au bon de la cuisine
		if(!saleItemCommand.getProduct().isRestauProduct()) return ;
		if(isKitchenOrderOpened(saleItemCommand.getSale())==false){
			System.out.println("\n \n creating Kitchen Order...");
			createKitchenOrder(saleItemCommand);
		}
		KitchenOrder kitchenOrder = KitchenOrder.findKitchenOrdersBySaleAndKitchenOrderItemState(saleItemCommand.getSale(), BarRestauState.OPENED).getResultList().iterator().next();
		KitchenOrderItem newKitchenItemOrder = saleItemCommand.newKitchenItemOrder(kitchenOrder);
		newKitchenItemOrder.persist();
	}

	public void createKitchenOrder(SaleItemCommand saleItemCommand) {
		KitchenOrder kitchenOrder = new KitchenOrder();
		kitchenOrder.setDateOfOrdering(new Date());
		kitchenOrder.setKitchenOrderItemState(BarRestauState.OPENED);
		kitchenOrder.setKitchenOrderKey(IdGenerator.generateId());
		kitchenOrder.setSale(saleItemCommand.getSale());
		kitchenOrder.setSellerAccount(SecurityUtil.getBaseUser());
		kitchenOrder.persist();
		System.out.println("\n \n Kitchen Order Created : ");
	}
	public boolean isKitchenOrderOpened(Sale sale){
		List<KitchenOrder> kitchenOrders = KitchenOrder.findKitchenOrdersBySaleAndKitchenOrderItemState(sale, BarRestauState.OPENED).getResultList();
		if(kitchenOrders == null || kitchenOrders.isEmpty()) return false;
		return true;
	}
	public void findCurrentInventory(){
		
	}
	public BigDecimal getAmountSale(Sale sale) {
		List<SaleItem> list = SaleItem.findSaleItemsBySale(sale).getResultList();
		BigDecimal totalSaleAmount = BigDecimal.ZERO;
		for (SaleItem saleItem : list) {
			totalSaleAmount = totalSaleAmount.add(saleItem.getAmount());
		}
		return totalSaleAmount;
	}

	@Transactional
	public void closeSale(String saleKey,boolean close) {
		Assert.notNull(saleKey, "The sale key may not be null");
		Sale sale = Sale.findSalesBySaleKeyEquals(saleKey).getResultList()
				.iterator().next();
		sale.setCustomerType(CustomerType.SIMPLE_CUSTOMER);
		sale.setSaleState(BarRestauState.CLOSED);
		if(sale.getNote() != null){
			sale.setNote(sale.getNote()+"  ; Fermee a "+new DateTime().getHourOfDay());
		}
		actualiseSalesInSaleProcess(sale,close);
	}
	private void actualiseSalesInSaleProcess(Sale sale,boolean close){
		sale.setTotalAmount(SaleProcess.getInstance().getSaleTotal(sale));
		sale.merge();
		sale.flush();
	}
	@Transactional
	public void closeSale(String saleKey,Customer customer,boolean close){
		Invoice invoice = Invoice.findInvoicesByCustomer(customer).getResultList().iterator().next();
		Sale sale = Sale.findSalesBySaleKeyEquals(saleKey).getSingleResult();
		List<SaleItem> saleItems = SaleItem.findSaleItemsBySale(sale).getResultList();
		for (SaleItem saleItem : saleItems) {
			InvoiceItem invoiceItem = saleItem.newInvoiceItem(invoice);
			invoiceItem.persist();
			invoiceItem.flush();
		}
		sale.setCustomerType(CustomerType.HOSTED_CUSTOMER);
		sale.setSaleState(BarRestauState.CLOSED);
		sale.setCustomer(customer);
		sale.setInvoice(invoice);
		sale.merge();
		if(sale.getNote() != null){
			sale.setNote(sale.getNote()+"  ; Fermee a "+new DateTime().getHourOfDay());
		}
		actualiseSalesInSaleProcess(sale,close);
	}
	@Transactional
	public void closeSale(String saleKey,Employee employee,boolean close){
		Sale sale = Sale.findSalesBySaleKeyEquals(saleKey).getSingleResult();
		List<SaleItem> saleItems = SaleItem.findSaleItemsBySale(sale).getResultList();
		for (SaleItem saleItem : saleItems) {
			BigDecimal employeePrice = saleItem.getProduct().getEmployeePrice();
			saleItem.setUnitPrice(employeePrice);
			saleItem.setAmount(saleItem.getQty().multiply(saleItem.getUnitPrice()));
			saleItem.merge();
			saleItem.flush();
		}
		sale.setCustomerType(CustomerType.EMPLOYEE);
		sale.setSaleState(BarRestauState.CLOSED);
		if(sale.getNote() != null){
			sale.setNote(sale.getNote()+"  ; Fermee a "+new DateTime().getHourOfDay());
		}
		actualiseSalesInSaleProcess(sale,close);
	}
//	@Transactional
	public void showSaleDetailsForEmployee(String saleKey){
		Sale sale = Sale.findSalesBySaleKeyEquals(saleKey).getSingleResult();
		List<SaleItem> saleItems = SaleItem.findSaleItemsBySale(sale).getResultList();
		for (SaleItem saleItem : saleItems) {
			BigDecimal employeePrice = saleItem.getProduct().getEmployeePrice();
			saleItem.setUnitPrice(employeePrice);
			saleItem.setAmount(saleItem.getQty().multiply(saleItem.getUnitPrice()));
		}
		sale.setCustomerType(CustomerType.EMPLOYEE);
		sale.setTotalAmount(SaleProcess.getInstance().getSaleTotal(sale));
		sale.merge().flush();
	}
	@Transactional
	public BigDecimal getSaleTotal(Sale sale) {
		List<SaleItem> saleItems = SaleItem.findSaleItemsBySale(sale).getResultList();
		BigDecimal totalAmount = BigDecimal.ZERO;
		for (SaleItem saleItem : saleItems) {
			totalAmount = totalAmount.add(saleItem.getAmount());
		}
		BigDecimal taxValue = BigDecimal.ZERO;
		taxValue = totalAmount.multiply(sale.getTax()).divide(
				new BigDecimal(100));
		sale.setTotalAmount(totalAmount.add(taxValue));
		sale.merge();
		sale.flush();
		System.out.println("Tax Value " + taxValue);
		return taxValue;
	}

	@Transactional
	public void mergeSales(Sale baseSale, Sale secondSale) {
		Assert.notNull(baseSale, "The base sale may not be null");
		Assert.notNull(secondSale, "the second sale may not be null");
		List<SaleItem> saleItems = SaleItem.findSaleItemsBySale(secondSale)
				.getResultList();
		for (SaleItem saleItem : saleItems) {
			saleItem.setSale(baseSale);
			saleItem.merge();
			saleItem.flush();
		}
		baseSale.getTotalAmount().add(secondSale.getTotalAmount());
		baseSale.merge();
		baseSale.flush();
	}
	@Transactional
	public void mergeSales(String baseSaleKey, String secondSaleKey) {
		Sale baseSale = Sale.findSalesBySaleKeyEquals(baseSaleKey).getResultList().iterator().next();
		Sale secondSale = Sale.findSalesBySaleKeyEquals(secondSaleKey).getResultList().iterator().next();
		mergeSales(baseSale, secondSale);
	}

	@Transactional
	public void mergeSales(Sale baseSale, List<Sale> sales) {
		Assert.notNull(baseSale, "The base sale may not be null");
		Assert.notNull(sales, "the second sale may not be null");
		for (Sale sale : sales) {
			mergeSales(baseSale, sale);
		}
		System.out.println("Merge Done .");
	}

	public BarRestauInvoice generateInvoice(String saleKey) {
		Sale sale = Sale.findSalesBySaleKeyEquals(saleKey).getResultList().iterator().next();
		BarRestauInvoice invoice = new BarRestauInvoice();
		invoice.setBarRestauInvoiceKey(IdGenerator.generateId());
		invoice.setCustomerType(sale.getCustomerType());
		invoice.setInvoiceState(InvoiceState.Ouvert);
		invoice.setInvoiceNumber(""+INVOICE_NUMBER);
		INVOICE_NUMBER ++;
		invoice.setInvoicePath("/tools/pdf/invoices/"+invoice.getInvoiceNumber()+".pdf");
		invoice.setNote("N/A");
		invoice.setCreationDate(new Date());
		invoice.setSale(sale);
		createInvoiceFile(invoice);
		invoice.persist();
		invoice.flush();
		return invoice;
	}
	public void createInvoiceFile(BarRestauInvoice barRestauInvoice){
		Rectangle pageSize = new Rectangle(900f,300f);
		pageSize.setBackgroundColor(BaseColor.GRAY);
		pageSize.setBorder(5);
		Document document = new Document(pageSize);
		try {
			PdfWriter.getInstance(document, new FileOutputStream(barRestauInvoice.getInvoicePath()));
			document.open();
			generateHeader(document, barRestauInvoice);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		document.close();
	}
	public void generateHeader(Document document,BarRestauInvoice barRestauInvoice) throws DocumentException{
		Font font = new Font();
		font.setStyle(Font.BOLD);
		Chunk name = new Chunk("HOTEL LUMIERE\n");
		name.setFont(font);
		Phrase namePhrase = new Phrase(name);
		Chunk address = new Chunk("B.P 1979");
		address.setFont(font);
		Chunk contact = new Chunk("    Tel : 33 42 94 16-33 42 93 80 \n");
		Font contactFont = new Font();
		contactFont.setStyle(Font.NORMAL);
		contact.setFont(contactFont);
		Phrase phrase = new Phrase();
		phrase.add(address);
		phrase.add(contact);
		Chunk location = new Chunk("Douala - Cameroun \n");
		location.setFont(font);
		Phrase locationPhrase = new Phrase(location);
		Chunk activity = new Chunk("Bar-Restaurant    \n");
		activity.setFont(font);
		Phrase activityPhrase = new Phrase(activity);
		DateTime dateTime = new DateTime(new Date().getTime());
		String dateRepresentation = dateTime.toString("dd-MM-yyyy HH:mm:ss"	);
		Chunk dateChunk = new Chunk(dateRepresentation);
		Font dateFont = new Font();
		dateFont.setStyle(Font.BOLDITALIC);
		dateChunk.setFont(dateFont);
		Phrase datePhrase = new Phrase(dateChunk);
		Paragraph paragraph = new Paragraph();
		paragraph.add(namePhrase);
		paragraph.add(phrase);
		paragraph.add(locationPhrase);
		paragraph.add(activityPhrase);
		paragraph.add(datePhrase);
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);
		//done with the header
		PdfPTable pdfPTable = new PdfPTable(4);
		Set<SaleItem> saleItem = barRestauInvoice.getSale().getSaleItem();
		pdfPTable.addCell(new PdfPCell(new Phrase("QTE", font)));
		pdfPTable.addCell(new PdfPCell(new Phrase("DESIGNATION", font)));
		pdfPTable.addCell(new PdfPCell(new Phrase("PU", font)));
		pdfPTable.addCell(new PdfPCell(new Phrase("MONTANT", font)));
		for (SaleItem item : saleItem) {
			System.out.println("line added"+item.toString());
			pdfPTable.addCell(new PdfPCell(new Phrase(item.getQty().toBigInteger().toString())));
			pdfPTable.addCell(new PdfPCell(new Phrase(item.getProduct().getTitle())));
			pdfPTable.addCell(new PdfPCell(new Phrase(item.getUnitPrice().toPlainString())));
			pdfPTable.addCell(new PdfPCell(new Phrase(item.getAmount().toPlainString())));
		}

		PdfPCell totalSaleCellLabel = new PdfPCell(new Phrase("Total ", font));
		totalSaleCellLabel.setColspan(3);
		pdfPTable.addCell(totalSaleCellLabel);
		pdfPTable.addCell(new PdfPCell(new Phrase(barRestauInvoice.getSale().getTotalAmount().toPlainString(), font)));
		Paragraph paragraph2 = new Paragraph();
		paragraph2.add(pdfPTable);
		paragraph2.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph2);
		Paragraph paragraph3 = new Paragraph();
		Phrase invoiceNumberPhrase = new Phrase("Invoice Number : "+barRestauInvoice.getInvoiceNumber()+"\n");
		Phrase thanksPhrase = new Phrase("Merci a bientot/You're welcome\n");
		Phrase sellerNamePhrase = new Phrase("Nom du vendeur : "+SecurityUtil.getUserName()+"\n");
		paragraph3.add(invoiceNumberPhrase);
		paragraph3.add(thanksPhrase);
		paragraph3.add(sellerNamePhrase);
		paragraph3.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph3);
	}
	@Transactional
	public void updateInventoryItemAfterSaleItemAdded(SaleItem saleItem,Inventory inventory) throws UnableToAddInventoryItem{
		if(saleItem == null || inventory == null) throw new UnableToAddInventoryItem("Null Valued Passed; " +
				"Either the sale item doesn't exist nor the Inventory is Absent",inventory);

		List<InventoryItem> items = InventoryItem.findInventoryItemsByInventoryAndDesignationEquals(inventory, saleItem.getProduct().getTitle()).getResultList();
		if(items == null || items.isEmpty()) {

			//create new Inventory Item and store It.
			InventoryItem inventoryItem = new InventoryItem();
			inventoryItem.setDesignation(saleItem.getProduct().getTitle());
			inventoryItem.setEntry(BigDecimal.ZERO);
			inventoryItem.setSales(saleItem.getQty());
			inventoryItem.setFinalStock(null);
			inventoryItem.setInitialStock(null);
			inventoryItem.setInventory(inventory);
			inventoryItem.setProduct(saleItem.getProduct());
			inventoryItem.persist();
			inventoryItem.flush();
			System.out.println("Done");;
			return ;
		}else {
			InventoryItem item = items.iterator().next();
			System.out.println("\n \n Item [sale process] : \t "+item);
			BigDecimal augend = item.getSales();
			if(augend == null) {
				augend = saleItem.getQty();
			}else{
				System.out.println("\n \n incrementing sales : \t"+augend);
				augend = saleItem.getQty().add(augend);
			} 
			item.setSales(augend);
			item.merge().flush();
			
		}
	}
	public String findSaleToJson(String saleKey){
		Sale sale = Sale.findSalesBySaleKeyEquals(saleKey).getSingleResult();
		//TODO : convert to json
		List<SaleItem> saleItems = SaleItem.findSaleItemsBySale(sale).getResultList();
		DetailedSaleBean detailedSaleBean = new DetailedSaleBean(sale, saleItems);
		List<KitchenOrder> resultList = KitchenOrder.findKitchenOrdersBySale(sale).getResultList();
		if(checkCollection(resultList)){
			KitchenOrder next = resultList.iterator().next();
			detailedSaleBean.setKitchenOrder(next);
			detailedSaleBean.setKitchenOrderItems(KitchenOrderItem.findKitchenOrderItemsByKitchenOrder(next).getResultList());
		}
		return detailedSaleBean.getSaleItemsToJSon();
	}
	public List<KitchenOrder> findKitchenOrder(String saleKey){
		Assert.notNull(saleKey, "The sale Key might not be null");
		Sale sale = Sale.findSalesBySaleKeyEquals(saleKey).getSingleResult();
		List<KitchenOrder> kitchenOrders = KitchenOrder.findKitchenOrdersBySaleAndKitchenOrderItemState(sale, BarRestauState.OPENED).getResultList();
		if(!checkCollection(kitchenOrders)) throw new NullPointerException("No Kitchen Order FOund");
		return kitchenOrders;
	}
	public List<KitchenOrderItem> findKitchenOrderItems(String saleKey)throws NullPointerException{
		List<KitchenOrder> kitchenOrders = findKitchenOrder(saleKey);
		KitchenOrder next = kitchenOrders.iterator().next();
		return KitchenOrderItem.findKitchenOrderItemsByKitchenOrder(next).getResultList();
	}
	@Transactional
	public void removeSaleItem(String saleItemKey)throws Exception{
		SaleItem saleItem = SaleItem.findSaleItemsBySaleItemKeyEquals(saleItemKey).getSingleResult();
		Sale sale = saleItem.getSale();
		//calcule le montant de la ligne de commande et on multiplie par la TVA
		BigDecimal taxedValue = saleItem.getAmount().multiply(sale.getTax().divide(new BigDecimal(100)));
		sale.setTotalAmount(sale.getTotalAmount().subtract(taxedValue));
		sale.getSaleItem().remove(saleItem);
		sale.merge().flush();
		saleItem.remove();
	}
	public boolean checkCollection(List<? extends Object> collection){
		return (collection != null && !collection.isEmpty());
	}
	public List<Sale> getOpenedSales(){
		return Sale.findSalesBySaleState(BarRestauState.OPENED).getResultList();
	}
	public List<Sale> getClosedSales(){
		DateConfigPeriod dateConfigPeriod = DateConfig.getBegingEndOfDay(new Date());
		return Sale.findSalesBySaleStateAndRecordDateBetween(BarRestauState.CLOSED, dateConfigPeriod.getBegin(),dateConfigPeriod.getEnd()).getResultList();
	}

    /**
     * Trie et organise les services de l'hotel avec les montants de chaque service, Pour un client.
     * @param customer : le client pour qui on veut etablir le bilan de consomation. Si plusieurs Clients,
     * alors boucler cette methode sur tous les clients.
     * @param invoiceState : L'etat de la facture. recherche toutes les facture qui ne sont pas invoiceState.
     * @return
     */
    public static CustomReservationImg findServicesReports(Customer customer,InvoiceState invoiceState,CustomReservationImg allGroupAndSaleReport){
    	List<Sale> customerSalesByInvoiceStates = Sale.findCustomerSalesByInvoiceStates(customer, invoiceState);
    	if(customer == null )throw new NullPointerException("the customer might not be null");
    	for (Sale sale : customerSalesByInvoiceStates) {
			List<GroupAndSaleReport> servicesAndSaleItems = sale.findServicesAndSaleItems(allGroupAndSaleReport);
			BigDecimal totalAmount = BigDecimal.ZERO;
			for (GroupAndSaleReport groupAndSaleReport : servicesAndSaleItems) {
				totalAmount.add(groupAndSaleReport.getServiceAmount());
			}
			allGroupAndSaleReport.getGroupAndSaleReportAmount().add(totalAmount);
		}
    	return allGroupAndSaleReport;
    }
}
