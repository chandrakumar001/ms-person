package com.chandrakumar.ms.api.person.mapper;

import com.chandrakumar.ms.api.person.dto.PersonDTO;
import com.chandrakumar.ms.api.person.dto.PersonNameDTO;
import com.chandrakumar.ms.api.person.entity.Person;
import com.chandrakumar.ms.api.person.entity.PersonName;

public class PersonMapper {

    private PersonMapper() {
        throw new IllegalStateException("PersonMapper class");
    }

    public static PersonDTO mapToPersonDTO(final Person existingPerson) {

        final PersonNameDTO personNameDTO = mapToPersonNameDTO(
                existingPerson.getPersonName()
        );
        PersonDTO personDTO = new PersonDTO();
        personDTO.setEmailId(existingPerson.getEmailId());
        personDTO.setAge(existingPerson.getAge());
        personDTO.setFavouriteColour(existingPerson.getFavouriteColour());
        personDTO.setPersonName(personNameDTO);
        return personDTO;
    }

    private static PersonNameDTO mapToPersonNameDTO(final PersonName existingPersonName) {

        PersonNameDTO personNameDTO = new PersonNameDTO();
        personNameDTO.setFirstName(existingPersonName.getFirstName());
        personNameDTO.setLastName(existingPersonName.getLastName());
        return personNameDTO;
    }

    public static Person mapToPerson(final PersonDTO personDTO,
                                     final Person person) {

        final PersonName personName = mapToPersonName(
                personDTO.getPersonName()
        );
        person.setEmailId(personDTO.getEmailId());
        person.setAge(personDTO.getAge());
        person.setFavouriteColour(personDTO.getFavouriteColour());
        person.setPersonName(personName);
        return person;
    }

    private static PersonName mapToPersonName(final PersonNameDTO personNameDTO) {

        PersonName personName = new PersonName();
        personName.setFirstName(personNameDTO.getFirstName());
        personName.setLastName(personNameDTO.getLastName());
        return personName;
    }

}
