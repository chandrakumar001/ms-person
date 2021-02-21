package com.chandrakumar.ms.api.person.resource;

import com.chandrakumar.ms.api.person.service.PersonQueryService;
import com.chandrakumar.ms.api.person.swagger.model.PersonDTO;
import com.chandrakumar.ms.api.person.swagger.model.PersonListResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.chandrakumar.ms.api.person.resource.PersonURLConstant.V1_PERSONS_PERSON_ID_URL;
import static com.chandrakumar.ms.api.person.resource.PersonURLConstant.V1_PERSONS_URL;

@RestController
@Tag(name = "Person API")
//@ConditionalOnProperty(name = "app.write.enabled", havingValue = "false")
public class PersonQueryRestController {

    @Autowired
    PersonQueryService personQueryService;

    @GetMapping(V1_PERSONS_URL)
    @Operation(summary = "Retrieve all persons", description = "Retrieve all persons")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the person with the supplied id", content = @Content),
                    @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content)
            }
    )
    public ResponseEntity<PersonListResponseDTO> getAllPerson() {

        final PersonListResponseDTO personList = personQueryService.getAllPerson();
        return new ResponseEntity<>(personList, HttpStatus.OK);
    }

    @GetMapping(V1_PERSONS_PERSON_ID_URL)
    @Operation(summary = "Retrieve specific person with the supplied person id", description = "Retrieve specific person with the supplied person id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the person with the supplied id", content = @Content),
                    @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content)
            }
    )
    public ResponseEntity<PersonDTO> getPersonById(
            @PathVariable(name = PersonURLConstant.PERSON_ID_PATH) final String personId) {

        final PersonDTO personById = personQueryService.getPersonById(
                personId
        );
        return new ResponseEntity<>(personById, HttpStatus.OK);
    }
}