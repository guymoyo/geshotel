package org.adorsys.geshotel.booking.planning;

import java.util.Date;
import java.util.List;

import org.adorsys.geshotel.booking.domain.Reservation;
import org.adorsys.geshotel.booking.domain.Room;
import org.adorsys.geshotel.booking.domain.RoomCategory;
import org.adorsys.geshotel.utility.DateUtility;
import org.springframework.ui.Model;

public class PlanningService {
	
	
	public static void getPlanning(Date from,RoomCategory category,Model uiModel){
		
		List<Room> rooms ;
	
		if(category == null){
			rooms = Room.findAllRooms();
		}
		else{
			rooms=Room.findRoomsByRoomCategory(category).getResultList();
		}
	
		CellsDashboard dashboard = new CellsDashboard(from, DateUtility.addDayToDate(from, 14), category);
		for (int i=0;i<14;i++){
			dashboard.getCellsHeader().add(DateUtility.addDayToDate(from, i));
		}
		int k=0;
		for(Room room : rooms){
			CellsLine line = new CellsLine();
			line.setRoom(room);
			for (int i=0;i<14;i++){
				Cell cell = new Cell();
				cell.setColon(i);
				cell.setLine(k);
				cell.setRoom(room);
				cell.setDay(DateUtility.addDayToDate(from, i));
				List<Reservation> list = Reservation.getReservationByDayState(room, cell.getDay());
				if(list!=null && !list.isEmpty()){
					cell.setReservation(list.get(0));
				}
				line.getCellules().add(cell);	
			}
			k++;
			line.arrange();
			dashboard.getCellsLine().add(line);
		}
		uiModel.addAttribute("from", from);
		uiModel.addAttribute("dashboard", dashboard);
	}
			
}
