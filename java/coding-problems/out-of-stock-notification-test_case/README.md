# Implement notify users for out of stock products functionality for an e-commerce platform

## Problem Statement

You are building an e-commerce platform. As a part of this system, you need to expose a functionality using which users can register for notifications when a out-of-stock product becomes available. Once the product is back in stock, these users should be notified via email.

## Requirements
We need to build 3 functionalities:

### Requirement #1: Interested users can register for notifications when a product becomes available.

Request for registering for notifications will contain: product id and user id.
Before registering for notifications we need to check the following things:
* Does the user exist in the system? If not then we need to throw an exception.
* Does the product exist in the system? If not then we need to throw an exception.
* Is the product really out of stock? If not then we need to throw an exception.
* If all the above checks pass, then we need to register the user for notifications by making an entry in the `notifications` table.

Response for registering for notifications will contain: notification object and response status.

### Requirement #2: Users who have already registered for notifications can unregister themselves.

Request for unregistering for notifications will contain: notification id and user id.
Before unregistering for notifications we need to check the following things:
* Does the user exist in the system? If not then we need to throw an exception.
* Does the notification exist in the system? If not then we need to throw an exception.
* Does the notification belong to the user? If not then we need to throw an exception.
* If all the above checks pass, then we need to unregister the user for notifications by deleting the entry from the `notifications` table.

Response for unregistering for notifications will contain: response status.

### Requirement #3: There is a functionality using which Admins can add stocks for a product. Modify this functionality to notify all the users who have registered for notifications for this product. The users who have registered themselves should be notified via email.
We need to modify the `updateInventory` method inside the `InventoryController` to notify all the users who have registered for notifications for this product. The users who have registered themselves should be notified via email.
We will use SendGrid (a third party service) to send emails. The email subject will be "{product_name} back in stock!" and the email body will be "Dear {user_name}, {product_name} is now back in stock. Grab it ASAP!".
The integration with SendGrid should be as loosely coupled as possible. We should be able to easily replace SendGrid with any other email service provider in the future.

## Instructions
1. Carefully look at classes in dtos package. These classes represent the request and response of the functionality which we want to implement.
2. Carefully examine the models package to understand the database schema.
3. Implement the `registerUser` and `deregisterUser` method inside the `NotificationController`.
4. Implement the `NotificationService` interface and fix the repository interfaces.
5. Modify the implementation of `InventoryService` interface to notify users when a product becomes available.
6. You might need to add annotations like `@Service`, `@Autowired`, `@Entity` etc. to make the solution work. You might also need to handle cardinality between the models.
7. We will be using H2 database which is an in-memory SQL database. You do not need to implement any database related code. You just need to use the repository interfaces to interact with the database.
8. Carefully examine the `TestControllers` class to understand how the controller will be tested. Your solution should pass all the tests in this class.
9. Implement the necessary exceptions in the `exceptions` package.
10. Do not modify the `NotificationService`, `InventoryService` interface's method signatures. You can add additional methods to the interface if you want.
11. Refer the libraries package to understand how to use SendGrid.