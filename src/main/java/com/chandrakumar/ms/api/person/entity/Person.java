package com.chandrakumar.ms.api.person.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "person", schema = "person")
@Data
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID personId;
    private String emailId;
    @Embedded
    private PersonName personName;
    private String age;
    private String favouriteColour;
}
