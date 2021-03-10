package com.chandrakumar.ms.api.person.resource

import com.chandrakumar.ms.api.person.service.PersonCommandService
import com.chandrakumar.ms.api.person.service.PersonMockData
import com.chandrakumar.ms.api.person.swagger.model.PersonDTO
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete

@Unroll
// No @ContextConfiguration with Spring MVC Test!
class DefaultPersonCommandControllerSpec extends Specification {

    // Don't want to call real service, so use mock
    def personCommandService = Mock(PersonCommandService)
    ObjectMapper objectMapper;
    def mockMvc = null;

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

    def "should delete personDTO object"() {
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
}
