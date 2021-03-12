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
    And assert response.data.personName.firstName == "sarah"
    And assert response.data.favouriteColour == "blue"

    ## update the payload
    Given def updateReq =
    """
        {
          "emailId": "sarah.r5@gmail.com",
          "personName": {
          "firstName": "sarahUpdated",
          "lastName": "r"
          },
          "age": "32",
          "favouriteColour": "red"
        }
    """
    Given path 'v1/people/'+id
    And request updateReq
    When method put
    Then status 202
    * def id = response.personId

    #Get the person id
    Given path 'v1/people/'+id
    When method get
    Then status 200
    And assert response.data.personName.firstName == "sarahUpdated"
    And assert response.data.favouriteColour == "red"

    #Delete the person id
    Given path 'v1/people/'+id
    When method delete
    Then status 204

    #Again Get the person id
    Given path 'v1/people/'+id
    When method get
    Then status 404

    #Again Put the person id
    Given path 'v1/people/'+id
    And request updateReq
    When method put
    Then status 404

    #Again delete the person id
    Given path 'v1/people/'+id
    When method delete
    Then status 404

    # Get all person
    Given path 'v1/people'
    When method GET
    Then status 200
    And assert response.count == 10