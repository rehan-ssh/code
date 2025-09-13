# Implement generate Invoice functionality for Hotel Management System.

## Problem Statement
You are building a Hotel Management System. As a part of this system, you need to implement functionality using which customers can generate invoice for their bookings.

## Assignment

Your task is to implement the following functionality in the system.

#### Requirements

1.A customer can book multiple rooms before requesting for the invoice. Hence, our system must be able to track all the rooms booked by a customer.
   * We should have an entity called as `CustomerSession` in our system which will help us track the bookings by a customer.
   * Once the customer books their 1st room, we should create a `CustomerSession` for them with status as `ACTIVE`.
   * All the subsequent bookings by the customer should be associated with the `CustomerSession` created for them.
   * Once the customer requests for the invoice, we should mark the `CustomerSession` as `ENDED`.
2. The request for generating the invoice will contain just the customer id.
3. This functionality should aggregate the rooms booked by the customer and calculate the total amount to be paid by the customer.
4. This functionality should also calculate GST and service charge on the total amount.
5. GST will be 18% of the total rooms price and service charge will be 10% of the total rooms price.
6. Return the invoice details in the response.

#### Instructions

* Refer the `generateInvoice` function in `BookingController` class.
* Refer the `GenerateInvoiceRequestDto` and `GenerateInvoiceResponseDto` classes for understanding the expected input and output to the functionality.
* Refer the models package for reference of the models.
* Implement the `BookingRepository`, `CustomerSessionRepository` and `BookingService` interfaces to achieve the above requirements.
* Please implement an in memory database to store the rooms. You can use any data structure of your choice to store the rooms.
