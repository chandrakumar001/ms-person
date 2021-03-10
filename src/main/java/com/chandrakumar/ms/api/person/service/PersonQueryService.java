package com.chandrakumar.ms.api.person.service;

import com.chandrakumar.ms.api.person.swagger.model.PersonDTO;
import com.chandrakumar.ms.api.person.swagger.model.PersonListResponseDTO;

/**
 * Provides access to 'com.chandrakumar.ms.api.person.service' data
 *
 * @author chandrakumar ovanan
 */
public interface PersonQueryService {

    /**
     * Retrieves all peerson.
     * If the person list does not exist, a {@link com.chandrakumar.ms.api.error.NoRecordFoundException} will be thrown.
     *
     * @return the PersonListResponseDTO details
     */
    PersonListResponseDTO getAllPerson(final Integer page,
                                       final Integer size);

    /**
     * Retrieves a person by the id.
     * If the id is not uuid format, a {@link com.chandrakumar.ms.api.error.FieldValidationException} will be thrown.
     * If the id does not exist, a {@link com.chandrakumar.ms.api.error.ResourceNotFoundException} will be thrown.
     *
     * @param personId the unique id of the person
     * @return the PersonDTO details
     */
    PersonDTO getPersonById(final String personId);
}