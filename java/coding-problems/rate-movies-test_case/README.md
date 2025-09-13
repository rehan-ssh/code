# Implement ratings functionality for movies

## Problem Statement

You are building book my show. As a part of this system, you need to expose a functionality using which users can rate movies and check out average ratings of movies.

## Requirements
We want to build 2 functionalities:

### 1. Rate a movie
- Users should be able to rate a movie on a scale of 1 to 5.
- Once a user has rated a movie, they can update their rating.
- Users cannot delete their rating for a movie.

Request for rating a movie will contain:
* `userId` - Id of the user who is rating the movie.
* `movieId` - Id of the movie which is being rated.
* `rating` - Rating given by the user.

Response for rating a movie will contain:
* rating object
* response status - it will be SUCCESS or FAILURE

Make sure to add basic validations.

### 2. Get average rating of a movie
- Users should be able to get average rating of a movie.

Request for getting average rating of a movie will contain:
* `movieId` - Id of the movie for which average rating is being requested.

Response for getting average rating of a movie will contain:
* `averageRating` - Average rating of the movie.
* response status - it will be SUCCESS or FAILURE


## Instructions
1. Carefully look at classes in `dtos` package. These classes represent the request and response of the functionality which we want to implement.
2. Carefully examine the models package to understand the database schema.
3. Implement the `rateMovie` and `getAverageMovieRating` method inside the `RatingsController`.
4. Implement the `RatingsService` interface and fix the repository interfaces.
5. You might need to add annotations like `@Service`, `@Autowired`, `@Entity` etc. to make the solution work. You might also need to handle cardinality between the models.
6. We will be using H2 database which is an in-memory SQL database. You do not need to implement any database related code. You just need to use the repository interfaces to interact with the database.
7. Carefully examining the `TestRatingsController` to understand how your solution will be tested.