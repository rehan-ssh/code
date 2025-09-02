# Estimate delivery time functionality for an e-commerce platform

## Problem Statement
Whenever we browse a product on Amazon/Flipkart, we see an estimated delivery date and time on the product page. This estimate tells us that if we buy the product now, then by what time we will receive the product.
We want to build this functionality for our e-commerce platform.

## Requirements
Products are stored at seller's warehouses.
To ensure seamless and quick delivery experience, ecommerce companies setup delivery hubs at various locations. We can assume that each zipcode (pincode) has 1 delivery hub.
Since each zipcode has 1 delivery hub, this is ideal spot from where the product can be delivered to the user.

Total time required to deliver a product to a user is made up of 2 parts:
1. Ship the product from seller warehouse to the delivery hub.
2. Ship the product from the delivery hub to the user's delivery location.
   Total time required = Time required to ship from seller warehouse to delivery hub + Time required to ship from delivery hub to user's delivery location.
   Add this total time required to the current time to get the estimated time.

We will use Google Maps API to calculate the time required to move from a src location to a destination location.
We need to make sure that this integration is as loosely coupled as possible to ensure that we can easily switch to a different API in the future.

We have a address table which will be storing addresses of users, seller warehouses and delivery hubs.
We can use this table to calculate the estimated delivery time.

Assumptions:
1. Each zip code has 1 delivery hub.
2. Each customer has 1 stored address.
3. Each seller has 1 warehouse.
4. Each product will be sold by 1 seller.
5. Each seller can sell multiple products.

Request:
The request will contain the following information:
1. Product ID - The product for which we want to calculate the estimated delivery time.
2. Address ID - The delivery address of the user.

Response:
The response will contain the following information:
1. Estimated date - The estimated date and time of delivery.
2. Response status - This will be either success or failure.

## Instructions
1. Carefully look at classes in dtos package. These classes represent the request and response of the functionality which we want to implement.
2. Carefully examine the models package to understand the database schema.
3. Implement the `estimateDeliveryTime` method inside the `ProductController`.
4. Implement the `ProductService` interface and fix the repository interfaces.
5. You might need to add annotations like `@Service`, `@Autowired`, `@Entity` etc. to make the solution work. You might also need to handle cardinality between the models.
6. We will be using H2 database which is an in-memory SQL database. You do not need to implement any database related code. You just need to use the repository interfaces to interact with the database.
7. Carefully examine the `TestProductController` class to understand how the controller will be tested. Your solution should pass all the tests in this class.
8. Refer the libraries package to understand how to use Google Maps api.