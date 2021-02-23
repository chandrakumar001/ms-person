package com.chandrakumar.ms.api.person.service

import com.chandrakumar.ms.api.common.audit.Action
import com.chandrakumar.ms.api.person.entity.Person
import com.chandrakumar.ms.api.person.repository.PersonRepository
import com.chandrakumar.ms.api.person.swagger.model.PersonDTO
import com.chandrakumar.ms.api.person.swagger.model.PersonListResponseDTO
import spock.lang.Specification
import spock.lang.Unroll

import static com.chandrakumar.ms.api.person.service.PersonMockData.person
import static com.chandrakumar.ms.api.person.util.PersonErrorCodeConstant.*

@Unroll
class DefaultPersonQueryServiceSpec extends Specification {

    public static final int PERSON_TOTAL_COUNT = 1
    public static final int FIRST_INDEX = 0

    private PersonQueryService queryService
    def personRepository = Mock(PersonRepository)

    /**
     * Runs before each test method, like the JUnit Before
     * annotation
     */
    def setup() {
        queryService = new DefaultPersonQueryService(personRepository)
    }

    def "Success::getAllPerson IDs are logged whenever they are saved in the DB"() {
        given: "all person mock "
        def emailId = "osaimar19@gmail.com"
        def firstName = "chandra"
        def lastName = "kumar"
        def age = "28"

        final Person person = person(emailId, firstName, lastName, age)
        person.action = Action.UPDATED

        personRepository.findAll() >> List.of(person)

        when: "that person is saved in the DB"
        PersonListResponseDTO personDTOList = queryService.getAllPerson()

        then: "the ID is correctly logged"
        personDTOList.count == PERSON_TOTAL_COUNT
        PersonDTO personDTO = personDTOList.persons.get(FIRST_INDEX);
        personDTO?.data?.emailId == emailId
        personDTO?.data?.age == age
    }

    def "Failed::getAllPerson IDs are logged whenever they are saved in the DB"() {
        given: "all person mock"

        def actualErrorMessage = null
        personRepository.findAll() >> mockDBPersonData

        when: "that person is saved in the DB"
        try {
            queryService.getAllPerson()
        } catch (Exception e) {
            actualErrorMessage = e.getMessage()
        }
        then: "the ID is correctly logged"
        actualErrorMessage == expectedErrorMessage

        where:
        testStep          | mockDBPersonData        || expectedErrorMessage
        "No record found" | Collections.emptyList() || ERROR_NO_RECORD_FOUND
    }

    def "Success::getPersonById IDs are logged whenever they are saved in the DB"() {
        given: "a person that assigns an ID to person"
        def personId = "d6f02a17-c676-4b1b-ae39-e3b12f47c407"
        def personIdUUID = UUID.fromString(personId)
        def emailId = "osaimar19@gmail.com"
        def firstName = "chandra"
        def lastName = "kumar"
        def age = "28"

        final Person person = person(emailId, firstName, lastName, age)
        person.personId = personIdUUID
        person.action = Action.UPDATED

        personRepository.findByPersonId(_ as UUID) >> Optional.of(person)

        when: "that person is saved in the DB"
        PersonDTO personDTO = queryService.getPersonById(personId)

        then: "the ID is correctly logged"
        personDTO.personId == personIdUUID
        personDTO?.data?.emailId == emailId
        personDTO?.data?.age == age
    }

    def "Failed::getPersonById IDs are logged whenever they are saved in the DB"() {
        given: "a person that assigns an ID to person"

        def actualErrorMessage = null
        personRepository.findByPersonId(_ as UUID) >> mockDBPersonData

        when: "that person is saved in the DB"
        try {
            queryService.getPersonById(personId)
        } catch (Exception e) {
            actualErrorMessage = e.getMessage()
        }
        then: "the ID is correctly logged"
        actualErrorMessage == expectedErrorMessage

        where:
        testStep                      | personId                               | mockDBPersonData || expectedErrorMessage
        "The personId invalid format" | "d6f02a17-c676-4b1b-ae39"              | Optional.empty() || ERROR_THE_PERSON_ID_IS_INVALID_UUID_FORMAT
        "The person is not found"     | "d6f02a17-c676-4b1b-ae39-e3b12f47c407" | Optional.empty() || ERROR_PERSON_ID_IS_NOT_FOUND
    }
}
