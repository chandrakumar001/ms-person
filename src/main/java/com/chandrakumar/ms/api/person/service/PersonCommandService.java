package com.chandrakumar.ms.api.person.service;

import com.chandrakumar.ms.api.person.dto.PersonDTO;

/**
 * Provides access to 'com.chandrakumar.ms.api.person.service' data
 *
 * @author chandrakumar ovanan
 */
public interface PersonCommandService {

    /**
     * Saves the Person.
     *
     * @param personDTO the details of the Person
     */
    PersonDTO createPerson(final PersonDTO personDTO);

    /**
     * Update a person by the id.
     * If the id is not uuid format, a {@link com.chandrakumar.ms.api.exception.FieldValidationException} will be thrown.
     * If the id does not exist, a {@link com.chandrakumar.ms.api.exception.ResourceNotFoundException} will be thrown.
     *
     * @param personId the unique id of the person
     * @return the PersonDTO details
     */
    PersonDTO updatePerson(final String personId,
                           final PersonDTO personDTO);

    /**
     * Delete a person by the id.
     * If the id is not uuid format, a {@link com.chandrakumar.ms.api.exception.FieldValidationException} will be thrown.
     * If the id does not exist, a {@link com.chandrakumar.ms.api.exception.ResourceNotFoundException} will be thrown.
     *
     * @param personId the unique id of the person
     */
    void deletePerson(final String personId);
}