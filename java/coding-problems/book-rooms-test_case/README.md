# Implement functionality using which customers can book Hotel rooms.

## Problem Statement
You are building a Hotel Management System. As a part of this system, you need to implement functionality using which customers can book hotel rooms.

## Assignment

Your task is to implement the following functionality in the system.

#### Requirements

1. A customer can book multiple rooms before requesting for the invoice. Hence, our system must be able to track all the rooms booked by a customer.
   * We have an entity called as `CustomerSession` in our system which will help us track the rooms booked by a customer.
   * Once the customer books their 1st room, we should create a `CustomerSession` for them with status as `ACTIVE`.
   * All the subsequent rooms booked by the customer should be associated with the `CustomerSession` created for them.
2. The request for booking a room will contain the user id of the customer and a Map<Long,Integer>, where the key will be room ids and value is the number of such rooms booked.
3. This functionality should store the booking details in the database.
4. If the room is booked for a customer who is not present in the database, then we should throw an error.
5. If a booking contains a room which is not present in the database, then we should throw an error.
6. Return the booking details in the response.

#### Instructions

* Refer the `makeBooking` method inside `BookingController` class.
* Refer the `MakeBookingRequestDto` and `MakeBookingResponseDto` for understanding the expected input and output to the functionality.
* Refer the models package to understand the models.
* Implement the `BookingService`, `CustomerSessionRepository`, `UserRepository`, `RoomRepository` and `BookingRepository` interfaces to achieve the above requirements.
* We need in memory database implementation for this assignment. Feel free to use any data structure to store data for each repository. 
* Do not modify existing methods and their parameters for interfaces, feel free to add more methods if required.



