# Implement functionality which will allow Splitwise users to manage groups

## Problem Statement
You are building web application similar to Splitwise. This functionality will be used by the users to manage groups.

## Requirements
* We need to create 3 functionalities to support this feature.
  1. Add member to a group
  2. Remove member from a group
  3. Get all members of a group

1. Add member to a group
* We will get group id, admin Id (user who is inviting the user) and user Id (user who is being invited) as input.
* Only admins of a group can add members to the group.
* Make sure to basic checks like whether the group exists, whether the admin is part of the group etc.
* We need to create a new entry in the `group_members` table (GroupMember entity).
* Return the group member object in response.

2. Remove member from a group
* We will get group id, admin Id (user who is removing the user) and user Id (user who is being removed) as input.
* Only admins of a group can remove members from the group.
* Make sure to basic checks like whether the group exists, whether the admin is part of the group etc.
* We need to delete the entry from the `group_members` table (GroupMember entity).
* Return the status of the operation in response.

3. Get all members of a group
* We will get group id and user id (user trying to fetch this information) as input.
* Only admins and members of a group can fetch the members of the group.
* If a user is not part of the group, then we should throw UnAuthorizedException.
* Make sure to basic checks like whether the group exists, whether the user is part of the group etc.
* We need to fetch all the entries from the `group_members` table (GroupMember entity) for the given group id.
* Also fetch all the entries from the `group_admins` table (GroupAdmin entity) for the given group id.
* Return a combined list of all the members and admins of the group in response.

## Instructions
1. Carefully look at the dtos package. These classes represent the request and response of the functionality which we want to implement.
2. Carefully examine the models package to understand the database schema.
3. Implement the incomplete methods inside the `GroupController`.
4. Implement the `GroupService` interface and fix the repository interfaces.
5. You might need to add annotations like `@Service`, `@Autowired`, `@Entity` etc. to make the solution work. You might also need to handle cardinality between the models.
6. We will be using H2 database which is an in-memory SQL database. You do not need to implement any database related code. You just need to use the repository interfaces to interact with the database.