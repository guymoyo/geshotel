package org.adorsys.geshotel.booking.planning;

import java.util.Date;

import org.adorsys.geshotel.booking.domain.RoomCategory;
import org.springframework.format.annotation.DateTimeFormat;

public class Planning {
	
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date SatrtDate;
	
	private RoomCategory roomcategory;

	public Planning() {
		super();
		this.SatrtDate = new Date();
		this.roomcategory = null;
	}
	
	

}
