package org.adorsys.geshotel.web;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.adorsys.geshotel.domain.Customer;
import org.adorsys.geshotel.domain.security.SecurityUtil;
import org.adorsys.geshotel.resto.domain.BillOfSale;
import org.adorsys.geshotel.resto.domain.BrTable;
import org.adorsys.geshotel.resto.domain.Product;
import org.adorsys.geshotel.resto.domain.SaleItem;
import org.adorsys.geshotel.resto.work.beans.WorkEntitySession;
import org.adorsys.geshotel.resto.work.session.SaleSession;
import org.apache.commons.compress.utils.IOUtils;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.Resource;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

@RooWebScaffold(path = "billofsales", formBackingObject = BillOfSale.class)
@RequestMapping("/billofsales")
@Controller
public class BillOfSaleController {
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel,HttpServletRequest request) {
    	List<WorkEntitySession<SaleItem>> activeSaleSessions = SaleSession.getActiveSaleSessions();
    	if(activeSaleSessions == null){
    		SaleSession.addSaleSession(new WorkEntitySession<SaleItem>(request.getSession().getId(), new Date(),null, SecurityUtil.getUserName()));
    	}
        uiModel.addAttribute("billOfSale", new BillOfSale());
        addSaleItemDateTimeFormatPatterns(uiModel);
        return "redirect:/billofsales/saleitems?form";
    }
    
    /**
     * =====================
     */
    @RequestMapping(method = RequestMethod.POST,value="/saleitems")
    public String createSaleItem(@Valid SaleItem saleItem, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("saleItem", saleItem);
            addSaleItemDateTimeFormatPatterns(uiModel);
            return "saleitems/create";
        }
        uiModel.asMap().clear();
        saleItem.persist();
        SaleSession.addSaleItem(saleItem);
        return "redirect:/billofsales/saleitems/" + encodeUrlPathSegment(saleItem.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", method = RequestMethod.GET,value="/saleitems")
    public String createSaleItemForm(Model uiModel) {
        uiModel.addAttribute("saleItem", new SaleItem());
        addSaleItemDateTimeFormatPatterns(uiModel);
        List dependencies = new ArrayList();
        if (Customer.countCustomers() == 0) {
            dependencies.add(new String[]{"customer", "customers"});
        }
        if (Product.countProducts() == 0) {
            dependencies.add(new String[]{"product", "products"});
        }
        if (BrTable.countBrTables() == 0) {
            dependencies.add(new String[]{"brtable", "brtables"});
        }
        uiModel.addAttribute("dependencies", dependencies);
        return "billofsales/saleitems/create";
    }
    
    @RequestMapping(value = "/saleitems/{id}", method = RequestMethod.GET)
    public String showSaleItem(@PathVariable("id") Long id, Model uiModel) {
        addSaleItemDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("saleitem", SaleItem.findSaleItem(id));
        uiModel.addAttribute("itemId", id);
        return "billofsales/saleitems/show";
    }
    
    @RequestMapping(method = RequestMethod.GET,value="/saleitems")
    public String listSaleItem(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("saleitems", SaleItem.findSaleItemEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) SaleItem.countSaleItems() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("saleitems", SaleItem.findAllSaleItems());
        }
        addSaleItemDateTimeFormatPatterns(uiModel);
        return "billofsales/saleitems/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT,value="/saleitems")
    public String updateSaleItem(@Valid SaleItem saleItem, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("saleItem", saleItem);
            addSaleItemDateTimeFormatPatterns(uiModel);
            return "billofsales/saleitems/update";
        }
        uiModel.asMap().clear();
        saleItem.merge();
        SaleSession.updateSaleItem(saleItem);
        return "redirect:/billofsales/saleitems/" + encodeUrlPathSegment(saleItem.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/saleitems/{id}", params = "form", method = RequestMethod.GET)
    public String updateSaleItemForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("saleItem", SaleItem.findSaleItem(id));
        addSaleItemDateTimeFormatPatterns(uiModel);
        return "billofsales/saleitems/update";
    }
    
    @RequestMapping(value = "/saleitems/{id}", method = RequestMethod.DELETE)
    public String deleteSaleItem(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        SaleItem findSaleItem = SaleItem.findSaleItem(id);
        SaleSession.removeSaleItem(findSaleItem);
        findSaleItem.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/billofsales/saleitems";
    }
    
    @ModelAttribute("customers")
    public Collection<Customer> populateCustomers() {
        return Customer.findAllCustomers();
    }
    
    @ModelAttribute("brtables")
    public Collection<BrTable> populateBrTables() {
        return BrTable.findAllBrTables();
    }
    
    @ModelAttribute("products")
    public Collection<Product> populateProducts() {
        return Product.findAllProducts();
    }
    
    
    void addSaleItemDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("saleItem_dateofsale_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }
    /**
     * ===================
     */
    @ModelAttribute("saleitems")
    public Collection<SaleItem> populateSaleItems() {
//        return SaleItem.findAllSaleItems();
//    	SaleSession.mergeActiveSessions();
    	WorkEntitySession<SaleItem> mergeActiveSessions = SaleSession.mergeActiveSessions();
    	if(mergeActiveSessions == null) return new ArrayList<SaleItem>();
    	return mergeActiveSessions.getEntities();
    }
    @RequestMapping(params="create=true",method=RequestMethod.GET)
    public String confirmBillOfSale(Model uiModel,HttpServletRequest request){
    	BillOfSale billOfSale = SaleSession.getBillOfSale();
    	billOfSale.persist();
    	uiModel.addAttribute("billOfSale", billOfSale);
    	addDateTimeFormatPatterns(uiModel);
    	return "billofsales/update";
    }
    @RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid BillOfSale billOfSale, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest){
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("billOfSale", billOfSale);
            addDateTimeFormatPatterns(uiModel);
            return "billofsales/update";
        }
        uiModel.asMap().clear();
        billOfSale.merge();
        return "redirect:/billofsales/" + encodeUrlPathSegment(billOfSale.getId().toString(), httpServletRequest);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel) throws IOException, DocumentException {
        addDateTimeFormatPatterns(uiModel);
        BillOfSale billOfSale = BillOfSale.findBillOfSale(id);
//        String fileName = RandomStringUtils.randomAlphanumeric(2)+".pdf";
//        String fileName = "/home/adorsys/dev/entreprise/adhotel/BillOfSale-"+RandomStringUtils.randomAlphanumeric(6)+"-.pdf";
//        Document createPDF = createPDF(billOfSale, fileName);
        uiModel.addAttribute("billofsale", billOfSale);
        uiModel.addAttribute("itemId", id);
        return "billofsales/show";
    }
    public static Document createPDF(BillOfSale billOfSale,String file) throws IOException, DocumentException{
    	Document document = new Document();
    	PdfWriter instance = PdfWriter.getInstance(document, new FileOutputStream(file));
    	instance.setViewerPreferences(PdfWriter.PageLayoutSinglePage);
    	document.open();
    	document.addTitle("Bill Of Sale Number "+billOfSale.getSellKey()+". Generated The : "+new Date());
    	document.addCreator("adorsys:adhotel");
    	document.addAuthor("adorsys:hotel");
    	document.addCreationDate();
    	document.add(new Phrase("                      The real text go here                  "));
    	document.add(new Paragraph(billOfSale.getRestaurantName()));
    	document.add(new Paragraph(billOfSale.getNote()));
    	document.add(new Paragraph(billOfSale.toString()));
    	document.setPageSize(new Rectangle(2,2));
    	document.close();
    	return document;
    }
    @RequestMapping(value="/{sellKey}/print",method=RequestMethod.GET)
    public String generateBillOfSalePDF(@PathVariable("sellKey") String sellKey,HttpServletRequest request,HttpServletResponse response,Model uiModel) throws IOException, DocumentException{
    	BillOfSale singleResult = BillOfSale.findBillOfSalesBySellKeyEquals(sellKey).getSingleResult();
    	WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
    	Resource resource = webApplicationContext.getResource("/home/adorsys/dev/entreprise/adhotel/BillOfSale-"+singleResult.getSellKey()+".pdf");
//    	String fileName = "/home/adorsys/dev/entreprise/adhotel/BillOfSale-"+singleResult.getSellKey()+".pdf";
    	String fileName = resource.getFile().getAbsolutePath();
    	createPDF(singleResult, fileName);
    	ServletOutputStream outputStream = response.getOutputStream();
    	response.setContentType("application/pdf");
    	response.addHeader("Content-Disposition", "attachment; filename="
    	          + fileName);
    	IOUtils.copy(resource.getInputStream(), outputStream);
    	outputStream.flush();
    	outputStream.close();
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("billofsale", singleResult);
        uiModel.addAttribute("itemId", singleResult.getId());
        return "billofsales/show";
    	
    }
}