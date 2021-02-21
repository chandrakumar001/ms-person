package com.chandrakumar.ms.api.person.service


import com.chandrakumar.ms.api.person.entity.Person
import com.chandrakumar.ms.api.person.entity.PersonName
import com.chandrakumar.ms.api.person.swagger.model.PersonBareDTO
import com.chandrakumar.ms.api.person.swagger.model.PersonDTO
import com.chandrakumar.ms.api.person.swagger.model.PersonNameDTO

class PersonMockData {

    static PersonDTO personDTO(String emailId,
                               String firstName,
                               String lastName,
                               String age) {
        PersonBareDTO personBareDTO = personBareDTO(
                emailId,
                firstName,
                lastName,
                age
        )
        PersonDTO personDTO = new PersonDTO()
        personDTO.personId = UUID.fromString("d6f02a17-c676-4b1b-ae39-e3b12f47c407")
        personDTO.data = personBareDTO
        personDTO
    }

    static PersonBareDTO personBareDTO(String emailId,
                                       String firstName,
                                       String lastName,
                                       String age) {

        PersonNameDTO personNameDTO = personNameDTO(
                firstName,
                lastName
        )
        PersonBareDTO personBareDTO = new PersonBareDTO()
        personBareDTO.emailId = emailId
        personBareDTO.personName = personNameDTO
        personBareDTO.age = age
        personBareDTO.favouriteColour = "blue"
        personBareDTO
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
