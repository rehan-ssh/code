# Implement place order functionality for an e-commerce platform

## Problem Statement

You are building an e-commerce platform. As a part of this system, you need to expose a functionality using which users can place an order for products.

## Requirements
1. The request to place an order will contain the following information:
   * User id of the user who wants to place the order.
   * List of product ids and their quantities (List<Pair<Integer, Integer>>, the pair's key is the product id and value is the quantity) which the user wants to order.
   * Address id of the user's address where the order needs to be delivered.
2. Before creating an order we need to check the following things:
   * Does the user exist in the system? If not then we need to throw an exception.
   * Does the address exist in the system? If not then we need to throw an exception.
   * Does the address belong to the user? If not then we need to throw an exception.
   * Do all the products have enough quantity to fill the order? If not then we need to throw an exception.
   * If all the above checks pass, then we need to update the inventory with the updated quantity, create an order (with status as placed) and return the order details.
3. We should handle for concurrent requests, i.e. we should not overbook the inventory. We should allow concurrent requests to place an order only if the inventory has enough quantity to fulfill the order.
4. Few products might be facing a lot of demand but their supply is limited eg. iPhones. For such products we will store max number of quantity per order that a user can order. Details for such products will be stored in the `high_demand_products` table.


## Instructions
1. Carefully look at `PlaceOrderRequestDto` and `PlaceOrderResponseDto` classes. These classes represent the request and response of the functionality which we want to implement.
2. Carefully examine the models package to understand the database schema.
3. Implement the `placeOrder` method inside the `OrderController`.
4. Implement the `OrderService` interface and fix the repository interfaces.
5. You might need to add annotations like `@Service`, `@Autowired`, `@Entity` etc. to make the solution work. You might also need to handle cardinality between the models.
6. We will be using H2 database which is an in-memory SQL database. You do not need to implement any database related code. You just need to use the repository interfaces to interact with the database.
7. Implement the necessary exceptions in the `exceptions` package.
8. Do not modify the `OrderService` interface's placeOrder method signature. You can add additional methods to the interface if you want.