package org.adorsys.geshotel.booking.planning;

import java.util.ArrayList;
import java.util.List;

import org.adorsys.geshotel.booking.domain.Room;



public class CellsLine {
	
	private Room room;
	
	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public List<Cell> getCellules() {
		return cellules;
	}

	public void setCellules(List<Cell> cellules) {
		this.cellules = cellules;
	}

	private List<Cell> cellules = new ArrayList<Cell>();
	
	public void arrange(){
		 List<Cell> cels = new ArrayList<Cell>();
		 int j=2,k=0;
		 	
		 for(int i=1;i<cellules.size();i++){
			 
			 if((cellules.get(k).getReservation()!=null && cellules.get(i).getReservation()!=null)
					 && (cellules.get(k).getReservation().getReservationState().equals(cellules.get(i).getReservation().getReservationState()))
					 && cellules.get(k).getReservation().getId().equals(cellules.get(i).getReservation().getId())){
				 cellules.get(k).setColospan(j);
				 j++;//incremente le colospan
				
			 }else{
				 j=2;//initie le colospan
				 cels.add(cellules.get(k));
				 k=i;//index de la nvelle cellule
			 }
			 if(i==(cellules.size()-1)){//ajouter le dernier elt
				 cels.add(cellules.get(k));
			 }
	}	
		 cellules=cels;
		 
  }

}