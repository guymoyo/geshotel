// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.booking.domain;

import java.lang.String;

privileged aspect Room_Roo_ToString {
    
    public String Room.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Capacity: ").append(getCapacity()).append(", ");
        sb.append("Coordinates: ").append(getCoordinates()).append(", ");
        sb.append("Description: ").append(getDescription()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("PhoneNumber: ").append(getPhoneNumber()).append(", ");
        sb.append("RoomCategory: ").append(getRoomCategory()).append(", ");
        sb.append("RoomNumber: ").append(getRoomNumber()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Created: ").append(isCreated());
        return sb.toString();
    }
    
}
