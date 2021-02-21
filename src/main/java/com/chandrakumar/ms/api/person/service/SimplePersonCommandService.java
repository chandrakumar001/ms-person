package com.chandrakumar.ms.api.person.service;

import com.chandrakumar.ms.api.exception.CommonUtilException;
import com.chandrakumar.ms.api.exception.ResourceAlreadyFoundException;
import com.chandrakumar.ms.api.exception.ResourceNotFoundException;
import com.chandrakumar.ms.api.person.dto.PersonDTO;
import com.chandrakumar.ms.api.person.entity.Person;
import com.chandrakumar.ms.api.person.mapper.PersonMapper;
import com.chandrakumar.ms.api.person.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

import static com.chandrakumar.ms.api.person.util.PersonConstant.*;
import static com.chandrakumar.ms.api.person.validation.PersonValidator.validatePersonDTO;
import static com.chandrakumar.ms.api.util.CommonUtil.validateUUID;

@Service
@Transactional
public class SimplePersonCommandService implements PersonCommandService {

    private final PersonRepository personRepository;

    public SimplePersonCommandService(@Autowired PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public PersonDTO createPerson(final PersonDTO personDTO) {

        validatePersonDTO(personDTO)
                .ifPresent(CommonUtilException::fieldValidationException);

        existingPersonByEmailId(personDTO.getEmailId())
                .ifPresent(this::personAlreadyFoundException);

        final Person person = PersonMapper.mapToPerson(personDTO, new Person());
        person.setPersonId(UUID.randomUUID());
        final Person newPerson = personRepository.save(person);
        return PersonMapper.mapToPersonDTO(newPerson);
    }


    @Override
    public PersonDTO updatePerson(final String personId,
                                  final PersonDTO personDTO) {

        validateUUID(personId, ERROR_THE_PERSON_ID_IS_INVALID_UUID_FORMAT)
                .ifPresent(CommonUtilException::fieldValidationException);
        final UUID personIdUUID = UUID.fromString(personId);

        validatePersonDTO(personDTO)
                .ifPresent(CommonUtilException::fieldValidationException);

        final Person existingPerson = existingPersonById(personIdUUID);
        final Person updatedPerson = PersonMapper.mapToPerson(personDTO, existingPerson);
        return PersonMapper.mapToPersonDTO(updatedPerson);
    }

    @Override
    public void deletePerson(final String personId) {

        validateUUID(personId, ERROR_THE_PERSON_ID_IS_INVALID_UUID_FORMAT)
                .ifPresent(CommonUtilException::fieldValidationException);
        final UUID personIdUUID = UUID.fromString(personId);

        existingPersonById(personIdUUID);
        personRepository.deleteById(personIdUUID);
    }

    private Person existingPersonById(final UUID personId) {
        return personRepository.findById(personId)
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