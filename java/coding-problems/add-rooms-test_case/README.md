# Add Hotel Rooms

## Problem Statement
You are building a Hotel Management System. As a part of this system, you need to implement functionality using which hotel staff (ADMINs) can add the hotel rooms in the system.

## Assignment

Your task is to implement the following functionality in the system.

#### Requirements

1. The add a room, request will contain the following details.
   * User id of the admin who is adding the room.
   * Room name
   * Room description
   * Room price
   * Room Type (The room type can be either "deluxe" or "super_deluxe" or "suite")
2. Non admin users should not be able to add a room in the system. If a non admin user tries to add a room, the system should return an error.
3. Persist the room details in the database.
4. Once the room is added, the system should return the room instance in response.

#### Instructions

* Refer the `addRoom` function in RoomController class.
* Refer the `AddRoomRequestDto` and `AddRoomResponseDto` classes for understanding the expected input and output to the functionality.
* Refer the models package for reference of the models.
* Implement the `RoomRepository`, `RoomService` & `UserRepository` interfaces to achieve the above requirements.
* Please implement an in memory database to store the rooms. You can use any data structure of your choice to store the rooms.
