package com.chandrakumar.ms.api.person.service

import com.chandrakumar.ms.api.common.audit.Action
import com.chandrakumar.ms.api.person.entity.Person
import com.chandrakumar.ms.api.person.repository.PersonRepository
import com.chandrakumar.ms.api.person.swagger.model.PersonBareDTO
import com.chandrakumar.ms.api.person.swagger.model.PersonDTO
import org.springframework.data.domain.AuditorAware
import spock.lang.Specification
import spock.lang.Unroll

import static com.chandrakumar.ms.api.person.service.PersonMockData.person
import static com.chandrakumar.ms.api.person.service.PersonMockData.personBareDTO
import static com.chandrakumar.ms.api.person.util.PersonErrorCodeConstant.*

@Unroll
class DefaultPersonCommandServiceSpec extends Specification {

    public static final String USER_NAME = "admin"
    private PersonCommandService commandService
    def personRepository = Mock(PersonRepository)
    def auditorAware = Mock(AuditorAware)

    /**
     * Runs before each test method, like the JUnit Before
     * annotation
     */
    def setup() {
        commandService = new DefaultPersonCommandService(
                personRepository,
                auditorAware
        )
    }

    def "Success::person IDs are logged whenever they are saved in the DB"() {
        given: "a person that assigns"

        def emailId = "osaimar19@gmail.com"
        def firstName = "chandra"
        def lastName = "kumar"
        def age = "28"

        final PersonBareDTO personBareDTO = personBareDTO(emailId, firstName, lastName, age)
        final Person person = person(emailId, firstName, lastName, age)
        person.action= Action.CREATED

        personRepository.findByEmailId(_ as String) >> Optional.empty()
        personRepository.save(_ as Person) >> person

        when: "that person is saved in the DB"
        PersonDTO personDTO = commandService.createPerson(personBareDTO)

        then: "the ID is correctly logged"
        personDTO?.data?.emailId == emailId
        personDTO?.data?.age == age
        and:
        1 * personRepository.save(_ as Person) >> person
    }

    def "Failed::person IDs are logged whenever they are saved in the DB"() {
        given: "a person that assigns"

        def firstName = "chandra"
        def lastName = "kumar"
        def age = "28"
        def actualErrorMessage = null

        final PersonBareDTO personBareDTO = personBareDTO(emailId, firstName, lastName, age)
        personRepository.findByEmailId(_ as String) >> Optional.of(mockDBPersonData)

        when: "that person is saved in the DB"
        try {
            commandService.createPerson(personBareDTO)
        } catch (Exception e) {
            actualErrorMessage = e.getMessage()
        }
        then: "the ID is correctly logged"
        actualErrorMessage == expectedErrorMessage

        where:
        testStep                              | emailId               | mockDBPersonData                                        || expectedErrorMessage
        "Email id already exists in database" | "osaimar19@gmail.com" | person("osaimar19@gmail.com", "chandra", "kumar", "21") || ERROR_THE_EMAIL_IS_ALREADY_EXISTS

    }

    def "Success::updatePerson IDs are logged whenever they are saved in the DB"() {
        given: "a person that assigns an ID to person"
        def personId = "d6f02a17-c676-4b1b-ae39-e3b12f47c407"
        def emailId = "osaimar19@gmail.com"
        def firstName = "chandra"
        def lastName = "kumar"
        def age = "28"

        final PersonBareDTO personBareDTO = personBareDTO(emailId, firstName, lastName, age)
        final Person person = person(emailId, firstName, lastName, age)
        person.action= Action.UPDATED

        personRepository.findByPersonId(_ as UUID) >> Optional.of(person)
        personRepository.save(_ as Person) >> person

        when: "that person is saved in the DB"
        PersonDTO personDTO = commandService.updatePerson(personId, personBareDTO)

        then: "the ID is correctly logged"
        personDTO?.data?.emailId == emailId
        personDTO?.data?.age == age
    }

    def "Failed::updatePerson IDs are logged whenever they are saved in the DB"() {
        given: "a person that assigns an ID to person"
        def emailId = "osaimar19@gmail.com"
        def firstName = "chandra"
        def lastName = "kumar"
        def age = "28"
        def actualErrorMessage = null

        final PersonBareDTO personBareDTO = personBareDTO(emailId, firstName, lastName, age)
        personRepository.findByPersonId(_ as UUID) >> mockDBPersonData

        when: "that person is saved in the DB"
        try {
            commandService.updatePerson(personId, personBareDTO)
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

    def "Success::deletePerson IDs are logged whenever they are saved in the DB"() {
        given: "a person that assigns an ID to person"
        def personId = "d6f02a17-c676-4b1b-ae39-e3b12f47c407"
        def emailId = "osaimar19@gmail.com"
        def firstName = "chandra"
        def lastName = "kumar"
        def age = "28"

        final Person person = person(emailId, firstName, lastName, age)
        person.action= Action.UPDATED

        personRepository.findByPersonId(_ as UUID) >> Optional.of(person)
        personRepository.softDeleteByPersonId(_ as UUID, null, null) >> null
        auditorAware.getCurrentAuditor() >> Optional.of(USER_NAME)

        when: "that person is saved in the DB"
        commandService.deletePerson(personId)

        then: "the ID is correctly logged"
        1 * personRepository.softDeleteByPersonId(_ as UUID, _ as Date, _ as String) >> null
    }

    def "Failed::deletePerson IDs are logged whenever they are saved in the DB"() {
        given: "a person that assigns an ID to person"

        def actualErrorMessage = null
        personRepository.findByPersonId(_ as UUID) >> mockDBPersonData

        when: "that person is saved in the DB"
        try {
            commandService.deletePerson(personId)
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
