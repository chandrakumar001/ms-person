Feature: Test Person API

  Background:
    * url baseUrl

  Scenario: Create a Person, ADD & REMOVE a Person

    Given def req =
    """
        {
          "email_id": "osaimar12@gmail.com",
          "person_name": {
          "first_name": "sarah",
          "last_name": "r"
          },
          "age": "32",
          "favourite_colour": "blue"
        }
    """

    Given path 'v1/persons'
    And request req
    When method post
    Then status 409