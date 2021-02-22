package com.chandrakumar.ms.api.person.service;

import com.chandrakumar.ms.api.exception.CommonUtilException;
import com.chandrakumar.ms.api.exception.ResourceAlreadyFoundException;
import com.chandrakumar.ms.api.exception.ResourceNotFoundException;
import com.chandrakumar.ms.api.person.entity.Person;
import com.chandrakumar.ms.api.person.mapper.PersonMapper;
import com.chandrakumar.ms.api.person.repository.PersonRepository;
import com.chandrakumar.ms.api.person.swagger.model.PersonBareDTO;
import com.chandrakumar.ms.api.person.swagger.model.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import static com.chandrakumar.ms.api.common.audit.Action.CREATED;
import static com.chandrakumar.ms.api.common.audit.Action.UPDATED;
import static com.chandrakumar.ms.api.person.mapper.PersonMapper.mapToPerson;
import static com.chandrakumar.ms.api.person.util.PersonConstant.*;
import static com.chandrakumar.ms.api.person.validation.PersonValidator.validatePersonDTO;
import static com.chandrakumar.ms.api.util.CommonUtil.validateUUID;

@Service
@Transactional
public class SimplePersonCommandService implements PersonCommandService {

    private static final String UNKNOWN = "unknown";

    private final PersonRepository personRepository;
    private final AuditorAware<String> auditorAware;

    public SimplePersonCommandService(
            @Autowired final PersonRepository personRepository,
            final AuditorAware<String> auditorAware) {

        this.personRepository = personRepository;
        this.auditorAware = auditorAware;
    }

    @Override
    public PersonDTO createPerson(final PersonBareDTO personBareDTO) {

        validatePersonDTO(personBareDTO)
                .ifPresent(CommonUtilException::fieldValidationException);

        existingPersonByEmailId(personBareDTO.getEmailId())
                .ifPresent(this::personAlreadyFoundException);

        final Person person = mapToPerson(personBareDTO, new Person());
        person.setPersonId(UUID.randomUUID());
        person.setAction(CREATED);
        final Person newPerson = personRepository.save(person);
        return PersonMapper.mapToPersonDTO(newPerson);
    }


    @Override
    public PersonDTO updatePerson(final String personId,
                                  final PersonBareDTO personBareDTO) {

        validateUUID(personId, ERROR_THE_PERSON_ID_IS_INVALID_UUID_FORMAT)
                .ifPresent(CommonUtilException::fieldValidationException);
        final UUID personIdUUID = UUID.fromString(personId);

        validatePersonDTO(personBareDTO)
                .ifPresent(CommonUtilException::fieldValidationException);

        final Person existingPerson = existingPersonById(personIdUUID);
        final Person updatedPerson = mapToPerson(personBareDTO, existingPerson);
        updatedPerson.setAction(UPDATED);
        return PersonMapper.mapToPersonDTO(updatedPerson);
    }

    @Override
    public void deletePerson(final String personId) {

        validateUUID(personId, ERROR_THE_PERSON_ID_IS_INVALID_UUID_FORMAT)
                .ifPresent(CommonUtilException::fieldValidationException);
        final UUID personIdUUID = UUID.fromString(personId);

        existingPersonById(personIdUUID);

        final String name = auditorAware.getCurrentAuditor()
                .orElse(UNKNOWN);
        personRepository.softDeleteByPersonId(
                personIdUUID,
                new Date(),
                name
        );
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