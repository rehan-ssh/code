package com.example.addroomshotelmanagement.dtos;

import com.example.addroomshotelmanagement.models.Room;

public class AddRoomResponseDto {
    private Room room;
    private ResponseStatus responseStatus;

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }
}
