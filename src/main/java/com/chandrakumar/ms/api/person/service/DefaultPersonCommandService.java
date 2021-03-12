package com.chandrakumar.ms.api.person.service;

import com.chandrakumar.ms.api.error.FieldValidationException;
import com.chandrakumar.ms.api.error.ResourceAlreadyFoundException;
import com.chandrakumar.ms.api.error.ResourceNotFoundException;
import com.chandrakumar.ms.api.person.entity.Person;
import com.chandrakumar.ms.api.person.repository.PersonRepository;
import com.chandrakumar.ms.api.person.swagger.model.PersonBareDTO;
import com.chandrakumar.ms.api.person.swagger.model.PersonDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static com.chandrakumar.ms.api.common.audit.Action.CREATED;
import static com.chandrakumar.ms.api.common.audit.Action.UPDATED;
import static com.chandrakumar.ms.api.person.mapper.PersonMapper.mapToPerson;
import static com.chandrakumar.ms.api.person.mapper.PersonMapper.mapToPersonDTO;
import static com.chandrakumar.ms.api.person.util.PersonErrorCodeConstant.*;
import static com.chandrakumar.ms.api.person.validation.PersonValidator.validatePersonDTO;
import static com.chandrakumar.ms.api.util.CommonUtil.validateUUID;

@Service
@Transactional
@Slf4j
public class DefaultPersonCommandService implements PersonCommandService {

    private static final String UNKNOWN = "unknown";

    private final PersonRepository personRepository;
    private final AuditorAware<String> auditorAware;

    public DefaultPersonCommandService(
            @Autowired final PersonRepository personRepository,
            final AuditorAware<String> auditorAware) {

        this.personRepository = personRepository;
        this.auditorAware = auditorAware;
    }

    @Override
    public PersonDTO createPerson(final PersonBareDTO personBareDTO) {
        log.info("called createPerson begin");

        validatePersonDTO(personBareDTO)
                .ifPresent(FieldValidationException::fieldValidationException);

        existingPersonByEmailId(personBareDTO.getEmailId())
                .ifPresent(this::personAlreadyFoundException);

        final Person person = mapToPerson(personBareDTO, new Person());
        person.setPersonId(UUID.randomUUID());
        person.setAction(CREATED);
        final Person newPerson = personRepository.save(person);
        log.info("called createPerson end");
        return mapToPersonDTO(newPerson);
    }


    @Override
    public PersonDTO updatePerson(final String personId,
                                  final PersonBareDTO personBareDTO) {
        log.info("called updatePerson begin");

        validateUUID(personId, ERROR_THE_PERSON_ID_IS_INVALID_UUID_FORMAT)
                .ifPresent(FieldValidationException::fieldValidationException);
        final UUID personIdUUID = UUID.fromString(personId);

        validatePersonDTO(personBareDTO)
                .ifPresent(FieldValidationException::fieldValidationException);

        final Person existingPerson = existingPersonById(personIdUUID);
        mapToPerson(personBareDTO, existingPerson);
        existingPerson.setAction(UPDATED);

        final Person updatedPersonDB = personRepository.save(
                existingPerson
        );
        log.info("called updatePerson end");
        return mapToPersonDTO(updatedPersonDB);
    }

    @Override
    public void deletePerson(final String personId) {
        log.info("called deletePerson begin");

        validateUUID(personId, ERROR_THE_PERSON_ID_IS_INVALID_UUID_FORMAT)
                .ifPresent(FieldValidationException::fieldValidationException);
        final UUID personIdUUID = UUID.fromString(personId);

        existingPersonById(personIdUUID);

        final String name = auditorAware.getCurrentAuditor()
                .orElse(UNKNOWN);
        personRepository.softDeleteByPersonId(
                personIdUUID,
                new Date(),
                name
        );
        log.info("called deletePerson end");
    }

    private Person existingPersonById(final UUID personId) {
        return personRepository.findByPersonId(personId)
                .orElseThrow(this::personNotFoundException);
    }

    private Optional<Person> existingPersonByEmailId(final String emailId) {
        return personRepository.findByEmailId(emailId);
    }

    private RuntimeException personNotFoundException() {
        return new ResourceNotFoundException(ERROR_PERSON_ID_IS_NOT_FOUND);
    }

    private void personAlreadyFoundException(Person person) {
        throw new ResourceAlreadyFoundException(ERROR_THE_EMAIL_IS_ALREADY_EXISTS);
    }
}