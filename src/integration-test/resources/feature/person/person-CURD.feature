Feature: Test Person API

  Background:
    * url baseUrl

  #@ignore
  Scenario: Create a Person, ADD & REMOVE a Person

    Given def req =
    """
        {
          "emailId": "sarah.r5@gmail.com",
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
    Then status 201
    * def id = response.personId

    #Get the person id
    Given path 'v1/people/'+id
    When method get
    Then status 200

    #Delete the person id
    Given path 'v1/people/'+id
    When method delete
    Then status 204

    #Get the person id
    Given path 'v1/people/'+id
    When method get
    Then status 404

    Given path 'v1/people'
    When method GET
    Then status 200
    And assert response.count == 4