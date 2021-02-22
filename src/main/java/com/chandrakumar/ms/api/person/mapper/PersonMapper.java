package com.chandrakumar.ms.api.person.mapper;

import com.chandrakumar.ms.api.person.entity.Person;
import com.chandrakumar.ms.api.person.entity.PersonName;
import com.chandrakumar.ms.api.person.swagger.model.PersonBareDTO;
import com.chandrakumar.ms.api.person.swagger.model.PersonDTO;
import com.chandrakumar.ms.api.person.swagger.model.PersonListResponseDTO;
import com.chandrakumar.ms.api.person.swagger.model.PersonNameDTO;

import java.util.List;

public class PersonMapper {

    private PersonMapper() {
        throw new IllegalStateException("PersonMapper class");
    }

    public static PersonListResponseDTO getPersonListResponseDTO(
            final List<PersonDTO> personDTOList) {

        PersonListResponseDTO personListResponseDTO = new PersonListResponseDTO();
        personListResponseDTO.setCount(personDTOList.size());
        personListResponseDTO.setPersons(personDTOList);
        return personListResponseDTO;
    }

    public static PersonDTO mapToPersonDTO(final Person existingPerson) {

        final PersonNameDTO personNameDTO = mapToPersonNameDTO(
                existingPerson.getPersonName()
        );
        final PersonBareDTO personBareDTO = mapToPersonBareDTO(
                existingPerson,
                personNameDTO
        );
        PersonDTO personDTO = new PersonDTO();
        personDTO.setData(personBareDTO);
        personDTO.setPersonId(existingPerson.getPersonId());
        return personDTO;
    }

    private static PersonBareDTO mapToPersonBareDTO(final Person existingPerson,
                                                    final PersonNameDTO personNameDTO) {

        PersonBareDTO personBareDTO = new PersonBareDTO();
        personBareDTO.setEmailId(existingPerson.getEmailId());
        personBareDTO.setAge(existingPerson.getAge());
        personBareDTO.setFavouriteColour(existingPerson.getFavouriteColour());
        personBareDTO.setPersonName(personNameDTO);
        return personBareDTO;
    }

    private static PersonNameDTO mapToPersonNameDTO(final PersonName existingPersonName) {

        PersonNameDTO personNameDTO = new PersonNameDTO();
        personNameDTO.setFirstName(existingPersonName.getFirstName());
        personNameDTO.setLastName(existingPersonName.getLastName());
        return personNameDTO;
    }

    public static Person mapToPerson(final PersonBareDTO personBareDTO,
                                     final Person person) {

        final PersonName personName = mapToPersonName(
                personBareDTO.getPersonName()
        );
        person.setEmailId(personBareDTO.getEmailId());
        person.setAge(personBareDTO.getAge());
        person.setFavouriteColour(personBareDTO.getFavouriteColour());
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
