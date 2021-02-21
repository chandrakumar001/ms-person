package com.chandrakumar.ms.api.person.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"emailId", "personName", "age", "favouriteColour"})
public class PersonDTO {

    @JsonProperty("email_id")
    private String emailId;
    @JsonProperty("person_name")
    private PersonNameDTO personName;
    private String age;
    @JsonProperty("favourite_colour")
    private String favouriteColour;
}