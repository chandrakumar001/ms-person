package com.chandrakumar.ms.api.person.service;

import com.chandrakumar.ms.api.exception.CommonUtilException;
import com.chandrakumar.ms.api.exception.NoRecordFoundException;
import com.chandrakumar.ms.api.exception.ResourceNotFoundException;
import com.chandrakumar.ms.api.person.entity.Person;
import com.chandrakumar.ms.api.person.mapper.PersonMapper;
import com.chandrakumar.ms.api.person.repository.PersonRepository;
import com.chandrakumar.ms.api.person.swagger.model.PersonDTO;
import com.chandrakumar.ms.api.person.swagger.model.PersonListResponseDTO;
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
public class SimplePersonQueryService implements PersonQueryService {

    private final PersonRepository personRepository;

    public SimplePersonQueryService(@Autowired PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public PersonListResponseDTO getAllPerson() {

        final List<Person> personList = personRepository.findAll();

        final List<PersonDTO> personDTOList = getPersonDTOList(personList);
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

        validateUUID(personId, ERROR_THE_PERSON_ID_IS_INVALID_UUID_FORMAT)
                .ifPresent(CommonUtilException::fieldValidationException);
        final UUID personIdUUID = UUID.fromString(personId);

        final Person existingPerson = existingPerson(personIdUUID);
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