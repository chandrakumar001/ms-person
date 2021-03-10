package com.chandrakumar.ms.api.person.resource

import com.chandrakumar.ms.api.person.service.PersonMockData
import com.chandrakumar.ms.api.person.service.PersonQueryService
import com.chandrakumar.ms.api.person.swagger.model.PersonDTO
import com.chandrakumar.ms.api.person.swagger.model.PersonListResponseDTO
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@Unroll
// No @ContextConfiguration with Spring MVC Test!
class DefaultPersonQueryControllerSpec extends Specification {

    // Don't want to call real service, so use mock
    def queryService = Mock(PersonQueryService)
    ObjectMapper objectMapper;
    def mockMvc = null;

    /**
     * Runs before each test method, like the JUnit Before
     * annotation
     */
    def setup() {
        objectMapper = new ObjectMapper()
        def personQueryRestController = new PersonQueryRestController(
                queryService
        )
        // Let Spring MVC Test process the controller:
        mockMvc = MockMvcBuilders
                .standaloneSetup(personQueryRestController)
                .build()
    }

    void "should return getAllPerson"() {
        given:

        List<PersonDTO> people = PersonMockData.personDTOs()
        final PersonListResponseDTO personListResponseDTO = new PersonListResponseDTO()
        personListResponseDTO.setPeople(people)
        personListResponseDTO.setCount(people.size())

        queryService.getAllPerson(_ as Integer, _ as Integer) >> personListResponseDTO

        when:
        // Try different URL or Method to see what will happen!
        MockHttpServletRequestBuilder builder = get(
                "/v1/people?page=1&size=10"
        );
        MvcResult mvcResult = mockMvc.perform(builder)
                .andReturn()

        then:
        final String actualResponse = mvcResult.getResponse().getContentAsString();
        final List<PersonDTO> peopleList = actualResponseBody(actualResponse)

        final String actualResponseBody = objectMapper.writeValueAsString(peopleList)
        final String exceptedResponseBody = objectMapper.writeValueAsString(people)
        and:
        actualResponseBody == exceptedResponseBody
        1 * queryService.getAllPerson(_ as Integer, _ as Integer) >> personListResponseDTO
    }

    def "should return personDTO object"() {
        given:

        final PersonDTO personDTO = PersonMockData.personDTO(
                "abc@in.com",
                "abc",
                "c",
                String.valueOf(18)
        )

        queryService.getPersonById(_ as String) >> personDTO
        when:
        // Try different URL or Method to see what will happen!
        MockHttpServletRequestBuilder builder = get(
                "/v1/people/d6f02a17-c676-4b1b-ae39-e3b12f47c407"
        );
        MvcResult mvcResult = mockMvc.perform(builder)
                .andReturn()

        then:
        final String actualResponseBody = mvcResult.getResponse().getContentAsString();
        final String exceptedResponseBody = objectMapper.writeValueAsString(personDTO)

        and:
        actualResponseBody == exceptedResponseBody
        1 * queryService.getPersonById(_ as String) >> personDTO
    }


    private List<PersonDTO> actualResponseBody(String actualResponse) {

        final PersonListResponseDTO personDTOList = objectMapper.readValue(
                actualResponse,
                PersonListResponseDTO.class
        )
        List<PersonDTO> peopleList = personDTOList.getPeople()
        peopleList
    }
}
