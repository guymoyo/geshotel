package org.adorsys.geshotel.test;

import java.io.File;
import java.io.IOException;

import org.adorsys.geshotel.resto.domain.BarRestauInvoice;
import org.adorsys.geshotel.resto.domain.BarRestauInvoiceDataOnDemand;
import org.adorsys.geshotel.resto.domain.BillOfSale;
import org.adorsys.geshotel.resto.domain.BillOfSaleDataOnDemand;
import org.adorsys.geshotel.resto.domain.Sale;
import org.adorsys.geshotel.resto.work.beans.SaleProcess;
import org.adorsys.geshotel.web.BillOfSaleController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.DocumentException;

/**
 * 
 * @author "w2b"
 *
 */
@RunWith(value=SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:META-INF/spring/applicationContext*.xml"})
@Transactional
public class TestPDF {
	private BillOfSale billOfSale;
	@Autowired
	private BillOfSaleDataOnDemand billOfSaleDataOnDemand;
	@Autowired
	private BarRestauInvoiceDataOnDemand barRestauInvoiceDataOnDemand;
	private BarRestauInvoice barRestauInvoice;
	@Before
	public void before(){
		billOfSale = billOfSaleDataOnDemand.getRandomBillOfSale();
		barRestauInvoice = barRestauInvoiceDataOnDemand.getSpecificBarRestauInvoice(3);
		barRestauInvoice.setInvoicePath("/tools/pdf/invoices/"+barRestauInvoice.getInvoiceNumber()+".pdf");
	}
	@Test
	public void testGeneratePDF(){
		try {
			File file = new File("/home/adorsys/dev/entreprise/adhotel/go.pdf");
			BillOfSaleController.createPDF(billOfSale,file.getAbsolutePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testBarRestauPdfInvoiceGeneration(){
		SaleProcess.getInstance().createInvoiceFile(barRestauInvoice);
	}
}
