package org.adorsys.geshotel.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;

import org.adorsys.geshotel.booking.domain.Invoice;
import org.adorsys.geshotel.booking.domain.Periode;
import org.adorsys.geshotel.booking.domain.Reservation;
import org.adorsys.geshotel.services.AdhotelPrintDocumentService;
import static org.adorsys.geshotel.services.DocumentsPath.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/documents/print")
public class PrintDocumentsController {
	
	public static final Logger LOGS= Logger.getLogger(PrintDocumentsController.class);
	
	@Autowired
	AdhotelPrintDocumentService printDocumentService;
	
	// Liste des reservations
	@Produces({"application/pdf", "application/xls"})
	@RequestMapping(value="/listeReservations", method=RequestMethod.GET)
	public void printListeReservations(@RequestParam("media")String type, Map parameters, HttpServletRequest request, HttpServletResponse response){
		
		
		try{
			if(type.equals("pdf")){
				printDocumentService.printDocumentToPdf(parameters, response, LISTE_RESERVATIONS_PATH);
				System.out.println("Impression pdf ok");
			}
			/*if(type.equals("xls")){
				printDocumentService.printDocumentToExcel(null, response, DocumentsPath.LISTE_RESERVATIONS_PATH);
			}*/
			if(type.equals("xls")){
				List<String> colonnes= new ArrayList<String>();
				colonnes.add("Nom");
				colonnes.add("Prenom");
				colonnes.add("Age");
				colonnes.add("Sexe");
				String sheetName= "Liste des reservations";
				printDocumentService.printExcelDocument(colonnes, LISTE_RESERVATIONS_EXCEL_PATH, sheetName, response);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ;
	}
	
	
	// Impression de la Main courante
	@Produces({"application/pdf", "application/xls"})
	@RequestMapping(value="/maincourante", method=RequestMethod.GET)
	public void printMainCourante(@RequestParam("media")String type, Map parameters, HttpServletRequest request, HttpServletResponse response){
		List<Reservation> reservations = Reservation.findAllReservations();
		for(Reservation reservation: reservations){
			Invoice invoice=null;
			try {
				invoice = reservation.getInvoice();
				
			} catch (Exception e) {
				System.out.println("Erreur de chargement de la facture \n"+e.getCause());
			}
			parameters.put("Tfacture", invoice.getInvoiceAmount());
			parameters.put("avance", invoice.getAmountPaid());
			parameters.put("reste", invoice.getReste());
		}
		
		try{
			if(type.equals("pdf")){
				printDocumentService.printDocumentToPdf(parameters, response, MAIN_COURANTE_PATH);
				System.out.println("Impression pdf ok");
			}
			if(type.equals("xls")){
				printDocumentService.printDocumentToExcel(null, response, MAIN_COURANTE_PATH);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ;
	}
	
	// Impression de la fiche de police
	@Produces({"application/pdf", "application/xls"})
	@RequestMapping(value="/fichePolice", method=RequestMethod.POST)
	public void printFichePolice(Periode periode,  Map parameters, HttpServletRequest request, HttpServletResponse response){
		parameters.put("DateDebut", periode.getMinDate());
		parameters.put("DateFin", periode.getMaxDate());
		
		try {
			printDocumentService.printDocumentToPdf(parameters, response, FICHE_POLICE_PATH);
		} catch (Exception e) {
		    LOGS.error("Erreur d'impression du pdf");
		}
		
		return;
	}

	
	// Impression de la facture
	@Produces({"application/pdf", "application/xls"})
	@RequestMapping(value="/facture/{id}", method=RequestMethod.GET)
	public void printFacture(@PathVariable("id")String id, Map parameters, HttpServletRequest request, HttpServletResponse response){
		parameters.put("idFac", id);
		try {
			printDocumentService.printDocumentToPdf(parameters, response, FACTURE_PATH);
		} catch (Exception e) {
		    LOGS.error("Erreur d'impression du pdf");
		}
		return;
	}
	

}
