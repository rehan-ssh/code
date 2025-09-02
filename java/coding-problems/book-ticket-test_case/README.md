# Implement book ticket for Book My Show

## Problem Statement

You are building book my show. As a part of this system, you need to expose a functionality using which users can book tickets for a movie.

## Requirements
1. The request to book a ticket will contain the following things:
   * userId - The user who is trying to book the ticket
   * List of showSeatIds - The ids of the show_seat table which user is trying to book.
2. The system should check if the seats are available or not. If the seats are available, then the system should book the seats and return a ticket with the details of the seats booked and status of the ticket as "UN_PAID" and the seats should be set to "BLOCKED" in the show_seat table.
3. Once the user pays for the ticket, the status of the ticket should be set to "PAID" and the seats should be set to "BOOKED" in the show_seat table. (This is out of scope for the current question)
4. Even if one of the seats is not available, then the system should not book any of the seats and return an error message saying "Seats are not available". Also, the ids of the unavailable seats should be returned in the error message.
5. Basic cases like invalid or non-existing userId, invalid showSeatIds etc. should be handled.
6. The system should be scalable and should be able to handle a large number of concurrent requests.

## Instructions
1. Carefully look at `BookTicketRequestDto` and `BookTicketResponseDto` classes. These classes represent the request and response of the functionality which we want to implement.
2. Carefully examine the models package to understand the database schema.
3. Implement the `bookTicket` method inside the `TicketController`.
4. Implement the `TicketService` interface and fix the repository interfaces.
5. You might need to add annotations like `@Service`, `@Autowired`, `@Entity` etc. to make the solution work. You might also need to handle cardinality between the models.
6. We will be using H2 database which is an in-memory SQL database. You do not need to implement any database related code. You just need to use the repository interfaces to interact with the database.