package com.chandrakumar.ms.api.person.resource

import com.chandrakumar.ms.api.person.service.PersonCommandService
import com.chandrakumar.ms.api.person.service.PersonMockData
import com.chandrakumar.ms.api.person.swagger.model.PersonBareDTO
import com.chandrakumar.ms.api.person.swagger.model.PersonDTO
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification
import spock.lang.Unroll

import static com.chandrakumar.ms.api.person.service.PersonMockData.personBareDTO
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put

@Unroll
// No @ContextConfiguration with Spring MVC Test!
class DefaultPersonCommandControllerSpec extends Specification {

    // Don't want to call real service, so use mock
    def personCommandService = Mock(PersonCommandService)
    ObjectMapper objectMapper;
    MockMvc mockMvc = null;

    /**
     * Runs before each test method, like the JUnit Before
     * annotation
     */
    def setup() {
        objectMapper = new ObjectMapper()
        def personQueryRestController = new PersonCommandRestController(
                personCommandService
        )
        // Let Spring MVC Test process the controller:
        mockMvc = MockMvcBuilders
                .standaloneSetup(personQueryRestController)
                .build()
    }

    def "should delete person object"() {
        given:

        personCommandService.deletePerson(_ as String) >> null
        when:
        // Try different URL or Method to see what will happen!
        MockHttpServletRequestBuilder builder = delete(
                "/v1/people/d6f02a17-c676-4b1b-ae39-e3b12f47c407"
        );
        MvcResult mvcResult = mockMvc.perform(builder)
                .andReturn()

        then:
        1 * personCommandService.deletePerson(_ as String) >> null
    }

    def "should create person object"() {
        given:
        def emailId = "osaimar19@gmail.com"
        def firstName = "chandra"
        def lastName = "kumar"
        def age = "28"

        final PersonBareDTO personBareDTO = personBareDTO(emailId, firstName, lastName, age)
        personCommandService.createPerson(_ as PersonBareDTO) >> PersonMockData.personDTO(
                emailId,
                firstName,
                lastName,
                age
        )
        when:
        // Try different URL or Method to see what will happen!
        MockHttpServletRequestBuilder builder = post("/v1/people")
                .content(asJsonString(personBareDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)

        MvcResult mvcResult = mockMvc.perform(builder)
                .andReturn()

        then:
        1 * personCommandService.createPerson(_ as PersonBareDTO) >> PersonMockData.personDTO(
                emailId,
                firstName,
                lastName,
                age
        )
    }

    def "should update person object"() {
        given:
        def personId = "d6f02a17-c676-4b1b-ae39-e3b12f47c407"
        def emailId = "osaimar19@gmail.com"
        def firstName = "chandra"
        def lastName = "kumar"
        def age = "28"

        final PersonBareDTO personBareDTO = personBareDTO(emailId, firstName, lastName, age)
        personCommandService.updatePerson(_ as String, _ as PersonBareDTO) >> PersonMockData.personDTO(
                emailId,
                firstName,
                lastName,
                age
        )
        when:
        // Try different URL or Method to see what will happen!
        MockHttpServletRequestBuilder builder = put("/v1/people/" + personId)
                .content(asJsonString(personBareDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)

        MvcResult mvcResult = mockMvc.perform(builder)
                .andReturn()

        then:
        1 * personCommandService.updatePerson(_ as String, _ as PersonBareDTO) >> PersonMockData.personDTO(
                emailId,
                firstName,
                lastName,
                age
        )
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
