package com.chandrakumar.ms.api.person.service;

import com.chandrakumar.ms.api.error.FieldValidationException;
import com.chandrakumar.ms.api.error.NoRecordFoundException;
import com.chandrakumar.ms.api.error.ResourceNotFoundException;
import com.chandrakumar.ms.api.person.entity.Person;
import com.chandrakumar.ms.api.person.mapper.PersonMapper;
import com.chandrakumar.ms.api.person.repository.PersonRepository;
import com.chandrakumar.ms.api.person.swagger.model.PersonDTO;
import com.chandrakumar.ms.api.person.swagger.model.PersonListResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.chandrakumar.ms.api.person.mapper.PersonMapper.getPersonListResponseDTO;
import static com.chandrakumar.ms.api.person.util.PersonErrorCodeConstant.*;
import static com.chandrakumar.ms.api.util.CommonUtil.validateUUID;

@Service
@Slf4j
public class DefaultPersonQueryService implements PersonQueryService {

    private final PersonRepository personRepository;

    public DefaultPersonQueryService(@Autowired PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public PersonListResponseDTO getAllPerson() {
        log.info("called getAllPerson begin");

        final List<Person> personList = personRepository.findAll();

        final List<PersonDTO> personDTOList = getPersonDTOList(personList);
        log.info("called getAllPerson end");
        return getPersonListResponseDTO(personDTOList);
    }

    private List<PersonDTO> getPersonDTOList(final List<Person> personList) {

        if (CollectionUtils.isEmpty(personList)) {
            throw new NoRecordFoundException(ERROR_NO_RECORD_FOUND);
        }
        return personList.stream()
                .map(PersonMapper::mapToPersonDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PersonDTO getPersonById(final String personId) {
        log.info("called getPersonById begin");

        validateUUID(personId, ERROR_THE_PERSON_ID_IS_INVALID_UUID_FORMAT)
                .ifPresent(FieldValidationException::fieldValidationException);
        final UUID personIdUUID = UUID.fromString(personId);

        final Person existingPerson = existingPerson(personIdUUID);
        log.info("called getPersonById begin");
        return PersonMapper.mapToPersonDTO(existingPerson);
    }

    private Person existingPerson(final UUID personId) {
        return personRepository.findByPersonId(personId)
                .orElseThrow(this::personNotFoundException);
    }

    private RuntimeException personNotFoundException() {
        return new ResourceNotFoundException(ERROR_PERSON_ID_IS_NOT_FOUND);
    }
}