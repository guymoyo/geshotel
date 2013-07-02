package org.adorsys.geshotel.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.adorsys.geshotel.domain.Hotel;
import org.adorsys.geshotel.domain.security.SecurityUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;


@Service
public class AdhotelPrintDocumentService {
	
	@Autowired
	DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	
	/**
	 * This method print 
	 * @param parameters(Parameters to use in the report)
	 * @param response(response return by the request to print)
	 * @param document(The .jrxml file to print)
	 */
	public void printDocumentToPdf(Map parameters, HttpServletResponse response, String document) throws Exception{
		Hotel hotel = Hotel.findHotel(new Long(1));
		if(document.isEmpty() || document == null || response == null){
            throw new RuntimeException("The document or the response must not be null ! ");
        }
		else{
            Connection connection = DataSourceUtils.doGetConnection(getDataSource());
            String userName= SecurityUtil.getUserName();
            parameters.put("PrintBy", userName);
            parameters.put("nomHotel", hotel.getName());
            parameters.put("email", hotel.getEmail());
            parameters.put("phoneHotel", hotel.getPhoneNumber());
            JasperReport jasperReport = JasperCompileManager.compileReport(document);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
            response.setContentLength(baos.size());
            ServletOutputStream out1 = response.getOutputStream();
            baos.writeTo(out1);
            out1.flush();
            connection.close();
            return;
        }
	}
	
	/**
	 * Print document to excel files
	 */
	public void printDocumentToExcel(Map parameters, HttpServletResponse response, String document) throws Exception{
		if(document.isEmpty() || document == null || response == null){
            throw new RuntimeException("The document or the response must not be null ! ");
        }
		else{
        	
            Connection connection = DataSourceUtils.doGetConnection(getDataSource());
            /*parameters.put("PrintBy", SecurityUtil.getUserName());
            parameters.put("user", SecurityUtil.getUserName());
            System.out.println("Liste des parametres: "+parameters.toString());*/
            JasperReport jasperReport = JasperCompileManager.compileReport(document);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            OutputStream outputFile= new FileOutputStream("excel.xls");
            
            // parametres d'exportation excel
            try {
            	JRXlsExporter exporter=  new JRXlsExporter();
            	exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,  jasperPrint);
                exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos);
                exporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.TRUE);
                exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
                exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
                exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
                exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
                exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
                exporter.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS, Boolean.TRUE);
                exporter.exportReport();
			} catch (Exception e) {
				System.out.println("Erreur d'exportation du document");
			}
            outputFile.write(baos.toByteArray());
            response.setContentLength(baos.size());
            response.setContentType("application/xls");
            ServletOutputStream out1 = response.getOutputStream();
            baos.writeTo(out1);
            out1.flush();
            connection.close();
            return;
        }
	}
	
	
	public void printExcelDocument(List<String> columnHeaders, String document, String sheetName, HttpServletResponse response){
		HSSFWorkbook book = new HSSFWorkbook();
		HSSFSheet sheet = book.createSheet(sheetName);
		HSSFRow rowHeader = sheet.createRow(0);
	    Object[] objects = columnHeaders.toArray();
		for (int i = 0; i < objects.length; i++) {
			 rowHeader.createCell(i).setCellValue(StringUtils.upperCase((String)objects[i]));
		}
		for(int i= 1; i<3; i++){
			HSSFRow row = sheet.createRow(i);
			for(int j=0; j<objects.length; j++){
			   row.createCell(j).setCellValue("test1");
		    }
		}
		ByteArrayOutputStream baos= new ByteArrayOutputStream();
		try {
			FileOutputStream fileOutputStream= new FileOutputStream(document);
			fileOutputStream.write(baos.toByteArray());
			book.write(fileOutputStream);
			fileOutputStream.close();
			ServletOutputStream servletOutputStream= response.getOutputStream();
			response.setContentLength(baos.size());
            response.setContentType("application/xls");
			baos.writeTo(servletOutputStream);
			servletOutputStream.flush();
		} catch (FileNotFoundException e) {
			System.out.println("Erreur de creation de flux");
			e.printStackTrace();
		}catch (IOException e) {
		    System.out.println("Erreur de d'ecriture dans le flux");
		    e.printStackTrace();
		}
		
		
	}

}
