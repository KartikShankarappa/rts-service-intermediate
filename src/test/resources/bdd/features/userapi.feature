@RTStests
@UserAPI
Feature: User API Operations
	As an administrator, I want the ability to add, remove, update, retrieve user information in the RTS system

	Background
		Given a client with an active Administrator account

      @wip
  Scenario: Get a list of all users in the system
    Given a user in the system
    When the client issues a get request to the uri /users
    Then the client receives status code of 200
    And the client receives list of all users in the system

  Scenario: Add a new user into the system
    When the client issues a POST request to the uri /users with user details
    Then the client receives status code of 200
    And the new user is created in the system
    
  Scenario: Update a user details in the system
  	Given a user in the system
    When the client issues a PUT request to the uri /users with modified user details
    Then the client receives status code of 200
    And the user is updated in the system
    
  Scenario: Delete a user from the system
    Given a user in the system
    When the client issues a DELETE request to the uri /users
    Then the client receives status code of 200
    And the user is deleted from the system