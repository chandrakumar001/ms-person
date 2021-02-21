package com.chandrakumar.ms.api.person.resource;

import com.chandrakumar.ms.api.person.dto.PersonDTO;
import com.chandrakumar.ms.api.person.service.PersonCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.chandrakumar.ms.api.person.resource.PersonURLConstant.*;

@RestController
@Tag(name = "Person API")
//@ConditionalOnProperty(name = "app.write.enabled", havingValue = "true")
public class PersonCommandRestController {

    @Autowired
    PersonCommandService personCommandService;

    @PostMapping(V1_PERSONS_URL)
    @Operation(summary = "Create a new person", description = "Create a new person")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Created a new person", content = @Content),
                    @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content)
            }
    )
    public ResponseEntity<PersonDTO> createPerson(
            @RequestBody PersonDTO personDTO) {

        final PersonDTO personResponse = personCommandService.createPerson(
                personDTO
        );
        return new ResponseEntity<>(personResponse, HttpStatus.CREATED);
    }

    @PutMapping(V1_PERSONS_PERSON_ID_URL)
    @Operation(summary = "Update a person information", description = "Update a person information")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Update a person information", content = @Content),
                    @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content)
            }
    )
    public ResponseEntity<PersonDTO> updatePerson(
            @PathVariable(name = PERSON_ID_PATH) final String personId,
            @RequestBody PersonDTO personDTO) {

        final PersonDTO updatePerson = personCommandService.updatePerson(
                personId,
                personDTO
        );
        return new ResponseEntity<>(updatePerson, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(V1_PERSONS_PERSON_ID_URL)
    @Operation(summary = "Deletes specific person with the supplied person id", description = "Deletes specific person with the supplied person id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Deletes specific person with the supplied person id", content = @Content),
                    @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content)
            }
    )
    public ResponseEntity<Void> deletePerson(
            @PathVariable(name = PERSON_ID_PATH) final String personId) {

        personCommandService.deletePerson(personId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}