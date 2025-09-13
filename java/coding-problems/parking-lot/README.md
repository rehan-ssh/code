# Additional Services at Parking Lot

## Problem Statement
We want to start offering additional services at our parking lot. We want to offer additional services like car wash, bike wash etc. Modify the existing ticket generation logic to support additional services. Also we want to generate an invoice for the customer when they are exiting the parking lot. The invoice should include the parking fee and the additional services fee.

## Requirements
### Additional Services
A customer will opt in for additional services when they are getting the ticket.
The customer will pay for the additional services when they are exiting the parking lot along with the parking fee.
All the additional services have a fixed fee.
Each additional service has its own set of supported vehicle types. Meaning a car wash can only be done for cars and not for bikes.
Details for the addtionals services are as follows:
1. Car Wash
    - Supported Vehicle Types: Car, Suv
    - Fee: 200
2. Bike Wash
    - Supported Vehicle Types: Bike, EV bike
    - Fee: 100
3. Car detailing
    - Supported Vehicle Types: Car, Suv
    - Fee: 500
4. EV bike charging
    - Supported Vehicle Types: EV bike
    - Fee: 100
5. EV car charging
    - Supported Vehicle Types: EV car
    - Fee: 200
      If a vehicle type is not supported for a particular service, then the service should not be offered to the customer i.e. ticket should not be generated.

### Invoice generation
We want to generate an invoice for the customer when they are exiting the parking lot. The invoice should include the parking fee and the additional services fee.
Additional services pricing is fixed and is mentioned above.
For parking fee we have 2 models:
1. Weekday pricing: In this model we will have a fixed fee for all vehicle types which is 10 per hour. Eg. If a vehicle enters the parking lot at 10:00 AM and exits at 11:30 AM then the parking fee will be 20. If a vehicle enters the parking lot at 10:00 AM and exits at 11:00 AM then the parking fee will be 10.
2. Weekend pricing: In this model we will have slab based pricing which will differ as per vehicle type.
   E.g. Slab based pricing for cars:
    - 0-2 hours: Rs. 20 per hour
    - 2-4 hours: Rs. 30 per hour
    - 4-6 hours: Rs. 40 per hour
    - 6-(-1) hours: Rs. 50 per hour (-1 indicates that there is no upper limit for the slab)
      If a car is parked for 1 hour then the parking fee will be 20.
      If a car is parked for 3 hours then the parking fee will be 70 (40 for first 2 hours and 30 for next 1 hour).
      If a car is parked for 5 hours then the parking fee will be 140 (40 for first 2 hours, 60 for next 2 hours and 40 for next 1 hour).
      If a car is parked for 7 hours then the parking fee will be 230 (40 for first 2 hours, 60 for next 2 hours, 80 for next 2 hours and 50 for next 1 hour).
      Slab related data will be stored in a table called as `slabs`.
      If a vehicle has opted for additional services then the parking fee will be calculated first and then the additional services fee will be added to it.
      If no additional services are opted then only the parking fee will be charged.

To achieve these requirements we need to do 2 things:
- Modify the already implemented ticket generation logic to include additional services. I.e. if a customer is opting for additional services then details related to the additional services should be stored in the ticket.
- Implement functionality for generating invoice, it should include the parking fee and the additional services fee.

## Instructions
1. We need to modify the `generateTicket` inside `TicketController` class to support additional services.
2. We need to implement the `createInvoice` inside the `InvoiceController` class to generate the invoice.
3. Refer the dtos package to understand the input and output of the functionality we are implementing.
4. Implement the `InvoiceService`,`TicketService` and relevant repositories and strategies to achieve the requirements.
5. Refer the `TestControllers` to understand how the functionality will be tested.
6. Do not modify existing methods inside interfaces, feel to add new if required.
7. We will be using in memory database for this assignment.