# Implement functionality to show ads on an ecommerce platform

## Problem Statement

You are building an e-commerce platform. As a part of this system, you need to build a functionality using which users can be shown ads on the platform.

## Requirements

* Our in house data science team has figure out preferences of existing users using their past orders. Now using this information we can show them relevant ads on the platform and increase the chances of them buying the product.
* They have stored the preferences of the users in a `preferences` table. 
* We want to build a functionality which will be called when user logs in to the platform. This functionality will fetch the preferences of the user and show them relevant ads.
* Advertisements created on the platform will be stored in `advertisements` table. Each add will be having a relevant category.
* Eg. A user might have preferences for things like [furniture, electronics, fashion]. So we will show them ads like 'iPhone 15', 'Levis Jeans', 'Wakefit Mattress' etc.
* There can be certain users whose preference data might not be populated yet. For such users we can any ad from the platform.

## Instructions
1. Carefully look at the dto package. These classes represent the request and response of the functionality which we want to implement.
2. Carefully examine the models package to understand the database schema.
3. Implement the `getAdvertisementForUser` method inside the `AdsController`.
4. Implement the `AdsService` interface and fix the repository interfaces.
5. You might need to add annotations like `@Service`, `@Autowired`, `@Entity` etc. to make the solution work. You might also need to handle cardinality between the models.
6. We will be using H2 database which is an in-memory SQL database. You do not need to implement any database related code. You just need to use the repository interfaces to interact with the database.
7. Implement the necessary exceptions in the `exceptions` package.
8. Do not modify the `AdsService` interface's placeOrder method signature. You can add additional methods to the interface if you want.