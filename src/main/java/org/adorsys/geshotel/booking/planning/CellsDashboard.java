package org.adorsys.geshotel.booking.planning;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.adorsys.geshotel.booking.domain.RoomCategory;
import org.springframework.format.annotation.DateTimeFormat;

public class CellsDashboard {

private List<Date> cellsHeader = new ArrayList<Date>();
	
	private List<CellsLine> cellsLine = new ArrayList<CellsLine>();
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date dateStart;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date dateEnd;
	
	private RoomCategory category;

	public List<Date> getCellsHeader() {
		return cellsHeader;
	}

	public void setCellsHeader(List<Date> cellsHeader) {
		this.cellsHeader = cellsHeader;
	}

	public List<CellsLine> getCellsLine() {
		return cellsLine;
	}

	public void setCellsLine(List<CellsLine> cellsLine) {
		this.cellsLine = cellsLine;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public RoomCategory getCategory() {
		return category;
	}

	public void setCategory(RoomCategory category) {
		this.category = category;
	}

	public CellsDashboard(Date dateStart, Date dateEnd, RoomCategory category) {
		super();
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.category = category;
	}
	
	
}
