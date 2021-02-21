Feature: Test Person API

  Background:
    * url baseUrl

  Scenario Outline: Retrieve the person id,<testStep>,<personId> and <expectedType> and <expectedErrorMessage>

    #Get the person id
    Given path 'v1/persons/'+<personId>
    When method get
    Then status <expectedCode>
    And match response.message == <expectedErrorMessage>
    And match response.type == <expectedType>

    Examples:
      | testStep                          | personId                               | expectedCode | expectedType  | expectedErrorMessage                    |
      | "The Person id is null"           | null                                   | 400          | "Bad Request" | "The 'personId' is invalid UUID format" |
      | "The Person id is invalid format" | 'd6f02a17-c676-4b1b'                   | 400          | "Bad Request" | "The 'personId' is invalid UUID format" |
      | "The Person id is not found"      | 'd6f02a17-c676-4b1b-ae39-e3b12f47c407' | 404          | "Not Found"   | "The 'personId' is not found"           |

  Scenario Outline: delete the person id,<testStep>,<personId> and <expectedType> and <expectedErrorMessage>

    #delete the person id
    Given path 'v1/persons/'+<personId>
    When method delete
    Then status <expectedCode>
    And match response.message == <expectedErrorMessage>
    And match response.type == <expectedType>

    Examples:
      | testStep                          | personId                               | expectedCode | expectedType  | expectedErrorMessage                    |
      | "The Person id is null"           | null                                   | 400          | "Bad Request" | "The 'personId' is invalid UUID format" |
      | "The Person id is invalid format" | 'd6f02a17-c676-4b1b'                   | 400          | "Bad Request" | "The 'personId' is invalid UUID format" |
      | "The Person id is not found"      | 'd6f02a17-c676-4b1b-ae39-e3b12f47c407' | 404          | "Not Found"   | "The 'personId' is not found"           |

  Scenario Outline: GET the person id,<testStep>,<personId> and <expectedType> and <expectedErrorMessage>

    #delete the person id
    Given path 'v1/persons/'+<personId>
    When method GET
    Then status <expectedCode>
    And match response.message == <expectedErrorMessage>
    And match response.type == <expectedType>

    Examples:
      | testStep                          | personId                               | expectedCode | expectedType  | expectedErrorMessage                    |
      | "The Person id is null"           | null                                   | 400          | "Bad Request" | "The 'personId' is invalid UUID format" |
      | "The Person id is invalid format" | 'd6f02a17-c676-4b1b'                   | 400          | "Bad Request" | "The 'personId' is invalid UUID format" |
      | "The Person id is not found"      | 'd6f02a17-c676-4b1b-ae39-e3b12f47c407' | 404          | "Not Found"   | "The 'personId' is not found"           |