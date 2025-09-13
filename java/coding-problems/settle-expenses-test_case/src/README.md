# Implement functionality which will allow Splitwise users to manage groups

## Problem Statement
You are building web application similar to Splitwise. This functionality will be used by the users to settle up transactions.

## Requirements
We need to create 2 functionalities to support this feature.
  1. Settle a group
  2. Settle a user 


1. Settle a group
* We will get group id as input.
* For this group we need to consider all the transactions between all the members of the group and come up with the minimum number of transactions required to settle up all the balances.
* We should use the min heap and max heap approach to solve this problem. But keep the implementation flexible so that we can change the approach later.
* We need to return the list of transactions which will settle up all the balances.
* Make sure to do basic checks like whether the group exists etc.

2. Settle a user
* We will get user id (user trying to delete this group) as input.
* Fetch all the non group expenses of this user.
* Run the algorithm to settle up all the balances and return the list of transactions which will settle up all the balances.

Note: Some of the logic might be common between these 2 functionalities. Make sure to reuse the code.

## Instructions
1. Carefully look at the dtos package. These classes represent the request and response of the functionality which we want to implement.
2. Carefully examine the models package to understand the database schema.
3. Implement the incomplete methods inside the `SettleUpController`.
4. Implement the `SettleUpService` interface and fix the repository interfaces.
5. You might need to add annotations like `@Service`, `@Autowired`, `@Entity` etc. to make the solution work. You might also need to handle cardinality between the models.
6. We will be using H2 database which is an in-memory SQL database. You do not need to implement any database related code. You just need to use the repository interfaces to interact with the database.