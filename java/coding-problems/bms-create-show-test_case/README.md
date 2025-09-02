# Create a show for Book My Show

## Problem Statement

You are building book my show. As a part of this system, you need to expose a functionality using which theatre admins can create a show. Once this show has been created, users will be able to book tickets for this show on the platform.

## Requirements
1. The request to book a ticket will contain the following things:
   * Movie ID - The ID of the movie which is being shown.
   * User ID - The ID of the user who is creating the show.
   * Screen ID - The ID of the screen where the show is being hosted.
   * PricingConfig (List<Pair<SeatType, Double>) - The price of each seat type for this show.
   * Start Time - The start time of the show.
   * End Time - The end time of the show.
   * Date - The date on which the show is being hosted.
   * Feature List - The list of features that are supported by this show.
2. This functionality should be only accessible to the theatre admin.
3. Every screen has supported features like 2D, 3D, Dolby vision etc. The show that is going to be scheduled on a screen should support all or subset of these features. Example scenarios:
   * If a screen is a 2D screen, then a 3D show cannot be scheduled on it.
   * If a screen supports 3D, 2D, Dolby atmos, then a show which supports 2D and Dolby atmos can be scheduled on it.
4. The functionality should do basic data validation checks ex. The start time should be before the end time.
5. Once this functionality executes successfully, we should store show details, seats related details for this show and pricing details for this show in the database.


## Instructions
1. Carefully look at `CreateShowRequestDto` and `CreateShowResponseDto` classes. These classes represent the request and response of the functionality which we want to implement.
2. Carefully examine the models package to understand the database schema.
3. Implement the `createShow` method inside the `ShowController`.
4. Implement the `ShowService` interface and fix the repository interfaces.
5. You might need to add annotations like `@Service`, `@Autowired`, `@Entity` etc. to make the solution work. You might also need to handle cardinality between the models.
6. We will be using H2 database which is an in-memory SQL database. You do not need to implement any database related code. You just need to use the repository interfaces to interact with the database.
7. Carefully examine the `ShowControllerTest` class to understand how the controller will be tested. Your solution should pass all the tests in this class.