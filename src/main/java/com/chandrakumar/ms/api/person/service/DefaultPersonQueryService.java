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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.chandrakumar.ms.api.person.mapper.PersonMapper.getPersonListResponseDTO;
import static com.chandrakumar.ms.api.person.util.PageRequestBuild.getPageRequest;
import static com.chandrakumar.ms.api.person.util.PersonErrorCodeConstant.*;
import static com.chandrakumar.ms.api.person.validation.PageRequestValidator.validateRequest;
import static com.chandrakumar.ms.api.util.CommonUtil.validateUUID;

@Service
@Slf4j
public class DefaultPersonQueryService implements PersonQueryService {

    private final PersonRepository personRepository;

    public DefaultPersonQueryService(@Autowired PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public PersonListResponseDTO getAllPerson(final Integer page,
                                              final Integer size) {
        log.info("called getAllPerson begin");

        validateRequest(page, size)
                .ifPresent(FieldValidationException::fieldValidationException);

        final PageRequest pageRequest = getPageRequest(
                page,
                size
        );

        final Page<Person> personList = personRepository.findAll(
                pageRequest
        );
        final List<PersonDTO> personDTOList = getPersonDTOList(
                personList
        );
        log.info("called getAllPerson end");
        return getPersonListResponseDTO(personDTOList);
    }

    private List<PersonDTO> getPersonDTOList(final Page<Person> pagePersonList) {

        final List<Person> personList = pagePersonList.getContent();
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

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final Person existingPerson = existingPerson(personIdUUID);
        stopWatch.stop();
        log.info("Execution time of " + stopWatch.getTotalTimeMillis() + "ms");
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