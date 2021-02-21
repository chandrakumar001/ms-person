package com.chandrakumar.ms.api.person.service;

import com.chandrakumar.ms.api.person.dto.PersonDTO;

import java.util.List;

/**
 * Provides access to 'com.chandrakumar.ms.api.person.service' data
 *
 * @author chandrakumar ovanan
 */
public interface PersonQueryService {

    /**
     * Retrieves all peerson.
     * If the person list does not exist, a {@link com.chandrakumar.ms.api.exception.NoRecordFoundException} will be thrown.
     *
     * @return the List<PersonDTO> details
     */
    List<PersonDTO> getAllPerson();

    /**
     * Retrieves a person by the id.
     * If the id is not uuid format, a {@link com.chandrakumar.ms.api.exception.FieldValidationException} will be thrown.
     * If the id does not exist, a {@link com.chandrakumar.ms.api.exception.ResourceNotFoundException} will be thrown.
     *
     * @param personId the unique id of the person
     * @return the PersonDTO details
     */
    PersonDTO getPersonById(final String personId);
}