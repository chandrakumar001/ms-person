package com.chandrakumar.ms.api.person.service

import com.chandrakumar.ms.api.person.dto.PersonDTO
import com.chandrakumar.ms.api.person.dto.PersonNameDTO
import com.chandrakumar.ms.api.person.entity.Person
import com.chandrakumar.ms.api.person.entity.PersonName

class PersonMockData {

    static PersonDTO personDTO(String emailId,
                               String firstName,
                               String lastName,
                               String age) {

        PersonNameDTO personNameDTO = personNameDTO(
                firstName,
                lastName
        )
        PersonDTO personDTO = new PersonDTO()
        personDTO.emailId = emailId
        personDTO.personName = personNameDTO
        personDTO.age = age
        personDTO.favouriteColour = "blue"
        personDTO
    }

    private static PersonNameDTO personNameDTO(String firstName,
                                               String lastName) {

        PersonNameDTO personNameDTO = new PersonNameDTO()
        personNameDTO.firstName = firstName
        personNameDTO.lastName = lastName
        personNameDTO
    }

    static Person person(String emailId,
                         String firstName,
                         String lastName,
                         String age) {

        PersonName personName = personName(
                firstName,
                lastName
        )
        Person person = new Person()
        person.emailId = emailId
        person.personName = personName
        person.age = age
        person.favouriteColour = "blue"
        person
    }

    private static PersonName personName(String firstName,
                                         String lastName) {

        PersonName personName = new PersonName()
        personName.firstName = firstName
        personName.lastName = lastName
        personName
    }
}
