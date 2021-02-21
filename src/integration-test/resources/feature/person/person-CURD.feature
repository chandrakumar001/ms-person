Feature: Test Person API

  Background:
    * url baseUrl

  #@ignore
  Scenario: Create a Person, ADD & REMOVE a Person

    Given def req =
    """
        {
          "email_id": "sarah.r5@gmail.com",
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
    Then status 201

    #Get the person id
    * def id = 'd6f02a17-c676-4b1b-ae39-e3b12f47c400'
    Given path 'v1/persons/'+id
    When method get
    Then status 200

    #Delete the person id
    Given path 'v1/persons/'+id
    When method delete
    Then status 204

    #Get the person id
    Given path 'v1/persons/'+id
    When method get
    Then status 404

    Given path 'v1/persons'
    When method GET
    Then status 200
    And assert response.length == 4