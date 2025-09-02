# Register user for Book My Show

## Problem Statement

You are building book my show. As a part of this system, you need to expose a functionality using which users can sign up on the system and later can login to the system.

## Requirements
- Signup Requirements
1. The request to signup will contain the following things:
   * name - The name of the user
   * email - The email of the user
   * password - The password of the user
2. We will not store users plain password in the database. You can use the `BCryptPasswordEncoder` class to generate the hash of the password.
3. If a user already has signed up, then we should throw an error.
4. If a user is registering themselves for the first time, then we should create a new user in the database and return the user details (only name, email and userId). Password should not be returned.

- Login Requirements
1. The request to login will contain the following things:
   * email - The email of the user
   * password - The password of the user
2. If the user is not registered, and they are trying to login, then we should throw an error.
3. If the user is registered, and they are trying to login, then we should check if the password is correct or not. If the password is correct we should return isLoggedIn as true else false.

## Instructions
1. Carefully look at `SignupUserRequestDto`, `SignupUserResponseDto`, `LoginRequestDto` and `LoginResponseDto` classes. These classes represent the request and response of the functionality which we want to implement.
2. Carefully examine the models package to understand the database schema.
3. Implement the `signupUser` and `login` method inside the `TicketController`.
4. Implement the `UserService` interface and fix the repository interfaces.
5. You might need to add annotations like `@Service`, `@Autowired`, `@Entity` etc. to make the solution work. You might also need to handle cardinality between the models.
6. We will be using H2 database which is an in-memory SQL database. You do not need to implement any database related code. You just need to use the repository interfaces to interact with the database.
7. Examine the `TestUserController` class to understand how your code will be tested. Your solution should pass all the tests.