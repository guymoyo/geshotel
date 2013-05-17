package org.adorsys.geshotel.booking.planning;

import java.util.Date;

import org.adorsys.geshotel.booking.domain.Reservation;
import org.adorsys.geshotel.booking.domain.Room;

public class Cell {
	
	private Date day;
	
	private Date date1; 
	
	private Date date2;
	
	private Date date3;
	
	private Date date4;
	
	private int line;
	
	private int colon;
	
	private int colospan=1;
	
	private Reservation reservation;
	
	private Room room;

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public Date getDate1() {
		return date1;
	}

	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	public Date getDate2() {
		return date2;
	}

	public void setDate2(Date date2) {
		this.date2 = date2;
	}

	public Date getDate3() {
		return date3;
	}

	public void setDate3(Date date3) {
		this.date3 = date3;
	}

	public Date getDate4() {
		return date4;
	}

	public void setDate4(Date date4) {
		this.date4 = date4;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getColon() {
		return colon;
	}

	public void setColon(int colon) {
		this.colon = colon;
	}

	public int getColospan() {
		return colospan;
	}

	public void setColospan(int colospan) {
		this.colospan = colospan;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
}
