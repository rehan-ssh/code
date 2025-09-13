# Implement cancel order functionality for an e-commerce platform

## Problem Statement
You are building an e-commerce platform. As a part of this system, you need to expose a functionality using which users can cancel an order.

## Requirements
1. The request to cancel an order will contain the following information:
   * User id of the user who wants to cancel the order.
   * Order id of the order which the user wants to cancel.
2. Before canceling an order we need to check the following things:
   * Does the user exist in the system? If not then we need to throw an exception.
   * Does order exist in the system? If not then we need to throw an exception.
   * Does the order belong to the user? If not then we need to throw an exception.
   * An order cannot be cancelled if its in SHIPPED or DELIVERED or CANCELLED state.
   * If all the above checks pass then we need to update the inventory with by adding the quantity of the order back to the inventory. And marking the order as cancelled.
3. We should handle for concurrent requests, i.e. we should update the inventory correctly.

## Instructions
1. Carefully look at `CancelOrderRequestDto` and `CancelOrderResponseDto` classes. These classes represent the request and response of the functionality which we want to implement.
2. Carefully examine the models package to understand the database schema.
3. Implement the `cancelOrder` method inside the `OrderController`.
4. Implement the `OrderService` interface and fix the repository interfaces.
5. You might need to add annotations like `@Service`, `@Autowired`, `@Entity` etc. to make the solution work. You might also need to handle cardinality between the models.
6. We will be using H2 database which is an in-memory SQL database. You do not need to implement any database related code. You just need to use the repository interfaces to interact with the database.
7. Carefully examine the `TestOrderController` class to understand how the controller will be tested. Your solution should pass all the tests in this class.
8. Implement the necessary exceptions in the `exceptions` package.
9. Do not modify the `OrderService` interface's cancelOrder method signature. You can add additional methods to the interface if you want.