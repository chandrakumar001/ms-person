package com.chandrakumar.ms.api.person.util

import org.springframework.data.domain.PageRequest
import spock.lang.Specification
import spock.lang.Unroll

import static com.chandrakumar.ms.api.person.util.PageRequestBuild.getPageRequest

@Unroll
class PageRequestBuildSpec extends Specification {

    def "Failed::PageRequestBuild,Object creation"() {

        given: "a pageRequest declaration"
        String errorMessage = null
        when: "call that validatePersonDTO() method"
        try {
            new PageRequestBuild();
        } catch (Exception e) {
            errorMessage = e.getMessage()
        }

        then: "Verify the error message"
        errorMessage == 'PageRequestBuild class'
    }

    def "Failed::validateRequest,#testStep,#page,#size==#expectedPageNumber,#expectedPageSize"() {
        given: "a pageRequest declaration"
        PageRequest pageRequest = null
        when: "call that validatePersonDTO() method"
        pageRequest = getPageRequest(
                page,
                size
        )

        then: "Verify the error message"
        pageRequest.getPageNumber() == expectedPageNumber
        pageRequest.getPageSize() == expectedPageSize

        where:
        testStep                         | page | size || expectedPageNumber | expectedPageSize
        "The page and size is null"      | null | null || 1                  | 10
        "The page is null and size is 5" | null | 5    || 1                  | 5
        "The size is 1 and page is null" | 1    | null || 1                  | 10
        "The size is 1 and page is 5"    | 1    | 5    || 1                  | 5

        // Not applicable for below test case..
        // already request in validate page=0 and size=0

        //"The size is 0 and page is 1"    | 0    | 5    || 1                  | 5
        //"The size is 1 and page is 0"    | 1    | 0    || 1                  | 5

    }

    def "Success::validateRequest,#testStep,#page,#size==#expectedPageNumber,#expectedPageSize"() {
        given: "a pageRequest declaration"
        PageRequest pageRequest = null
        when: "call that validatePersonDTO() method"
        pageRequest = getPageRequest(
                page,
                size
        )

        then: "Verify the error message"
        pageRequest.getPageNumber() == expectedPageNumber
        pageRequest.getPageSize() == expectedPageSize

        where:
        testStep                      | page | size || expectedPageNumber | expectedPageSize
        "The size is 1 and page is 5" | 1    | 5    || 1                  | 5
    }
}
