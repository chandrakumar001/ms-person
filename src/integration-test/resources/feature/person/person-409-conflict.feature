Feature: Test Person API

  Background:
    * url baseUrl

  Scenario: Create a Person, ADD & REMOVE a Person

    Given def req =
    """
        {
          "emailId": "osaimar12@gmail.com",
          "personName": {
          "firstName": "sarah",
          "lastName": "r"
          },
          "age": "32",
          "favouriteColour": "blue"
        }
    """

    Given path 'v1/people'
    And request req
    When method post
    Then status 409