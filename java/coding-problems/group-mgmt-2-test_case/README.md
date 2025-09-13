# Implement functionality which will allow Splitwise users to manage groups

## Problem Statement
You are building web application similar to Splitwise. This functionality will be used by the users to manage groups.

## Requirements
We need to create 2 functionalities to support this feature.
  1. Create a group
  2. Delete a group


1. Create a group
* We will get group name, group description and user Id (user who is creating the group) as input.
* We need to create a new entry in the `groups` table (Group entity).
* We need to create a new entry in the `group_admins` table (GroupAdmin entity) with the user creating the group as the admin.
* Make sure to do basic checks like whether the user exists etc.
* Return the group object in response

2. Delete a group
* We will get group id and user id (user trying to delete this group) as input.
* Only admins of a group can delete the group.
* Make sure to do basic checks like whether the group exists, whether the user is admin of the group etc.
* Delete all the rows from the `group_members` table (GroupMember entity) for the given group id.
* Delete all the rows from the `group_admins` table (GroupAdmin entity) for the given group id.
* Delete the entry from the `groups` table (Group entity) for the given group id.
* Return the status of the operation in response.


## Instructions
1. Carefully look at the dtos package. These classes represent the request and response of the functionality which we want to implement.
2. Carefully examine the models package to understand the database schema.
3. Implement the incomplete methods inside the `GroupController`.
4. Implement the `GroupService` interface and fix the repository interfaces.
5. You might need to add annotations like `@Service`, `@Autowired`, `@Entity` etc. to make the solution work. You might also need to handle cardinality between the models.
6. We will be using H2 database which is an in-memory SQL database. You do not need to implement any database related code. You just need to use the repository interfaces to interact with the database.