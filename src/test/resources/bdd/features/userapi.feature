Feature: User API Operations

  @FirstScenario
  Scenario: client makes call to all users using GET /gatewayapi/user
    When the client calls /gatewayapi/user
    Then the client receives status code of 200
    And the client receives user with name usertest

  Scenario: client makes call to specific user using GET /gatewayapi/user/userid
    When the client calls /gatewayapi/user/userid
    Then the client receives status code of 200
    And the client receives name useridtest