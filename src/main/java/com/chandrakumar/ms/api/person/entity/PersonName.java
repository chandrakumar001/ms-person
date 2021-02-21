package com.chandrakumar.ms.api.person.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class PersonName implements Serializable {

    private String firstName;
    private String lastName;
}
